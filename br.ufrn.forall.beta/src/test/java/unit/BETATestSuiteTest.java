package unit;

import static org.junit.Assert.*;
import general.CombinatorialCriterias;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.BETATestCase;
import testgeneration.BETATestSuite;
import testgeneration.coveragecriteria.ActiveClauseCoverage;
import testgeneration.coveragecriteria.BoundaryValueAnalysis;
import testgeneration.coveragecriteria.ClauseCoverage;
import testgeneration.coveragecriteria.CombinatorialCoverage;
import testgeneration.coveragecriteria.CoverageCriterion;
import testgeneration.coveragecriteria.EquivalenceClasses;
import testgeneration.coveragecriteria.PredicateCoverage;

public class BETATestSuiteTest {

	@Test
	public void shouldGenerateTestCasesForSingleCharacteristics() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/MaxIntMinIntTest.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		CoverageCriterion coverageCriterion = new BoundaryValueAnalysis(operationUnderTest, CombinatorialCriterias.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, coverageCriterion);

		assertFalse(testSuite.getTestCases().isEmpty());
	}



	@Test
	public void shouldIdentifyPositiveAndNegativeTestCases() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		CoverageCriterion coverageCriterion = new BoundaryValueAnalysis(operationUnderTest, CombinatorialCriterias.ALL_COMBINATIONS);

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, coverageCriterion);

		assertTrue(testSuite.getTestCases().get(0).isNegative());
		assertFalse(testSuite.getTestCases().get(1).isNegative());
	}



	@Test
	public void shouldGetFeasibleTestCaseFormulasWithoutInvariant() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Classroom.mch"));
		Operation operationUnderTest = machine.getOperation(3);
		CoverageCriterion coverageCriterion = new EquivalenceClasses(operationUnderTest, CombinatorialCriterias.EACH_CHOICE);

		List<String> expectedTestCaseFormulas = new ArrayList<String>();
		expectedTestCaseFormulas
				.add("grades(student) > 2 & grades(student) > 3 & has_taken_lab_classes(student) = TRUE & student : dom(grades) & student : dom(has_taken_lab_classes) & student : students");

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, coverageCriterion);

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

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, coverageCriterion);

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

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, coverageCriterion);

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
		CoverageCriterion coverageCriterion = new CombinatorialCoverage(operationUnderTest);

		Set<String> expectedTestCaseFormulas = new HashSet<String>();

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade : INT");
		expectedTestCaseFormulas.add("not(averageGrade : 0..5) & averageGrade : INT");

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 4) & averageGrade : INT");

		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 2 & averageGrade < 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & not(averageGrade >= 2) & averageGrade < 4 & averageGrade : INT");
		expectedTestCaseFormulas.add("averageGrade : 0..5 & averageGrade >= 2 & not(averageGrade < 4) & averageGrade : INT");

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, coverageCriterion);

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

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, coverageCriterion);

		Set<String> actualTestCaseFormulas = new HashSet<String>();

		for (BETATestCase testCase : testSuite.getTestCases()) {
			actualTestCaseFormulas.add(testCase.getTestFormula());
		}

		// Asserting feasible test cases are correct
		assertEquals(expectedTestCaseFormulas, actualTestCaseFormulas);

		// Asserting infeasible test cases are correct
		assertEquals("((averageGrade : 0..5 & 1 = 1) <=> (not(averageGrade : 0..5 & 1 = 2))) & not(averageGrade : INT)", testSuite.getUnsolvableFormulas().get(0));
	}
}
