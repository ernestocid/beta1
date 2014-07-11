/* Opening
 * Author: Haniel Barbosa
 */
MACHINE
    Opening
    
SEES Sets
    
OPERATIONS
    conditions_to_open, doors_right_open_simultaneously, doors_left_open_simultaneously <-- update_opening_conditions (stopped, lined_up, in_platform, over_6km, mode) =
    PRE stopped : BOOL &
        lined_up : BOOL &
        in_platform : BOOL &
        over_6km : BOOL &
        mode : OPERATION_MODE
    THEN
        IF over_6km = TRUE
        THEN
            /*block_opening*/
            conditions_to_open := FALSE ||
            doors_right_open_simultaneously := FALSE || doors_left_open_simultaneously := FALSE
        ELSE IF (mode = MAN) or ((mode = MCS or mode = ATO) & (stopped = TRUE & lined_up = TRUE & in_platform = TRUE)) THEN
            /*Opening allowed*/
            conditions_to_open := TRUE ||
       	    doors_right_open_simultaneously := FALSE || doors_left_open_simultaneously := FALSE
       		ELSE
       	    	/*block opening*/
        	    conditions_to_open := FALSE ||
        	    doors_right_open_simultaneously := FALSE || doors_left_open_simultaneously := FALSE
        	END
        END
    END  
    
END
