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
	public void shouldGetTestsForPredicateCoverage() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);

		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		// Setting up expected results

		Set<String> expectedTestFormulas = new HashSet<String>();

		expectedTestFormulas.add("averageGrade : 0..5 & result : RESULT");
		expectedTestFormulas.add("not(averageGrade : 0..5 & result : RESULT)");
		expectedTestFormulas.add("averageGrade : 0..5 & result : RESULT & averageGrade >= 2 & averageGrade < 4");
		expectedTestFormulas.add("averageGrade : 0..5 & result : RESULT & not(averageGrade >= 2 & averageGrade < 4)");
		expectedTestFormulas.add("averageGrade : 0..5 & result : RESULT & averageGrade >= 4");
		expectedTestFormulas.add("averageGrade : 0..5 & result : RESULT & not(averageGrade >= 4)");

		// Assertions

		assertEquals(expectedTestFormulas, pc.getTestFormulas());
	}

}
