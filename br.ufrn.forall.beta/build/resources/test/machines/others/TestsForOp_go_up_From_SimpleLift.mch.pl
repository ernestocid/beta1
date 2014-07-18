spec_trans(root,'$initialise_machine',0).
spec_trans(0,'go_up_test1(1)',0).
spec_trans(0,'go_up_test2(5)',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
