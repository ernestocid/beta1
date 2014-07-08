/* LightuserdataADT
 * Author: Anamaria
 * Creation date: Wed Aug 1 2012
 */
MACHINE
    LightuserdataADT
SEES
    BasicTypes,
    BasicTypeProjections
    
CONSTANTS
    lua_value_from_lightuserdata, 
    lua_lightuserdata_from_lua_value
    
PROPERTIES
    /* function that "construct" the record for each userdata */
    /* the values of the other fields are not specified - 
    	 any implementation would do*/ 
    lua_value_from_lightuserdata : LUA_LIGHTUSERDATA --> LIGHTUSERDATA_LUA_VALUES &
    !bb. (bb:  LUA_LIGHTUSERDATA => 
          (value_type(lua_value_from_lightuserdata(bb)) = LUA_TLIGHTUSERDATA &
           select_lightuserdata(lua_value_from_lightuserdata(bb)) = bb)) &
    
    /* function to extract the (Lua) userdata value from a LUA_VALUE */
    lua_lightuserdata_from_lua_value : LIGHTUSERDATA_LUA_VALUES --> LUA_LIGHTUSERDATA &
    !vv. (vv: LIGHTUSERDATA_LUA_VALUES => 
        lua_lightuserdata_from_lua_value(vv) = select_lightuserdata(vv)) 


END

