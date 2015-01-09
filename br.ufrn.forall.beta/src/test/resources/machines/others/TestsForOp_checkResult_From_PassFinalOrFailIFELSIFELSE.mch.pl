spec_trans(root,'$initialise_machine',0).
spec_trans(0,'checkResult_test1(0)',0).
spec_trans(0,'checkResult_test2(0)',0).
spec_trans(0,'checkResult_test3(2)',0).
spec_trans(0,'checkResult_test4(4)',0).
spec_trans(0,'checkResult_test5(4)',0).
spec_trans(0,'checkResult_test6(0)',0).
spec_trans(0,'checkResult_test7(0)',0).
spec_trans(0,'checkResult_test8(-1)',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
