package unit;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.*;
import general.CombinatorialCriteria;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import testgeneration.BETATestCase;
import testgeneration.BETATestSuite;
import testgeneration.coveragecriteria.ActiveClauseCoverage;
import testgeneration.coveragecriteria.BoundaryValueAnalysis;
import testgeneration.coveragecriteria.ClauseCoverage;
import testgeneration.coveragecriteria.CombinatorialClauseCoverage;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.coveragecriteria.EquivalenceClasses;
import testgeneration.coveragecriteria.PredicateCoverage;
import testgeneration.preamblecalculation.Event;

public class BETATestSuiteTest {


	@Before
	public void setup() {
		Configurations.setFindPreamble(false);
	}



	@Test
	public void shouldGenerateTestCasesForSingleCharacteristics() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/MaxIntMinIntTest.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new BoundaryValueAnalysis(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		assertFalse(testSuite.getTestCases().isEmpty());
	}



	@Test
	public void shouldIdentifyPositiveAndNegativeTestCases() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		CoverageCriterion coverageCriterion = new BoundaryValueAnalysis(operationUnderTest, CombinatorialCriteria.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		assertTrue(testSuite.getTestCases().get(0).isNegative());
		assertFalse(testSuite.getTestCases().get(1).isNegative());
	}



	@Test
	public void shouldGetFeasibleTestCaseFormulasWithoutInvariant() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Classroom.mch"));
		Operation operationUnderTest = machine.getOperation(3);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.EACH_CHOICE);

		List<String> expectedTestCaseFormulas = new ArrayList<String>();
		expectedTestCaseFormulas
				.add("grades(student) > 2 & grades(student) > 3 & has_taken_lab_classes(student) = TRUE & student : dom(grades) & student : dom(has_taken_lab_classes) & student : students");

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		assertEquals(expectedTestCaseFormulas, testSuite.getFeasbileTestCaseFormulasWithoutInvariant());
	}



	@Test
	public void shouldCreateTestCasesForClauseCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new ClauseCoverage(operationUnderTest);

		Set<String> expectedTestCaseFormulas = new HashSet<String>();

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade < 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 2 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade < 4) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 2) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 4) & averageGrade : INT");
		expectedTestCaseFormulas.add("not(averageGrade : 0..5) & averageGrade : INT");

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		Set<String> actualTestCaseFormulas = new HashSet<String>();

		for (BETATestCase testCase : testSuite.getTestCases()) {
			actualTestCaseFormulas.add(testCase.getTestFormula());
		}

		assertEquals(expectedTestCaseFormulas, actualTestCaseFormulas);
	}



	@Test
	public void shouldCreateTestCasesForPredicateCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new PredicateCoverage(operationUnderTest);

		Set<String> expectedTestCaseFormulas = new HashSet<String>();

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade : INT");
		expectedTestCaseFormulas.add("not(averageGrade : 0..5 & averageGrade : INT)");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 2 & averageGrade < 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 2 & averageGrade < 4) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 4) & averageGrade : INT");

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		Set<String> actualTestCaseFormulas = new HashSet<String>();

		for (BETATestCase testCase : testSuite.getTestCases()) {
			actualTestCaseFormulas.add(testCase.getTestFormula());
		}

		assertEquals(expectedTestCaseFormulas, actualTestCaseFormulas);
	}



	@Test
	public void shouldCreateTestCasesForCombinatorialClauses() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new CombinatorialClauseCoverage(operationUnderTest);

		Set<String> expectedTestCaseFormulas = new HashSet<String>();

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade : INT");
		expectedTestCaseFormulas.add("not(averageGrade : 0..5) & averageGrade : INT");

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 4) & averageGrade : INT");

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 2 & averageGrade < 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 2) & averageGrade < 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 2 & not(averageGrade < 4) & averageGrade : INT");

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		Set<String> actualTestCaseFormulas = new HashSet<String>();

		for (BETATestCase testCase : testSuite.getTestCases()) {
			actualTestCaseFormulas.add(testCase.getTestFormula());
		}

		// Asserting feasible test cases are correct
		assertEquals(expectedTestCaseFormulas, actualTestCaseFormulas);

		// Asserting infeasible test cases are correct
		assertEquals("averageGrade : 0..5 & averageGrade : INT & not(averageGrade < 4) & not(averageGrade >= 2)", testSuite.getUnsolvableFormulas().get(0));
	}



	@Test
	public void shouldCreateTestCasesForActiveClauseCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new ActiveClauseCoverage(operationUnderTest);

		Set<String> expectedTestCaseFormulas = new HashSet<String>();

		expectedTestCaseFormulas.add("averageGrade : 0..5 & ((1 = 1 & averageGrade < 4) <=> (not(1 = 2 & averageGrade < 4))) & not(averageGrade >= 2) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & ((averageGrade >= 2 & 1 = 1) <=> (not(averageGrade >= 2 & 1 = 2))) & not(averageGrade < 4) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & ((averageGrade >= 2 & 1 = 1) <=> (not(averageGrade >= 2 & 1 = 2))) & averageGrade < 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("((1 = 1 & averageGrade : INT) <=> (not(1 = 2 & averageGrade : INT))) & not(averageGrade : 0..5)");
		expectedTestCaseFormulas.add("((averageGrade : 0..5 & 1 = 1) <=> (not(averageGrade : 0..5 & 1 = 2))) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 4) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 2 & ((1 = 1 & averageGrade < 4) <=> (not(1 = 2 & averageGrade < 4))) & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & ((1 = 1 & averageGrade : INT) <=> (not(1 = 2 & averageGrade : INT)))");

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		Set<String> actualTestCaseFormulas = new HashSet<String>();

		for (BETATestCase testCase : testSuite.getTestCases()) {
			actualTestCaseFormulas.add(testCase.getTestFormula());
		}

		// Asserting feasible test cases are correct
		assertEquals(expectedTestCaseFormulas, actualTestCaseFormulas);

		// Asserting infeasible test cases are correct
		assertEquals("((averageGrade : 0..5 & 1 = 1) <=> (not(averageGrade : 0..5 & 1 = 2))) & not(averageGrade : INT)", testSuite.getUnsolvableFormulas().get(0));
	}
	
	
	
	
	@Test
	public void shouldCreateTestCasesForActiveClauseCoverageAndFindPreamble() {
		Configurations.setFindPreamble(true);

		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new ActiveClauseCoverage(operationUnderTest);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		assertThat(testSuite.getTestCases().get(0).getTestFormula()).isEqualTo("((1 = 1 & averageGrade : INT) <=> (not(1 = 2 & averageGrade : INT))) & not(averageGrade : 0..5)");
		assertThat(testSuite.getTestCases().get(1).getTestFormula()).isEqualTo("((averageGrade : 0..5 & 1 = 1) <=> (not(averageGrade : 0..5 & 1 = 2))) & averageGrade : INT");
		assertThat(testSuite.getTestCases().get(2).getTestFormula()).isEqualTo("averageGrade : 0..5 & ((1 = 1 & averageGrade : INT) <=> (not(1 = 2 & averageGrade : INT)))");
		assertThat(testSuite.getTestCases().get(3).getTestFormula()).isEqualTo("averageGrade : 0..5 & ((1 = 1 & averageGrade < 4) <=> (not(1 = 2 & averageGrade < 4))) & not(averageGrade >= 2) & averageGrade : INT");
		assertThat(testSuite.getTestCases().get(4).getTestFormula()).isEqualTo("averageGrade : 0..5 & ((averageGrade >= 2 & 1 = 1) <=> (not(averageGrade >= 2 & 1 = 2))) & averageGrade < 4 & averageGrade : INT");
		assertThat(testSuite.getTestCases().get(5).getTestFormula()).isEqualTo("averageGrade : 0..5 & ((averageGrade >= 2 & 1 = 1) <=> (not(averageGrade >= 2 & 1 = 2))) & not(averageGrade < 4) & averageGrade : INT");
		assertThat(testSuite.getTestCases().get(6).getTestFormula()).isEqualTo("averageGrade : 0..5 & averageGrade >= 2 & ((1 = 1 & averageGrade < 4) <=> (not(1 = 2 & averageGrade < 4))) & averageGrade : INT");
		assertThat(testSuite.getTestCases().get(7).getTestFormula()).isEqualTo("averageGrade : 0..5 & averageGrade >= 4 & averageGrade : INT");
		assertThat(testSuite.getTestCases().get(8).getTestFormula()).isEqualTo("averageGrade : 0..5 & not(averageGrade >= 4) & averageGrade : INT");

		List<Event> preambleTC1 = new ArrayList<Event>(); 
		preambleTC1.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(0).getPreamble()).isEqualTo(preambleTC1);

		List<Event> preambleTC2 = new ArrayList<Event>(); 
		preambleTC2.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(1).getPreamble()).isEqualTo(preambleTC2);
		
		List<Event> preambleTC3 = new ArrayList<Event>(); 
		preambleTC3.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(2).getPreamble()).isEqualTo(preambleTC3);
		
		List<Event> preambleTC4 = new ArrayList<Event>(); 
		preambleTC4.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(3).getPreamble()).isEqualTo(preambleTC4);
		
		List<Event> preambleTC5 = new ArrayList<Event>(); 
		preambleTC5.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(4).getPreamble()).isEqualTo(preambleTC5);
		
		List<Event> preambleTC6 = new ArrayList<Event>();
		preambleTC6.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(5).getPreamble()).isEqualTo(preambleTC6);
		
		List<Event> preambleTC7 = new ArrayList<Event>(); 
		preambleTC7.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(6).getPreamble()).isEqualTo(preambleTC7);
		
		List<Event> preambleTC8 = new ArrayList<Event>(); 
		preambleTC8.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(7).getPreamble()).isEqualTo(preambleTC8);
		
		List<Event> preambleTC9 = new ArrayList<Event>(); 
		preambleTC9.add(new Event("initialisation", new HashMap<String, String>()));
		assertThat(testSuite.getTestCases().get(8).getPreamble()).isEqualTo(preambleTC9);

		assertThat(testSuite.getUnsolvableFormulas()).contains("((averageGrade : 0..5 & 1 = 1) <=> (not(averageGrade : 0..5 & 1 = 2))) & not(averageGrade : INT)");
	}



	@Test
	public void shouldGenerateTestsForMachinesWithAbstractVariables() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/TicTacToe.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriteria.EACH_CHOICE);

		BETATestSuite testSuite = new BETATestSuite(coverageCriterion);

		// Setting up expected values
		
		Map<String, String> expectedInputParamsTC1 = new HashMap<String,String>();
		expectedInputParamsTC1.put("pp", "0");
		
		Map<String,String> expectedStateVariablesTC1 = new HashMap<String,String>();
		expectedStateVariablesTC1.put("turn", "red");
		expectedStateVariablesTC1.put("bposn", "{3,4,6}");
		expectedStateVariablesTC1.put("rposn", "{0,1,2}");
		
		Map<String, String> expectedInputParamsTC2 = new HashMap<String,String>();
		expectedInputParamsTC2.put("pp", "-1");
		
		Map<String,String> expectedStateVariablesTC2 = new HashMap<String,String>();
		expectedStateVariablesTC2.put("turn", "blue");
		expectedStateVariablesTC2.put("bposn", "{}");
		expectedStateVariablesTC2.put("rposn", "{}");
		
		// Making Assertions
		
		assertThat(testSuite.getTestCases()).hasSize(2);
		
		assertThat(testSuite.getTestCases().get(0).getTestFormulaWithoutInvariant()).isEqualTo("not(ThreeInRow(rposn) = FALSE) & not(pp /: bposn \\/ rposn) & not(turn = blue) & pp : 0..8");
		assertThat(testSuite.getTestCases().get(0).getInputParamValues()).isEqualTo(expectedInputParamsTC1);
		assertThat(testSuite.getTestCases().get(0).getStateValues()).isEqualTo(expectedStateVariablesTC1);
		
		assertThat(testSuite.getTestCases().get(1).getTestFormulaWithoutInvariant()).isEqualTo("ThreeInRow(rposn) = FALSE & pp /: bposn \\/ rposn & pp : MININT..((0 - 1)) & turn = blue");
		assertThat(testSuite.getTestCases().get(1).getInputParamValues()).isEqualTo(expectedInputParamsTC2);
		assertThat(testSuite.getTestCases().get(1).getStateValues()).isEqualTo(expectedStateVariablesTC2);
	}
}
