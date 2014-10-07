package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;


public class PredicatesTest {

	
	@Test
	public void shouldParseAllKindsOfPredicate() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/PredicateTest.mch");
		
		Set<String> expectedPreconditions = new HashSet<String>();
		
		expectedPreconditions.add("aa : INT"); // belongs
		expectedPreconditions.add("bb > 10"); // comes from OR, greater
		expectedPreconditions.add("cc >= 10"); // comes from OR, greater equal
		expectedPreconditions.add("dd = 20"); // equal
		expectedPreconditions.add("((ee < 10) => (ff <= 20))"); // implication, less, less equal
		expectedPreconditions.add("gg <: NAT"); // includes
		expectedPreconditions.add("hh <<: NAT1"); // includes strictly
		expectedPreconditions.add("not(ii < 20)"); // negation
		expectedPreconditions.add("jj /: {1, 2, 3}"); // not belongs
		expectedPreconditions.add("kk /= 1"); // not equal
		expectedPreconditions.add("ll /<: NAT"); // not include
		expectedPreconditions.add("mm /<<: NAT1"); // not include strictly

		Set<String> actualPreconditions = new HashSet<String>();
		actualPreconditions.addAll(machine.getOperation(0).getPreconditionClausesAsList());
		
		assertEquals(expectedPreconditions, actualPreconditions);
	}

	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
}
