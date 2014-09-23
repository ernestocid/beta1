package unit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Characteristic;
import parser.Machine;
import parser.Operation;
import testgeneration.Partitioner;
import static org.mockito.Mockito.*;


public class PartitionerTest {

	
	@Test
	public void shouldGetInputSpaceVariables() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/Test.mch");
		
		Partitioner partitioner = new Partitioner(machine.getOperation(0));
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("pressed");
		expectedVariables.add("foo");
		expectedVariables.add("test_variable");
		expectedVariables.add("test_variable3");
		expectedVariables.add("test_variable2");
		expectedVariables.add("cab_consol_close_button_pushed");
		expectedVariables.add("cab_side_open_button_pushed");
		expectedVariables.add("cab_consol_open_button_pushed");
		expectedVariables.add("cab_side_close_button_pushed");
		
		assertEquals(expectedVariables, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceVariables2() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/bibliotheque.mch");
		
		Partitioner partitioner = new Partitioner(machine.getOperation(0));
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("abonne");
		expectedVariables.add("Abonnes");
		expectedVariables.add("Exemplaires");
		expectedVariables.add("Livres");
		expectedVariables.add("est_emprunte_par");
		expectedVariables.add("est_exemplaire_de");
		
		assertEquals(expectedVariables, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceVariablesForMachineWithConditionalStatements() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Life.mch");
		
		Partitioner partitioner = new Partitioner(machine.getOperation(0));
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("nn");
		expectedVariables.add("ss");
		expectedVariables.add("male");
		expectedVariables.add("female");
		
		assertEquals(expectedVariables, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceForMachineWithoutInvariant() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleMachine.mch");
		
		Partitioner partitioner = new Partitioner(machine.getOperation(0));
		
		Set<String> expectedVariables = new HashSet<String>();
		expectedVariables.add("yy");
		expectedVariables.add("xx");
		
		assertEquals(expectedVariables, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceForMachineStructuredWithIncludes() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Locks.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedInputSpace = new HashSet<String>();
		expectedInputSpace.add("dd");
		expectedInputSpace.add("status");
		expectedInputSpace.add("position");
		
		assertEquals(expectedInputSpace, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceFromPromotedOperations() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Locks.mch");
		Operation operationUnderTest = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedInputSpace = new HashSet<String>();
		expectedInputSpace.add("dd");
		expectedInputSpace.add("position");
		
		assertEquals(expectedInputSpace, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceForMachineStructuredWithSees() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedInputSpace = new HashSet<String>();
		expectedInputSpace.add("purchases");
		expectedInputSpace.add("gg");
		expectedInputSpace.add("price");
		
		assertEquals(expectedInputSpace, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceForMachinesStructuredWithUses() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Marriage.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedInputSpace = new HashSet<String>();
		expectedInputSpace.add("mm");
		expectedInputSpace.add("ff");
		expectedInputSpace.add("marriage");
		expectedInputSpace.add("male");
		expectedInputSpace.add("female");
		
		assertEquals(expectedInputSpace, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceForMachinesStructuredWithExtends() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Registrar.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedInputSapce = new HashSet<String>();
		expectedInputSapce.add("nn");
		expectedInputSapce.add("male");
		expectedInputSapce.add("female");
		expectedInputSapce.add("marriage");
		
		assertEquals(expectedInputSapce, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetInputSpaceForMachinesWithConstants() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleCostumer.mch");
		Operation operationUnderTest = machine.getOperation(0);
		
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedInputSpace = new HashSet<String>();
		expectedInputSpace.add("purchases");
		expectedInputSpace.add("gg");
		expectedInputSpace.add("price");
		
		assertEquals(expectedInputSpace, partitioner.getOperationInputSpace());
	}
	
	
	
	@Test
	public void shouldGetOperationCharacteristics() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/Test.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("foo : BOOL");
		expectedCharacteristics.add("pressed : BOOL");
		expectedCharacteristics.add("test_variable <: NAT1");
		expectedCharacteristics.add("test_variable2 <: NAT1");
		expectedCharacteristics.add("test_variable3 <: NAT1");
		expectedCharacteristics.add("cab_consol_open_button_pushed : BOOL");
		expectedCharacteristics.add("cab_side_open_button_pushed : BOOL");
		expectedCharacteristics.add("cab_side_close_button_pushed : BOOL");
		expectedCharacteristics.add("cab_consol_close_button_pushed : BOOL");
		expectedCharacteristics.add("((pressed = TRUE) => (foo = FALSE))");
		expectedCharacteristics.add("((test_variable3 = test_variable) => (foo = TRUE))");
		expectedCharacteristics.add("((test_variable2 = test_variable) => (test_variable3 /= test_variable))");
		expectedCharacteristics.add("cab_consol_open_button_pushed : BOOL");
		 
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetOperationCharacteristics2() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/bibliotheque.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("abonne : Ens_abonnes");
		expectedCharacteristics.add("abonne /: Abonnes");
		expectedCharacteristics.add("Abonnes <: Ens_abonnes");
		expectedCharacteristics.add("Exemplaires <: Ens_exemplaires");
		expectedCharacteristics.add("Livres <: Ens_livres");
		expectedCharacteristics.add("est_exemplaire_de : (Exemplaires --> Livres)");
		expectedCharacteristics.add("est_emprunte_par : (Exemplaires +-> Abonnes)");
		expectedCharacteristics.add("!(abonne).((abonne : Abonnes) => (card(dom((est_emprunte_par |> {abonne}))) <= 3))");
		expectedCharacteristics.add("!(abonne,ex1,ex2).((abonne : Abonnes & ex1 : Exemplaires & ex2 : Exemplaires & ex1 /= ex2 & ex1 : dom((est_emprunte_par |> {abonne})) & ex2 : dom((est_emprunte_par |> {abonne}))) => (est_exemplaire_de(ex1) /= est_exemplaire_de(ex2)))");
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetOperationCharacteristicsForOperationWithNoPrecondition() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/SimpleLift.mch");
		Operation operationUnderTest = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("floor : 1..5");
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetOperationCharacteristicsWhenItHasConditionals() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Player.mch");
		Operation operationUnderTest = machine.getOperation(1);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("pp : PLAYER");
		expectedCharacteristics.add("pp : team");
		expectedCharacteristics.add("team <: PLAYER");
		expectedCharacteristics.add("card(team) = 11");
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetCharacteristicsForMachineStructuredWithIncludes() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Locks.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("dd : DOOR");
		expectedCharacteristics.add("status(dd) = unlocked");
		expectedCharacteristics.add("status : (DOOR --> STATUS)");
		expectedCharacteristics.add("(position~)[{open}] <: (status~)[{unlocked}]");
		expectedCharacteristics.add("position : (DOOR --> POSITION)");
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetCharacteristicsFromPromotedOperations() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Locks.mch");
		Operation operationUnderTest = machine.getOperation(3);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("dd : DOOR");
		expectedCharacteristics.add("position : (DOOR --> POSITION)");
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetCharacteristicsForMachineStructuredWithSees() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Costumer.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("gg : GOODS");
		expectedCharacteristics.add("price : (GOODS --> NAT1)");
		expectedCharacteristics.add("price(gg) <= limit(gg)");
		expectedCharacteristics.add("purchases <: GOODS");
		

		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetCharacteristicsForMachinesStructuredWithUses() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Marriage.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("mm : male");
		expectedCharacteristics.add("ff : female");
		expectedCharacteristics.add("mm /: dom(marriage)");
		expectedCharacteristics.add("ff /: ran(marriage)");
		expectedCharacteristics.add("marriage : (male >+> female)");
		expectedCharacteristics.add("male <: PERSON");
		expectedCharacteristics.add("female <: PERSON");
		expectedCharacteristics.add("male /\\ female = {}");
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetCharacteristicsForMachinesStructuredWithExtends() {
		Machine machine = getMachineInstance("src/test/resources/machines/schneider/Registrar.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);
		
		Set<String> expectedCharacteristics = new HashSet<String>();
		expectedCharacteristics.add("nn : male \\/ female");
		expectedCharacteristics.add("male <: PERSON");
		expectedCharacteristics.add("female <: PERSON");
		expectedCharacteristics.add("male /\\ female = {}");
		expectedCharacteristics.add("marriage : (male >+> female)");
		expectedCharacteristics.add("nn : dom(marriage)");
		expectedCharacteristics.add("nn : ran(marriage)");
		
		assertEquals(expectedCharacteristics, partitioner.getOperationCharacteristicsAsStrings());
	}
	
	
	
	@Test
	public void shouldGetTypingClauses() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/Test.mch");
		Operation operationUnderTest = machine.getOperation(0);
		Partitioner partitioner = new Partitioner(operationUnderTest);

		Set<String> expectedTypingClauses = new HashSet<String>();
		expectedTypingClauses.add("pressed : BOOL");
		expectedTypingClauses.add("foo : BOOL");
		expectedTypingClauses.add("test_variable <: NAT1");
		expectedTypingClauses.add("test_variable2 <: NAT1");
		expectedTypingClauses.add("test_variable3 <: NAT1");
		expectedTypingClauses.add("cab_side_open_button_pushed : BOOL");
		expectedTypingClauses.add("cab_side_close_button_pushed : BOOL");
		expectedTypingClauses.add("cab_consol_close_button_pushed : BOOL");
		expectedTypingClauses.add("cab_consol_open_button_pushed : BOOL");
		
		
		assertEquals(expectedTypingClauses, partitioner.getTypingClauses());
	}
	
	
	
	@Test
	public void shouldGetCharacteristcsWhenTheyAreRepeatedInManyPlaces() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/TicTacToe.mch");
		Operation operationUnderTestOperation = machine.getOperation(2);
		Partitioner partitioner = new Partitioner(operationUnderTestOperation);
		
		Set<Characteristic> expected = new HashSet<Characteristic>();
		
		Characteristic mockedChar1 = mock(Characteristic.class);
		when(mockedChar1.toString()).thenReturn("bposn \\/ rposn = 0..8");
		when(mockedChar1.getCharacteristic()).thenReturn("bposn \\/ rposn = 0..8");
		
		Characteristic mockedChar2 = mock(Characteristic.class);
		when(mockedChar2.toString()).thenReturn("ThreeInRow(bposn) = TRUE");
		when(mockedChar2.getCharacteristic()).thenReturn("ThreeInRow(bposn) = TRUE");
		
		Characteristic mockedChar3 = mock(Characteristic.class);
		when(mockedChar3.toString()).thenReturn("bposn /\\ rposn = {}");
		when(mockedChar3.getCharacteristic()).thenReturn("bposn /\\ rposn = {}");
		
		Characteristic mockedChar4 = mock(Characteristic.class);
		when(mockedChar4.toString()).thenReturn("rposn <: 0..8");
		when(mockedChar4.getCharacteristic()).thenReturn("rposn <: 0..8");
		
		Characteristic mockedChar5 = mock(Characteristic.class);
		when(mockedChar5.toString()).thenReturn("ThreeInRow(rposn) = TRUE");
		when(mockedChar5.getCharacteristic()).thenReturn("ThreeInRow(rposn) = TRUE");
		
		Characteristic mockedChar6 = mock(Characteristic.class);
		when(mockedChar6.toString()).thenReturn("turn : Player");
		when(mockedChar6.getCharacteristic()).thenReturn("turn : Player");
		
		Characteristic mockedChar7 = mock(Characteristic.class);
		when(mockedChar7.toString()).thenReturn("bposn <: 0..8");
		when(mockedChar7.getCharacteristic()).thenReturn("bposn <: 0..8");		

		expected.add(mockedChar1);
		expected.add(mockedChar2);
		expected.add(mockedChar3);
		expected.add(mockedChar4);
		expected.add(mockedChar5);
		expected.add(mockedChar6);
		expected.add(mockedChar7);
		
		System.out.println(expected);
		System.out.println(partitioner.getOperationCharacteristics());
		
		assertEquals(7, partitioner.getOperationCharacteristics().size());
		assertTrue(compareSetsOfCharacteristics(expected, partitioner.getOperationCharacteristics()));
	}
	
	
	
	private boolean compareSetsOfCharacteristics(Set<Characteristic> setOne, Set<Characteristic> setTwo) {
		
		Set<String> characteristics1 = new HashSet<String>();
		Set<String> characteristics2 = new HashSet<String>();
		
		for(Characteristic c : setOne) {
			characteristics1.add(c.getCharacteristic());
		}
		
		for(Characteristic c : setOne) {
			characteristics2.add(c.getCharacteristic());
		}
		
		if(characteristics1.size() == characteristics2.size()) {
			for(String c : characteristics1) {
				if(!characteristics2.contains(c)) {
					return false;
				} 
			}
			return true;
		} else {
			return false;
		}
		
	}

	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
}
