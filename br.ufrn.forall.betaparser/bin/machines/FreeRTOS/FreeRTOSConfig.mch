/* FreeRTOSConfig

 \brief Definitions of configurations for FreeRTOS.
 
 FreeRTOS can be configured in several ways to tailor it to a particular application. This machine contains the definitions of all such configuration settings.
 
 Author: Stephenson Galvao, David Deharbe
 Creation date: Fri Dec 5 2008
 Universidade Federal do Rio Grande do Norte
 Departamento de Informatica e Matematica Aplicada
 Programa de Pos-Graduacao em Sistemas e Computacao
 Formal Methods and Languages Laboratory
 */
MACHINE
	FreeRTOSConfig

SEES
	Types

CONSTANTS
	configMAX_PRIORITIES, 	/** maximum value for task priorities */
	configTOTAL_HEAP_SIZE,	/** memory size */
	configMINIMAL_STACK_SIZE,

	/** The following are flags controlling the availability of parts of the API */
	INCLUDE_vTaskPrioritySet,	/** TaskPrioritySet */
	INCLUDE_vTaskSuspend,		/** vTaskSuspend and vTaskResume */
	INCLUDE_uxTaskPriorityGet,	/** uxTaskPriorityGet */
	INCLUDE_vTaskDelete,		/** vTaskDelete */
	INCLUDE_xTaskGetSchedulerState,	/** xTaskGetSchedulerState */
	INCLUDE_xTaskGetCurrentTaskHandle,	/** xTaskGetCurrentTaskHandle */
	INCLUDE_vTaskDelay,	/**vTaskDelay**/
	INCLUDE_vTaskDelayUntil	/**vTaskDelayUntil**/

	/* 
	No used	
	configUSE_PREEMPTION, configUSE_IDLE_HOOK, configUSE_TICK_HOOK,
	configCPU_CLOCK_HZ, configTICK_RATE_HZ, configMAX_TASK_NAME_LEN,
	configUSE_TRACE_FACILITY, configUSE_16_BIT_TICKS, configIDLE_SHOULD_YIELD,
	configUSE_USE_MUTEXES, configUSE_RECURSIVE_MUTEXES, configUSE_COUNTING_SEMAPHORES,
	configUSE_ALTERNATIVE_API, configCHECK_FOR_STACK_OVERFLOW, configQUEUE_REGISTRY_SIZE,
	configUSE_CO_ROUTINES, configMAX_CO_ROUTINE_PRIORITIES, configKERNEL_INTERRUPT_PRIORITY,
	configMAX_SYSCALL_INTERRUPT_PRIORITY, INCLUDE_vTaskCleanUpResources,
	INCLUDE_vTaskDelayUntil, INCLUDE_vTaskDelay	
	*/

PROPERTIES

	configMAX_PRIORITIES : NAT &
	configMAX_PRIORITIES >=1 &
	configTOTAL_HEAP_SIZE : NAT &
	configTOTAL_HEAP_SIZE >= 0 &
	configMINIMAL_STACK_SIZE : NAT  &
	configMINIMAL_STACK_SIZE >= 0 &
	INCLUDE_uxTaskPriorityGet : BIT &
	INCLUDE_vTaskDelete : BIT &
	INCLUDE_vTaskSuspend : BIT &
	INCLUDE_xTaskGetSchedulerState : BIT &
	INCLUDE_vTaskPrioritySet : BIT &
	INCLUDE_xTaskGetCurrentTaskHandle : BIT &
	INCLUDE_vTaskDelayUntil : BIT &
	INCLUDE_vTaskDelay : BIT &
	
	PRIORITY = 0..(configMAX_PRIORITIES-1) 
/*
No Used
& configUSE_PREEMPTION : BIT
& configUSE_IDLE_HOOK : BIT
& configUSE_TICK_HOOK : BIT
& configCPU_CLOCK_HZ : NATURAL
& configTICK_RATE_HZ : NATURAL
& configMAX_TASK_NAME_LEN :NATURAL
& configUSE_TRACE_FACILITY :BIT
& configUSE_16_BIT_TICKS : BIT
& configIDLE_SHOULD_YIELD : BIT
& configUSE_USE_MUTEXES  : BIT
& configUSE_RECURSIVE_MUTEXES : BIT
& configUSE_COUNTING_SEMAPHORES : BIT
& configUSE_ALTERNATIVE_API : BIT
& configCHECK_FOR_STACK_OVERFLOW : BIT
& configQUEUE_REGISTRY_SIZE : BIT
& configUSE_CO_ROUTINES : BIT
& configMAX_CO_ROUTINE_PRIORITIES : NATURAL
& configKERNEL_INTERRUPT_PRIORITY : NATURAL
& configMAX_SYSCALL_INTERRUPT_PRIORITY : NATURAL
& INCLUDE_vTaskCleanUpResources : BIT
*/

ASSERTIONS
    configMAX_PRIORITIES : NATURAL
    
END


  	
  