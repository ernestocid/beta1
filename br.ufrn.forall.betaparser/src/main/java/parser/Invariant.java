package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import de.be4.classicalb.core.parser.node.AInvariantMachineClause;

public class Invariant {

	
	private MyPredicate predicate;
	
	
	public Invariant(AInvariantMachineClause invariant) {
		this.predicate = MyPredicateFactory.convertPredicate(invariant.getPredicates());
	}

	
	
	public Set<MyPredicate> getClauses() {
		return this.predicate.getClauses();
	}
	
	
	
	public MyPredicate getPredicate() {
		return predicate;
	}
	
	
	
	@Override
	public String toString() {
		return predicate.toString() + "\n";
	}
	
	
	
	public List<String> getClausesAsList() {
		List<String> clausesList = new ArrayList<String>();
		for (MyPredicate clause : predicate.getClauses()) {
			clausesList.add(clause.toString());
		}
		Collections.sort(clausesList);
		return clausesList;
	}
}