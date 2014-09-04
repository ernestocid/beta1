package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import criteria.PredicateCoverage;

public class PredicateCoverageTest extends TestingUtils {

	
	@Test
	public void shouldGetTestsFormulasIfElsifElseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);

		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		// Setting up expected results

		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("#averageGrade.(averageGrade : 0..5 & averageGrade : INT)");
		expectedTestFormulas.add("#averageGrade.(not(averageGrade : 0..5 & averageGrade : INT))");
		expectedTestFormulas.add("#averageGrade.(averageGrade : 0..5 & averageGrade : INT & averageGrade >= 2 & averageGrade < 4)");
		expectedTestFormulas.add("#averageGrade.(averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 2 & averageGrade < 4))");
		expectedTestFormulas.add("#averageGrade.(averageGrade : 0..5 & averageGrade : INT & averageGrade >= 4)");
		expectedTestFormulas.add("#averageGrade.(averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 4))");

		// Assertions

		assertEquals(expectedTestFormulas, pc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestFormulasForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("#xx,yy.(xx : ID & yy : ID)");
		expectedTestFormulas.add("#xx,yy.(xx : ID & not(yy : ID))");
		expectedTestFormulas.add("#xx,yy.(xx : ID & yy : ID & yy = aa)");
		expectedTestFormulas.add("#xx,yy.(xx : ID & yy : ID & not(yy = aa))");
		expectedTestFormulas.add("#xx,yy.(xx : ID & yy : ID & yy = bb)");
		expectedTestFormulas.add("#xx,yy.(xx : ID & yy : ID & not(yy = bb))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, pc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForSelectStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/Priorityqueue.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT)");
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & not(nn : NAT))");
		
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & queue = [])");
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(queue = []))");
		
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & queue /= [] & nn <= min(ran(queue)))");
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(queue /= [] & nn <= min(ran(queue))))");
		
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & queue /= [] & nn >= max(ran(queue)))");
		expectedTestFormulas.add("#queue,nn.(queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(queue /= [] & nn >= max(ran(queue))))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, pc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAnyStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/Any.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("#col,b,col1,col2.(col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & x : COLOURS & x /: col1)");
		expectedTestFormulas.add("#col,b,col1,col2.(col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & not(x : COLOURS & x /: col1))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, pc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAssertStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/AssertStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		expectedFormulas.add("#xx,yy.(xx <: ID & yy : ID)");
		expectedFormulas.add("#xx,yy.(xx <: ID & not(yy : ID))");
		
		expectedFormulas.add("#xx,yy.(xx <: ID & yy : ID & xx /= {} & yy : xx)");
		expectedFormulas.add("#xx,yy.(xx <: ID & yy : ID & not(xx /= {} & yy : xx))");
		
		// Assertions
		
		assertEquals(expectedFormulas, pc.getTestFormulas());
	}

}
