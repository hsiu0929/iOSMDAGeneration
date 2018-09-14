package fcu.selab.Velocity.GenerateFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class ExecTerminal{
	public printOutput getStreamWrapper(InputStream is, String type) {
		return new printOutput(is, type);
	}
 
	public static void ExecRuby(String fileName) throws InterruptedException {
 
		Runtime rt = Runtime.getRuntime();
		ExecTerminal rte = new ExecTerminal();
		printOutput errorReported, outputMessage;
		//rte.start();
		try {
			Process proc = rt.exec("ruby "+ fileName);
			proc.waitFor();
			errorReported = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
			outputMessage = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
			errorReported.start();
			outputMessage.start();
			//Thread.sleep(1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
	private class printOutput extends Thread {
		InputStream is = null;
 
		printOutput(InputStream is, String type) {
			this.is = is;
		}
 
		public void run() {
			String s = null;
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				while ((s = br.readLine()) != null) {
					System.out.println(s);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
