MACHINE           Paperround
/* This B Machine is taken from the book: The B-Method: An Introduction
   (Editor Palgrave, ISBN 0-33-79284-X, Year 2002) by Steve Schneider.
   This machine is distributed with kind permission by Steve Schneider.
*/
VARIABLES         papers, magazines

INVARIANT         papers <: 1..163 & magazines <: papers & card(papers) <= 60

INITIALISATION    papers := {} || magazines := {}

OPERATIONS

addpaper(hh) =
  PRE hh : 1..163 & card(papers) < 60
  THEN papers := papers \/ {hh}
  END;

addmagazine(hh) =
  PRE hh : papers
  THEN magazines := magazines \/ {hh} 
  END;

remove(hh) =
  PRE hh : 1..163
  THEN papers := papers - {hh} || magazines := magazines - {hh}
  END

END
