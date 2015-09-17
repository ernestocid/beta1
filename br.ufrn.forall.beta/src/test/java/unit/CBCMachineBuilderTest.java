package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;

import machinebuilder.CBCMachineBuilder;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import tools.FileTools;

public class CBCMachineBuilderTest {

	@Test
	public void shouldGenerateCBCTestingMachineForBETATestCases() {
		Machine machine = new Machine(new File("src/test/resources/machines/others/ClassroomWithoutDeferredSets.mch"));
		Operation operationUnderTest = machine.getOperation(3);

		String stateGoal = "students = {st1} & has_taken_lab_classes = {(st1|->TRUE)} & grades = {(st1|->4)}";

		CBCMachineBuilder cbcMchBuilder = new CBCMachineBuilder(operationUnderTest, stateGoal);
		File mchFile = cbcMchBuilder.getBuiltMachine();

		File expectedMchFile = new File("src/test/resources/machines/others/expected_ClassroomWithoutDeferredSetsCBCTest.mch");

		assertEquals(FileTools.getFileContent(expectedMchFile), FileTools.getFileContent(mchFile));
	}

}
