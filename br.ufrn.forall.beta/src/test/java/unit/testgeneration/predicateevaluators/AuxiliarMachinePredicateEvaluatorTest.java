package unit.testgeneration.predicateevaluators;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import criteria.Pairwise;
import parser.Machine;
import parser.Operation;
import testgeneration.Block;
import testgeneration.ECBlockBuilder;
import testgeneration.Partitioner;
import testgeneration.predicateevaluators.AuxiliarMachinePredicateEvaluator;
import testgeneration.predicateevaluators.IPredicateEvaluator;

public class AuxiliarMachinePredicateEvaluatorTest {

	@Before
	public void setUp() {
		Configurations.setRandomiseEnumerationOrder(false);
		Configurations.setUseKodkod(true);
	}



	@Test
	public void shouldEvaluatePredicateUsingAuxiliarMachineTrick1() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Classroom.mch"));
		Operation operationUnderTest = machine.getOperation(0); // add_student(student)

		List<List<Block>> blocks = new ECBlockBuilder(new Partitioner(operationUnderTest)).getBlocksAsListsOfBlocks();
		Set<List<Block>> combinations = new Pairwise<Block>(blocks).getCombinations();

		IPredicateEvaluator pe = new AuxiliarMachinePredicateEvaluator(operationUnderTest, combinations);

		String expectedFormula = "students <: STUDENT & grades : (students +-> 0..5) & student : STUDENT & finished : BOOL & has_taken_lab_classes : (students +-> BOOL)";

		Map<String, String> expectedSolutions = new HashMap<String, String>();

		expectedSolutions.put("students", "{}");
		expectedSolutions.put("student", "STUDENT1");
		expectedSolutions.put("has_taken_lab_classes", "{}");
		expectedSolutions.put("finished", "TRUE");
		expectedSolutions.put("grades", "{}");

		assertEquals(expectedFormula, pe.getSolutions().get(0).getFormula());
		assertEquals(expectedSolutions, pe.getSolutions().get(0).getValues().get(0));
		assertTrue(pe.getInfeasiblePredicates().isEmpty());
	}



	@Test
	public void shouldEvaluatePredicateUsingAuxiliarMachineTrick2() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Classroom.mch"));
		Operation operationUnderTest = machine.getOperation(3); // rr <--
																// student_pass_or_fail(student)

		List<List<Block>> blocks = new ECBlockBuilder(new Partitioner(operationUnderTest)).getBlocksAsListsOfBlocks();
		Set<List<Block>> combinations = new Pairwise<Block>(blocks).getCombinations();

		IPredicateEvaluator pe = new AuxiliarMachinePredicateEvaluator(operationUnderTest, combinations);

		String expectedFormula = "students <: STUDENT & student : dom(has_taken_lab_classes) & grades : (students +-> 0..5) & student : dom(grades) & finished : BOOL & not(grades(student) > 3 & has_taken_lab_classes(student) = TRUE) & has_taken_lab_classes : (students +-> BOOL) & not(grades(student) > 2 & has_taken_lab_classes(student) = TRUE) & student : students";

		Map<String, String> expectedSolutions = new HashMap<String, String>();

		expectedSolutions.put("students", "{STUDENT1}");
		expectedSolutions.put("student", "STUDENT1");
		expectedSolutions.put("has_taken_lab_classes", "{(STUDENT1|->FALSE)}");
		expectedSolutions.put("finished", "TRUE");
		expectedSolutions.put("grades", "{(STUDENT1|->0)}");

		assertEquals(expectedFormula, pe.getSolutions().get(0).getFormula());
		assertEquals(expectedSolutions, pe.getSolutions().get(0).getValues().get(0));
		assertEquals(5, pe.getInfeasiblePredicates().size());
	}

}
