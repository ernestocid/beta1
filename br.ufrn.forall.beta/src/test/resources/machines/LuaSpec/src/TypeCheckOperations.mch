/* TypeCheckOperations
 * Author: 
 * Creation date: 16/05/14
 */
MACHINE
    TypeCheckOperations
SEES
    BasicTypes ,
    Aux ,
    Pseudo_Stack







OPERATIONS

/*    lua_isboolean[-0, +0, ?]
int lua_isboolean (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a boolean, and 0 otherwise.
*/
ret <-- lua_isboolean ( index ) =
PRE
    index : INT & index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT \/ ( stack_top + 1 ) .. max_stack_top
THEN
    LET actual_index
    BE actual_index = convert_index ( stack_top , index )
    IN
       IF stack ( actual_index ) : BOOLEAN_LUA_VALUES
       THEN
           ret := 1
       ELSE
           ret := 0
       END
    END
END ;


/*
lua_isnil [-0, +0, ?]
int lua_isnil (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is nil, and 0 otherwise.
*/
ret <-- lua_isnil ( index ) =
PRE
    index : INT & index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT \/ ( stack_top + 1 ) .. max_stack_top
THEN
    LET actual_index
    BE actual_index = convert_index ( stack_top , index )
    IN
       IF stack ( actual_index ) : NIL_LUA_VALUES
       THEN
           ret := 1
       ELSE
           ret := 0
       END
    END
END ;


/*
lua_isnone [-0, +0, ?]
int lua_isnone (lua_State *L, int index);
Returns 1 if the given acceptable index is not valid (that is, it refers to an element outside the current stack), and 0 otherwise.
*/
ret <-- lua_isnone ( index ) =
PRE
    index : INT & index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT \/ ( stack_top + 1 ) .. max_stack_top
THEN
    LET actual_index
    BE actual_index = convert_index ( stack_top , index )
    IN
        IF stack ( actual_index ) : NOT_LUA_VALUES
        THEN
            ret := 1
        ELSE
            ret := 0
        END
    END
END ;

/*
lua_isnoneornil [-0, +0, ?]
int lua_isnoneornil (lua_State *L, int index);
Returns 1 if the given acceptable index is not valid (that is, it refers to an element outside the current stack) or if the value at this index is nil, and 0 otherwise.
*/
ret <-- lua_isnoneornil ( index ) =
PRE
    index : INT & index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT \/ ( stack_top + 1 ) .. max_stack_top
THEN
    LET actual_index
    BE actual_index = convert_index ( stack_top , index )
    IN
        IF ( stack ( actual_index ) : NIL_LUA_VALUES \/ NOT_LUA_VALUES )
        THEN
            ret := 1
        ELSE
            ret := 0
        END
    END
END ;


/* lua_type [-0, +0, ?]
int lua_type (lua_State *L, int index); Returns the type of the value in the given acceptable index, or LUA_TNONE for a non-valid index. The types returned by lua_type are coded by the following constants defined in lua.h: LUA_TNIL, LUA_TNUMBER, LUA_TBOOLEAN, LUA_TSTRING, LUA_TTABLE, LUA_TFUNCTION, LUA_TUSERDATA, LUA_TTHREAD, and LUA_TLIGHTUSERDATA. 
*/
ret <-- lua_type ( index ) =
PRE
    index : INT & index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT \/ ( stack_top + 1 ) .. max_stack_top
THEN
    LET actual_index
    BE actual_index = convert_index ( stack_top , index )
    IN
        ret := value_type ( stack ( actual_index ) )
    END
END
END
