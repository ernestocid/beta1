spec_trans(root,'$initialise_machine',1).
spec_trans(0,'$initialise_machine',1).
spec_trans(1,'lua_arith_test2([(LUA_TNUMBER|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1)),(LUA_TNIL|->((nil|->false)|->-1))],1,1,LUA_OPUNM)',1).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_max_reached_for_node(1).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
