package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import criteria.ClauseCoverage;

public class ClauseCoverageTest extends TestingUtils {

	@Test
	public void shouldGenerateTestFormulasForClausesCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT"); // 5
		expectedTestFormulas.add("not(averageGrade : 0..5) & averageGrade : INT"); // 6
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade < 4");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade < 4)");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade >= 4");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 4)");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade >= 2");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 2)");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestFormulasForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("xx : ID & yy : ID");
		expectedTestFormulas.add("xx : ID & yy : ID & yy = aa");
		expectedTestFormulas.add("xx : ID & yy : ID & not(yy = aa)");
		expectedTestFormulas.add("xx : ID & yy : ID & yy = bb");
		expectedTestFormulas.add("xx : ID & yy : ID & not(yy = bb)");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForSelectStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/Priorityqueue.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT");

		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & queue = []");
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(queue = [])");
		
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & queue /= []");
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(queue /= [])");
		
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & nn >= max(ran(queue))");
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(nn >= max(ran(queue)))");
		
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & nn <= min(ran(queue))");
		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(nn <= min(ran(queue)))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAnyStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/Any.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & x : COLOURS");
		expectedTestFormulas.add("col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & x /: col1");
		expectedTestFormulas.add("col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS & not(x /: col1)");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAssertStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/AssertStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		expectedFormulas.add("xx <: ID & yy : ID");
		
		expectedFormulas.add("xx <: ID & yy : ID & xx /= {}");
		expectedFormulas.add("xx <: ID & yy : ID & not(xx /= {})");
		
		expectedFormulas.add("xx <: ID & yy : ID & yy : xx");
		expectedFormulas.add("xx <: ID & yy : ID & not(yy : xx)");
		
		// Assertions
		
		assertEquals(expectedFormulas, cc.getTestFormulas());
	}

}
