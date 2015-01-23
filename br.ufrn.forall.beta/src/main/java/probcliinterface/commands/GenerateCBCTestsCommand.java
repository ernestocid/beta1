package probcliinterface.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import configurations.Configurations;

public class GenerateCBCTestsCommand {

	private File machine;
	private String operationToCover;
	private File outputXML;



	public GenerateCBCTestsCommand(File machine, String operationToCover, File outputXML) {
		this.machine = machine;
		this.operationToCover = operationToCover;
		this.outputXML = outputXML;
	}



	public void execute() {
		executeCommandOnTerminal(getCommand());
	}



	private String[] getCommand() {
		String[] command = new String[] {
				Configurations.getProBPath() + "probcli",
				this.machine.getAbsolutePath(),
				"-cbc_tests",
				String.valueOf(Configurations.getCBCDepth()),
				"1=1",
				this.outputXML.getAbsolutePath(),
				"-cbc_cover",
				this.operationToCover
		};

		return command;
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

}
