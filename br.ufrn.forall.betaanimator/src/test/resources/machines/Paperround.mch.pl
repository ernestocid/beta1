spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{3,8,9,10,11,12,15,20,21,22,23,24,25,26,27,30,31,35,37,39,41,42,45,46,48,51,52,53,55,56,61,69,71,77,78,79,81,82,92,94,95,97,102,108,113,115,116,117,123,128,131,132,133,137,140,141,144,145,153,161})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
