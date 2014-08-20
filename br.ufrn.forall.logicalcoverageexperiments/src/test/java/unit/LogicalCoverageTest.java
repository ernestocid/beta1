package unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import parser.Machine;
import parser.Operation;
import parser.decorators.predicates.MyPredicate;
import criteria.PredicateCoverage;

public class LogicalCoverageTest extends TestingUtils {
	

	@Test
	public void shouldGetPredicatesSet() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		// Setting up mocked expected results
		
		Set<MyPredicate> expectedPredicates = new HashSet<MyPredicate>();
		
		MyPredicate mockedPredicate1 = mock(MyPredicate.class);
		when(mockedPredicate1.toString()).thenReturn("averageGrade : 0..5 & averageGrade : INT");
		
		MyPredicate mockedPredicate2 = mock(MyPredicate.class);
		when(mockedPredicate2.toString()).thenReturn("averageGrade >= 4");
		
		MyPredicate mockedPredicate3 = mock(MyPredicate.class);
		when(mockedPredicate3.toString()).thenReturn("averageGrade >= 2 & averageGrade < 4");
		
		expectedPredicates.add(mockedPredicate1);
		expectedPredicates.add(mockedPredicate2);
		expectedPredicates.add(mockedPredicate3);
		
		// Assertions
		
		assertTrue(compare(expectedPredicates, pc.getPredicates()));
	}
	
	
	
	@Test
	public void shouldGetPredicatesInOrder() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		// Setting up mocked expected results
		
		List<MyPredicate> expectedPredicates = new ArrayList<MyPredicate>();
		
		MyPredicate mockedPredicate1 = mock(MyPredicate.class);
		when(mockedPredicate1.toString()).thenReturn("averageGrade : 0..5 & averageGrade : INT");
		
		MyPredicate mockedPredicate2 = mock(MyPredicate.class);
		when(mockedPredicate2.toString()).thenReturn("averageGrade >= 2 & averageGrade < 4");
		
		MyPredicate mockedPredicate3 = mock(MyPredicate.class);
		when(mockedPredicate3.toString()).thenReturn("averageGrade >= 4");
		
		expectedPredicates.add(mockedPredicate1);
		expectedPredicates.add(mockedPredicate2);
		expectedPredicates.add(mockedPredicate3);
		
		// Assertions
		
		assertTrue(compare(expectedPredicates, pc.getOrderedPredicates()));
	}
	
	
	
	@Test
	public void shouldGetPredicatesForCaseStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/CaseStmt.mch"));
		Operation operationUnderTest = machine.getOperation(1);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<MyPredicate> expectedPredicates = new HashSet<MyPredicate>();
		
		MyPredicate mockedPredicate1 = mock(MyPredicate.class);
		when(mockedPredicate1.toString()).thenReturn("yy : ID");
		
		MyPredicate mockedPredicate2 = mock(MyPredicate.class);
		when(mockedPredicate2.toString()).thenReturn("yy = aa");
		
		MyPredicate mockedPredicate3 = mock(MyPredicate.class);
		when(mockedPredicate3.toString()).thenReturn("yy = bb");
		
		expectedPredicates.add(mockedPredicate1);
		expectedPredicates.add(mockedPredicate2);
		expectedPredicates.add(mockedPredicate3);
		
		assertTrue(compare(expectedPredicates, pc.getPredicates()));
	}
	
	
	

	@Test
	public void shouldGetPredicatesForSelectStatement() {
		Machine machine = new Machine(new File("src/test/resources/machines/Priorityqueue.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);
		
		// Setting up expected results
		
		Set<MyPredicate> expectedPredicates = new HashSet<MyPredicate>();
		
		MyPredicate mockedPredicate1 = mock(MyPredicate.class);
		when(mockedPredicate1.toString()).thenReturn("nn : NAT");
		
		MyPredicate mockedPredicate2 = mock(MyPredicate.class);
		when(mockedPredicate2.toString()).thenReturn("queue = []");
		
		MyPredicate mockedPredicate3 = mock(MyPredicate.class);
		when(mockedPredicate3.toString()).thenReturn("queue /= [] & nn <= min(ran(queue))");
		
		MyPredicate mockedPredicate4 = mock(MyPredicate.class);
		when(mockedPredicate4.toString()).thenReturn("queue /= [] & nn >= max(ran(queue))");
		
		expectedPredicates.add(mockedPredicate1);
		expectedPredicates.add(mockedPredicate2);
		expectedPredicates.add(mockedPredicate3);
		expectedPredicates.add(mockedPredicate4);
		
		// Assertions
		
		assertTrue(compare(expectedPredicates, pc.getPredicates()));
	}

	
	
	@Test
	public void shouldGetClausesSet() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		// Setting up mocked expected results
		
		Set<MyPredicate> expectedClauses = new HashSet<MyPredicate>();
		
		MyPredicate mockedClause1 = mock(MyPredicate.class);
		when(mockedClause1.toString()).thenReturn("averageGrade : 0..5");
		
		MyPredicate mockedClause2 = mock(MyPredicate.class);
		when(mockedClause2.toString()).thenReturn("averageGrade : INT");
		
		MyPredicate mockedClause3 = mock(MyPredicate.class);
		when(mockedClause3.toString()).thenReturn("averageGrade >= 4");
		
		MyPredicate mockedClause4 = mock(MyPredicate.class);
		when(mockedClause4.toString()).thenReturn("averageGrade >= 2");
		
		MyPredicate mockedClause5 = mock(MyPredicate.class);
		when(mockedClause5.toString()).thenReturn("averageGrade < 4");
		
		expectedClauses.add(mockedClause1);
		expectedClauses.add(mockedClause2);
		expectedClauses.add(mockedClause3);
		expectedClauses.add(mockedClause4);
		expectedClauses.add(mockedClause5);

		// Assertions
		
		assertTrue(compare(expectedClauses, pc.getClauses()));
	}
	
	
	
	@Test
	public void shouldGetClausesInOrder() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		// Setting up mocked expected results
		
		List<MyPredicate> expectedClauses = new ArrayList<MyPredicate>();
		
		MyPredicate mockedClause1 = mock(MyPredicate.class);
		when(mockedClause1.toString()).thenReturn("averageGrade : 0..5");
		
		MyPredicate mockedClause2 = mock(MyPredicate.class);
		when(mockedClause2.toString()).thenReturn("averageGrade : INT");
		
		MyPredicate mockedClause3 = mock(MyPredicate.class);
		when(mockedClause3.toString()).thenReturn("averageGrade < 4");
		
		MyPredicate mockedClause4 = mock(MyPredicate.class);
		when(mockedClause4.toString()).thenReturn("averageGrade >= 2");
		
		MyPredicate mockedClause5 = mock(MyPredicate.class);
		when(mockedClause5.toString()).thenReturn("averageGrade >= 4");
		
		
		
		expectedClauses.add(mockedClause1);
		expectedClauses.add(mockedClause2);
		expectedClauses.add(mockedClause3);
		expectedClauses.add(mockedClause4);
		expectedClauses.add(mockedClause5);

		// Assertions

		assertTrue(compare(expectedClauses, pc.getOrderedClauses()));
	}
	
	
	
	@Test
	public void shouldGetPredicateClauses(){
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		Set<MyPredicate> preconditionClauses = pc.getPredicateClauses(operationUnderTest.getPrecondition());

		// Setting up mocked expected results
		
		Set<MyPredicate> expectedClauses = new HashSet<MyPredicate>();
		
		MyPredicate mockedClause1 = mock(MyPredicate.class);
		when(mockedClause1.toString()).thenReturn("averageGrade : 0..5");
		
		MyPredicate mockedClause2 = mock(MyPredicate.class);
		when(mockedClause2.toString()).thenReturn("averageGrade : INT");

		expectedClauses.add(mockedClause1);
		expectedClauses.add(mockedClause2);
		
		// Assertions

		assertTrue(compare(expectedClauses, preconditionClauses));
	}
	
	
	
	@Test
	public void shouldCheckIfAClauseBelongsToAPredicate() {
		Machine machine = new Machine(new File("src/test/resources/machines/PassFinalOrFailIFELSIFELSE.mch"));
		Operation operationUnderTest = machine.getOperation(0);
		PredicateCoverage pc = new PredicateCoverage(operationUnderTest);

		MyPredicate mockedClause = mock(MyPredicate.class);
		when(mockedClause.toString()).thenReturn("averageGrade : 0..5");

		MyPredicate precondition = operationUnderTest.getPrecondition();
		
		assertTrue(pc.clauseBelongsToPredicate(mockedClause, precondition));
	}

}
