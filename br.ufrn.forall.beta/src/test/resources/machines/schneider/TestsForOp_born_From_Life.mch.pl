spec_trans(root,'$initialise_machine',0).
spec_trans(0,'born_test1(girl,{},{},PERSON1)',0).
spec_trans(0,'born_test2(boy,{},{},PERSON2)',0).
spec_trans(0,'born_test3(girl,{},{PERSON1},PERSON1)',0).
spec_trans(0,'born_test4(boy,{},{PERSON1},PERSON1)',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
