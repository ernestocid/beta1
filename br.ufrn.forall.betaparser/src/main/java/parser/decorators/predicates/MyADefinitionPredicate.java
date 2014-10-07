package parser.decorators.predicates;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ADefinitionPredicate;
import de.be4.classicalb.core.parser.node.PExpression;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyADefinitionPredicate extends MyPredicateDecorator {

	
	private ADefinitionPredicate definitionPredicate;
	
	
	public MyADefinitionPredicate(ADefinitionPredicate definitionPredicate) {
		this.definitionPredicate = definitionPredicate;
	}
	
	
	
	@Override
	public PPredicate getNode() {
		return this.definitionPredicate;
	}

	
	
	@Override
	public Set<MyPredicate> getClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		clauses.add(this);
		return clauses;
	}

	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		for (PExpression param : definitionPredicate.getParameters()) {
			MyExpression expression = MyExpressionFactory.convertExpression(param);
			variables.addAll(expression.getVariables());
		}
		
		return variables;
	}

	
	
	@Override
	public String toString() {
		return getDefinitionName() + "(" + getParametersList() +")";
	}
	
	
	
	public String getParametersList() {
		StringBuffer parameterList = new StringBuffer("");
		LinkedList<PExpression> parameters = definitionPredicate.getParameters();
		
		int paramCount = 0;
		for (PExpression param : parameters) {
			MyExpression expression = MyExpressionFactory.convertExpression(param);

			if(paramCount < parameters.size() - 1) {
				parameterList.append(expression.toString() + ", ");
			} else {
				parameterList.append(expression.toString());
			}
			
			paramCount++;
		}
		
		return parameterList.toString();
	}
	
	
	
	public String getDefinitionName() {
		return this.definitionPredicate.getDefLiteral().getText();
	}

}
