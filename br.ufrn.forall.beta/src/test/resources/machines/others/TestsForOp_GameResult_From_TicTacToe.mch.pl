spec_trans(root,'$initialise_machine',1).
spec_trans(0,'$initialise_machine',1).
spec_trans(1,'GameResult_test1(blue,{3,4,5,6,7,8},{0,1,2})',1).
spec_trans(1,'GameResult_test2(blue,{0,1,2,3,4,5,6,7,8},{})',1).
spec_trans(1,'GameResult_test3(blue,{7,8},{0,1,2,3,4,5,6})',1).
spec_trans(1,'GameResult_test4(blue,{2,3,7,8},{0,1,4,5,6})',1).
spec_trans(1,'GameResult_test5(blue,{3,4,5},{0,1,2})',1).
spec_trans(1,'GameResult_test6(blue,{0,1,2},{})',1).
spec_trans(1,'GameResult_test7(blue,{},{0,1,2})',1).
spec_trans(1,'GameResult_test8(blue,{},{})',1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(1).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
