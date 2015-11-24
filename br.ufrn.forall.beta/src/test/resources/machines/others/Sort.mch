MACHINE
    Sort
VARIABLES
   vector,descending_sort
INVARIANT
   vector : 0..9 --> 0..10 & 
   !(ii, jj).(ii : 0..9 & jj : 0..9 => (ii /= jj => vector(ii) /= vector(jj))) &
   descending_sort : BOOL &
   (descending_sort = TRUE => !ii.(ii : 0..8 => vector(ii)>vector(ii+1)))
INITIALISATION
   ANY 
       vec 
   WHERE
       vec : 0..9 --> 0..10 & 
       !(ii, jj).(ii : 0..9 & jj : 0..9 & ii /= jj => vec(ii) /= vec(jj)) &
       not(!ii.(ii : 0..8 => vec(ii)>vec(ii+1)))
   THEN
       vector := vec
   END ||
   descending_sort := FALSE
   
OPERATIONS
   op_sort =
   PRE 
	descending_sort = FALSE
   THEN
   ANY sorted_vector WHERE
    sorted_vector : 0..9 --> 0..10 &
    !ii.(ii : 0..8 => sorted_vector(ii)>sorted_vector(ii+1)) &
    ran(sorted_vector) = ran(vector)
   THEN
    vector := sorted_vector|| descending_sort := TRUE
   END
   END
END
