package integration;

import static org.junit.Assert.*;
import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import reports.XMLReport;
import testgeneration.BETATestSuite;
import tools.FileTools;

public class XMLReportGenerationTest {

	
	@Before
	public void setUp() {
		Configurations.setAutomaticOracleEvaluation(true);
	}
	
	
	
	@After
	public void tearDown() {
		Configurations.setAutomaticOracleEvaluation(false);
	}
	
	
	
	@Test
	public void shouldGenerateXMLReportForSimpleLift() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimpleLift.mch"));
		Operation operationUnderTest = machine.getOperation(2);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.EACH_CHOICE);
		
		XMLReport xmlReport = new XMLReport(testSuite, new File("src/test/resources/test_reports/xml/simplelift_report.xml"));
		xmlReport.generateReport();
		
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/expected_simplelift_report.xml"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/simplelift_report.xml"));
		
		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldGenerateXMLReportForMachinesWithSets() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/WithSets.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.ALL_COMBINATIONS);
		
		XMLReport xmlReport = new XMLReport(testSuite, new File("src/test/resources/test_reports/xml/WithSets_teste_EC_AC_report.xml"));
		xmlReport.generateReport();
		
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/expected_WithSets_teste_EC_AC_report.xml"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/WithSets_teste_EC_AC_report.xml"));
		
		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldGenerateXMLRerportForOperationsWithReturnValues() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Calc.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.ALL_COMBINATIONS);
		
		XMLReport xmlReport = new XMLReport(testSuite, new File("src/test/resources/test_reports/xml/Calc_sum_EC_AC_report.xml"));
		xmlReport.generateReport();
		
		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/expected_Calc_sum_EC_AC_report.xml"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/Calc_sum_EC_AC_report.xml"));
		
		assertEquals(expectedReport, actualReport);
	}
}
