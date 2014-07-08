package utils;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import parser.decorators.predicates.MyPredicateFactory;
import de.be4.classicalb.core.parser.node.AAnySubstitution;
import de.be4.classicalb.core.parser.node.AAssignSubstitution;
import de.be4.classicalb.core.parser.node.AParallelSubstitution;
import de.be4.classicalb.core.parser.node.PExpression;
import de.be4.classicalb.core.parser.node.PSubstitution;

public class SubstitutionTools {

	public static String convertToString(PSubstitution substitution) {
		
		if (substitution instanceof AAssignSubstitution) {
			AAssignSubstitution assign = (AAssignSubstitution) substitution;
			
			StringBuffer leftVars = new StringBuffer("");
			int leftVarsCount = 1;
			
			for (PExpression expression : assign.getLhsExpression()) {
				MyExpression myExpression = MyExpressionFactory.convertExpression(expression);
				leftVars.append(myExpression.toString());
				if(leftVarsCount < assign.getLhsExpression().size()) {
					leftVars.append(", ");
				}
				leftVarsCount++;
			}
			
			StringBuffer rightVars = new StringBuffer("");
			int rightVarsCount = 1;
			
			for (PExpression expression : assign.getRhsExpressions()) {
				MyExpression myExpression = MyExpressionFactory.convertExpression(expression);
				rightVars.append(myExpression.toString());
				if(rightVarsCount < assign.getRhsExpressions().size()) {
					rightVars.append(", ");
				}
				rightVarsCount++;
			}
			
			return leftVars.toString() + " := " + rightVars.toString();
		} else if (substitution instanceof AAnySubstitution) {
			AAnySubstitution any = (AAnySubstitution) substitution;
			
			StringBuffer anyText = new StringBuffer("");
			anyText.append("ANY ");
			
			StringBuffer identifiers = new StringBuffer("");
			int idCount = 1;
			
			for (PExpression expression : any.getIdentifiers()) {
				MyExpression myExpression = MyExpressionFactory.convertExpression(expression);
				identifiers.append(myExpression.toString());
				if(idCount < any.getIdentifiers().size()) {
					identifiers.append(", ");
				}
				idCount++;
			}
			
			anyText.append(identifiers.toString() + "\n");
			anyText.append("WHERE ");
			anyText.append(MyPredicateFactory.convertPredicate(any.getWhere()).toString() + "\n");
			anyText.append("THEN ");
			anyText.append(convertToString(any.getThen()) + "\n");
			anyText.append("END\n");
			
			return anyText.toString();
		} else if (substitution instanceof AParallelSubstitution) {
			AParallelSubstitution parallel = (AParallelSubstitution) substitution;
			StringBuffer parallelText = new StringBuffer("");
			
			int subsCount = 1;
			
			for (PSubstitution subs : parallel.getSubstitutions()) {
				parallelText.append(convertToString(subs));
				if (subsCount < parallel.getSubstitutions().size()) {
					parallelText.append(" ||\n");
					subsCount++;
				}
			}
			
			return parallelText.toString();
		}
		
		return null;
	}
	
}
