/* Borra un elemento de una lista. No sé si ya está implementado, pero es
   fácil de hacer */
remove([], _, []).
remove(L, Elem, L1):-
	append(L2, L3, L), (L3 = [Elem|L4]), append(L2, L4, L1).
/* Definimos el predicado */
/* Caso 1: Si la cantidad es 0, terminamos */
cantex(_, 0).
cantex(Monedas, Cantidad):-
	/*Caso 2: partimos la cantidad y resolvemos recursivamente */
	member(X, Monedas), (Y is Cantidad - X),
	remove(Monedas, X, Monedas2), cantex(Monedas2, Y). 