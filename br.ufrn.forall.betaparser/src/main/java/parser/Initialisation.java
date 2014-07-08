package parser;

import utils.SubstitutionTools;
import de.be4.classicalb.core.parser.node.AInitialisationMachineClause;
import de.be4.classicalb.core.parser.node.PSubstitution;

public class Initialisation {

	private AInitialisationMachineClause initialisation;

	public Initialisation(AInitialisationMachineClause initialisation) {
		this.initialisation = initialisation;
	}
	
	@Override
	public String toString() {
		PSubstitution substitution = initialisation.getSubstitutions();
		
		StringBuffer initialisationText = new StringBuffer("");
		initialisationText.append("INITIALISATION\n");
		initialisationText.append(SubstitutionTools.convertToString(substitution) + "\n");
		
		return initialisationText.toString();
	}

}
