MACHINE TestsForOp_ajouter_exemplaire_From_bibliotheque

SETS
Ens_abonnes = {abonne1};
Ens_livres = {livre1};
Ens_exemplaires

OPERATIONS
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test1(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__livre : Ens_livres & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & (Ens_exemplaires - i__Exemplaires) /= {} & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & i__livre : i__Livres /* POSITIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test2(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__livre : Ens_livres & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & (Ens_exemplaires - i__Exemplaires) /= {} & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & not(i__livre : i__Livres) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test3(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__livre : Ens_livres & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & not((Ens_exemplaires - i__Exemplaires) /= {}) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & i__livre : i__Livres /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test4(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & i__livre : Ens_livres & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & not((Ens_exemplaires - i__Exemplaires) /= {}) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & not(i__livre : i__Livres) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test5(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & not(i__livre : Ens_livres) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & (Ens_exemplaires - i__Exemplaires) /= {} & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & i__livre : i__Livres /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test6(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & not(i__livre : Ens_livres) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & (Ens_exemplaires - i__Exemplaires) /= {} & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & not(i__livre : i__Livres) /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test7(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & not(i__livre : Ens_livres) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & not((Ens_exemplaires - i__Exemplaires) /= {}) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & i__livre : i__Livres /* NEGATIVE */
THEN
skip
END
;
/* Equivalence Class test data for ajouter_exemplaire */
ajouter_exemplaire_test8(
i__Abonnes,
i__Exemplaires,
i__est_emprunte_par,
i__est_exemplaire_de,
i__livre,
i__Livres
) =
PRE
i__Abonnes <: Ens_abonnes & !(abonne).((abonne : i__Abonnes) => (card(dom((i__est_emprunte_par |> {abonne}))) <= 3)) & !(abonne,ex1,ex2).((abonne : i__Abonnes & ex1 : i__Exemplaires & ex2 : i__Exemplaires & ex1 /= ex2 & ex1 : dom((i__est_emprunte_par |> {abonne})) & ex2 : dom((i__est_emprunte_par |> {abonne}))) => (i__est_exemplaire_de(ex1) /= i__est_exemplaire_de(ex2))) & not(i__livre : Ens_livres) & i__Livres <: Ens_livres & i__est_exemplaire_de : (i__Exemplaires --> i__Livres) & not((Ens_exemplaires - i__Exemplaires) /= {}) & i__Exemplaires <: Ens_exemplaires & i__est_emprunte_par : (i__Exemplaires +-> i__Abonnes) & not(i__livre : i__Livres) /* NEGATIVE */
THEN
skip
END
END