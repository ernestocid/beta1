/* StringADT
 * Author: Anamaria
 * Creation date: Tue Jul 17 2012
 */
MACHINE
    StringADT
SEES
    BasicTypes,
    BasicTypeProjections
    
CONSTANTS
    lua_value_from_string, 
    lua_string_from_lua_value
    
PROPERTIES
    /* function that "construct" the record for each string */
    /* the values of the other fields are not specified - 
    	 any implementation would do*/ 
    lua_value_from_string : LUA_STRING --> STRING_LUA_VALUES &
    !bb. (bb:  LUA_STRING => 
        ( value_type(lua_value_from_string(bb)) = LUA_TSTRING &
          select_string(lua_value_from_string(bb)) = bb)) &
    
    /* function to extract the (Lua) string value from a LUA_VALUE */
    lua_string_from_lua_value : STRING_LUA_VALUES --> LUA_STRING &
    !vv. (vv: STRING_LUA_VALUES => 
        lua_string_from_lua_value(vv) = select_string(vv)) 
    



END
