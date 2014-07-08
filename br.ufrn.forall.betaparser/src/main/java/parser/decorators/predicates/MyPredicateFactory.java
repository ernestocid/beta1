package parser.decorators.predicates;

import de.be4.classicalb.core.parser.node.AConjunctPredicate;
import de.be4.classicalb.core.parser.node.ADefinitionPredicate;
import de.be4.classicalb.core.parser.node.ADisjunctPredicate;
import de.be4.classicalb.core.parser.node.AEqualPredicate;
import de.be4.classicalb.core.parser.node.AEquivalencePredicate;
import de.be4.classicalb.core.parser.node.AExistsPredicate;
import de.be4.classicalb.core.parser.node.AForallPredicate;
import de.be4.classicalb.core.parser.node.AGreaterEqualPredicate;
import de.be4.classicalb.core.parser.node.AGreaterPredicate;
import de.be4.classicalb.core.parser.node.AImplicationPredicate;
import de.be4.classicalb.core.parser.node.ALessEqualPredicate;
import de.be4.classicalb.core.parser.node.ALessPredicate;
import de.be4.classicalb.core.parser.node.AMemberPredicate;
import de.be4.classicalb.core.parser.node.ANegationPredicate;
import de.be4.classicalb.core.parser.node.ANotEqualPredicate;
import de.be4.classicalb.core.parser.node.ANotMemberPredicate;
import de.be4.classicalb.core.parser.node.ANotSubsetPredicate;
import de.be4.classicalb.core.parser.node.ANotSubsetStrictPredicate;
import de.be4.classicalb.core.parser.node.ASubsetPredicate;
import de.be4.classicalb.core.parser.node.ASubsetStrictPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class MyPredicateFactory {

	public static MyPredicate convertPredicate(PPredicate predicate) {

		if (predicate instanceof AMemberPredicate) {
			return new MyAMemberPredicate((AMemberPredicate) predicate);
		} else if (predicate instanceof AConjunctPredicate) {
			return new MyAConjunctPredicate((AConjunctPredicate) predicate);
		} else if (predicate instanceof ADisjunctPredicate) {
			return new MyADisjunctPredicate((ADisjunctPredicate) predicate);
		} else if (predicate instanceof AEqualPredicate) {
			return new MyAEqualPredicate((AEqualPredicate) predicate);
		} else if (predicate instanceof AGreaterEqualPredicate) {
			return new MyAGreaterEqualPredicate((AGreaterEqualPredicate) predicate);
		} else if (predicate instanceof AGreaterPredicate) {
			return new MyAGreaterPredicate((AGreaterPredicate) predicate);
		} else if (predicate instanceof AImplicationPredicate) {
			return new MyAImplicationPredicate((AImplicationPredicate) predicate);
		} else if (predicate instanceof ASubsetPredicate) {
			return new MyASubsetPredicate((ASubsetPredicate) predicate);
		} else if (predicate instanceof ALessEqualPredicate) {
			return new MyALessEqualPredicate((ALessEqualPredicate) predicate);
		} else if (predicate instanceof ALessPredicate) {
			return new MyALessPredicate((ALessPredicate) predicate);
		} else if (predicate instanceof ANegationPredicate) {
			return new MyANegationPredicate((ANegationPredicate) predicate);
		} else if (predicate instanceof ANotMemberPredicate) {
			return new MyANotMemberPredicate((ANotMemberPredicate) predicate);
		} else if (predicate instanceof ANotEqualPredicate) {
			return new MyANotEqualPredicate((ANotEqualPredicate) predicate);
		} else if (predicate instanceof AForallPredicate) {
			return new MyAForallPredicate((AForallPredicate) predicate);
		} else if (predicate instanceof ASubsetStrictPredicate) {
			return new MyASubsetStrictPredicate((ASubsetStrictPredicate) predicate);
		} else if (predicate instanceof ANotSubsetPredicate) {
			return new MyANotSubsetPredicate((ANotSubsetPredicate) predicate);
		} else if (predicate instanceof ANotSubsetStrictPredicate) {
			return new MyANotSubsetStrictPredicate((ANotSubsetStrictPredicate) predicate);
		} else if (predicate instanceof AExistsPredicate) {
			return new MyAExistsPredicate((AExistsPredicate) predicate);
		} else if (predicate instanceof AEquivalencePredicate) {
			return new MyAEquivalencePredicate((AEquivalencePredicate) predicate);
		} else if (predicate instanceof ADefinitionPredicate) { 
			return new MyADefinitionPredicate((ADefinitionPredicate) predicate);
		} else {
			System.out.println("Couldnt find decorator for " + predicate.getClass().getName());
			return null;
		}
	}

}
