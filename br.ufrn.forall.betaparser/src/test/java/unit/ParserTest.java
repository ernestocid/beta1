package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import parser.Machine;
import parser.Operation;


public class ParserTest {

	
	private Machine machineHandler;

	
	
	@Before
	public void setUp() {
		this.machineHandler = new Machine(new File("src/test/resources/machines/others/Test.mch"));
	}
	
	
	
	@Test
	public void shouldParseBelongsAndDoestBelongsToFromOperationPreconditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/BelongAndNotBelongPredicatesTestMachine.mch");

		Operation operation = machine.getOperation(0);
		String precondition = operation.getPrecondition().toString();
		assertEquals("var1 : ASET & var2 /: ASET", precondition);
	}
	
	
	
	@Test
	public void shouldParseRelationalOperatorsFromOperationPreconditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/RelationalOperatorsTestMachine.mch");
		
		Operation operation = machine.getOperation(0);
		String precondition = operation.getPrecondition().toString();
		assertEquals("var1 = var2 & var2 /= var3 & var1 >= var3 & var2 <= var3 & var1 > var3 & var2 < var3", precondition.trim());
	}
	
	
	
	@Test
	public void shouldParseConjunctFromOperationPreconditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/ConjunctPredicateTestMachine.mch");
		
		Operation operation = machine.getOperation(0);
		String precondition = operation.getPrecondition().toString();
		assertEquals("test_variable = 10 & test_variable = another_test_variable", precondition);
	}
	
	
	
	@Test
	public void shouldParseDisjunctFromOperationPreconditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/DisjunctPredicateTestMachine.mch");
		
		Operation operation = machine.getOperation(0);
		String precondition = operation.getPrecondition().toString();
		assertEquals("test_variable = 10 or test_variable = another_test_variable", precondition);
	}
	
	
	
	@Test
	public void shouldParseImplicaltionFromOperationPreconditions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/ImplicationPredicateTestMachine.mch");
		
		Operation operation = machine.getOperation(0);
		String precondition = operation.getPrecondition().toString();
		assertEquals("((var1 = TRUE) => (var2 = FALSE))", precondition);
	}
	
	
	
	@Test
	public void shouldParseFalseAndTrueExpressions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/FalseAndTrueExpressionsTestMachine.mch");

		Operation operation = machine.getOperation(0);
		String precondition = operation.getPrecondition().toString();
		assertEquals("test_variable = TRUE or another_test_variable = FALSE", precondition);
	}
	
	
	
	@Test
	public void shouldExtractPreconditionClauses() {
		Operation operation = machineHandler.getOperation(0);
		
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("pressed : BOOL");
		expectedList.add("((pressed = TRUE) => (foo = FALSE))");
		Collections.sort(expectedList);
		
		List<String> clauses = operation.getPreconditionClausesAsList();
		assertEquals(expectedList, clauses);
	}
	
	
	
	@Test
	public void shouldExtractInvariantClauses() {
		List<String> expectedList = new ArrayList<String>();

		expectedList.add("cab_consol_open_button_pushed : BOOL");
		expectedList.add("cab_side_open_button_pushed : BOOL");
		expectedList.add("cab_consol_close_button_pushed : BOOL");
		expectedList.add("cab_side_close_button_pushed : BOOL");
		expectedList.add("foo : BOOL");
		expectedList.add("test_variable <: NAT1");
		expectedList.add("test_variable2 <: NAT1");
		expectedList.add("test_variable3 <: NAT1");
		expectedList.add("((test_variable2 = test_variable) => (test_variable3 /= test_variable))");
		expectedList.add("((test_variable3 = test_variable) => (foo = TRUE))");
		
		Collections.sort(expectedList);

		List<String> clauses = machineHandler.getInvariant().getClausesAsList();
		
		assertEquals(expectedList, clauses);
	}
	
	
	// TODO: Refactor this test. Add a test machine for all kinds of predicates, maybe?
	@Test
	public void shouldParseFreeRTOSMachine() {
		Machine machine = getMachineInstance("src/test/resources/machines/FreeRTOS/FreeRTOS.mch");
		
		Operation xQueueSendToBack = machine.getOperation(0);
		List<String> xQueueSendToBackPrecondition = xQueueSendToBack.getPreconditionClausesAsList();
		
		List<String> expectedxQueueSendToBackResult = new ArrayList<String>();
		expectedxQueueSendToBackResult.add("queue : queues_msg");
		expectedxQueueSendToBackResult.add("item : ITEM");
		expectedxQueueSendToBackResult.add("ticks : TICK");
		expectedxQueueSendToBackResult.add("active = TRUE");
		expectedxQueueSendToBackResult.add("running /= idle");
		expectedxQueueSendToBackResult.add("not(item : queue_items(queue))");
		Collections.sort(expectedxQueueSendToBackResult);
		
		assertEquals(expectedxQueueSendToBackResult, xQueueSendToBackPrecondition);
	}
	
	
	
	@Test
	public void shouldParseTaskMachine() {
		Machine machine = getMachineInstance("src/test/resources/machines/FreeRTOS/Task.mch");

		List<String> invariantClauses = machine.getInvariant().getClausesAsList();
		List<String> expectedResult = new ArrayList<String>();

		expectedResult.add("active : BOOL");
		expectedResult.add("tickCount : TICK");
		expectedResult.add("tickMissed : TICK");
		expectedResult.add("tasks : FIN(TASK)");
		expectedResult.add("blocked : FIN(TASK)");
		expectedResult.add("blocked <: tasks");
		expectedResult.add("unblocked : FIN(TASK)");
		expectedResult.add("unblocked <: blocked");
		expectedResult.add("runable : FIN(TASK)");
		expectedResult.add("runable <: tasks");
		expectedResult.add("suspended : FIN(TASK)");
		expectedResult.add("suspended <: tasks");
		expectedResult.add("runable /\\ blocked = {}");
		expectedResult.add("blocked /\\ suspended = {}");
		expectedResult.add("suspended /\\ runable = {}");
		expectedResult.add("tasks = suspended \\/ blocked \\/ runable");
		expectedResult.add("running : TASK");
		expectedResult.add("idle : TASK");
		expectedResult.add("((active = TRUE) => (runable /= {} & running : runable & idle : runable & TASK_NULL /: tasks))");
		
		Collections.sort(expectedResult);  
		
		assertEquals(expectedResult, invariantClauses);  
	}
	
	
	
	@Test
	public void shouldParseQuantifierPredicateClause() {
		Machine machine = getMachineInstance("src/test/resources/machines/FreeRTOS/FreeRTOSBasic.mch");
		
		List<String> actualResult = machine.getInvariant().getClausesAsList();
		
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("!(que).((que : queues) => (queue_sending(que) <: blocked \\/ suspended))");
		expectedResult.add("!(que).((que : queues) => (queue_receiving(que) <: blocked \\/ suspended))");
		expectedResult.add("ran(mutex_holder) <: tasks \\/ {TASK_NULL}");
		expectedResult.add("!(mt).((mt : mutexes) => (queue_sending(mt) = {}))");
		expectedResult.add("((active = TRUE) => " +
		    "(idle /: ran(mutex_holder) & " +
		    "!(mt).((mt : mutexes_busy) => (mutex_holder(mt) : tasks)) & " +
		    "!(mt).((mt : mutexes & mt /: mutexes_busy) => (mutex_holder(mt) /: tasks))))");
		Collections.sort(expectedResult);
		
		assertEquals(expectedResult, actualResult);
	}
	
	
	
	@Test
	public void shouldParseQueueInvariant() {
		Machine machine = getMachineInstance("src/test/resources/machines/estudo/SimpleQueue.mch");
		
		Set<String> expectedInvariantClauses = new HashSet<String>();
		
		expectedInvariantClauses.add("queues_msg /\\ semaphores = {}"); 
		expectedInvariantClauses.add("!(q1).((q1 : queues & q1 : dom(first_sending) & queue_sending(q1) /= {}) => (first_sending(q1) : queue_sending(q1)))"); 
		expectedInvariantClauses.add("!(mt).((mt : mutexes & mt /: mutexes_busy) => (mutex_holder(mt) = TASK_NULL))");
		expectedInvariantClauses.add("mutexes_busy <: mutexes");
		expectedInvariantClauses.add("!(q1,q2,tk).((q1 : queues & q2 : queues & tk : TASK & tk : queue_sending(q2)) => (tk /: queue_receiving(q1)))"); 
		expectedInvariantClauses.add("semaphores_busy /\\ semaphores_full = {}");
		expectedInvariantClauses.add("mutexes = dom(mutex_holder)");
		expectedInvariantClauses.add("queues_msg /\\ mutexes = {}");
		expectedInvariantClauses.add("queues_msg <: queues");
		expectedInvariantClauses.add("queue_sending : (QUEUE +-> POW(TASK))"); 
		expectedInvariantClauses.add("!(q1).((q1 : queues & q1 : dom(first_receiving) & queue_receiving(q1) /= {}) => (first_receiving(q1) : queue_receiving(q1)))"); 
		expectedInvariantClauses.add("queues : POW(QUEUE)");
		expectedInvariantClauses.add("dom(first_sending) = dom(queue_sending)"); 
		expectedInvariantClauses.add("mutexes /\\ semaphores = {}");
		expectedInvariantClauses.add("semaphores <: queues");
		expectedInvariantClauses.add("queues = dom(queue_receiving)"); 
		expectedInvariantClauses.add("queues_msg_empty <: queues_msg");
		expectedInvariantClauses.add("!(q1,q2,tk).((q1 : queues & q2 : queues & q1 /= q2 & tk : TASK & tk : queue_receiving(q1)) => (tk /: queue_receiving(q2)))"); 
		expectedInvariantClauses.add("queue_items : (QUEUE +-> POW(ITEM))");
		expectedInvariantClauses.add("mutex_holder : (QUEUE +-> TASK)");
		expectedInvariantClauses.add("!(mt).((mt : mutexes_busy) => (mutex_holder(mt) /= TASK_NULL))"); 
		expectedInvariantClauses.add("first_receiving : (QUEUE +-> TASK)");
		expectedInvariantClauses.add("queues_msg_full /\\ queues_msg_empty = {}"); 
		expectedInvariantClauses.add("first_sending : (QUEUE +-> TASK)");
		expectedInvariantClauses.add("queues = dom(queue_sending)");
		expectedInvariantClauses.add("!(q1,q2,tk).((q1 : queues & q2 : queues & q1 /= q2 & tk : TASK & tk : queue_sending(q1)) => (tk /: queue_sending(q2)))"); 
		expectedInvariantClauses.add("queues_msg_full <: queues_msg");
		expectedInvariantClauses.add("semaphores : POW(QUEUE)");
		expectedInvariantClauses.add("dom(first_receiving) = dom(queue_receiving)"); 
		expectedInvariantClauses.add("!(q1,q2,tk).((q1 : queues & q2 : queues & tk : TASK & tk : queue_receiving(q1)) => (tk /: queue_sending(q2)))"); 
		expectedInvariantClauses.add("queue_receiving : (QUEUE +-> POW(TASK))");
		expectedInvariantClauses.add("semaphores_busy <: semaphores");
		expectedInvariantClauses.add("mutexes <: queues");
		expectedInvariantClauses.add("semaphores_full <: semaphores"); 
		expectedInvariantClauses.add("queues_msg = dom(queue_items)");
		
		Set<String> actualInvariantClauses = new HashSet<String>(machine.getInvariant().getClausesAsList());
		
		assertEquals(expectedInvariantClauses, actualInvariantClauses);
	}
	
	
	
	@Test
	public void shouldParseQueueProperties() {
		Machine machine = getMachineInstance("src/test/resources/machines/estudo/SimpleQueue.mch");
		
		Set<String> expectedPropertiesClauses = new HashSet<String>();
		
		expectedPropertiesClauses.add("remove_task : (((QUEUE +-> POW(TASK)) * POW(TASK)) --> (QUEUE +-> POW(TASK)))");
		expectedPropertiesClauses.add("remove_task = %(q_task,unblocked).(q_task : (QUEUE +-> POW(TASK)) & unblocked : POW(TASK) | %(q1).(q1 : QUEUE & q1 : dom(q_task) | (q_task(q1) - unblocked)))");

		assertEquals(expectedPropertiesClauses, machine.getProperties().getPropertiesClausesList());
	}
	
	
	
	private Machine getMachineInstance(String path) {
		Machine	machine = new Machine(new File(path));
		return machine;
	}
}