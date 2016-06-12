/* Clase que representa al algoritmo minimax en Java */
public class Minimax{
    private Gato gato; /* El juego actual de gato (En el futuro, se podría 
			  extender a un juego genérico) */
    
    /* Constructor */
    public Minimax(Gato gato){
	this.gato = gato;
    }
    
    public AccionGato decisionMinimax(){
	int v = asignarValor(gato); /* El valor del Gato */
	/* Para todos los tiros posibles... */
	for(Gato sucesor: gato.sucesores()){
	    int valor = asignarValor(sucesor); /* El valor del sucesor */
	    if(valor == v){
		AccionGato a = sucesor.resta(gato); /* La acción que 
						       realizamos para llegar 
						       al sucesor */
		return a;
	    }
	}
    }
}
