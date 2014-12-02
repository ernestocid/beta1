spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{1,2,4,5,6,7,8,9,10,13,14,15,16,18,20,21,23,24,26,30,32,39,40,41,42,43,52,56,61,63,65,71,74,79,82,87,88,92,93,94,95,96,100,102,107,112,113,114,117,121,123,129,134,135,137,141,142,143,158,162})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
