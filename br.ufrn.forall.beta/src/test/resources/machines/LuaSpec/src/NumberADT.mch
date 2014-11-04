
MACHINE
    NumberADT
SEES
    BasicTypes ,
    BasicTypeProjections

CONCRETE_CONSTANTS
    lua_number_from_lua_value ,
    lua_value_from_number 


PROPERTIES
    lua_value_from_number : LUA_NUMBER --> NUMBER_LUA_VALUES &
    ! nn . ( nn : LUA_NUMBER =>
        ( value_type ( lua_value_from_number ( nn ) ) = LUA_TNUMBER &
         select_number ( lua_value_from_number ( nn ) ) = nn ) ) &

    lua_number_from_lua_value : NUMBER_LUA_VALUES --> LUA_NUMBER &
    ! vv . ( vv : NUMBER_LUA_VALUES =>
        lua_number_from_lua_value ( vv ) = select_number ( vv ) )

END
