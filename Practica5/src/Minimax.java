/* Clase que representa al algoritmo minimax en Java */
import java.lang.Math;

public class Minimax{
    private Gato gato; /* El juego actual de gato (En el futuro, se podría 
			  extender a un juego genérico) */
    
    /* Constructor */
    public Minimax(Gato gato){
	this.gato = gato;
    }

    /* Nos dice qué decisión nos conviene tomar */
    public AccionGato decisionMinimax(){
	int v = asignarValor(gato); /* El valor del Gato */
	/* Para todos los tiros posibles... */
	for(Gato sucesor: gato.sucesores()){
	    int valor = asignarValor(sucesor); /* El valor del sucesor */
	    if(valor == v){
		System.out.println(sucesor);
		System.out.println("\n");
		AccionGato a = sucesor.resta(gato); /* La acción que 
						       realizamos para llegar 
						       al sucesor */
		return a;
	    }
	}
	return null;
    }
    
    /* Regresa la utilidad del Gato dado.
     La utilidad será 1 si gana el jugador 1, 0 si empatan y -1 si gana el 
     jugador 2 */
    public int asignarValor(Gato estado){
	int v; /* El valor a regresar */
	if(estado.getFinalizado())
	    return estado.getUtilidad();
	if(estado.getTurno()){ /* Jugador 1 */
	    v = -1; 
	    for(Gato sucesor: estado.sucesores())
		v = Math.max(v, asignarValor(sucesor));
      	}else{ /* Jugador 2 */
	    v = 1;
	    for(Gato sucesor: estado.sucesores())
		v = Math.min(v, asignarValor(sucesor));
	}
	return v;
    }

    public static void main(String args[]){
	int[][] tablero = new int[3][3];
	tablero[0][0] = 1;
	tablero[1][1] = 1;
	tablero[0][2] = -1;
	Gato g = new Gato(tablero);
	System.out.println(g);
	System.out.println("\n");
	Minimax m = new Minimax(g);
	AccionGato ag = m.decisionMinimax();
	System.out.println(ag);
    }
    
}
