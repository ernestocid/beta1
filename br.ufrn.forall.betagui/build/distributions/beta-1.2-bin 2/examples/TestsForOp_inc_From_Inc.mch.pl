spec_trans(root,'$initialise_machine',0).
spec_trans(0,'inc_test1(0,30)',0).
spec_trans(0,'inc_test2(0,-20)',0).
spec_trans(0,'inc_test3(1,30)',0).
spec_trans(0,'inc_test4(2,29)',0).
spec_trans(0,'inc_test5(-20,30)',0).
spec_trans(0,'inc_test6(31,-20)',0).
spec_trans(0,'inc_test7(31,30)',0).
spec_trans(0,'inc_test8(51,-20)',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
