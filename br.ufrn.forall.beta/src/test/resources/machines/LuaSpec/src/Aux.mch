
MACHINE
    Aux
SEES
    BasicTypes ,
    BasicTypeProjections ,
    NumberADT ,
    BooleanADT ,
    NilADT

CONCRETE_CONSTANTS
    convert_index

PROPERTIES

    convert_index : ( NAT * INT ) --> NAT &
    ! ( actual_size , index ) .
    ( actual_size : NAT & index : INT =>
        ( index > 0 => convert_index ( actual_size , index ) = index ) &
        ( index < 0 & - index <= actual_size =>
            convert_index ( actual_size , index ) = actual_size - ( - index ) + 1 ) &
        ( ( index = 0 or - index > actual_size ) =>
                                            convert_index ( actual_size , index ) = 0 ) )

ASSERTIONS

     ! ( actual_size , index ) .
     ( actual_size : NAT & index : INT =>
         ( convert_index ( actual_size , index ) > actual_size => index > 0 ) ) &


     ! ( actual_size , index ) .
     ( actual_size : NAT & index : NAT1 =>
         ( convert_index ( actual_size , index ) = index ) ) &

      ! ( actual_size , index ) .
     ( actual_size : NAT & index : NAT =>
         ( convert_index ( actual_size , index ) = index ) ) &

      ! ( actual_size , index ) .
     ( actual_size : NAT1 & index : - actual_size .. - 1 =>
         ( convert_index ( actual_size , index ) : 1 .. actual_size ) ) &


      ! ( actual_size , index ) .
     ( actual_size : NAT1 & index : - actual_size .. - 1 =>
         ( convert_index ( actual_size , index ) = actual_size - ( - index ) + 1 ) ) &

     ! ( actual_size , index ) .
     ( actual_size : NAT & index : - actual_size .. - 1 \/ NAT1 =>
         ( convert_index ( actual_size , index ) : NAT1 ) )


END
