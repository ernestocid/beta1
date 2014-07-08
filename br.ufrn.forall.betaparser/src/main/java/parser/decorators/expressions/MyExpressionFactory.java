package parser.decorators.expressions;

import de.be4.classicalb.core.parser.node.AAddExpression;
import de.be4.classicalb.core.parser.node.ABoolSetExpression;
import de.be4.classicalb.core.parser.node.ABooleanFalseExpression;
import de.be4.classicalb.core.parser.node.ABooleanTrueExpression;
import de.be4.classicalb.core.parser.node.ACardExpression;
import de.be4.classicalb.core.parser.node.ACompositionExpression;
import de.be4.classicalb.core.parser.node.AComprehensionSetExpression;
import de.be4.classicalb.core.parser.node.ACoupleExpression;
import de.be4.classicalb.core.parser.node.ADefinitionExpression;
import de.be4.classicalb.core.parser.node.ADivExpression;
import de.be4.classicalb.core.parser.node.ADomainExpression;
import de.be4.classicalb.core.parser.node.ADomainRestrictionExpression;
import de.be4.classicalb.core.parser.node.ADomainSubtractionExpression;
import de.be4.classicalb.core.parser.node.AEmptySequenceExpression;
import de.be4.classicalb.core.parser.node.AEmptySetExpression;
import de.be4.classicalb.core.parser.node.AFinSubsetExpression;
import de.be4.classicalb.core.parser.node.AFirstExpression;
import de.be4.classicalb.core.parser.node.AFirstProjectionExpression;
import de.be4.classicalb.core.parser.node.AFrontExpression;
import de.be4.classicalb.core.parser.node.AFunctionExpression;
import de.be4.classicalb.core.parser.node.AGeneralUnionExpression;
import de.be4.classicalb.core.parser.node.AIdentifierExpression;
import de.be4.classicalb.core.parser.node.AImageExpression;
import de.be4.classicalb.core.parser.node.AIntSetExpression;
import de.be4.classicalb.core.parser.node.AIntegerExpression;
import de.be4.classicalb.core.parser.node.AIntegerSetExpression;
import de.be4.classicalb.core.parser.node.AIntersectionExpression;
import de.be4.classicalb.core.parser.node.AIntervalExpression;
import de.be4.classicalb.core.parser.node.AIseqExpression;
import de.be4.classicalb.core.parser.node.ALambdaExpression;
import de.be4.classicalb.core.parser.node.ALastExpression;
import de.be4.classicalb.core.parser.node.AMaxExpression;
import de.be4.classicalb.core.parser.node.AMaxIntExpression;
import de.be4.classicalb.core.parser.node.AMinExpression;
import de.be4.classicalb.core.parser.node.AMinIntExpression;
import de.be4.classicalb.core.parser.node.AMinusOrSetSubtractExpression;
import de.be4.classicalb.core.parser.node.AModuloExpression;
import de.be4.classicalb.core.parser.node.AMultOrCartExpression;
import de.be4.classicalb.core.parser.node.ANat1SetExpression;
import de.be4.classicalb.core.parser.node.ANatSetExpression;
import de.be4.classicalb.core.parser.node.ANatural1SetExpression;
import de.be4.classicalb.core.parser.node.ANaturalSetExpression;
import de.be4.classicalb.core.parser.node.AOverwriteExpression;
import de.be4.classicalb.core.parser.node.APartialBijectionExpression;
import de.be4.classicalb.core.parser.node.APartialFunctionExpression;
import de.be4.classicalb.core.parser.node.APartialInjectionExpression;
import de.be4.classicalb.core.parser.node.APowSubsetExpression;
import de.be4.classicalb.core.parser.node.ARangeExpression;
import de.be4.classicalb.core.parser.node.ARangeRestrictionExpression;
import de.be4.classicalb.core.parser.node.ARangeSubtractionExpression;
import de.be4.classicalb.core.parser.node.ARelationsExpression;
import de.be4.classicalb.core.parser.node.ARevExpression;
import de.be4.classicalb.core.parser.node.AReverseExpression;
import de.be4.classicalb.core.parser.node.ASecondProjectionExpression;
import de.be4.classicalb.core.parser.node.ASeqExpression;
import de.be4.classicalb.core.parser.node.ASetExtensionExpression;
import de.be4.classicalb.core.parser.node.ASizeExpression;
import de.be4.classicalb.core.parser.node.AStringSetExpression;
import de.be4.classicalb.core.parser.node.ATailExpression;
import de.be4.classicalb.core.parser.node.ATotalBijectionExpression;
import de.be4.classicalb.core.parser.node.ATotalFunctionExpression;
import de.be4.classicalb.core.parser.node.ATotalInjectionExpression;
import de.be4.classicalb.core.parser.node.ATotalSurjectionExpression;
import de.be4.classicalb.core.parser.node.AUnaryMinusExpression;
import de.be4.classicalb.core.parser.node.AUnionExpression;
import de.be4.classicalb.core.parser.node.PExpression;

public class MyExpressionFactory {

	public static MyExpression convertExpression(PExpression expression) {
		if (expression instanceof AIdentifierExpression) {
			return new MyAIdentifierExpression((AIdentifierExpression) expression);
		} else if (expression instanceof AIntSetExpression) {
			return new MyAIntSetExpression((AIntSetExpression) expression);
		} else if (expression instanceof AStringSetExpression) {
			return new MyAStringSetExpression((AStringSetExpression) expression);
		} else if (expression instanceof ABoolSetExpression) {
			return new MyABoolSetExpression((ABoolSetExpression) expression);
		} else if (expression instanceof ANatSetExpression) {
			return new MyANatSetExpression((ANatSetExpression) expression);
		} else if (expression instanceof ANat1SetExpression) {
			return new MyANat1SetExpression((ANat1SetExpression) expression);
		} else if (expression instanceof AIntegerExpression) {
			return new MyAIntegerExpression((AIntegerExpression) expression);
		} else if (expression instanceof AMaxIntExpression) {
			return new MyAMaxIntExpression((AMaxIntExpression) expression);
		} else if (expression instanceof AMinIntExpression) {
			return new MyAMinIntExpression((AMinIntExpression) expression);			
		} else if (expression instanceof AAddExpression) {
			return new MyAAddExpression((AAddExpression) expression);
		} else if (expression instanceof AFunctionExpression) {
			return new MyAFunctionExpression((AFunctionExpression) expression);
		} else if (expression instanceof ATotalFunctionExpression) {
			return new MyATotalFunctionExpression((ATotalFunctionExpression) expression);
		} else if (expression instanceof APartialInjectionExpression) {
			return new MyAPartialInjectionExpression((APartialInjectionExpression) expression);
		} else if (expression instanceof AFinSubsetExpression) {
			return new MyAFinSubsetExpression((AFinSubsetExpression) expression);
		} else if (expression instanceof AEmptySetExpression) {
			return new MyAEmptySetExpression((AEmptySetExpression) expression);
		} else if (expression instanceof AIntersectionExpression) {
			return new MyAIntersectionExpression((AIntersectionExpression) expression);
		} else if (expression instanceof AUnionExpression) {
			return new MyAUnionExpression((AUnionExpression) expression);
		} else if (expression instanceof ASetExtensionExpression) {
			return new MyASetExtensionExpression((ASetExtensionExpression) expression);
		} else if (expression instanceof ARangeExpression) {
			return new MyARangeExpression((ARangeExpression) expression);
		} else if (expression instanceof ADomainExpression) {
			return new MyADomainExpression((ADomainExpression) expression);
		} else if (expression instanceof AImageExpression) {
			return new MyAImageExpression((AImageExpression) expression);
		} else if (expression instanceof AReverseExpression) {
			return new MyAReverseExpression((AReverseExpression) expression);
		} else if (expression instanceof ACardExpression) {
			return new MyACardExpression((ACardExpression) expression);
		} else if (expression instanceof APowSubsetExpression) {
			return new MyAPowSubsetExpression((APowSubsetExpression) expression);
		} else if (expression instanceof AMinusOrSetSubtractExpression) {
			return new MyAMinusOrSetSubtractExpression((AMinusOrSetSubtractExpression) expression);
		} else if (expression instanceof AIntervalExpression) {
			return new MyAIntervalExpression((AIntervalExpression) expression);
		} else if (expression instanceof AMultOrCartExpression) {
			return new MyAMultOrCartExpression((AMultOrCartExpression) expression);
		} else if (expression instanceof ALambdaExpression) {
			return new MyALambdaExpression((ALambdaExpression) expression);
		} else if (expression instanceof APartialFunctionExpression) {
			return new MyAPartialFunctionExpression((APartialFunctionExpression) expression);
		} else if (expression instanceof ABooleanTrueExpression) {
			return new MyABooleanTrueExpression((ABooleanTrueExpression) expression);
		} else if (expression instanceof ABooleanFalseExpression) {
			return new MyABooleanFalseExpression((ABooleanFalseExpression) expression);
		} else if (expression instanceof ATotalSurjectionExpression) {
			return new MyATotalSurjectionExpression((ATotalSurjectionExpression) expression);
		} else if (expression instanceof ATotalInjectionExpression) {
			return new MyATotalInjectionExpression((ATotalInjectionExpression) expression);
		} else if (expression instanceof APartialBijectionExpression) {
			return new MyAPartialBijectionExpression((APartialBijectionExpression) expression);
		} else if (expression instanceof ATotalBijectionExpression) {
			return new MyATotalBijectionExpression((ATotalBijectionExpression) expression);
		} else if (expression instanceof ARelationsExpression) {
			return new MyARelationsExpression((ARelationsExpression) expression);
		} else if (expression instanceof ADomainSubtractionExpression) {
			return new MyADomainSubtractionExpression((ADomainSubtractionExpression) expression);
		} else if (expression instanceof ARangeSubtractionExpression) {
			return new MyARangeSubtractionExpression((ARangeSubtractionExpression) expression);
		} else if (expression instanceof ADomainRestrictionExpression) {
			return new MyADomainRestrictionExpression((ADomainRestrictionExpression) expression);
		} else if (expression instanceof ARangeRestrictionExpression) {
			return new MyARangeRestrictionExpression((ARangeRestrictionExpression) expression);
		} else if (expression instanceof ACoupleExpression) {
			return new MyACoupleExpression((ACoupleExpression) expression);
		} else if (expression instanceof AOverwriteExpression) {
			return new MyAOverwriteExpression((AOverwriteExpression) expression);
		} else if (expression instanceof AUnaryMinusExpression) {
			return new MyAUnaryMinusExpression((AUnaryMinusExpression) expression);
		} else if (expression instanceof ADefinitionExpression) {
			return new MyADefinitionExpression((ADefinitionExpression) expression);
		} else if (expression instanceof ASeqExpression) {
			return new MyASeqExpression((ASeqExpression) expression);
		} else if (expression instanceof ASizeExpression) {
			return new MyASizeExpression((ASizeExpression) expression);
		} else if (expression instanceof AIntegerSetExpression) {
			return new MyAIntegerSetExpression((AIntegerSetExpression) expression);
		} else if (expression instanceof ANaturalSetExpression) {
			return new MyANaturalSetExpression((ANaturalSetExpression) expression);
		} else if (expression instanceof ANatural1SetExpression) {
			return new MyANatural1SetExpression((ANatural1SetExpression) expression);
		} else if (expression instanceof AMaxExpression) {
			return new MyAMaxExpression((AMaxExpression) expression);
		} else if (expression instanceof AMinExpression) {
			return new MyAMinExpression((AMinExpression) expression);
		} else if (expression instanceof AIseqExpression) {
			return new MyAIseqExpression((AIseqExpression) expression);
		} else if (expression instanceof AEmptySequenceExpression) {
			return new MyAEmptySequenceExpression((AEmptySequenceExpression) expression);
		} else if (expression instanceof AFirstExpression) {
			return new MyAFirstExpression((AFirstExpression) expression);
		} else if (expression instanceof AGeneralUnionExpression) {
			return new MyAGeneralUnionExpression((AGeneralUnionExpression) expression);
		} else if (expression instanceof AFirstProjectionExpression) {
			return new MyAFirstProjectionExpression((AFirstProjectionExpression) expression);
		} else if (expression instanceof ASecondProjectionExpression) {
			return new MyASecondProjectionExpression((ASecondProjectionExpression) expression);
		} else if (expression instanceof ACompositionExpression) {
			return new MyACompositionExpression((ACompositionExpression) expression);
		} else if (expression instanceof AComprehensionSetExpression) {
			return new MyAComprehensionSetExpression((AComprehensionSetExpression) expression);
		} else if (expression instanceof ALastExpression) {
			return new MyALastExpression((ALastExpression) expression);
		} else if (expression instanceof AFrontExpression) {
			return new MyAFrontExpression((AFrontExpression) expression);
		} else if (expression instanceof ATailExpression) {
			return new MyATailExpression((ATailExpression) expression);
		} else if (expression instanceof ARevExpression) {
			return new MyARevExpression((ARevExpression) expression);
		} else if (expression instanceof ADivExpression) {
			return new MyADivExpression((ADivExpression) expression);
		} else if (expression instanceof AModuloExpression) {
			return new MyAModuloExpression((AModuloExpression) expression);
		} else {
			System.out.println("No decorator for: " + expression.getClass().getName());
			return null;
		}
	}
	
}
