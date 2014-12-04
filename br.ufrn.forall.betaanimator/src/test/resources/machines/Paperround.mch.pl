spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{1,8,10,13,16,17,20,23,25,26,36,37,41,42,49,51,52,60,61,63,65,66,68,73,76,80,81,82,83,84,90,91,92,93,94,95,96,98,102,103,104,106,107,112,117,118,119,120,123,125,128,129,130,133,138,143,148,153,154,155})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
