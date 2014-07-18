/* TableOperations
 * Author: Anamaria
 * Creation date: Fri Aug 17 2012
 */
MACHINE
    TableOperations
    
SEES 
    BasicTypes,
    StringADT,
    NumberADT,
    TableADT,
    Aux
    
INCLUDES 
    Memory
        
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == stack_indexes \/ pseudo_indexes;
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top;
    free_table_heap_space == LUA_TABLE - dom(table_heap)
    
OPERATIONS
    
/* 
lua_createtable [-0, +1, e] 
void lua_createtable (lua_State *L, int narr, int nrec); 
Creates a new empty table and pushes it onto the stack. Parameter narr is a hint for how many elements the table will have as a sequence; parameter nrec is a hint for how many other elements the table will have. Lua may use these hints to preallocate memory for the new table. This pre-allocation is useful for performance when you know in advance how many elements the table will have. Otherwise you can use the function lua_newtable. 
*/
/* !!! allocation parameters are being ignored */
lua_createtable (narr, nrec) =
PRE
    narr : INT &
    nrec : INT &
    stack_top < max_stack_top &          /* there is space on the stack */
    free_table_heap_space /= {}           /* there is space on the heap */
THEN 
    ANY
       ident
    WHERE
       ident : free_table_heap_space
    THEN
      /* generates the empty table on the heap and pushes its reference on the stack */
      mem_push_new_table (ident)
    END    
END;


/*
lua_getfield [-0, +1, e] 
void lua_getfield (lua_State *L, int index, const char *k); 
Pushes onto the stack the value t[k], where t is the value at the given valid index. As in Lua, this function may trigger a metamethod for the "index" event (see §2.4). 
*/
getfield (index, kk) =
PRE
    index : valid_indexes &
    kk : LUA_STRING  &			    /* !!! check */
    stack_top < max_stack_top &        /* there is space on the stack */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES   /* !!! won't need to be a table because of the meta methods, which are not yet modeled */
THEN
    LET
        actual_index
    BE
        actual_index = convert_index(stack_top,index)
    IN
        /* accesses the stack, extracts the table reference, accesses the heap 
        at this reference, getting a table, and pushes the kk field of this table 
        on the stack */
        mem_push (access_table( 
                    table_heap(lua_table_from_lua_value(stack(actual_index))),
                    (lua_value_from_string(kk))))
    END
    
END;

/*
lua_gettable [-1, +1, e] 
void lua_gettable (lua_State *L, int index); 
Pushes onto the stack the value t[k], where t is the value at the given valid index and k is the value at the top of the stack. 
This function pops the key from the stack (putting the resulting value in its place). As in Lua, this function may trigger a metamethod for the "index" event (see §2.4). 
*/
lua_gettable(index) =
PRE 
    index : valid_indexes &
    stack_top >= 1 &
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES  /* !!! won't need to be a table because of the meta methods, which are not yet modeled */ 
THEN   
    /* accesses the stack, extracts the table reference, accesses the heap at this reference, getting a table, and substitutes the stack top by the contents of the field of this table addressed by the previous stack_top (pop and push) */
    mem_update_at_index(stack_top,       
        access_table(
            table_heap(lua_table_from_lua_value(stack(convert_index(stack_top,index)))),
            (stack(stack_top))))  
END;

/* lua_newtable [-0, +1, e]
void lua_newtable (lua_State *L);
Creates a new empty table and pushes it onto the stack. It is equivalent to lua_createtable(L, 0, 0). 
*/
lua_newtable  =
PRE
    stack_top < max_stack_top &          /* there is space on the stack */
    free_table_heap_space /= {}           /* there is space on the heap */
THEN
    ANY
       ident
    WHERE
       ident : free_table_heap_space
    THEN
      /* generates the empty table on the heap and pushes its reference on the stack */
      mem_push_new_table (ident)
    END    
END;

/* lua_rawget [-1, +1, ?]
void lua_rawget (lua_State *L, int index);
==Pushes onto the stack the value t[k], where t is the value at the given valid index and k is the value at the top of the stack. 
This function pops the key from the stack (putting the resulting value in its place).==
Similar to lua_gettable, but does a raw access (i.e., without metamethods).
*/
lua_rawget(index) =
PRE 
    index : valid_indexes &
    stack_top >= 1 &   
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES 
THEN   
    /* accesses the stack, extracts the table reference, accesses the heap at this reference, getting a table, and substitutes the stack top by the contents of the field of this table addressed by the previous stack_top (pop and push) */
    mem_update_at_index(stack_top,       
        access_table(
            table_heap(lua_table_from_lua_value(stack(convert_index(stack_top,index)))),
            (stack(stack_top))))  
END;

/*
lua_rawgeti [-0, +1, ?]
void lua_rawgeti (lua_State *L, int index, int n);
Pushes onto the stack the value t[n], where t is the table at the given valid index. The access is raw; that is, it does not invoke metamethods. 
*/
lua_rawgeti(index, nn) =
PRE 
    index : valid_indexes &
    nn : INT &
    stack_top < max_stack_top &            /* there is space on the stack */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES 
THEN   
    /* accesses the stack, extracts the table reference, accesses the heap at this reference, getting a table, and pushes the nn field of this table on the stack */
    mem_push( 
        access_table(
            table_heap(lua_table_from_lua_value(stack(convert_index(stack_top,index)))),
            (lua_value_from_number(nn)) )) 
END;

/* lua_rawgetp [-0, +1, ?]
void lua_rawgetp (lua_State *L, int index, const void *p);
Pushes onto the stack the value t[k], where t is the table at the given valid index and k is the pointer p represented as a light userdata. The access is raw; that is, it does not invoke metamethods. 
*/
lua_rawgetp(index, pp) =
PRE 
    index : valid_indexes &
    pp : LIGHTUSERDATA_LUA_VALUES  &             /* !!! era para ser ponteiro */
    stack_top < max_stack_top &                 /* there is space on the stack */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES 
THEN   
    /* accesses the stack, extracts the table reference, accesses the heap at this reference, getting a table, and pushes the pp field of this table on the stack */
    mem_push( 
        access_table(
            table_heap(lua_table_from_lua_value(stack(convert_index(stack_top,index)))),
            (pp) ))  
END;

/* lua_rawset [-2, +0, e]
void lua_rawset (lua_State *L, int index);
== Does the equivalent to t[k] = v, where t is the value at the given valid index, v is the value at the top of the stack, and k is the value just below the top.
This function pops both the key and the value from the stack. ==
Similar to lua_settable, but does a raw assignment (i.e., without metamethods).
*/
lua_rawset (index) =
PRE
    index : valid_indexes & 
    stack_top >= 2 &                          /* will pop 2 items */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES 
THEN
    LET actual_index,vv,kk BE
        actual_index = convert_index(stack_top,index) &
        vv = stack(stack_top) &
        kk = stack(stack_top - 1)
    IN
        /* pops vv and kk and updates the kk cell of the table referenced by stack(index) with vv */
        mem_pop_update_table(lua_table_from_lua_value(stack(actual_index)),
                         table_heap(lua_table_from_lua_value(stack(actual_index))) <+ {kk |-> vv}, 2) 
    END
END;

/* lua_rawseti [-1, +0, e]
void lua_rawseti (lua_State *L, int index, int n);
Does the equivalent of t[n] = v, where t is the table at the given valid index and v is the value at the top of the stack.
This function pops the value from the stack. The assignment is raw; that is, it does not invoke metamethods. 
*/
lua_rawseti(index, nn) =
PRE 
    index : valid_indexes &
    nn : INT  &
    stack_top >= 1 & 			  /* there is at least one item to pop */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES   /* !!! acrescentei essa condição */
THEN  
    LET
        actual_index
    BE
        actual_index = convert_index(stack_top,index)
    IN
        /* accesses the stack, extracts the table reference, accesses the heap at
        this reference, getting a table, and updates this table with the pair (nn,
        poped value from the top of the stack) */
    	  mem_pop_update_table (lua_table_from_lua_value(stack(actual_index)),
        (table_heap(lua_table_from_lua_value(stack(actual_index))) <+ 
            {lua_value_from_number(nn)|-> stack(stack_top)} ), 1)
    END
END;

/* lua_rawsetp [-1, +0, e]
void lua_rawsetp (lua_State *L, int index, const void *p);
Does the equivalent of t[k] = v, where t is the table at the given valid index, k is the pointer p represented as a light userdata, and v is the value at the top of the stack.
This function pops the value from the stack. The assignment is raw; that is, it does not invoke metamethods. */
lua_rawsetp (index, pp) =
PRE
    index : valid_indexes &
    pp : LIGHTUSERDATA_LUA_VALUES  &             /* !!! era para ser ponteiro */
    stack_top >= 1 & 			  /* there is at least one item to pop */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES   /* !!! acrescentei essa condição */
THEN
    LET 
        ident
    BE
        /* accesses the stack, extracts the table reference */
        ident = lua_table_from_lua_value(stack(convert_index(stack_top,index)))
    IN
        /*accesses the heap at this reference, getting a table, and updates this
        table with the pair (pp, poped value at the top of the stack) */
        mem_pop_update_table(ident,
            table_heap(ident)  <+ {pp |-> stack(stack_top)},
            1)
    END
END;
  
/*
lua_setfield [-1, +0, e]
void lua_setfield (lua_State *L, int index, const char *k);
Does the equivalent to t[k] = v, where t is the value at the given valid index and v is the value at the top of the stack.
This function pops the value from the stack. As in Lua, this function may trigger a metamethod for the "newindex" event (see §2.4). 
*/
lua_setfield (index, kk) =
PRE
    index : valid_indexes &          
    stack_top >= 1 &                    /* stack is not empty */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES  & /* !!! won't need to be a table because of the meta methods, which are not yet modeled */ 
    kk : LUA_STRING
THEN
    LET actual_index,vv BE
        actual_index = convert_index(stack_top,index) &
        vv = stack(stack_top) 
    IN
        mem_pop_update_table(lua_table_from_lua_value(stack(actual_index)),
                         table_heap(lua_table_from_lua_value(stack(actual_index))) <+ {lua_value_from_string(kk) |-> vv}, 1)         
    END
END;


/* lua_setmetatable [-1, +0, ?]
void lua_setmetatable (lua_State *L, int index);
Pops a table from the stack and sets it as the new metatable for the value at the given acceptable index.
*/

/* !!! o que significa "the new metatable for the value"? */

lua_setmetatable (index) =
PRE
    index : acceptable_indexes &
    stack_top >= 1 &                         /* the stack is not empty */ 
    stack(stack_top) : TABLE_LUA_VALUES
THEN
    LET tt BE
        tt = stack(stack_top) 
    IN
        pop (1)
    END
END;


/* lua_settable [-2, +0, e]
void lua_settable (lua_State *L, int index);
Does the equivalent to t[k] = v, where t is the value at the given valid index, v is the value at the top of the stack, and k is the value just below the top.
This function pops both the key and the value from the stack. As in Lua, this function may trigger a metamethod for the "newindex" event (see §2.4). 
*/
lua_settable (index) =
PRE
    index : valid_indexes & 
    stack_top >= 2 &                           /* will pop 2 items */
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES /* !!! won't need to be a table because of the meta methods, which are not yet modeled */ 
THEN
    LET vv,kk,ident BE
        vv = stack(stack_top) &
        kk = stack(stack_top - 1) &
        ident = lua_table_from_lua_value(stack(convert_index(stack_top,index)))
    IN
        /* pops vv and kk and updates the kk cell of the table referenced by
        stack(index) with vv */
        mem_pop_update_table(ident, table_heap(ident)<+{kk|->vv}, 2)
    END
    
END

END
