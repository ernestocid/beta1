/* Aux
 * Author: 
 * Creation date: 28/04/14
 */
MACHINE
    Aux
SEES
    /* all type information */
    BasicTypes,
    BasicTypeProjections,
    NumberADT,
    BooleanADT,
    NilADT

CONSTANTS
    convert_index
    
PROPERTIES
    /* function to convert acceptable indexes in their absolute values, returning zero or the value itself in the case of non-acceptable indexes. To be a constant, it cannot depend on the global values of stack, so they have to be passed as arguments to the function. 
    Order of arguments: stack_top, index
*/    
    convert_index : (NAT * INT) --> NAT &
    ! (actual_size, index). 
    (actual_size: NAT & index: INT =>
        (index > 0 => convert_index(actual_size, index) = index) &
        /* stack index (index : -stack_top..-1) */
        (index < 0 & -index <= actual_size  => 
            convert_index(actual_size, index) = actual_size - (-index) + 1) &
        /* non-acceptable index (0 or below -stack_top)*/
        ((index = 0 or -index > actual_size) =>
            				convert_index(actual_size, index) =  0))
     
ASSERTIONS
    
     ! (actual_size, index). 
     (actual_size: NAT & index: INT  => 
         (convert_index(actual_size, index) > actual_size => index > 0 ) )  &
     
     
     ! (actual_size, index). 
     (actual_size: NAT & index: NAT1 => 
         (convert_index(actual_size, index) = index ))  &
     
      ! (actual_size, index). 
     (actual_size: NAT & index: NAT => 
         (convert_index(actual_size, index) = index ))  &
     
      ! (actual_size, index). 
     (actual_size: NAT1 & index: -actual_size..-1 => 
         (convert_index(actual_size, index) : 1..actual_size ))  &
     
     
      ! (actual_size, index). 
     (actual_size: NAT1 & index: -actual_size..-1 => 
         (convert_index(actual_size, index) = actual_size - (-index) + 1)) &
     
     ! (actual_size, index). 
     (actual_size: NAT & index: -actual_size..-1 \/ NAT1 => 
         (convert_index(actual_size, index) : NAT1)) 
     

END
