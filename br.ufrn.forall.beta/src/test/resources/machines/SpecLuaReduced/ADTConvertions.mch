/* ADTConvertions
 * Author: 
 * Creation date: 16/05/14
 */
MACHINE
    ADTConvertions
SEES
    BasicTypes,
    NumberADT,
    StringADT
    
CONSTANTS
    NUMBER_LUA_STRING,
    NUMBER_STRING_LUA_VALUES,
    number_from_string,
    string_from_number,
    number_from_string_lua_values,
    string_from_number_lua_values
    
PROPERTIES
    
    /* the strings that correspond to numbers */
    NUMBER_LUA_STRING <: LUA_STRING &
    NUMBER_STRING_LUA_VALUES <: STRING_LUA_VALUES &
    NUMBER_STRING_LUA_VALUES = {vv| vv: STRING_LUA_VALUES & lua_string_from_lua_value(vv) : NUMBER_LUA_STRING} &
    

    /* the semantics of the convertions is not defined */
    /* gives the number corresponding to each NUMBER_LUA_STRING */
    number_from_string : NUMBER_LUA_STRING --> LUA_NUMBER &
    /* gives the string corresponding to each LUA_NUMBER */
    string_from_number : LUA_NUMBER --> LUA_STRING &

    /* extracts, converts and constructs the corresponding NUMBER_LUA_VALUE */
    number_from_string_lua_values: NUMBER_STRING_LUA_VALUES --> NUMBER_LUA_VALUES &
    !vv.(vv: STRING_LUA_VALUES & 
        lua_string_from_lua_value(vv) : NUMBER_LUA_STRING => 
        number_from_string_lua_values(vv) = 
        (lua_string_from_lua_value;number_from_string;lua_value_from_number)(vv)) &
        
    
    /* extracts, converts and constructs the corresponding STRING_LUA_VALUE */
    string_from_number_lua_values: NUMBER_LUA_VALUES --> STRING_LUA_VALUES &
    !vv.(vv: NUMBER_LUA_VALUES => 
        string_from_number_lua_values(vv) = 
        (lua_number_from_lua_value;string_from_number;lua_value_from_string)(vv))


END
