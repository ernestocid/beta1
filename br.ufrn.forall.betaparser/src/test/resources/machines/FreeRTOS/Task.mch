/**********************************************************************************************************
\brief Core definitions for task support in FreeRTOS.

Module responsible for specific the features task. In this initial specification, we concerns priority 
with the changes of task states, abstracting the outher features as priority, delay time, context and other

Author: Stephenson Galvao, David Deharbe
Creation date: Fri Dec 5 2008
Universidade Federal do Rio Grande do Norte
Departamento de Informatica e Matematica Aplicada
Programa de Pos-Graduacao em Sistemas e Computacao
Formal Methods and Languages Laboratory
**********************************************************************************************************/
MACHINE
  Task
  
SEES
  FreeRTOSConfig,
  Types
  
VARIABLES
  tickCount,		/** total tick count */
  tickMissed,
  active, /** the system is in active mode: the scheduler is functionning and tasks are executed */
  
  tasks,	/** all existing tasks */   
  blocked, /** set of blocked task*/
  runable, /** set of runable task*/
  suspended, /** set of suspend task*/
  running, /** task current*/
  idle, /* a private task that runs when all other tasks are suspended or blocked */
  unblocked
INVARIANT
  
  active : BOOL &
  
  tickCount: TICK &
  tickMissed:TICK &
  
  tasks : FIN(TASK) &
  
  
  blocked : FIN(TASK) &
  blocked <: tasks &
  
  unblocked: FIN(TASK) &
  unblocked<:blocked &
  
  runable : FIN(TASK) &
  runable <: tasks &
  
  suspended : FIN(TASK) &
  suspended <: tasks &
  
  runable /\ blocked = {} & 
  blocked /\ suspended = {} &
  suspended /\ runable= {} &
  
  tasks=suspended \/ blocked \/ runable &
  
  running:TASK & 
  idle:TASK &
  
  
  (active=TRUE => 
    runable/={} &
    running:runable &
    idle:runable &
    TASK_NULL /: tasks
  )
  
  
ASSERTIONS
  active = TRUE => runable/={};
  active = TRUE => tasks/={};
  active = TRUE => idle:runable;
  active = TRUE => idle/:blocked;
  active = TRUE => idle/:suspended;
  active = TRUE => idle/=TASK_NULL;
  active = TRUE => running:runable;
  active = TRUE => (running/:blocked);
  active = TRUE => (running/:suspended)
  
  
INITIALISATION
  tickCount := 0 ||
  tickMissed := 0 ||
  active := FALSE ||
  tasks,blocked, runable, suspended,unblocked := {}, {}, {}, {},{} ||
  idle::TASK ||
  running::TASK
  
OPERATIONS
  
  /****************************************************************************************************
  \brief Task creation
  
  \do 
  This method create a new task. If the created task have priority great than the running task
  the switch context is necessary.
  
  \parameters
  priority - The priority of the task.
  
  \note 
  The actual function takes the handled as a parameter passed by reference.
  ****************************************************************************************************/
  result <-- t_create (priority)=
  PRE
    priority : PRIORITY
  THEN	
    ANY
      task,
      n_runable /* the creating of n_runable was better to refinament prove*/
    WHERE
      task : TASK &
      task /: tasks &
      n_runable <: tasks &
      n_runable = runable \/ {task} &
      task/=TASK_NULL
    THEN
      tasks := tasks \/ {task} ||
      runable := n_runable||
      CHOICE 
        skip
      OR
        running::n_runable
      END||
      result := task
    END
  END;
  
  
  /****************************************************************************************************
  \brief Delete Task
  
  \do	
  Delete a task managed by FreeRTOS. If the task that will be delete is the current task, a runable 
  task will became the running task.
  
  \parameters 
  aTask - Task that will be deleted
  ****************************************************************************************************/
  t_delete (atask)=
  PRE
    atask : TASK &
    atask : tasks & 
    atask /= idle 
  THEN
    tasks := tasks - { atask } || 	
    IF atask : runable  THEN
      ANY 
        n_runable
      WHERE
        n_runable<:tasks &
        n_runable = runable - {atask}
      THEN
        runable := n_runable||
        IF atask = running THEN
          IF active=TRUE THEN
            running::n_runable
          END
        END
      END
    ELSIF atask : blocked  THEN
      blocked := blocked - {atask}||
      IF atask:unblocked THEN
        unblocked := unblocked - {atask}
      END
    ELSIF atask : suspended THEN
      suspended := suspended - { atask }
    END
  END;
  
  
  
  t_yield =
  PRE
    active=TRUE
  THEN
    running::runable
  END;
  
  
  /****************************************************************************************************
  \brief Suspend Task
  
  \do	
  This method suspend the passed task. If the task that will be suspend is the current task, a task 
  with the state runable will choice to be the current task.
  
  \parameters 
  atask- Task that will be suspend
  *****************************************************************************************************/
  t_suspend (stask)=
  PRE
    stask : TASK &
    stask : tasks &
    stask /= idle
  THEN
    
    IF stask : runable THEN
      ANY n_runable 
      WHERE 
        n_runable<:tasks&
        n_runable=runable-{stask}
      THEN
        runable:=n_runable||
        IF stask=running THEN
          running::n_runable
        END
        
      END                
    ELSIF stask : blocked THEN
      blocked := blocked - { stask }||
      IF stask:unblocked THEN
        unblocked := unblocked - {stask}
      END
    END||
    suspended := suspended \/ { stask } 
  END;
  
  /****************************************************************************************************
  \brief	Resume Task
  
  \do	
  This method resume a suspended tasks. The resumed task can go to runable or running state.
  
  \parameters 
  atask - Task that will be resume
  ******************************************************************************************************/
  t_resume (rtask) =
  PRE 
    rtask : TASK & 
    rtask : suspended &
    active=TRUE
  THEN 
    ANY 
      n_runable
    WHERE
      n_runable<:tasks &
      n_runable = runable \/ {rtask}
    THEN
      runable := n_runable ||
      CHOICE
        running ::n_runable
      OR
        skip
      END 
    END||        
    suspended := suspended - {rtask}
  END;
  
  /*****************************************************************************************************
  \brief	Get Task Priority
  
  \do	
  This method get the priority of the task passed.
  
  \parameters 
  atask - Task that will be returned the priority
  
  \returns
  priority - a priority belong of Priority set
  
  I think that this function is unnecessary 'cause when i'll want the priority task i can use the 
  refinament state that 
  *********************************************************************************************************/
  priority <-- t_getPriority(atask)=
  PRE
    atask : TASK &
    atask : tasks
  THEN 
    priority :: PRIORITY
  END;
  
  /********************************************************************************************************
  \brief	Get Current Task
  
  \do	
  Return the currently running task
  
  \returns
  result - The currently running task
  **********************************************************************************************************/
  result <-- t_getCurrent =
  BEGIN
    result := running
  END;
  
  /**********************************************************************************************************
  \brief	Get Number of Task
  
  \do	
  Get the number of tasks managed by the FreeRTOS
  
  \returns
  numberOfTasks -  number of tasks managed by the FreeRTOS
  **********************************************************************************************************/
  result <-- t_getNumberOfTasks =
  BEGIN
    result := card(tasks)
  END;
  
  /**********************************************************************************************************
  \brief	Delay Task
  
  \do	
  Delay the current task until a number of ticks passed as parameter
  
  \parameters
  tick - The tick that the task should be unbloked. Is declared but no used here
  **********************************************************************************************************/
  t_delayTask (ticks,task)=
  PRE
    task:TASK &
    task=running &
    running:runable &
    running /= idle &
    ticks : TICK 
  THEN  
    ANY
      n_runable
    WHERE
      n_runable <: tasks &
      n_runable = runable-{running}
    THEN
      runable:=n_runable ||
      running::n_runable
    END||
    blocked := blocked \/ {running}
  END;
  
  
  
  
  /**********************************************************************************************************
  \brief	Start Scheduler
  
  \do	
  Start the scheduler and create the idle task. 
  
  
  **********************************************************************************************************/
  t_startScheduler = 
  PRE
    active = FALSE
  THEN
    active := TRUE ||
    ANY
      idle_task,
      n_runable
    WHERE
      idle_task:TASK & 
      idle_task/: tasks &
      n_runable <:tasks &
      n_runable = runable\/ {idle_task}
    THEN
      idle := idle_task ||
      tasks := tasks\/{idle_task}||
      runable:=n_runable||
      running::n_runable
    END
  END;
  
  /**********************************************************************************************************
  \brief	End Scheduler
  
  \do	
  End the task of schuler
  **********************************************************************************************************/
  t_endScheduler =
  PRE
    active = TRUE
  THEN
    active := FALSE ||
    tasks, blocked, suspended, runable,unblocked := {}, {}, {}, {}, {}
  END;
  
  
  
  
  t_removeFromEventList(task)=
  PRE
    task:TASK &
    task:(blocked\/suspended)&
    active=TRUE
  THEN
    IF 
      task: blocked 
    THEN
      blocked:= blocked - {task}||
      IF task:unblocked THEN
        unblocked:=unblocked -{task}
      END
    END||
    IF
      task:suspended
    THEN
      suspended:= suspended - {task}
    END||
    
    ANY
      n_runable
    WHERE
      n_runable<:tasks &
      n_runable = runable \/ {task}
    THEN
      runable := n_runable||
      CHOICE
        running::n_runable
      OR
        skip
      END     
    END
  END;
  
  
  
  t_setPriority(task,priority)=
  PRE
    task:tasks &
    priority:PRIORITY &
    task/=idle & 
    active=TRUE
  THEN
    CHOICE
      running::runable
    OR
      skip
    END
  END;
  
  t_suspendAll=
  PRE
    active=TRUE
  THEN
    active:=FALSE
  END;
  
  
  t_priorityInherit(holderTask,xTicksToWait)=
  PRE
    xTicksToWait:TICK &
    holderTask:tasks &        
    holderTask/=idle &
    running/=idle &
    running:runable &
    active=TRUE
  THEN
    ANY 
      n_runable
    WHERE
      n_runable <: tasks &
      n_runable = runable-{running}
    THEN
      running::n_runable||
      runable := n_runable 
    END||
    blocked := blocked \/ {running}
  END;
  
  t_returnPriority(btask)=/*bstask é a tarefa que foi desbloqueada com a liberação do mutex*/
  PRE
    btask: TASK & 
    btask:tasks &
    btask:(blocked\/suspended)&
    active=TRUE &
    running/=idle
  THEN
    IF 
      btask: blocked 
    THEN
      blocked:= blocked - {btask}||
      IF btask:unblocked THEN
        unblocked:=unblocked - {btask}
      END
    END||
    IF
      btask:suspended
    THEN
      suspended:= suspended - {btask}
    END||
    ANY
      n_runable
    WHERE
      n_runable<:tasks &
      n_runable = runable \/ {btask}
    THEN
      runable := n_runable||
      CHOICE
        running::n_runable
      OR
        skip
      END     
    END
  END;
  
  t_incrementTick =
  BEGIN
    IF active = TRUE THEN
      tickCount := TICK_INCREMENT(tickCount, 1)
    ELSE
      tickMissed:=TICK_INCREMENT(tickMissed, 1)
    END||
    ANY 
      n_unblocked
    WHERE
      n_unblocked : FIN(TASK)&
      n_unblocked <:blocked
    THEN
      unblocked := n_unblocked
    END
  END;
  /**********************************************************************************************************
  \brief	Resume All
  
  \do	
  Method used for resume the scheduler. It increment the tick with missed ticks and verify the list of 
  blocked tasks to unblock the tasks with expired timers.
  **********************************************************************************************************/
  t_resumeAll (tunblocked) = 
  PRE
    tunblocked : FIN(TASK) &
    tunblocked <: blocked &
    tunblocked=unblocked 
  THEN
    
    IF tunblocked /= {} THEN
      ANY
        n_runable
      WHERE
        n_runable<:tasks &
        n_runable = runable \/ tunblocked
      THEN
        runable:=n_runable ||
        CHOICE
          running::n_runable
        OR
          skip
        END 
      END||
      blocked := blocked - tunblocked||
      unblocked:={}
    END
  END;
  
  t_beforeResumeAll=
  PRE
    active=FALSE
  THEN
    tickCount := TICK_INCREMENT(tickCount, tickMissed)||
    tickMissed:=0||
    ANY 
      n_unblocked
    WHERE
      n_unblocked : FIN(TASK)&
      n_unblocked <:blocked
    THEN
      unblocked := n_unblocked
    END
  END
END
