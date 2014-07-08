package unit;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




import org.junit.Test;

import criteria.AllCombinations;


public class AllCombinationsTest {

	
	@Test
	public void shouldGenerateEmptyListForEmptyInputValues() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		AllCombinations<String> allCombinations = new AllCombinations<String>(parametersInputValues);
		
		assertTrue(allCombinations.getCombinations().isEmpty());
	}
	
	
	
	@Test
	public void shouldGenerateEmptyListForInputValuesWithOneParameter() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		
		AllCombinations<String> allCombinations = new AllCombinations<String>(parametersInputValues);
		
		Set<List<String>> expectedCombinations = new HashSet<List<String>>();
		expectedCombinations.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		
		assertEquals(expectedCombinations, allCombinations.getCombinations());
	}

	
	
	@Test
	public void shouldGenerateAllCombinationsAsSet() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		
		Set<List<String>> expectedResults = new HashSet<List<String>>();
		
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "b1"})));
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "b2"})));
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a2", "b1"})));
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a2", "b2"})));
		
		AllCombinations<String> allComb = new AllCombinations<String>(parametersInputValues);
		
		assertEquals(expectedResults, allComb.getCombinations());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinations() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));
		
		Set<String> expectedResults = new HashSet<String>();
		expectedResults.add("a1 & b1");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parametersInputValues);
		
		assertEquals(expectedResults, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinations1() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1"})));
		
		Set<String> expectedResults = new HashSet<String>();
		expectedResults.add("a1 & b1 & c1");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parametersInputValues);
		
		assertEquals(expectedResults, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinations2() {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();
		
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2", "a3"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2", "b3"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1");
		expectedCombinations.add("a1 & b2");
		expectedCombinations.add("a1 & b3");
		expectedCombinations.add("a2 & b1");
		expectedCombinations.add("a2 & b2");
		expectedCombinations.add("a2 & b3");
		expectedCombinations.add("a3 & b1");
		expectedCombinations.add("a3 & b2");
		expectedCombinations.add("a3 & b3");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parameterInputValues);
		
		assertEquals(expectedCombinations, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinations3() {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();
		
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b1 & c2");
		expectedCombinations.add("a1 & b2 & c1");
		expectedCombinations.add("a1 & b2 & c2");
		expectedCombinations.add("a2 & b1 & c1");
		expectedCombinations.add("a2 & b1 & c2");
		expectedCombinations.add("a2 & b2 & c2");
		expectedCombinations.add("a2 & b2 & c1");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parameterInputValues);
		
		assertEquals(expectedCombinations, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinations4() {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();
		
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2", "a3"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b2 & c1");
		expectedCombinations.add("a2 & b1 & c1");
		expectedCombinations.add("a2 & b2 & c1");
		expectedCombinations.add("a3 & b1 & c1");
		expectedCombinations.add("a3 & b2 & c1");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parameterInputValues);
		
		assertEquals(expectedCombinations, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinations5() {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();
		
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2", "b3"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b2 & c1");
		expectedCombinations.add("a1 & b3 & c1");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parameterInputValues);
		
		assertEquals(expectedCombinations, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinations6() {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();
		
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2", "a3", "a4"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2", "b3", "b4"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1");
		expectedCombinations.add("a1 & b2");
		expectedCombinations.add("a1 & b3");
		expectedCombinations.add("a1 & b4");
		expectedCombinations.add("a2 & b1");
		expectedCombinations.add("a2 & b2");
		expectedCombinations.add("a2 & b3");
		expectedCombinations.add("a2 & b4");
		expectedCombinations.add("a3 & b1");
		expectedCombinations.add("a3 & b2");
		expectedCombinations.add("a3 & b3");
		expectedCombinations.add("a3 & b4");
		expectedCombinations.add("a4 & b1");
		expectedCombinations.add("a4 & b2");
		expectedCombinations.add("a4 & b3");
		expectedCombinations.add("a4 & b4");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parameterInputValues);
		
		assertEquals(expectedCombinations, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinationsCleaningEmptyLists() {
		List<List<String>> parameterInputValues = new ArrayList<List<String>>();
		
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parameterInputValues.add(new ArrayList<String>());
		parameterInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b1 & c2");
		expectedCombinations.add("a1 & b2 & c1");
		expectedCombinations.add("a1 & b2 & c2");
		expectedCombinations.add("a2 & b1 & c1");
		expectedCombinations.add("a2 & b1 & c2");
		expectedCombinations.add("a2 & b2 & c2");
		expectedCombinations.add("a2 & b2 & c1");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parameterInputValues);
		
		assertEquals(expectedCombinations, allComb.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateAllCombinationsLimitingMaxCombinations() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2", "a3"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2", "b3"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1");
		expectedCombinations.add("a1 & b2");
		expectedCombinations.add("a1 & b3");
		expectedCombinations.add("a2 & b1");
		expectedCombinations.add("a2 & b2");
		
		AllCombinations<String> allComb = new AllCombinations<String>(parametersInputValues, 5);
		
		assertEquals(expectedCombinations, allComb.getCombinationsAsStrings());
	}
	
	
}
