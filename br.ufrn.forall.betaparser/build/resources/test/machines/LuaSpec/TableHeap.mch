/* TableHeap
 * Author: Anamaria
 * Creation date: Wed Aug 15 2012
 */
MACHINE
    TableHeap
    
SEES 
    BasicTypes,
    TableADT
    
VARIABLES
    table_heap
    
INVARIANT
    table_heap : LUA_TABLE +-> TABLE_CONTENTS 
       
INITIALISATION
    table_heap := {}
    
OPERATIONS
   /* creates a new table ident |-> ff on the heap. 
    */
    new_table (ident, ff) =
    PRE
        ident : LUA_TABLE - dom(table_heap) &
        ff : TABLE_CONTENTS 
    THEN
        table_heap(ident) := ff
    END; 
    
    /* updates the table refered to by ident to ident |-> ff on the heap. 
    */
    update_table (ident, ff) =
    PRE
        ident : dom(table_heap) &
        ff : TABLE_CONTENTS 
    THEN
        table_heap(ident) := ff
    END;
    
    /* updates the table heap */
    update_table_heap (newth) =
    PRE
        newth : LUA_TABLE +-> TABLE_CONTENTS 
    THEN
        table_heap := newth
    END
    
    
    /* creates a new table  ident |-> ff on the heap, respecting that any 
    tables on any cell of the current table must already exist on the heap. 
    Its reference ident is any free table reference on the heap */
    /* !!! por enquanto não foi necessária
    ret <-- any_new_table (ff) =
    PRE
        LUA_TABLE - dom(table_heap) /= {} &
        ff : TABLE_CONTENTS &
        lua_tables_from_lua_values(ran(ff |> TABLE_LUA_VALUES)) <:
                                                           dom(table_heap)
    THEN
        ANY
            ident
        WHERE
            ident : LUA_TABLE - dom(table_heap)
        THEN
            table_heap(ident) := ff||
            ret := ident
        END
    END;
    */
    
    /* !!! por enquanto não foi necessária
    ret <-- get_free_lua_table =
    PRE
        dom(table_heap) <<: LUA_TABLE
    THEN
        ret :: LUA_TABLE - dom(table_heap)
    END;
    */
    
     
    /* !!! por enquanto não foi necessária
    ret <-- get_table (ident) =
    PRE
        ident : dom(table_heap)
    THEN
        ret := table_heap(ident)
    END
    */
    
END
