/* Calculator
 * Author: 
 * Creation date: 31/05/14
 */
MACHINE
    Calculator

OPERATIONS
    
    result <-- add(numberA, numberB) = 
    PRE
        numberA : INT & numberB : INT
    THEN
        result := numberA + numberB
    END;
    
    result <-- sub(numberA, numberB) = 
    PRE
        numberA : INT & numberB : INT
    THEN
        result := numberA - numberB
    END;

    result <-- mult(numberA, numberB) = 
    PRE
        numberA : INT & numberB : INT
    THEN
        result := numberA * numberB
    END;

    result <-- div(numberA, numberB) = 
    PRE
        numberA : INT & numberB : INT & numberB /= 0
    THEN
        result := numberA / numberB
    END;

    result <-- modulo(numberA, numberB) = 
    PRE
        numberA : INT & numberB : INT & numberB > 0
    THEN
        result := numberA mod numberB
    END;
    
    result <-- pow(numberA, numberB) = 
    PRE
        numberA : INT & numberB : INT & numberB : 0..MAXINT
    THEN
        result := numberA ** numberB
    END

END
