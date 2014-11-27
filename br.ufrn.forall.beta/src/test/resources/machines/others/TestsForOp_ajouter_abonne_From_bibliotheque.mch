MACHINE TestsForOp_ajouter_abonne_From_bibliotheque

SETS
Ens_abonnes = {abonne1};
Ens_livres = {livre1};
Ens_exemplaires

OPERATIONS
/* Equivalence Class test data for ajouter_abonne */
ajouter_abonne_test1(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__abonne,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(i__abonne).((i__abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {i__abonne}))) <= 3)) & !(i__abonne,ex1,ex2).((i__abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {i__abonne})) & ex2 : dom((i__est_emprunte_par |> {i__abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & i__abonne : Ens_abonnes & i__abonne /: i__Abonnes /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_abonne */
ajouter_abonne_test2(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__abonne,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(i__abonne).((i__abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {i__abonne}))) <= 3)) & !(i__abonne,ex1,ex2).((i__abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {i__abonne})) & ex2 : dom((i__est_emprunte_par |> {i__abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & i__abonne : Ens_abonnes & not(i__abonne /: i__Abonnes) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_abonne */
ajouter_abonne_test3(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__abonne,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(i__abonne).((i__abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {i__abonne}))) <= 3)) & !(i__abonne,ex1,ex2).((i__abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {i__abonne})) & ex2 : dom((i__est_emprunte_par |> {i__abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & not(i__abonne : Ens_abonnes) & i__abonne /: i__Abonnes /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_abonne */
ajouter_abonne_test4(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__abonne,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(i__abonne).((i__abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {i__abonne}))) <= 3)) & !(i__abonne,ex1,ex2).((i__abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {i__abonne})) & ex2 : dom((i__est_emprunte_par |> {i__abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & not(i__abonne : Ens_abonnes) & not(i__abonne /: i__Abonnes) /* NEGATIVE */
THEN
skip
END
END