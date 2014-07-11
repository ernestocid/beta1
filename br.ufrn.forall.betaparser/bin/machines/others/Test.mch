MACHINE
    Test

ABSTRACT_VARIABLES
    cab_consol_open_button_pushed,
    cab_side_open_button_pushed,
    cab_consol_close_button_pushed,
    cab_side_close_button_pushed,
    test_variable,
    test_variable2,
    test_variable3,
    foo

INVARIANT
    cab_consol_open_button_pushed : BOOL &
    cab_side_open_button_pushed : BOOL &
    cab_consol_close_button_pushed : BOOL &
    cab_side_close_button_pushed : BOOL &
    foo : BOOL &
    test_variable <: NAT1 &
    test_variable2 <: NAT1 &
    test_variable3 <: NAT1 &
    (test_variable2 = test_variable => test_variable3 /= test_variable) &
    (test_variable3 = test_variable => foo = TRUE)
    
INITIALISATION
    cab_consol_open_button_pushed, cab_side_open_button_pushed := FALSE, FALSE ||
    cab_consol_close_button_pushed, cab_side_close_button_pushed := FALSE, FALSE ||
    test_variable := {} ||
    test_variable2 := {} ||
    test_variable3 := {} ||
    foo := TRUE

OPERATIONS
    
    set_cab_consol_open_button_pressed (pressed) =
    PRE
        pressed : BOOL & (pressed = TRUE => foo = FALSE)
    THEN
    	cab_consol_open_button_pushed := pressed
    END
    ;
    
    set_cab_side_open_button_pressed (pressed) =
    PRE
        pressed : BOOL
    THEN
    	cab_side_open_button_pushed := pressed
    END
    ;
    
    set_cab_consol_close_button_pressed (pressed) =
    PRE
        pressed : BOOL
    THEN
    	cab_consol_close_button_pushed := pressed
    END
    ;

    set_cab_side_close_button_pressed (pressed) =
    PRE
        pressed : BOOL
    THEN
    	cab_side_close_button_pushed := pressed
    END
END