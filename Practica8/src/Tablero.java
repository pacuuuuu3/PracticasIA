/* Representa un tablero en el problema de las 8 reinas */
import java.lang.Math;
import java.lang.Comparable;
import java.text.DecimalFormat;

public class Tablero implements Comparable<Tablero>{
    private int[] acomodo; /* Nos dice cómo están acomodadas las reinas en el 
			      tablero. */
    private double fitness; /* La aptitud del tablero en el algoritmo 
			       genético */
    /* Se construye el Tablero a partir de un tamaño */
    public Tablero(int tamano){
	this.acomodo = new int[tamano];
	for(int i = 0; i < tamano; ++i)
	    acomodo[i] = 1 + (int)(Math.random() * tamano); /* Se llena aleatoriamente */
	calculaFitness();
    }
    
    
    /* Construye un tablero para el problema de las 8 reinas */
    public Tablero(int[] acomodo){
	this.acomodo = acomodo;
	calculaFitness();
    }
    
    /* Muta un tablero de acuerdo a la probabilidad dada */
    public void muta(double probabilidad){
	int posibilidades = acomodo.length; /* Las posibles posiciones de una 
					     reina en el tablero */
	for(int i = 0; i < posibilidades; ++i){
	    double proba = Math.random(); /* Número aleatorio que nos dirá si 
					     hay que modificar o no */
	    if(proba <= probabilidad){ /* Se modifica */
		int aleatorio = (int)(Math.random() * posibilidades); /* Número aleatorio entre 0 y (posibilidades - 1) */
		aleatorio++; /* Se le suma 1 para que nos de un número entre 1
				y posibilidades */ 
		acomodo[i] = aleatorio;
	    }
	}
	calculaFitness();
    }
    
    /* Regresa el acomodo del tablero */
    public int[] getAcomodo(){
	return this.acomodo;
    }

    /* Regresa el fitness del tablero */
    public double getFitness(){
	return this.fitness;
    } 

    /* Calcula el fitness del individuo */
    public void calculaFitness(){
	/* Primero, debemos calcular el número de ataques 
	   Afortunadamente, no hay ataques verticales */
	int ataques = 0; /* El número de ataques */
	for(int i = 0; i < acomodo.length; ++i){
	    for(int j = i+1; j < acomodo.length; ++j){
		if(acomodo[i] == acomodo[j])
		    ataques++;
		if(Math.abs(acomodo[i] - acomodo[j]) == (j-i))
		    ataques++;
	    }
	}
	this.fitness = 1.0/((0.1)*ataques+1); /* El máximo valor de fitness 
						   será 1. El mínimo tiende a 
						   0 */
    }
    
    /* Se compara el fitness de los Tableros 
       Tuve que implementarlo al revés (entre mayor fitness, menor el 
       elemento) para facilitarme el trabajo al sacar la élite de una 
       Poblacion */
    @Override
	public int compareTo(Tablero o){
	double otraFitness = o.getFitness(); /* La fitness del Tablero a 
						comparar */
	if(this.fitness > otraFitness)
	    return -1;
	else if(this.fitness == otraFitness)
	    return 0;
	return 1;
    }
    
    /* Combina nuestro Tablero con 'otro' */
    public Tablero recombina(Tablero otro){
	int tamano = this.acomodo.length; /* Tamaño del tablero */
	int corte = (int)(Math.random() * (tamano + 1)); /* Número aleatorio 
							    entre 0 y 
							     tamano */
	int[] acomodoOtro = otro.getAcomodo(); /* Acomodo del otro tablero */
	int[] acomodoHijo = new int[tamano]; /* El acomodo del tablero hijo */
	int contador = 0; /* Un contador */
	for(; contador < corte; ++contador)
	    acomodoHijo[contador] = this.acomodo[contador];
	for(; contador < tamano; ++contador)
	    acomodoHijo[contador] = acomodoOtro[contador];
	Tablero hijo = new Tablero(acomodoHijo);
	return hijo;
    }
    
    /* Imprime al Tablero */
    @Override
	public String toString(){
	String aImprimir = "["; /* La cadena a imprimir */
	for(int reina: this.acomodo)
	    aImprimir += reina + ", ";
	aImprimir = aImprimir.substring(0, aImprimir.length() -2);
	aImprimir += "]  |  fitness: ";
	DecimalFormat df = new DecimalFormat("#.##"); /* Queremos imprimir 
							   sólo hasta 2 
							   lugares después 
							   del punto decimal */
	aImprimir += df.format(fitness); 
	return aImprimir;
    }
}
