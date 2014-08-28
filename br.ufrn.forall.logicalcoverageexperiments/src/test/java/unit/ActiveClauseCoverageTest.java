package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import criteria.ActiveClauseCoverage;
import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class ActiveClauseCoverageTest {

	
	@Test
	public void shouldGetFormulasForMajorClause() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up inputs
		
		MyPredicate majorClause = mock(MyPredicate.class);
		when(majorClause.toString()).thenReturn("averageGrade >= 2");
		
		MyPredicate mockedPredicate = mock(MyPredicate.class);
		when(mockedPredicate.toString()).thenReturn("averageGrade >= 2 & averageGrade < 4");
		
		// Setting up expected outputs
		
		String expectedFormula = "(((1=1 & averageGrade < 4) or (1=2 & averageGrade < 4)) & "
								+ "not((1=1 & averageGrade < 4) & (1=2 & averageGrade < 4)))";
		
		assertEquals(expectedFormula, acc.createFormulaToFindValuesForMinorClauses(majorClause, mockedPredicate));
	}
	
	
	
	@Test
	public void shouldGetTestFormulas() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected result
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade >= 4");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 4)");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade >= 2 & (((1=1 & averageGrade < 4) or (1=2 & averageGrade < 4)) & not((1=1 & averageGrade < 4) & (1=2 & averageGrade < 4)))");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 2) & (((1=1 & averageGrade < 4) or (1=2 & averageGrade < 4)) & not((1=1 & averageGrade < 4) & (1=2 & averageGrade < 4)))");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade < 4 & (((averageGrade >= 2 & 1=1) or (averageGrade >= 2 & 1=2)) & not((averageGrade >= 2 & 1=1) & (averageGrade >= 2 & 1=2)))");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade < 4) & (((averageGrade >= 2 & 1=1) or (averageGrade >= 2 & 1=2)) & not((averageGrade >= 2 & 1=1) & (averageGrade >= 2 & 1=2)))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestFormulasForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("xx : ID & yy : ID & yy = aa");
		expectedTestFormulas.add("xx : ID & yy : ID & not(yy = aa)");
		
		expectedTestFormulas.add("xx : ID & yy : ID & yy = bb");
		expectedTestFormulas.add("xx : ID & yy : ID & not(yy = bb)"); 
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}

	
	
	// TODO: missing tests for other substitutions
	@Test
	public void shouldGenerateTestFormulasForSelectStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/SelectStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		expectedFormulas.add("xx <: ID & yy : ID & xx = {}");
		expectedFormulas.add("xx <: ID & yy : ID & not(xx = {})");
		
		expectedFormulas.add("xx <: ID & yy : ID & xx /= {} & (((1=1 & yy : xx) or (1=2 & yy : xx)) & not((1=1 & yy : xx) & (1=2 & yy : xx)))");
		expectedFormulas.add("xx <: ID & yy : ID & not(xx /= {}) & (((1=1 & yy : xx) or (1=2 & yy : xx)) & not((1=1 & yy : xx) & (1=2 & yy : xx)))");
		
		expectedFormulas.add("xx <: ID & yy : ID & yy : xx & (((xx /= {} & 1=1) or (xx /= {} & 1=2)) & not((xx /= {} & 1=1) & (xx /= {} & 1=2)))");
		expectedFormulas.add("xx <: ID & yy : ID & not(yy : xx) & (((xx /= {} & 1=1) or (xx /= {} & 1=2)) & not((xx /= {} & 1=1) & (xx /= {} & 1=2)))");
		
		expectedFormulas.add("xx <: ID & yy : ID & xx /= {} & (((1=1 & yy /: xx) or (1=2 & yy /: xx)) & not((1=1 & yy /: xx) & (1=2 & yy /: xx)))");
		expectedFormulas.add("xx <: ID & yy : ID & not(xx /= {}) & (((1=1 & yy /: xx) or (1=2 & yy /: xx)) & not((1=1 & yy /: xx) & (1=2 & yy /: xx)))");
		
		expectedFormulas.add("xx <: ID & yy : ID & yy /: xx & (((xx /= {} & 1=1) or (xx /= {} & 1=2)) & not((xx /= {} & 1=1) & (xx /= {} & 1=2)))");
		expectedFormulas.add("xx <: ID & yy : ID & not(yy /: xx) & (((xx /= {} & 1=1) or (xx /= {} & 1=2)) & not((xx /= {} & 1=1) & (xx /= {} & 1=2)))");
		
		// Assertions
		
		assertEquals(expectedFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAssertStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/AssertStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		expectedFormulas.add("xx <: ID & yy : ID & xx /= {} & (((1=1 & yy : xx) or (1=2 & yy : xx)) & not((1=1 & yy : xx) & (1=2 & yy : xx)))");
		expectedFormulas.add("xx <: ID & yy : ID & not(xx /= {}) & (((1=1 & yy : xx) or (1=2 & yy : xx)) & not((1=1 & yy : xx) & (1=2 & yy : xx)))");
		
		expectedFormulas.add("xx <: ID & yy : ID & yy : xx & (((xx /= {} & 1=1) or (xx /= {} & 1=2)) & not((xx /= {} & 1=1) & (xx /= {} & 1=2)))");
		expectedFormulas.add("xx <: ID & yy : ID & not(yy : xx) & (((xx /= {} & 1=1) or (xx /= {} & 1=2)) & not((xx /= {} & 1=1) & (xx /= {} & 1=2)))");
		
		// Assertions
		
		assertEquals(expectedFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAnyStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/Any.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & x : COLOURS & (((1=1 & x /: col1) or (1=2 & x /: col1)) & not((1=1 & x /: col1) & (1=2 & x /: col1)))");
		expectedTestFormulas.add("col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & not(x : COLOURS) & (((1=1 & x /: col1) or (1=2 & x /: col1)) & not((1=1 & x /: col1) & (1=2 & x /: col1)))");
		
		expectedTestFormulas.add("col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & x /: col1 & (((x : COLOURS & 1=1) or (x : COLOURS & 1=2)) & not((x : COLOURS & 1=1) & (x : COLOURS & 1=2)))");
		expectedTestFormulas.add("col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & not(x /: col1) & (((x : COLOURS & 1=1) or (x : COLOURS & 1=2)) & not((x : COLOURS & 1=1) & (x : COLOURS & 1=2)))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
}
