package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;

import de.be4.classicalb.core.parser.node.AComprehensionSetExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyAComprehensionSetExpression extends MyExpressionDecorator {

	
	
	private AComprehensionSetExpression comprehensionSetExpression;
	
	
	public MyAComprehensionSetExpression(AComprehensionSetExpression comprehensionSetExpression) {
		this.comprehensionSetExpression = comprehensionSetExpression;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.comprehensionSetExpression;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		MyPredicate predicate = MyPredicateFactory.convertPredicate(comprehensionSetExpression.getPredicates());
		variables.addAll(predicate.getVariables());
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer ids = new StringBuffer("");
		MyPredicate predicate = MyPredicateFactory.convertPredicate(comprehensionSetExpression.getPredicates());
		
		for (int i = 0; i < comprehensionSetExpression.getIdentifiers().size(); i++) {
			String id = MyExpressionFactory.convertExpression(comprehensionSetExpression.getIdentifiers().get(i)).toString();

			if(i < comprehensionSetExpression.getIdentifiers().size() - 1) {
				ids.append(id + ", ");
			} else {
				ids.append(id);
			}
		}
		
		return "{" + ids.toString() + " | " + predicate.toString() + "}";
	}

}
