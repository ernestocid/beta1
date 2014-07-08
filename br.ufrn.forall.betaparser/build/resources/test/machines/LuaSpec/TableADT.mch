/* TableADT
 * Author: Anamaria
 * Creation date: Tue Jul 17 2012
 */
MACHINE
    TableADT
SEES
    BasicTypes,
    BasicTypeProjections,
    NilADT
    
CONSTANTS
    lua_value_from_table, 
    lua_table_from_lua_value,
    access_table,
    
    /* corresponds to the actual table contents that are stored in the heap */
    TABLE_CONTENTS
    
PROPERTIES
    /* function that "constructs" the record for each table */
    /* the values of the other fields are not specified but will 
    always be the same: it is a function - any implementation would do*/ 
    lua_value_from_table : LUA_TABLE --> TABLE_LUA_VALUES &
    !bb. (bb:  LUA_TABLE => 
          (value_type(lua_value_from_table(bb)) = LUA_TTABLE &
           select_table(lua_value_from_table(bb)) = bb) ) &
    
    /* function to extract the (Lua) table value from a LUA_VALUE */
    lua_table_from_lua_value : TABLE_LUA_VALUES --> LUA_TABLE &
    !vv. (vv: TABLE_LUA_VALUES =>
        lua_table_from_lua_value(vv) = select_table(vv)) &
    
               
   
    /* the table contents stored in the heap are functions mapping some 
       LUA_VALUES to LUA_VALUES */
       TABLE_CONTENTS = LUA_VALUES +-> LUA_VALUES &
       
    /* function that gives a table cell for a specific key or nil, if the 
    key is not used */    
    access_table : TABLE_CONTENTS * LUA_VALUES --> LUA_VALUES &
    !(tt,kk). (tt: TABLE_CONTENTS & kk: LUA_VALUES =>
        ((kk  : dom(tt) => access_table(tt,kk) = tt(kk)) &
         (kk /: dom(tt) => access_table(tt,kk) = nil_lua_value))) 

END

    /*
ASSERTIONS
    
    !vv. (vv: TABLE_LUA_VALUES => 
        #tt. (tt : LUA_TABLE & select_table(vv) = tt & 
            lua_table_from_lua_value(vv) = tt))  &
    
    !tt.(tt: LUA_TABLE =>  lua_table_from_lua_value (lua_value_from_table(tt)) = tt) 
    
    lua_table_from_lua_value = TABLE_LUA_VALUES <| select_table &
    lua_table_from_lua_value = 
    {vv,tt | vv:TABLE_LUA_VALUES & tt: LUA_TABLE & select_table(vv) = tt} 
    
    */
    
    
    /* function to extract the (Lua) table values corresponding to a set of   
    LUA_VALUEs */
    /*lua_tables_from_lua_values : POW(TABLE_LUA_VALUES) --> POW(LUA_TABLE) &
    !ss. (ss: POW(TABLE_LUA_VALUES) => 
        lua_tables_from_lua_values(ss) = {tt | tt:LUA_TABLE & 
            #vv. (vv : ss & tt =  lua_table_from_lua_value(vv))}) & */
    
    /* a redundant definition of the same function */
    /* lua_tables_from_lua_values = 
    {sv,st | sv: POW(TABLE_LUA_VALUES) & st: POW(LUA_TABLE) & 
        !vv.(vv:sv => select_table(vv): st) &
        !tt.(tt:st => #vv. (vv:sv & select_table(vv) = tt))
        }  */
 