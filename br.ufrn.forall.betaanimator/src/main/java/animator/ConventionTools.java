package animator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import parser.Operation;

public class ConventionTools {

	
	public static String systemDirectoryDivider = "/";
	
	
	public static String getTestMachineName(Operation operation) {
		String machineName = operation.getMachine().getName();
		String operationName = operation.getName();
		String testMachineName = "TestsForOp_" + operationName + "_From_" + machineName;
		return testMachineName;
	}
	
	
	
	/**
	 * Return current directory where the jar file is located
	 * @return path to the directory
	 */
	public static String getCurrentPath() {
		try {
			String directoryPath = URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource("").getPath(), "UTF-8");
			return directoryPath;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}



	public static String getReportFileName(String machineName, String operationUnderTestName, String extension) {
		return "report_for_" + operationUnderTestName + "_from_" + machineName + "." + extension;
	}	
}
