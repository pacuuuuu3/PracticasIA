/* Clase que representa una acción en el juego de gato */
public class AccionGato{
    private int i, j; /* Coordenadas del tiro */
    boolean tiro; /* Nos dice si se tiró una 'X'(true) o una 'O' (false) */

    /* Constructor */
    public AccionGato(int i, int j, boolean tiro){
	this.i = i;
	this.j = j;
	this.tiro = tiro;
    }
    
    /* Imprime la acción */
    @Override
    public String toString(){
	if(tiro)
	    return "X    " + i + "    " + j;
	else
	    return "O    " + i + "    " + j;
    }
}
