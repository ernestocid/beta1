package unit;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.coveragecriteria.ClauseCoverage;

public class ClauseCoverageTest extends TestingUtils {

	
	@Test
	public void shouldGenerateFormulasForOperationWithoutPredicatesButWithInvariant() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/swap.mch"));
		Operation operationUnderTest = machine.getOperation(0); // step 
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected result
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("v1 : INT & v2 : INT");
		
		// Assertions
		
		assertThat(expectedTestFormulas).isEqualTo(cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForClausesCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT))"); // 5
		expectedTestFormulas.add("((not(averageGrade : 0..5) & averageGrade : INT))"); // 6
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade < 4))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade < 4))");
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 4))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade >= 4))");
		
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & (averageGrade >= 2))");
		expectedTestFormulas.add("((averageGrade : 0..5 & averageGrade : INT) & not(averageGrade >= 2))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGetTestFormulasForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((xx : ID) & (yy : ID))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (yy = aa))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & not(yy = aa))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & (yy = bb))");
		expectedTestFormulas.add("((xx : ID) & (yy : ID) & not(yy = bb))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForSelectStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Priorityqueue.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();
		
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & not(queue /= []))"); 
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & (queue = []))");
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & not(queue = []))"); 
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & (nn <= min(ran(queue))))"); 
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & not(nn <= min(ran(queue))))"); 
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & not(nn >= max(ran(queue))))");
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & (queue /= []))");
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT) & (nn >= max(ran(queue))))"); 
		expectedTestFormulas.add("((!(xx).((xx : 1..((size(queue) - 1))) => (queue(xx) <= queue((xx + 1)))) & queue : seq(NAT)) & (nn : NAT))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	

	@Test
	public void shouldGenerateTestFormulasForAnyStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/AnyStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("((xx : NAT) & (yy : NAT))");
		expectedTestFormulas.add("((xx : NAT) & (yy : NAT) & (yy : 1..20))");
		expectedTestFormulas.add("((xx : NAT) & (yy : NAT) & not(yy : 1..20))");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
		
	
	@Test
	public void shouldGenerateTestFormulasForAssertStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/AssertStmt.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedFormulas = new HashSet<String>();
		
		expectedFormulas.add("((xx <: ID) & (yy : ID))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (xx /= {}))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(xx /= {}))");
		
		expectedFormulas.add("((xx <: ID) & (yy : ID) & (yy : xx))");
		expectedFormulas.add("((xx <: ID) & (yy : ID) & not(yy : xx))");
		
		// Assertions
		
		assertEquals(expectedFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForAnyStatement2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Sort.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("((!(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1)))))) & descending_sort : BOOL & vector : (0..9 --> 0..10)) & (descending_sort = FALSE) & (sorted_vector : (0..9 --> 0..10)))"); 
		expectedTestFormulas.add("((!(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1)))))) & descending_sort : BOOL & vector : (0..9 --> 0..10)) & (descending_sort = FALSE) & (sorted_vector : (0..9 --> 0..10)) & not(!(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1))))))"); 
		expectedTestFormulas.add("((!(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1)))))) & descending_sort : BOOL & vector : (0..9 --> 0..10)) & (descending_sort = FALSE) & (sorted_vector : (0..9 --> 0..10)) & not(ran(sorted_vector) = ran(vector)))");
		expectedTestFormulas.add("((!(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1)))))) & descending_sort : BOOL & vector : (0..9 --> 0..10)) & (descending_sort = FALSE) & (sorted_vector : (0..9 --> 0..10)) & (ran(sorted_vector) = ran(vector)))");
		expectedTestFormulas.add("((!(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1)))))) & descending_sort : BOOL & vector : (0..9 --> 0..10)) & (descending_sort = FALSE))");
		expectedTestFormulas.add("((!(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1)))))) & descending_sort : BOOL & vector : (0..9 --> 0..10)) & (descending_sort = FALSE) & (sorted_vector : (0..9 --> 0..10)) & (!(ii).((ii : 0..8) => (sorted_vector(ii) > sorted_vector((ii + 1))))))"); 
		expectedTestFormulas.add("((!(ii,jj).((ii : 0..9 & jj : 0..9) => (((ii /= jj) => (vector(ii) /= vector(jj))))) & ((descending_sort = TRUE) => (!(ii).((ii : 0..8) => (vector(ii) > vector((ii + 1)))))) & descending_sort : BOOL & vector : (0..9 --> 0..10)) & (not(descending_sort = FALSE)))");

		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
	
	
	
	@Test
	public void shouldGenerateTestFormulasForMachineWithImportsAndDefinitions() {
		Machine machine = new Machine(new File("src/test/resources/machines/SpecLuaReduced/TypeCheckOperations.mch"));
		Operation operationUnderTest = machine.getOperation(4); // lua_type
		
		ClauseCoverage cc = new ClauseCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("((max_stack_top : NAT1 & max_stack_top < pseudo_bottom & stack : (NAT1 --> LUA_VALUES) & stack_top : NAT & stack_top <= max_stack_top) & (index : INT & not(index : -stack_top..-1 \\/ 1..stack_top \\/ pseudo_bottom..MAXINT \\/ ((stack_top + 1))..max_stack_top)))"); 
		expectedTestFormulas.add("((max_stack_top : NAT1 & max_stack_top < pseudo_bottom & stack : (NAT1 --> LUA_VALUES) & stack_top : NAT & stack_top <= max_stack_top) & (index : INT & index : -stack_top..-1 \\/ 1..stack_top \\/ pseudo_bottom..MAXINT \\/ ((stack_top + 1))..max_stack_top))"); 

		// Assertions
		
		assertEquals(expectedTestFormulas, cc.getTestFormulas());
	}
}