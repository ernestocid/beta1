package unit;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.coveragecriteria.CombinatorialClauseCoverage;

public class CombinatorialCoverageTest extends TestingUtils{

	
	@Test
	public void shouldGenerateFormulasForOperationWithoutPredicatesButWithInvariant() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/swap.mch"));
		Operation operationUnderTest = machine.getOperation(0); // step 
		
		CombinatorialClauseCoverage coc = new CombinatorialClauseCoverage(operationUnderTest);
		
		// Setting up expected result
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("v1 : INT & v2 : INT");
		
		// Assertions
		
		assertThat(expectedTestFormulas).isEqualTo(coc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestsForCombinatorialCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		CombinatorialClauseCoverage coc = new CombinatorialClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT))");
		expectedTestFormulas.add("((not(averageGrade : 0..5) & averageGrade : INT))");
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade < 4 & not(averageGrade >= 2)))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (not(averageGrade < 4) & averageGrade >= 2))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade < 4 & averageGrade >= 2))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (not(averageGrade < 4) & not(averageGrade >= 2)))");
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 4))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (not(averageGrade >= 4)))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, coc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestFormulasForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		CombinatorialClauseCoverage coc = new CombinatorialClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((xx : ID) & (yy : ID))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (yy = aa))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (not(yy = aa)))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (yy = bb))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (not(yy = bb)))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, coc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForSelectStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Priorityqueue.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		CombinatorialClauseCoverage coc = new CombinatorialClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT))");
		
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (queue = []))");
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (not(queue = [])))");
		
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (nn <= min(ran(queue)) & queue /= []))");
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (nn <= min(ran(queue)) & not(queue /= [])))");
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (not(nn <= min(ran(queue))) & queue /= []))");
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (not(nn <= min(ran(queue))) & not(queue /= [])))");
		
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (nn >= max(ran(queue)) & queue /= []))");
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (nn >= max(ran(queue)) & not(queue /= [])))");
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (not(nn >= max(ran(queue))) & queue /= []))");
		expectedTestFormulas.add("((queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1))))) & (nn : NAT) & (not(nn >= max(ran(queue))) & not(queue /= [])))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, coc.getTestFormulas());
	}
	
	
	
//	@Test
//	public void shouldGenerateTestFormulasForAnyStatement() {
//		Machine machine = new Machine(new File("src/test/resources/machines/others/Any.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
//		
//		CombinatorialClauseCoverage coc = new CombinatorialClauseCoverage(operationUnderTest);
//		
//		// Setting up expected results
//		
//		Set<String> expectedTestFormulas = new HashSet<String>();
//
//		expectedTestFormulas.add("((col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS) & (x /: col1 & x : COLOURS))");
//		expectedTestFormulas.add("((col1 : POW(COLOURS) & col2 : POW(COLOURS) & !(cc).((cc : col1) => (cc /: col2)) & !(cc2).((cc2 : col2) => (cc2 /: col1)) & b : BOOL & col : COLOURS) & (not(x /: col1) & x : COLOURS))");
//		
//		// Assertions
//		
//		assertEquals(expectedTestFormulas, coc.getTestFormulas());
//	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAssertStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/AssertStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		CombinatorialClauseCoverage coc = new CombinatorialClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		expectedFormulas.add("((xx <: ID) & (yy : ID))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (xx /= {} & yy : xx))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (not(xx /= {}) & yy : xx))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (xx /= {} & not(yy : xx)))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (not(xx /= {}) & not(yy : xx)))");
		
		// Assertions
		
		assertEquals(expectedFormulas, coc.getTestFormulas());
	}

}
