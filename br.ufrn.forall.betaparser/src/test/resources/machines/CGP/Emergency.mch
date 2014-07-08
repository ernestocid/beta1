/* Emergency
 * Author: Haniel Barbosa
 */
MACHINE
    Emergency
    
SEES Sets
    
OPERATIONS
    /*Update conditions of the mechanic emergency switch actuation:*/
    control_mechanic_actuation, authorize_emergency, cab_emergency_sound, interlock, emergency_break <-- update_mechanic_emergency_conditions (stopped, in_platform, doors_closed_and_locked, mechanic_actuated) =
    PRE stopped : BOOL &
        in_platform : BOOL &
        doors_closed_and_locked : BOOL &
        mechanic_actuated : BOOL
    THEN
        IF stopped = TRUE & in_platform = TRUE & doors_closed_and_locked = TRUE & mechanic_actuated = TRUE 
        THEN
            /*6.2.1.2*/
            control_mechanic_actuation := TRUE || authorize_emergency := TRUE || cab_emergency_sound := TRUE || interlock := TRUE || emergency_break := FALSE
        ELSE IF stopped = FALSE & in_platform = TRUE & doors_closed_and_locked = TRUE & mechanic_actuated = TRUE
        THEN
			/*6.2.1.3*/                
            control_mechanic_actuation := TRUE || authorize_emergency := FALSE || cab_emergency_sound := TRUE || interlock := TRUE || emergency_break := TRUE 
        ELSE IF stopped = FALSE & in_platform = FALSE & doors_closed_and_locked = TRUE & mechanic_actuated = TRUE
        THEN
            /*6.2.1.4*/                
            control_mechanic_actuation := TRUE || authorize_emergency := FALSE || cab_emergency_sound := TRUE || interlock := FALSE || emergency_break := TRUE   
        END
        END
        END        
    END  
    /*;*/
    
    /*Update conditions of the door emergency switch actuation:*/
    /*
    control_door_actuation, authorize_emergency, cab_emergency_sound, interlock, emergency_break, doors_right_open, doors_left_open <-- update_door_emergency_conditions (stopped, over_6km, in_platform, doors_closed, doors_locked, opening_side, door_actuated, side) =
    PRE stopped : BOOL &
        over_6km : BOOL &
        in_platform : BOOL &
        doors_closed : BOOL &
        doors_locked : BOOL &
        opening_side : SIDE &
        door_actuated : BOOL &
        side : SIDE
    THEN
        IF stopped = TRUE & over_6km = FALSE & in_platform = TRUE & doors_closed = TRUE & doors_locked = TRUE & door_actuated = TRUE & (opening_side = side)
        THEN
            *6.2.2.3*
            control_door_actuation := TRUE || authorize_emergency := TRUE || cab_emergency_sound := TRUE || interlock := TRUE || emergency_break := FALSE || 
            IF side = RIGHT THEN
                doors_right_open := TRUE ||
                doors_left_open := FALSE
            ELSE
                doors_left_open := TRUE ||
                doors_right_open := FALSE
            END
*        ELSE IF stopped = FALSE & in_platform = TRUE & doors_closed = TRUE & doors_locked = TRUE & mechanic_actuated = TRUE
        THEN
            control_mechanic_actuation := TRUE || authorize_emergency := FALSE || cab_emergency_sound := TRUE || interlock := TRUE || emergency_break := TRUE 
        ELSE IF stopped = FALSE & in_platform = FALSE & doors_closed = TRUE & doors_locked = TRUE & mechanic_actuated = TRUE
        THEN
        	control_mechanic_actuation := TRUE || authorize_emergency := FALSE || cab_emergency_sound := TRUE || interlock := FALSE || emergency_break := TRUE   
        END
        END*
        END        
    END  
    */

END
