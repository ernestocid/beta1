spec_trans(root,'$initialise_machine',1).
spec_trans(0,'$initialise_machine',1).
spec_trans(1,'substitute_test1(PLAYER1,{PLAYER1,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11,PLAYER12},PLAYER2)',1).
spec_trans(1,'substitute_test2(PLAYER1,{PLAYER2,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11,PLAYER12},PLAYER1)',1).
spec_trans(1,'substitute_test3(PLAYER1,{PLAYER1,PLAYER2,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11},PLAYER1)',1).
spec_trans(1,'substitute_test4(PLAYER1,{PLAYER2,PLAYER3,PLAYER4,PLAYER5,PLAYER6,PLAYER7,PLAYER8,PLAYER9,PLAYER10,PLAYER11,PLAYER12},PLAYER2)',1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(1).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
