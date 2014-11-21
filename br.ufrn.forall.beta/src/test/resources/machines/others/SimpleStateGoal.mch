MACHINE SimpleStateGoal

VARIABLES counter, limit

INVARIANT
  counter : INTEGER &
  limit : INTEGER
 
INITIALISATION counter := 0 || limit := 5

OPERATIONS
  increaseCounter(inc) = 
    PRE
      inc : INTEGER &
      inc > 0 &
      counter + inc <= limit 
    THEN 
       counter := counter + inc
    END /*;

  test1(t_inc, t_counter, t_limit) = 
    PRE
      t_counter : INTEGER & t_limit : INTEGER & t_inc : INTEGER & t_inc > 0 & t_counter + t_inc <= t_limit
    THEN skip
    END;

  test2(t_inc, t_counter, t_limit) = 
    PRE
      t_counter : INTEGER & t_limit : INTEGER & t_inc : INTEGER & t_inc > 0 & t_counter + t_inc = t_limit
    THEN skip
    END;

  test3(t_inc, t_counter, t_limit) = 
    PRE
      t_counter : INTEGER & t_limit : INTEGER & t_inc : INTEGER & t_inc > 0 & t_counter + t_inc > t_limit
    THEN skip
    END;

 test4(t_inc, t_counter, t_limit) = 
    PRE
      t_counter : INTEGER & t_limit : INTEGER & t_inc : INTEGER & t_inc = 0 & t_counter + t_inc <= t_limit
    THEN skip
    END;

 test5(t_inc, t_counter, t_limit) = 
    PRE
      t_counter : INTEGER & t_limit : INTEGER & t_inc : INTEGER & t_inc < 0 & t_counter + t_inc <= t_limit
    THEN skip
    END */
END

