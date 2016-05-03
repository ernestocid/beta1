/* PseudoStackOperations
 * Author: 
 * Creation date: 28/04/14
 */
MACHINE
    PseudoStackOperations
SEES 
    BasicTypes,
    NumberADT,
    BooleanADT,
    NilADT,
    Aux
    
INCLUDES 
    Pseudo_Stack
        
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == stack_indexes \/ pseudo_indexes;
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top;
    SET_PREF_MININT == -10;
    SET_PREF_MAXINT == 30

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
    update_at_index(toidx, stack(fromidx))
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
    push(lua_value_from_boolean(bb))
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
    push(lua_value_from_number(nn))
END;

/*
lua_pushnil [-0, +1, ?] 
void lua_pushnil (lua_State *L); 
Pushes a nil value onto the stack. 
*/
lua_pushnil =
PRE
     stack_top < max_stack_top
THEN
    push(nil_lua_value)
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
    push(lua_value_from_number(nn))
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
    push(stack(index))
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
   update_and_pop(index,stack(stack_top))
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
   
   
   /* this place is weird, considering that all lua operations are usually located higher on the hierarchy */
      /* lua_arith [-(2|1), +1, e]
void lua_arith (lua_State *L, int op); Performs an arithmetic operation over the two values (or one, in the case of negation) at the top of the stack, with the value at the top being the second operand, pops these values, and pushes the result of the operation. The function follows the semantics of the corresponding Lua operator (that is, it may call metamethods).

The value of op must be one of the following constants:

    LUA_OPADD: performs addition (+)
    LUA_OPSUB: performs subtraction (-)
    LUA_OPMUL: performs multiplication (*)
    LUA_OPDIV: performs division (/)
    LUA_OPMOD: performs modulo (%)
    LUA_OPPOW: performs exponentiation (^)
    LUA_OPUNM: performs mathematical negation (unary -)
    */
 /* !!! podem ser outros valores nao numericos */   
    
    lua_arith(op1) =
    PRE
        op1 : LUA_OP_KEYS &
        
        stack_top >= 1 &
        (stack(stack_top)): NUMBER_LUA_VALUES &
        
        (op1 /= LUA_OPUNM => (stack_top >= 2 & (stack(stack_top-1)): NUMBER_LUA_VALUES))
    THEN 
        IF 
            op1 = LUA_OPUNM
        THEN
            skip
        ELSE
            CASE op1 OF
            EITHER LUA_OPADD THEN skip
            	OR LUA_OPSUB THEN skip
            	OR LUA_OPMUL THEN skip	
            	OR LUA_OPDIV THEN skip
            	OR LUA_OPMOD THEN skip
            	OR LUA_OPPOW THEN skip	
            	END
            END
       
        END
   END

END
