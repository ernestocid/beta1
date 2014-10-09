package parser;

import java.util.Set;

import parser.decorators.predicates.MyAEqualPredicate;
import parser.decorators.predicates.MyAGreaterEqualPredicate;
import parser.decorators.predicates.MyAGreaterPredicate;
import parser.decorators.predicates.MyALessEqualPredicate;
import parser.decorators.predicates.MyALessPredicate;
import parser.decorators.predicates.MyANotEqualPredicate;
import parser.decorators.predicates.MyPredicate;

public class PredicateCharacteristic extends Characteristic {


	private MyPredicate predicate;
	
	
	public PredicateCharacteristic(MyPredicate predicate, CharacteristicType type) {
		super(type, predicate.toString());
		this.predicate = predicate;
	}
	
	
	
	public MyPredicate getPredicate() {
		return predicate;
	}



	public void setPredicate(MyPredicate predicate) {
		this.predicate = predicate;
	}

	

	@Override
	public String toString() {
		return this.predicate.toString();
	}

	

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	


	@Override
	public boolean isTypingCharacteristic() {
		return this.predicate.isTypingClause();
	}

	
	
	@Override
	public boolean isIntervalCharacteristic() {
		return this.predicate.isInterval();
	}

	

	@Override
	public Set<String> getVariables() {
		return this.predicate.getVariables();
	}



	@Override
	public boolean isRelationalCharacteristic() {
		boolean equal = getPredicate() instanceof MyAEqualPredicate;
		boolean notEqual = getPredicate() instanceof MyANotEqualPredicate;
		boolean greater = getPredicate() instanceof MyAGreaterPredicate;
		boolean greaterOrEqual = getPredicate() instanceof MyAGreaterEqualPredicate;
		boolean less = getPredicate() instanceof MyALessPredicate;
		boolean lessEqual = getPredicate() instanceof MyALessEqualPredicate;
		
		if(equal || notEqual || greater || greaterOrEqual || less || lessEqual) {
			return true;
		} else {
			return false;
		}
	}

}
