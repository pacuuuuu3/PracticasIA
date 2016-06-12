/* Ejercicio 3 */
/* Predicados de color */
color(verde).
color(rojo).
color(azul).
color(amarillo).
/* Predicado de colorear */
colorea(Reg1, Reg2, Reg3, Reg4, Reg5, Reg6):-
	/* Que todos sean colores */
	color(Reg1), color(Reg2), color(Reg3), color(Reg4), color(Reg5),
	color(Reg6),
	/* Aquí ponemos cuáles deben ser distintos */
	(Reg1 \== Reg4), (Reg1 \== Reg2), (Reg1 \== Reg3), (Reg1 \== Reg5),
	(Reg2 \== Reg3),
	(Reg3 \== Reg5),
	(Reg4 \== Reg5), (Reg4 \== Reg6).
	