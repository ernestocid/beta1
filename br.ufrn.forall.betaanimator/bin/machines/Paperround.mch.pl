spec_trans(root,'$initialise_machine',0).
spec_trans(0,'addPaper(1)',1).
spec_trans(0,'addPaperTest(1,{})',0).
spec_trans(0,'addPaperTest2(1,{})',0).
spec_trans(0,'addPaper_MF_Test(1,{})',0).
spec_trans(0,'remove(1)',0).
spec_not_all_transitions_added(1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(0).
spec_completely_explored :-
        fail.
