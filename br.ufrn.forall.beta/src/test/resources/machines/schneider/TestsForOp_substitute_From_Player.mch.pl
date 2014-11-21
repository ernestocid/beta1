spec_trans(root,'$initialise_machine',1).
spec_trans(0,'$initialise_machine',1).
spec_trans(1,'substitute_test1(PLAYER8,PLAYER21,{PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER13,PLAYER14,PLAYER15,PLAYER17,PLAYER22,PLAYER23,PLAYER24})',1).
spec_trans(1,'substitute_test2(PLAYER6,PLAYER22,{PLAYER5,PLAYER7,PLAYER8,PLAYER13,PLAYER14,PLAYER15,PLAYER16,PLAYER17,PLAYER21,PLAYER23,PLAYER24})',1).
spec_trans(1,'substitute_test3(PLAYER6,PLAYER6,{PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER13,PLAYER14,PLAYER15,PLAYER16,PLAYER21,PLAYER22,PLAYER24})',1).
spec_trans(1,'substitute_test4(PLAYER8,PLAYER21,{PLAYER5,PLAYER6,PLAYER7,PLAYER13,PLAYER15,PLAYER16,PLAYER17,PLAYER21,PLAYER22,PLAYER23,PLAYER24})',1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(1).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
