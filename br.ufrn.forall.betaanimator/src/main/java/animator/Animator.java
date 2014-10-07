package animator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import parser.Machine;
import parser.Operation;
import configurations.Configurations;

public class Animator {

	
	public final static String SLASH = System.getProperty("file.separator");

	
	private Machine machine;

	
	public Animator(Machine machine) {
		this.machine = machine;
	}

	
	
	public List<Map<String, String>> animateOperation(int operationIndex) {
		executeCommandOnTerminal(animationCommand());
		List<Map<String, String>> enabledInputsForOperation = getAnimationsFromPLFile(this.machine.getOperation(operationIndex));
		return enabledInputsForOperation;
	}
	


	private void executeCommandOnTerminal(String[] command) {
		Runtime runtime = Runtime.getRuntime();

		System.out.print("Executing: " + printCommandForDebugging(command));

		try {
			Process process = runtime.exec(command);
			
			BufferedReader cmdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader cmdOutputErrors = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			printCommandOutput(cmdOutput);
		} catch (IOException e) {
			System.err.println("Error while trying to execute the animation command.");
		}
		
		System.out.println(" Executed!");
		
	}
	
	
	
	private String printCommandForDebugging(String[] command) {
		StringBuffer fullCommand = new StringBuffer();
		for (int i = 0; i < command.length; i++) {
			fullCommand.append(command[i] + " ");
		}
		return fullCommand.toString();
	}



	private List<Map<String, String>> getAnimationsFromPLFile(Operation operation) {
		List<Map<String, String>> inputsForOperation = new ArrayList<Map<String, String>>();
		File plFile = new File(getPLFileLocation());
		
		if(plFile.exists()) {
			PLReader plReader = new PLReader(plFile);
			inputsForOperation = plReader.readParamValuesFor(operation);
		}
		
		return inputsForOperation;
	}
	
	
	
	public List<Animation> animateAllOperations() {
		List<Animation> animations = new ArrayList<Animation>();
		executeCommandOnTerminal(animationCommand());
		
		for(Operation op : this.machine.getOperations()) {
			List<Map<String, String>> values = getAnimationsFromPLFile(op);
			if(!values.isEmpty()) {
				animations.add(new Animation(op.getPrecondition(), values));
			}
		}

		return animations;
	}

	

	private void printCommandOutput(BufferedReader cmdOutput) {
		String line = "";
		String code = "";

		try {
			while ((line = cmdOutput.readLine()) != null) {
			}
		} catch (IOException e) {
			System.err.println("Error while trying to print animation output message.");
		}
	}

	

	private String[] animationCommand() {
		String[] command = new String[] {
				Configurations.getProBPath() + "probcli",
				"-init",
				"-mc",
				"1",
				"-p",
				"MAX_INITIALISATIONS",
				"1",
				"-p",
				"MAX_OPERATIONS",
				"1",
				"-p",
				"MININT",
				String.valueOf(Configurations.getMinIntProperties()),
				"-p",
				"MAXINT",
				String.valueOf(Configurations.getMaxIntProperties()),
//				"-p",
//				"CHR",
//				"TRUE",
				getMachineLocation(),
				"-save",
				getPLFileLocation()
		};
		
		return command;
	}

	
	
	private String getPLFileLocation() {
		String plLocation = machine.getFile().getParentFile().getAbsolutePath() + System.getProperty("file.separator") + machine.getName() + ".mch.pl";
		try {
			return URLDecoder.decode(plLocation, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	
	
	private String getMachineLocation() {
		try {
			return URLDecoder.decode(this.machine.getFile().getAbsolutePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}



	public List<Operation> getOperationsWithUnsolvablePreconditionPredicates() {
		List<Operation> unsolvableOperations = new ArrayList<Operation>();

		for(int i = 0; i < this.machine.getOperations().size(); i++) {
			List<Map<String, String>> animation = animateOperation(i);
			
			if(animation.isEmpty()) {
				unsolvableOperations.add(this.machine.getOperation(i));
			}
		}
		
		return unsolvableOperations;
	}
	
}
