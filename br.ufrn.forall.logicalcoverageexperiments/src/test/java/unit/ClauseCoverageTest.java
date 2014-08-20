package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import criteria.ClauseCoverage;
import criteria.PredicateCoverage;

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

}
