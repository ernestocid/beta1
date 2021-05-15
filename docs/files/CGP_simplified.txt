/* 
   This is a simplified version of the original CGP specification.
   It's not complete and will have errors when type checked
*/

MACHINE
   CGP
   
SEES Sets
    
INCLUDES Opening, Closing, Emergency
    
ABSTRACT_VARIABLES
    doors_right_open_simultaneously,
    doors_right_close_simultaneously,
    
    opening_side,
    closing_side,
    
    conditions_to_open_satisfied,
    conditions_to_close_satisfied,
    
    internal_speed_over_6km_h,
    internal_train_mode,
    internal_train_stopped,
    internal_train_lined_up,
    internal_train_in_platform

    /* Remainder of the variables were removed */
        
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


    /************* Opening and Closing *************/
    
    ((internal_speed_over_6km_h = TRUE) => ( doors_right_open_simultaneously = FALSE & doors_left_open_simultaneously = FALSE )) & 
    
    (( doors_right_open_simultaneously = TRUE or doors_left_open_simultaneously = TRUE ) => (conditions_to_open_satisfied = TRUE)) & 
    
    (( doors_right_close_simultaneously = TRUE or doors_left_close_simultaneously = TRUE ) => (conditions_to_close_satisfied = TRUE)) & 
    
    (((internal_train_mode = MCS or internal_train_mode = ATO) &
    	internal_train_stopped = TRUE & 
    	internal_train_lined_up = TRUE &
    	internal_train_in_platform = TRUE &
    	internal_speed_over_6km_h = FALSE ) =>
 			(conditions_to_open_satisfied = TRUE)	
  	) &
 
	  (((internal_train_mode = MAN) &
    	internal_speed_over_6km_h = FALSE ) =>
 			(conditions_to_open_satisfied = TRUE)	
	  ) &	
	
    (((internal_train_mode = ATO) &
	    internal_cab_consol_open_button_pushed = TRUE &
	    internal_command_to_close_from_ATC = TRUE ) =>
 			(conditions_to_close_satisfied = FALSE)	
	  )

    /* Remainder of the invariant was removed */

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
    internal_train_in_platform := TRUE
    
    /* Remainder of the initialisation was removed */
    
OPERATIONS
    simultaneous_opening_all_doors_right =
    PRE internal_speed_over_6km_h = FALSE & conditions_to_open_satisfied = TRUE
    THEN
        doors_right_open_simultaneously := TRUE ||
        internal_doors_closed_and_locked := FALSE ||
        internal_interlock_between_doors_and_traction_activated := TRUE ||
        internal_cab_consol_signalization_doors_closed_and_locked := FALSE 
    END

    /* Remainder of the operations were removed */
END
