spec_trans(root,'$initialise_machine',0).
spec_trans(0,'inc_test1(20,0)',0).
spec_trans(0,'inc_test2(-1,0)',0).
spec_trans(0,'inc_test3(20,1)',0).
spec_trans(0,'inc_test4(19,2)',0).
spec_trans(0,'inc_test5(20,-1)',0).
spec_trans(0,'inc_test6(-1,21)',0).
spec_trans(0,'inc_test7(20,21)',0).
spec_trans(0,'inc_test8(-1,22)',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
