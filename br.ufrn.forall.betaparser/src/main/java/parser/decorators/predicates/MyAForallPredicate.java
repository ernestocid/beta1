package parser.decorators.predicates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AForallPredicate;
import de.be4.classicalb.core.parser.node.PExpression;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAForallPredicate extends MyPredicateDecorator {
	
	
	private AForallPredicate forallPredicate;

	
	
	public MyAForallPredicate(AForallPredicate forallPredicate) {
		this.forallPredicate = forallPredicate;
	}

	
	
	@Override
	public PPredicate getNode() {
		return forallPredicate;
	}

	
	
	@Override
	public Set<MyPredicate> getClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		clauses.add(this);
		return clauses;
	}

	
	
	@Override
	public Set<String> getVariables() {
		MyPredicate implication = MyPredicateFactory.convertPredicate(forallPredicate.getImplication());
		Set<String> variables = implication.getVariables();
		variables.removeAll(getIdentifiers());
		return variables;
	}

	
	
	@Override
	public String toString() {
		StringBuffer universalQuant = new StringBuffer("");
		universalQuant.append("!(");
		List<String> identifiers = getIdentifiers();
		int idCounter = 1;
		for (String id : identifiers) {
			if(idCounter < identifiers.size()) {
				universalQuant.append(id + ",");
			} else {
				universalQuant.append(id);
			}
			idCounter++;
		}
		universalQuant.append(").");
		universalQuant.append(getImplication().toString());
		return universalQuant.toString();
	}
	
	
	
	private MyPredicate getImplication() {
		return MyPredicateFactory.convertPredicate(forallPredicate.getImplication());
	}

	
	
	private List<String> getIdentifiers() {
		List<String> identifiers = new ArrayList<String>();
		for (PExpression pExpression : forallPredicate.getIdentifiers()) {
			MyExpression myExpression = MyExpressionFactory.convertExpression(pExpression);
			identifiers.add(myExpression.toString());
		}
		return identifiers;
	}
	
}
