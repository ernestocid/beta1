package parser;

import java.util.HashSet;
import java.util.Set;

import parser.decorators.predicates.MyPredicate;
import parser.decorators.predicates.MyPredicateFactory;
import de.be4.classicalb.core.parser.node.APropertiesMachineClause;

public class Properties {

	
	private APropertiesMachineClause propertiesClause;
	private MyPredicate predicate;
	
	
	
	public Properties(APropertiesMachineClause properties) {
		this.propertiesClause = properties;
		this.predicate = MyPredicateFactory.convertPredicate(this.propertiesClause.getPredicates());
	}
	
	
	
	public Set<MyPredicate> getPropertiesClauses() {
		Set<MyPredicate> clauses = new HashSet<MyPredicate>();
		this.predicate.createClausesList(clauses);
		return clauses;
	}
	
	
	
	public Set<String> getPropertiesClausesList() {
		Set<String> clauses = new HashSet<String>();
		
		for (MyPredicate propertie : getPropertiesClauses()) {
			clauses.add(propertie.toString());
		}
		 
		return clauses;
	}
	
	
	
	@Override
	public String toString() {
		StringBuffer propertiesText = new StringBuffer("");
		propertiesText.append("PROPERTIES\n");
		
		MyPredicate propertiesPredicate = MyPredicateFactory.convertPredicate(this.propertiesClause.getPredicates());
		propertiesText.append(propertiesPredicate.toString() + "\n");
		
		propertiesText.append("\n");
		
		return propertiesText.toString();
	}
}
