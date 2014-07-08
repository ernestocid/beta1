MACHINE IFWithNoPrecondition

VARIABLES xx

INVARIANT
 xx : NAT

INITIALISATION xx := 0

OPERATIONS
  operate = IF xx = 1 THEN skip ELSE skip
END

END

