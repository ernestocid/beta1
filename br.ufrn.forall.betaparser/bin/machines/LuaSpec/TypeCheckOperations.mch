/* TypeCheckOperations
 * Author: Anamaria
 * Creation date: Wed Jul 11 2012
 */
MACHINE
    TypeCheckOperations
    
SEES
    BasicTypes,
    Aux,
    StringADT,
    ADTConvertions
    
INCLUDES
    Memory
    
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == stack_indexes \/ pseudo_indexes;
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top
   
    
OPERATIONS
    
/*    lua_isboolean[-0, +0, ?]
int lua_isboolean (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a boolean, and 0 otherwise.
*/
ret <-- lua_isboolean (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF stack(actual_index) : BOOLEAN_LUA_VALUES
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END        
END;

/*
lua_iscfunction[-0, +0, ?]
int lua_iscfunction (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a C function, and 0 otherwise.
*/
/* !!! na realidade, por enquanto está apenas verificando se é function */
ret <-- lua_iscfunction (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF stack(actual_index) : FUNCTION_LUA_VALUES
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;

/*
lua_isfunction [-0, +0, ?]
int lua_isfunction (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a function (either C or Lua), and 0 otherwise.
*/
ret <-- lua_isfunction (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF stack(actual_index) : FUNCTION_LUA_VALUES
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;

/*
lua_islightuserdata [-0, +0, ?]
int lua_islightuserdata (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a light userdata, and 0 otherwise.
*/
ret <-- lua_islightuserdata (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF stack(actual_index) : LIGHTUSERDATA_LUA_VALUES
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;

/*
lua_isnil [-0, +0, ?]
int lua_isnil (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is nil, and 0 otherwise.
*/
ret <-- lua_isnil (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF stack(actual_index) : NIL_LUA_VALUES
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;


/*
lua_isnone [-0, +0, ?]
int lua_isnone (lua_State *L, int index);
Returns 1 if the given acceptable index is not valid (that is, it refers to an element outside the current stack), and 0 otherwise.
*/
ret <-- lua_isnone (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index( stack_top, index)
    IN
        IF stack(actual_index) : NOT_LUA_VALUES
        THEN 
            ret := 1
        ELSE
            ret := 0
        END
    END
END;

/*
lua_isnoneornil [-0, +0, ?]
int lua_isnoneornil (lua_State *L, int index);
Returns 1 if the given acceptable index is not valid (that is, it refers to an element outside the current stack) or if the value at this index is nil, and 0 otherwise.
*/
ret <-- lua_isnoneornil (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index( stack_top, index)
    IN
        IF (stack(actual_index): NIL_LUA_VALUES \/ NOT_LUA_VALUES)
        THEN 
            ret := 1
        ELSE
            ret := 0
        END
    END
END;

/* 
lua_isnumber [-0, +0, ?]
int lua_isnumber (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a number or a string convertible to a number, and 0 otherwise.
*/
ret <-- lua_isnumber (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF (stack(actual_index) : NUMBER_LUA_VALUES or
               (stack(actual_index) : STRING_LUA_VALUES &
                  lua_string_from_lua_value(stack(actual_index)) : NUMBER_LUA_STRING))
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;

/*
lua_isstring [-0, +0, ?]
int lua_isstring (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a string or a number (which is always convertible to a string), and 0 otherwise.
*/
ret <-- lua_isstring (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF (stack(actual_index) : STRING_LUA_VALUES \/ NUMBER_LUA_VALUES)
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;

/*
lua_istable [-0, +0, ?]
int lua_istable (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a table, and 0 otherwise.
*/
ret <-- lua_istable (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF stack(actual_index) : TABLE_LUA_VALUES
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;

/*
lua_isthread [-0, +0, ?]
int lua_isthread (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a thread, and 0 otherwise.
*/
ret <-- lua_isthread (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF stack(actual_index) : THREAD_LUA_VALUES
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;

/*
lua_isuserdata [-0, +0, ?]
int lua_isuserdata (lua_State *L, int index);
Returns 1 if the value at the given acceptable index is a userdata (either full or light), and 0 otherwise. 
*/
ret <-- lua_isuserdata (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index(stack_top, index)
    IN
       IF (stack(actual_index) : USERDATA_LUA_VALUES \/ LIGHTUSERDATA_LUA_VALUES)
       THEN 
           ret := 1
       ELSE
           ret := 0
       END
    END 
END;


/* lua_type [-0, +0, ?]
int lua_type (lua_State *L, int index); Returns the type of the value in the given acceptable index, or LUA_TNONE for a non-valid index. The types returned by lua_type are coded by the following constants defined in lua.h: LUA_TNIL, LUA_TNUMBER, LUA_TBOOLEAN, LUA_TSTRING, LUA_TTABLE, LUA_TFUNCTION, LUA_TUSERDATA, LUA_TTHREAD, and LUA_TLIGHTUSERDATA. 
*/
ret <-- lua_type (index) =
PRE 
    index : acceptable_indexes
THEN
    LET actual_index
    BE actual_index = convert_index( stack_top, index)
    IN
        ret := value_type(stack(actual_index))
    END
END


END
