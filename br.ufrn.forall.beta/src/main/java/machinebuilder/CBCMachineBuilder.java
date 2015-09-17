package machinebuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import parser.Operation;
import testgeneration.BETATestCase;
import tools.FileTools;

public class CBCMachineBuilder {

	private Operation operationUnderTest;
	private String stateGoal;
	private File builtMachine;



	public CBCMachineBuilder(Operation operationUnderTest, String stateGoal) {
		this.operationUnderTest = operationUnderTest;
		this.stateGoal = stateGoal;
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

		cbcTestMachine.append(createTestOperation());
		
		cbcTestMachine.append("END");

		String cbcMachineFilePath = getSourceMachineDirectory() + getSourceMachineName() + "_" + getOperationUnderTest().getName() + "_" + "CBCTest.mch";

		File mchFile = FileTools.createFileWithContent(cbcMachineFilePath, cbcTestMachine.toString());

		return mchFile;
	}



	private String createPromotedOperations() {
		if(operationsFromOriginalMachineWithoutOperationUnderTest().trim().equals("")) {
			return "";
		} else {
			return "PROMOTES " + operationsFromOriginalMachineWithoutOperationUnderTest();
		}
	}



	private String createMachineIncludes() {
		return "INCLUDES " + getOperationUnderTest().getMachine().getName();
	}



	private String createMachineHeader() {
		return "MACHINE " + getOperationUnderTest().getMachine().getName() + "CBCTest";
	}



	private String createTestOperation() {
		StringBuffer cbcTestOperation = new StringBuffer("");
		
		cbcTestOperation.append(getOperationUnderTest().getName() + "_test" + " =");
		cbcTestOperation.append("\n");
		cbcTestOperation.append("PRE");
		cbcTestOperation.append("\n");
		cbcTestOperation.append(getStateGoal());
		cbcTestOperation.append("\n");
		cbcTestOperation.append("THEN skip");
		cbcTestOperation.append("\n");
		cbcTestOperation.append("END");
		cbcTestOperation.append("\n\n");
		
		return cbcTestOperation.toString();
	}



	private String createStateGoal(BETATestCase testCase) {
		StringBuffer stateGoal = new StringBuffer("");
		Map<String, String> stateValues = testCase.getStateValues();
		
		int count = 0;
		
		for(Entry<String, String> stateValue : stateValues.entrySet()) {
			stateGoal.append(stateValue.getKey() + " = " + stateValue.getValue());
			
			if(count < stateValues.size() - 1) {
				stateGoal.append(" & ");
			}
			
			count++;
		}
		
		return stateGoal.toString();
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

	
	
	public String getStateGoal() {
		return this.stateGoal;
	}

	

	private String getSourceMachineName() {
		return getOperationUnderTest().getMachine().getName();
	}



	private String getSourceMachineDirectory() {
		return getOperationUnderTest().getMachine().getFile().getParentFile().getAbsolutePath() + "/";
	}

}
