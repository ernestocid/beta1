MACHINE
  SimpleQueue
  
SEES
  Types
     
VARIABLES
  
  /** Different queues in FreeRTOS */
  queues,			/** Set of all queues, semaphores and mutexes **/
  queues_msg,			/** Set of queues on the system **/
  queues_msg_full,			/** Full Queue **/
  queues_msg_empty, 			/** Empty Queue **/
  
  
  /** miscellaneous queue properties */
  queue_items,			/** Set of items of a queue **/
  queue_receiving,			/** Set of tasks waiting to recive one item of queue **/
  queue_sending,			/** Set of tasks waiting to send one item to queue **/
  first_sending,			/** First task that has to be removed from the set of tasks blocked to write **/
  first_receiving			/** First task that has to be removed from the set of tasks blocked to wrread **/
  
INVARIANT
  queues : POW(QUEUE) & 
 
  queue_receiving : QUEUE +-> POW(TASK) & 
  queue_sending : QUEUE +-> POW(TASK) &
  
  queues = dom(queue_receiving) &
  queues = dom(queue_sending) &
  
  first_receiving : QUEUE +-> TASK &
  first_sending : QUEUE +-> TASK &
  
  dom(first_receiving) = dom(queue_receiving) &
  dom(first_sending) = dom(queue_sending)&
 
  
  !(q1).(q1 : queues & q1 : dom(first_receiving) & queue_receiving(q1) /= {} => first_receiving(q1) : queue_receiving(q1)) &
  
  !(q1).(q1 : queues & q1 : dom(first_sending) & queue_sending(q1) /= {} => first_sending(q1) : queue_sending(q1)) &
  
  queues_msg <: queues &
  queues_msg_full <: queues_msg &
  queues_msg_empty <: queues_msg &
  
  queues_msg_full/\queues_msg_empty = {} &
  
  queue_items : QUEUE +-> POW(ITEM) &
  
  queues_msg = dom(queue_items) &
  
  /** A task should not be in more than one set of blocked tasks **/
  !(q1,q2,tk).(q1:queues & q2:queues & tk:TASK & tk:queue_receiving(q1)=> tk/:queue_sending(q2))&
  !(q1,q2,tk).(q1:queues & q2:queues & tk:TASK & tk:queue_sending(q2)=> tk/:queue_receiving(q1))&
  !(q1,q2,tk).(q1:queues & q2:queues & q1/=q2 & tk:TASK & tk:queue_receiving(q1) => tk/:queue_receiving(q2))&
  !(q1,q2,tk).(q1:queues & q2:queues & q1/=q2 & tk:TASK & tk:queue_sending(q1) => tk/:queue_sending(q2))
  
ASSERTIONS
  !(que).(que : queues_msg & que : queues_msg_full => que /: queues_msg_empty)&
  !(que).(que : queues_msg & que : queues_msg_empty => que /: queues_msg_full)
   
INITIALISATION
  queues := {} ||
  queues_msg:={} ||
  queue_items := {} ||
  queue_receiving := {} ||
  queue_sending := {} ||
  queues_msg_full:={} ||
  queues_msg_empty := {} ||
  first_sending :={} ||
  first_receiving :={}
OPERATIONS
  
  
  /**********************************************************************************************************
  /brief Create Queue
  
  /do
  Create a new queue that will be manage by FreeRTOS. The length queue and size itens of queue 
  features aren't used in this level of abstraction and the decision of when the queue can or not be 
  create will be implemente in future refinement.
  
  /parameters
  uxQueueLength - Queue length - No used in this abstraction level
  uxItemSize - the size of one item of queue - No used in this abstraction level
  
  **********************************************************************************************************/
  xQueueHandle <-- q_queueCreate(uxQueueLength, uxItemSize) =
  PRE
    uxQueueLength : QUEUE_LENGTH & 
    uxItemSize : NAT
  THEN    
    
    ANY
      pxQueue 
    WHERE
      pxQueue : QUEUE & 
      pxQueue /: queues
    THEN	
      queues := queues \/ {pxQueue}||
      queues_msg := queues_msg \/ {pxQueue}||
      queue_items := queue_items \/ {pxQueue |-> {}} ||
      queue_receiving := queue_receiving \/ {pxQueue |-> {}} ||
      queue_sending := queue_sending \/ {pxQueue |-> {}} ||
      queues_msg_empty := queues_msg_empty \/ {pxQueue} ||
      xQueueHandle := pxQueue ||
      first_sending := first_sending \/ {pxQueue |-> TASK_NULL} ||
      first_receiving := first_receiving \/ {pxQueue |-> TASK_NULL}
    END		    
    
  END;
  
  /**********************************************************************************************************		
  /brief 
  Delete Queue		
  /do
  Delete a hardware abstraction Queue. This operation will delete only Queue that It aren't mutex or
  semaphore
  /parameters
  queue - Queue that will be delete
  
  **********************************************************************************************************/
  q_queueDelete(pxQueue) =
  PRE
    pxQueue : queues &
    queue_receiving(pxQueue) = {} &
    queue_sending(pxQueue) = {}
  THEN    
    queues := queues - {pxQueue} ||
    queues_msg := queues_msg - {pxQueue} ||
    queue_items := {pxQueue} <<| queue_items  ||
    queue_receiving := {pxQueue} <<| queue_receiving ||
    queue_sending := {pxQueue} <<| queue_sending ||
    first_sending := {pxQueue} <<| first_sending ||
    first_receiving := {pxQueue} <<| first_receiving||
    IF pxQueue : queues_msg_full
    THEN
        queues_msg_full := queues_msg_full - {pxQueue}
    END ||
    IF pxQueue : queues_msg_empty
    THEN
        queues_msg_empty := queues_msg_empty - {pxQueue}
    END
  END;
  
  /**********************************************************************************************************
  /brief 
  Send Item
  /do
  Inserts one item in the queue and remove the task from the set of tasks that are waiting to receive one item. 
  The given task shall be waiting on the given queue.   
  /parameters
  pxQueue - Queue that will send the item
  pxItem - Queue that will send to queue
  task - Task in reciving set that will unblock
  copy_position - Position that the item will insert in the queue, this can be queueSEND_TO_BACK,queueSEND_TO_FRONT, to insert 
  a item in front and black respective 
  **********************************************************************************************************/
  q_sendItem(pxQueue, pxItem, copy_position) =
  PRE
    pxQueue : queues_msg & 
    pxItem : ITEM & 
    pxItem /: queue_items(pxQueue) &
    copy_position : COPY_POSITION &	    
    pxQueue /: queues_msg_full 
  THEN
    queue_items(pxQueue) := queue_items(pxQueue) \/ {pxItem} ||
    IF queue_receiving(pxQueue) /= {}
    THEN
      ANY n_receiving, n_first
      WHERE
        n_receiving : POW(TASK) &
        n_first : TASK &
        n_receiving = (queue_receiving(pxQueue) - {first_receiving(pxQueue)})&
        n_first : n_receiving
      THEN
        queue_receiving(pxQueue) := n_receiving||
      	first_receiving(pxQueue) := n_first
      END
    END ||  
    IF pxQueue:queues_msg_empty
    THEN
        queues_msg_empty := queues_msg_empty - {pxQueue}
    END ||
    CHOICE
      queues_msg_full := queues_msg_full \/ {pxQueue}
    OR
      skip
    END
  END;
  
  /****************************************************************************************************************************
  /brief Received Item Recive
  
  /do
  Remove or not one item of indicated queue and remove the passed task from the set of tasks waiting to send. For remove 
  the intem the parameter justPeeking shall be false, for only read the item in the queue without remove then, the paramenter
  justPeeking shall be true. 
  
  /parameters
  pxQueue - Queue that the task will be insert in the sending
  justPeeking - Flag that indicate if the item will reade and remove or only read
  pxTask - Task that have the item that will be read
  ****************************************************************************************************************************/
  xItem <-- q_receivedItem(pxQueue,justPeeking) =
  PRE
    pxQueue : queues_msg & 
    justPeeking : BOOL & 
    pxQueue /: queues_msg_empty 
  THEN
    ANY
      item
    WHERE
      item : ITEM & 
      item : queue_items(pxQueue)
    THEN
      IF justPeeking = FALSE THEN       
        IF queue_sending(pxQueue) /= {} THEN
          ANY n_sending, n_first
          WHERE
            n_sending : POW(TASK) &
            n_first : TASK &
            n_sending = (queue_sending(pxQueue) - {first_sending(pxQueue)}) &
            n_first : n_sending
          THEN
            queue_items(pxQueue) := queue_items(pxQueue) - {item} ||
            queue_sending(pxQueue) := n_sending||            
           	first_sending(pxQueue) := n_first||
            IF pxQueue : queues_msg_full THEN
              queues_msg_full := queues_msg_full - {pxQueue}
            END||
            CHOICE
              queues_msg_empty := queues_msg_empty \/ {pxQueue}
            OR
              skip
            END
          END
        END
      END ||
      xItem := item
    END
  END;
  
  /**********************************************************************************************************
  /brief
  Insert Task in Wainting To Send
  /do
  Insert a task in task wainting to send list
  **********************************************************************************************************/
  q_insertTaskWaitingToSend(pxQueue, pxTask) =
  PRE
    pxQueue : queues & 
    pxTask : TASK &
    !q1.(q1 : queues => pxTask /: queue_sending(q1) & pxTask /: queue_receiving(q1))
  THEN
    queue_sending(pxQueue) := queue_sending(pxQueue) \/ {pxTask}||
    IF queue_sending(pxQueue) ={} THEN
      first_sending(pxQueue) := pxTask
    END
  END;
  
  
  
  /**********************************************************************************************************
  /brief Insert Task in Waiting to Send
  
  /do
  Insert a task in the set of tasks waiting to send one item to passed queue
  
  /parameters
  pxQueue - Queue that the task will be insert in the sending
  pxTask - Task that will insert in the set sending
  **********************************************************************************************************/
  q_insertTaskWaitingToReceive(pxQueue, pxTask) =
  PRE
    pxQueue : queues &
    pxTask : TASK &
    !q1.(q1:queues => pxTask /: queue_sending(q1) & pxTask /: queue_receiving(q1))
  THEN
    queue_receiving(pxQueue) := queue_receiving(pxQueue) \/ {pxTask}||
    IF queue_receiving(pxQueue) = {} THEN
      first_receiving(pxQueue) := pxTask
    END
  END	;
  
  /**
  /brief 
  Remove From Event List Queue
  /do
  Remove a task from all events sets (reciving, sending) in all queues of FreeRTOS
  
  /parameters
  task - Task that will be removing
  
  **/	
  q_removeFromEventListQueue(task) =
  PRE
    task : TASK  
  THEN
    ANY
      pxQueue, n_receiving , n_first
    WHERE
      pxQueue : queues &
      task:queue_receiving(pxQueue) &
      n_receiving : POW(TASK) &
      n_receiving = queue_receiving(pxQueue) - {first_receiving(pxQueue)} &
      n_first : TASK&
      n_first : queue_receiving(pxQueue)
    THEN
      queue_receiving(pxQueue) := n_receiving||
      first_receiving(pxQueue) := n_first
    END ||
    ANY
      pxQueue, n_sending, n_first
    WHERE
      pxQueue : queues &
      n_sending : POW(TASK) &
      n_first : TASK &
      task:queue_sending(pxQueue) &
      n_sending = queue_sending(pxQueue) - {first_sending(pxQueue)} &
      n_first = first_sending(pxQueue)
    THEN
      queue_sending(pxQueue) := n_sending||
      first_sending(pxQueue) := n_first
    END
  END
END