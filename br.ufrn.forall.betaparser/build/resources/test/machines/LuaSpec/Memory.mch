/* Memory
 * Author: Anamaria
 * Creation date: Fri Aug 17 2012
 */
MACHINE
    Memory
    
SEES 
    /* all type information */
    BasicTypes,
    NumberADT,
    BooleanADT,
    NilADT,
    StringADT,
    FunctionADT,
    UserdataADT,
    LightuserdataADT,
    TableADT,
    ThreadADT,
    ADTConvertions,
    /* pseudo-stack indexes manipulation and other auxiliary functions */
    Aux    
    
INCLUDES    
    Pseudo_Stack,
    TableHeap,
    UserdataHeap
    
PROMOTES
    pop,
    remove,
    insert,
    increase_max_stack_top,
    lua_arith,
    settop
        
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == stack_indexes \/ pseudo_indexes;
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top;
    free_table_heap_space == LUA_TABLE - dom(table_heap);
    free_userdata_heap_space == LUA_USERDATA - dom(userdata_heap);
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
         
INVARIANT    
    /* stack is consistent with table and userdata heap */
    !index. ( index: (1..stack_top \/ pseudo_indexes) =>         
        exists_in_heap(stack(index), table_heap, userdata_heap)) &
       
    /* userdata is consistent with table heap */   
    !udata. ( udata: dom(userdata_heap) =>
        exists_in_table_heap(udtable_of(userdata_heap(udata)), table_heap)) & 					
    /* table cell contents and keys are consistent with table and userdata heap */                					
    !(tt,cc). (tt: dom(table_heap) & cc: ran(table_heap(tt)) => 
        exists_in_heap(cc, table_heap, userdata_heap)) &
       
    !(tt,kk). ( tt: dom(table_heap) & kk: dom(table_heap(tt))  =>        
        exists_in_heap(kk, table_heap, userdata_heap)) 
 
       
   ASSERTIONS
    /* stack is consistent with table heap */
    !index. (
        index: 1..stack_top & 
        stack(index): TABLE_LUA_VALUES 
        =>      
        lua_table_from_lua_value(stack(index)) : dom(table_heap)) &
    !index. (
        index: pseudo_indexes & 
        stack(index): TABLE_LUA_VALUES 
        =>      
        lua_table_from_lua_value(stack(index)) : dom(table_heap)) &
    
    /* stack is consistent with userdata heap */
    !index. (
        index: 1..stack_top & 
        stack(index): USERDATA_LUA_VALUES 
        =>      
        lua_userdata_from_lua_value(stack(index)) : dom(userdata_heap)) &
    !index. (
        index: pseudo_indexes & 
        stack(index): USERDATA_LUA_VALUES 
        =>      
        lua_userdata_from_lua_value(stack(index)) : dom(userdata_heap))  &


    /* userdata is consistent with table heap */   
    !udata. (
        udata: dom(userdata_heap) & 
        udtable_of(userdata_heap(udata)) : TABLE_LUA_VALUES 
        =>
        lua_table_from_lua_value(udtable_of(userdata_heap(udata))):
        									dom(table_heap)) &
    /* tables are consistent with userdata heap */    					
    !(t1,ud). (
        t1: dom(table_heap) & 
        ud: ran(table_heap(t1)) & ud: USERDATA_LUA_VALUES =>
        			lua_userdata_from_lua_value(ud): dom(userdata_heap)) &
    !(t1,ud). (
        t1: dom(table_heap) & 
        ud: dom(table_heap(t1)) & ud: USERDATA_LUA_VALUES =>
        			lua_userdata_from_lua_value(ud): dom(userdata_heap)) &
       
    /* stack is consistent with table and userdata heap */
    !index. ( index: (1..stack_top \/ pseudo_indexes) =>         
        (stack(index): TABLE_LUA_VALUES 
             => lua_table_from_lua_value(stack(index)) : dom(table_heap)) &
        (stack(index): USERDATA_LUA_VALUES 
             => lua_userdata_from_lua_value(stack(index)) : dom(userdata_heap))) &
       
       
    /* userdata is consistent with table heap */   
    !udata. ( udata: dom(userdata_heap) =>
        (udtable_of(userdata_heap(udata)) : TABLE_LUA_VALUES 
        =>
        lua_table_from_lua_value(udtable_of(userdata_heap(udata))):
        									dom(table_heap))) &
        					
    /* table cell contents and keys are consistent with table and userdata heap */  	      
    !(tt,cc). (tt: dom(table_heap) & cc: ran(table_heap(tt)) => 
        (cc: USERDATA_LUA_VALUES 
             => lua_userdata_from_lua_value(cc): dom(userdata_heap)) &
        (cc: TABLE_LUA_VALUES 
             => lua_table_from_lua_value(cc): dom(table_heap))) &
       
    !(tt,kk). ( tt: dom(table_heap) & kk: dom(table_heap(tt))  =>        
        (kk: USERDATA_LUA_VALUES 
            => lua_userdata_from_lua_value(kk): dom(userdata_heap)) &
        (kk: TABLE_LUA_VALUES 
             => lua_table_from_lua_value(kk): dom(table_heap)))   
                 
OPERATIONS
    
    /* creates an empty table on the heap and pushes its reference on the stack */
    mem_push_new_table (ident) =
    PRE
        ident : free_table_heap_space &
        stack_top < max_stack_top
    THEN
      /* generates the empty table on the heap */ 
      new_table(ident,{}) ||
      /* pushes its reference on the stack */
      push(lua_value_from_table(ident))
    END;  
    
    /* creates an userdata on the heap and pushes its reference on the stack */
    mem_push_new_ud (ident,datasize,addr) =
    PRE
        ident : free_userdata_heap_space &
        stack_top < max_stack_top &
        datasize : NAT &
        addr : INT
    THEN
        /* generates the userdata on the heap */ 
        new_userdata(ident, datasize, addr) ||
        /* pushes its reference on the stack */
        push(lua_value_from_userdata(ident))  
    END;    
    
    /* updates the table refered to by ident with the given contents and pops */
    mem_pop_update_table(ident,ff,nn) = 
    PRE 
        ident : dom(table_heap) &
        ff : TABLE_CONTENTS &    
        !tt.(tt : ran(ff) =>  exists_in_heap(tt, table_heap, userdata_heap)) &
        !tt.(tt : dom(ff) =>  exists_in_heap(tt, table_heap, userdata_heap)) &
        nn: NAT1 &
    	  stack_top >= nn 
    THEN   
    	/* updates the given table and pops the stack */
    	update_table(ident,ff)||
    	pop(nn)
    END;
    
     
    /* updates the userdata refered to by ident with a new table or nil and pops */
    mem_pop_update_ud (ident, t_or_nil,nn) =
    PRE
        ident : dom(userdata_heap) &
        t_or_nil : TABLE_LUA_VALUES \/ NIL_LUA_VALUES &
        exists_in_table_heap (t_or_nil, table_heap) &
        nn: NAT1 &
        stack_top >= nn
    THEN
        update_userdata(ident, t_or_nil)||
        pop(nn)
    END;
    
    /* updates stack and heaps on a function call */
    mem_call(npops, pushvalues, newth, newuh) =
    PRE
        npops : NAT &
        pushvalues : seq(LUA_VALUES) &
        newth : LUA_TABLE +-> TABLE_CONTENTS &
        newuh : LUA_USERDATA +-> USERDATA_CONTENTS &
        stack_top >= npops &
        stack_top - npops + size(pushvalues) <= max_stack_top &
        !vv. (vv : ran(pushvalues) => exists_in_heap(vv, newth, newuh)) &
        /* new userdata is consistent with new table heap */   
        !udata. (udata: dom(newuh) =>
            exists_in_table_heap (udtable_of(newuh(udata)),newth)) &
        /* new table cell contents and keys are consistent with new table and userdata 
        heaps */  	    
        !(tt,cc). (tt: dom(newth) & cc: ran(newth(tt)) => 
            exists_in_heap(cc,newth,newuh)) &
        !(tt,kk). (tt: dom(newth) & kk: dom(newth(tt)) =>        
            exists_in_heap(kk,newth,newuh))
    THEN
        pop_and_push(npops, pushvalues) ||
        update_table_heap (newth) ||
        update_userdata_heap (newuh)
    END;

 
 /* ----
 The folowing operations are "PROMOTED" from Pseudo-Stack, with the addition of a consistency pre-condition
 --- */
 
    /* pushes a LUA_VALUE on top of the stack */
    mem_push (vv) =
    PRE
        vv : LUA_VALUES &
        stack_top < max_stack_top &
        exists_in_heap(vv, table_heap, userdata_heap) 
    THEN
        push(vv)
    END;
    
    /* updates the value at a given stack position */
    mem_update_at_index (index,vv) =
    PRE
        index : INT &
        index : valid_indexes & 
        vv : LUA_VALUES &
        exists_in_heap(vv, table_heap, userdata_heap) 
    THEN
        update_at_index (index, vv)
    END;
    
    /* updates the value at a given stack position and pops the top of the stack */
    mem_update_and_pop (index,vv) =
    PRE
        stack_top >= 1 &
        index : INT &
        index : valid_indexes &
        vv : LUA_VALUES &
        exists_in_heap(vv, table_heap, userdata_heap) 
    THEN 
        update_and_pop(index, vv)
    END; 
   
    /* pops npops values and pushes the given new values */
    /* mem_pop_and_push(npops, pushvalues) =
    PRE
        npops : NAT &
        pushvalues : seq(LUA_VALUES) &
        stack_top >= npops &
        stack_top - npops + size(pushvalues) <= max_stack_top &
        !vv. (vv : ran(pushvalues) & vv: TABLE_LUA_VALUES =>      
            lua_table_from_lua_value(vv) : dom(table_heap))  &
        !vv. (vv : ran(pushvalues) & vv: USERDATA_LUA_VALUES =>      
            lua_userdata_from_lua_value(vv) : dom(userdata_heap))          
    THEN
        pop_and_push(npops, pushvalues)
    END;*/
    
    
    /* updates top and pushes an extra value or just pops */
    mem_next (key, vv, flag) = 
    PRE
    	stack_top > 0 &                             /* stack is not empty */
    	stack_top < max_stack_top &      /* there is place on the stack */
    	key : LUA_VALUES &
    	exists_in_table_heap(key, table_heap) &
    	vv : LUA_VALUES &
    	exists_in_table_heap(vv, table_heap) &
    	flag : BOOL
    THEN
      next(key, vv, flag)   	
  END
  
  
  /* ----
 The folowing operations are "PROMOTED" from Userdata_heap, with the addition of a consistency pre-condition
 --- */
 


END
