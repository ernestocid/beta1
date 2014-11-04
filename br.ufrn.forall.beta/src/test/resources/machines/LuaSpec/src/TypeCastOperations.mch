
MACHINE
    TypeCastOperations
SEES
    BasicTypes ,
    Aux ,
    NumberADT ,
    BooleanADT ,
    NilADT ,
    Pseudo_Stack







OPERATIONS

/* lua_toboolean [-0, +0, ?]
int lua_toboolean (lua_State *L, int index);
Converts the Lua value at the given acceptable index to a C boolean value (0 or 1). Like all tests in Lua, lua_toboolean returns true for any Lua value different from false and nil; otherwise it returns false. It also returns false when called with a non-valid index. (If you want to accept only actual boolean values, use lua_isboolean to test the value's type.)
*/
/* !!! assuming acceptable but non valid indexes to contain not_a_lua_value. This should be true, but is not required by the pseudo-stack invariant*/
ret <-- lua_toboolean ( index ) =
PRE
    index : INT & index : - stack_top .. - 1 \/ 1 .. stack_top \/ pseudo_bottom .. MAXINT \/ ( stack_top + 1 ) .. max_stack_top
THEN
    LET
        actual_index
    BE
        actual_index = convert_index ( stack_top , index )
    IN
        IF
            stack ( actual_index ) : { false_lua_value , nil_lua_value , not_a_lua_value }
      THEN
          ret := 0
      ELSE
          ret := 1
      END
    END
END

END
