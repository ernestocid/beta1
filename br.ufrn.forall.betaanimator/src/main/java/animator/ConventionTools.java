package animator;

import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import parser.Operation;

public class ConventionTools {

	
//	public static String systemDirectoryDivider = "/";
	
	
	public static String getTestMachineName(Operation operation) {
		String machineName = operation.getMachine().getName();
		String operationName = operation.getName();
		String testMachineName = "TestsForOp_" + operationName + "_From_" + machineName;
		return testMachineName;
	}
	
	
	
//	/**
//	 * Return current directory where the jar file is located
//	 * @return path to the directory
//	 */
//	public static String getCurrentPath() {
//		try {
//			String directoryPath = URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource("").getPath(), "UTF-8");
//			return directoryPath;
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			return "";
//		}
//	}



	public static String getReportFileName(Operation operationUnderTest, PartitionStrategy chosenPartitionStrategy, CombinatorialCriterias chosenCombinatorialCriteria, String extension) {
		String operationUnderTestName = operationUnderTest.getName();
		String machineName = operationUnderTest.getMachine().getName();
		String partitionStrategyAcronym = chosenPartitionStrategy.getAcronym();
		String combinatorialCriteriaAcronym = chosenCombinatorialCriteria.getAcronym();
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
		Date currentDate = new Date();
		String reportDate = dateFormat.format(currentDate);
		
		return "report_for_" + operationUnderTestName + "_from_" + machineName + "_" + partitionStrategyAcronym + combinatorialCriteriaAcronym + "_" + reportDate + "." + extension;
	}	
}
