package integration;

import static org.junit.Assert.*;
import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import reports.HTMLReport;
import testgeneration.BETATestSuite;
import tools.FileTools;

public class HTMLReportGenerationTest {

	
	@Before
	public void setUp() {
		Configurations.setMinIntProperties(-1);
	}
	

	@Test
	public void shouldGenerateTestReportForOperationUnderTest() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimpleLift.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.EACH_CHOICE);
		
		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/simplelift_report.html"));
		report.generateReport();
		
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_simplelift_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/simplelift_report.html"));
		
		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldGenerateTestReportForBoundaryValuesAllCombinations() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimpleLift.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.BOUNDARY_VALUES, CombinatorialCriterias.ALL_COMBINATIONS);
		
		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/simplelift_BV_AC_report.html"));
		report.generateReport();
		
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/simplelift_BV_AC_report.html"));
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_simplelift_BV_AC_report.html"));
		
		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldGenerateReportForMachineWithoutState() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/NoState.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.ALL_COMBINATIONS);
		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/report_for_Set_from_NoState.html"));
		report.generateReport();
		
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/report_for_Set_from_NoState.html"));
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_report_for_Set_from_NoState.html"));
		
		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldGenerateHTMLReportForOperationWithReturnValues() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Calc.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.ALL_COMBINATIONS);
		
		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/Calc_sum_EC_AC_report.html"));
		report.generateReport();
		
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_Calc_sum_EC_AC_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/Calc_sum_EC_AC_report.html"));
		
		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldGenerateHTMLReportForOperationWithParametersAndMachineWithState() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Inc.mch"));
		
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.ALL_COMBINATIONS);
		
		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/Inc_inc_EC_AC_report.html"));
		report.generateReport();
		
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_Inc_inc_EC_AC_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/Inc_inc_EC_AC_report.html"));
		
		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldGenerateHTMLReportForOperationWithParametersAndMachineWithState2() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.ALL_COMBINATIONS);
		
		HTMLReport report = new HTMLReport(testSuite, new File("src/test/resources/test_reports/html/Player_substitute_EC_AC_report.html"));
		report.generateReport();
		
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/expected_Inc_inc_EC_AC_report.html"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/html/Player_substitute_EC_AC_report.html"));
		
		assertEquals(expectedReport, actualReport);
	}
	
}
