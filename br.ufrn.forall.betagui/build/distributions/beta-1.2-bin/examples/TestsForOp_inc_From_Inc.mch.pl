spec_trans(root,'$initialise_machine',0).
spec_trans(0,'inc_test1(0,20)',0).
spec_trans(0,'inc_test2(0,-1)',0).
spec_trans(0,'inc_test3(1,20)',0).
spec_trans(0,'inc_test4(2,19)',0).
spec_trans(0,'inc_test5(-1,20)',0).
spec_trans(0,'inc_test6(21,-1)',0).
spec_trans(0,'inc_test7(21,20)',0).
spec_trans(0,'inc_test8(22,-1)',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
