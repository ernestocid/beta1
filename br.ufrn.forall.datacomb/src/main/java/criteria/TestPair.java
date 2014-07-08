package criteria;

import java.util.List;

public class TestPair<T> {

	
	private int valueAIndex;
	private int valueBIndex;
	private T valueA;
	private T valueB;
	
	
	public TestPair(int valueAIndex, int valueBIndex, T valueA, T valueB) {
		this.valueAIndex = valueAIndex;
		this.valueBIndex = valueBIndex;
		this.valueA = valueA;
		this.valueB = valueB;
	}

	
	
	public int getValueAIndex() {
		return valueAIndex;
	}

	
	
	public int getValueBIndex() {
		return valueBIndex;
	}

	
	
	public T getValueA() {
		return valueA;
	}

	
	
	public T getValueB() {
		return valueB;
	}
	
	
	
	@Override
	public String toString() {
		return "(" + valueA.toString() + ", " + valueB.toString() + ")";
	}
	
	
	// TODO: Check the use of generics here
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TestPair<?>) {
			TestPair<?> pair = (TestPair<?>) obj;
			if(pair.getValueA().equals(this.getValueA())
					&& pair.getValueB().equals(this.getValueB())
					&& pair.getValueAIndex() == this.getValueAIndex()
					&& pair.getValueBIndex() == this.getValueBIndex()) {
					
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	
	
	public boolean isCoveredBy(List<T> combination) {
		boolean isValueAOnCombination = combination.get(getValueAIndex()).equals(getValueA());
		boolean isValueBOnCombination = combination.get(getValueBIndex()).equals(getValueB());
		
		if(isValueAOnCombination && isValueBOnCombination) {
			return true;
		} else {
			return false;
		}
	}
}
