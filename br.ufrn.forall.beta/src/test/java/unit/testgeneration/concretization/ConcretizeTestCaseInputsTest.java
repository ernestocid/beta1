package unit.testgeneration.concretization;

import static com.google.common.truth.Truth.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;
import parser.Implementation;
import parser.Machine;
import parser.Operation;
import testgeneration.concretization.ConcretizeTestCaseInputs;
import tools.FileTools;

public class ConcretizeTestCaseInputsTest {

	@Before
	public void setUp() {
		Configurations.setMaxIntProperties(20);
		Configurations.setMinIntProperties(-1);
		Configurations.setAutomaticOracleEvaluation(true);
		Configurations.setUseKodkod(false);
		Configurations.setRandomiseEnumerationOrder(false);
		Configurations.setUseProBApiToSolvePredicates(false);
		Configurations.setFindPreamble(false);
		Configurations.setDeleteTempFiles(true);
	}

	
	
	@Test
	public void shouldConcretizeTestCaseInputs() {
		File xmlReport = new File("src/test/resources/test_reports/xml/report_for_substitute_from_Player_EC-PW.xml");
		Implementation implementation = new Implementation(new File("src/test/resources/machines/others/RefinementExamples/Player_i.imp"));
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/RefinementExamples/Player.mch"));
		Operation operationUnderTest = machine.getOperation(0); // substitute

		ConcretizeTestCaseInputs concretization = new ConcretizeTestCaseInputs(xmlReport, operationUnderTest, implementation);
		File xmlWithConcreteData = concretization.concretizeTestData();

		String expectedResult = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/expected_report_for_substitute_from_Player_EC-PW_concrete.xml"));
		String actualResult = FileTools.getFileContent(xmlWithConcreteData);
		
		assertThat(actualResult).isEqualTo(expectedResult);
	}
	
	
	
	@Test
	public void shouldConcretizeTestCaseInputs2() {
		File xmlReport = new File("src/test/resources/test_reports/xml/report_for_dec_from_Counter_LC-CoC.xml");
		Implementation implementation = new Implementation(new File("src/test/resources/machines/others/RefinementExamples/CounterI.imp"));
		
		Machine machine = new Machine(new File("src/test/resources/machines/others/RefinementExamples/Counter.mch"));
		Operation operationUnderTest = machine.getOperation(2); // dec

		ConcretizeTestCaseInputs concretization = new ConcretizeTestCaseInputs(xmlReport, operationUnderTest, implementation);
		File xmlWithConcreteData = concretization.concretizeTestData();

		String expectedResult = FileTools.getFileContent(new File("src/test/resources/test_reports/xml/expected_report_for_dec_from_Counter_LC-CoC_concrete.xml"));
		String actualResult = FileTools.getFileContent(xmlWithConcreteData);
		
		assertThat(actualResult).isEqualTo(expectedResult);
	}

}
