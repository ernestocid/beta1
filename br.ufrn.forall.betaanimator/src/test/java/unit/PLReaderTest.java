package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import parser.Machine;
import parser.Operation;

import animator.PLReader;


public class PLReaderTest {

	@Test
	public void shouldReadInputDataFromPLFile() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		
		List<String> expectedValues = new ArrayList<String>();

		expectedValues.add("1");
		expectedValues.add("2");
		expectedValues.add("3");
		expectedValues.add("4");
		expectedValues.add("5");
		expectedValues.add("6");
		expectedValues.add("7");
		expectedValues.add("8");
		expectedValues.add("9");
		expectedValues.add("10");

		assertEquals(expectedValues, plReader.readValuesFor("addPaper"));
	}
	
	
	
	@Test
	public void shouldReadInputDataForOperationWithTwoParameters() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		
		List<String> expectedValues = new ArrayList<String>();
		
		expectedValues.add("1,{}");
		expectedValues.add("1,{1}");
		expectedValues.add("1,{1,2}");
		expectedValues.add("1,{1,2,3}");
		expectedValues.add("1,{1,3}");
		expectedValues.add("1,{2}");
		expectedValues.add("1,{2,3}");

		assertEquals(expectedValues, plReader.readValuesFor("addPaperTest"));
	}
	
	
	
	@Test
	public void shouldReturnEmptyListWhenOperationDoestExist() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		List<String> values = plReader.readValuesFor("thisOperationNameDoesNotExist");
		assertTrue(values.isEmpty());
	}
	
	
	
	@Test
	public void shouldReadInputDataForOperation() {
		Machine machine = getMachineInstance("src/test/resources/machines/Paperround.mch");
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		Operation operationUnderTest = machine.getOperation(1);

		List<Map<String, String>> expectedParamCombinations = new ArrayList<Map<String, String>>();
		
		Map<String, String> comb1 = new HashMap<String, String>();
		comb1.put("hh", "1");
		comb1.put("papers", "{}");
		
		Map<String, String> comb2 = new HashMap<String, String>();
		comb2.put("hh", "1");
		comb2.put("papers", "{1}");
		
		Map<String, String> comb3 = new HashMap<String, String>();
		comb3.put("hh", "1");
		comb3.put("papers", "{1,2}");
		
		Map<String, String> comb4 = new HashMap<String, String>();
		comb4.put("hh", "1");
		comb4.put("papers", "{1,2,3}");

		Map<String, String> comb5 = new HashMap<String, String>();
		comb5.put("hh", "1");
		comb5.put("papers", "{1,3}");
		
		Map<String, String> comb6 = new HashMap<String, String>();
		comb6.put("hh", "1");
		comb6.put("papers", "{2}");
		
		Map<String, String> comb7 = new HashMap<String, String>();
		comb7.put("hh", "1");
		comb7.put("papers", "{2,3}");
		
		expectedParamCombinations.add(comb1);
		expectedParamCombinations.add(comb2);
		expectedParamCombinations.add(comb3);
		expectedParamCombinations.add(comb4);
		expectedParamCombinations.add(comb5);
		expectedParamCombinations.add(comb6);
		expectedParamCombinations.add(comb7);
		
		assertEquals(expectedParamCombinations, plReader.readParamValuesFor(operationUnderTest));
	}


	
	@Test
	public void shouldSplitParameterDataString1() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		String parameters = "{0},1,{1,2,3,4,5}";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("{0}");
		expectedParams.add("1");
		expectedParams.add("{1,2,3,4,5}");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	
	
	
	@Test
	public void shouldSplitParameterDataString2() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		
		String parameters = "1,2,{1,2},{1,2,3},3";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("1");
		expectedParams.add("2");
		expectedParams.add("{1,2}");
		expectedParams.add("{1,2,3}");
		expectedParams.add("3");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	
	
	
	@Test
	public void shouldSplitParameterDataString3() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		
		String parameters = "1,2,3";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("1");
		expectedParams.add("2");
		expectedParams.add("3");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	

	
	@Test
	public void shouldSplitParameterDataString4() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		
		String parameters = "{1}, {1,2}, {1,2,3}";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("{1}");
		expectedParams.add("{1,2}");
		expectedParams.add("{1,2,3}");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	
	
	
	@Test
	public void shouldSplitParameterDataString5() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		
		String parameters = "{1}";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("{1}");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}

	
	
	@Test
	public void shouldSplitParameterDataString6() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
		
		String parameters = "1";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("1");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	
	
	
	@Test
	public void shouldSplitParameterDataString7() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));

		String parameters = "";
		List<String> paramsValues = plReader.splitParameterValues(parameters);
		
		assertTrue(paramsValues.isEmpty());
	}
	
	
	
	@Test
	public void shouldSplitParameterDataString8() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));

		String parameters = "Teste, A, -23";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("Teste");
		expectedParams.add("A");
		expectedParams.add("-23");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	
	
	
	@Test
	public void shouldSplitParameterDataString9() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/Paperround.mch.pl"));
				
		String parameters = "{(GOODS1|->1),(GOODS2|->1)},{(GOODS1|->1),(GOODS2|->1)},GOODS1";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("{(GOODS1|->1),(GOODS2|->1)}");
		expectedParams.add("{(GOODS1|->1),(GOODS2|->1)}");
		expectedParams.add("GOODS1");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	
	
	
	@Test
	public void shouldSplitParameterDataString10() {
		PLReader plReader = new PLReader(new File("src/test/resources/plFiles/lua_arith.mch.pl"));
				
		String parameters = "[(LUA_TNUMBER|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1))],1,1,LUA_OPUNM";
		
		List<String> expectedParams = new ArrayList<String>();
		expectedParams.add("[(LUA_TNUMBER|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1))]");
		expectedParams.add("1");
		expectedParams.add("1");
		expectedParams.add("LUA_OPUNM");
		
		assertEquals(expectedParams, plReader.splitParameterValues(parameters));
	}
	
	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
}
