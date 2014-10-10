package testgeneration;

import java.util.HashSet;
import java.util.Set;

import parser.Characteristic;
import parser.CharacteristicType;
import parser.PredicateCharacteristic;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AEqualPredicate;
import de.be4.classicalb.core.parser.node.AGreaterEqualPredicate;
import de.be4.classicalb.core.parser.node.AGreaterPredicate;
import de.be4.classicalb.core.parser.node.ALessEqualPredicate;
import de.be4.classicalb.core.parser.node.ALessPredicate;
import de.be4.classicalb.core.parser.node.ANotEqualPredicate;
import de.be4.classicalb.core.parser.node.PPredicate;

public class EquivalenceClasses {

	public static Set<Block> findBlocks(Characteristic c) {
		Set<Block> blocks = new HashSet<Block>();
		
		
		if(c.getType() == CharacteristicType.PRE_CONDITION || c.getType() == CharacteristicType.CONDITIONAL) {
			if(c.isTypingCharacteristic()) {
				blocks.add(new Block(c.toString(), false));
			} else if (c instanceof PredicateCharacteristic && c.isRelationalCharacteristic()){
				PredicateCharacteristic characteristic = (PredicateCharacteristic) c;
				blocks.addAll(createBlocksForRelationalCharacteristic(characteristic));
			}
		} else if (c.getType() == CharacteristicType.INVARIANT) {
			
		} else {
			
		}
		
		return blocks;
	}

	
	
	private static Set<Block> createBlocksForRelationalCharacteristic(PredicateCharacteristic characteristic) {
		Set<Block> blocks = new HashSet<Block>();

		PPredicate relationalNode = characteristic.getPredicate().getNode();
		
		if(relationalNode instanceof AGreaterPredicate) {
			blocks.addAll(createBlocksFor((AGreaterPredicate) relationalNode));
		} else if (relationalNode instanceof AGreaterEqualPredicate) {
			blocks.addAll(createBlocksFor((AGreaterEqualPredicate) relationalNode));
		} else if (relationalNode instanceof ALessPredicate) {
			blocks.addAll(createBlocksFor((ALessPredicate) relationalNode));
		} else if (relationalNode instanceof ALessEqualPredicate) {
			blocks.addAll(createBlocksFor((ALessEqualPredicate) relationalNode));
		} else if (relationalNode instanceof AEqualPredicate) {
			blocks.addAll(createBlocksFor((AEqualPredicate) relationalNode));
		} else if (relationalNode instanceof ANotEqualPredicate) {
			blocks.addAll(createBlocksFor((ANotEqualPredicate) relationalNode));
		}
		
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

}
