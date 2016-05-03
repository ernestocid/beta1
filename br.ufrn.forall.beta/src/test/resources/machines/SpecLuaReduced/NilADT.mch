/* NilADT
 * Author: 
 * Creation date: 28/04/14
 */
MACHINE
    NilADT
SEES
    BasicTypes,
    BasicTypeProjections
    
CONSTANTS
    lua_value_from_nil, 
    lua_nil_from_lua_value,
    nil_lua_value
    
PROPERTIES
    /* function that "construct" the record for nil */
    /* the values of the other fields are not specified - 
    	 any implementation would do*/ 
    lua_value_from_nil : LUA_NIL --> NIL_LUA_VALUES &
        value_type(lua_value_from_nil(nil)) = LUA_TNIL &
        select_nil(lua_value_from_nil(nil)) = nil &
    
    /* function to extract the (Lua) nil value from a LUA_VALUE */
    lua_nil_from_lua_value : NIL_LUA_VALUES --> LUA_NIL &
    !vv. (vv: NIL_LUA_VALUES => lua_nil_from_lua_value(vv) = nil) &
    
    nil_lua_value : NIL_LUA_VALUES & 
    nil_lua_value = lua_value_from_nil(nil) &
    lua_nil_from_lua_value(nil_lua_value) = nil

END
