MACHINE
   CGP

SETS
    OPERATION_MODE = {MAN, MCS, ATO, NO_MODE};
    DOOR_STATE = {CLOSED_AND_LOCKED, NOT_CLOSED_AND_LOCKED};
    EMG_SWITCH_STATE = {ACTUATED, NOT_ACTUATED};
    SIDE = {LEFT, RIGHT, NO_SIDE};
    DOORS;
    MECHANIC_EMG_SWITCHES;
    DOOR_EMG_SWITCHES

INCLUDES Opening, Closing, Emergency
    
ABSTRACT_VARIABLES
    doors_right_open_simultaneously,
    doors_left_open_simultaneously,    
    doors_right_close_simultaneously,
    doors_left_close_simultaneously,
    
    opening_side,
    closing_side,
    
    conditions_to_open_satisfied,
    conditions_to_close_satisfied,
    
    internal_speed_over_6km_h,
    internal_train_mode,
    internal_train_stopped,
    internal_train_lined_up,
    internal_train_in_platform,
    
    internal_cab_consol_open_button_pushed,
    internal_cab_side_open_button_pushed,
    internal_command_to_open_from_ATC,
    internal_cab_consol_close_button_pushed,
    internal_cab_side_close_button_pushed,
    internal_command_to_close_from_ATC,
    
    internal_doors_closed_and_locked,
    
    internal_cab_consol_signalization_doors_closed_and_locked,
    internal_cab_emergency_sound_alarm_activated, 
    
    internal_interlock_between_doors_and_traction_activated,
    emergency_break_applied_by_switch,
    
    some_mechanic_emergency_switch_actuated,
    some_door_emergency_switch_actuated,
    
    internal_control_mechanic_emergency_switch_actuation,
    internal_control_door_emergency_switch_actuation,
    internal_authorize_emergency_switch_operation
    
INVARIANT
    
    /************* Typing *************/    
	
	doors_right_open_simultaneously : BOOL &
    doors_left_open_simultaneously : BOOL &
    doors_right_close_simultaneously : BOOL &
    doors_left_close_simultaneously : BOOL &
    
    opening_side : SIDE &
    closing_side : SIDE &
    
    conditions_to_open_satisfied : BOOL &
    conditions_to_close_satisfied : BOOL &
    
	internal_speed_over_6km_h : BOOL  &
	internal_train_mode : OPERATION_MODE &
	internal_train_stopped : BOOL &
    internal_train_lined_up : BOOL &
    internal_train_in_platform : BOOL &
    
    internal_cab_consol_open_button_pushed : BOOL &
    internal_cab_side_open_button_pushed : BOOL &
    internal_command_to_open_from_ATC : BOOL &
    internal_cab_consol_close_button_pushed : BOOL &
    internal_cab_side_close_button_pushed : BOOL &
    internal_command_to_close_from_ATC : BOOL &
    
    internal_doors_closed_and_locked : BOOL &
    
    internal_cab_consol_signalization_doors_closed_and_locked : BOOL &
    internal_cab_emergency_sound_alarm_activated : BOOL &
    
    internal_interlock_between_doors_and_traction_activated : BOOL &
    emergency_break_applied_by_switch : BOOL &
    
    some_mechanic_emergency_switch_actuated : BOOL &
    some_door_emergency_switch_actuated : BOOL &

    internal_control_mechanic_emergency_switch_actuation : BOOL &
    internal_control_door_emergency_switch_actuation : BOOL &
    internal_authorize_emergency_switch_operation : BOOL &
    
    /************* Opening and Closing *************/
    
    ((internal_speed_over_6km_h = TRUE) => (doors_right_open_simultaneously = FALSE & doors_left_open_simultaneously = FALSE )) &
    
    (( doors_right_open_simultaneously = TRUE or doors_left_open_simultaneously = TRUE ) => (conditions_to_open_satisfied = TRUE)) &
    
    (( doors_right_close_simultaneously = TRUE or doors_left_close_simultaneously = TRUE ) => (conditions_to_close_satisfied = TRUE)) &
    
    ( (	(internal_train_mode = MCS or internal_train_mode = ATO) &
    	internal_train_stopped = TRUE & 
    	internal_train_lined_up = TRUE &
    	internal_train_in_platform = TRUE &
    	internal_speed_over_6km_h = FALSE ) =>
 			(conditions_to_open_satisfied = TRUE)	
	) &

	( (	(internal_train_mode = MAN) &
    	internal_speed_over_6km_h = FALSE ) =>
 			(conditions_to_open_satisfied = TRUE)	
	) & 
	
	( (	(internal_train_mode = ATO) &
	    internal_cab_consol_open_button_pushed = TRUE &
	    internal_command_to_close_from_ATC = TRUE ) =>
 			(conditions_to_close_satisfied = FALSE)	
	)  & 
	
    ( (internal_doors_closed_and_locked = FALSE) 
        	=> internal_interlock_between_doors_and_traction_activated = TRUE) &
    
	/************* Signalization *************/
    
    ((internal_doors_closed_and_locked = TRUE) 
        	=> (internal_cab_consol_signalization_doors_closed_and_locked = TRUE)) & 
    
    /************* Emergency *************/

    ( (internal_train_stopped = TRUE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_cab_emergency_sound_alarm_activated = TRUE) &

    ( (internal_train_stopped = TRUE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_control_mechanic_emergency_switch_actuation = TRUE) &

    ( (internal_train_stopped = TRUE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_interlock_between_doors_and_traction_activated = TRUE) & 

    ( (internal_train_stopped = TRUE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_authorize_emergency_switch_operation = TRUE) & 

    ( (internal_train_stopped = FALSE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_cab_emergency_sound_alarm_activated = TRUE) & 

    ( (internal_train_stopped = FALSE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_control_mechanic_emergency_switch_actuation = TRUE) &

    ( (internal_train_stopped = FALSE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_interlock_between_doors_and_traction_activated = TRUE) &

    ( (internal_train_stopped = FALSE & internal_train_in_platform = TRUE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> emergency_break_applied_by_switch = TRUE) &

	( (internal_train_stopped = FALSE & internal_train_in_platform = FALSE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_cab_emergency_sound_alarm_activated = TRUE) &

    ( (internal_train_stopped = FALSE & internal_train_in_platform = FALSE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_control_mechanic_emergency_switch_actuation = TRUE) &

    ( (internal_train_stopped = FALSE & internal_train_in_platform = FALSE & 
    internal_doors_closed_and_locked = TRUE &
    some_mechanic_emergency_switch_actuated = TRUE) 
		=> internal_authorize_emergency_switch_operation = FALSE)


INITIALISATION
    doors_right_open_simultaneously := FALSE ||
    doors_left_open_simultaneously := FALSE ||    
    doors_right_close_simultaneously := FALSE ||
    doors_left_close_simultaneously := FALSE ||
        
    opening_side := NO_SIDE ||
    closing_side := NO_SIDE ||
    
    conditions_to_open_satisfied := FALSE ||
    conditions_to_close_satisfied := FALSE ||
    
    internal_speed_over_6km_h := FALSE ||
    internal_train_mode := NO_MODE ||
    internal_train_stopped := TRUE ||
    internal_train_lined_up := TRUE ||
    internal_train_in_platform := TRUE ||
    internal_cab_consol_open_button_pushed, internal_cab_side_open_button_pushed := FALSE, FALSE || 
    internal_cab_consol_close_button_pushed, internal_cab_side_close_button_pushed := FALSE, FALSE || 
    internal_command_to_open_from_ATC, internal_command_to_close_from_ATC := FALSE, FALSE || 
    
    internal_doors_closed_and_locked := TRUE ||
    
    internal_cab_consol_signalization_doors_closed_and_locked := TRUE ||
    internal_cab_emergency_sound_alarm_activated := FALSE ||
    
    internal_interlock_between_doors_and_traction_activated := FALSE ||
    emergency_break_applied_by_switch := FALSE ||
    
    some_mechanic_emergency_switch_actuated := FALSE ||
    some_door_emergency_switch_actuated := FALSE ||

    internal_control_mechanic_emergency_switch_actuation := FALSE ||
    internal_control_door_emergency_switch_actuation := FALSE ||
    internal_authorize_emergency_switch_operation := FALSE
    
OPERATIONS
    simultaneous_opening_all_doors_right =
    PRE internal_speed_over_6km_h = FALSE & conditions_to_open_satisfied = TRUE
    THEN
        doors_right_open_simultaneously := TRUE ||
        internal_doors_closed_and_locked := FALSE ||
        internal_interlock_between_doors_and_traction_activated := TRUE ||
        internal_cab_consol_signalization_doors_closed_and_locked := FALSE 
    END
    ;

	simultaneous_opening_all_doors_left =
    PRE internal_speed_over_6km_h = FALSE & conditions_to_open_satisfied = TRUE
   	THEN
   	    doors_left_open_simultaneously := TRUE || 
   	    internal_doors_closed_and_locked := FALSE ||
   	    internal_interlock_between_doors_and_traction_activated := TRUE ||
   	    internal_cab_consol_signalization_doors_closed_and_locked := FALSE
    END
    ;
      
    simultaneous_closing_all_doors_right =
    PRE conditions_to_close_satisfied = TRUE
    THEN
        doors_right_close_simultaneously := TRUE || 
        internal_doors_closed_and_locked := TRUE ||
        internal_cab_consol_signalization_doors_closed_and_locked := TRUE ||
        internal_control_mechanic_emergency_switch_actuation, internal_authorize_emergency_switch_operation, internal_cab_emergency_sound_alarm_activated, internal_interlock_between_doors_and_traction_activated, emergency_break_applied_by_switch <-- update_mechanic_emergency_conditions (internal_train_stopped, internal_train_in_platform, TRUE, some_mechanic_emergency_switch_actuated)    
    END
    ;
    
	simultaneous_closing_all_doors_left =
	PRE conditions_to_close_satisfied = TRUE
	THEN
    	doors_left_close_simultaneously := TRUE || 
    	internal_doors_closed_and_locked := TRUE ||
    	internal_cab_consol_signalization_doors_closed_and_locked := TRUE||
    	internal_control_mechanic_emergency_switch_actuation, internal_authorize_emergency_switch_operation, internal_cab_emergency_sound_alarm_activated, internal_interlock_between_doors_and_traction_activated, emergency_break_applied_by_switch <-- update_mechanic_emergency_conditions (internal_train_stopped, internal_train_in_platform, TRUE, some_mechanic_emergency_switch_actuated)
    END
    ;
    
    set_opening_side (side) =
    PRE
        side : SIDE
    THEN
        opening_side := side
    END
    ;
    
    set_closing_side (side) =
    PRE
        side : SIDE
    THEN
        closing_side := side
    END
    ;
        
    set_internal_speed (speed_over) = 
    PRE speed_over : BOOL
    THEN
        internal_speed_over_6km_h := speed_over ||
        conditions_to_open_satisfied, doors_right_open_simultaneously, doors_left_open_simultaneously <-- update_opening_conditions (internal_train_stopped, internal_train_lined_up, internal_train_in_platform, speed_over, internal_train_mode) 
    END
    ;
    
    set_internal_train_mode (mode) = 
    PRE mode : OPERATION_MODE
    THEN
        internal_train_mode := mode ||
        conditions_to_open_satisfied, doors_right_open_simultaneously, doors_left_open_simultaneously <-- update_opening_conditions (internal_train_stopped, internal_train_lined_up, internal_train_in_platform, internal_speed_over_6km_h, mode) ||
        conditions_to_close_satisfied, doors_right_close_simultaneously, doors_left_close_simultaneously <-- update_closing_conditions (mode, internal_command_to_close_from_ATC, internal_cab_consol_open_button_pushed)
    END
    ;
    
    set_internal_train_stopped (stopped) = 
    PRE stopped : BOOL
    THEN
        internal_train_stopped := stopped ||
        conditions_to_open_satisfied, doors_right_open_simultaneously, doors_left_open_simultaneously <-- update_opening_conditions (stopped, internal_train_lined_up, internal_train_in_platform, internal_speed_over_6km_h, internal_train_mode) ||
    	internal_control_mechanic_emergency_switch_actuation, internal_authorize_emergency_switch_operation, internal_cab_emergency_sound_alarm_activated, internal_interlock_between_doors_and_traction_activated, emergency_break_applied_by_switch <-- update_mechanic_emergency_conditions (stopped, internal_train_in_platform, internal_doors_closed_and_locked, some_mechanic_emergency_switch_actuated)
    END
    ;
    
    set_internal_train_lined_up (lined_up) = 
    PRE lined_up : BOOL
    THEN
        internal_train_lined_up := lined_up ||
     	conditions_to_open_satisfied, doors_right_open_simultaneously, doors_left_open_simultaneously <-- update_opening_conditions (internal_train_stopped, lined_up, internal_train_in_platform, internal_speed_over_6km_h, internal_train_mode)
    END
    ;

    set_internal_train_in_platform (in_platform) = 
    PRE in_platform : BOOL
    THEN
        internal_train_in_platform := in_platform ||
        conditions_to_open_satisfied, doors_right_open_simultaneously, doors_left_open_simultaneously <-- update_opening_conditions (internal_train_stopped, internal_train_lined_up, in_platform, internal_speed_over_6km_h, internal_train_mode)||
        internal_control_mechanic_emergency_switch_actuation, internal_authorize_emergency_switch_operation, internal_cab_emergency_sound_alarm_activated, internal_interlock_between_doors_and_traction_activated, emergency_break_applied_by_switch <-- update_mechanic_emergency_conditions (internal_train_stopped, in_platform, internal_doors_closed_and_locked, some_mechanic_emergency_switch_actuated)
    END
    ;
    
    set_internal_cab_consol_open_button_pressed (pressed) = 
    PRE
        pressed : BOOL
    THEN    
        internal_cab_consol_open_button_pushed := pressed ||
        conditions_to_close_satisfied, doors_right_close_simultaneously, doors_left_close_simultaneously <-- update_closing_conditions (internal_train_mode, internal_command_to_close_from_ATC, pressed)
    END
    ;
   
    set_internal_cab_side_open_button_pressed (pressed) = 
    PRE
        pressed : BOOL
    THEN    
    	internal_cab_side_open_button_pushed := pressed
    END
    ;
   
    set_internal_command_to_open_from_ATC (to_open) = 
    PRE
        to_open : BOOL
    THEN    
    	internal_command_to_open_from_ATC := to_open
    END
    ;
   
    set_internal_cab_consol_close_button_pressed (pressed) = 
    PRE
        pressed : BOOL
    THEN    
    	internal_cab_consol_close_button_pushed := pressed
    END
    ;
   
    set_internal_cab_side_close_button_pressed (pressed) = 
    PRE
        pressed : BOOL
    THEN    
    	internal_cab_side_close_button_pushed := pressed
    END
    ;
   
    set_internal_command_to_close_from_ATC (to_close) = 
    PRE
        to_close : BOOL
    THEN    
        internal_command_to_close_from_ATC := to_close ||
        conditions_to_close_satisfied, doors_right_close_simultaneously, doors_left_close_simultaneously <-- update_closing_conditions (internal_train_mode, to_close, internal_cab_consol_open_button_pushed)
    END
    ;

    set_actuation_mechanic_emergency_switch (is_activated) =
    PRE is_activated : BOOL
    THEN
        some_mechanic_emergency_switch_actuated := is_activated ||
        internal_control_mechanic_emergency_switch_actuation, internal_authorize_emergency_switch_operation, internal_cab_emergency_sound_alarm_activated, internal_interlock_between_doors_and_traction_activated, emergency_break_applied_by_switch <-- update_mechanic_emergency_conditions (internal_train_stopped, internal_train_in_platform, internal_doors_closed_and_locked, is_activated)
    END
    ;
    
    set_actuation_door_emergency_switch (is_activated) =
    PRE is_activated : BOOL 
    THEN
        some_door_emergency_switch_actuated := is_activated 
    END
   
END
