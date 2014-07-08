/* FunctionADT
 * Author: Anamaria
 * Creation date: Tue Jul 17 2012
 */
MACHINE
    FunctionADT
SEES
    BasicTypes,
    BasicTypeProjections,
    TableADT,
    UserdataADT
DEFINITIONS    
    /* true if the value is in its corresponding heap or if it does not need to be in 
    a heap */
    exists_in_heap(vv,th,uh) == 
        (vv: USERDATA_LUA_VALUES 
            => lua_userdata_from_lua_value(vv): dom(uh)) &
        (vv: TABLE_LUA_VALUES 
             => lua_table_from_lua_value(vv): dom(th));  
    /* true if the value is in the table heap or if it does not need to be in it */
    exists_in_table_heap(vv,th) == 
        (vv: TABLE_LUA_VALUES => lua_table_from_lua_value(vv): dom(th))          
CONSTANTS
    lua_value_from_function, 
    lua_function_from_lua_value, 
    
    function_application,
    select_results,
    select_table_heap,
    select_userdata_heap
    
PROPERTIES
    /* function that "construct" the record for each function */
    /* the values of the other fields are not specified - 
    	 any implementation would do*/ 
    lua_value_from_function : LUA_FUNCTION --> FUNCTION_LUA_VALUES &
    !bb. (bb:  LUA_FUNCTION => 
          (value_type(lua_value_from_function(bb)) = LUA_TFUNCTION &
           select_function(lua_value_from_function(bb)) = bb)) &
    
    /* function to extract the (Lua) function value from a LUA_VALUE */
    lua_function_from_lua_value : FUNCTION_LUA_VALUES --> LUA_FUNCTION &
    !vv. (vv: FUNCTION_LUA_VALUES => 
        lua_function_from_lua_value(vv) = select_function(vv))  &
    
    /* extracts the sequence of results of a function application */
    select_results : 
      (seq(LUA_VALUES) *                             /* the results */
	(LUA_TABLE +-> TABLE_CONTENTS) *               /* the new table heap */
	(LUA_USERDATA +-> USERDATA_CONTENTS))          /* the new userdata heap */
	-->
	seq(LUA_VALUES) &
	
    select_results = 
	(prj1(seq(LUA_VALUES) * (LUA_TABLE +-> TABLE_CONTENTS), 
	    (LUA_USERDATA +-> USERDATA_CONTENTS)) ; 
	prj1(seq(LUA_VALUES),(LUA_TABLE +-> TABLE_CONTENTS))) &

    /* extracts the new table heap of a function application */
    select_table_heap: 
      (seq(LUA_VALUES) *                             /* the results */
	(LUA_TABLE +-> TABLE_CONTENTS) *               /* the new table heap */
	(LUA_USERDATA +-> USERDATA_CONTENTS))          /* the new userdata heap */
	-->
	(LUA_TABLE +-> TABLE_CONTENTS) &
	
    select_table_heap =
	(prj1(seq(LUA_VALUES) * (LUA_TABLE +-> TABLE_CONTENTS), 
	    (LUA_USERDATA +-> USERDATA_CONTENTS)) ; 
	prj2(seq(LUA_VALUES),(LUA_TABLE +-> TABLE_CONTENTS)) )&

    /* extracts the new userdata heap of a function application */
    select_userdata_heap:   
    	seq(LUA_VALUES) *                             /* the results */
	(LUA_TABLE +-> TABLE_CONTENTS) *              /* the new table heap */
	(LUA_USERDATA +-> USERDATA_CONTENTS)          /* the new userdata heap */ 
	-->
	(LUA_USERDATA +-> USERDATA_CONTENTS)   & 
	
    select_userdata_heap = prj2(
        seq(LUA_VALUES) *(LUA_TABLE +-> TABLE_CONTENTS),
        (LUA_USERDATA +-> USERDATA_CONTENTS)) &

    /* simulates a function application, receiving the function identifier, 
	the application parameters and global state (table and userdata heap) and 
	the expected number of results, and providing the correct number of results, 
	a potentially changed table heap and a potentially changed userdata heap 
    */
    function_application:  
	(LUA_TABLE +-> TABLE_CONTENTS) *              /* the table heap */
	(LUA_USERDATA +-> USERDATA_CONTENTS) *        /* the userdata heap */ 
	seq(LUA_VALUES) *                             /* the parameters */
	NAT                                           /* expected number of results */ 
	--> 
	seq(LUA_VALUES) *                             /* the results */
	(LUA_TABLE +-> TABLE_CONTENTS) *              /* the new table heap */
	(LUA_USERDATA +-> USERDATA_CONTENTS)   &       /* the new userdata heap */ 

    /* the number of results is the expected one */ 
    !(th,uh,param,nn). (
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    size (select_results(function_application(th,uh,param,nn))) = nn) &

    /* all results are compatible with the new table and userdata heaps */
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !vv. (vv: ran(select_results(function_application(th,uh,param,nn))) =>
	        exists_in_heap(
	            vv,
	            select_table_heap(function_application(th,uh,param,nn)),
	            select_userdata_heap(function_application(th,uh,param,nn))))) &
	  
    /* the new userdata and table heaps are compatible */
    
    /* the new userdata heap is consistent with table heap */   
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !udata. (
	        udata: dom(select_userdata_heap(function_application(th,uh,param,nn)))  
	        =>
	        exists_in_table_heap (
	            udtable_of(select_userdata_heap(
	                    function_application(th,uh,param,nn))(udata)),
	            select_table_heap(function_application(th,uh,param,nn))))) &

    /* table cell contents and keys in the new table heap are consistent with 
	table and userdata heap */  	    	    
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !(tt,cc). (tt: dom(select_table_heap(function_application(th,uh,param,nn))) & 
	        cc: ran(select_table_heap(function_application(th,uh,param,nn))(tt)) 
	        => 
	        exists_in_heap (
	            cc,
	            select_table_heap(function_application(th,uh,param,nn)),
	            select_userdata_heap(function_application(th,uh,param,nn))))) &
      
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !(tt,kk). (tt: dom(select_table_heap(function_application(th,uh,param,nn))) & 
	        kk: dom(select_table_heap(function_application(th,uh,param,nn))(tt)) 
	        => 
	        exists_in_heap (
	            kk,
	            select_table_heap(function_application(th,uh,param,nn)),
	            select_userdata_heap(function_application(th,uh,param,nn)))))  
      
ASSERTIONS
      

    /* all results are compatible with the new table heap */
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !vv. (vv: ran(select_results(function_application(th,uh,param,nn))) &
	          vv: TABLE_LUA_VALUES 
	          =>      
	          lua_table_from_lua_value(vv) :  
	          dom(select_table_heap(function_application(th,uh,param,nn)))) ) &
	  
    /* all results are compatible with the new userdata heap */
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !vv. (vv : ran(select_results(function_application(th,uh,param,nn))) & 
	          vv: USERDATA_LUA_VALUES   
	          =>      
	          lua_userdata_from_lua_value(vv) : 
	          dom(select_userdata_heap(function_application(th,uh,param,nn)))) ) &
	  
	      /* the new userdata and table heaps are compatible */
    
    /* the new userdata heap is consistent with table heap */   
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !udata. (
	        udata: dom(select_userdata_heap(function_application(th,uh,param,nn)))  
	        =>
	        (udtable_of(
	           select_userdata_heap(function_application(th,uh,param,nn))(udata)):
	             TABLE_LUA_VALUES 
                 =>
                 lua_table_from_lua_value(udtable_of(select_userdata_heap(function_application(th,uh,param,nn))(udata))):
dom(select_table_heap(function_application(th,uh,param,nn)))) )) &

	/* table cell contents and keys in the new table heap are consistent with 
	table and userdata heap */  	    	    
    !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !(tt,cc). (tt: dom(select_table_heap(function_application(th,uh,param,nn))) & 
	        cc: ran(select_table_heap(function_application(th,uh,param,nn))(tt)) 
	        => 
              (cc: USERDATA_LUA_VALUES 
                  => lua_userdata_from_lua_value(cc):
                  dom(select_userdata_heap(function_application(th,uh,param,nn)))) &
              (cc: TABLE_LUA_VALUES 
                  => lua_table_from_lua_value(cc): 
                  dom(select_table_heap(function_application(th,uh,param,nn)))))) &
      
          !(th,uh,param,nn).(
	    th : LUA_TABLE +-> TABLE_CONTENTS &
	    uh : LUA_USERDATA +-> USERDATA_CONTENTS &
	    param : seq(LUA_VALUES) &
	    nn : NAT 
	    => 
	    !(tt,kk). (tt: dom(select_table_heap(function_application(th,uh,param,nn))) & 
	        kk: dom(select_table_heap(function_application(th,uh,param,nn))(tt)) 
	        => 
              (kk: USERDATA_LUA_VALUES 
                  => lua_userdata_from_lua_value(kk):
                  dom(select_userdata_heap(function_application(th,uh,param,nn)))) &
              (kk: TABLE_LUA_VALUES 
                  => lua_table_from_lua_value(kk): 
                  dom(select_table_heap(function_application(th,uh,param,nn)))))) 
 
END

 