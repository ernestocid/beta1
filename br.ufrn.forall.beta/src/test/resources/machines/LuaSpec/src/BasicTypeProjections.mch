
MACHINE
    BasicTypeProjections
SEES
    BasicTypes






















CONCRETE_CONSTANTS
    value_tuple ,
    select_number ,
    select_boolean ,
    select_nil



PROPERTIES
    value_tuple : LUA_VALUES --> VALUE_TUPLES &
    value_tuple =  prj2 ( LUA_TYPES , VALUE_TUPLES ) &

    select_number : LUA_VALUES --> LUA_NUMBER &
    select_number = ( value_tuple ; prj2 ( LUA_NIL * LUA_BOOLEAN , LUA_NUMBER ) ) &

    select_boolean : LUA_VALUES --> LUA_BOOLEAN &
    select_boolean = ( value_tuple ; ( prj1 ( LUA_NIL * LUA_BOOLEAN , LUA_NUMBER ) ; prj2 ( LUA_NIL , LUA_BOOLEAN ) ) ) &

    select_nil : LUA_VALUES --> LUA_NIL &
    select_nil = ( value_tuple ; ( prj1 ( LUA_NIL * LUA_BOOLEAN , LUA_NUMBER ) ; prj1 ( LUA_NIL , LUA_BOOLEAN ) ) )

END
