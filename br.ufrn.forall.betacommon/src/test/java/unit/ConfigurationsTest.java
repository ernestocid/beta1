package unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import configurations.Configurations;

public class ConfigurationsTest {

	@Before
	public void setUp() {
		Configurations.setUseKodkod(false);
		Configurations.setRandomiseEnumerationOrder(false);
		Configurations.setUseProBApiToSolvePredicates(false);
		Configurations.setMaxIntProperties(20);
		Configurations.setMinIntProperties(-10);
	}



	@Test
	public void shouldGetProBPath() {
		assertNotNull(Configurations.getProBPath());
	}



	@Test
	public void shouldGetMinIntValue() {
		assertEquals(-10, Configurations.getMinIntProperties());
	}



	@Test
	public void shouldGetMaxIntValue() {
		assertEquals(20, Configurations.getMaxIntProperties());
	}



	@Test
	public void shouldGetUseKodkod() {
		assertFalse(Configurations.getUseKodkod());
	}



	@Test
	public void shouldGetUseProBApi() {
		assertFalse(Configurations.isUseProBApiToSolvePredicates());
	}



	@Test
	public void shouldGetUseProBRandomEnumeration() {
		assertFalse(Configurations.getRandomiseEnumerationOrder());
	}
}
