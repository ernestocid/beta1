
MACHINE RegistrarPP
SETS /* deferred */
  PERSON
 ; /* enumerated */
  SEX={boy,girl}
ABSTRACT_VARIABLES
  male,
  female,
  marriage
INVARIANT
    male : POW(PERSON)
  & female : POW(PERSON)
  & male /\ female = {}
  & marriage : male >+> female
INITIALISATION
    BEGIN
           male := {}
        ||
           female := {}
      ;
        marriage := {}
    END
OPERATIONS
  dies(nn) = 
    PRE 
        nn : male \/ female
    THEN
         die(nn)
      ||
         IF nn : dom(marriage) THEN
           part(nn,marriage(nn))
         ELSIF nn : ran(marriage) THEN
           part((marriage~)(nn),nn)
         END
    END;
  
  born(nn,ss) = 
    PRE 
        nn : PERSON
      & ss : SEX
      & nn /: (male \/ female)
    THEN
      IF ss = boy THEN
        male := male \/ {nn}
      ELSE
        female := female \/ {nn}
      END
    END;
  
  die(nn) = 
    PRE 
        nn : male \/ female
    THEN
      IF nn : male THEN
        male := male - {nn}
      ELSE
        female := female - {nn}
      END
    END;
  
  wed(mm,ff) = 
    PRE 
        mm : male
      & mm /: dom(marriage)
      & ff : female
      & ff /: ran(marriage)
    THEN
      marriage(mm) := ff
    END;
  
  part(mm,ff) = 
    PRE 
        mm : male
      & ff : female
      & mm |-> ff : marriage
    THEN
      marriage := marriage - {mm |-> ff}
    END;
  
  pp <-- partner(nn) = 
    PRE 
        nn : dom(marriage) \/ ran(marriage)
    THEN
      IF nn : dom(marriage) THEN
        pp := marriage(nn)
      ELSE
        pp := (marriage~)(nn)
      END
    END
END
