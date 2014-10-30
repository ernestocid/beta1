package testgeneration;

import java.util.HashSet;
import java.util.Set;

import parser.Characteristic;
import parser.CharacteristicType;
import parser.Operation;
import parser.PredicateCharacteristic;
import parser.decorators.expressions.MyABoolSetExpression;
import parser.decorators.expressions.MyAIntSetExpression;
import parser.decorators.expressions.MyAIntervalExpression;
import parser.decorators.expressions.MyAPartialBijectionExpression;
import parser.decorators.expressions.MyAPartialFunctionExpression;
import parser.decorators.expressions.MyAPartialInjectionExpression;
import parser.decorators.expressions.MyAPartialSurjectionExpression;
import parser.decorators.expressions.MyAStringSetExpression;
import parser.decorators.expressions.MyATotalBijectionExpression;
import parser.decorators.expressions.MyATotalFunctionExpression;
import parser.decorators.expressions.MyATotalInjectionExpression;
import parser.decorators.expressions.MyATotalSurjectionExpression;
import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import parser.decorators.predicates.MyAExistsPredicate;
import parser.decorators.predicates.MyAMemberPredicate;
import parser.decorators.predicates.MyANotMemberPredicate;
import parser.decorators.predicates.MyANotSubsetPredicate;
import parser.decorators.predicates.MyANotSubsetStrictPredicate;
import parser.decorators.predicates.MyASubsetPredicate;
import parser.decorators.predicates.MyASubsetStrictPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import de.be4.classicalb.core.parser.node.AEqualPredicate;
import de.be4.classicalb.core.parser.node.AEquivalencePredicate;
import de.be4.classicalb.core.parser.node.AExistsPredicate;
import de.be4.classicalb.core.parser.node.AForallPredicate;
import de.be4.classicalb.core.parser.node.AGreaterEqualPredicate;
import de.be4.classicalb.core.parser.node.AGreaterPredicate;
import de.be4.classicalb.core.parser.node.AImplicationPredicate;
import de.be4.classicalb.core.parser.node.AIntervalExpression;
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

public class EquivalenceClasses {


	public static Set<Block> findBlocks(Characteristic characteristic, Operation operation) {
		Set<Block> blocks = new HashSet<Block>();

		if(comesFromPreconditionOrConditional(characteristic) && isTypingCharacteristic(characteristic, operation)) return getSinglePositiveBlock(characteristic);
		if(comesFromPreconditionOrConditional(characteristic) && !isTypingCharacteristic(characteristic, operation)) return getBlocks(characteristic);
		if(isInvariant(characteristic)) return getBlocksForInvariant(characteristic);
		if(isProperties(characteristic)) return getSinglePositiveBlock(characteristic);

		return blocks;
	}



	private static boolean isProperties(Characteristic characteristic) {
		return characteristic.getType() == CharacteristicType.PROPERTIES;
	}



	private static Set<Block> getBlocks(Characteristic characteristic) {
		Set<Block> blocks = new HashSet<Block>();

		if (characteristic instanceof PredicateCharacteristic) {
			PredicateCharacteristic predC = (PredicateCharacteristic) characteristic;
			blocks.addAll(createBlocks(predC));
		}

		return blocks;
	}



	private static Set<Block> getSinglePositiveBlock(Characteristic characteristic) {
		Set<Block> blocks = new HashSet<Block>();
		blocks.add(new Block(characteristic.toString(), false));
		return blocks;
	}



	private static boolean comesFromPreconditionOrConditional(Characteristic characteristic) {
		return characteristic.getType() == CharacteristicType.PRE_CONDITION || characteristic.getType() == CharacteristicType.CONDITIONAL;
	}



	private static boolean isInvariant(Characteristic characteristic) {
		return characteristic.getType() == CharacteristicType.INVARIANT;
	}



	private static Set<Block> getBlocksForInvariant(Characteristic characteristic) {
		return getSinglePositiveBlock(characteristic);
	}



	private static boolean isTypingCharacteristic(Characteristic characteristic, Operation operation) {
		if(characteristic instanceof PredicateCharacteristic) {
			PredicateCharacteristic predC = (PredicateCharacteristic) characteristic;

			if(predC.getPredicate() instanceof MyAMemberPredicate) {
				MyAMemberPredicate memberOf = (MyAMemberPredicate) predC.getPredicate();
				MyExpression rightExpression = memberOf.getRightExpression();
				
				if(rightExpression instanceof MyAIntSetExpression || 
						rightExpression instanceof MyABoolSetExpression ||
						isAbstractSet(rightExpression, operation) ||
						rightExpression instanceof MyATotalFunctionExpression ||
						rightExpression instanceof MyAPartialFunctionExpression ||
						rightExpression instanceof MyATotalInjectionExpression ||
						rightExpression instanceof MyATotalSurjectionExpression ||
						rightExpression instanceof MyAPartialInjectionExpression ||
						rightExpression instanceof MyATotalBijectionExpression ||
						rightExpression instanceof MyAPartialBijectionExpression ||
						rightExpression instanceof MyAPartialSurjectionExpression ||
						rightExpression instanceof MyAStringSetExpression) {
					
					return true;
				}
			}
		}
		return false;
	}



	private static boolean isAbstractSet(MyExpression rightExpression, Operation operation) {
		if(operation.getMachine().getSets() != null) {
			return operation.getMachine().getSets().getDeferredSets().contains(rightExpression.toString());
		}

		return false;
	}



	private static Set<Block> createBlocks(PredicateCharacteristic characteristic) {
		Set<Block> blocks = new HashSet<Block>();

		PPredicate predicateNode = characteristic.getPredicate().getNode();

		// Relational predicates

		if (predicateNode instanceof AGreaterPredicate) {
			blocks.addAll(createBlocksFor((AGreaterPredicate) predicateNode));
		} else if (predicateNode instanceof AGreaterEqualPredicate) {
			blocks.addAll(createBlocksFor((AGreaterEqualPredicate) predicateNode));
		} else if (predicateNode instanceof ALessPredicate) {
			blocks.addAll(createBlocksFor((ALessPredicate) predicateNode));
		} else if (predicateNode instanceof ALessEqualPredicate) {
			blocks.addAll(createBlocksFor((ALessEqualPredicate) predicateNode));
		} else if (predicateNode instanceof AEqualPredicate) {
			blocks.addAll(createBlocksFor((AEqualPredicate) predicateNode));
		} else if (predicateNode instanceof ANotEqualPredicate) {
			blocks.addAll(createBlocksFor((ANotEqualPredicate) predicateNode));
		} else if (predicateNode instanceof AImplicationPredicate) {
			blocks.addAll(createBlocksFor((AImplicationPredicate) predicateNode));
		} else if (predicateNode instanceof AEquivalencePredicate) {
			blocks.addAll(createBlocksFor((AEquivalencePredicate) predicateNode));
		} else if (predicateNode instanceof AForallPredicate) {
			blocks.addAll(createBlocksFor((AForallPredicate) predicateNode));
		} else if (predicateNode instanceof AMemberPredicate) {
			blocks.addAll(createBlocksFor((AMemberPredicate) predicateNode));
		} else if (predicateNode instanceof ANotMemberPredicate) {
			blocks.addAll(createBlocksFor((ANotMemberPredicate) predicateNode));
		} else if (predicateNode instanceof ASubsetPredicate) {
			blocks.addAll(createBlocksFor((ASubsetPredicate) predicateNode));
		} else if (predicateNode instanceof ANotSubsetPredicate) {
			blocks.addAll(createBlocks((ANotSubsetPredicate) predicateNode));
		} else if (predicateNode instanceof ASubsetStrictPredicate) {
			blocks.addAll(createBlocksFor((ASubsetStrictPredicate) predicateNode));
		} else if (predicateNode instanceof ANotSubsetStrictPredicate) {
			blocks.addAll(createBlocksFor((ANotSubsetStrictPredicate) predicateNode));
		} else if (predicateNode instanceof AExistsPredicate) {
			blocks.addAll(createBlocksFor((AExistsPredicate) predicateNode));
		}

		// Negation Predicate

		else if (predicateNode instanceof ANegationPredicate) {
			blocks.addAll(createBlocksFor((ANegationPredicate) predicateNode));
		}

		return blocks;
	}



	private static Set<Block> createBlocksFor(AExistsPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		MyAExistsPredicate exists = (MyAExistsPredicate) MyPredicateFactory.convertPredicate(predicateNode);
		String negExists = negateExistential(exists);

		blocks.add(new Block(exists.toString(), false));
		blocks.add(new Block(negExists, true));

		return blocks;
	}



	private static String negateExistential(MyAExistsPredicate exists) {
		StringBuffer negExists = new StringBuffer("");

		negExists.append(createQuantifiedVariablesList(exists));
		negExists.append(createNegationOfQuantifiedPredicate(exists));

		return negExists.toString();
	}



	private static String createNegationOfQuantifiedPredicate(MyAExistsPredicate exists) {
		return "(not(" + exists.getQuantifiedPredicate().toString() + "))";
	}



	private static String createQuantifiedVariablesList(MyAExistsPredicate exists) {
		StringBuffer quantifiedVariables = new StringBuffer("#(");

		int counter = 0;

		for (String var : exists.getQuantifiedVariables()) {
			if (counter < exists.getQuantifiedVariables().size() - 1) {
				quantifiedVariables.append(var + ",");
			} else {
				quantifiedVariables.append(var + ").");
			}
			counter++;
		}

		return quantifiedVariables.toString();
	}



	private static Set<Block> createBlocksFor(ANotSubsetStrictPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		MyANotSubsetStrictPredicate notSubsetStrictPredicate = (MyANotSubsetStrictPredicate) MyPredicateFactory.convertPredicate(predicateNode);

		String leftExpression = notSubsetStrictPredicate.getLeftExpression().toString();
		String rightExpression = notSubsetStrictPredicate.getRightExpression().toString();

		blocks.add(new Block(notSubsetStrictPredicate.toString(), false));
		blocks.add(new Block(leftExpression + " <<: " + rightExpression, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(ASubsetStrictPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		MyASubsetStrictPredicate subsetStrictPredicate = (MyASubsetStrictPredicate) MyPredicateFactory.convertPredicate(predicateNode);

		String leftExpression = subsetStrictPredicate.getLeftExpression().toString();
		String rightExpression = subsetStrictPredicate.getRightExpression().toString();

		blocks.add(new Block(subsetStrictPredicate.toString(), false));
		blocks.add(new Block(leftExpression + " /<<: " + rightExpression, true));

		return blocks;
	}



	private static Set<Block> createBlocks(ANotSubsetPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		MyANotSubsetPredicate notSubsetPredicate = (MyANotSubsetPredicate) MyPredicateFactory.convertPredicate(predicateNode);

		String leftExpression = notSubsetPredicate.getLeftExpression().toString();
		String rightExpression = notSubsetPredicate.getRightExpression().toString();

		blocks.add(new Block(notSubsetPredicate.toString(), false));
		blocks.add(new Block(leftExpression + " <: " + rightExpression, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(ASubsetPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		MyASubsetPredicate subsetPredicate = (MyASubsetPredicate) MyPredicateFactory.convertPredicate(predicateNode);

		String leftExpression = subsetPredicate.getLeftExpression().toString();
		String rightExpression = subsetPredicate.getRightExpression().toString();

		blocks.add(new Block(subsetPredicate.toString(), false));
		blocks.add(new Block(leftExpression + " /<: " + rightExpression, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(ANotMemberPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		MyANotMemberPredicate notMemberPredicate = (MyANotMemberPredicate) MyPredicateFactory.convertPredicate(predicateNode);

		String leftExpression = notMemberPredicate.getLeftExpression().toString();
		String rightExpression = notMemberPredicate.getRightExpression().toString();

		blocks.add(new Block(notMemberPredicate.toString(), false));
		blocks.add(new Block(leftExpression + " : " + rightExpression, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(AMemberPredicate predicateNode) {
		if(predicateNode.getRight() instanceof AIntervalExpression) {
			return createBlocksForBelongsToInterval(predicateNode, (AIntervalExpression) predicateNode.getRight());
		}

		return createRegularBlocksFor(predicateNode);
	}



	private static Set<Block> createBlocksForBelongsToInterval(AMemberPredicate predicateNode, AIntervalExpression interval) {
		Set<Block> blocks = new HashSet<Block>();
		
		MyAIntervalExpression myRange = (MyAIntervalExpression) MyExpressionFactory.convertExpression(interval);
		
		String varName = MyExpressionFactory.convertExpression(predicateNode.getLeft()).toString();
		
		String leftExp = myRange.getLeftExpression().toString();
		String rightExp = myRange.getRightExpression().toString();
		
		String insideInterval = varName + " : " + leftExp + ".." + rightExp;
		String beforeInterval = varName + " : " + "MININT" + ".." + "("+leftExp+"-1)";
		String afterInterval = varName + " : " + "(" + rightExp + "+1)" + ".." + "MAXINT";
		
		blocks.add(new Block(insideInterval, false));
		blocks.add(new Block(beforeInterval, true));
		blocks.add(new Block(afterInterval, true));
		
		return blocks;
	}



	private static Set<Block> createRegularBlocksFor(AMemberPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		MyAMemberPredicate memberPredicate = (MyAMemberPredicate) MyPredicateFactory.convertPredicate(predicateNode);

		String leftExpression = memberPredicate.getLeftExpression().toString();
		String rightExpression = memberPredicate.getRightExpression().toString();

		blocks.add(new Block(memberPredicate.toString(), false));
		blocks.add(new Block(leftExpression + " /: " + rightExpression, true));
		return blocks;
	}



	private static Set<Block> createBlocksFor(AForallPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		String forallPredicate = MyPredicateFactory.convertPredicate(predicateNode).toString();

		blocks.add(new Block(forallPredicate, false));
		blocks.add(new Block("not(" + forallPredicate + ")", true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(AEquivalencePredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		String equivalencePredicate = MyPredicateFactory.convertPredicate(predicateNode).toString();

		blocks.add(new Block(equivalencePredicate, false));
		blocks.add(new Block("not(" + equivalencePredicate + ")", true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(AImplicationPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		String implicationPredicate = MyPredicateFactory.convertPredicate(predicateNode).toString();

		blocks.add(new Block(implicationPredicate, false));
		blocks.add(new Block("not(" + implicationPredicate + ")", true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(ANotEqualPredicate equal) {
		Set<Block> blocks = new HashSet<Block>();

		String leftExp = MyExpressionFactory.convertExpression(equal.getLeft()).toString();
		String rightExp = MyExpressionFactory.convertExpression(equal.getRight()).toString();

		blocks.add(new Block(leftExp + " /= " + rightExp, false));
		blocks.add(new Block(leftExp + " = " + rightExp, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(AEqualPredicate equal) {
		Set<Block> blocks = new HashSet<Block>();

		String leftExp = MyExpressionFactory.convertExpression(equal.getLeft()).toString();
		String rightExp = MyExpressionFactory.convertExpression(equal.getRight()).toString();

		blocks.add(new Block(leftExp + " = " + rightExp, false));
		blocks.add(new Block(leftExp + " /= " + rightExp, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(ALessEqualPredicate lessOrEqual) {
		Set<Block> blocks = new HashSet<Block>();

		String leftExp = MyExpressionFactory.convertExpression(lessOrEqual.getLeft()).toString();
		String rightExp = MyExpressionFactory.convertExpression(lessOrEqual.getRight()).toString();

		blocks.add(new Block(leftExp + " <= " + rightExp, false));
		blocks.add(new Block(leftExp + " > " + rightExp, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(ALessPredicate less) {
		Set<Block> blocks = new HashSet<Block>();

		String leftExp = MyExpressionFactory.convertExpression(less.getLeft()).toString();
		String rightExp = MyExpressionFactory.convertExpression(less.getRight()).toString();

		blocks.add(new Block(leftExp + " < " + rightExp, false));
		blocks.add(new Block(leftExp + " > " + rightExp, true));
		blocks.add(new Block(leftExp + " = " + rightExp, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(AGreaterEqualPredicate greaterOrEqual) {
		Set<Block> blocks = new HashSet<Block>();

		String leftExp = MyExpressionFactory.convertExpression(greaterOrEqual.getLeft()).toString();
		String rightExp = MyExpressionFactory.convertExpression(greaterOrEqual.getRight()).toString();

		blocks.add(new Block(leftExp + " >= " + rightExp, false));
		blocks.add(new Block(leftExp + " < " + rightExp, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(AGreaterPredicate greater) {
		Set<Block> blocks = new HashSet<Block>();

		String leftExp = MyExpressionFactory.convertExpression(greater.getLeft()).toString();
		String rightExp = MyExpressionFactory.convertExpression(greater.getRight()).toString();

		blocks.add(new Block(leftExp + " > " + rightExp, false));
		blocks.add(new Block(leftExp + " < " + rightExp, true));
		blocks.add(new Block(leftExp + " = " + rightExp, true));

		return blocks;
	}



	private static Set<Block> createBlocksFor(ANegationPredicate predicateNode) {
		Set<Block> blocks = new HashSet<Block>();

		String predicate = MyPredicateFactory.convertPredicate(predicateNode).toString();
		String negatedPredicate = MyPredicateFactory.convertPredicate(predicateNode.getPredicate()).toString();

		blocks.add(new Block(predicate, false));
		blocks.add(new Block(negatedPredicate, true));

		return blocks;
	}

}
