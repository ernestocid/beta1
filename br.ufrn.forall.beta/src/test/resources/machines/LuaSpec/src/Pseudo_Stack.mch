MACHINE
    Pseudo_Stack
SEES
    BasicTypes ,
    NumberADT ,
    NilADT ,
    Aux

CONCRETE_CONSTANTS
    LUA_MINSTACK , 
    pseudo_bottom              /* bottom limit of the pseudo-indexes */

PROPERTIES
    LUA_MINSTACK : NAT1 &
    LUA_MINSTACK = 20 &
    pseudo_bottom : NAT1  &
    pseudo_bottom >  LUA_MINSTACK

ABSTRACT_VARIABLES
    stack ,                     /* a stack from indexes 1 to stack_top, but also containing valid data in its pseudo-indexes */
    stack_top ,                         /* top of the stack part of stack */
    max_stack_top              /* safe limit for stack_top */

INVARIANT
    stack : NAT1 --> LUA_VALUES &
    stack_top : NAT &
    max_stack_top : NAT1 &
    max_stack_top < pseudo_bottom &
    stack_top <= max_stack_top

INITIALISATION
    stack := % ii . ( ii : NAT1 | not_a_lua_value ) ||
    stack_top := 0 ||
    max_stack_top := LUA_MINSTACK









ASSERTIONS
    /* some useful info for the prover */
    ! index . ( index : NAT1 => convert_index ( stack_top , index ) = index ) &
    ! index . ( index : NAT => convert_index ( stack_top , index ) = index ) &
    ! index . ( index : - stack_top .. - 1 =>
              convert_index ( stack_top , index ) : 1 .. stack_top ) &
    ! index . ( index : - stack_top .. - 1 =>
        convert_index ( stack_top , index ) = stack_top + index + 1 ) &
    ! index . ( index : pseudo_bottom .. MAXINT => convert_index ( stack_top , index ) = index ) &
    ! index . ( index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT => convert_index ( stack_top , index ) : NAT1 ) &

    stack_top <= MAXINT &
    stack_top < MAXINT

OPERATIONS

    /* pushes a LUA_VALUE on top of the stack */
    push ( vv ) =
    PRE
        vv : LUA_VALUES &
        stack_top < max_stack_top
    THEN
        stack := stack <+ { stack_top + 1 |-> vv } ||
        stack_top := stack_top + 1
    END ;

    /* pops nn items from the top of the stack */
    pop ( nn ) =
    PRE
        nn : NAT1 &
        stack_top >= nn
    THEN
        stack_top := stack_top - nn ||
        stack := stack <+ % ii . ( ii : stack_top - nn + 1 .. stack_top | not_a_lua_value )
    END ;

    /* updates the value at a given stack position */
    update_at_index ( index , vv ) =
    PRE
        index : INT &
        index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT &
        vv : LUA_VALUES
    THEN
        stack ( convert_index ( stack_top , index ) ) := vv
    END ;

    /* updates the value at a given stack position and pops the top of the stack */
    update_and_pop ( index , vv ) =
    PRE
        stack_top >= 1 &
        index : INT &
        index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT &
        vv : LUA_VALUES
    THEN
        LET
            actual_index
        BE
            actual_index = convert_index ( stack_top , index )
        IN
            IF
                    not ( actual_index = stack_top )
                THEN
                    stack := stack <+ { actual_index |-> vv , stack_top |-> not_a_lua_value } ||
                    stack_top := stack_top - 1
                ELSE
                    stack := stack <+ { stack_top |-> not_a_lua_value } ||
                    stack_top := stack_top - 1
            END
        END
    END ;

    /* pops npops values and pushes the given new values */
    pop_and_push ( npops , pushvalues ) =
    PRE
        npops : NAT &
        pushvalues : seq ( LUA_VALUES ) &
        stack_top >= npops &
        stack_top - npops + size ( pushvalues ) <= max_stack_top
    THEN
        LET
            base
        BE
            base = stack_top - npops
        IN
            stack_top := base + size ( pushvalues ) ||
            stack := ( stack <+ % ii . ( ii : base + 1 .. stack_top | not_a_lua_value ) )
            <+ % ii . ( ii : base + 1 .. base + size ( pushvalues ) | pushvalues ( ii - base ) )
        END
    END ;

    /* updates max_stack_top to a greater value */
    increase_max_stack_top ( xx ) =
    PRE
        xx : NAT &
        xx > max_stack_top &
        xx < pseudo_bottom
    THEN
        max_stack_top := xx
    END ;

    /* updates stack_top to the given value */
    settop ( index ) =
    PRE
        index : INT &
        ( index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT \/ ( stack_top + 1 ) .. max_stack_top or index = 0 ) &
        index < max_stack_top
    THEN
        LET new_top
        BE
            new_top = convert_index ( stack_top , index )
        IN
            stack_top := new_top ||
                IF
                new_top > stack_top
            THEN
                stack := stack <+ % ii . ( ii : stack_top + 1 .. new_top | nil_lua_value )
            ELSIF
                new_top < stack_top
            THEN
                stack := stack <+ % ii . ( ii : new_top + 1 .. stack_top | not_a_lua_value )
            END
        END
    END ;

    /* inserts a value in a given position, moving up the items on top of that position */
    insert ( index ) =
    PRE
            stack_top >= 1 &
            index : - stack_top .. - 1 \/ 1 .. stack_top
    THEN
        LET
            actual_index
        BE
            actual_index = convert_index ( stack_top , index )
        IN
            stack := % xx . ( xx : 1 .. actual_index - 1 | stack ( xx ) ) \/
                    { actual_index |-> stack ( stack_top ) } \/
                    % xx . ( xx : actual_index + 1 .. stack_top | stack ( xx - 1 ) ) \/
                    % xx . ( xx : stack_top + 1 .. pseudo_bottom - 1 | not_a_lua_value ) \/
                    ( pseudo_bottom .. MAXINT <| stack )
            END
    END ;

    /* removes a value from a given position, moving down the items on top of that position */
    remove ( index ) =
    PRE
            stack_top >= 1 &
            index : - stack_top .. - 1 \/ 1 .. stack_top
    THEN
        LET
            actual_index
        BE
            actual_index = convert_index ( stack_top , index )
        IN
                  stack := % xx . ( xx : 1 .. actual_index - 1 | stack ( xx ) ) \/
                    % xx . ( xx : actual_index .. stack_top - 1 | stack ( xx + 1 ) ) \/
                    % xx . ( xx : stack_top .. pseudo_bottom - 1 | not_a_lua_value ) \/
                    ( pseudo_bottom .. MAXINT <| stack ) ||
                    stack_top := stack_top - 1
            END
    END ;

    /* updates top and pushes an extra value or just pops */
    next ( key , vv , flag ) =
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
         stack := stack <+ { stack_top |-> key , stack_top + 1 |-> vv } ||
                 stack_top := stack_top + 1
      ELSE
         stack := stack <+ { stack_top |-> not_a_lua_value } ||
                 stack_top := stack_top - 1
      END
   END
END
