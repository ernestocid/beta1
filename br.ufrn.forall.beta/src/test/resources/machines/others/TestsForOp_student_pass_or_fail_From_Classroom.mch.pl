spec_trans(root,'$initialise_machine',1).
spec_trans(0,'$initialise_machine',1).
spec_trans(1,'student_pass_or_fail_test2(TRUE,STUDENT4,{(STUDENT4|->4)},{(STUDENT4|->TRUE)},{STUDENT4})',1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(1).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
