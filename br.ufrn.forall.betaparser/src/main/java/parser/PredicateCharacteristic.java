package parser;

import java.util.Set;

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
//		if(obj instanceof PredicateCharacteristic) {
//			PredicateCharacteristic c = (PredicateCharacteristic) obj;
//			if(c.getCharacteristic().equals(this.getCharacteristic())) {
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
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

}
