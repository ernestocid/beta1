package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import parser.decorators.expressions.MyExpression;
import parser.decorators.expressions.MyExpressionFactory;
import de.be4.classicalb.core.parser.node.ADeferredSetSet;
import de.be4.classicalb.core.parser.node.AEnumeratedSetSet;
import de.be4.classicalb.core.parser.node.ASetsMachineClause;
import de.be4.classicalb.core.parser.node.PExpression;
import de.be4.classicalb.core.parser.node.PSet;

public class Sets {

	private ASetsMachineClause setsClause;

	public Sets(ASetsMachineClause sets) {
		this.setsClause = sets;
	}

	public ASetsMachineClause getNode() {
		return setsClause;
	}

	public Set<String> getElementsFromAllSets() {
		Set<String> setElements = new HashSet<String>();
		LinkedList<PSet> sets = setsClause.getSetDefinitions();
		for (PSet set : sets) {
			if (set instanceof AEnumeratedSetSet) {
				AEnumeratedSetSet enumeratedSet = (AEnumeratedSetSet) set;
				LinkedList<PExpression> elements = enumeratedSet.getElements();
				for (PExpression expression : elements) {
					MyExpression myExpression = MyExpressionFactory.convertExpression(expression);
					String element = myExpression.toString();
					setElements.add(element);
				}
			}
		}
		return setElements;
	}
	
	public List<String> getDeferredSets() {
		LinkedList<PSet> setDefinitions = setsClause.getSetDefinitions();
		List<String> deferredSets = new ArrayList<String>();
		for (PSet pSet : setDefinitions) {
			if (pSet instanceof ADeferredSetSet) {
				ADeferredSetSet deferredSet = (ADeferredSetSet) pSet;
				String setName = deferredSet.getIdentifier().getFirst()
						.getText();
				
				deferredSets.add(setName);
			}
		}
		return deferredSets;
	}
	
	public List<String> getEnumeratedSets() {
		LinkedList<PSet> setDefinitions = this.setsClause.getSetDefinitions();
		List<String> enumeratedSets = new ArrayList<String>();
		for (PSet pSet : setDefinitions) {
			if (pSet instanceof AEnumeratedSetSet) {
				AEnumeratedSetSet enumeratedSet = (AEnumeratedSetSet) pSet;
				String setName = enumeratedSet.getIdentifier().getFirst()
						.getText();
				
				enumeratedSets.add(setName);
			}
		}
		return enumeratedSets;
	}

	public List<String> getSetElements(String setName) {
		LinkedList<PSet> setDefinitions = setsClause.getSetDefinitions();
		List<String> setValues = new ArrayList<String>();
		for (PSet set : setDefinitions) {
			if(set instanceof AEnumeratedSetSet) {
				AEnumeratedSetSet enumeratedSet = (AEnumeratedSetSet) set;
				String enumeratedSetName = enumeratedSet.getIdentifier().element().getText(); 
				if(enumeratedSetName.equals(setName)) {
					LinkedList<PExpression> elements = enumeratedSet.getElements();
					for (PExpression expression : elements) {
						MyExpression myExpression = MyExpressionFactory.convertExpression(expression);
						String value = myExpression.toString();
						setValues.add(value);
					}
				}
			}
		}
		return setValues;
	}
	
	public List<String> getEnumeratedSetsWithElements() {
		List<String> enumeratedSets = new ArrayList<String>();
		
		for(String enumeratedSet : getEnumeratedSets()) {
			StringBuffer set = new StringBuffer(enumeratedSet + " = {");
			List<String> elements = getSetElements(enumeratedSet);
			
			for (int i = 0; i < elements.size(); i++) {
				if(i < elements.size() - 1) {
					set.append(elements.get(i) + ",");
				} else {
					set.append(elements.get(i) + "}");
				}
			}
			
			enumeratedSets.add(set.toString());
			
		}
		
		return enumeratedSets;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sets = new StringBuffer("");

		List<String> enumeratedSets = getEnumeratedSets();
		List<String> deferredSets = getDeferredSets();
		
		sets.append("SETS\n");
		
		if (!enumeratedSets.isEmpty()) {
			String enumeratedSetsText = parseEnumeratedSetsToString(enumeratedSets);
			sets.append(enumeratedSetsText);
		}
		
		if (!deferredSets.isEmpty()) { 
			String deferredSetsText = parseDeferredSetsToString(deferredSets);
			sets.append(deferredSetsText);
		}

		sets.append("\n");
		return sets.toString();
	}

	private String parseDeferredSetsToString(List<String> deferredSets) {
		StringBuffer deferredSetsString = new StringBuffer("");
		
		int numberOfSets = deferredSets.size();
		int setCounter = 1;
		for (String deferredSet : deferredSets) {
			deferredSetsString.append(deferredSet);

			if (setCounter < numberOfSets) {
				deferredSetsString.append(";\n");
			} else {
				deferredSetsString.append("\n");
			}
			
			setCounter++;
		}
		
		return deferredSetsString.toString();
	}

	private String parseEnumeratedSetsToString(List<String> enumeratedSets) {
		StringBuffer enumeratedSetsString = new StringBuffer("");
		
		int numberOfSets = enumeratedSets.size();
		int setCounter = 1;
		
		for (String enumeratedSet : enumeratedSets) {
			enumeratedSetsString.append(enumeratedSet + " = {");
			
			List<String> enumeratedSetElements = getSetElements(enumeratedSet);
			String enumeratedSetElementsText = parseEnumeratedSetElementsToString(enumeratedSetElements);
			enumeratedSetsString.append(enumeratedSetElementsText);

			if (setCounter < numberOfSets) {
				enumeratedSetsString.append("};\n");
			} else if (!getDeferredSets().isEmpty()) {
				enumeratedSetsString.append("};\n");
			} else {
				enumeratedSetsString.append("}\n");
			}
			setCounter++;
		}
		
		return enumeratedSetsString.toString();
	}

	private String parseEnumeratedSetElementsToString(List<String> enumeratedSetElements) {
		StringBuffer elements = new StringBuffer();
		
		int numberOfElements = enumeratedSetElements.size();
		int elementCounter = 1;
		
		for (String element : enumeratedSetElements) {
			if (elementCounter < numberOfElements) {
				elements.append(element + ", ");
			} else {
				elements.append(element);
			}
			elementCounter++;
		}
		
		return elements.toString();
	}
}
