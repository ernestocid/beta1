package integration;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.*;
import general.CombinatorialCriteria;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import reports.XMLReport;
import testgeneration.BETATestSuite;
import testgeneration.coveragecriteria.ClauseCoverage;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.coveragecriteria.EquivalenceClasses;
import tools.FileTools;

public class XMLReportGenerationTest {

	@Before
	public void setUp() {
		Configurations.setMaxIntProperties(20);
		Configurations.setMinIntProperties(-1);
		Configurations.setAutomaticOracleEvaluation(true);
		Configurations.setUseKodkod(false);
		Configurations.setRandomiseEnumerationOrder(false);
		Configurations.setUseProBApiToSolvePredicates(false);
		Configurations.setFindPreamble(false);
		Configurations.setDeleteTempFiles(true);
		Configurations.setOracleStrategy(true, true, true);
	}



	@After
	public void tearDown() {
		Configurations.setAutomaticOracleEvaluation(false);
	}



	@Test
	public void shouldGenerateXMLReportForSimpleLift() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SimpleLift.mch"));
		Operation operationUnderTest = machine.getOperation(2);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.EACH_CHOICE);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

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
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

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
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		XMLReport xmlReport = new XMLReport(testSuite, new File("src/test/resources/test_reports/xml/Calc_sum_EC_AC_report.xml"));
		xmlReport.generateReport();

		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/expected_Calc_sum_EC_AC_report.xml"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/Calc_sum_EC_AC_report.xml"));

		assertEquals(expectedReport, actualReport);
	}



	@Test
	public void shouldGenerateXMLReportForWithPreambles() {
		Configurations.setFindPreamble(true);
		Configurations.setAutomaticOracleEvaluation(false);

		Machine machine = new Machine(new File("src/test/resources/machines/others/Course.mch"));
		Operation operationUnderTest = machine.getOperation(3); // student_pass_or_fail
		CoverageCriterion coverageCriterion = new ClauseCoverage(operationUnderTest);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		XMLReport report = new XMLReport(testSuite, new File("src/test/resources/test_reports/xml/student_pass_or_fail_LC_CC_report.xml"));
		report.generateReport();

		String expectedReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/expected_student_pass_or_fail_LC_CC_report.xml"));
		String actualReport = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/student_pass_or_fail_LC_CC_report.xml"));

		assertEquals(expectedReport, actualReport);
	}
	
	
	
	@Test
	public void shouldContainExistentialTestFormula() {
		Configurations.setAutomaticOracleEvaluation(false);
		Configurations.setUseProBApiToSolvePredicates(true);
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/RefinementExamples/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substitute
		CoverageCriterion coverageCriterion = new ClauseCoverage(operationUnderTest);
		
		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		XMLReport report = new XMLReport(testSuite, new File("src/test/resources/test_reports/xml/substitute_LC_CC_report.xml"));
		report.generateReport();
		
		String XML = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/substitute_LC_CC_report.xml"));
		
		assertThat(XML).contains("<existential-formula>#rr, pp, team.(not(rr : 1..22) &amp; pp : 1..22 &amp; pp : team &amp; rr /: team &amp; team &lt;: 1..22 &amp; card(team) = 11)</existential-formula>");
		assertThat(XML).contains("<existential-formula>#rr, pp, team.(pp : 1..22 &amp; rr : 1..22 &amp; not(pp : team) &amp; rr /: team &amp; team &lt;: 1..22 &amp; card(team) = 11)</existential-formula>");
		assertThat(XML).contains("<existential-formula>#rr, pp, team.(pp : 1..22 &amp; rr : 1..22 &amp; pp : team &amp; not(rr /: team) &amp; team &lt;: 1..22 &amp; card(team) = 11)</existential-formula>");
		assertThat(XML).contains("<existential-formula>#rr, pp, team.(rr : 1..22 &amp; pp : 1..22 &amp; pp : team &amp; rr /: team &amp; team &lt;: 1..22 &amp; card(team) = 11)</existential-formula>");
		assertThat(XML).contains("<existential-formula>#rr, pp.(rr : 1..22 &amp; pp : team &amp; not(pp : 1..22) &amp; rr /: team &amp; team &lt;: 1..22 &amp; card(team) = 11)</existential-formula>");
	}
}
