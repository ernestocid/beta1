package parser.decorators.expressions;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;

import de.be4.classicalb.core.parser.node.ALambdaExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyALambdaExpression extends MyExpressionDecorator {

	
	private ALambdaExpression lambda;
	
	
	public MyALambdaExpression(ALambdaExpression lambda) {
		this.lambda = lambda;
	}
	
	
	
	@Override
	public PExpression getNode() {
		return this.lambda;
	}
	
	
	
	@Override
	public Set<String> getVariables() {
		Set<String> variables = new HashSet<String>();
		
		for(PExpression expr : this.lambda.getIdentifiers()) {
			MyExpression convertedExpr = MyExpressionFactory.convertExpression(expr);
			variables.addAll(convertedExpr.getVariables());
		}
		
		MyPredicate convertedPredicate = MyPredicateFactory.convertPredicate(this.lambda.getPredicate());
		variables.addAll(convertedPredicate.getVariables());
		
		return variables;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer lambdaText = new StringBuffer();
		
		lambdaText.append("%(");
		
		int identCount = 1;
		
		for (PExpression expr : this.lambda.getIdentifiers()) {
			MyExpression convertedExpr = MyExpressionFactory.convertExpression(expr);

			if (identCount < this.lambda.getIdentifiers().size()) {
				lambdaText.append(convertedExpr.toString() + ",");
			} else {
				lambdaText.append(convertedExpr.toString());
			}
			
			identCount++;
		}
		
		lambdaText.append(").");
		
		MyPredicate lambdaPredicate = MyPredicateFactory.convertPredicate(this.lambda.getPredicate());
		
		lambdaText.append("(" + lambdaPredicate.toString());
		
		lambdaText.append(" | ");
		MyExpression convertedExpr = MyExpressionFactory.convertExpression(this.lambda.getExpression());
		lambdaText.append(convertedExpr.toString());
		
		lambdaText.append(")");
		
		return lambdaText.toString();
	}

}
