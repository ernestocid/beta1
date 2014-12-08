package criteria;

import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import de.be4.classicalb.core.parser.node.APredicateParseUnit;
import de.be4.classicalb.core.parser.node.PParseUnit;
import de.prob.animator.domainobjects.ClassicalB;

public class TestApi4 {

	public static void main(String[] args) {
		
		String formulaForEvaluation = "xx > 10 & yy < xx";

		PParseUnit parseUnit = new ClassicalB(formulaForEvaluation).getAst().getPParseUnit();
		
		if(parseUnit instanceof APredicateParseUnit) {
			APredicateParseUnit predUnit = (APredicateParseUnit) parseUnit;
			MyPredicate myPredicate = MyPredicateFactory.convertPredicate(predUnit.getPredicate());
			
			System.out.println(myPredicate.toString());
			System.out.println(myPredicate.isInterval());
			System.out.println(myPredicate.getVariables());
			
		}
		
		
		// PPredicate
		
		
		
		
		
		

	}
}
