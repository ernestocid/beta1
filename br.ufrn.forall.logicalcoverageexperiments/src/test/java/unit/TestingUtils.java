package unit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;

public class TestingUtils {

	
	public boolean compare(Set<MyPredicate> expected, Set<MyPredicate> actual) {
		Set<String> expectedPredicates = convertSetOfPredicatesToStrings(expected);
		Set<String> actualPredicates = convertSetOfPredicatesToStrings(actual);
		
		if(expected.size() != actual.size()) {
			return false;
		} else {
			for(String expectedPredicate : expectedPredicates) {
				if(!actualPredicates.contains(expectedPredicate)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	
	public boolean compare(List<MyPredicate> expected, List<MyPredicate> actual) {
		if(expected.size() != actual.size()) {
			return false;
		} else {
			String expectedElement;
			String actualElement;
			for(int i = 0; i < expected.size(); i++) {
				expectedElement = expected.get(i).toString();
				actualElement = actual.get(i).toString();

				if(!expectedElement.equals(actualElement)) {
					return false;
				}
			}
		}
		
		return true;
	}



	private Set<String> convertSetOfPredicatesToStrings(Set<MyPredicate> myPredicates) {
		Set<String> predicates = new HashSet<String>();
		
		for(MyPredicate myPredicate : myPredicates) {
			predicates.add(myPredicate.toString());
		}
		
		return predicates;
	}
	
}
