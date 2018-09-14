package fcu.selab.Acceleo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;

public class AcceleoGen {
	public static void generate(String[] args) {
		try {
	        if (args.length < 2) {
	            System.out.println("Arguments not valid : {model, folder}.");
	        } else {
	            URI modelURI = URI.createFileURI(args[0]);
	            File folder = new File(args[1]);
	            Generate.EcorePath = args[2];
	            
	            List<String> arguments = new ArrayList<String>();
	            
	            /*
	             * If you want to change the content of this method, do NOT forget to change the "@generated"
	             * tag in the Javadoc of this method to "@generated NOT". Without this new tag, any compilation
	             * of the Acceleo module with the main template that has caused the creation of this class will
	             * revert your modifications.
	             */

	            /*
	             * Add in this list all the arguments used by the starting point of the generation
	             * If your main template is called on an element of your model and a String, you can
	             * add in "arguments" this "String" attribute.
	             */
	            
	            Generate generator = new Generate(modelURI, folder, arguments);
	            
	            /*
	             * Add the properties from the launch arguments.
	             * If you want to programmatically add new properties, add them in "propertiesFiles"
	             * You can add the absolute path of a properties files, or even a project relative path.
	             * If you want to add another "protocol" for your properties files, please override 
	             * "getPropertiesLoaderService(AcceleoService)" in order to return a new property loader.
	             * The behavior of the properties loader service is explained in the Acceleo documentation
	             * (Help -> Help Contents).
	             */
	            
	            
	            generator.doGenerate(new BasicMonitor());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
