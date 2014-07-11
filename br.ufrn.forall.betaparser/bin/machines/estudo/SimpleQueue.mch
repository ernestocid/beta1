/**********************************************************************************************************
QueueCore

\brief Basic layer of definitions for queue support in FreeRTOS.

This machine defines the concept of a queue in FreeRTOS without the features of length queue and size queue itens.
Queues are modelled as sets, instead as true queues.

Author: Stephenson Galvao, David Deharbe
Creation date: Tue Jan 27 2009
Universidade Federal do Rio Grande do Norte
Departamento de Informática e Matemática Aplicada
Programa de Pos-Graduacao em Sistemas e Computacao
Formal Methods and Languages Research Laboratory
**********************************************************************************************************/ 

MACHINE
  SimpleQueue
  
SEES
  Types
  
  	    
VARIABLES
  
  /** Different queues in FreeRTOS */
  queues,
  
  queues_msg,
  queues_msg_full,
  queues_msg_empty,
 
  semaphores,
  semaphores_busy,
  semaphores_full,
  
  mutexes,
  mutexes_busy,
  
  
  /** miscellaneous queue properties */
  queue_items, /**Set of items of a queue **/
  queue_receiving, /**Set of tasks waiting to recive one item of queue **/
  queue_sending,/**Set of tasks waiting to send one item to queue **/
  mutex_holder,
  first_sending,
  first_receiving
  
  
INVARIANT
  queues : POW(QUEUE) & 
 
  queue_receiving : QUEUE +-> POW(TASK) & 
  queue_sending : QUEUE+-> POW(TASK) &
  
  queues = dom(queue_receiving) &
  queues = dom(queue_sending) &
  
  first_receiving:QUEUE +-> TASK &
  first_sending:QUEUE +-> TASK &
  
  dom(first_receiving)= dom(queue_receiving) &
  dom(first_sending) = dom(queue_sending)&
 
  
  !(q1).(q1:queues & q1:dom(first_receiving) & queue_receiving(q1)/={} => first_receiving(q1):queue_receiving(q1)) &
  /*
  !(q1).(q1:queues & q1:dom(first_receiving) & first_receiving(q1):queue_receiving(q1) =>queue_receiving(q1)/={} ) &
  */
  !(q1).(q1:queues & q1:dom(first_sending) & queue_sending(q1)/={} => first_sending(q1):queue_sending(q1)) &
  /*
  !(q1).(q1:queues & q1:dom(first_sending) & first_sending(q1):queue_sending(q1) => queue_sending(q1)/={} ) &
  */
  /*
  !(q1).(q1:queues & q1:dom(first_receiving) & queue_receiving(q1)={} => first_receiving(q1)=TASK_NULL) &
  !(q1).(q1:queues & q1:dom(first_receiving) & first_receiving(q1)=TASK_NULL => queue_receiving(q1)={}) &
  
  !(q1).(q1:queues & q1:dom(first_sending) & queue_sending(q1)={} => first_sending(q1)=TASK_NULL) &
  !(q1).(q1:queues & q1:dom(first_sending) & first_sending(q1)=TASK_NULL => queue_sending(q1)={}) &
 	*/
 	
  
  
  queues_msg <: queues &
  queues_msg_full <: queues_msg &
  queues_msg_empty <: queues_msg &
  
  queues_msg_full/\queues_msg_empty = {} &
  
  
  
  queue_items : QUEUE +-> POW(ITEM) &
  
  queues_msg = dom(queue_items) &
  
  
  !(q1,q2,tk).(q1:queues & q2:queues & tk:TASK & tk:queue_receiving(q1)=> tk/:queue_sending(q2))&
  !(q1,q2,tk).(q1:queues & q2:queues & tk:TASK & tk:queue_sending(q2)=> tk/:queue_receiving(q1))&
  !(q1,q2,tk).(q1:queues & q2:queues & q1/=q2 & tk:TASK & tk:queue_receiving(q1) => tk/:queue_receiving(q2))&
  !(q1,q2,tk).(q1:queues & q2:queues & q1/=q2 & tk:TASK & tk:queue_sending(q1) => tk/:queue_sending(q2))&
  
  
  semaphores: POW(QUEUE) &
  semaphores<:queues &
  
  semaphores_busy<:semaphores &
  semaphores_full<:semaphores&
  semaphores_busy/\semaphores_full={} &
  
  
  mutexes <: queues &/* mutex is a type of binary semaphore, however some operation will work only 
  with semaphore, for this reason mutex was specific as without relation with semaphore
  */ 
  mutex_holder: QUEUE +-> TASK &
  
  mutexes=dom(mutex_holder)  &
  
  mutexes_busy<:mutexes &
  
  
  !mt.(mt:mutexes_busy => mutex_holder(mt)/=TASK_NULL) &
  !mt.(mt:mutexes & mt/:mutexes_busy => mutex_holder(mt)=TASK_NULL) &
  
  queues_msg/\semaphores={} &
  queues_msg/\mutexes={} &
  mutexes/\semaphores={}
  
  
CONSTANTS 
  remove_task
/*,
  update_first */
  
PROPERTIES 
  remove_task : ((QUEUE+->POW(TASK)) * POW(TASK))--> (QUEUE+->POW(TASK)) &
  remove_task = %(q_task,unblocked).(q_task:QUEUE+->POW(TASK) & unblocked:POW(TASK)|
    %(q1).(q1:QUEUE & q1:dom(q_task) | q_task(q1)-unblocked)) /*&
  
  update_first : ((QUEUE+->POW(TASK)) * POW(TASK))--> (QUEUE+-> TASK)&
  update_first = %(q_task,unblocked).(q_task:QUEUE+->POW(TASK) & unblocked:POW(TASK)|
    {q1,task|q1:QUEUE & q1:dom(q_task) & (q_task(q1)-unblocked) /={} & task:TASK & task:(q_task(q1)-unblocked)}) */
  /*
    %(q1).(q1:QUEUE & q1:dom(q_task) & (q_task(q1)-unblocked) /={} | choice(q_task(q1)-unblocked) ))
  */
  
ASSERTIONS
  !(que).(que:QUEUE & que/:queues=> que/:mutexes)&
  !(que).(que:QUEUE & que/:queues=> que/:semaphores)&
  !(que).(que:queues_msg & que:queues_msg_full=>que/:queues_msg_empty)&
  !(que).(que:queues_msg & que:queues_msg_empty=>que/:queues_msg_full)
  
  
  
INITIALISATION
  queues := {}||
  queues_msg:={}||
  queue_items := {}||
  queue_receiving := {}||
  queue_sending := {}||
  semaphores:={}||
  semaphores_busy:={}||
  mutexes:={}||
  mutexes_busy:={}||
  mutex_holder:={} ||
  queues_msg_full:={} ||
  queues_msg_empty := {}||
  semaphores_full:={}||
  first_sending :={}||
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
    uxQueueLength: QUEUE_LENGTH & 
    uxItemSize:NAT
  THEN    
    
    ANY
      pxQueue 
    WHERE
      pxQueue : QUEUE & 
      pxQueue /: queues
    THEN	
      queues := queues \/ {pxQueue}||
      queues_msg:= queues_msg \/ {pxQueue}||
      queue_items := queue_items \/ {pxQueue |-> {}} ||
      queue_receiving := queue_receiving \/ {pxQueue |-> {}} ||
      queue_sending := queue_sending \/ {pxQueue |-> {}}||
      queues_msg_empty:=queues_msg_empty\/{pxQueue}||
      xQueueHandle:=pxQueue||
      first_sending:=first_sending\/{pxQueue|->TASK_NULL}||
      first_receiving:=first_receiving\/{pxQueue|->TASK_NULL}
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
    pxQueue /:semaphores &
    pxQueue /: mutexes &
    queue_receiving(pxQueue) = {} &
    queue_sending(pxQueue) = {}
  THEN    
    queues := queues - {pxQueue}||
    queues_msg := queues_msg - {pxQueue} ||
    queue_items := {pxQueue} <<| queue_items  ||
    queue_receiving := {pxQueue} <<| queue_receiving ||
    queue_sending := {pxQueue} <<| queue_sending||
    first_sending := {pxQueue} <<| first_sending||
		first_receiving := {pxQueue} <<| first_receiving||
    IF pxQueue:queues_msg_full THEN  queues_msg_full := queues_msg_full-{pxQueue} END||
  	IF pxQueue:queues_msg_empty THEN queues_msg_empty := queues_msg_empty-{pxQueue} END
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
    pxItem:ITEM & 
    pxItem/:queue_items(pxQueue)&
    copy_position:COPY_POSITION &	    
    pxQueue/:queues_msg_full 
  THEN
    queue_items(pxQueue) := queue_items(pxQueue) \/ {pxItem} ||
    IF queue_receiving(pxQueue)/={} THEN
      ANY n_receiving, n_first
      WHERE
        n_receiving:POW(TASK) &
        n_first:TASK &
        n_receiving = (queue_receiving(pxQueue)-{first_receiving(pxQueue)})&
        n_first:n_receiving
      THEN
        queue_receiving(pxQueue) := n_receiving||
      	first_receiving(pxQueue):= n_first
      END
    END||  
    IF pxQueue:queues_msg_empty THEN
      queues_msg_empty := queues_msg_empty-{pxQueue}
    END||
    CHOICE
      queues_msg_full:= queues_msg_full\/{pxQueue}
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
    pxQueue/:queues_msg_empty 
  THEN
    ANY
      item
    WHERE
      item : ITEM & 
      item : queue_items(pxQueue)
    THEN
      IF justPeeking = FALSE THEN       
        IF queue_sending(pxQueue)/={} THEN
          ANY n_sending,n_first
          WHERE
            n_sending:POW(TASK) &
            n_first:TASK &
            n_sending = (queue_sending(pxQueue) - {first_sending(pxQueue)}) &
            n_first:n_sending
          THEN
            queue_items(pxQueue) := queue_items(pxQueue) - {item} ||
            queue_sending(pxQueue) := n_sending||            
           	first_sending(pxQueue):=n_first||
            IF pxQueue: queues_msg_full THEN
              queues_msg_full:=queues_msg_full-{pxQueue}
            END||
            CHOICE
              queues_msg_empty:=queues_msg_empty\/{pxQueue}
            OR
              skip
            END
          END
        END
      END ||
      xItem:=item
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
    pxQueue:queues & 
    pxTask : TASK &
    !q1.(q1:queues => pxTask/: queue_sending(q1) & pxTask/:queue_receiving(q1))
  THEN
    queue_sending(pxQueue) := queue_sending(pxQueue) \/ {pxTask}||
    IF queue_sending(pxQueue)={} THEN
      first_sending(pxQueue):=pxTask
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
    !q1.(q1:queues => pxTask/: queue_sending(q1) & pxTask/:queue_receiving(q1))
  THEN
    queue_receiving(pxQueue) := queue_receiving(pxQueue) \/ {pxTask}||
    IF queue_receiving(pxQueue)={} THEN
      first_receiving(pxQueue):=pxTask
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
      pxQueue:queues &
      task:queue_receiving(pxQueue) &
      n_receiving: POW(TASK) &
      n_receiving = queue_receiving(pxQueue)-{first_receiving(pxQueue)} &
      n_first:TASK&
      n_first: queue_receiving(pxQueue)
    THEN
      queue_receiving(pxQueue) := n_receiving||
     	first_receiving(pxQueue):=n_first
    END ||
    ANY
      pxQueue, n_sending ,n_first
    WHERE
      pxQueue:queues &
      n_sending: POW(TASK) &
      n_first:TASK &
      task:queue_sending(pxQueue) &
      n_sending = queue_sending(pxQueue) -{first_sending(pxQueue)} &
      n_first = first_sending(pxQueue)
    THEN
      queue_sending(pxQueue) := n_sending||
			first_sending(pxQueue):=n_first
    END
  END;
  
  
  rSemaphore<--q_createSemaphore(maxCount,initialCount)=
  PRE
    maxCount:QUEUE_LENGTH &
    initialCount:QUEUE_QUANT &
    initialCount <= maxCount
  THEN
    ANY 
      semaphore
    WHERE
      semaphore:QUEUE &
      semaphore/:queues
    THEN
      queues:= queues \/ {semaphore} ||
      queue_receiving := queue_receiving \/ {semaphore |-> {}} ||
      first_receiving := first_receiving \/ {semaphore|-> TASK_NULL}||
      queue_sending := queue_sending \/ {semaphore |-> {}}||
      first_sending := first_sending \/ {semaphore|-> TASK_NULL}||
      semaphores:=semaphores \/ {semaphore} ||
      CHOICE
        semaphores_busy:=semaphores_busy\/{semaphore}
      OR
        semaphores_full:=semaphores_full\/{semaphore}
      OR
        skip
      END||
      rSemaphore:=semaphore
    END
  END;
  
  q_deleteSemaphore(semaphore)=
  PRE
    semaphore:semaphores
  THEN
    queues := queues - {semaphore}||
    semaphores := semaphores - {semaphore} ||
    queue_receiving := {semaphore} <<| queue_receiving ||
    first_receiving := {semaphore} <<| first_receiving||
    queue_sending := {semaphore} <<| queue_sending ||
    first_sending := {semaphore} <<| first_sending ||
    IF semaphore:semaphores_busy THEN
      semaphores_busy := semaphores_busy-{semaphore}
    END||
    IF semaphore:semaphores_full THEN
      semaphores_full:=semaphores_full-{semaphore}
    END
  END;
  
  q_takeSemaphore(semaphore)=
  PRE 
    semaphore: semaphores &
    semaphore/: semaphores_busy 
  THEN
    IF semaphore:semaphores_full THEN
      semaphores_full:=semaphores_full-{semaphore}
    END||
    CHOICE
      semaphores_busy:= semaphores_busy\/{semaphore}
    OR
      skip
    END||
    
    ANY
      n_sending,n_first
    WHERE
      n_sending:POW(TASK) &
      n_sending=queue_sending(semaphore)-{first_sending(semaphore)} &
      n_first:TASK&
      n_first:n_sending
    THEN
      queue_sending(semaphore):=n_sending||
      first_sending(semaphore):=n_first
    END
  END;
  
  /* When the semaphore is take, other task can give it, put it in 
    unbusy state */
  q_giveSemaphore(semaphore)=
  PRE
    semaphore:semaphores 
  THEN
    IF semaphore/:semaphores_full THEN
      IF semaphore: semaphores_busy 
      THEN
        semaphores_busy := semaphores_busy - {semaphore}
      END||
      CHOICE
        semaphores_full:= semaphores_full \/ {semaphore}
      OR
        skip
      END||
      ANY n_receiving, n_first
      WHERE
        n_receiving:POW(TASK) &
        n_receiving = queue_receiving(semaphore)-{first_receiving(semaphore)}&
        n_first:TASK &
        n_first: n_receiving
      THEN
        queue_receiving(semaphore):=n_receiving ||
        first_receiving(semaphore):=n_first
      END
    END
  END;
  
  rMutex<--q_createMutex =
  BEGIN
    ANY 
      mutex
    WHERE
      mutex : QUEUE &
      mutex /: queues 
    THEN
      queues:=queues\/{mutex}||
      mutexes:=mutexes\/{mutex}||
      queue_receiving := queue_receiving \/ {mutex |-> {}} ||
      first_receiving := first_receiving \/ {mutex |-> TASK_NULL} ||
      queue_sending := queue_sending\/ {mutex|-> {}}||
      first_sending := first_sending \/ {mutex |-> TASK_NULL} ||
      mutex_holder := mutex_holder\/ {mutex|-> TASK_NULL}||
      rMutex:=mutex
    END
  END;
  
  q_takeMutex(mutex,task)=
  PRE
    mutex:mutexes &
    mutex/:mutexes_busy &
    task:TASK &
    task/=TASK_NULL
  THEN
    mutexes_busy := mutexes_busy \/ {mutex}||
    mutex_holder(mutex):=task 
  END;
  
  q_giveMutex(mutex)=
  PRE
    mutex:mutexes &
    mutex:mutexes_busy 
  THEN
    mutexes_busy:=mutexes_busy-{mutex}||
    mutex_holder(mutex):=TASK_NULL||
    ANY n_receiving, n_first
    WHERE
      n_receiving:POW(TASK)&
      n_receiving = queue_receiving(mutex)-{first_receiving(mutex)} &
      n_first:TASK &
      n_first:n_receiving
    THEN
    
      queue_receiving(mutex):= n_receiving||
      first_receiving(mutex):= n_first
    END
  END;
  
  
  q_endScheduler=
  BEGIN
    queues := {}||
    queues_msg:={}||
    queues_msg_empty:={}||
    queues_msg_full:={}||
    queue_items := {}||
    queue_receiving := {}||
    first_receiving:={}||
    queue_sending := {}||
    first_sending :={}||
    semaphores:={}||
    semaphores_busy:={}||
    mutexes:={}||
    mutexes_busy:={}||
    mutex_holder:={}||
    semaphores_full:={}
  END;
  
  q_resumeAll(unblocked)=
  PRE
    unblocked:POW(TASK)
  THEN
    IF queue_sending/={} 
    THEN
      ANY 
        n_sending,n_first
      WHERE
        n_sending:QUEUE +-> POW(TASK) &
        dom(n_sending) = dom(queue_sending) &
        n_sending = remove_task(queue_sending,unblocked) &
        n_first:QUEUE +-> TASK &
        dom(n_first) = dom(first_sending) &
        !(q1).(q1:queues & q1:dom(n_first) & n_sending(q1)/={} => n_first(q1):n_sending(q1)) 
      THEN
        queue_sending:=n_sending||
        first_sending:=n_first
      END  
    END||
    
    IF queue_receiving/={} 
    THEN
      ANY 
        n_receiving,n_first
      WHERE
        n_receiving:QUEUE +-> POW(TASK) &
        dom(n_receiving) = dom(queue_receiving)&
        n_receiving = remove_task(queue_receiving,unblocked) &
        n_first:QUEUE +-> TASK &
        dom(n_first) = dom(first_receiving) &
        !(q1).(q1:queues & q1:dom(n_first) & n_receiving(q1)/={} => n_first(q1):n_receiving(q1)) 
      THEN
        queue_receiving:=n_receiving||
        first_receiving:=n_first
      END  
    END
  END

END
