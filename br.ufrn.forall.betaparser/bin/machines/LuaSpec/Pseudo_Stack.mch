/* Pseudo_Stack
 * Author: Anamaria
 * Creation date: Mon Jul 30 2012
 */
MACHINE
    Pseudo_Stack
    
SEES 
    /* all type information */
    BasicTypes,
    NumberADT,
    NilADT,
    /* pseudo-stack indexes manipulation */
    Aux
    
CONSTANTS
    LUA_MINSTACK,			  /* initial limit for the size of the stack */
    					  /* (the stack part of stack) */		
    pseudo_bottom              /* bottom limit of the pseudo-indexes */
    
PROPERTIES
    LUA_MINSTACK = 20 &
    pseudo_bottom : NAT1  &
    pseudo_bottom >  LUA_MINSTACK 

VARIABLES
    stack,                      /* a stack from indexes 1 to stack_top, but also containing valid data in its pseudo-indexes */
    stack_top,			  /* top of the stack part of stack */
    max_stack_top              /* safe limit for stack_top */  
    
INVARIANT
    stack : NAT1 --> LUA_VALUES &
    stack_top : NAT &
    max_stack_top: NAT1 &
    max_stack_top < pseudo_bottom &
    stack_top <= max_stack_top

INITIALISATION
    stack := %ii . (ii : NAT1| not_a_lua_value) ||
    stack_top := 0 ||
    max_stack_top := LUA_MINSTACK
    
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == stack_indexes \/ pseudo_indexes;
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top
    
ASSERTIONS
    /* some useful info for the prover */
    !index. (index: NAT1 => convert_index(stack_top, index) = index )  &
    !index. (index: NAT => convert_index(stack_top, index) = index )  &
    !index. (index: -stack_top..-1 => 
    	  convert_index(stack_top, index) : 1..stack_top) &
    !index. (index: -stack_top..-1 => 
        convert_index(stack_top, index) = stack_top + index + 1) &
    !index. (index: pseudo_indexes => convert_index(stack_top, index) = index ) &
    !index. (index: valid_indexes => convert_index(stack_top,index) : NAT1) &

    stack_top <= MAXINT &
    stack_top < MAXINT

OPERATIONS
    
    /* pushes a LUA_VALUE on top of the stack */
    push (vv) =
    PRE
        vv : LUA_VALUES &
        stack_top < max_stack_top
    THEN
        stack := stack <+ {stack_top+1 |-> vv} ||
        stack_top := stack_top + 1
    END;
    
    /* pops nn items from the top of the stack */
    pop (nn) =
    PRE 
        nn : NAT1 &
        stack_top >= nn
    THEN
        stack_top := stack_top - nn ||
        stack := stack <+ %ii. (ii: stack_top-nn+1..stack_top |not_a_lua_value)
    END;
        
    /* updates the value at a given stack position */
    update_at_index (index,vv) =
    PRE
        index : INT &
        index: valid_indexes &
        vv : LUA_VALUES
    THEN
        stack(convert_index(stack_top,index)) := vv
    END;
    
    /* updates the value at a given stack position and pops the top of the stack */
    update_and_pop (index,vv) =
    PRE
        stack_top >= 1 &
        index : INT &
        index : valid_indexes &
        vv : LUA_VALUES
    THEN 
        LET
            actual_index 
        BE
            actual_index = convert_index(stack_top, index)
        IN
            IF 
            	not(actual_index = stack_top)
        	THEN
            	stack := stack <+ {actual_index |-> vv, stack_top |-> not_a_lua_value}||
            	stack_top := stack_top - 1
        	ELSE 
            	stack := stack <+ {stack_top |-> not_a_lua_value}||
            	stack_top := stack_top - 1
            END
        END
    END; 
    
    /* pops npops values and pushes the given new values */
    pop_and_push (npops, pushvalues) =
    PRE
        npops : NAT &
        pushvalues : seq(LUA_VALUES) &
        stack_top >= npops &
        stack_top - npops + size(pushvalues) <= max_stack_top
    THEN    
        LET
            base
        BE
            base = stack_top - npops
        IN
            stack_top := base + size(pushvalues) ||
            stack := (stack <+ %ii. (ii: base+1..stack_top |not_a_lua_value)) 
            <+ %ii. (ii: base+1..base+size(pushvalues) |pushvalues(ii - base))
        END
    END;        
    
    /* updates max_stack_top to a greater value */
    increase_max_stack_top (xx) = 
    PRE
        xx : NAT &
        xx > max_stack_top &
        xx < pseudo_bottom
    THEN
        max_stack_top := xx
    END;
    
    /* updates stack_top to the given value */
    settop (index) = 
    PRE
        index : INT &
        (index : acceptable_indexes or index = 0) &
        index < max_stack_top
    THEN
        LET new_top
        BE
            new_top = convert_index(stack_top,index)
        IN
            stack_top := new_top ||
        	IF 
                new_top > stack_top 
            THEN 
                stack := stack <+ % ii.(ii : stack_top+1..new_top | nil_lua_value)          
            ELSIF
                new_top < stack_top
            THEN
                stack := stack <+ % ii.(ii : new_top+1..stack_top | not_a_lua_value)                          
            END
        END 
    END;
    
    /* inserts a value in a given position, moving up the items on top of that position */
    insert (index) =
    PRE
    	stack_top >= 1 & 
    	index : stack_indexes  
    THEN
        LET
            actual_index
        BE
            actual_index = convert_index(stack_top, index)
        IN
            stack := %xx.(xx:1..actual_index - 1| stack(xx)) \/ 
    		{actual_index |-> stack(stack_top)} \/
    		%xx.(xx:actual_index+1..stack_top | stack(xx-1)) \/
    		%xx.(xx:stack_top+1..pseudo_bottom-1 |not_a_lua_value) \/
    		(pseudo_indexes <| stack) 
  	  END
    END;

    /* removes a value from a given position, moving down the items on top of that position */
    remove (index) =
    PRE
    	stack_top >= 1 & 
    	index : stack_indexes /* this implies that stack_top is at least 1 */ 
    THEN
        LET
            actual_index
        BE
            actual_index = convert_index(stack_top, index)
        IN
    	      stack := %xx.(xx:1..actual_index - 1|stack(xx)) \/ 
    		%xx.(xx:actual_index..stack_top-1 | stack(xx+1)) \/
    		%xx.(xx:stack_top..pseudo_bottom-1 |not_a_lua_value) \/
    		(pseudo_indexes <| stack) ||
    		stack_top := stack_top -1
  	  END
    END;
    
    /* updates top and pushes an extra value or just pops */
    next (key, vv, flag) = 
    PRE
    	stack_top > 0 &                             /* stack is not empty */
    	stack_top < max_stack_top &      /* there is place on the stack */
    	key : LUA_VALUES &
    	vv : LUA_VALUES &
    	flag : BOOL
    THEN
      IF 
         flag = TRUE
      THEN 
         stack := stack <+ {stack_top |-> key, stack_top + 1 |-> vv} ||
                 stack_top := stack_top + 1
      ELSE
         stack := stack <+ {stack_top |-> not_a_lua_value} ||
                 stack_top := stack_top - 1 
      END        
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
 /* !!! podem ser outros valores não numéricos */   
    
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
            stack := stack <+ {stack_top |-> (lua_value_unary_minus (stack(stack_top)))}
        ELSE
            CASE op1 OF
                EITHER LUA_OPADD THEN 
                    stack := stack <+ 
                    {(stack_top-1) |-> 
                        (lua_value_addition(stack(stack_top-1),stack(stack_top))),
                      (stack_top |-> not_a_lua_value)} ||
                    stack_top := stack_top -1
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
