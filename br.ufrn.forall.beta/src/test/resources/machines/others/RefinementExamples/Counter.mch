/* Counter
 * Author: 
 * Creation date: 24/09/14
 */
MACHINE
    Counter
VARIABLES
    value, overflow, underflow
INVARIANT
    value : INT & 
    overflow : BOOL & 
    underflow : BOOL &
    value >= MININT &
    value <= MAXINT &
    ((underflow = TRUE) => (value = MININT)) &
    ((overflow = TRUE) => (value = MAXINT)) 
INITIALISATION
    value := 0 || overflow := FALSE || underflow := FALSE
OPERATIONS
    zero = 
    BEGIN
        value := 0 || overflow := FALSE || underflow := FALSE
    END;
    inc =
    PRE
	value : MININT..MAXINT
    THEN
        IF value < MAXINT THEN
            value := succ(value) ||
            underflow := FALSE
        ELSE
            overflow := TRUE
        END
    END;
    dec = 
    PRE
	value : MININT..MAXINT
    THEN
    IF value > MININT THEN
            value := pred(value) ||
            overflow := FALSE
        ELSE
            underflow := TRUE
        END
    END
    ;
    res <-- get = 
    BEGIN
	res := value
   END
   
END
