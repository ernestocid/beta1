package testgeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.Characteristic;
import parser.CharacteristicType;
import parser.PredicateCharacteristic;
import parser.decorators.expressions.MyAIntSetExpression;
import parser.decorators.expressions.MyAIntervalExpression;
import parser.decorators.expressions.MyANat1SetExpression;
import parser.decorators.expressions.MyANatSetExpression;
import parser.decorators.predicates.MyAMemberPredicate;
import parser.decorators.predicates.MyASubsetPredicate;
import parser.decorators.predicates.MyASubsetStrictPredicate;

// TODO: Still needs to be refactored in some parts
public class BVBlockBuilder extends BlockBuilder {

	
	public BVBlockBuilder(Partitioner partitioner) {
		super(partitioner);
		super.setBlocks(generateBlocks());
	}

	
	
	private Map<Characteristic, List<Block>> generateBlocks() {
		Map<Characteristic, List<Block>> blocks = new HashMap<Characteristic, List<Block>>();
		
		addBlocksForCommonCharacteristics(blocks);
		addBlocksForConditionalCharacteristics(blocks);
		
		return blocks;
	}



	private void addBlocksForConditionalCharacteristics(Map<Characteristic, List<Block>> blocks) {
		for (Characteristic conditionalCharacteristic : getPartitioner().getOperation().getConditionalCharacteristics()) {
			addBasicPositiveAndNegativeBlockPair(blocks, conditionalCharacteristic);
		}
	}



	private void addBasicPositiveAndNegativeBlockPair(Map<Characteristic, List<Block>> blocks, Characteristic characteristic) {
		List<Block> chBlocks = new ArrayList<Block>();

		chBlocks.add(new Block(characteristic.toString(), false));
		chBlocks.add(new Block(negateClause(characteristic.toString()), true));
		
		blocks.put(characteristic, chBlocks);
	}



	private void addBlocksForCommonCharacteristics(Map<Characteristic, List<Block>> blocks) {
		for(Characteristic characterisitic : getPartitioner().getGeneralCharacteristicsClauses()) {
			if(characterisitic.isIntervalCharacteristic() && characterisitic.getType() != CharacteristicType.INVARIANT) {
				if(characterisitic.isTypingCharacteristic()) {
					addIntervalBlocksForTypingCharacteristic(blocks, characterisitic);
				} else {
					addIntervalBlocksForNonTypingCharaceristic(blocks, characterisitic);
				}
			} else {
				addCommonBlock(blocks, characterisitic);
			}
		}
	}
	
	
	
	private void addIntervalBlocksForTypingCharacteristic(Map<Characteristic, List<Block>> blocks, Characteristic characteristic) {
		if(characteristic instanceof PredicateCharacteristic) {
			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
			
			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
				addIntervalBlocksForMemberOfCharacteristic(blocks, predicateCharacteristic);
			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
				addIntervalBlocksForSubsetCharacteristic(blocks, predicateCharacteristic);			
			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
				addIntervalBlocksForSubsetStrictCharacteristic(blocks, predicateCharacteristic);
			}
			
		}
		
	}



	private void addIntervalBlocksForSubsetStrictCharacteristic(Map<Characteristic, List<Block>> blocks, PredicateCharacteristic predicateCharacteristic) {
		List<Block> subsetStrictBlocks = new ArrayList<Block>();
		
		MyASubsetStrictPredicate subsetStrictCharacteristic = (MyASubsetStrictPredicate) predicateCharacteristic.getPredicate();
		
		if (subsetStrictCharacteristic.getRightExpression() instanceof MyANatSetExpression) {
			subsetStrictBlocks.add(new Block(subsetStrictCharacteristic.toString(), false));
			subsetStrictBlocks.add(new Block(negateClause(subsetStrictCharacteristic.toString()), true));
		} else if (subsetStrictCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
			subsetStrictBlocks.add(new Block(subsetStrictCharacteristic.toString(), false));
			subsetStrictBlocks.add(new Block(negateClause(subsetStrictCharacteristic.toString()), true));
		} else if (subsetStrictCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
			subsetStrictBlocks.add(new Block(subsetStrictCharacteristic.toString(), false));
		}
		
		blocks.put(predicateCharacteristic, subsetStrictBlocks);
	}



	private void addIntervalBlocksForSubsetCharacteristic(Map<Characteristic, List<Block>> blocks, PredicateCharacteristic predicateCharacteristic) {
		List<Block> subsetBlocks = new ArrayList<Block>();
		
		MyASubsetPredicate subsetCharacteristic = (MyASubsetPredicate) predicateCharacteristic.getPredicate();
		
		if (subsetCharacteristic.getRightExpression() instanceof MyANatSetExpression) {
			subsetBlocks.add(new Block(subsetCharacteristic.toString(), false));
			subsetBlocks.add(new Block(negateClause(subsetCharacteristic.toString()), true));
		} else if (subsetCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
			subsetBlocks.add(new Block(subsetCharacteristic.toString(), false));
			subsetBlocks.add(new Block(negateClause(subsetCharacteristic.toString()), true));
		} else if (subsetCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
			subsetBlocks.add(new Block(subsetCharacteristic.toString(), false));
		}
		
		blocks.put(predicateCharacteristic, subsetBlocks);
	}



	private void addIntervalBlocksForMemberOfCharacteristic(Map<Characteristic, List<Block>> blocks, PredicateCharacteristic predicateCharacteristic) {
		List<Block> memberOfBlocks = new ArrayList<Block>();
		
		MyAMemberPredicate memberOfCharacteristic = (MyAMemberPredicate) predicateCharacteristic.getPredicate();
		
		String id = memberOfCharacteristic.getLeftExpression().toString();
		
		if (memberOfCharacteristic.getRightExpression() instanceof MyANatSetExpression) {
			memberOfBlocks.add(new Block(id + " = -1", true));
			memberOfBlocks.add(new Block(id + " = 0", false));
			memberOfBlocks.add(new Block(id + " = 1", false));
			memberOfBlocks.add(new Block(id + " = MAXINT-1", false));
			memberOfBlocks.add(new Block(id + " = MAXINT", false));
			memberOfBlocks.add(new Block(id + " = MAXINT+1", true));
		} else if (memberOfCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
			memberOfBlocks.add(new Block(id + " = 0", true));
			memberOfBlocks.add(new Block(id + " = 1", false));
			memberOfBlocks.add(new Block(id + " = 2", false));
			memberOfBlocks.add(new Block(id + " = MAXINT-1", false));
			memberOfBlocks.add(new Block(id + " = MAXINT", false));
			memberOfBlocks.add(new Block(id + " = MAXINT+1", true));
		} else if (memberOfCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
			memberOfBlocks.add(new Block(memberOfCharacteristic.toString(), false));
		}
		
		blocks.put(predicateCharacteristic, memberOfBlocks);
	}
	
	
	
	private void addIntervalBlocksForNonTypingCharaceristic(Map<Characteristic, List<Block>> blocks, Characteristic characteristic) {
		List<Block> intervalBlocks = new ArrayList<Block>();
		
		if (characteristic instanceof PredicateCharacteristic) {
			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
			
			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
				addIntervalBlocksForNonTypingMemberCharacteristic(intervalBlocks, predicateCharacteristic);
			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
				addIntervalBlocksForNonTypingSubsetCharacteristic(intervalBlocks, predicateCharacteristic);
			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
				addIntervalBlocksForNonTypingSubsetStrictCharacteristic(intervalBlocks, predicateCharacteristic);
			}
			
			blocks.put(characteristic, intervalBlocks);
		}
		
	}



	private void addIntervalBlocksForNonTypingSubsetStrictCharacteristic(List<Block> intervalBlocks, PredicateCharacteristic predicateCharacteristic) {
		MyASubsetStrictPredicate includeStrictly = (MyASubsetStrictPredicate) predicateCharacteristic.getPredicate();

		if(includeStrictly.getRightExpression().isInterval() && !includeStrictly.getRightExpression().isBasicType()) {
			intervalBlocks.add(new Block(predicateCharacteristic.toString(), false));
			intervalBlocks.add(new Block(negateClause(predicateCharacteristic.toString()), true));
		} else if(includeStrictly.getRightExpression().isBasicType()) {
			intervalBlocks.add(new Block(predicateCharacteristic.toString(), false));
		}
	}



	private void addIntervalBlocksForNonTypingSubsetCharacteristic(List<Block> intervalBlocks, PredicateCharacteristic predicateCharacteristic) {
		MyASubsetPredicate include = (MyASubsetPredicate) predicateCharacteristic.getPredicate();
		
		if(include.getRightExpression().isInterval() && !include.getRightExpression().isBasicType()) {
			intervalBlocks.add(new Block(predicateCharacteristic.toString(), false));
			intervalBlocks.add(new Block(negateClause(predicateCharacteristic.toString()), true));
		} else if(include.getRightExpression().isInterval() && include.getRightExpression().isBasicType()) {
			intervalBlocks.add(new Block(predicateCharacteristic.toString(), false));
		}
	}



	private void addIntervalBlocksForNonTypingMemberCharacteristic(List<Block> intervalBlocks, PredicateCharacteristic predicateCharacteristic) {
		MyAMemberPredicate belong = (MyAMemberPredicate) predicateCharacteristic.getPredicate();

		if(belong.getRightExpression().isInterval()) {
			String leftTerm = belong.getLeftExpression().toString();
			boolean isExplicitInterval = belong.getRightExpression() instanceof MyAIntervalExpression;
			boolean isImplicitInterval = belong.getRightExpression() instanceof MyAIntSetExpression || 
										 belong.getRightExpression() instanceof MyANatSetExpression ||
										 belong.getRightExpression() instanceof MyANat1SetExpression;
			
			if(isExplicitInterval) {
				MyAIntervalExpression interval = (MyAIntervalExpression) belong.getRightExpression();
				
				String leftBorder = interval.getLeftExpression().toString();
				String rightBorder = interval.getRightExpression().toString();
				
				intervalBlocks.add(new Block(leftTerm + " = " + (leftBorder + " - 1"), true)); // Inferior Left Border
				intervalBlocks.add(new Block(leftTerm + " = " + leftBorder, false)); // In Left Border
				intervalBlocks.add(new Block(leftTerm + " = " + (leftBorder + " + 1"), false)); // Superior Left Border
				intervalBlocks.add(new Block(leftTerm + " = " + (rightBorder + " - 1"), false)); // Inferior Right Border
				intervalBlocks.add(new Block(leftTerm + " = " + rightBorder, false)); // In Right Border
				intervalBlocks.add(new Block(leftTerm + " = " + (rightBorder + " + 1"), true)); // Superior Right Border
			} else if(isImplicitInterval) {
				intervalBlocks.add(new Block(leftTerm + " = " + "MININT-1", false));
				intervalBlocks.add(new Block(leftTerm + " = " + "MININT", false));
				intervalBlocks.add(new Block(leftTerm + " = " + "MININT+1", false));
				intervalBlocks.add(new Block(leftTerm + " = " + "MAXINT-1", false));
				intervalBlocks.add(new Block(leftTerm + " = " + "MAXINT", false));
				intervalBlocks.add(new Block(leftTerm + " = " + "MAXINT+1", false));
			}
		}
	}
	
	
	
	private void addCommonBlock(Map<Characteristic, List<Block>> blocks, Characteristic characteristic) {
		List<Block> chBlocks = new ArrayList<Block>();
		
		chBlocks.add(new Block(characteristic.toString(), false));
		
		if(characteristic.getType() != CharacteristicType.INVARIANT) {
			if(!characteristic.isTypingCharacteristic()) {
				chBlocks.add(new Block(negateClause(characteristic.toString()), true));
			} else if(characteristic instanceof PredicateCharacteristic) {
				PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
				
				if(predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
					MyASubsetStrictPredicate strictSubset = (MyASubsetStrictPredicate) predicateCharacteristic.getPredicate();
					if(strictSubset.getRightExpression().isBasicType() && !strictSubset.getRightExpression().toString().equals("BOOL")) {
						chBlocks.add(new Block(negateClause(predicateCharacteristic.toString()), true));
					}
				}
			}
		}
		
		blocks.put(characteristic, chBlocks);
		
	}

}
