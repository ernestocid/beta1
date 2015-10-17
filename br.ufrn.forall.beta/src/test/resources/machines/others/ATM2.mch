/* ATM
 * Author:
 * Creation date: 08/10/14
 */
MACHINE
    ATM2
VARIABLES
    account_id, account_balance
INVARIANT
    account_id : INT &
    account_id > 0 &
    account_balance : INT &
    account_balance >= 0
INITIALISATION
    account_id, account_balance := 1, 0
OPERATIONS
    deposit(mm) =
    PRE
        mm : INT & mm > 0
    THEN
        account_balance := account_balance + mm
    END;

    withdraw(mm) =
    PRE
        mm : INT & mm > 0 & account_balance >= mm
    THEN
        account_balance := account_balance - mm
    END;

    bb <-- balance = bb := account_balance

END
