package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import criteria.ActiveClauseCoverage;
import de.prob.Main;
import de.prob.scripting.Api;
import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;

public class ActiveClauseCoverageTest {

	
	private Api probApi = Main.getInjector().getInstance(Api.class);
	
	
	@Test
	public void shouldGetTestFormulas() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest, probApi);
		
		// Setting up expected result
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade >= 4");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 4)");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade >= 2 & 0 < 4");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 2) & 0 < 4");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade < 4 & 2 >= 2");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade < 4) & 2 >= 2");

		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestFormulasForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest, probApi);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("xx : ID & yy : ID & yy = aa");
		expectedTestFormulas.add("xx : ID & yy : ID & not(yy = aa)");
		
		expectedTestFormulas.add("xx : ID & yy : ID & yy = bb");
		expectedTestFormulas.add("xx : ID & yy : ID & not(yy = bb)"); 
		
		// Assertions
		
		assertEquals(expectedTestFormulas, acc.getTestFormulas());
	}

	
	
	@Test
	public void shouldGetFormulasForMajorClause() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest, probApi);
		
		// Setting up inputs
		
		MyPredicate majorClause = mock(MyPredicate.class);
		when(majorClause.toString()).thenReturn("averageGrade >= 2");
		
		MyPredicate mockedPredicate = mock(MyPredicate.class);
		when(mockedPredicate.toString()).thenReturn("averageGrade >= 2 & averageGrade < 4");
		
		// Setting up expected outputs
		
		String expectedFormula = "((averageGrade : 0..5 & averageGrade : INT & 1=1 & averageGrade < 4) or (averageGrade : 0..5 & averageGrade : INT & 1=2 & averageGrade < 4)) & "
								+ "not((averageGrade : 0..5 & averageGrade : INT & 1=1 & averageGrade < 4) & (averageGrade : 0..5 & averageGrade : INT & 1=2 & averageGrade < 4))";
		
		assertEquals(expectedFormula, acc.createFormulaToFindValuesForMinorClauses(majorClause, mockedPredicate));
	}
	
	
	
//	@Test
//	public void shouldGenerateTestFormulasForSelectStatement() {
//		Machine machine = new Machine(new File("src/test/resources/machines/Priorityqueue.mch"));
//		Operation operationUnderTest = machine.getOperation(0);
//		
//		ActiveClauseCoverage acc = new ActiveClauseCoverage(operationUnderTest, probApi);
//		
//		// Setting up expected results
//		
//		Set<String> expectedTestFormulas = new HashSet<String>();
//		
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT");
////		
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & queue = []");
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(queue = [])");
////		
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & nn <= min(ran(queue)) & queue /= []");
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & nn <= min(ran(queue)) & not(queue /= [])");
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(nn <= min(ran(queue))) & queue /= []");
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(nn <= min(ran(queue))) & not(queue /= [])");
////		
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & nn >= max(ran(queue)) & queue /= []");
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & nn >= max(ran(queue)) & not(queue /= [])");
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(nn >= max(ran(queue))) & queue /= []");
////		expectedTestFormulas.add("queue : seq(NAT) & !(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & nn : NAT & not(nn >= max(ran(queue))) & not(queue /= [])");
//		
//		// Assertions
//		
//		assertEquals(expectedTestFormulas, acc.getTestFormulas());
//	}
	
}
