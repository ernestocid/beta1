/* Calendar
 * Author: 
 * Creation date: 11/09/14
 */
MACHINE
    Calendar
SETS
    MONTH = {January, February, March, April, May, June, July, August, September, October, November, December, Invalid_Month}
    
OPERATIONS
    mm <-- month(nn) = 
    PRE
        nn : 1..12
    THEN
        CASE nn OF
            EITHER 1 THEN
                mm := January
            OR 2 THEN
                mm := February
            OR 3 THEN
                mm := March
            OR 4 THEN
                mm := April
            OR 5 THEN
                mm := May
            OR 6 THEN
                mm := June
            OR 7 THEN
                mm := July
            OR 8 THEN
                mm := August
            OR 9 THEN
                mm := September
            OR 10 THEN
                mm := October
            OR 11 THEN
                mm := November
            OR 12 THEN
                mm := December
            END
       END
    END
END
