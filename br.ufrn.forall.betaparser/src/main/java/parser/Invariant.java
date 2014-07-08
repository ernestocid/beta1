package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
	
	public Set<MyPredicate> getClausesList() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		predicate.createClausesList(clauses);
		return clauses;
	}
	
	public MyPredicate getPredicate() {
		return predicate;
	}
	
	@Override
	public String toString() {
		return predicate.toString() + "\n";
	}
	
	public List<String> getClauses() {
		List<String> clausesString = new ArrayList<String>();
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		predicate.createClausesList(clauses);
		for (MyPredicate clause : clauses) {
			clausesString.add(clause.toString());
		}
		Collections.sort(clausesString);
		return clausesString;
	}
}
