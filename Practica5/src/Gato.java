/* Clase que modela al juego de Gato en Java.
   Por simplicidad, el jugador 1 es las 'X' y el jugador 2 las 'O' */
public class Gato{
    private int[][] cuadricula; /* Diremos que una celda está vacía si 
				   contiene un cero, tiene una 'X' si 
				   contiene un 1, y tiene una 'O' si contiene 
				   un -1 */
    private boolean turno; /* true si le toca tirar al jugador 1, false si le 
			      toca al 2 */
    private boolean finalizado; /* Nos dice si el juego ya acabó */
    
    /* Constructor. Incializa la cuadricula  de 3x3 */
    public Gato(){
	this.cuadricula = new int[3][3];
	this.turno = true;
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
    }
    
    /* Tira en la casilla (i, j) */
    public void tira(int i, int j){
	if(turno)
	    cuadricula[i][j] = 1;
	else
	    cuadricula[i][j] = -1;
	actualizaFinalizado();
	turno = !turno;
    }
    
    /* Actualiza el valor de la variable 'finalizado' de acuerdo al juego */
    public void actualizaFinalizado(){
	finalizado = (cuadricula[0][0] != 0 && (cuadricula[0][0] == 
						cuadricula[1][0] == 
						cuadricula[2][0])) || 
	    (cuadricula[0][1] != 0 && (cuadricula[0][1] == 
				       cuadricula[1][1] == 
				       cuadricula[2][1])) || 
	    (cuadricula[0][2] != 0 && (cuadricula[0][2] == 
				       cuadricula[1][2] == 
				       cuadricula[2][2])) ||
	    (cuadricula[0][0] != 0 && (cuadricula[0][0] == 
				       cuadricula[0][1] == 
				       cuadricula[0][2])) || 
	    (cuadricula[1][0] != 0 && (cuadricula[1][0] == 
				       cuadricula[1][1] == 
				       cuadricula[1][2])) ||
	    (cuadricula[2][0] != 0 && (cuadricula[2][0] == 
				       cuadricula[2][1] == 
				       cuadricula[2][2])) || 
	    (cuadricula[0][0] != 0 && (cuadricula[0][0] == 
				       cuadricula[1][1] == 
				       cuadricula[2][2])) || 
	    (cuadricula[0][2] != 0 && (cuadricula[0][2] == 
				       cuadricula[1][1] == 
				       cuadricula[2][0]));
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
	finalizado |= lleno;
    }
    
    /* Nos dice si es válido tirar en i, j */
    public boolean tiroValido(int i, int j){
	return cuadricula[i][j] == 0;
    }
}
