/* BooleanADT
 * Author: 
 * Creation date: 28/04/14
 */
MACHINE
    BooleanADT
SEES
    BasicTypes,
    BasicTypeProjections
    
CONSTANTS
    lua_value_from_boolean, 
    lua_boolean_from_lua_value,
    false_lua_value,
    true_lua_value 
    
PROPERTIES
    /* function that "constructs" the record for each boolean */
    /* the values of the other fields are not specified but will 
    always be the same: it is a function - any implementation would do*/ 
    lua_value_from_boolean : LUA_BOOLEAN --> BOOLEAN_LUA_VALUES &
    !bb. (bb:  LUA_BOOLEAN => 
          (value_type(lua_value_from_boolean(bb)) = LUA_TBOOLEAN &
           select_boolean(lua_value_from_boolean(bb)) = bb )) &
    
    /* function to extract the (Lua) boolean value from a LUA_VALUE */
    lua_boolean_from_lua_value : BOOLEAN_LUA_VALUES --> LUA_BOOLEAN &
    !vv. (vv: BOOLEAN_LUA_VALUES => 
        lua_boolean_from_lua_value(vv) = select_boolean(vv)) &
    
    true_lua_value = lua_value_from_boolean(true) &
    false_lua_value = lua_value_from_boolean(false) & 
    select_boolean(false_lua_value) = false &
    lua_boolean_from_lua_value(false_lua_value) = false

END
