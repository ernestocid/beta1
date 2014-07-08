package unit;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import criteria.Pairwise;


public class PairwiseTest {

	
	@Test
	public void shouldReturnEmptyListForAnEmptyInputList() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);

		assertTrue(pairwise.getCombinations().isEmpty());
	}

	
	
	@Test
	public void shouldReturnEmptyListForInputListWithOneParameter() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
	
		Set<List<String>> expectedCombinations = new HashSet<List<String>>();
		expectedCombinations.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);

		assertEquals(expectedCombinations, pairwise.getCombinations());
	}
	
	

	@Test
	public void shouldGenerateCombinationsAsSet() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		
		Set<List<String>> expectedResults = new HashSet<List<String>>();
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "b1"})));
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "b2"})));
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a2", "b1"})));
		expectedResults.add(new ArrayList<String>(Arrays.asList(new String[] {"a2", "b2"})));
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedResults, pairwise.getCombinations());
	}
	
	
	
	@Test
	public void shouldGenerateCombinationsForTwoParametersWithOneValues() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));

		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	
	@Test
	public void shouldGenerateCombinationsForThreeParametersWithOneValues() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateCombinationsForThreeParametersWithTwoValues() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b2 & c2");
		expectedCombinations.add("a2 & b1 & c2");
		expectedCombinations.add("a2 & b2 & c1");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}

	
	
	@Test
	public void shouldGenerateCombinationsForFourParametersWithTwoValues() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"d1", "d2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();

		expectedCombinations.add("a2 & b1 & c2 & d1"); 
		expectedCombinations.add("a1 & b1 & c1 & d1"); 
		expectedCombinations.add("a1 & b1 & c1 & d2"); 
		expectedCombinations.add("a1 & b2 & c2 & d2"); 
		expectedCombinations.add("a2 & b2 & c1 & d2");
		expectedCombinations.add("a1 & b2 & c1 & d1");

		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateCombinationsForThreeParametersWithUnequalValueLengths(){
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();

		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b1 & c2");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateCombinationsForThreeParametersWithUnequalValueLengths2(){
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();

		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2", "c3"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b1 & c2");
		expectedCombinations.add("a1 & b1 & c3");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	

	@Test
	public void shouldGenerateCombinationsForThreeParametersWithUnequalValueLengths3(){
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();

		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2", "c3"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b2 & c2");
		expectedCombinations.add("a1 & b2 & c1");
		expectedCombinations.add("a1 & b1 & c2");
		expectedCombinations.add("a1 & b1 & c3");
		expectedCombinations.add("a1 & b2 & c3");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateCombinationsForThreeParametersWithUnequalValueLengths4(){
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();

		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a2 & b1 & c2");
		expectedCombinations.add("a2 & b1 & c1");
		expectedCombinations.add("a1 & b1 & c2");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldGenerateCombinationsForThreeParametersWithUnequalValueLengths5(){
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();

		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"d1", "d2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a2 & b1 & c2 & d1");
		expectedCombinations.add("a1 & b1 & c2 & d2");
		expectedCombinations.add("a2 & b1 & c1 & d2");
		expectedCombinations.add("a1 & b1 & c1 & d1");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	

	
	@Test
	public void shouldGenerateCombinationsForThreeParametersWithUnequalValueLengths6(){
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();

		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2", "c3"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b2 & c2");
		expectedCombinations.add("a2 & b1 & c3");
		expectedCombinations.add("a2 & b2 & c1");
		expectedCombinations.add("a2 & b1 & c2");
		expectedCombinations.add("a1 & b2 & c3");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
	
	
	@Test
	public void shouldClearEmptyParametersAndGenerateCombinations() {
		List<List<String>> parametersInputValues = new ArrayList<List<String>>();
		
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"a1", "a2"})));
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"b1", "b2"})));
		parametersInputValues.add(new ArrayList<String>());
		parametersInputValues.add(new ArrayList<String>(Arrays.asList(new String[] {"c1", "c2"})));
		
		Set<String> expectedCombinations = new HashSet<String>();
		expectedCombinations.add("a1 & b1 & c1");
		expectedCombinations.add("a1 & b2 & c2");
		expectedCombinations.add("a2 & b1 & c2");
		expectedCombinations.add("a2 & b2 & c1");
		
		Pairwise<String> pairwise = new Pairwise<String>(parametersInputValues);
		
		assertEquals(expectedCombinations, pairwise.getCombinationsAsStrings());
	}
	
}
