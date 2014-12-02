package machinebuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import parser.Operation;
import tools.FileTools;

public class CBCMachineBuilder {

	private Operation operationUnderTest;
	private List<String> testCasePredicates;
	private File builtMachine;



	public CBCMachineBuilder(Operation operationUnderTest, List<String> testCasePredicates) {
		this.operationUnderTest = operationUnderTest;
		this.testCasePredicates = testCasePredicates;
		this.builtMachine = buildCBCTestMachine();
	}



	private File buildCBCTestMachine() {
		StringBuffer cbcTestMachine = new StringBuffer("");

		cbcTestMachine.append(createMachineHeader());
		cbcTestMachine.append("\n\n");

		cbcTestMachine.append(createMachineIncludes());
		cbcTestMachine.append("\n\n");

		cbcTestMachine.append(createPromotedOperations());
		cbcTestMachine.append("\n\n");

		cbcTestMachine.append("OPERATIONS");
		cbcTestMachine.append("\n");

		for (int testIndex = 0; testIndex < testCasePredicates.size(); testIndex++) {
			createTestOperation(cbcTestMachine, testIndex);
		}

		cbcTestMachine.append("END");
		
		File mchFile = FileTools.createFileWithContent("src/test/resources/machines/others/ClassroomCBCTest.mch", cbcTestMachine.toString());
		
		return mchFile;
	}



	private String createPromotedOperations() {
		return "PROMOTES " + operationsFromOriginalMachineWithoutOperationUnderTest();
	}



	private String createMachineIncludes() {
		return "INCLUDES " + getOperationUnderTest().getMachine().getName();
	}



	private String createMachineHeader() {
		return "MACHINE " + getOperationUnderTest().getMachine().getName() + "CBCTest";
	}



	private void createTestOperation(StringBuffer cbcTestMachine, int testIndex) {
		cbcTestMachine.append(getOperationUnderTest().getName() + "_test" + String.valueOf(testIndex + 1));
		cbcTestMachine.append(operationUnderTestParameters() + " =");
		cbcTestMachine.append("\n");
		cbcTestMachine.append("PRE");
		cbcTestMachine.append("\n");
		cbcTestMachine.append(this.testCasePredicates.get(testIndex));
		cbcTestMachine.append("\n");
		cbcTestMachine.append("THEN skip");
		cbcTestMachine.append("\n");
		if (testIndex == testCasePredicates.size() - 1) {
			cbcTestMachine.append("END");
		} else {
			cbcTestMachine.append("END;");
		}
		cbcTestMachine.append("\n\n");
	}



	private String operationUnderTestParameters() {
		List<String> parameters = getOperationUnderTest().getParameters();

		if (parameters.isEmpty()) {
			return "";
		}

		StringBuffer listOfParameters = new StringBuffer("(");

		for (int i = 0; i < parameters.size(); i++) {
			if (i < parameters.size() - 1) {
				listOfParameters.append(parameters.get(i) + ", ");
			} else {
				listOfParameters.append(parameters.get(i));
			}
		}

		listOfParameters.append(")");

		return listOfParameters.toString();
	}



	private String operationsFromOriginalMachineWithoutOperationUnderTest() {
		StringBuffer listOfOperations = new StringBuffer("");
		List<String> operationsWithoutOperationUnderTest = new ArrayList<String>();

		for (Operation op : getOperationUnderTest().getMachine().getOperations()) {
			if (!op.getName().equals(getOperationUnderTest().getName())) {
				operationsWithoutOperationUnderTest.add(op.getName());
			}
		}

		Collections.sort(operationsWithoutOperationUnderTest);

		for (int i = 0; i < operationsWithoutOperationUnderTest.size(); i++) {
			if (i < operationsWithoutOperationUnderTest.size() - 1) {
				listOfOperations.append(operationsWithoutOperationUnderTest.get(i) + ", ");
			} else {
				listOfOperations.append(operationsWithoutOperationUnderTest.get(i));
			}
		}

		return listOfOperations.toString();
	}



	public File getBuiltMachine() {
		return this.builtMachine;
	}



	public Operation getOperationUnderTest() {
		return operationUnderTest;
	}

}
