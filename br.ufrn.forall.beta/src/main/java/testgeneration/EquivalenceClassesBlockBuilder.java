//package main;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import parser.Characteristic;
//import parser.CharacteristicType;
//import parser.PredicateCharacteristic;
//import parser.decorators.expressions.MyAIntSetExpression;
//import parser.decorators.expressions.MyAIntervalExpression;
//import parser.decorators.expressions.MyANat1SetExpression;
//import parser.decorators.expressions.MyANatSetExpression;
//import parser.decorators.predicates.MyAMemberPredicate;
//import parser.decorators.predicates.MyASubsetPredicate;
//import parser.decorators.predicates.MyASubsetStrictPredicate;
//import parser.decorators.predicates.MyPredicate;
//
//public class EquivalenceClassesBlockBuilder extends BlockBuilder {
//
//	
////	public EquivalenceClassesBlockBuilder(Partitioner partitioner) {
////		super(partitioner);
////	}
////
////	
////	
////	public Map<String, List<String>> getBlocks() {
////		return arrangeEquivalenceClassesBlocks();
////	}
////	
////	
////	
////	public List<List<String>> getBlocksAsLists() {
////		return parseMapToList(getBlocks());
////	}
////	
////	
////	
////	private Map<String, List<String>> arrangeEquivalenceClassesBlocks() {
////		HashMap<String, List<String>> blocks = new HashMap<String, List<String>>();
////
////		addGeneralCharacteristicsBlocks(blocks);
////		addConditionalCharacteristicsBlocks(blocks);
////		
////		return blocks;
////	}
////	
////	
////	
////	private void addGeneralCharacteristicsBlocks(HashMap<String, List<String>> blocks) {
////		for (Characteristic characteristic : getPartitioner().getGeneralCharacteristicsClauses()) {
////			if(characteristic.isIntervalCharacteristic() && characteristic.getType() != CharacteristicType.INVARIANT) {
////				if(characteristic.isTypingCharacteristic()) {
////					addIntervalBlocksForTyping(blocks, characteristic);
////				} else {
////					addIntervalBlockForNonTyping(blocks, characteristic);
////				}
////			} else {
////				addCommonBlock(blocks, characteristic);
////			}
////		}
////	}
////	
////	
////	
////	private void addIntervalBlocksForTyping(Map<String, List<String>> blocks, Characteristic characteristic) {
////		blocks.put(characteristic.toString(), getIntervalBlocksForTyping(characteristic));
////	}
////	
////	
////	
////	private List<String> getIntervalBlocksForTyping(Characteristic characteristic) {
////		List<String> intervalBlocks = new ArrayList<String>();
////		
////		if(characteristic instanceof PredicateCharacteristic) {
////			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
////			
////			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
////				MyAMemberPredicate memberOfCharacteristic = (MyAMemberPredicate) predicateCharacteristic.getPredicate();
////				addIntervalBlocksForMemberOf(memberOfCharacteristic, intervalBlocks);
////			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
////				MyASubsetPredicate subsetCharacteristic = (MyASubsetPredicate) predicateCharacteristic.getPredicate();
////				addIntervalBlocksForSubsetCharacteristic(subsetCharacteristic, intervalBlocks);
////			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
////				MyASubsetStrictPredicate subsetStrictCharacteristic = (MyASubsetStrictPredicate) predicateCharacteristic.getPredicate();
////				addIntervalBlocksForSubsetStrictCharacteristic(subsetStrictCharacteristic, intervalBlocks);
////			}
////		}
////		
////		
////		return intervalBlocks;
////	}
////	
////	
////	
////	private void addIntervalBlocksForMemberOf(MyAMemberPredicate memberOfCharacteristic, List<String> intervalBlocks) {
////		if (memberOfCharacteristic.getRightExpression() instanceof MyANatSetExpression || memberOfCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
////			intervalBlocks.add(memberOfCharacteristic.toString());
////			intervalBlocks.add(negateClause(memberOfCharacteristic.toString()));
////		} else if (memberOfCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
////			intervalBlocks.add(memberOfCharacteristic.toString());
////		}
////	}
////	
////	
////	
////	private void addIntervalBlocksForSubsetCharacteristic(MyASubsetPredicate subsetCharacteristic, List<String> intervalBlocks) {
////		if (subsetCharacteristic.getRightExpression() instanceof MyANatSetExpression || 
////				subsetCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
////			intervalBlocks.add(subsetCharacteristic.toString());
////			intervalBlocks.add(negateClause(subsetCharacteristic.toString()));
////		} else if (subsetCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
////			intervalBlocks.add(subsetCharacteristic.toString());
////		}
////	}
////	
////	
////	
////	private void addIntervalBlocksForSubsetStrictCharacteristic(MyASubsetStrictPredicate subsetStrictCharacteristic, List<String> intervalBlocks) {
////		if (subsetStrictCharacteristic.getRightExpression() instanceof MyANatSetExpression || subsetStrictCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
////			intervalBlocks.add(subsetStrictCharacteristic.toString());
////			intervalBlocks.add(negateClause(subsetStrictCharacteristic.toString()));
////		} else if (subsetStrictCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
////			intervalBlocks.add(subsetStrictCharacteristic.toString());
////		}
////	}
////	
////	
////	
////	private void addIntervalBlockForNonTyping(HashMap<String, List<String>> blocks, Characteristic characteristic) {
////		blocks.put(characteristic.toString(), getIntervalBlocks(characteristic));
////	}
////	
////	
////	
////	private List<String> getIntervalBlocks(Characteristic characteristic) {
////		List<String> intervalBlocks = new ArrayList<String>();
////		
////		if(characteristic instanceof PredicateCharacteristic) {
////			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
////			
////			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
////				addIntervalBlocksToBelongs(predicateCharacteristic.getPredicate(), intervalBlocks);
////			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
////				addIntervalBlocksToIncludes(predicateCharacteristic.getPredicate(), intervalBlocks);
////			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
////				addIntervalBlocksToIncludesStrictly(predicateCharacteristic.getPredicate(), intervalBlocks);
////			}
////		}
////		
////		
////		return intervalBlocks;
////	}
////	
////	
////	
////	private void addIntervalBlocksToBelongs(MyPredicate characteristic, List<String> intervalBlocks) {
////		MyAMemberPredicate belong = (MyAMemberPredicate) characteristic;
////		
////		if(belong.getRightExpression().isInterval()) {
////			String leftTerm = belong.getLeftExpression().toString();
////			
////			boolean isExplicitInterval = belong.getRightExpression() instanceof MyAIntervalExpression;
////			boolean isImplicitInterval = (belong.getRightExpression() instanceof MyAIntSetExpression) || (belong.getRightExpression() instanceof MyANatSetExpression);
////			
////			if(isExplicitInterval) {
////				MyAIntervalExpression interval = (MyAIntervalExpression) belong.getRightExpression();
////				buildIntervals(intervalBlocks, leftTerm, " : ", interval);
////			} else if(isImplicitInterval) {
////				intervalBlocks.add(characteristic.toString());			
////			}
////			
////		}
////	}
////	
////	
////	
////	private void addIntervalBlocksToIncludes(MyPredicate characteristic, List<String> intervalBlocks) {
////		MyASubsetPredicate include = (MyASubsetPredicate) characteristic;
////		
////		if(include.getRightExpression().isInterval()) {
////			intervalBlocks.add(characteristic.toString());
////			intervalBlocks.add(negateClause(characteristic.toString()));
////		}
////	}
////	
////	
////	
////	private void buildIntervals(List<String> intervalBlocks, String leftTerm, String predicateSymbol, MyAIntervalExpression interval) {
////		String leftBorder = interval.getLeftExpression().toString();
////		String rightBorder = interval.getRightExpression().toString();
////		
////		intervalBlocks.add(leftTerm + predicateSymbol + "MININT" + ".." + (leftBorder + " - 1"));
////		intervalBlocks.add(leftTerm + predicateSymbol + (rightBorder + " + 1") + ".." + "MAXINT");
////		intervalBlocks.add(leftTerm + predicateSymbol + leftBorder + ".." + rightBorder);
////	}
////	
////	
////	
////	private void addIntervalBlocksToIncludesStrictly(MyPredicate characteristic, List<String> intervalBlocks) {
////		MyASubsetStrictPredicate includeStrictly = (MyASubsetStrictPredicate) characteristic;
////
////		if(includeStrictly.getRightExpression().isInterval()) {
////			intervalBlocks.add(characteristic.toString());
////			intervalBlocks.add(negateClause(characteristic.toString()));
////		}
////	}
////	
////	
////	
////	private void addCommonBlock(HashMap<String, List<String>> blocks, Characteristic characteristic) {
////		List<String> chBlocks = new ArrayList<String>();
////		
////		chBlocks.add(characteristic.toString());
////		
////		if(characteristic.getType() != CharacteristicType.INVARIANT) {
////			if(!characteristic.isTypingCharacteristic()) {
////				chBlocks.add(negateClause(characteristic.toString()));
////			} else if (characteristic instanceof PredicateCharacteristic) {
////				PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
////
////				if(predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
////					MyASubsetStrictPredicate strictSubset = (MyASubsetStrictPredicate) predicateCharacteristic.getPredicate();
////					if(strictSubset.getRightExpression().isBasicType() && !strictSubset.getRightExpression().toString().equals("BOOL")) {
////						chBlocks.add(negateClause(predicateCharacteristic.toString()));
////					}
////				}
////			}
////			
////		}
////
////		
////		blocks.put(characteristic.toString(), chBlocks);
////	}
////	
////	
////	private void addConditionalCharacteristicsBlocks(HashMap<String, List<String>> blocks) {
////		for (Characteristic conditional : getPartitioner().getOperation().getConditionalCharacteristics()) {
////			List<String> chBlocks = new ArrayList<String>();
////			chBlocks.add(conditional.toString());
////			chBlocks.add(negateClause(conditional.toString()));
////			
////			blocks.put(conditional.toString(), chBlocks);
////		}
////	}
//}
