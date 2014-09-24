spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'addPaper_MF_Test_N2(1,{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
