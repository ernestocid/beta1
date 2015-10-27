package unit;

import static com.google.common.truth.Truth.*;
import java.io.File;

import org.junit.Test;

import parser.Implementation;

public class ImplementationTest {

	@Test
	public void shouldGetImplementationName() {
		Implementation implementation = new Implementation(new File("src/test/resources/machines/refinements/Player_i.imp"));

		assertThat(implementation.getName()).isEqualTo("Player_i");
	}
	
	
	
	@Test
	public void shouldGetNameOfMachineRefined() {
		Implementation implementation = new Implementation(new File("src/test/resources/machines/refinements/Player_i.imp"));

		assertThat(implementation.getNameOfMachineRefined()).isEqualTo("Player");
	}



	@Test
	public void shouldGetImplementationValuesClause() {
		Implementation implementation = new Implementation(new File("src/test/resources/machines/refinements/Player_i.imp"));

		assertThat(implementation.getValues()).isNotNull();
		assertThat(implementation.getValues().getEntries()).containsKey("PLAYER");
		assertThat(implementation.getValues().getEntries().get("PLAYER").toString()).isEqualTo("1..22");
	}

}
