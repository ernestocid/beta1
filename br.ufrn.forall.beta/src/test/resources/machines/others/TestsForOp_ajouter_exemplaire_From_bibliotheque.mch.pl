spec_trans(root,'$initialise_machine',0).
spec_trans(0,'ajouter_exemplaire_test1({},{},{},{},livre1,{livre1})',0).
spec_trans(0,'ajouter_exemplaire_test2({},{},{},{},livre1,{})',0).
spec_trans(0,'ajouter_exemplaire_test3({},{Ens_exemplaires1,Ens_exemplaires2},{},{(Ens_exemplaires1|->livre1),(Ens_exemplaires2|->livre1)},livre1,{livre1})',0).
spec_max_reached_for_node(root).
spec_max_reached_for_node(0).
spec_not_all_transitions_added(_) :-
        fail.
spec_completely_explored :-
        fail.
