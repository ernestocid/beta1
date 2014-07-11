/* counter
* Author: David Deharbe
* Modificaitons: Ernesto Cid
* Creation date: 6/6/13
*/
MACHINE
    CounterModified
VARIABLES
    value, overflow
INVARIANT
    value: INT & 
    overflow: BOOL &
    0 <= value & 
    value <= MAXINT &
    ((overflow = TRUE) => (value = MAXINT))
INITIALISATION
    value := 0 || overflow := FALSE
OPERATIONS
    zero = 
    BEGIN
        value := 0 || overflow := FALSE
    END;
    inc =
    PRE
        value : 0..MAXINT-1
    THEN
        IF value < MAXINT THEN
            value := value + 1
        ELSE
            overflow := TRUE
        END
    END;
    res <-- get = res := value
END
