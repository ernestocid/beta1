package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.google.common.truth.Truth.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import testgeneration.coveragecriteria.ActiveClauseCoverage;

public class ActiveClauseCoverageTest {

	
	@Test
	public void shouldGenerateFormulasForOperationWithoutPredicatesButWithInvariant() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/swap.mch"));
		Operation operationUnderTest = machine.getOperation(0); // step 
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected result
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("v1 : INT & v2 : INT");
		
		// Assertions
		
		assertThat(expectedTestFormulas).isEqualTo(acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetFormulasForMajorClause() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up inputs
		
		MyPredicate majorClause = mock(MyPredicate.class);
		when(majorClause.toString()).thenReturn("averageGrade >= 2");
		
		MyPredicate mockedPredicate = mock(MyPredicate.class);
		when(mockedPredicate.toString()).thenReturn("averageGrade >= 2 & averageGrade < 4");
		
		// Setting up expected outputs
		
		String expectedFormula = "((1=1 & averageGrade < 4) <=> not(1=2 & averageGrade < 4))";
		
		assertEquals(expectedFormula, acc.createFormulaToFindValuesForMinorClauses(majorClause, mockedPredicate));
	}
	
	
	
	@Test
	public void shouldGetTestFormulas() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected result
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((averageGrade : 0..5) & ((1=1 & averageGrade : INT) <=> not(1=2 & averageGrade : INT)))");
		expectedTestFormulas.add("(not(averageGrade : 0..5) & ((1=1 & averageGrade : INT) <=> not(1=2 & averageGrade : INT)))");
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 4))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (not(averageGrade >= 4)))");
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade < 4) & ((averageGrade >= 2 & 1=1) <=> not(averageGrade >= 2 & 1=2)))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade < 4) & ((averageGrade >= 2 & 1=1) <=> not(averageGrade >= 2 & 1=2)))");
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 2) & ((1=1 & averageGrade < 4) <=> not(1=2 & averageGrade < 4)))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade >= 2) & ((1=1 & averageGrade < 4) <=> not(1=2 & averageGrade < 4)))");

		// Assertions

		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestFormulasForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((xx : ID) & (yy : ID))");
		expectedTestFormulas.add("((xx : ID) & (not(yy : ID)))");
		
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (yy = aa))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (not(yy = aa)))");
		
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (yy = bb))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (not(yy = bb)))"); 
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}

	
	
	@Test
	public void shouldGenerateTestFormulasForSelectStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/SelectStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		
		expectedFormulas.add("((xx <: ID) & (not(yy : ID)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (xx = {}))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (not(xx = {})))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (xx /= {}) & ((1=1 & yy /: xx) <=> not(1=2 & yy /: xx)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(xx /= {}) & ((1=1 & yy /: xx) <=> not(1=2 & yy /: xx)))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (yy /: xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(yy /: xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");

		// Assertions
		
		assertEquals(expectedFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAssertStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/AssertStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		expectedFormulas.add("((xx <: ID) & (yy : ID))");
		expectedFormulas.add("((xx <: ID) & (not(yy : ID)))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		
		// Assertions
		
		assertEquals(expectedFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAnyStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/AnyStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("((xx : NAT) & (yy : 1..20) & ((yy : NAT & 1=1) <=> not(yy : NAT & 1=2)))");
		expectedTestFormulas.add("((xx : NAT) & not(yy : 1..20) & ((yy : NAT & 1=1) <=> not(yy : NAT & 1=2)))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAnyStatement2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Sort.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (not(descending_sort = FALSE)))");
		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (descending_sort = FALSE) & (!(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1))))) & ((sorted_vector : (0..9 --> 0..10) & 1=1 & ran(sorted_vector) = ran(vector)) <=> not(sorted_vector : (0..9 --> 0..10) & 1=2 & ran(sorted_vector) = ran(vector))))");
		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (descending_sort = FALSE) & (sorted_vector : (0..9 --> 0..10)) & ((1=1 & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & ran(sorted_vector) = ran(vector)) <=> not(1=2 & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & ran(sorted_vector) = ran(vector))))");
		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (descending_sort = FALSE) & not(ran(sorted_vector) = ran(vector)) & ((sorted_vector : (0..9 --> 0..10) & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & 1=1) <=> not(sorted_vector : (0..9 --> 0..10) & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & 1=2)))");
		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (descending_sort = FALSE) & not(sorted_vector : (0..9 --> 0..10)) & ((1=1 & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & ran(sorted_vector) = ran(vector)) <=> not(1=2 & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & ran(sorted_vector) = ran(vector))))");
		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (descending_sort = FALSE) & (ran(sorted_vector) = ran(vector)) & ((sorted_vector : (0..9 --> 0..10) & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & 1=1) <=> not(sorted_vector : (0..9 --> 0..10) & !(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1)))) & 1=2)))");
		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (descending_sort = FALSE))");
		expectedTestFormulas.add("((vector : (0..9 --> 0..10) & !(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & descending_sort : BOOL & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1))))))) & (descending_sort = FALSE) & not(!(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1))))) & ((sorted_vector : (0..9 --> 0..10) & 1=1 & ran(sorted_vector) = ran(vector)) <=> not(sorted_vector : (0..9 --> 0..10) & 1=2 & ran(sorted_vector) = ran(vector))))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForMachinesWithNestedSubstitutions() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/NestedSubstitutions.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy))");
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (not(xx /= yy)))");
		
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy) & (xx > yy))");
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy) & (not(xx > yy)))");
		
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy) & (xx = 2))");
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy) & (not(xx = 2)))");
		
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy & xx = 2) & (yy = 1))");
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy & xx = 2) & (not(yy = 1)))");
		
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy) & (yy = 2))");
		expectedTestFormulas.add("((xx : NAT & yy : NAT) & (xx /= yy) & (not(yy = 2)))");

		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForMachinesWithNestedSubstitutions2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/CaseWithNestedSubstitutions.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT))");
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (not(xx : NAT)))");
		
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1))");
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (not(xx = 1)))");
		
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1) & (aa > bb))");
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1) & (not(aa > bb)))");
		
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2))");
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (not(xx = 2)))");
		
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2) & (bb >= aa))");
		expectedTestFormulas.add("((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2) & (not(bb >= aa)))");

		// Assertions

		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
}
