/* UserdataADT
 * Author: Anamaria
 * Creation date: Tue Jul 17 2012
 */
MACHINE
    UserdataADT
SEES
    BasicTypes,
    BasicTypeProjections
    
CONSTANTS
    lua_value_from_userdata, 
    lua_userdata_from_lua_value,
    
    USERDATA_CONTENTS,
    
    udsize_of,
    udaddress_of,
    udtable_of
    
PROPERTIES
    /* function that "construct" the record for each userdata */
    /* the values of the other fields are not specified - 
    	 any implementation would do*/ 
    lua_value_from_userdata : LUA_USERDATA --> USERDATA_LUA_VALUES &
    !bb. (bb:  LUA_USERDATA => 
          (value_type(lua_value_from_userdata(bb)) = LUA_TUSERDATA &
           select_userdata(lua_value_from_userdata(bb)) = bb)) &
    
    /* function to extract the (Lua) userdata value from a LUA_VALUE */
    lua_userdata_from_lua_value : USERDATA_LUA_VALUES --> LUA_USERDATA &
    !vv. (vv: USERDATA_LUA_VALUES => 
        lua_userdata_from_lua_value(vv) = select_userdata(vv)) &
    
    /* a userdata contains its size on memory, its address and its assotiated 
    table (optional) */
    
    USERDATA_CONTENTS = (NAT * INT * (TABLE_LUA_VALUES \/ NIL_LUA_VALUES)) &
    
    udsize_of : USERDATA_CONTENTS --> NAT &
    !(nn,mm,tt). (nn:NAT & mm:INT & tt : (TABLE_LUA_VALUES \/ NIL_LUA_VALUES) 
        => udsize_of(nn,mm,tt) = nn) &
    udaddress_of : USERDATA_CONTENTS --> INT &
    !(nn,mm,tt). (nn:NAT & mm:INT & tt : (TABLE_LUA_VALUES \/ NIL_LUA_VALUES) 
        => udaddress_of(nn,mm,tt) = mm) &
    udtable_of: USERDATA_CONTENTS --> (TABLE_LUA_VALUES \/ NIL_LUA_VALUES) &
    !(nn,mm,tt). (nn:NAT & mm:INT & tt : (TABLE_LUA_VALUES \/ NIL_LUA_VALUES) 
        => udtable_of(nn,mm,tt) = tt) 



END
