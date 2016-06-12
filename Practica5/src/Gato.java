/* Clase que modela al juego de Gato en Java.
   Por simplicidad, el jugador 1 es las 'X' y el jugador 2 las 'O' */
import java.util.List;
import java.util.LinkedList;

public class Gato{
    private int[][] cuadricula; /* Diremos que una celda está vacía si 
				   contiene un cero, tiene una 'X' si 
				   contiene un 1, y tiene una 'O' si contiene 
				   un -1 */
    private boolean turno; /* true si le toca tirar al jugador 1, false si le 
			      toca al 2 */
    private boolean finalizado; /* Nos dice si el juego ya acabó */
    private int utilidad; /* 0 si el juego no ha terminado o fue un empate, 1 
			     si el jugador 1 ganó, -1 si el jugador 2 ganó */

    /* Constructor. Incializa la cuadricula  de 3x3 */
    public Gato(){
	this.cuadricula = new int[3][3];
	this.turno = true;
	this.finalizado = false;
	this.utilidad = 0;
    }
    
    /* Crea un gato dada una cuadrícula válida */
    public Gato(int[][] cuadricula){
	this.cuadricula = cuadricula;
	int suma = 0; /* La suma de los valores de la cuadrícula */
	for(int i = 0; i < 3; ++i){
	    for(int j = 0; j < 3; ++j){
		suma += cuadricula[i][j];
	    }
	}
	this.turno = (suma == 0);
	actualizaFinalizado();
    }

    /* Crea un gato dada una cuadrícula válida y un booleano para 
       representar al turno (optimización del constructo anterior) */
    public Gato(int[][] cuadricula, boolean turno){
	this.cuadricula = cuadricula;
	this.turno = turno;
	actualizaFinalizado();
    }
    
    /* Pone a quién le toca tirar */
    public void setTurno(boolean turno){
	this.turno = turno;
    }
    
    /* Regresa un boolean que representa qué jugador va a tirar */
    public boolean getTurno(){
	return this.turno;
    }

    /* Le dice al Gato si el juego ya está finalizado */
    public void setFinalizado(boolean finalizado){
	this.finalizado = finalizado;
    }

    /* Nos dice si el juego ya terminó */
    public boolean getFinalizado(){
	return this.finalizado;
    }
    
    /* Regresa la utilidad del Gato */
    public int getUtilidad(){
	return this.utilidad;
    }
    
    /* Regresa a la cuadrícula del Gato */
    public int[][] getCuadricula(){
	return this.cuadricula; 
    }
    
    /* Tira en la casilla (i, j) */
    public Gato tira(int i, int j){
	int[][] copia = new int[3][3]; /* Una copia de la cuadrícula */
	for(int k = 0; k < 3; ++k)
	    for(int l = 0; l < 3; ++l)
		copia[k][l] = cuadricula[k][l];
	if(turno)
	    copia[i][j] = 1;
	else
	    copia[i][j] = -1;
	Gato sucesor = new Gato(copia, !turno);
	sucesor.actualizaFinalizado();
	return sucesor;
    }
    
    /* Actualiza el valor de la variable 'finalizado' de acuerdo al juego */
    public void actualizaFinalizado(){
	if((cuadricula[0][0] != 0 && (cuadricula[0][0] == 
				      cuadricula[1][0] &&
				      cuadricula[1][0] ==
				      cuadricula[2][0])) ||  
	   (cuadricula[0][0] != 0 && (cuadricula[0][0] == 
				      cuadricula[0][1] && 
				      cuadricula[0][1] ==
				      cuadricula[0][2])) ||
	   (cuadricula[0][0] != 0 && (cuadricula[0][0] == 
				      cuadricula[1][1] &&
				      cuadricula[1][1] ==
				      cuadricula[2][2]))){
	    finalizado = true; 
	    utilidad = cuadricula[0][0];
	    return;
	}
	if((cuadricula[1][1] != 0 && (cuadricula[0][1] == 
				      cuadricula[1][1] && 
				      cuadricula[1][1] ==
				      cuadricula[2][1])) || 
	   (cuadricula[1][1] != 0 && (cuadricula[1][0] == 
				      cuadricula[1][1] &&
				      cuadricula[1][1] ==
				      cuadricula[1][2])) ||
	   (cuadricula[1][1] != 0 && (cuadricula[0][2] == 
				      cuadricula[1][1] &&
				      cuadricula[1][1] ==
				      cuadricula[2][0]))){
	    finalizado = true; 
	    utilidad = cuadricula[1][1];
	    return;
	}
	if((cuadricula[2][2] != 0 && (cuadricula[0][2] == 
				      cuadricula[1][2] &&
				      cuadricula[1][2] ==
				      cuadricula[2][2])) ||
	   (cuadricula[2][2] != 0 && (cuadricula[2][0] == 
				      cuadricula[2][1] && 
				      cuadricula[2][1] ==
				      cuadricula[2][2]))){
	    finalizado = true; 
	    utilidad = cuadricula[2][2];
	    return;
	}
	boolean lleno = true; /* Nos dice si el tablero ya se llenó */
	/* Si no sale con break, entonces sí está lleno el tablero */
	for(int i = 0; i < 3; ++i){
	    for(int j = 0; j < 3; ++j){
		if(cuadricula[i][j] == 0){
		    lleno = false;
		    break;
		}
	    }
	}
	utilidad = 0;
	finalizado = lleno;
    }
    
    /* Nos dice si es válido tirar en i, j */
    public boolean tiroValido(int i, int j){
	return cuadricula[i][j] == 0;
    }

    /* Nos regresa a los sucesores de un Gato */
    public List<Gato> sucesores(){
	List<Gato> sucesores = new LinkedList<>(); /* Los sucesores 
						      del Gato */
	if(!finalizado){
	    for(int i = 0; i < 3; ++i){
		for(int j = 0; j < 3; ++j){
		    if(tiroValido(i, j)){
			Gato sucesor = tira(i, j);
			if(!sucesores.contains(sucesor))
			    sucesores.add(sucesor);
		    }
		}
	    }
	}
	return sucesores;
    }
    
    /* Nos dice si dos Gatos son simétricos 
       (pienso copiar esto del código de la práctica 2) */
    @Override
    public boolean equals(Object obj){
	if (obj instanceof Gato){
	    Gato otro = (Gato) obj;
	    return esIgual(otro) ||
		esSimetricoDiagonalInvertida(otro) ||
		esSimetricoDiagonal(otro) ||
		esSimetricoVerticalmente(otro) ||
		esSimetricoHorizontalmente(otro) ||
		esSimetrico90(otro) ||
		esSimetrico180(otro) ||	
		esSimetrico270(otro);
	}
	return false;
    }
    
    /** Revisa si ambos gatos son exactamente el mismo. */
    boolean esIgual(Gato otro){
	int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	for(int i = 0; i < 3; i++){
	    for(int j = 0; j < 3; j++){
		if(cuadricula[i][j] != c[i][j]) return false;
	    }
	}
	return true;
    }
    
    /** Al reflejar el gato sobre la diagonal \ son iguales (ie traspuesta) */
    boolean esSimetricoDiagonalInvertida(Gato otro){
        int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	for(int i = 0; i < 3; i++){
	    for(int j = 0; j < 3; j++){
		if(cuadricula[j][i] != c[i][j]) return false;
	    }
	}
	return true;    
    }
    
    /** Al reflejar el gato sobre la diagonal / son iguales (ie traspuesta) */
    boolean esSimetricoDiagonal(Gato otro){
	int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	return (cuadricula[0][0] == c[2][2] && cuadricula[0][1] == c[1][2] && cuadricula[1][0] == c[2][1]
		&& cuadricula[0][2] == c[0][2] && cuadricula[1][1] == c[1][1] && cuadricula[2][0] == c[2][0]
		&& cuadricula[2][2] == c[0][0] && cuadricula[1][2] == c[0][1] && cuadricula[2][1] == c[1][0]);
    }
    
    /** Al reflejar el otro gato sobre la vertical son iguales */
    boolean esSimetricoVerticalmente(Gato otro){
	int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	for(int i = 0; i < 3; i++){
	    for(int j = 0; j < 3; j++){
		    if(cuadricula[j][i] != c[j][2-i]) return false;
	    }
	}
	return true;    
    }
    
    /** Al reflejar el otro gato sobre la horizontal son iguales */
    boolean esSimetricoHorizontalmente(Gato otro){
	int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	for(int i = 0; i < 3; i++){
	    for(int j = 0; j < 3; j++){
		if(cuadricula[j][i] != c[2-j][i]) return false;
	    }
	}
	return true;    
    }
    
    /** Rota el otro cuadricula 90° en la dirección de las manecillas del reloj. */
    boolean esSimetrico90(Gato otro){
	int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	return (cuadricula[0][0] == c[2][0] && cuadricula[0][1] == c[1][0] && cuadricula[0][2] == c[0][0]
		&& cuadricula[1][0] == c[2][1] && cuadricula[1][1] == c[1][1] && cuadricula[1][2] == c[0][1]
		&& cuadricula[2][0] == c[2][2] && cuadricula[2][1] == c[1][2] && cuadricula[2][2] == c[0][2]);
    }
    
    /** Rota el otro cuadricula 180° en la dirección de las manecillas del reloj. */
    boolean esSimetrico180(Gato otro){
	int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	for(int i = 0; i < 3; i++){
	    for(int j = 0; j < 3; j++){
		if(c[j][i] != cuadricula[2-j][2-i]) return false;
	    }
	}
	return true;            
    }
    
    /** Rota el otro cuadricula 270° en la dirección de las manecillas del reloj. */
    boolean esSimetrico270(Gato otro){
	int[][] c = otro.getCuadricula(); /* La cuadrícula del Gato a 
					     comparar */
	return (cuadricula[0][0] == c[0][2] && cuadricula[0][1] == c[1][2] && cuadricula[0][2] == c[2][2]
		&& cuadricula[1][0] == c[0][1] && cuadricula[1][1] == c[1][1] && cuadricula[1][2] == c[2][1]
		&& cuadricula[2][0] == c[0][0] && cuadricula[2][1] == c[1][0] && cuadricula[2][2] == c[2][0]);   
    } 
    /* Termina código copiado de la Dra. Verónica Arriola */
    
    /* Le resta a 'otroGato' a nuestro Gato, regresando la acción que llevó a 
       uno a volverse el otro. Sólo funciona si la diferencia es de una 
       casilla */
    public AccionGato resta(Gato otroGato){
	int[][] c = otroGato.getCuadricula(); /* La cuadrícula del Gato a 
						 restar */
	for(int i = 0; i < 3; ++i){
	    for(int j = 0; j < 3; ++j){
		if(cuadricula[i][j] != 0 && c[i][j] == 0)
		    return new AccionGato(i, j, !turno);
	    }
	}
	return null;
    }

    /* Imprime el tablero */
    @Override
    public String toString(){
	String tablero = ""; /* La representación en String del tablero */
	for(int j = 0; j < 3; ++j){
	    if(j > 0)
		tablero += "\n";
	    for(int i = 0; i < 3; ++i){ /* Quiero imprimir por filas, no 
					   por columnas */
		if(i > 0)
		    tablero += "    ";
		if(cuadricula[i][j] == 1) 
		    tablero += "X";
		else if(cuadricula[i][j] == 0)
		    tablero += "_";
		else
		    tablero += "O";
	    }
	}
	return tablero;
    }
}   
