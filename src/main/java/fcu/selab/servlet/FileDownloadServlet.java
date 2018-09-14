package fcu.selab.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Download File From The Server", urlPatterns = { "/downloadServlet" })
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static int BUFFER_SIZE = 1024 * 100;
	public static final String UPLOAD_DIR = "uploadedFiles";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		handleRequest(req, resp);
	}



	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/***** Get The Absolute Path Of The File To Be Downloaded *****/
		String projectName = (String) request.getAttribute("projectName"),
				applicationPath = getServletContext().getRealPath(""),
				//downloadPath = applicationPath + "WEB-INF/resource/Models",
				filePath = applicationPath + "WEB-INF/resource/" + projectName;
		System.out.println("下載: " + projectName);

		File file = new File(filePath);
		OutputStream outStream = null;
		FileInputStream inputStream = null;

		if (file.exists()) {

			/**** Setting The Content Attributes For The Response Object ****/
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);

			/**** Setting The Headers For The Response Object ****/
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
			response.setHeader(headerKey, headerValue);

			try {

				/**** Get The Output Stream Of The Response ****/
				outStream = response.getOutputStream();
				inputStream = new FileInputStream(file);
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;

				/**** Write Each Byte Of Data Read From The Input Stream Write Each Byte Of Data  Read From The Input Stream Into The Output Stream ****/
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}				
			} catch(IOException ioExObj) {
				System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
			} finally {				
				if (inputStream != null) {
					inputStream.close();
				}

				outStream.flush();
				if (outStream != null) {
					outStream.close();
				}
			}
		} else {

			/***** Set Response Content Type *****/
			response.setContentType("text/html");

			/***** Print The Response *****/
			response.getWriter().println("<h3>File "+ projectName +" Is Not Present .....!</h3>");
		}
	}
}