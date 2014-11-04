MACHINE
    BooleanADT
SEES
    BasicTypes ,
    BasicTypeProjections

CONCRETE_CONSTANTS
    lua_value_from_boolean ,
    lua_boolean_from_lua_value ,
    false_lua_value ,
    true_lua_value

PROPERTIES

    lua_value_from_boolean : LUA_BOOLEAN --> BOOLEAN_LUA_VALUES &
    ! bb . ( bb : LUA_BOOLEAN =>
          ( value_type ( lua_value_from_boolean ( bb ) ) = LUA_TBOOLEAN &
           select_boolean ( lua_value_from_boolean ( bb ) ) = bb ) ) &

    lua_boolean_from_lua_value : BOOLEAN_LUA_VALUES --> LUA_BOOLEAN &
    ! vv . ( vv : BOOLEAN_LUA_VALUES =>
        lua_boolean_from_lua_value ( vv ) = select_boolean ( vv ) ) &

    true_lua_value = lua_value_from_boolean ( true ) &
    false_lua_value = lua_value_from_boolean ( false ) &
    select_boolean ( false_lua_value ) = false &
    lua_boolean_from_lua_value ( false_lua_value ) = false

END
