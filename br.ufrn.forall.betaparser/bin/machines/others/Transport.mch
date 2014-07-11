MACHINE
    Transport
SETS
    CARD_TYPES = {entire_card, student_card, gratutious_card}
    
VARIABLES
    balance, card_type, time
INVARIANT
    balance : NAT &
    time : (0..23) * (0..59) &
    card_type : CARD_TYPES &
    (card_type = gratutious_card => balance = 0)
    
INITIALISATION
    balance := 0 ||
    time := 0 |-> 0 ||
    card_type :: CARD_TYPES
    
OPERATIONS
    addCredit(cr) =
    PRE
        cr : NAT1 &
        card_type /= gratutious_card &
        balance + cr <= MAXINT
    THEN
        balance := balance + cr
    END
END