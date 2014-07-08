/* NumberADT
 * Author: Anamaria
 * Creation date: Thu Jul 12 2012
 */
MACHINE
    NumberADT
SEES
    BasicTypes,
    BasicTypeProjections
    
CONSTANTS
    lua_number_from_lua_value,
    lua_value_from_number,
    lua_value_unary_minus,
    lua_value_addition
    
    
PROPERTIES
    /* function that "constructs" the record for each lua number */
    /* the values of the other fields are not specified */ 
    lua_value_from_number : LUA_NUMBER --> NUMBER_LUA_VALUES &
    !nn. (nn:  LUA_NUMBER => 
        (value_type(lua_value_from_number(nn)) = LUA_TNUMBER &
         select_number(lua_value_from_number(nn)) = nn)) &
 
    /* function to extract the LUA_NUMBER value from a LUA_VALUE */
    lua_number_from_lua_value : NUMBER_LUA_VALUES --> LUA_NUMBER &
    !vv. (vv: NUMBER_LUA_VALUES => 
        lua_number_from_lua_value(vv) = select_number(vv)) &
           
    
    /* function to provide the LUA_VALUE representation of minus a number */
    /* total for every number in NUMBER_LUA_VALUES */
    lua_value_unary_minus : NUMBER_LUA_VALUES --> NUMBER_LUA_VALUES &
    !vv. (vv: NUMBER_LUA_VALUES => 
           (( value_type(lua_value_unary_minus(vv)) = LUA_TNUMBER) & 
              select_number(lua_value_unary_minus(vv)) = 
               						-(select_number(vv)))) &
                
    /* function to provide the LUA_VALUE representation of the addition of 2 numbers */
    /* because of the INT limit, the result, as it is, may be wrong (overflow) */
    lua_value_addition : (NUMBER_LUA_VALUES * NUMBER_LUA_VALUES) --> NUMBER_LUA_VALUES &
    !(v1, v2). ((v1: NUMBER_LUA_VALUES & v2: NUMBER_LUA_VALUES) => 
           (( value_type(lua_value_addition(v1,v2)) = LUA_TNUMBER) & 
              select_number(lua_value_addition(v1,v2)) = 
               						(select_number(v1) + select_number(v2))))    
 
    
        

END
