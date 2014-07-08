package parser.decorators.expressions;

import java.util.Set;

import de.be4.classicalb.core.parser.node.ABooleanFalseExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyABooleanFalseExpression extends MyExpressionDecorator {

	
		private ABooleanFalseExpression booleanFalseExpression;
		
		
		public MyABooleanFalseExpression(ABooleanFalseExpression booleanFalseExpression) {
			this.booleanFalseExpression = booleanFalseExpression;
		}



		@Override
		public PExpression getNode() {
			return this.booleanFalseExpression;
		}
		
		
		
		@Override
		public Set<String> getVariables() {
			return super.getVariables();
		}
		

		
		@Override
		public String toString() {
			return "FALSE";
		}
		
}
