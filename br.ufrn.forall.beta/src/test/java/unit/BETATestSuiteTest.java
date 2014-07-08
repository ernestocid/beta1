package unit;

import static org.junit.Assert.*;
import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.io.File;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.BETATestSuite;

public class BETATestSuiteTest {

	@Test
	public void shouldGenerateTestCasesForSingleCharacteristics() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/MaxIntMinIntTest.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.BOUNDARY_VALUES, CombinatorialCriterias.ALL_COMBINATIONS);
	
		assertFalse(testSuite.getTestCases().isEmpty());
	}
	
	
	
	@Test
	public void shouldIdentifyPositiveAndNegativeTestCases() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/counter.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.BOUNDARY_VALUES, CombinatorialCriterias.ALL_COMBINATIONS);
		
		assertTrue(testSuite.getTestCases().get(0).isNegative());
		assertFalse(testSuite.getTestCases().get(1).isNegative());
	}

}
