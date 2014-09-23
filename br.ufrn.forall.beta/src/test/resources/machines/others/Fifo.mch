/* Fifo
 * Author: 
 * Creation date: 20/09/14
 */
MACHINE Fifo
CONSTANTS
    cap
PROPERTIES
    cap : NAT1
VARIABLES
    contents
INVARIANT
    contents : seq(INT) & size(contents) <= cap
INITIALISATION
    contents := []
OPERATIONS
    input(ee) = 
    PRE
        ee : INT & size(contents) < cap
    THEN
        contents := contents <- ee
    END;
    ee <-- output = 
    PRE
        size(contents) > 0
    THEN
        ee := first(contents) || contents := tail(contents)
    END
END
