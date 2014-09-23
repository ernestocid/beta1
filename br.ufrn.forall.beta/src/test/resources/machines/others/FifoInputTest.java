
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
* Machine: Fifo
* Operation: input
*
* Partition Strategy: Equivalence Classes
* Combination Strategy: All-Combinations
* Oracle Strategy: Check the state invariants, state variables and return values
*/
public class FifoInputTest { 

	private Fifo fifo;

	@Before
	public void setUp() throws Exception {
		fifo = new Fifo();
	} 
	
	@After
	public void checkInvariant() throws Exception {
		// implement something to check the state invariants
	}
	
	
	/**
	* Test Case 1
	* Formula: size(contents) <= cap & not(size(contents) < cap) & contents : seq(INT) & ee : INT
	*/
	@Test
	public void testCase1() {
		// State   
		seq(INT) contents = [-20];
		fifo.setContents(contents);
		 
		// Input Values   
		int ee = -20;
		 
		
		fifo.input(ee);
		
		
		
		seq(INT) contentsExpected;	// Add expected value here.
		assertEquals(fifo.getContents(), contentsExpected);
		   
	}
	
	/**
	* Test Case 2
	* Formula: size(contents) <= cap & size(contents) < cap & contents : seq(INT) & ee : INT
	*/
	@Test
	public void testCase2() {
		// State   
		seq(INT) contents = [];
		fifo.setContents(contents);
		 
		// Input Values   
		int ee = -20;
		 
		
		fifo.input(ee);
		
		
		
		seq(INT) contentsExpected;	// Add expected value here.
		assertEquals(fifo.getContents(), contentsExpected);
		   
	}
	
}

