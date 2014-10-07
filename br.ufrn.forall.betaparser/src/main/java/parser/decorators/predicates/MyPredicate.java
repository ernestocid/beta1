package parser.decorators.predicates;

import java.util.Set;

import de.be4.classicalb.core.parser.node.PPredicate;

public interface MyPredicate {

	PPredicate getNode();
	String toString();
	Set<MyPredicate> getClauses();
	Set<String> getVariables();
	boolean isTypingClause();
	boolean isInterval();
	@Override
	public boolean equals(Object obj);
}
