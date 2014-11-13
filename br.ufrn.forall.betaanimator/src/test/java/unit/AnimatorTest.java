package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import animator.Animator;

public class AnimatorTest {

	@Test
	public void shouldAnimateMachineAndRetrieveInputData() {
		Machine machine = getMachineInstance("src/test/resources/machines/Paperround.mch");
		Animator animator = new Animator(machine);

		List<Map<String, String>> expected = new ArrayList<Map<String, String>>();

		Map<String, String> comb = new HashMap<String, String>();
		comb.put("hh", "1");
		comb.put("papers", "{}");

		expected.add(comb);

		assertEquals(expected, animator.animateOperation(1));
	}
	
	
	
	@Test
	public void shouldReturnEmptyListForOperationThatCantAnimate() {
		Machine machine = getMachineInstance("src/test/resources/machines/Paperround.mch");
		Animator animator = new Animator(machine);

		List<Map<String, String>> expected = new ArrayList<Map<String, String>>();

		assertEquals(expected, animator.animateOperation(2));
	}
	
	
	
	@Test
	public void shouldGetListOfOperationsWithUnsolvablePreconditionPredicates() {
		Machine machine = getMachineInstance("src/test/resources/machines/UnsolvablePredicates.mch");
		Animator animator = new Animator(machine);
		
		List<Operation> unsolvableOperations = animator.getOperationsWithInfeasiblePreconditions();
		
		assertEquals("op0", unsolvableOperations.get(0).getName());
		assertEquals("op2", unsolvableOperations.get(1).getName());
	}
	
	// TODO: Find another place for this test.
//	@Test
//	public void shouldAnimateListOfFormulas() throws AnimationErrorException {
//		Machine machine = getMachineInstance("src/test/resources/machines/Paperround.mch");
//
//		Operation operationUnderTest = machine.getOperation(0);
//
//		Set<String> formulas = new HashSet<String>();
//		formulas.add("hh : 164..168 & not(card(papers) < 60) & not(card(papers) <= 60) & not(magazines <: papers) & papers <: 164..168");
//		formulas.add("card(papers) < 60 & card(papers) <= 60 & hh : -4..0 & magazines <: papers & papers <: -4..0");
//		formulas.add("hh : 1..163 & not(card(papers) < 60) & not(card(papers) <= 60) & not(magazines <: papers) & papers <: 1..163");
//
//		TestMachineBuilder testMachineBuilder = new TestMachineBuilder(
//				operationUnderTest, formulas);
//		String testMachineContent = testMachineBuilder.generateTestMachine();
//
//		String path = machine.getFile().getParentFile() + "/"
//				+ ConventionTools.getTestMachineName(operationUnderTest)
//				+ ".mch";
//		File fileForAnimation = FileTools.createFileWithContent(path,
//				testMachineContent);
//
//		List<Map<String, String>> expected = new ArrayList<Map<String, String>>();
//
//		Map<String, String> comb = new HashMap<String, String>();
//		comb.put("magazines", "{}");
//		comb.put("hh", "-4");
//		comb.put("papers", "{}");
//
//		Map<String, String> comb2 = new HashMap<String, String>();
//		comb2.put("magazines", "{0}");
//		comb2.put("hh", "1");
//		comb2.put(
//				"papers",
//				"{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61}");
//
//		expected.add(comb);
//		expected.add(comb2);
//
//		Machine testMachine = getMachineInstance(fileForAnimation
//				.getAbsolutePath());
//
//		Animator animator = new Animator(testMachine);
//		List<Animation> animations = animator.animateAllOperations();
//
//		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
//
//		for (Animation animation : animations) {
//			result.add(animation.getValues().get(0));
//		}
//
//		assertEquals(expected, result);
//	}

	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}

}
