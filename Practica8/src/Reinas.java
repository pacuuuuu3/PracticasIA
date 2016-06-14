/* Modela el problema de las 8 (o n) reinas */
import java.util.ArrayList;

public class Reinas{
    private Poblacion poblacion; /* La población de soluciones en el 
				    algoritmo genético */
    private int generaciones; /* El límite de generaciones del algoritmo */
    private int elite; /* El número de elitismo que usaremos */
    private int tamanoPoblacion; /* El tamaño de la población */
    private double mutacion; /* Probabilidad de mutación de un individuo */
    private int generacionOptima; /* Entero que nos dice en qué generación se 
				     encontró la solución óptima */
    
    /**
     * Crea una nueva instancia del algoritmo genético.
     * @param tamanoPoblacion - Tamaño de la población.
     * @param tamanoTablero - Tamaño del lado del tablero.
     * @param generaciones - Número máximo de generaciones para correr el 
     algoritmo.
     * @param elite - Número de elite que queremos. 
     * @param mutacion - Probabilidad de mutación. */
    public Reinas(int tamanoPoblacion, int tamanoTablero, int generaciones,
		  int elite, double mutacion){
	this.tamanoPoblacion = tamanoPoblacion;
	this.poblacion = new Poblacion(tamanoPoblacion, tamanoTablero);
	this.generaciones = generaciones;
	this.elite = elite;
	this.mutacion = mutacion;
    }

    /* Intenta encontrar la mejor solución al problema de las reinas mediante 
       un algoritmo genético */
    public Tablero mejorIndividuo(){
	for(int i = 0; i < generaciones; ++i){ /* Mientras no hayamos pasado 
						  el límite de 
						  generaciones... */
	    Tablero optimo = poblacion.optimoEncontrado(); /* El tablero 
							      óptimo, si ya 
							      lo encontramos */
	    if(optimo != null){
		generacionOptima = i + 1;
		return optimo;
	    }
	    ArrayList<Tablero> nuevaPoblacion = poblacion.elitismo(elite); /* La población de la siguiente generación */
	    for(int j = elite; j < tamanoPoblacion; ++j){
		Tablero individuo1 = poblacion.seleccionRuleta(); /* Se selecciona al 'padre' de manera aleatoria */
		Tablero individuo2 = poblacion.seleccionRuleta(); /* Se selecciona a la 'madre de manera aleatoria */
		Tablero hijo = individuo1.recombina(individuo2); /* Se 
								    recombina 
								 */
		hijo.muta(mutacion);
		nuevaPoblacion.add(hijo);
	    }
	    poblacion = new Poblacion(nuevaPoblacion);
	}
	return poblacion.mejorIndividuo();
    }
    
    /* Main para probar el programa */
    public static void main(String args[]){
	for(int i = 50; i <= 500; i+=50){
	    Reinas prueba = new Reinas(50, 8, i, 1, 0.2);
	    Tablero mejor = prueba.mejorIndividuo();
	    System.out.println("Mejor solución en iteración " + i + " es: " 
			      + mejor);
	}
	Reinas prueba = new Reinas(50, 16, Integer.MAX_VALUE, 1, 0.2);
	Tablero mejor = prueba.mejorIndividuo();
	System.out.println("Se encontró la solución óptima en la " + 
			   "generación: " + prueba.generacionOptima);
	System.out.println(mejor);
	System.out.println("Programa finalizado");
    }
    
}
