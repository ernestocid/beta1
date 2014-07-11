/* Closing
 * Author: Haniel Barbosa
 */
MACHINE
    Closing
    
SEES Sets
    
OPERATIONS
    /*To add: side*/
    conditions_to_close, doors_right_close_simultaneously, doors_left_close_simultaneously <-- update_closing_conditions (mode, command_to_close_from_ATC, command_to_open_from_cab_consol) =
    PRE mode : OPERATION_MODE &
        command_to_close_from_ATC : BOOL &
        command_to_open_from_cab_consol : BOOL
    THEN
        IF mode = ATO & command_to_close_from_ATC = TRUE & command_to_open_from_cab_consol = TRUE
        THEN
            /*block_closing*/
            conditions_to_close := FALSE ||
            doors_right_close_simultaneously := FALSE || doors_left_close_simultaneously := FALSE
        ELSE
            /*Closing allowed*/
            conditions_to_close := TRUE ||
       	    doors_right_close_simultaneously := FALSE || doors_left_close_simultaneously := FALSE
        END
    END  

END
