/* PseudoStackOperations
 * Author: Anamaria
 * Creation date: Wed Aug 15 2012
 */
MACHINE
    PseudoStackOperations
    
SEES 
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
    Aux,
    ADTConvertions
    
INCLUDES 
    Memory
    
PROMOTES
    lua_arith
        
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == stack_indexes \/ pseudo_indexes;
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top
        
OPERATIONS


    /*  lua_checkstack [-0, +0, ?] 
int lua_checkstack (lua_State *L, int extra); Ensures that there are at least extra free stack slots in the stack. It returns false if it cannot fulfill the request, because it would cause the stack to be larger than a fixed maximum size (typically at least a few thousand elements) or because it cannot allocate memory for the new stack size. This function never shrinks the stack; if the stack is already larger than the new size, it is left unchanged. 
*/
ret <-- lua_checkstack (extra) =
PRE
    extra : INT & stack_top + extra <= MAXINT
THEN
    IF 
        stack_top + extra <= max_stack_top
    THEN 
        ret := true
    ELSE
        IF stack_top + extra < pseudo_bottom
        THEN
            CHOICE ret := true || increase_max_stack_top (stack_top + extra)
        	OR ret := false               /* memory allocation problems */
        	END  
        ELSE  
            ret:= false                   /* no space */
        END
    END
END;

/* 
lua_copy [-0, +0, ?] 
void lua_copy (lua_State *L, int fromidx, int toidx); 
Moves the element at the valid index fromidx into the valid index toidx without shifting any element (therefore replacing the value at that position). 
*/
lua_copy (fromidx, toidx) = 
PRE
    fromidx : valid_indexes &
    toidx : valid_indexes
THEN
    mem_update_at_index(toidx, stack(fromidx))
END;


/* lua_getuservalue [-0, +1, ?]
void lua_getuservalue (lua_State *L, int index);
Pushes onto the stack the Lua value associated with the userdata at the given index. This Lua value must be a table or nil.
*/
lua_getuservalue (index) = 
PRE
    index : INT &
    index : acceptable_indexes &
    stack(convert_index(stack_top,index)) : USERDATA_LUA_VALUES &
    stack_top < max_stack_top
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN        
        mem_push(udtable_of(userdata_heap(lua_userdata_from_lua_value(stack(actual_index)))))    	
    END     
END;
    
/*
lua_insert [-1, +1, ?]
void lua_insert (lua_State *L, int index);
Moves the top element into the given valid index, shifting up the elements above this index to open space. Cannot be called with a pseudo-index, because a pseudo-index is not an actual stack position. 
*/
lua_insert (index) =
PRE
    stack_top >= 1 & 
    index : stack_indexes  
THEN
    insert(index) 
END;

    
    /*    
lua_len  [-0, +1, e] 
void lua_len (lua_State *L, int index); 
Returns the "length" of the value at the given acceptable index; it is equivalent to the '#' operator in Lua (see  3.4.6). The result is pushed on the stack.
*/
/*!!! so e definido para string e tabela */
/* !!! qual deveria ser o comportamento em caso de not_a_lua_value ?*/
lua_len (index) = 
PRE
    index : acceptable_indexes &
    stack_top < max_stack_top &
    stack(convert_index(stack_top,index)) : TABLE_LUA_VALUES \/ STRING_LUA_VALUES
THEN
    LET
        lenght
    BE 
        lenght = len(stack(convert_index(stack_top,index)),table_heap)
    IN
        mem_push(lua_value_from_number(lenght))
    END
END;

    
    /* lua_newthread [-0, +1, e]
lua_State *lua_newthread (lua_State *L);
Creates a new thread, pushes it on the stack, and returns a pointer to a lua_State that represents this new thread. The new thread returned by this function shares with the original thread its global environment, but has an independent execution stack.
There is no explicit function to close or to destroy a thread. Threads are subject to garbage collection, like any Lua object. 
*/
/* !!! apenas empilha um valor thread generico e retorna um inteiro qualquer */ 
ret <-- lua_newthread =
PRE
    stack_top < max_stack_top
THEN
    ANY 
        tt
    WHERE
        tt: THREAD_LUA_VALUES
    THEN
        mem_push (tt)
    END ||
    ret :: INT
END;

/* lua_newuserdata [-0, +1, e]
void *lua_newuserdata (lua_State *L, size_t size);
This function allocates a new block of memory with the given size, pushes onto the stack a new full userdata with the block address, and returns this address. The host program can freely use this memory. 
*/
/* !!! a alocacao nao e modelada - o endereco do userdata e qualquer */
ret <-- lua_newuserdata (datasize) =
PRE
    datasize : NAT &
    stack_top < max_stack_top &
    LUA_USERDATA - dom(userdata_heap) /= {}
THEN
    ANY 
       dd, addr
    WHERE
       dd: LUA_USERDATA - dom(userdata_heap) &
       addr : INT
    THEN 
       mem_push_new_ud (dd,datasize,addr) ||
       ret := addr
    END
END;

 /* lua_next [-1, +(2|0), e]
int lua_next (lua_State *L, int index);
Pops a key from the stack, and pushes a key?value pair from the table at the given index (the "next" pair after the given key). If there are no more elements in the table, then lua_next returns 0 (and pushes nothing).
*/

ret <-- lua_next (index) = 
PRE
    index : INT &
    index : valid_indexes &               
    stack_top > 0 &                             /* stack is not empty */
    stack_top < max_stack_top   &    /* there is place on the stack */
    stack(index) : TABLE_LUA_VALUES
THEN
    LET key,actual_index 
    BE key = stack(stack_top) & actual_index = convert_index(stack_top,index)
    IN
        ANY v1, v2, success
        WHERE 
            v1 : LUA_VALUES &
            v1: dom(table_heap(lua_table_from_lua_value(stack(actual_index)))) & /*!!! seria o next */
            v2 : LUA_VALUES &
            v2 :  ran(table_heap(lua_table_from_lua_value(stack(actual_index)))) &/*!!! seria o conteudo em next */
            success : BOOL  /* !!! diz se havia um next */
        THEN 
            mem_next (v1, v2, success) ||
            IF success = TRUE THEN ret := 1 ELSE ret := 0 END
        END
    END  
END;
   

/* lua_pop [-n, +0, ?]
void lua_pop (lua_State *L, int n); Pops n elements from the stack.
*/
/* !!! assumi n>0 */
lua_pop (nn) = 
PRE
    nn: NAT1 &
    nn <= stack_top
THEN
    pop(nn)
END;

/* lua_pushboolean [-0, +1, ?]
void lua_pushboolean (lua_State *L, int b);
Pushes a boolean value with value b onto the stack. 
*/

lua_pushboolean (bb) = 
PRE
    bb : LUA_BOOLEAN &
    stack_top < max_stack_top
THEN
    mem_push(lua_value_from_boolean(bb))
END;

/*  lua_pushcclosure [-n, +1, e] 
void lua_pushcclosure (lua_State *L, lua_CFunction fn, int n); 
Pushes a new C closure onto the stack. When a C function is created, it is possible to associate some values with it, thus creating a C closure 
(see  4.4); these values are then accessible to the function whenever it is called. To associate values with a C function, ?rst these values should be pushed onto the stack (when there are multiple values, the ?rst value is pushed ?rst). Then lua_pushcclosure is called to create and push the C function 
onto the stack, with the argument n telling how many values should be associated with the function. lua_pushcclosure also pops these values from the stack. The maximum value for n is 255. 
When n is zero, this function creates a light C function, which is just a pointer to the C function. In that case, it never throws a memory error. 
*/
/* !!! a fazer */
lua_pushcclosure (fn, nn) =
PRE
    fn : LUA_FUNCTION &
    nn : 0..255
THEN
    skip
END;
   
    
/*
lua_pushcfunction [-0, +1, ?]
void lua_pushcfunction (lua_State *L, lua_CFunction f); 
Pushes a C function onto the stack. This function receives a pointer to a C function and pushes onto the stack a Lua value of type function that, when called, invokes the corresponding C function. 
Any function to be registered in Lua must follow the correct protocol to receive its parameters and return its results (see lua_CFunction). 
lua_pushcfunction is de?ned as a macro: 
     #define lua_pushcfunction(L,f)  lua_pushcclosure(L,f,0) 
Note that f is used twice. 
*/
/* !!! a fazer */
lua_pushcfunction (fn) =
PRE
    fn : LUA_FUNCTION
THEN
    skip
END;


/*
lua_pushinteger [-0, +1, ?] 
void lua_pushinteger (lua_State *L, lua_Integer n); 
Pushes a number with value n onto the stack. 
*/
lua_pushinteger (nn) = 
PRE
    nn : LUA_NUMBER &
    stack_top < max_stack_top
THEN
    mem_push(lua_value_from_number(nn))
END;

/*
lua_pushlightuserdata [-0, +1, ?] 
void lua_pushlightuserdata (lua_State *L, void *p); 
Pushes a light userdata onto the stack. 
Userdata represent C values in Lua. A light userdata represents a pointer, a void*. It is a value (like a number): you do not create it, it has no individual metatable, and it is not collected (as it was never created). A light userdata is equal to "any" light userdata with the same C address.
*/
lua_pushlightuserdata (dd) = 
PRE
    dd : LUA_LIGHTUSERDATA &
    stack_top < max_stack_top
THEN
    mem_push(lua_value_from_lightuserdata(dd))
END;


/*
lua_pushliteral [-0, +1, e] 
const char *lua_pushliteral (lua_State *L, const char *s); 
This macro is equivalent to lua_pushlstring, but can be used only when s is a literal string. It automatically provides the string length. 
*/

/*
lua_pushlstring [-0, +1, e] 
const char *lua_pushlstring (lua_State *L, const char *s, size_t len); 
Pushes the string pointed to by s with size len onto the stack. Lua makes (or reuses) an internal copy 
of the given string, so the memory at s can be freed or reused immediately after the function returns. 
The string can contain any binary data, including embedded zeros. 
Returns a pointer to the internal copy of the string. 
*/

/*
lua_pushnil [-0, +1, ?] 
void lua_pushnil (lua_State *L); 
Pushes a nil value onto the stack. 
*/
lua_pushnil =
PRE
     stack_top < max_stack_top
THEN
    mem_push(nil_lua_value)
END;
   
   
/*   
lua_pushnumber [-0, +1, ?] 
void lua_pushnumber (lua_State *L, lua_Number n); 
Pushes a number with value n onto the stack. 
*/
lua_pushnumber (nn) =
PRE
    nn : LUA_NUMBER &
    stack_top < max_stack_top
THEN
    mem_push(lua_value_from_number(nn))
END;

/*
lua_pushstring [-0, +1, e]
const char *lua_pushstring (lua_State *L, const char *s); 
Pushes the zero-terminated string pointed to by s onto the stack. Lua makes (or reuses) an internal copy of the given string, so the memory at s can be freed or reused immediately after the function returns. 
Returns a pointer to the internal copy of the string. 
If s is NULL, pushes nil and returns NULL. 
*/
/* !!! como tratar a passagem por referencia??? Varios detalhes relacionados ao uso da string ou do ponteiro (inclusive ser NULL) nao estao modelados aqui. */

ret <-- lua_pushstring (ss) =
PRE
    ss : LUA_STRING &
    stack_top < max_stack_top
THEN
    mem_push(lua_value_from_string(ss))||
    ret := stack_top +1
END;

/*
lua_pushthread [-0, +1, ?] 
int lua_pushthread (lua_State *L); 
Pushes the thread represented by L onto the stack. Returns 1 if this thread is the main thread of its state. 
*/
ret <-- lua_pushthread (ll) =
PRE
    ll : LUA_THREAD &
    stack_top < max_stack_top
THEN
    mem_push(lua_value_from_thread(ll))||
    ret :: {0,1}
END;
    
    /*
lua_pushvalue [-0, +1, ?]
void lua_pushvalue (lua_State *L, int index); 
Pushes a copy of the element at the given valid index onto the stack. 
*/
lua_pushvalue (index) =
PRE
    index : valid_indexes &
    stack_top < max_stack_top
THEN
    mem_push(stack(index))
END;

    
/* lua_remove [-1, +0, ?]
void lua_remove (lua_State *L, int index);
Removes the element at the given valid index, shifting down the elements above this index to fill the gap. Cannot be called with a pseudo-index, because a pseudo-index is not an actual stack position. 
*/
lua_remove (index) =
PRE
    stack_top >= 1 & 
    index : stack_indexes /* this implies that stack_top is at least 1 */ 
THEN
   remove(index)
END;

/* lua_replace [-1, +0, ?]
void lua_replace (lua_State *L, int index);
Moves the top element into the given position without shifting any element (therefore replacing the value at the given position), and then pops the top element. 
*/
lua_replace (index) =
PRE
    stack_top >= 1 & 
    index : stack_indexes 
THEN
   mem_update_and_pop(index,stack(stack_top))
END;


    
    /* lua_setglobal [-1, +0, e]
void lua_setglobal (lua_State *L, const char *name);
Pops a value from the stack and sets it as the new value of global name. 
*/
lua_setglobal (name) =
PRE
    stack_top >= 1 &
    name : INT             /* !!! ainda nao modelado */
THEN
    LET vv BE
        vv = stack(stack_top) IN
        pop(1)
    END
END;


    
/* lua_settable [-2, +0, e]
void lua_settable (lua_State *L, int index);
Does the equivalent to t[k] = v, where t is the value at the given valid index, v is the value at the top of the stack, and k is the value just below the top.
This function pops both the key and the value from the stack. As in Lua, this function may trigger a metamethod for the "newindex" event (see  2.4). 
*/
lua_settable (index) =
PRE
    index : valid_indexes & 
    stack_top >= 2 &
    stack(index) : TABLE_LUA_VALUES  /* !!! acrescentei essa condicao */
THEN
    LET vv,kk,ident BE
        vv = stack(stack_top) &
        kk = stack(stack_top - 1) &
        ident = lua_table_from_lua_value(stack(index))
    IN
        mem_pop_update_table(ident, table_heap(ident)<+{kk|->vv}, 2)
    END
END;

    /* lua_settop [-?, +?, ?]
void lua_settop (lua_State *L, int index);
Accepts any acceptable index, or 0, and sets the stack top to this index. If the new top is larger than the old one, then the new elements are filled with nil. If index is 0, then all stack elements are removed.
*/
lua_settop (index) = 
PRE
    index : INT &
    (index : acceptable_indexes or index = 0) &
    index < max_stack_top
THEN
    settop(index)
END;


    
    /*
lua_setuservalue [-1, +0, ?]
void lua_setuservalue (lua_State *L, int index);
Pops a table or nil from the stack and sets it as the new value associated to the userdata at the given index. 
*/
lua_setuservalue (index) =
PRE
    index : acceptable_indexes &
    stack(convert_index(stack_top,index)) : USERDATA_LUA_VALUES &
    stack_top > 0 &
    stack(stack_top) : TABLE_LUA_VALUES \/ NIL_LUA_VALUES
THEN
    mem_pop_update_ud (
        lua_userdata_from_lua_value(stack(convert_index(stack_top,index))), 
        stack(stack_top),
        1) 
END;

    	    




/* lua_status [-0, +0, ?]
int lua_status (lua_State *L);
Returns the status of the thread L.
The status can be 0 (LUA_OK) for a normal thread, an error code if the thread finished the execution of a lua_resume with an error, or LUA_YIELD if the thread is suspended.
You can only call functions in threads with status LUA_OK. You can resume threads with status LUA_OK (to start a new coroutine) or LUA_YIELD (to resume a coroutine). 
*/
ret <-- lua_status =
BEGIN
    ret :: NAT
END;


    /*
lua_tolstring [-0, +0, e]
const char *lua_tolstring (lua_State *L, int index, size_t *len);
Converts the Lua value at the given acceptable index to a C string. If len is not NULL, it also sets *len with the string length. The Lua value must be a string or a number; otherwise, the function returns NULL. If the value is a number, then lua_tolstring also changes the actual value in the stack to a string. (This change confuses lua_next when lua_tolstring is applied to keys during a table traversal.)

lua_tolstring returns a fully aligned pointer to a string inside the Lua state. This string always has a zero ('\0') after its last character (as in C), but can contain other zeros in its body. Because Lua has garbage collection, there is no guarantee that the pointer returned by lua_tolstring will be valid after the corresponding value is removed from the stack.
*/
ret <-- lua_tolstring (index, slen) =
PRE
    index : acceptable_indexes &
    slen : INT 
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
       IF actual_index : valid_indexes or 
          actual_index : pseudo_indexes & actual_index : dom(stack)
       THEN
           IF
             stack(actual_index) : STRING_LUA_VALUES
           THEN
             ret := stack(actual_index)
           ELSIF stack(actual_index) : NUMBER_LUA_VALUES 
           THEN
               LET ss BE
                   ss = string_from_number_lua_values(stack(actual_index))
               IN
        	     ret := ss ||
        	     mem_update_at_index(actual_index, ss)
             END        
           ELSE
               ret :: STRING_LUA_VALUES /*!!! deveria ser NULL */
           END
       ELSE                 /* undefined value */
           ret :: STRING_LUA_VALUES /*!!! deveria ser NULL */
       END   
   END
END;

    
    /*
lua_tostring [-0, +0, e]
const char *lua_tostring (lua_State *L, int index);
Equivalent to lua_tolstring with len equal to NULL.
*/

ret <-- lua_tostring (index) =
PRE
    index : acceptable_indexes
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
       IF actual_index : valid_indexes or 
          actual_index : pseudo_indexes & actual_index : dom(stack)
       THEN
           IF
             stack(actual_index) : STRING_LUA_VALUES
           THEN
             ret := stack(actual_index)
           ELSIF stack(actual_index) : NUMBER_LUA_VALUES 
           THEN
               LET ss BE
                   ss = string_from_number_lua_values(stack(actual_index))
               IN
        	     ret := ss ||
        	     mem_update_at_index(actual_index, ss)
               END        
           ELSE
               ret :: STRING_LUA_VALUES /*!!! deveria ser NULL */
           END
       ELSE                 /* undefined value */
           ret :: STRING_LUA_VALUES /*!!! deveria ser NULL */
       END   
   END  
END

END


