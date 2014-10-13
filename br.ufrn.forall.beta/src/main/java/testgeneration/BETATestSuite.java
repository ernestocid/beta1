package testgeneration;

import general.CombinatorialCriterias;
import general.PartitionStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import configurations.Configurations;
import criteria.AllCombinations;
import criteria.Criteria;
import criteria.EachChoice;
import criteria.Pairwise;
import de.prob.Main;
import de.prob.scripting.Api;
import animator.Animation;
import animator.Animator;
import animator.ConventionTools;
import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import tools.FileTools;

public class BETATestSuite {

	
	private List<BETATestCase> testCases;
	private List<String> unsolvableFormulas = new ArrayList<String>();
	private Operation operationUnderTest;
	private PartitionStrategy partitionStrategy;
	private CombinatorialCriterias combinatorialCriteria;
	private Api probApi;

	
	public BETATestSuite(Operation operationUnderTest, PartitionStrategy partitionStrategy,	CombinatorialCriterias combinatorialCriteria) {
		this.operationUnderTest = operationUnderTest;
		this.partitionStrategy = partitionStrategy;
		this.combinatorialCriteria = combinatorialCriteria;
		this.testCases = new ArrayList<BETATestCase>();
		String probpath = Configurations.getProBPath();
//		System.setProperty("prob.home", probpath);
		this.probApi = Main.getInjector().getInstance(Api.class);
		generateTestCases();
	}

	

	private void generateTestCases() {
		
		// Generating partition blocks and combining them according to a combinatorial criteria
		
		BlockBuilder blockBuilder;
		
		if(this.partitionStrategy == PartitionStrategy.EQUIVALENT_CLASSES) {
			blockBuilder = new ECBlockBuilder(new Partitioner(this.operationUnderTest));
		} else if(this.partitionStrategy == PartitionStrategy.BOUNDARY_VALUES) {
			blockBuilder = new BVBlockBuilder(new Partitioner(this.operationUnderTest));
		} else {
			blockBuilder = null;
		}
		
		List<List<Block>> blocks = blockBuilder.getBlocksAsListsOfBlocks();

		Set<List<Block>> combinations = getCombinations(blocks);
		
		if(!combinations.isEmpty()) {
			// Creating and animating the auxiliary B machine to generate test data
			
			TestMachineBuilder testMachineBuilder = new TestMachineBuilder(operationUnderTest, combinations);
			String testMachineContent = testMachineBuilder.generateTestMachine();
			
			String parentPathDirectory = operationUnderTest.getMachine().getFile().getParent();
			
			String testMachineFilePath = parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest) + ".mch";
			File testMachineFile = FileTools.createFileWithContent(testMachineFilePath, testMachineContent);
			
			Machine testMachine = new Machine(testMachineFile);
			
			Animator animator = new Animator(testMachine);
			List<Animation> animations = animator.animateAllOperations();

			// Creating a map that identifies formulas for negative tests. It is used later for 
			// identifying negative tests when building the test suite
			
			Map<String, Boolean> mappingCombinationToTypeOfTest = mapCombinationsToHashMapOfTypes(combinations);
			
			// Getting unsolvable test formulas
			
			for(Operation op : animator.getOperationsWithUnsolvablePreconditionPredicates()) {
				this.unsolvableFormulas.add(generateTestFormulaWithoutInvariant(op.getPrecondition()));
			}
			
			// Creating a test case for each animation
			
			for (Animation animation : animations) {
				// Getting first animation. All others are supposedly equivalent.
				
				Map<String, String> animationValues = animation.getValues().get(0);
				
				// Setting up test case and adding to test suite.
				
				BETATestCase testCase = new BETATestCase(animation.getFormula(), 
														 generateTestFormulaWithoutInvariant(animation.getPredicate()), 
														 getAttributeValues(animationValues), 
														 getParamValues(animationValues), 
														 isNegativeTest(animation, mappingCombinationToTypeOfTest),
														 this);
				
				testCases.add(testCase);
			}
			
			// Sorting test cases
			
			Collections.sort(testCases);
			
			// Deleting temp files
			
			if(Configurations.isDeleteTempFiles() == true) {
				deleteTempFiles(parentPathDirectory);
			}
			
		}
	}

	

	private boolean isNegativeTest(Animation animation, Map<String, Boolean> mappingCombinationToTypeOfTest) {
		String formula = animation.getFormula().replaceAll("[()]", "");
		Boolean isNegative = mappingCombinationToTypeOfTest.get(formula);
		return isNegative.booleanValue();
	}



	private Map<String, Boolean> mapCombinationsToHashMapOfTypes(Set<List<Block>> combinations) {
		Map<String, Boolean> mapping = new HashMap<String, Boolean>();
		
		for (List<Block> combination : combinations) {
			String formula = convertCombinationToStringConcatenation(combination);
			formula = formula.replaceAll("i__", "");
			Boolean isNegative = hasNegativeBlock(combination);
			
			mapping.put(formula.replaceAll("[()]", ""), isNegative);
		}

		return mapping;
	}
	
	
	
	private boolean hasNegativeBlock(List<Block> combination) {
		for (Block block : combination) {
			if(block.isNegative()) {
				return true;
			}
		}
		
		return false;
	}

	

	private String convertCombinationToStringConcatenation(List<Block> combination) {
		StringBuffer concatenation = new StringBuffer("");
		
		for (int i = 0; i < combination.size(); i++) {
			if(i == combination.size() - 1) {
				concatenation.append(combination.get(i).getBlock());
			} else {
				concatenation.append(combination.get(i).getBlock() + " & ");
			}
		}
		
		return concatenation.toString();
	}



	// TODO: Refactor this later!
	private String generateTestFormulaWithoutInvariant(MyPredicate predicate) {
		// Recuperando as clausulas		
		
		Set<MyPredicate> clauses = predicate.getClauses();
		
		// Convertendo as clausulas para Strings
		
		List<String> clausesAsStrings = new ArrayList<String>();
		
		for (MyPredicate clause : clauses) {
			clausesAsStrings.add(clause.toString().replaceAll("i__", ""));
		}
		
		// Recuperando as clausulas de invariante e convertendo para Strings
		
		Set<MyPredicate> condensedInvariantFromAllMachines = this.operationUnderTest.getMachine().getCondensedInvariantFromAllMachines();
		Set<String> condesendInvariant = new HashSet<String>();

		for (MyPredicate myPredicate : condensedInvariantFromAllMachines) {
			condesendInvariant.add(myPredicate.toString());
		}
		
		// Removendo as clausulas de invariant das clausulas originais
		clausesAsStrings.removeAll(condesendInvariant);
		Collections.sort(clausesAsStrings);
		
		// Montando a f���������rmula
		
		StringBuffer testFormulaWithoutInvariant = new StringBuffer();
		
		int i = 0;
		
		
		for (String cl : clausesAsStrings) {
			if(i < (clausesAsStrings.size() - 1)) {
				testFormulaWithoutInvariant.append(cl + " & ");
			} else {
				testFormulaWithoutInvariant.append(cl);
			}
			
			i++;
		}
		
		return testFormulaWithoutInvariant.toString().replaceAll("i__", "");
	}



	private void deleteTempFiles(String parentPathDirectory) {
		File mchFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest) + ".mch");
		mchFile.delete();
		File plFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest) + ".mch.pl");
		plFile.delete();
		File probFile = new File(parentPathDirectory + System.getProperty("file.separator") + ConventionTools.getTestMachineName(operationUnderTest) + ".prob");
		probFile.delete();
	}
	
	
	
	// TODO: Find a better solution to create separate state variables and parameters.
	// TODO: refatorar!
	private HashMap<String, String> getAttributeValues(Map<String, String> animation) {
		HashMap<String, String> attributeValues = new HashMap<String, String>();
		Set<String> variables = new HashSet<String>();
		
		if (this.operationUnderTest.getMachine().getVariables() != null) {
			variables.addAll(this.operationUnderTest.getMachine().getVariables().getAll());
		}
		
		Partitioner partitioner = new Partitioner(this.operationUnderTest);
		Set<String> inputSpace = partitioner.getOperationInputSpace();
		
		if (this.operationUnderTest.getMachine().getIncludes() != null) {
			for (Machine machineIncluded : this.operationUnderTest.getMachine().getIncludes().getMachinesIncluded()) {
				if(machineIncluded.getVariables() != null) {
					for (String variable : machineIncluded.getVariables().getAll()) {
						if(inputSpace.contains(variable)) {
							variables.add(variable);
						}
					}
				}
			}
		}
		
		for (String variable : variables) {
			attributeValues.put(variable, animation.get(variable));
		}
		
		return attributeValues;
	}

		
		
	private HashMap<String, String> getParamValues(Map<String, String> animation) {
		HashMap<String, String> parameterValues = new HashMap<String, String>();
		List<String> parameters = operationUnderTest.getParameters();

		for (String param : parameters) {
			parameterValues.put(param, animation.get(param));
		}

		return parameterValues;
	}



	private Set<List<Block>> getCombinations(List<List<Block>> blocks) {
		Set<List<Block>> combinations = new HashSet<List<Block>>();
		
		if (this.combinatorialCriteria == CombinatorialCriterias.PAIRWISE) {
			Criteria<Block> pairwise = new Pairwise<Block>(blocks);
			return pairwise.getCombinations();
		} else if (this.combinatorialCriteria == CombinatorialCriterias.EACH_CHOICE) {
			Criteria<Block> eachChoice = new EachChoice<Block>(blocks);
			return eachChoice.getCombinations();
		} else if (this.combinatorialCriteria == CombinatorialCriterias.ALL_COMBINATIONS) {
			Criteria<Block> allCombinations = new AllCombinations<Block>(blocks);
			return allCombinations.getCombinations();
		}
		
		return combinations;
	}



	public void setTestCases(List<BETATestCase> testCases) {
		this.testCases = testCases;
	}



	public PartitionStrategy getPartitionStrategy() {
		return this.partitionStrategy;
	}



	public List<BETATestCase> getTestCases() {
		return this.testCases;
	}



	public Operation getOperationUnderTest() {
		return this.operationUnderTest;
	}



	public CombinatorialCriterias getCombinatorialCriteria() {
		return this.combinatorialCriteria;
	}
	
	
	
	public List<String> getUnsolvableFormulas() {
		return unsolvableFormulas;
	}
	
	
	
	public Api getProbApi() {
		return this.probApi;
	}

}
