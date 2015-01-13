package animator;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import parser.Operation;

public class ConventionTools {

	public static String getTestMachineName(Operation operation) {
		String machineName = operation.getMachine().getName();
		String operationName = operation.getName();
		String testMachineName = "TestsForOp_" + operationName + "_From_" + machineName;
		return testMachineName;
	}



	public static String getReportFileName(Operation operationUnderTest, String coverageCriterionAcronym, String extension) {
		String operationUnderTestName = operationUnderTest.getName();
		String machineName = operationUnderTest.getMachine().getName();
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
		Date currentDate = new Date();
		String reportDate = dateFormat.format(currentDate);
		
		return "report_for_" + operationUnderTestName + "_from_" + machineName + "_" + coverageCriterionAcronym + "_" + reportDate + "." + extension;
	}

}
