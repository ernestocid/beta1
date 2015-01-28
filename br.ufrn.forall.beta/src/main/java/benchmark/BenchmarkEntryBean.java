package benchmark;

public class BenchmarkEntryBean {

	private String operationName;

	private int equivalentClassesEachChoiceFeasible;
	private int equivalentClassesEachChoiceInfeasible;
	private long equivalentClassesEachChoiceTime;

	private int equivalentClassesPairwiseFeasible;
	private int equivalentClassesPairwiseInfeasible;
	private long equivalentClassesPairwiseTime;

	private int equivalentClassesAllCombinationsFeasible;
	private int equivalentClassesAllCombinationsInfeasible;
	private long equivalentClassesAllCombinationsTime;

	private int boundaryValuesEachChoiceFeasible;
	private int boundaryValuesEachChoiceInfeasible;
	private long boundaryValuesEachChoiceTime;

	private int boundaryValuesPairwiseFeasible;
	private int boundaryValuesPairwiseInfeasible;
	private long boundaryValuesPairwiseTime;

	private int boundaryValuesAllCombinationsFeasible;
	private int boundaryValuesAllCombinationsInfeasible;
	private long boundaryValuesAllCombinationsTime;

	private int predicateCoverageFeasible;
	private int predicateCoverageInfeasible;
	private long predicateCoverageTime;

	private int clauseCoverageFeasible;
	private int clauseCoverageInfeasible;
	private long clauseCoverageTime;

	private int combinatorialClauseCoverageFeasible;
	private int combinatorialClauseCoverageInfeasible;
	private long combinatorialClauseCoverageTime;

	private int activeClauseCoverageFeasible;
	private int activeClauseCoverageInfeasible;
	private long activeClauseCoverageTime;



	public BenchmarkEntryBean() {
	}



	public String getOperationName() {
		return operationName;
	}



	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}



	public int getEquivalentClassesEachChoiceFeasible() {
		return equivalentClassesEachChoiceFeasible;
	}



	public void setEquivalentClassesEachChoiceFeasible(int equivalentClassesEachChoiceFeasible) {
		this.equivalentClassesEachChoiceFeasible = equivalentClassesEachChoiceFeasible;
	}



	public int getEquivalentClassesEachChoiceInfeasible() {
		return equivalentClassesEachChoiceInfeasible;
	}



	public void setEquivalentClassesEachChoiceInfeasible(int equivalentClassesEachChoiceInfeasible) {
		this.equivalentClassesEachChoiceInfeasible = equivalentClassesEachChoiceInfeasible;
	}



	public long getEquivalentClassesEachChoiceTime() {
		return equivalentClassesEachChoiceTime;
	}



	public void setEquivalentClassesEachChoiceTime(long equivalentClassesEachChoiceTime) {
		this.equivalentClassesEachChoiceTime = equivalentClassesEachChoiceTime;
	}



	public int getEquivalentClassesPairwiseFeasible() {
		return equivalentClassesPairwiseFeasible;
	}



	public void setEquivalentClassesPairwiseFeasible(int equivalentClassesPairwiseFeasible) {
		this.equivalentClassesPairwiseFeasible = equivalentClassesPairwiseFeasible;
	}



	public int getEquivalentClassesPairwiseInfeasible() {
		return equivalentClassesPairwiseInfeasible;
	}



	public void setEquivalentClassesPairwiseInfeasible(int equivalentClassesPairwiseInfeasible) {
		this.equivalentClassesPairwiseInfeasible = equivalentClassesPairwiseInfeasible;
	}



	public long getEquivalentClassesPairwiseTime() {
		return equivalentClassesPairwiseTime;
	}



	public void setEquivalentClassesPairwiseTime(long equivalentClassesPairwiseTime) {
		this.equivalentClassesPairwiseTime = equivalentClassesPairwiseTime;
	}



	public int getEquivalentClassesAllCombinationsFeasible() {
		return equivalentClassesAllCombinationsFeasible;
	}



	public void setEquivalentClassesAllCombinationsFeasible(int equivalentClassesAllCombinationsFeasible) {
		this.equivalentClassesAllCombinationsFeasible = equivalentClassesAllCombinationsFeasible;
	}



	public int getEquivalentClassesAllCombinationsInfeasible() {
		return equivalentClassesAllCombinationsInfeasible;
	}



	public void setEquivalentClassesAllCombinationsInfeasible(int equivalentClassesAllCombinationsInfeasible) {
		this.equivalentClassesAllCombinationsInfeasible = equivalentClassesAllCombinationsInfeasible;
	}



	public long getEquivalentClassesAllCombinationsTime() {
		return equivalentClassesAllCombinationsTime;
	}



	public void setEquivalentClassesAllCombinationsTime(long equivalentClassesAllCombinationsTime) {
		this.equivalentClassesAllCombinationsTime = equivalentClassesAllCombinationsTime;
	}



	public int getBoundaryValuesEachChoiceFeasible() {
		return boundaryValuesEachChoiceFeasible;
	}



	public void setBoundaryValuesEachChoiceFeasible(int boundaryValuesEachChoiceFeasible) {
		this.boundaryValuesEachChoiceFeasible = boundaryValuesEachChoiceFeasible;
	}



	public int getBoundaryValuesEachChoiceInfeasible() {
		return boundaryValuesEachChoiceInfeasible;
	}



	public void setBoundaryValuesEachChoiceInfeasible(int boundaryValuesEachChoiceInfeasible) {
		this.boundaryValuesEachChoiceInfeasible = boundaryValuesEachChoiceInfeasible;
	}



	public long getBoundaryValuesEachChoiceTime() {
		return boundaryValuesEachChoiceTime;
	}



	public void setBoundaryValuesEachChoiceTime(long boundaryValuesEachChoiceTime) {
		this.boundaryValuesEachChoiceTime = boundaryValuesEachChoiceTime;
	}



	public int getBoundaryValuesPairwiseFeasible() {
		return boundaryValuesPairwiseFeasible;
	}



	public void setBoundaryValuesPairwiseFeasible(int boundaryValuesPairwiseFeasible) {
		this.boundaryValuesPairwiseFeasible = boundaryValuesPairwiseFeasible;
	}



	public int getBoundaryValuesPairwiseInfeasible() {
		return boundaryValuesPairwiseInfeasible;
	}



	public void setBoundaryValuesPairwiseInfeasible(int boundaryValuesPairwiseInfeasible) {
		this.boundaryValuesPairwiseInfeasible = boundaryValuesPairwiseInfeasible;
	}



	public long getBoundaryValuesPairwiseTime() {
		return boundaryValuesPairwiseTime;
	}



	public void setBoundaryValuesPairwiseTime(long boundaryValuesPairwiseTime) {
		this.boundaryValuesPairwiseTime = boundaryValuesPairwiseTime;
	}



	public int getBoundaryValuesAllCombinationsFeasible() {
		return boundaryValuesAllCombinationsFeasible;
	}



	public void setBoundaryValuesAllCombinationsFeasible(int boundaryValuesAllCombinationsFeasible) {
		this.boundaryValuesAllCombinationsFeasible = boundaryValuesAllCombinationsFeasible;
	}



	public int getBoundaryValuesAllCombinationsInfeasible() {
		return boundaryValuesAllCombinationsInfeasible;
	}



	public void setBoundaryValuesAllCombinationsInfeasible(int boundaryValuesAllCombinationsInfeasible) {
		this.boundaryValuesAllCombinationsInfeasible = boundaryValuesAllCombinationsInfeasible;
	}



	public long getBoundaryValuesAllCombinationsTime() {
		return boundaryValuesAllCombinationsTime;
	}



	public void setBoundaryValuesAllCombinationsTime(long boundaryValuesAllCombinationsTime) {
		this.boundaryValuesAllCombinationsTime = boundaryValuesAllCombinationsTime;
	}



	public int getPredicateCoverageFeasible() {
		return predicateCoverageFeasible;
	}



	public void setPredicateCoverageFeasible(int predicateCoverageFeasible) {
		this.predicateCoverageFeasible = predicateCoverageFeasible;
	}



	public int getPredicateCoverageInfeasible() {
		return predicateCoverageInfeasible;
	}



	public void setPredicateCoverageInfeasible(int predicateCoverageInfeasible) {
		this.predicateCoverageInfeasible = predicateCoverageInfeasible;
	}



	public long getPredicateCoverageTime() {
		return predicateCoverageTime;
	}



	public void setPredicateCoverageTime(long predicateCoverageTime) {
		this.predicateCoverageTime = predicateCoverageTime;
	}



	public int getClauseCoverageFeasible() {
		return clauseCoverageFeasible;
	}



	public void setClauseCoverageFeasible(int clauseCoverageFeasible) {
		this.clauseCoverageFeasible = clauseCoverageFeasible;
	}



	public int getClauseCoverageInfeasible() {
		return clauseCoverageInfeasible;
	}



	public void setClauseCoverageInfeasible(int clauseCoverageInfeasible) {
		this.clauseCoverageInfeasible = clauseCoverageInfeasible;
	}



	public long getClauseCoverageTime() {
		return clauseCoverageTime;
	}



	public void setClauseCoverageTime(long clauseCoverageTime) {
		this.clauseCoverageTime = clauseCoverageTime;
	}



	public int getCombinatorialClauseCoverageFeasible() {
		return combinatorialClauseCoverageFeasible;
	}



	public void setCombinatorialClauseCoverageFeasible(int combinatorialClauseCoverageFeasible) {
		this.combinatorialClauseCoverageFeasible = combinatorialClauseCoverageFeasible;
	}



	public int getCombinatorialClauseCoverageInfeasible() {
		return combinatorialClauseCoverageInfeasible;
	}



	public void setCombinatorialClauseCoverageInfeasible(int combinatorialClauseCoverageInfeasible) {
		this.combinatorialClauseCoverageInfeasible = combinatorialClauseCoverageInfeasible;
	}



	public long getCombinatorialClauseCoverageTime() {
		return combinatorialClauseCoverageTime;
	}



	public void setCombinatorialClauseCoverageTime(long combinatorialClauseCoverageTime) {
		this.combinatorialClauseCoverageTime = combinatorialClauseCoverageTime;
	}



	public int getActiveClauseCoverageFeasible() {
		return activeClauseCoverageFeasible;
	}



	public void setActiveClauseCoverageFeasible(int activeClauseCoverageFeasible) {
		this.activeClauseCoverageFeasible = activeClauseCoverageFeasible;
	}



	public int getActiveClauseCoverageInfeasible() {
		return activeClauseCoverageInfeasible;
	}



	public void setActiveClauseCoverageInfeasible(int activeClauseCoverageInfeasible) {
		this.activeClauseCoverageInfeasible = activeClauseCoverageInfeasible;
	}



	public long getActiveClauseCoverageTime() {
		return activeClauseCoverageTime;
	}



	public void setActiveClauseCoverageTime(long activeClauseCoverageTime) {
		this.activeClauseCoverageTime = activeClauseCoverageTime;
	}

}
