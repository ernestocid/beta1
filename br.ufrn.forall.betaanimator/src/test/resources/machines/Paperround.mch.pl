spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{1,2,4,5,6,10,14,15,16,17,20,21,25,29,30,31,32,33,34,35,36,39,40,41,42,47,51,53,56,57,61,62,66,71,72,74,79,82,88,89,91,92,97,99,102,108,109,113,120,123,124,133,138,139,145,153,154,156,157,158})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
