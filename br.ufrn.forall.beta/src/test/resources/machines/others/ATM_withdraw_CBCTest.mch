MACHINE ATMCBCTest

INCLUDES ATM

PROMOTES balance, deposit

OPERATIONS
withdraw_test1(mm) =
PRE
mm : INT & not((account_balance - mm) >= 0)
THEN skip
END

END