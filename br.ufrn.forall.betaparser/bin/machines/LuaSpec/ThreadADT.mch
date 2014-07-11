/* ThreadADT
 * Author: Anamaria
 * Creation date: Tue Jul 17 2012
 */
MACHINE
    ThreadADT
SEES
    BasicTypes,
    BasicTypeProjections
    
CONSTANTS
    lua_value_from_thread, 
    lua_thread_from_lua_value
    
PROPERTIES
    /* function that "construct" the record for each thread */
    /* the values of the other fields are not specified - 
    	 any implementation would do*/ 
    lua_value_from_thread : LUA_THREAD --> THREAD_LUA_VALUES &
    !bb. (bb:  LUA_THREAD => 
          (value_type(lua_value_from_thread(bb)) = LUA_TTHREAD &
           select_thread(lua_value_from_thread(bb)) = bb) ) &
    
    /* function to extract the (Lua) thread value from a LUA_VALUE */
    lua_thread_from_lua_value : THREAD_LUA_VALUES --> LUA_THREAD &
    !vv. (vv: THREAD_LUA_VALUES => 
        lua_thread_from_lua_value(vv) = select_thread(vv)) 



END
