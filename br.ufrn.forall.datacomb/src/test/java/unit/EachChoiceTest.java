package unit;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import criteria.EachChoice;


public class EachChoiceTest {
	
	
	@Test
	public void shouldGenerateEmptyListForEmptyInputValues() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertTrue(eachChoice.getCombinations().isEmpty());
	}
	
	
	
	@Test
	public void shouldGenerateEmptyListForOneParameterInput() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		
		Set<List<String>> expectedCombinations = new HashSet<List<String>>();
		expectedCombinations.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		
		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, eachChoice.getCombinations());
	}
	
	
	
	@Test
	public void shouldGenerateCombinationsAsSets() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"b1", "b2"})));
		
		Set<List<String>> expectedCombinations = new HashSet<List<String>>();
		expectedCombinations.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "b1"})));
		expectedCombinations.add(new ArrayList<String>(Arrays.asList(new String[] {"a2", "b2"})));
		
		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, eachChoice.getCombinations());
	}
	
	
	
	@Test
	public void shouldGenerateEachChoice() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"b1"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1");

		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, eachChoice.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateEachChoice1() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"b1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"c1"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");

		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, eachChoice.getCombinationsAsStrings());
	}

	
	
	@Test
	public void shouldGenerateEachChoice2() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"a1", "a2", "a3"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"b1", "b2", "b3"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"c1", "c2", "c3"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a2 & b2 & c2");
		expectedCombinations.add("a3 & b3 & c3");
		
		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, eachChoice.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateEachChoice3() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"a1", "a2", "a3"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"b1", "b2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"c1"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a2 & b2 & c1");
		expectedCombinations.add("a3 & b2 & c1");

		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, eachChoice.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateEachChoice4() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"b1", "b2", "b3"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[]{"c1"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b2 & c1");
		expectedCombinations.add("a1 & b3 & c1");

		EachChoice<String> eachChoice = new EachChoice<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, eachChoice.getCombinationsAsStrings());
	}
}
