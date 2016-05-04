package testgeneration;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import de.be4.classicalb.core.parser.node.AExpressionParseUnit;
import de.be4.classicalb.core.parser.node.APredicateParseUnit;
import de.be4.classicalb.core.parser.node.PParseUnit;
import de.prob.animator.domainobjects.ClassicalB;
import parser.Characteristic;
import parser.CharacteristicType;
import parser.Definitions;
import parser.ExpressionCharacteristic;
import parser.Operation;
import parser.PredicateCharacteristic;
import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;

/**
 * This class is responsible for gathering machine information for test case
 * generation. These information are: input space variables and characteristics
 * of this variables.
 * 
 * @author ernestocid
 * 
 */
public class Partitioner {

	/**
	 * Operation under test
	 */
	private Operation operation;



	public Partitioner(Operation operation) {
		this.operation = operation;
	}



	public Operation getOperation() {
		return this.operation;
	}



	/**
	 * This method creates a set containing all input space variables for the
	 * operation under test.
	 * 
	 * @return input space variables for the operation under test
	 */
	public Set<String> getOperationInputSpace() {
		Set<String> inputSpace = new HashSet<String>();
		inputSpace.addAll(getRelatedVariables(this.operation));
		inputSpace.addAll(this.operation.getIfCommandVariables());
		inputSpace.addAll(this.operation.getAnyCommandVariables());
		return inputSpace;
	}



	// TODO: needs refactoring
	/**
	 * Creates a set of input space variables present on the Precondition and
	 * Invariant clauses.
	 * 
	 * @param operation
	 *            under test
	 * @return set of input space variables present on precondition, invariant
	 *         and properties
	 */
	private Set<String> getRelatedVariables(Operation operation) {
		Set<String> relatedVariables = new HashSet<String>();

		if (operation.getMachine().getVariables() != null) {
			relatedVariables.addAll(operation.getMachine().getVariables().getAll());
		}

		if (this.operation.getPrecondition() != null) {
			// Add all variables mentioned in the precondition
			relatedVariables.addAll(this.operation.getPrecondition().getVariables());

			// Remove return variables that might be added on the previous step
			relatedVariables.removeAll(this.operation.getReturnVariables());

			// Search for more related variables on the invariant and add them
			// to the
			// related variables set
			relatedVariables.addAll(getRelatedVariablesOnInvariants(relatedVariables));
			
			// Search for related variables on machine definitions
			relatedVariables.addAll(getRelatedVariablesOnDefinitions(relatedVariables));
			
			// Remove set names that might be added in the previous steps
			removeSets(relatedVariables);

			// Remove constants from enumerated sets that might be added in the
			// previous steps
			removeConstantsFromSets(relatedVariables);
			
			// Remove constants that might have been added in the previous steps
			removeConstants(relatedVariables);
			
			// Remove definition names, they are only used for reference
			removeDefinitionsNames(relatedVariables);
		}

		return relatedVariables;
	}



	private void removeDefinitionsNames(Set<String> relatedVariables) {
		Definitions definitions = this.operation.getMachine().getDefinitions();
		
		if(definitions != null) {
			relatedVariables.removeAll(definitions.getDefinitions().keySet());
		}
	}



	private Set<String> getRelatedVariablesOnDefinitions(Set<String> relatedVariables) {
		Set<String> relatedDefinitionVariables = new HashSet<String>(relatedVariables);
		Set<String> oldRelatedVariables = new HashSet<String>();

		if(this.operation.getMachine().getDefinitions() != null) {
			while (!relatedDefinitionVariables.equals(oldRelatedVariables)) {
				oldRelatedVariables.addAll(relatedDefinitionVariables);
				searchMoreRelatedVariablesOnDefinitions(relatedDefinitionVariables);
			}
		}
		
		return relatedDefinitionVariables;
	}
	
	
	
	private void searchMoreRelatedVariablesOnDefinitions(Set<String> relatedVariables) {
		Set<Entry<String, MyExpression>> definitionEntries = this.operation.getMachine().getDefinitions().getDefinitions().entrySet();

		for (Entry<String, MyExpression> definition : definitionEntries) {
			Set<String> definitionVariables = new HashSet<String>();
			
			definitionVariables.add(definition.getKey());
			definitionVariables.addAll(definition.getValue().getVariables());
			
			for (String variable : definitionVariables) {
				if (relatedVariables.contains(variable)) {
					relatedVariables.addAll(definitionVariables);
					break;
				}
			}
		}
	}



	/**
	 * Searches a machine's invariant looking for related variables. It also
	 * searches on invariants of other machines included. Every time the set of
	 * related variables is updated a new search is done to check for variables
	 * that are related to the ones just added. The method ends when no related
	 * variables are found after the last search.
	 * 
	 * @param relatedVariables
	 *            initial set of related variables
	 * @return updated set of related variables
	 */
	private Set<String> getRelatedVariablesOnInvariants(Set<String> relatedVariables) {
		Set<String> relatedInvariantVariables = new HashSet<String>(relatedVariables);
		Set<String> oldRelatedVariables = new HashSet<String>();

		while (!relatedInvariantVariables.equals(oldRelatedVariables)) {
			oldRelatedVariables.addAll(relatedInvariantVariables);
			searchMoreRelatedVariables(relatedInvariantVariables);
		}

		return relatedInvariantVariables;
	}



	private void searchMoreRelatedVariables(Set<String> relatedVariables) {
		Set<MyPredicate> invariant = this.operation.getMachine().getCondensedInvariantFromAllMachines();

		for (MyPredicate clause : invariant) {
			Set<String> clauseVariables = clause.getVariables();
			for (String variable : clauseVariables) {
				if (relatedVariables.contains(variable)) {
					relatedVariables.addAll(clauseVariables);
					break;
				}
			}
		}
	}



	/**
	 * Removes set names that might be added to the related variables set.
	 * 
	 * @param relatedVariables
	 *            set of related variables
	 */
	private void removeSets(Set<String> relatedVariables) {
		if (this.operation.getMachine().getSets() != null) {
			List<String> deferredSets = this.operation.getMachine().getSets().getDeferredSets();
			List<String> enumeratedSets = this.operation.getMachine().getSets().getEnumeratedSets();

			relatedVariables.removeAll(deferredSets);
			relatedVariables.removeAll(enumeratedSets);
		}
	}



	/**
	 * Removes constant values that might be added to the related variables set.
	 * Those are constant values from enumerated sets that might be mistaken as
	 * variables.
	 * 
	 * @param relatedVariables
	 *            set of related variables
	 */
	private void removeConstantsFromSets(Set<String> relatedVariables) {
		if (this.operation.getMachine().getSets() != null) {
			Set<String> setElements = this.operation.getMachine().getSets().getElementsFromAllSets();
			relatedVariables.removeAll(setElements);
		}
	}



	/**
	 * Removes CONSTANTS that might be added to the related variables set.
	 * 
	 * @param relatedVariables
	 */
	private void removeConstants(Set<String> relatedVariables) {
		relatedVariables.removeAll(this.operation.getMachine().getConstantsFromAllMachines());
	}



	public Set<String> getOperationCharacteristicsAsStrings() {
		Set<String> characteristics = new HashSet<String>();

		for (Characteristic characteristic : getOperationCharacteristics()) {
			characteristics.add(characteristic.toString());
		}

		return characteristics;
	}



	/**
	 * Creates a set of characteristics of the input space of the operation
	 * under test.
	 * 
	 * @return set of input space characteristics of the operation under test
	 */
	public Set<Characteristic> getOperationCharacteristics() {
		Set<Characteristic> characteristics = new HashSet<Characteristic>();

		// Gets characteristics from precondition, invariant and properties.
		Set<Characteristic> generalCharacteristics = getGeneralCharacteristicsClauses();

		// Gets characteristics from conditional statements.
		Set<Characteristic> conditionalCharacteristics = this.operation.getConditionalCharacteristics();

		characteristics.addAll(generalCharacteristics);
		characteristics.addAll(conditionalCharacteristics);

		characteristics = expandDefinitions(characteristics);
		
		return characteristics;
	}



	private Set<Characteristic> expandDefinitions(Set<Characteristic> characteristics) {
		Set<Characteristic> characteristicsWithDefinitionsExpanded = new HashSet<Characteristic>();
		
		for(Characteristic ch : characteristics) {

			if(ch instanceof PredicateCharacteristic) {
				PredicateCharacteristic predCh = (PredicateCharacteristic) ch;
				String predicateExpanded = expandAllDefinitions(predCh.getPredicate().toString());
				
				PParseUnit pu = new ClassicalB(predicateExpanded).getAst().getPParseUnit();
				
				if(pu instanceof APredicateParseUnit) {
					APredicateParseUnit predPU = (APredicateParseUnit) pu;
					MyPredicate myPredicate = MyPredicateFactory.convertPredicate(predPU.getPredicate());
					
					characteristicsWithDefinitionsExpanded.add(new PredicateCharacteristic(myPredicate, predCh.getType()));
				}
				
			} else if (ch instanceof ExpressionCharacteristic) {
				ExpressionCharacteristic expCh = (ExpressionCharacteristic) ch;
				String expressionExpanded = expandAllDefinitions(expCh.toString());
				
				PParseUnit pu = new ClassicalB(expressionExpanded).getAst().getPParseUnit();
				
				if(pu instanceof AExpressionParseUnit) {
					AExpressionParseUnit expPU = (AExpressionParseUnit) pu;
					MyExpression myExpression = MyExpressionFactory.convertExpression(expPU.getExpression());
					
					characteristicsWithDefinitionsExpanded.add(new ExpressionCharacteristic(myExpression, expCh.getType()));
				}
				
			}
		}
		
		return characteristicsWithDefinitionsExpanded;
	}



	private String expandAllDefinitions(String predicate) {
		Definitions definitions = this.operation.getMachine().getDefinitions();
		
		if(definitions != null) {
			String oldPredicate = "";
			String newPredicate = predicate;
			
			while(!oldPredicate.equals(newPredicate)) {
				oldPredicate = newPredicate;
				
				for(Entry<String, MyExpression> entry : definitions.getDefinitions().entrySet()) {
					newPredicate = newPredicate.replace(entry.getKey(), entry.getValue().toString());
				}
				
			}

			return newPredicate;
		}
		
		return predicate;
	}



	protected Set<Characteristic> getGeneralCharacteristicsClauses() {
		Set<Characteristic> characs = new HashSet<Characteristic>();

		// The order from where you get the characteristics is important here if
		// there are
		// clauses that state the same thing on both precondition and invariant.

		for (Characteristic c : getCharacteristicsFromPrecondition()) {
			if (!doesSetContains(c, characs)) {
				characs.add(c);
			}
		}

		for (Characteristic c : getCharacteristicsFromInvariant()) {
			if (!doesSetContains(c, characs)) {
				characs.add(c);
			}
		}

		for (Characteristic c : getCharacteristicsFromInvariantThatMentionOperationBodyVariables()) {
			if (!doesSetContains(c, characs)) {
				characs.add(c);
			}
		}

		characs = expandDefinitions(characs);
		
		return characs;
	}



	// It is not checking the type of the characteristic. So there might occur
	// that clauses telling
	// the same thing (same String) do not appear repeated.
	private boolean doesSetContains(Characteristic characteristic, Set<Characteristic> characteristics) {

		for (Characteristic c : characteristics) {
			if (c.toString().equals(characteristic.toString())) {
				return true;
			}
		}

		return false;
	}



	private Set<Characteristic> getCharacteristicsFromInvariantThatMentionOperationBodyVariables() {
		Set<Characteristic> characteristicsFromInvariantThatMentionOperationBodyVariables = new HashSet<Characteristic>();

		Set<String> bodyVariables = this.operation.getStateVariablesUsedOnBody();

		for (MyPredicate clause : getPredicatesRelatedWithVariables(bodyVariables, this.operation.getMachine().getCondensedInvariantFromAllMachines())) {
			characteristicsFromInvariantThatMentionOperationBodyVariables.add(new PredicateCharacteristic(clause, CharacteristicType.INVARIANT));
		}

		return characteristicsFromInvariantThatMentionOperationBodyVariables;
	}



//	private Set<Characteristic> getCharacteristicsFromProperties() {
//		Set<Characteristic> characteristicsFromProperties = new HashSet<Characteristic>();
//
//		for (MyPredicate clause : getPredicatesRelatedWithVariables(getOperationInputSpace(), this.operation.getMachine().getPropertiesClauses())) {
//			characteristicsFromProperties.add(new PredicateCharacteristic(clause, CharacteristicType.PROPERTIES));
//		}
//
//		return characteristicsFromProperties;
//	}



	public Set<Characteristic> getCharacteristicsFromInvariant() {
		Set<Characteristic> characteristicsFromInvariant = new HashSet<Characteristic>();

		for (MyPredicate clause : getPredicatesRelatedWithVariables(getOperationInputSpace(), this.operation.getMachine()
				.getCondensedInvariantFromAllMachines())) {
			characteristicsFromInvariant.add(new PredicateCharacteristic(clause, CharacteristicType.INVARIANT));
		}

		return characteristicsFromInvariant;
	}



	public Set<Characteristic> getCharacteristicsFromPrecondition() {
		Set<Characteristic> characteristicsFromPrecondition = new HashSet<Characteristic>();

		Set<MyPredicate> preconditionClauses = this.operation.getPreconditionClauses();

		for (MyPredicate preconditionClause : preconditionClauses) {
			characteristicsFromPrecondition.add(new PredicateCharacteristic(preconditionClause, CharacteristicType.PRE_CONDITION));
		}

		// for(MyPredicate clause :
		// getPredicatesRelatedWithVariables(getOperationInputSpace(),
		// this.operation.getPreconditionClauses())) {
		// characteristicsFromPrecondition.add(new
		// PredicateCharacteristic(clause, CharacteristicType.PRE_CONDITION));
		// }

		return characteristicsFromPrecondition;
	}



	/**
	 * Creates a set of predicates that contain related variables. Receives a
	 * set of related variables and a set of predicates and creates a new set
	 * containing all the predicates that mention at least one related variable.
	 * 
	 * @param variables
	 *            set of related variables that are going to be searched
	 * @param predicates
	 *            set of predicates that are going to be investigated
	 * @return set of predicates that mention related variables
	 */
	private Set<MyPredicate> getPredicatesRelatedWithVariables(Set<String> variables, Set<MyPredicate> predicates) {
		Set<MyPredicate> relatedPredicates = new HashSet<MyPredicate>();

		for (MyPredicate clause : predicates) {
			for (String variable : variables) {
				if (clause.getVariables().contains(variable)) {
					relatedPredicates.add(clause);
					break;
				}
			}
		}

		return relatedPredicates;
	}



	public Set<String> getTypingClauses() {
		Set<String> typingClauses = new HashSet<String>();

		for (String variable : getOperationInputSpace()) {
			for (Characteristic clause : getGeneralCharacteristicsClauses()) {
				if (clause.isTypingCharacteristic() && clause.getVariables().contains(variable)) {
					typingClauses.add(clause.toString());
				}
			}
		}

		return typingClauses;
	}
}
