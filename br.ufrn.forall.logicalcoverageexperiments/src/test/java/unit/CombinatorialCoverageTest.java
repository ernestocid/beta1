package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import criteria.CombinatorialCoverage;

public class CombinatorialCoverageTest extends TestingUtils{

	
	@Test
	public void shouldGetTestsForCombinatorialCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		CombinatorialCoverage coc = new CombinatorialCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT");
		expectedTestFormulas.add("not(averageGrade : 0..5) & averageGrade : INT");
		expectedTestFormulas.add("averageGrade : 0..5 & not(averageGrade : INT)");
		expectedTestFormulas.add("not(averageGrade : 0..5) & not(averageGrade : INT)");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade < 4 & not(averageGrade >= 2)");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade < 4) & averageGrade >= 2");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade < 4 & averageGrade >= 2");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade < 4) & not(averageGrade >= 2)");
		
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & averageGrade >= 4");
		expectedTestFormulas.add("averageGrade : 0..5 & averageGrade : INT & not(averageGrade >= 4)");
		
		// Assertions
		
		assertEquals(expectedTestFormulas, coc.getTestFormulas());
	}

}
