package parser.decorators.predicates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AExistsPredicate;
import de.be4.classicalb.core.parser.node.PExpression;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyAExistsPredicate extends MyPredicateDecorator {

	
	private AExistsPredicate exists;
	
	
	public MyAExistsPredicate(AExistsPredicate exists) {
		this.exists = exists;
	}

	

	@Override
	public PPredicate getNode() {
		return this.exists;
	}

	
	
	@Override
	public Set<MyPredicate> getClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		clauses.add(this);
		return clauses;
	}

	

	@Override
	public Set<String> getVariables() {
		MyPredicate existsPredicate = MyPredicateFactory.convertPredicate(this.exists.getPredicate());
		Set<String> variables = existsPredicate.getVariables();
		variables.removeAll(getIdentifiers());
		return variables;
	}
	
	
	
	public List<String> getQuantifiedVariables() {
		return getIdentifiers();
	}


	
	private List<String> getIdentifiers() {
		List<String> identifiers = new ArrayList<String>();
		for (PExpression pExpression : this.exists.getIdentifiers()) {
			MyExpression myExpression = MyExpressionFactory.convertExpression(pExpression);
			identifiers.add(myExpression.toString());
		}
		return identifiers;
	}
	
	
	
	public MyPredicate getQuantifiedPredicate() {
		return MyPredicateFactory.convertPredicate(this.exists.getPredicate());
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer existentialQuant = new StringBuffer("");
		existentialQuant.append("#(");
		List<String> identifiers = getIdentifiers();
		int idCounter = 1;
		for (String id : identifiers) {
			if(idCounter < identifiers.size()) {
				existentialQuant.append(id + ",");
			} else {
				existentialQuant.append(id);
			}
			idCounter++;
		}
		existentialQuant.append(").");
		existentialQuant.append("(" + MyPredicateFactory.convertPredicate(this.exists.getPredicate()).toString() + ")");
		return existentialQuant.toString();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MyAExistsPredicate) {
			MyAExistsPredicate predicate = (MyAExistsPredicate) obj;
			if(predicate.toString().equals(this.toString())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
}
