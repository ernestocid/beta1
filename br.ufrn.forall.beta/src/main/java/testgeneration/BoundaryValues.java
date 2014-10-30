package testgeneration;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.expressions.MyAIntervalExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.AIntervalExpression;
import de.be4.classicalb.core.parser.node.AMemberPredicate;
import de.be4.classicalb.core.parser.node.ANat1SetExpression;
import de.be4.classicalb.core.parser.node.ANatSetExpression;

public class BoundaryValues extends EquivalenceClasses {

	private static BoundaryValues instance = null;



	protected BoundaryValues() {
		// avoids instantiation
	}



	public static BoundaryValues getInstance() {
		if (instance == null) {
			instance = new BoundaryValues();
		}

		return instance;
	}



	@Override
	protected Set<Block> createBlocksFor(AMemberPredicate predicateNode) {
		if (predicateNode.getRight() instanceof AIntervalExpression) {
			return createBlocksForBelongsToInterval(predicateNode, (AIntervalExpression) predicateNode.getRight());
		} else if (predicateNode.getRight() instanceof ANatSetExpression) {
			return createIntervalBlocksForBelongsToNAT(predicateNode, (ANatSetExpression) predicateNode.getRight());
		} else if (predicateNode.getRight() instanceof ANat1SetExpression) {
			return createIntervalBlocksForBelongsToNAT1(predicateNode, (ANat1SetExpression) predicateNode.getRight());
		}

		return createRegularBlocksFor(predicateNode);
	}



	private Set<Block> createIntervalBlocksForBelongsToNAT(AMemberPredicate predicateNode, ANatSetExpression right) {
		Set<Block> blocks = new HashSet<Block>();

		String varName = MyExpressionFactory.convertExpression(predicateNode.getLeft()).toString();

		String block1 = varName + " = -1";
		String block2 = varName + " = 0";
		String block3 = varName + " = 1";
		String block4 = varName + " = MAXINT-1";
		String block5 = varName + " = MAXINT";
		String block6 = varName + " = MAXINT+1";

		blocks.add(new Block(block1, true));
		blocks.add(new Block(block2, false));
		blocks.add(new Block(block3, false));
		blocks.add(new Block(block4, false));
		blocks.add(new Block(block5, false));
		blocks.add(new Block(block6, true));

		return blocks;
	}



	private Set<Block> createIntervalBlocksForBelongsToNAT1(AMemberPredicate predicateNode, ANat1SetExpression right) {
		Set<Block> blocks = new HashSet<Block>();

		String varName = MyExpressionFactory.convertExpression(predicateNode.getLeft()).toString();

		String block1 = varName + " = 0";
		String block2 = varName + " = 1";
		String block3 = varName + " = 2";
		String block4 = varName + " = MAXINT-1";
		String block5 = varName + " = MAXINT";
		String block6 = varName + " = MAXINT+1";

		blocks.add(new Block(block1, true));
		blocks.add(new Block(block2, false));
		blocks.add(new Block(block3, false));
		blocks.add(new Block(block4, false));
		blocks.add(new Block(block5, false));
		blocks.add(new Block(block6, true));

		return blocks;
	}



	@Override
	protected Set<Block> createBlocksForBelongsToInterval(AMemberPredicate predicateNode, AIntervalExpression interval) {
		Set<Block> blocks = new HashSet<Block>();

		MyAIntervalExpression myRange = (MyAIntervalExpression) MyExpressionFactory.convertExpression(interval);

		String varName = MyExpressionFactory.convertExpression(predicateNode.getLeft()).toString();

		String leftExp = myRange.getLeftExpression().toString();
		String rightExp = myRange.getRightExpression().toString();

		String b1 = varName + " = " + leftExp + "-1";
		String b2 = varName + " = " + leftExp;
		String b3 = varName + " = " + leftExp + "+1";

		String b4 = varName + " = " + rightExp + "-1";
		String b5 = varName + " = " + rightExp;
		String b6 = varName + " = " + rightExp + "+1";

		blocks.add(new Block(b1, true));
		blocks.add(new Block(b2, false));
		blocks.add(new Block(b3, false));

		blocks.add(new Block(b4, false));
		blocks.add(new Block(b5, false));
		blocks.add(new Block(b6, true));

		return blocks;
	}

}
