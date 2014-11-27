spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{1,3,4,5,10,11,16,19,20,21,24,26,27,29,30,34,37,41,42,46,47,50,53,56,57,58,61,66,80,82,85,87,89,92,93,97,98,100,108,109,111,117,118,120,123,124,129,130,133,134,135,139,142,143,146,147,148,153,158,161})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
