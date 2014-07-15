MACHINE Paperround
VARIABLES papers, magazines
INVARIANT
  papers <: 1..163 &
  magazines <: papers &
  card(papers) <= 60

INITIALISATION
  papers := {} ||
  magazines := {}

OPERATIONS
  addPaper(hh) = 
    PRE
      hh : 1..163 & card(papers) < 60
    THEN
      papers := papers \/ {hh}	
    END;
  
  addPaperTest(hh, papers) =
    PRE
      hh : 1..163 &
      papers <: 1..163 &
      card(papers) < 60
    THEN
      skip
    END;

  addPaperTest2(hh, papers) =
    PRE
      hh : 1..163 &
      papers <: 1..163 &
      card(papers) <= 60
    THEN
      skip
    END;

  addPaper_MF_Test(hh, papers) =
    PRE
      hh : 1..163 &
      papers <: 1..163 &
      card(papers) <= 60 &
      card(papers) < 60
    THEN
      skip
    END;
  
  addPaper_MF_Test_N1(hh, papers) =
    PRE
      hh : 1..163 &
      papers <: 1..163 &
      not(card(papers) <= 60) &
      card(papers) < 60
    THEN
      skip
    END;

  addPaper_MF_Test_N2(hh, papers) =
    PRE
      hh : 1..163 &
      papers <: 1..163 &
      card(papers) <= 60 &
      not(card(papers) < 60)
    THEN
      skip
    END;

  addMagazine(hh) =
    PRE
      hh : papers
    THEN
      magazines := magazines \/ {hh}
    END;
  remove(hh) =
    PRE
      hh : 1..163
    THEN
      papers := papers - {hh} ||
      magazines := magazines - {hh}
    END
END
