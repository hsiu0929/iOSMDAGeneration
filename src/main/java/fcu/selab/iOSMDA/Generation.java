package fcu.selab.iOSMDA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.Properties;

import org.apache.tools.ant.Main;
import org.apache.velocity.app.Velocity;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.m2m.atl.sample.files.IOSModel;


import com.lin.StateMachineToSubStateMachine.StateMachineToSubStateMachineWeb;

import fcu.selab.Acceleo.AcceleoGen;
import fcu.selab.Velocity.GenerateFile.GenerateProject;


public class Generation 
{
	
    public static void iOSMDAGen(String applicationPath, String projectName) throws IOException, InterruptedException, Exception
    {
    	long startTime = System.currentTimeMillis(); 
    	//String projectName = "AddTest";
    	
        System.out.println( "StateMachine to iOS Model" );
        
        reviseATLProperties(applicationPath);
        String[] StateMachineToModelAddress = {"file:///"+applicationPath+"WEB-INF/resource/model.uml",
        		"file:///"+applicationPath+"WEB-INF/resource/iOSModel/Main.storyboard", applicationPath+"WEB-INF/resource/IOSModel.properties"};
        IOSModel.main(StateMachineToModelAddress);
        
        
        reviseSubStateMachineProperties(applicationPath);
        StateMachineToSubStateMachineWeb stateMachineToSubStateMachineWeb = new StateMachineToSubStateMachineWeb();
		stateMachineToSubStateMachineWeb.StateMachineToSubStateMachineGenerate(applicationPath+"WEB-INF/resource/propertiesWeb.xml");
		
		
		int subStateMachieCount; //計算檔案數量(每條LINE)
		String folderPath = applicationPath+"WEB-INF/resource/subStateMachine";
		subStateMachieCount = getFileList(folderPath);
		System.out.println(subStateMachieCount + "條測試案例分析");
		
		GenerateProject.GenerateFile(projectName, subStateMachieCount, applicationPath); //生成專案其他檔案
		
		for(int i = 0; i < subStateMachieCount; i++){ //把每條LINE生成一個iOS Model(xmi檔)
			String[] StateMachineToModelAddressForEachTese = {"file:///"+applicationPath+"WEB-INF/resource/subStateMachine/subStateMachine_" + i + ".uml", "file:///"+applicationPath+"WEB-INF/resource/subStateMachineToiOSModel/subStateMachine_" + i + ".xmi", applicationPath+"WEB-INF/resource/IOSModel.properties"};
	        IOSModel.main(StateMachineToModelAddressForEachTese);
		}
		
		
		File emtl = new File(applicationPath+"WEB-INF/classes/fcu/selab/Acceleo/generate.emtl");
		if(!emtl.exists()){
			//移動emtl檔案到classes的Acceleo資料夾（否則無法編譯）
			FileMove.moveFile(applicationPath+"WEB-INF/resource/AcceleoEmtl", applicationPath+"WEB-INF/classes/fcu/selab/Acceleo/");
		}
		
		
		for(int i = 0; i < 6; i++){
			//String[] SingleLineToText = {applicationPath+"WEB-INF/resource/subStateMachineToiOSModel/subStateMachine_" + i + ".xmi" , applicationPath+"WEB-INF/resource/TestCaseSwift", "src/main/webapp/WEB-INF/resource/Metamodel/iOSModel.ecore"};
			//Generate.main(SingleLineToText); //Acceleo的執行方法
			String[] SingleLineToText = {applicationPath+"WEB-INF/resource/subStateMachineToiOSModel/subStateMachine_" + i + ".xmi" , applicationPath+"WEB-INF/resource/TestCaseSwift", applicationPath+"WEB-INF/resource/Metamodel/iOSModel.ecore"};
			AcceleoGen.generate(SingleLineToText);
			//新增每個測試檔案並修改檔名與使用者取的專案名稱一致
			new File(applicationPath+"WEB-INF/resource/TestCaseSwift/TestUITests.swift").renameTo(new File(applicationPath+"WEB-INF/resource/TestCaseSwift/" + projectName + "UITests_" + (i+1) + ".swift"));
			
			//將className改完寫入每個UITests檔
			ModifyContent modifySwift = new ModifyContent();
			modifySwift.modifyContent(applicationPath+"WEB-INF/resource/TestCaseSwift/" + projectName + "UITests_" + (i+1) + ".swift", "TestUITests", projectName + "UITests_" + (i+1));
			modifySwift.modifyContent(applicationPath+"WEB-INF/resource/TestCaseSwift/" + projectName + "UITests_" + (i+1) + ".swift", "test1", "test_" + (i+1));
			//將測試檔案移到專案資料夾
			FileMove.moveFile(applicationPath+"WEB-INF/resource/TestCaseSwift", applicationPath+"WEB-INF/resource/"+ projectName + "/" + projectName + "UITests/");
		}
		
		//將ATL生成好的Main.storyboard移到Bast.lproj資料夾中
	    FileMove.moveFile(applicationPath+"WEB-INF/resource/iOSModel", applicationPath+"WEB-INF/resource/"+ projectName + "/" + projectName + "/Base.lproj/");
		
		Compress.compress(applicationPath+"WEB-INF/resource/"+ projectName, applicationPath+"WEB-INF/resource/"+ projectName + ".zip"); //壓縮整個資料夾(來源, 目的)
        
		File file = new File(applicationPath + "WEB-INF/resource/Models/model.uml");
		
		if(file.exists()){ //刪除上傳的uml檔案
			file.delete();
			System.out.println("檔案刪除");
		}
		else{
			System.out.println("檔案不存在");
		}
        System.out.println("Using Time:" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("完成新增 " + projectName + ".zip");

    }
    
    public static void reviseATLProperties(String applicationPath) throws FileNotFoundException, IOException
    {
    	Properties properties = new Properties();
    	properties.load(new FileInputStream(applicationPath+"WEB-INF/resource/IOSModel.properties"));
    	
    	String metamodel = properties.getProperty("IOSModel.metamodels.iOSModel");
    	if(!metamodel.startsWith("file:///"))
    	{
    		properties.setProperty("IOSModel.metamodels.iOSModel", "file:///"+applicationPath+metamodel);
        	properties.store(new FileOutputStream(applicationPath+"WEB-INF/resource/IOSModel.properties"), "store");
    	}	
    }
    
    public static void reviseSubStateMachineProperties(String applicationPath) throws FileNotFoundException, IOException
    {
    	Properties properties = new Properties();
    	properties.loadFromXML(new FileInputStream(applicationPath+"WEB-INF/resource/propertiesWeb.xml"));
    	
    	if(!properties.getProperty("MainStateMachinePath").startsWith(applicationPath))
    	{
	    	String MainStateMachinePath = properties.getProperty("MainStateMachinePath");
	    	String SubStateMachinePath = properties.getProperty("SubStateMachinePath");
	    	String FileResourceLoaderPath = properties.getProperty("file.resource.loader.path");
	    	
	    	properties.put("MainStateMachinePath", applicationPath + MainStateMachinePath);
	    	properties.put("SubStateMachinePath", applicationPath + SubStateMachinePath);
	    	properties.put("file.resource.loader.path", applicationPath + FileResourceLoaderPath);
	        properties.storeToXML(new FileOutputStream(applicationPath + "WEB-INF/resource/propertiesWeb.xml"), "StateMachineToSubStateMachineGenerate");
    	}
    }
    
    public static int getFileList(String folderPath){   //抓總共有幾條測試案例的線
        //String subStateMachine = "D:/Data/M2Tworkspace/iOSMDA/resource/subStateMachine";//資料夾路徑
        StringBuffer fileList = new StringBuffer();
            try{
               java.io.File folder = new java.io.File(folderPath);
               String[] list = folder.list();
               //System.out.println(list.length);
               return list.length;
                }catch(Exception e){
                      System.out.println("'"+folderPath+"此資料夾不存在");
                } 
                System.out.println(fileList);
				return 0;
        }
    
    public static void generateM2M(String[] args) {
		try {
			if (args.length < 2) {
				System.out.println("Arguments not valid : {IN_model_path, OUT_model_path}.");
			} else {
				
				//"IOSModel.properties"
				IOSModel runner = new IOSModel(args[2]);
				runner.loadModels(args[0]);
				runner.doIOSModel(new NullProgressMonitor());
				runner.saveModels(args[1]);
			}
		}catch (Exception e) {
			e.printStackTrace();
			}
	}
}