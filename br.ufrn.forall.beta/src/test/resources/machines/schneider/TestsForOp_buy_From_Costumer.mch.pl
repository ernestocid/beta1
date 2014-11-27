spec_trans(root,'$initialise_machine',1).
spec_trans(0,'$initialise_machine',1).
spec_trans(1,'buy_test1({},{(GOODS1|->2),(GOODS2|->1)},GOODS1)',1).
spec_trans(1,'buy_test2({},{(GOODS1|->1),(GOODS2|->1)},GOODS2)',1).
spec_trans(1,'Price.setprice(GOODS1,1)',1).
spec_trans(1,'Price.pricequery(GOODS1)-->1',1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(1).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
