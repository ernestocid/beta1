package tools;

import java.util.HashSet;
import java.util.Set;

import parser.Characteristic;



public class PredicateTools {

	public static Set<String> convertSetOfClausesToSetOfStrings(Set<Characteristic> clauses) {
		Set<String> clausesString = new HashSet<String>();

		for (Characteristic characteristic : clauses) {
			clausesString.add(characteristic.toString());
		}
		
		return clausesString;
	}

}
