package fcu.selab.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.lin.Example_StateMachineToSubStateMachine.App;

import fcu.selab.iOSMDA.Generation;

@WebServlet(description = "Upload File To The Server", urlPatterns = { "/ProcessServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class ProcessServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_DIR = "uploadedFiles";

	/***** This Method Is Called By The Servlet Container To Process A 'POST' Request *****/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		System.out.println("Upload Finish");
		handleRequest(request, response);	
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/***** Get The Absolute Path Of The Web Application *****/
		String applicationPath = getServletContext().getRealPath("");
		String projectName = (String) request.getAttribute("iOSProjectName");
		System.out.println("tempPath: "+applicationPath);
		
		try {
			Generation.iOSMDAGen(applicationPath, projectName);
			request.setAttribute("projectName", projectName + ".zip");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fileName = (String) request.getAttribute("fileName");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/downloadServlet");
		dispatcher.forward(request, response);
	}
}