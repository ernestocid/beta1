spec_trans(root,'$initialise_machine',0).
spec_trans(0,'Set_test1(21,21)',0).
spec_trans(0,'Set_test2(0,-1)',0).
spec_trans(0,'Set_test3(21,22)',0).
spec_trans(0,'Set_test4(0,21)',0).
spec_trans(0,'Set_test5(21,0)',0).
spec_trans(0,'Set_test6(0,0)',0).
spec_trans(0,'Set_test7(-1,0)',0).
spec_trans(0,'Set_test8(0,1)',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
