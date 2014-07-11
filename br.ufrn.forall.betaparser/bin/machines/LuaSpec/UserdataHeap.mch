/* UserdataHeap
 * Author: Anamaria
 * Creation date: Mon Oct 22 2012
 */
MACHINE
    UserdataHeap
    
SEES 
    BasicTypes,
    UserdataADT,
    NilADT
    
VARIABLES
    userdata_heap
    
INVARIANT
    userdata_heap : LUA_USERDATA +-> USERDATA_CONTENTS
       
INITIALISATION
    userdata_heap := {}
    
OPERATIONS
    /* creates a new userdata with given size and adddress and no assotiated 
    table (ident |-> (udsize,udaddress,nil)) on the heap.
    */
    new_userdata (ident, udsize, udaddress) =
    PRE
        ident : LUA_USERDATA - dom(userdata_heap) &
        udsize : NAT &
        udaddress : INT
    THEN
        userdata_heap(ident) := (udsize |-> udaddress |-> nil_lua_value)
    END;
    
    
    /* updates the userdata refered to by ident with a new table or nil */
    update_userdata (ident, t_or_nil) =
    PRE
        ident : dom(userdata_heap) &
        t_or_nil : TABLE_LUA_VALUES \/ NIL_LUA_VALUES
    THEN
        userdata_heap(ident) := (
            udsize_of(userdata_heap(ident)) |->
            udaddress_of(userdata_heap(ident)) |-> 
            t_or_nil)
    END;
    
    /* updates the userdata heap */
    update_userdata_heap (newuh) =
    PRE
        newuh : LUA_USERDATA +-> USERDATA_CONTENTS
    THEN
        userdata_heap := newuh
    END


END

        /*lua_userdatas_from_lua_values(ran(ff |> TABLE_LUA_VALUES)) <:
        			dom(userdata_heap) & */
        /*udtable: TABLE_LUA_VALUES =>  
        lua_table_from_lua_value(udtable): dom(table_heap)) */
        

