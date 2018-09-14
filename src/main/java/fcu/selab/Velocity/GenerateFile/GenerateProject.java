package fcu.selab.Velocity.GenerateFile;


import java.io.*;
import org.apache.velocity.*;

import fcu.selab.iOSMDA.Compress;
import fcu.selab.iOSMDA.ModifyContent;

public class GenerateProject {

	public static void GenerateFile(String projectName, int subStateMachieCount, String applicationPath) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//String projectName = "Test";
		String projectLocation = applicationPath + "WEB-INF/resource/" + projectName;
		String xcodeproj = ".xcodeproj";
		String Tests = "Tests";
		String UITests = "UITests";
		String Assets = "Assets.xcassets";
		String Base = "Base.lproj";
		String AppIcon = "AppIcon.appiconset";
		String projectxcworkspace = "project.xcworkspace";
		String xcshareddata ="xcshareddata";
		
		//Create first Folder
		File Folder = new File(projectLocation);
		File FolderProject = new File(projectLocation + "/" + projectName);
		File Folderxcodeproj = new File(projectLocation + "/" + projectName + xcodeproj);
		File FolderTests = new File(projectLocation + "/" + projectName + Tests);
		File FolderUITests = new File(projectLocation + "/" + projectName + UITests);
		
		//Create projectName Folder
		File FolderAssets = new File(projectLocation + "/" + projectName + "/" + Assets);
		File FolderBase = new File(projectLocation + "/" + projectName + "/" + Base);
		File FolderAppIcon = new File(projectLocation + "/" + projectName + "/" + Assets + "/" + AppIcon);
		
		//Create xcodeproj Folder
		File FolderProjectxcworkspece = new File(projectLocation + "/" + projectName + xcodeproj + "/" + projectxcworkspace);
		File Folderxcshareddata = new File(projectLocation + "/" + projectName + xcodeproj + "/" + projectxcworkspace + "/" + xcshareddata);
		
		Folder.mkdir();//生成專案資料夾
		FolderProject.mkdir();//生成專案資料夾的資料夾
		Folderxcodeproj.mkdir();
		FolderTests.mkdir();
		FolderUITests.mkdir();
		
		FolderAssets.mkdir();//生成Assets資料夾
		FolderBase.mkdir();
		FolderAppIcon.mkdir();
		
		FolderProjectxcworkspece.mkdir();
		Folderxcshareddata.mkdir();
		
		
		new AppDelegateSwift("AppDelegateSwift.vm", projectLocation + "/" + projectName + "/", "AppDelegate.swift",projectName);
		new ProjectNameInfoPlist("projectNameInfo.vm", projectLocation + "/" + projectName + "/", "Info.plist");
		new ViewControllerSwift("ViewControllerSwift.vm", projectLocation + "/" + projectName + "/", "ViewController.swift",projectName);
		new AssetsContentsJson("Contents.vm",projectLocation + "/" + projectName + "/" + Assets + "/", "Contents.json");
		new AppIconContestsJson("AppIconContents.vm",projectLocation + "/" + projectName + "/" + Assets + "/" + AppIcon + "/", "Contents.json");
		new LaunchScreenStoryboard("LaunchScreen.vm",projectLocation + "/" + projectName + "/" + Base +  "/", "LaunchScreen.storyboard");
		new ProjectPbxproj("project.vm",projectLocation + "/" + projectName + xcodeproj + "/", "project.pbxproj", projectName);
		new ContentsXcworkspaceData("contentsxcworkspacedata.vm", projectLocation + "/" + projectName + xcodeproj + "/" + projectxcworkspace + "/", "contents.xcworkspacedata", projectName);
		new IDEWorkspaceChecksPlist("IDEWorkspaceChecks.vm", projectLocation + "/" + projectName + xcodeproj + "/" + projectxcworkspace + "/" + xcshareddata + "/", "IDEWorkspaceChecks.plist");
		new TestsInfoPlist("TestsInfo.vm", projectLocation + "/" + projectName + Tests + "/", "Info.plist");
		new TestsSwift("Tests.vm", projectLocation + "/" + projectName + Tests + "/", projectName + "Tests.swift", projectName);
		new UITestsInfoPlist("UITestsInfo.vm", projectLocation + "/" + projectName + UITests + "/", "Info.plist");
		new UITestsSwift("UITests.vm", projectLocation + "/" + projectName + UITests + "/", projectName + "UITests.swift", projectName);
		
		
		for(int i = 1; i <= subStateMachieCount; i++){
			new RubyAddTestFile("RubyAddTestFile.vm", applicationPath + "WEB-INF/resource/ruby/",projectName + "UITests_" + i +".rb", projectName, i, applicationPath);
			ExecTerminal.ExecRuby(applicationPath + "WEB-INF/resource/ruby/" + projectName + "UITests_" + i +".rb");//終端機執行ruby檔
			System.out.println("Finish Exec Ruby" + i);
		}
		
		//修改proj檔案裡面測試檔案位置因為JAVA專案的關係會有目錄上面的問題（將projectName/projectNameTest這行刪除)
		ModifyContent modifyProj = new ModifyContent();
		modifyProj.modifyContent(projectLocation + "/" + projectName + xcodeproj + "/project.pbxproj", projectName + "/" + projectName + "UITests/", "");
		
	}

}



