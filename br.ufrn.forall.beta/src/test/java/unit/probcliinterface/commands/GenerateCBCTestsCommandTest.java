package unit.probcliinterface.commands;

import static com.google.common.truth.Truth.*;

import java.io.File;

import org.junit.Test;

import probcliinterface.commands.GenerateCBCTestsCommand;
import tools.FileTools;

public class GenerateCBCTestsCommandTest {

	@Test
	public void shouldGenerateCBCTestsXMLFile() {
		File machine = new File("src/test/resources/machines/others/Course_student_pass_or_fail_CBCTest.mch");
		File outputXML = new File("src/test/resources/machines/others/cbc_test.xml");

		GenerateCBCTestsCommand cmd = new GenerateCBCTestsCommand(machine, "student_pass_or_fail_test1", outputXML);
		cmd.execute();

		String expectedXML = FileTools.getFileContent(new File("src/test/resources/others/cbc_tests_pass_or_fail_from_course.xml"));
		String actualXML = FileTools.getFileContent(outputXML);

		assertThat(actualXML).isEqualTo(expectedXML);
	}

}
