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
//
//public class BoundaryValuesBlockBuilder extends BlockBuilder {
//	
//	
////	public BoundaryValuesBlockBuilder(Partitioner partitioner) {
////		super(partitioner);
////	}
////	
////
////
////	public Map<String, List<String>> getBlocks() {
////		return arrangeBoundaryBlocks();
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
////	private Map<String, List<String>> arrangeBoundaryBlocks() {
////		HashMap<String, List<String>> blocks = new HashMap<String, List<String>>();
////
////		addGeneralCharacteristicsBlocksUsingBoundaryAnalisys(blocks);
////		addConditionalCharacteristicsBlocks(blocks);
////		
////		return blocks;
////	}
////	
////	
////	
////	private void addGeneralCharacteristicsBlocksUsingBoundaryAnalisys(HashMap<String, List<String>> blocks) {
////		for (Characteristic characteristic : getPartitioner().getGeneralCharacteristicsClauses()) {
////			if(characteristic.isIntervalCharacteristic() && characteristic.getType() != CharacteristicType.INVARIANT) {
////				if(characteristic.isTypingCharacteristic()) {
////					addIntervalBoundaryBlocksForTypingCharacteristics(blocks, characteristic);
////				} else {
////					addIntervalBoundaryBlock(blocks, characteristic);
////				}
////			} else {
////				addCommonBlock(blocks, characteristic);
////			}
////		}
////	}
////	
////	
////	
////	private void addIntervalBoundaryBlocksForTypingCharacteristics(HashMap<String, List<String>> blocks, Characteristic characteristic) {
////		if(characteristic instanceof PredicateCharacteristic) {
////			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
////			
////			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
////				MyAMemberPredicate memberOfCharacteristic = (MyAMemberPredicate) predicateCharacteristic.getPredicate();
////				blocks.put(memberOfCharacteristic.toString(), generateBoundaryValuesIntervalBlocksForMemberOfCharacteristic(memberOfCharacteristic));
////			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
////				MyASubsetPredicate subsetCharacteristic = (MyASubsetPredicate) predicateCharacteristic.getPredicate();
////				blocks.put(subsetCharacteristic.toString(), generateBoundaryValuesIntervalBlocksForSubsetCharacteristic(subsetCharacteristic));
////			} else if (predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
////				MyASubsetStrictPredicate subsetStrictCharacteristic = (MyASubsetStrictPredicate) predicateCharacteristic.getPredicate();
////				blocks.put(subsetStrictCharacteristic.toString(), generateBoudaryValuesIntervalBlocksForSubsetStrictCharacteristic(subsetStrictCharacteristic));
////			}
////		}
////		
////		
////	}
////	
////	
////	
////	private List<String> generateBoundaryValuesIntervalBlocksForMemberOfCharacteristic(MyAMemberPredicate memberOfCharacteristic) {
////		List<String> blocks = new ArrayList<String>();
////		
////		String id = memberOfCharacteristic.getLeftExpression().toString();
////		
////		if (memberOfCharacteristic.getRightExpression() instanceof MyANatSetExpression) {
////			blocks.add(id + " = -1");
////			blocks.add(id + " = 0");
////			blocks.add(id + " = 1");
////			blocks.add(id + " = MAXINT-1");
////			blocks.add(id + " = MAXINT");
////			blocks.add(id + " = MAXINT+1");
////		} else if (memberOfCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
////			blocks.add(id + " = 0");
////			blocks.add(id + " = 1");
////			blocks.add(id + " = 2");
////			blocks.add(id + " = MAXINT-1");
////			blocks.add(id + " = MAXINT");
////			blocks.add(id + " = MAXINT+1");
////		} else if (memberOfCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
////			blocks.add(memberOfCharacteristic.toString());
////		}
////		
////		return blocks;
////	}
////	
////	
////	
////	private List<String> generateBoundaryValuesIntervalBlocksForSubsetCharacteristic(MyASubsetPredicate subsetCharacteristic) {
////		List<String> blocks = new ArrayList<String>();
////		
////		if (subsetCharacteristic.getRightExpression() instanceof MyANatSetExpression) {
////			blocks.add(subsetCharacteristic.toString());
////			blocks.add(negateClause(subsetCharacteristic.toString()));
////		} else if (subsetCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
////			blocks.add(subsetCharacteristic.toString());
////			blocks.add(negateClause(subsetCharacteristic.toString()));
////		} else if (subsetCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
////			blocks.add(subsetCharacteristic.toString());
////		}
////		
////		return blocks;
////	}
////	
////	
////	
////	private List<String> generateBoudaryValuesIntervalBlocksForSubsetStrictCharacteristic(MyASubsetStrictPredicate subsetStrictCharacteristic) {
////		List<String> blocks = new ArrayList<String>();
////		
////		if (subsetStrictCharacteristic.getRightExpression() instanceof MyANatSetExpression) {
////			blocks.add(subsetStrictCharacteristic.toString());
////			blocks.add(negateClause(subsetStrictCharacteristic.toString()));
////		} else if (subsetStrictCharacteristic.getRightExpression() instanceof MyANat1SetExpression) {
////			blocks.add(subsetStrictCharacteristic.toString());
////			blocks.add(negateClause(subsetStrictCharacteristic.toString()));
////		} else if (subsetStrictCharacteristic.getRightExpression() instanceof MyAIntSetExpression) {
////			blocks.add(subsetStrictCharacteristic.toString());
////		}
////		
////		return blocks;
////	}
////	
////	
////	
////	private void addIntervalBoundaryBlock(HashMap<String, List<String>> blocks, Characteristic characteristic) {
////		blocks.put(characteristic.toString(), getBoundaryBlocks(characteristic));
////	}
////	
////	
////	
////	private List<String> getBoundaryBlocks(Characteristic characteristic) {
////		List<String> intervalBlocks = new ArrayList<String>();
////		
////		if(characteristic instanceof PredicateCharacteristic) {
////			PredicateCharacteristic predicateCharacteristic = (PredicateCharacteristic) characteristic;
////
////			if (predicateCharacteristic.getPredicate() instanceof MyAMemberPredicate) {
////				addIntervalBoundaryBlocksToBelongs(predicateCharacteristic.getPredicate(), intervalBlocks);
////			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetPredicate) {
////				addIntervalBoundaryBlocksToIncludes(predicateCharacteristic.getPredicate(), intervalBlocks);
////			} else if(predicateCharacteristic.getPredicate() instanceof MyASubsetStrictPredicate) {
////				addIntervalBoundaryBlocksToIncludesStrictly(predicateCharacteristic.getPredicate(), intervalBlocks);
////			}
////		}
////		
////		
////		return intervalBlocks;
////	}
////	
////	
////	
////	private void addIntervalBoundaryBlocksToBelongs(MyPredicate characteristic, List<String> intervalBlocks) {
////		MyAMemberPredicate belong = (MyAMemberPredicate) characteristic;
////
////		if(belong.getRightExpression().isInterval()) {
////			String leftTerm = belong.getLeftExpression().toString();
////			boolean isExplicitInterval = belong.getRightExpression() instanceof MyAIntervalExpression;
////			boolean isImplicitInterval = belong.getRightExpression() instanceof MyAIntSetExpression || 
////										 belong.getRightExpression() instanceof MyANatSetExpression ||
////										 belong.getRightExpression() instanceof MyANat1SetExpression;
////			
////			if(isExplicitInterval) {
////				MyAIntervalExpression interval = (MyAIntervalExpression) belong.getRightExpression();
////				buildBoundaryIntervalsToBelongs(intervalBlocks, leftTerm, " : ", interval);
////			} else if(isImplicitInterval) {
////				buildBoundaryValuesToSet(intervalBlocks, leftTerm, ":");
////			}
////		}
////	}
////	
////	
////	
////	private void addIntervalBoundaryBlocksToIncludes(MyPredicate characteristic, List<String> intervalBlocks) {
////		MyASubsetPredicate include = (MyASubsetPredicate) characteristic;
////		
////		if(include.getRightExpression().isInterval() && !include.getRightExpression().isBasicType()) {
////			
////			intervalBlocks.add(characteristic.toString());
////			intervalBlocks.add(negateClause(characteristic.toString()));
////			
////		} else if(include.getRightExpression().isInterval() && include.getRightExpression().isBasicType()) {
////			intervalBlocks.add(characteristic.toString());
////		}
////	}
////	
////	
////	
////	private void addIntervalBoundaryBlocksToIncludesStrictly(MyPredicate characteristic, List<String> intervalBlocks) {
////		MyASubsetStrictPredicate includeStrictly = (MyASubsetStrictPredicate) characteristic;
////
////		if(includeStrictly.getRightExpression().isInterval() && !includeStrictly.getRightExpression().isBasicType()) {
////			intervalBlocks.add(characteristic.toString());
////			intervalBlocks.add(negateClause(characteristic.toString()));
////		} else if(includeStrictly.getRightExpression().isBasicType()) {
////			intervalBlocks.add(characteristic.toString());
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
////			
////		
////		}
////		
////		blocks.put(characteristic.toString(), chBlocks);
////	}
////	
////	
////	
////	private void buildBoundaryIntervalsToBelongs(List<String> intervalBlocks, String leftTerm, String predicateSymbol, MyAIntervalExpression interval) {
////		String leftBorder = interval.getLeftExpression().toString();
////		String rightBorder = interval.getRightExpression().toString();
////		
////		intervalBlocks.add(leftTerm + " = " + (leftBorder + " - 1")); 	// Inferior Left Border
////		intervalBlocks.add(leftTerm + " = " + leftBorder); 			// In Left Border
////		intervalBlocks.add(leftTerm + " = " + (leftBorder + " + 1")); 	// Superior Left Border
////		intervalBlocks.add(leftTerm + " = " + (rightBorder + " - 1")); 	// Inferior Right Border
////		intervalBlocks.add(leftTerm + " = " + rightBorder); 		// In Right Border
////		intervalBlocks.add(leftTerm + " = " + (rightBorder + " + 1")); 	// Superior Right Border
////	}
////	
////	
////	
////	private void buildBoundaryValuesToSet(List<String> intervalBlocks, String leftTerm, String setRelation) {
////		intervalBlocks.add(leftTerm + " = " + "MININT-1");
////		intervalBlocks.add(leftTerm + " = " + "MININT");
////		intervalBlocks.add(leftTerm + " = " + "MININT+1");
////		intervalBlocks.add(leftTerm + " = " + "MAXINT-1");
////		intervalBlocks.add(leftTerm + " = " + "MAXINT");
////		intervalBlocks.add(leftTerm + " = " + "MAXINT+1");
////	}
////	
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
