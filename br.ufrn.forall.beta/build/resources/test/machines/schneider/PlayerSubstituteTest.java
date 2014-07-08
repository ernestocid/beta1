
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
* Machine: Player
* Operation: substitute
*
* Partition Strategy: Equivalence Classes
* Combination Strategy: All-Combinations
* Oracle Strategy: Check the state invariants, state variables and return values
*/
public class PlayerSubstituteTest { 

	private Player player;

	@Before
	public void setUp() throws Exception {
		player = new Player();
	} 
	
	@After
	public void checkInvariant() throws Exception {
		// implement something to check the state invariants
	}
	
	
	/**
	* Test Case 1
	* Formula: not(pp : team) & team <: PLAYER & rr /: team & rr : PLAYER & card(team) = 11
	*/
	@Test
	public void testCase1() {
		// State   
		PLAYER[] team = {PLAYER1, PLAYER2, PLAYER3, PLAYER4, PLAYER5, PLAYER6, PLAYER7, PLAYER8, PLAYER9, PLAYER10, PLAYER11};
		player.setTeam(team);
		 
		// Input Values   
		PLAYER rr = PLAYER12;
		
		pp pp = PLAYER12;
		 
		
		player.substitute(rr, pp);
		
		
		
		PLAYER[] teamExpected;	// Add expected value here.
		assertEquals(player.getTeam(), teamExpected);
		   
	}
	
	/**
	* Test Case 2
	* Formula: not(pp : team) & team <: PLAYER & not(rr /: team) & rr : PLAYER & card(team) = 11
	*/
	@Test
	public void testCase2() {
		// State   
		PLAYER[] team = {PLAYER1, PLAYER2, PLAYER3, PLAYER4, PLAYER5, PLAYER6, PLAYER7, PLAYER8, PLAYER9, PLAYER10, PLAYER11};
		player.setTeam(team);
		 
		// Input Values   
		PLAYER rr = PLAYER1;
		
		pp pp = PLAYER12;
		 
		
		player.substitute(rr, pp);
		
		
		
		PLAYER[] teamExpected;	// Add expected value here.
		assertEquals(player.getTeam(), teamExpected);
		   
	}
	
	/**
	* Test Case 3
	* Formula: pp : team & team <: PLAYER & not(rr /: team) & rr : PLAYER & card(team) = 11
	*/
	@Test
	public void testCase3() {
		// State   
		PLAYER[] team = {PLAYER1, PLAYER2, PLAYER3, PLAYER4, PLAYER5, PLAYER6, PLAYER7, PLAYER8, PLAYER9, PLAYER10, PLAYER11};
		player.setTeam(team);
		 
		// Input Values   
		PLAYER rr = PLAYER1;
		
		team pp = PLAYER1;
		 
		
		player.substitute(rr, pp);
		
		
		
		PLAYER[] teamExpected;	// Add expected value here.
		assertEquals(player.getTeam(), teamExpected);
		   
	}
	
	/**
	* Test Case 4
	* Formula: pp : team & team <: PLAYER & rr /: team & rr : PLAYER & card(team) = 11
	*/
	@Test
	public void testCase4() {
		// State   
		PLAYER[] team = {PLAYER1, PLAYER2, PLAYER3, PLAYER4, PLAYER5, PLAYER6, PLAYER7, PLAYER8, PLAYER9, PLAYER10, PLAYER11};
		player.setTeam(team);
		 
		// Input Values   
		PLAYER rr = PLAYER12;
		
		team pp = PLAYER1;
		 
		
		player.substitute(rr, pp);
		
		
		
		PLAYER[] teamExpected;	// Add expected value here.
		assertEquals(player.getTeam(), teamExpected);
		   
	}
	
}

