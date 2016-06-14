/* Clase para encapsular una población. No era muy necesaria, pero me parece 
   más limpio ponerla. */
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Poblacion{
    
    private ArrayList<Tablero> miembros; /* Los miembros de la población */
    private double fitness; /* Suma de las aptitudes de los distintos individuos 
			    de la población */
    
    /* Constructor */
    public Poblacion(ArrayList<Tablero> miembros){
	this.miembros = miembros;
	this.fitness = 0.0;
	for(Tablero t: miembros)
	    this.fitness += t.getFitness();
    }

    /* Crea una Poblacion aleatoria a partir de un tamaño de Poblacion y un 
       tamaño de Tablero */
    public Poblacion(int tamanoPoblacion, int tamanoTablero){
	this.miembros = new ArrayList<>();
	for(int i = 0; i < tamanoPoblacion; ++i)
	    miembros.add(new Tablero(tamanoTablero));
	this.fitness = 0.0;
	for(Tablero t: miembros)
	    fitness += t.getFitness();
    }
    
    /* Regresa el elemento óptimo si existe. En otro caso, regresa null */
    public Tablero optimoEncontrado(){
	for(Tablero miembro: miembros)
	    if(miembro.getFitness() == 1)
		return miembro;
	return null;
    }

    /* Regresa los mejores n elementos de la población */
    public ArrayList<Tablero> elitismo(int n){
	ArrayList<Tablero> elite = new ArrayList<>(); /* La élite que 
							 regresaremos */
	Collections.sort(miembros);
	int i = 0; /* Contador */
	for(Tablero miembro: miembros){
	    if(i == n)
		break;
	    i++;
	    elite.add(miembro);
	}
	return elite;
    }
    
    /* Regresa al mejor individuo de la población */
    public Tablero mejorIndividuo(){
	double max = 0; /* La máxima aptitud */
	Tablero mejor = null; /* El mejor individuo */
	for(Tablero individuo: miembros){
	    double aptitud = individuo.getFitness(); /* La aptitud del 
						     individuo */
	    if(aptitud > max){
		max = aptitud;
		mejor = individuo;
	    }
	}
	return mejor;
    }

    /* Selecciona un individuo para reproducirse */
    public Tablero seleccionRuleta(){
	double ruleta = 0.0; /* Valor con el que iremos viendo en dónde cayó 
				la ruleta */
	double aleatorio = Math.random(); /* Número aleatorio entre 0 y 1 */
	for(Tablero miembro: miembros){
	    ruleta += (miembro.getFitness() / fitness);
	    if(aleatorio <= ruleta) /* Si el número cae en el rango dado, le 
				       toca salir a este miembro */
		return miembro;
	}
	return null; /* Esto nunca pasará */
    }
}
