spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{1,2,4,10,11,12,13,14,15,16,18,20,22,26,27,32,41,44,51,53,56,58,60,61,63,66,67,69,71,72,73,74,75,76,78,82,83,86,87,90,91,92,97,98,100,101,102,103,105,106,107,123,128,133,134,140,143,148,157,162})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
