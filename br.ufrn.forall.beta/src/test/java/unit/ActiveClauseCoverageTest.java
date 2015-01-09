package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
		
		expectedTestFormulas.add("#averageGrade.((averageGrade : INT) & ((averageGrade : 0..5 & 1=1) <=> not(averageGrade : 0..5 & 1=2)))");
		expectedTestFormulas.add("#averageGrade.(not(averageGrade : INT) & ((averageGrade : 0..5 & 1=1) <=> not(averageGrade : 0..5 & 1=2)))");
		expectedTestFormulas.add("#averageGrade.((averageGrade : 0..5) & ((1=1 & averageGrade : INT) <=> not(1=2 & averageGrade : INT)))");
		expectedTestFormulas.add("#averageGrade.(not(averageGrade : 0..5) & ((1=1 & averageGrade : INT) <=> not(1=2 & averageGrade : INT)))");
		
		expectedTestFormulas.add("#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 4))");
		expectedTestFormulas.add("#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (not(averageGrade >= 4)))");
		
		expectedTestFormulas.add("#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (averageGrade < 4) & ((averageGrade >= 2 & 1=1) <=> not(averageGrade >= 2 & 1=2)))");
		expectedTestFormulas.add("#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade < 4) & ((averageGrade >= 2 & 1=1) <=> not(averageGrade >= 2 & 1=2)))");
		
		expectedTestFormulas.add("#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 2) & ((1=1 & averageGrade < 4) <=> not(1=2 & averageGrade < 4)))");
		expectedTestFormulas.add("#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade >= 2) & ((1=1 & averageGrade < 4) <=> not(1=2 & averageGrade < 4)))");
		
//		#averageGrade.((averageGrade : INT) & ((averageGrade : 0..5 & 1=1) <=> not(averageGrade : 0..5 & 1=2)))		
//		#averageGrade.(not(averageGrade : INT) & ((averageGrade : 0..5 & 1=1) <=> not(averageGrade : 0..5 & 1=2)))
//		#averageGrade.((averageGrade : 0..5) & ((1=1 & averageGrade : INT) <=> not(1=2 & averageGrade : INT)))
//		#averageGrade.(not(averageGrade : 0..5) & ((1=1 & averageGrade : INT) <=> not(1=2 & averageGrade : INT)))		

//		#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade < 4) & ((averageGrade >= 2 & 1=1) <=> not(averageGrade >= 2 & 1=2)))
//		#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (not(averageGrade >= 4)))
//		#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 2) & ((1=1 & averageGrade < 4) <=> not(1=2 & averageGrade < 4)))
//		#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 4))

//		#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade >= 2) & ((1=1 & averageGrade < 4) <=> not(1=2 & averageGrade < 4)))
//		#averageGrade.((averageGrade : 0..5 & averageGrade : INT) & (averageGrade < 4) & ((averageGrade >= 2 & 1=1) <=> not(averageGrade >= 2 & 1=2)))

		
		
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
		
		expectedTestFormulas.add("#xx,yy.((xx : ID) & (yy : ID))");
		expectedTestFormulas.add("#xx,yy.((xx : ID) & (not(yy : ID)))");
		
		expectedTestFormulas.add("#xx,yy.((xx : ID) & (yy : ID) & (yy = aa))");
		expectedTestFormulas.add("#xx,yy.((xx : ID) & (yy : ID) & (not(yy = aa)))");
		
		expectedTestFormulas.add("#xx,yy.((xx : ID) & (yy : ID) & (yy = bb))");
		expectedTestFormulas.add("#xx,yy.((xx : ID) & (yy : ID) & (not(yy = bb)))"); 
		
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
		
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (not(yy : ID)))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID))");
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (xx = {}))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (not(xx = {})))");
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (xx /= {}) & ((1=1 & yy /: xx) <=> not(1=2 & yy /: xx)))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & not(xx /= {}) & ((1=1 & yy /: xx) <=> not(1=2 & yy /: xx)))");
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & not(yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (yy /: xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & not(yy /: xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & not(xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");

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
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (not(yy : ID)))");
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & not(xx /= {}) & ((1=1 & yy : xx) <=> not(1=2 & yy : xx)))");
		
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & (yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		expectedFormulas.add("#xx,yy.((xx <: ID) & (yy : ID) & not(yy : xx) & ((xx /= {} & 1=1) <=> not(xx /= {} & 1=2)))");
		
		// Assertions
		
		assertEquals(expectedFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAnyStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Any.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("#col,b,col1,col2.((col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS) & (x : COLOURS) & ((1=1 & x /: col1) <=> not(1=2 & x /: col1)))");
		expectedTestFormulas.add("#col,b,col1,col2.((col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS) & not(x : COLOURS) & ((1=1 & x /: col1) <=> not(1=2 & x /: col1)))");
		
		expectedTestFormulas.add("#col,b,col1,col2.((col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS) & (x /: col1) & ((x : COLOURS & 1=1) <=> not(x : COLOURS & 1=2)))");
		expectedTestFormulas.add("#col,b,col1,col2.((col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS) & not(x /: col1) & ((x : COLOURS & 1=1) <=> not(x : COLOURS & 1=2)))");
		
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
		
		expectedTestFormulas.add("#xx,yy.((yy : NAT) & ((xx : NAT & 1=1) <=> not(xx : NAT & 1=2)))");
		expectedTestFormulas.add("#xx,yy.(not(yy : NAT) & ((xx : NAT & 1=1) <=> not(xx : NAT & 1=2)))");
		expectedTestFormulas.add("#xx,yy.((xx : NAT) & ((1=1 & yy : NAT) <=> not(1=2 & yy : NAT)))");
		expectedTestFormulas.add("#xx,yy.(not(xx : NAT) & ((1=1 & yy : NAT) <=> not(1=2 & yy : NAT)))");
		
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy))");
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (not(xx /= yy)))");
		
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy) & (xx > yy))");
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy) & (not(xx > yy)))");
		
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy) & (xx = 2))");
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy) & (not(xx = 2)))");
		
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy & xx = 2) & (yy = 1))");
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy & xx = 2) & (not(yy = 1)))");
		
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy) & (yy = 2))");
		expectedTestFormulas.add("#xx,yy.((xx : NAT & yy : NAT) & (xx /= yy) & (not(yy = 2)))");

		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForMachinesWithNestedSubstitutions2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/CaseWithNestedSubstitutions.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT))");
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (not(xx : NAT)))");
		
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1))");
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (not(xx = 1)))");
		
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1) & (aa > bb))");
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1) & (not(aa > bb)))");
		
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2))");
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (not(xx = 2)))");
		
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2) & (bb >= aa))");
		expectedTestFormulas.add("#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2) & (not(bb >= aa)))");

		
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (not(xx : NAT)))
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT))
		
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1) & (aa > bb))
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2))

//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1) & (not(aa > bb)))
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 1))
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (not(xx = 2)))
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (not(xx = 1)))

//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2) & (bb >= aa))
//		#aa,bb,xx.((aa : NAT & bb : NAT) & (xx : NAT) & (xx = 2) & (not(bb >= aa)))
		
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
}
