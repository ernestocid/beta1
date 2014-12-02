package unit;

import static org.junit.Assert.assertEquals;
import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.io.File;

import machinebuilder.CBCMachineBuilder;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import testgeneration.BETATestSuite;
import tools.FileTools;

public class CBCMachineBuilderTest {

	@Test
	public void shouldGenerateCBCTestingMachineForBETATestCases() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/Classroom.mch"));
		Operation operationUnderTest = machine.getOperation(3);

		BETATestSuite testSuite = new BETATestSuite(operationUnderTest, PartitionStrategy.EQUIVALENT_CLASSES, CombinatorialCriterias.EACH_CHOICE);
		CBCMachineBuilder cbcMchBuilder = new CBCMachineBuilder(operationUnderTest, testSuite.getFeasbileTestCaseFormulasWithoutInvariant());
		File mchFile = cbcMchBuilder.getBuiltMachine();

		File expectedMchFile = new File("src/test/resources/machines/others/expected_ClassroomCBCTest.mch");

		assertEquals(FileTools.getFileContent(expectedMchFile), FileTools.getFileContent(mchFile));
	}

}
