package unit;

import static org.junit.Assert.*;

import org.junit.Test;

import configurations.Configurations;

public class ConfigurationsTest {

	
	@Test
	public void shouldGetProBPath() {
		assertNotNull(Configurations.getProBPath());
	}
	
	
	
	@Test
	public void shouldGetMinIntValue() {
		assertNotEquals(0, Configurations.getMinIntProperties());
	}
	
	
	
	@Test
	public void shouldGetMaxIntValue() {
		assertNotEquals(0, Configurations.getMaxIntProperties());
	}
}
