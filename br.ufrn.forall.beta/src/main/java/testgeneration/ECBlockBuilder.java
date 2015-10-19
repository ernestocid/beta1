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
import parser.decorators.predicates.MyADisjunctPredicate;
import parser.decorators.predicates.MyAMemberPredicate;
import parser.decorators.predicates.MyASubsetPredicate;
import parser.decorators.predicates.MyASubsetStrictPredicate;
import parser.decorators.predicates.MyPredicate;

public class ECBlockBuilder extends BlockBuilder {

	
	public ECBlockBuilder(Partitioner partitioner) {
		super(partitioner);
		super.setBlocks(generateBlocks());
	}

	
	private Map<Characteristic, List<Block>> generateBlocks() {
		Map<Characteristic, List<Block>> blocks = new HashMap<Characteristic, List<Block>>();

		addGeneralCharacteristicsBlocks(blocks);
		addBlocksForConditionalCharacteristics(blocks);
		
		return blocks;
	}
	
	
	
	private void addBlocksForConditionalCharacteristics(Map<Characteristic, List<Block>> blocks) {
		for (Characteristic conditional : getPartitioner().getOperation().getConditionalCharacteristics()) {
			List<Block> chBlocks = new ArrayList<Block>();
			
			// Characteristics from CASE statements are treated differently. Each CASE condition will
			// be considered as a block and the whole case statement is a characteristic
			
			if(conditional.getType() == CharacteristicType.CONDITIONAL_CASE) {
				PredicateCharacteristic pc = (PredicateCharacteristic) conditional;

				if(pc.getPredicate() instanceof MyADisjunctPredicate) {
					MyADisjunctPredicate disjunct = (MyADisjunctPredicate) pc.getPredicate();
					
					for(MyPredicate caseCondition : disjunct.getClauses()) {
						chBlocks.add(new Block(caseCondition.toString(), false));
					}
				}
			} else {
				chBlocks.add(new Block(conditional.toString(), false));
				chBlocks.add(new Block(negateClause(conditional.toString()), true));				
			}
			
			blocks.put(conditional, chBlocks);
		}
	}
	
	
	
	private void addGeneralCharacteristicsBlocks(Map<Characteristic, List<Block>> blocks) {
		for (Characteristic characteristic : getPartitioner().getGeneralCharacteristicsClauses()) {
			if(characteristic.isIntervalCharacteristic() && characteristic.getType() != CharacteristicType.INVARIANT) {
				if(characteristic.isTypingCharacteristic()) {
					addIntervalBlocksForTyping(blocks, characteristic);
				} else {
					addIntervalBlockForNonTyping(blocks, characteristic);
				}
			} else {
				addCommonBlock(blocks, characteristic);
			}
		}
	}
	
	
	
	private void addCommonBlock(Map<Characteristic, List<Block>> blocks, Characteristic characteristic) {
		List<Block> chBlocks = new ArrayList<Block>();
		
		chBlocks.add(new Block(characteristic.toString(), false));
		
		if(characteristic.getType() != CharacteristicType.INVARIANT) {
			if(!characteristic.isTypingCharacteristic()) {
				chBlocks.add(new Block(negateClause(characteristic.toString()), true));
			} else if (characteristic instanceof PredicateCharacteristic) {
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
	
	
	
	private void addIntervalBlocksForTyping(Map<Characteristic, List<Block>> blocks, Characteristic characteristic) {
		blocks.put(characteristic, getIntervalBlocksForTyping(characteristic));
	}
	
	
	private List<Block> getIntervalBlocksForTyping(Characteristic characteristic) {
		List<Block> intervalBlocks = new ArrayList<Block>();
		
		if(characteristic instanceof PredicateCharacteristic) {
			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
			
			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
				MyAMemberPredicate memberOfCharacteristic = (MyAMemberPredicate) predicateCharacteristic.getPredicate();
				addIntervalBlocksForMemberOf(memberOfCharacteristic, intervalBlocks);
			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
				MyASubsetPredicate subsetCharacteristic = (MyASubsetPredicate) predicateCharacteristic.getPredicate();
				addIntervalBlocksForSubsetCharacteristic(subsetCharacteristic, intervalBlocks);
			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
				MyASubsetStrictPredicate subsetStrictCharacteristic = (MyASubsetStrictPredicate) predicateCharacteristic.getPredicate();
				addIntervalBlocksForSubsetStrictCharacteristic(subsetStrictCharacteristic, intervalBlocks);
			}
		}
		
		
		return intervalBlocks;
	}
	
	
	
	private void addIntervalBlocksForMemberOf(MyAMemberPredicate memberOfCharacteristic, List<Block> intervalBlocks) {
		if (memberOfCharacteristic.getRightExpression() instanceof MyANatSetExpression || memberOfCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
			intervalBlocks.add(new Block(memberOfCharacteristic.toString(), false));
			intervalBlocks.add(new Block(negateClause(memberOfCharacteristic.toString()), true));
		} else if (memberOfCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
			intervalBlocks.add(new Block(memberOfCharacteristic.toString(), false));
		}
	}
	
	
	
	private void addIntervalBlocksForSubsetCharacteristic(MyASubsetPredicate subsetCharacteristic, List<Block> intervalBlocks) {
		if (subsetCharacteristic.getRightExpression() instanceof MyANatSetExpression || 
				subsetCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
			intervalBlocks.add(new Block(subsetCharacteristic.toString(), false));
			intervalBlocks.add(new Block(negateClause(subsetCharacteristic.toString()), true));
		} else if (subsetCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
			intervalBlocks.add(new Block(subsetCharacteristic.toString(), false));
		}
	}
	
	
	
	private void addIntervalBlocksForSubsetStrictCharacteristic(MyASubsetStrictPredicate subsetStrictCharacteristic, List<Block> intervalBlocks) {
		if (subsetStrictCharacteristic.getRightExpression() instanceof MyANatSetExpression || subsetStrictCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
			intervalBlocks.add(new Block(subsetStrictCharacteristic.toString(), false));
			intervalBlocks.add(new Block(negateClause(subsetStrictCharacteristic.toString()), true));
		} else if (subsetStrictCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
			intervalBlocks.add(new Block(subsetStrictCharacteristic.toString(), false));
		}
	}
	
	
	
	private void addIntervalBlockForNonTyping(Map<Characteristic, List<Block>> blocks, Characteristic characteristic) {
		blocks.put(characteristic, getIntervalBlocks(characteristic));
	}
	
	
	
	private List<Block> getIntervalBlocks(Characteristic characteristic) {
		List<Block> intervalBlocks = new ArrayList<Block>();
		
		if(characteristic instanceof PredicateCharacteristic) {
			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
			
			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
				addIntervalBlocksToBelongs(predicateCharacteristic.getPredicate(), intervalBlocks);
			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
				addIntervalBlocksToIncludes(predicateCharacteristic.getPredicate(), intervalBlocks);
			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
				addIntervalBlocksToIncludesStrictly(predicateCharacteristic.getPredicate(), intervalBlocks);
			}
		}
		
		
		return intervalBlocks;
	}
	
	
	
	private void addIntervalBlocksToBelongs(MyPredicate characteristic, List<Block> intervalBlocks) {
		MyAMemberPredicate belong = (MyAMemberPredicate) characteristic;
		
		if(belong.getRightExpression().isInterval()) {
			String leftTerm = belong.getLeftExpression().toString();
			
			boolean isExplicitInterval = belong.getRightExpression() instanceof MyAIntervalExpression;
			boolean isImplicitInterval = (belong.getRightExpression() instanceof MyAIntSetExpression) || (belong.getRightExpression() instanceof MyANatSetExpression);
			
			if(isExplicitInterval) {
				MyAIntervalExpression interval = (MyAIntervalExpression) belong.getRightExpression();
				buildIntervals(intervalBlocks, leftTerm, " : ", interval);
			} else if(isImplicitInterval) {
				intervalBlocks.add(new Block(characteristic.toString(), false));			
			}
			
		}
	}
	
	
	// TODO: needs to be checked
	private void buildIntervals(List<Block> intervalBlocks, String leftTerm, String predicateSymbol, MyAIntervalExpression interval) {
		String leftBorder = interval.getLeftExpression().toString();
		String rightBorder = interval.getRightExpression().toString();
		
		intervalBlocks.add(new Block(leftTerm + predicateSymbol + "MININT" + ".." + (leftBorder + " - 1"), true));
		intervalBlocks.add(new Block(leftTerm + predicateSymbol + (rightBorder + " + 1") + ".." + "MAXINT", true));
		intervalBlocks.add(new Block(leftTerm + predicateSymbol + leftBorder + ".." + rightBorder, false));
		
//		intervalBlocks.add(leftTerm + predicateSymbol + "MININT" + ".." + (leftBorder + " - 1"));
//		intervalBlocks.add(leftTerm + predicateSymbol + (rightBorder + " + 1") + ".." + "MAXINT");
//		intervalBlocks.add(leftTerm + predicateSymbol + leftBorder + ".." + rightBorder);
	}
	
	
	
	private void addIntervalBlocksToIncludes(MyPredicate characteristic, List<Block> intervalBlocks) {
		MyASubsetPredicate include = (MyASubsetPredicate) characteristic;
		
		if(include.getRightExpression().isInterval()) {
			intervalBlocks.add(new Block(characteristic.toString(), false));
			intervalBlocks.add(new Block(negateClause(characteristic.toString()), true));
		}
	}
	
	
	
	private void addIntervalBlocksToIncludesStrictly(MyPredicate characteristic, List<Block> intervalBlocks) {
		MyASubsetStrictPredicate includeStrictly = (MyASubsetStrictPredicate) characteristic;

		if(includeStrictly.getRightExpression().isInterval()) {
			intervalBlocks.add(new Block(characteristic.toString(), false));
			intervalBlocks.add(new Block(negateClause(characteristic.toString()), true));
		}
	}

}
