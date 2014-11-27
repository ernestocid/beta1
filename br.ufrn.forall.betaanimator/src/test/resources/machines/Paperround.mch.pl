spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{1,2,3,4,5,10,11,20,23,25,27,29,30,35,39,41,42,43,46,48,51,57,58,59,61,62,65,66,68,75,76,81,82,84,87,89,90,91,95,98,102,103,104,106,107,112,123,124,127,128,132,133,134,137,138,143,151,153,154,155})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
