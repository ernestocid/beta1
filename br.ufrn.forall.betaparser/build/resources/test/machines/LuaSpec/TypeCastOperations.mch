/* TypeCastOperations
 * Author: Anamaria
 * Creation date: Tue Aug 7 2012
 */
MACHINE
    TypeCastOperations
        
SEES
    BasicTypes,
    Aux,
    NumberADT,
    BooleanADT,
    NilADT,
    StringADT,
    FunctionADT,
    UserdataADT,
    TableADT,
    ADTConvertions
    
INCLUDES
    Memory
    
DEFINITIONS
    stack_indexes == -stack_top..-1 \/ 1..stack_top;
    pseudo_indexes == pseudo_bottom..MAXINT;
    valid_indexes == stack_indexes \/ pseudo_indexes;
    acceptable_indexes == valid_indexes \/ (stack_top+1)..max_stack_top
    
    
CONSTANTS
    null,
    /* for now, they are all converting to INT - may be further developed */
    lua_CFunction_of,
    lua_State_of,
    lua_Integer_of,
    lua_Number_of,
    lua_Unsigned_of,
    pointer_to
    
PROPERTIES
    null : INT &
    lua_CFunction_of : LUA_VALUES +-> INT &
    lua_State_of : LUA_VALUES +-> INT &
    lua_Integer_of : LUA_VALUES +-> INT &
    lua_Number_of : LUA_VALUES +-> INT &
    lua_Unsigned_of : LUA_VALUES +-> INT &
    pointer_to : LUA_VALUES +-> INT 
    
OPERATIONS
    
/* lua_toboolean [-0, +0, ?]
int lua_toboolean (lua_State *L, int index);
Converts the Lua value at the given acceptable index to a C boolean value (0 or 1). Like all tests in Lua, lua_toboolean returns true for any Lua value different from false and nil; otherwise it returns false. It also returns false when called with a non-valid index. (If you want to accept only actual boolean values, use lua_isboolean to test the value's type.)
*/
/* !!! assuming acceptable but non valid indexes to contain not_a_lua_value. This should be true, but is not required by the pseudo-stack invariant*/
ret <-- lua_toboolean (index) =
PRE
    index : acceptable_indexes
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
	IF 
	    stack(actual_index) : {false_lua_value, nil_lua_value, not_a_lua_value}
      THEN
          ret := 0
      ELSE
          ret := 1
      END
    END 
END;


/*
lua_tocfunction [-0, +0, ?]
lua_CFunction lua_tocfunction (lua_State *L, int index);
Converts a value at the given acceptable index to a C function. That value must be a C function; otherwise, returns NULL.
*/

ret <-- lua_tocfunction (index) =
PRE
    index : acceptable_indexes
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
           IF stack(actual_index) : FUNCTION_LUA_VALUES   /* do we need a set for C functions? */
    	     THEN
        	   ret := lua_CFunction_of(stack(actual_index))
           ELSE
               ret := null 
           END
   END
END;

/*
lua_tointeger [-0, +0, ?]
lua_Integer lua_tointeger (lua_State *L, int index);
Equivalent to lua_tointegerx with isnum equal to NULL.
*/

ret <-- lua_tointeger (index) =
PRE
    index : acceptable_indexes 
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
          IF 
        	 stack(actual_index) : NUMBER_LUA_VALUES 
          THEN 
             ret := lua_Integer_of(stack(index))
          ELSIF 
              stack(actual_index) : STRING_LUA_VALUES  & 
              lua_string_from_lua_value(stack(actual_index)): NUMBER_LUA_STRING
          THEN 
              ret := lua_Integer_of(number_from_string_lua_values(stack(actual_index)))
          ELSE                  /* not a number or not a lua value */
              ret := 0
          END
    END
END;

/*
lua_tointegerx [-0, +0, ?]
lua_Integer lua_tointegerx (lua_State *L, int index, int *isnum);
Converts the Lua value at the given acceptable index to the signed integral type lua_Integer. The Lua value must be a number or a string convertible to a number (see §3.4.2); otherwise, lua_tointegerx returns 0.
If the number is not an integer, it is truncated in some non-specified way.
If isnum is not NULL, its referent is assigned a boolean value that indicates whether the operation succeeded.
*/

/* !!! isnum is modeled as an additional return parameter */

ret, isnum <-- lua_tointegerx (index) =
PRE
    index : acceptable_indexes 
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
          IF 
        	 stack(actual_index) : NUMBER_LUA_VALUES 
          THEN 
              ret := lua_Integer_of(stack(index))||
              isnum := true_lua_value
          ELSIF 
              stack(actual_index) : STRING_LUA_VALUES  & 
              lua_string_from_lua_value(stack(actual_index)): NUMBER_LUA_STRING
          THEN 
              ret := lua_Integer_of(number_from_string_lua_values(stack(actual_index))) ||
              isnum := true_lua_value
          ELSE                  /* not a number or not a lua value */
              ret := 0||
              isnum := false_lua_value
          END
    END
END;

/*
lua_tonumber [-0, +0, ?]
lua_Number lua_tonumber (lua_State *L, int index);
Equivalent to lua_tonumberx with isnum equal to NULL.
*/
/* !!! because we only have integers, this operation is identical to lua_to_integer */
ret <-- lua_tonumber (index) =
PRE
    index : acceptable_indexes 
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
          IF 
        	 stack(actual_index) : NUMBER_LUA_VALUES 
          THEN 
             ret := lua_Number_of(stack(index))
          ELSIF 
              stack(actual_index) : STRING_LUA_VALUES  & 
              lua_string_from_lua_value(stack(actual_index)): NUMBER_LUA_STRING
          THEN 
              ret := lua_Number_of(number_from_string_lua_values(stack(actual_index))) 
          ELSE                  /* not a number or not a lua value */
              ret := 0
          END
    END
END;

/*
lua_tonumberx [-0, +0, ?]
lua_Number lua_tonumberx (lua_State *L, int index, int *isnum);
Converts the Lua value at the given acceptable index to the C type lua_Number (see lua_Number). The Lua value must be a number or a string convertible to a number (see §3.4.2); otherwise, lua_tonumberx returns 0.

If isnum is not NULL, its referent is assigned a boolean value that indicates whether the operation succeeded.
*/
/* !!! because we only have integers, this operation is identical to lua_to_integerx */
ret, isnum <-- lua_tonumberx (index) =
PRE
    index : acceptable_indexes
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
          IF 
        	 stack(actual_index) : NUMBER_LUA_VALUES 
          THEN 
              ret := lua_Number_of(stack(index))||
              isnum := true_lua_value
          ELSIF 
              stack(actual_index) : STRING_LUA_VALUES  & 
              lua_string_from_lua_value(stack(actual_index)): NUMBER_LUA_STRING
          THEN 
              ret := lua_Number_of(number_from_string_lua_values(stack(actual_index))) ||
              isnum := true_lua_value
          ELSE                  /* not a number or not a lua value */
              ret := 0||
              isnum := false_lua_value
          END
    END
END;


/*
lua_topointer [-0, +0, ?]
const void *lua_topointer (lua_State *L, int index);
Converts the value at the given acceptable index to a generic C pointer (void*). The value can be a userdata, a table, a thread, or a function; otherwise, lua_topointer returns NULL. Different objects will give different pointers. There is no way to convert the pointer back to its original value.

Typically this function is used only for debug information.
*/
ret <-- lua_topointer (index) =
PRE
    index : acceptable_indexes
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
           IF stack(actual_index) : FUNCTION_LUA_VALUES \/ USERDATA_LUA_VALUES \/ TABLE_LUA_VALUES \/ THREAD_LUA_VALUES
    	     THEN
        	   ret := pointer_to(stack(actual_index))   
           ELSE
               ret := null      
           END
   END
END;



/*
lua_tothread [-0, +0, ?]
lua_State *lua_tothread (lua_State *L, int index);
Converts the value at the given acceptable index to a Lua thread (represented as lua_State*). This value must be a thread; otherwise, the function returns NULL.
*/

ret <-- lua_tothread (index) =
PRE
    index : acceptable_indexes
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
           IF stack(actual_index) : THREAD_LUA_VALUES   
    	     THEN
        	   ret := lua_State_of(stack(actual_index))
           ELSE
               ret := null
           END
   END
END;


/* 
lua_tounsigned [-0, +0, ?]
lua_Unsigned lua_tounsigned (lua_State *L, int index);
Equivalent to lua_tounsignedx with isnum equal to NULL.
*/

/* !!! convertion not made */
ret <-- lua_tounsigned (index) =
PRE
    index : acceptable_indexes 
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
          IF 
        	 stack(actual_index) : NUMBER_LUA_VALUES 
          THEN 
             ret := lua_Unsigned_of(stack(index))
          ELSIF 
              stack(actual_index) : STRING_LUA_VALUES  & 
              lua_string_from_lua_value(stack(actual_index)): NUMBER_LUA_STRING
          THEN 
              ret := lua_Unsigned_of(number_from_string_lua_values(stack(actual_index)))           	    ELSE                  /* not a number or not a lua value */
              ret := 0
          END
    END
END;

/*
lua_tounsignedx [-0, +0, ?]
lua_Unsigned lua_tounsignedx (lua_State *L, int index, int *isnum);
Converts the Lua value at the given acceptable index to the unsigned integral type lua_Unsigned. The Lua value must be a number or a string convertible to a number (see §3.4.2); otherwise, lua_tounsignedx returns 0.

If the number is not an integer, it is truncated in some non-specified way. If the number is outside the range of representable values, it is normalized to the remainder of its division by one more than the maximum representable value.

If isnum is not NULL, its referent is assigned a boolean value that indicates whether the operation succeeded.
*/

/* !!! convertion not made */

ret, isnum <-- lua_tounsignedx (index) =
PRE
    index : acceptable_indexes 
THEN
    LET
        actual_index
    BE 
        actual_index = convert_index(stack_top,index)
    IN 
          IF 
        	 stack(actual_index) : NUMBER_LUA_VALUES 
          THEN 
             ret := lua_Unsigned_of(stack(index))||
              isnum := true_lua_value
          ELSIF 
              stack(actual_index) : STRING_LUA_VALUES  & 
              lua_string_from_lua_value(stack(actual_index)): NUMBER_LUA_STRING
          THEN 
              ret := lua_Unsigned_of(number_from_string_lua_values(stack(actual_index)))||
              isnum := true_lua_value
          ELSE                  /* not a number or not a lua value */
              ret := 0||
              isnum := false_lua_value
          END
    END
END;


/* 
lua_touserdata [-0, +0, ?]
void *lua_touserdata (lua_State *L, int index);
If the value at the given acceptable index is a full userdata, returns its block address. If the value is a light userdata, returns its pointer. Otherwise, returns NULL. 
*/
ret <-- lua_touserdata (index) =
PRE
    index : acceptable_indexes
THEN
    LET
        vv
    BE 
        vv = stack(convert_index(stack_top,index))
    IN 
          IF 
        	 vv : USERDATA_LUA_VALUES 
          THEN 
              ret := udaddress_of(userdata_heap(lua_userdata_from_lua_value(vv)))  
          ELSIF 
              vv : LIGHTUSERDATA_LUA_VALUES
          THEN
              ret := pointer_to(stack(convert_index(stack_top,index)))   
          ELSE       /* not userdata or not a lua value */          
              ret :=  null  
          END
    END
END

END
