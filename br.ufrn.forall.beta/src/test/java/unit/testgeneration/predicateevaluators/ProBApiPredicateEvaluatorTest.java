package unit.testgeneration.predicateevaluators;

import static com.google.common.truth.Truth.*;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Machine;
import parser.Operation;
import testgeneration.Block;
import testgeneration.ECBlockBuilder;
import testgeneration.Partitioner;
import testgeneration.predicateevaluators.IPredicateEvaluator;
import testgeneration.predicateevaluators.ProBApiPredicateEvaluator;
import criteria.Pairwise;

public class ProBApiPredicateEvaluatorTest {

	@Before
	public void setUp() {
		Configurations.setUseKodkod(false);
		Configurations.setRandomiseEnumerationOrder(false);
	}


// 	TODO: ProB API integration not working correctly. Need to talk with them and ask for a fix.
//	@Test
//	public void shouldEvaluatePredicateUsingProBApi1() {
//		Machine machine = new Machine(new File("src/test/resources/machines/others/Classroom.mch"));
//		Operation operationUnderTest = machine.getOperation(0); // add_student(student)
//
//		List<List<Block>> blocks = new ECBlockBuilder(new Partitioner(operationUnderTest)).getBlocksAsListsOfBlocks();
//		Set<List<Block>> combinations = new Pairwise<Block>(blocks).getCombinations();
//
//		IPredicateEvaluator pe = new ProBApiPredicateEvaluator(operationUnderTest, combinations);
//
//		String expectedFormula = "students <: STUDENT & grades : (students +-> 0..5) & student : STUDENT & finished : BOOL & has_taken_lab_classes : (students +-> BOOL)";
//
//		Map<String, String> expectedSolutions = new HashMap<String, String>();
//
//		expectedSolutions.put("students", "{}");
//		expectedSolutions.put("student", "STUDENT1");
//		expectedSolutions.put("has_taken_lab_classes", "{}");
//		expectedSolutions.put("finished", "TRUE");
//		expectedSolutions.put("grades", "{}");
//
//		assertThat(pe.getSolutions().get(0).getFormula()).isEqualTo(expectedFormula);
//		assertThat(pe.getSolutions().get(0).getValues().get(0)).isEqualTo(expectedSolutions);
//		assertThat(pe.getInfeasiblePredicates()).isEmpty();
//	}



	@Test
	public void shouldEvaluateUsingFormula() {
		Machine machine = new Machine(new File("src/test/resources/machines/schneider/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substitute

		ProBApiPredicateEvaluator ev = new ProBApiPredicateEvaluator(operationUnderTest, new HashSet<List<Block>>());
		String formula = "#team, pp, rr.(team <: PLAYER & card(team) = 11 & pp : PLAYER & pp : team & rr /: team & rr : PLAYER)";

		Map<String, String> expectedSolutions = new HashMap<String, String>();
		expectedSolutions.put("pp", "PLAYER1");
		expectedSolutions.put("rr", "PLAYER2");
		expectedSolutions.put("team", "{PLAYER1,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11,PLAYER12}");

		assertThat(ev.evaluateFormula(formula)).isEqualTo(expectedSolutions);
	}

}
