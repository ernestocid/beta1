MACHINE
    NilADT
SEES
    BasicTypes ,
    BasicTypeProjections

CONCRETE_CONSTANTS
    lua_value_from_nil ,
    lua_nil_from_lua_value ,
    nil_lua_value

PROPERTIES

    lua_value_from_nil : LUA_NIL --> NIL_LUA_VALUES &
        value_type ( lua_value_from_nil ( nil ) ) = LUA_TNIL &
        select_nil ( lua_value_from_nil ( nil ) ) = nil &

    lua_nil_from_lua_value : NIL_LUA_VALUES --> LUA_NIL &
    ! vv . ( vv : NIL_LUA_VALUES => lua_nil_from_lua_value ( vv ) = nil ) &

    nil_lua_value : NIL_LUA_VALUES &
    nil_lua_value = lua_value_from_nil ( nil ) &
    lua_nil_from_lua_value ( nil_lua_value ) = nil

END
