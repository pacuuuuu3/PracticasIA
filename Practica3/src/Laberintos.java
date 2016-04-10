import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.util.Stack;

/* Clase para recorrido de laberintos */
public class Laberintos extends PApplet {

    PFont fuente;  // Fuente para mostrar texto en pantalla
    int alto = 10;         // Altura (en celdas) de la cuadricula.
    int ancho = 10;        // Anchura (en celdas) de la cuadricula.
    int celda = 50;          /* Tamanio de cada celda cuadrada (en 
			       pixeles). */
    ModeloLaberintos modelo;  // El objeto que representa el modelo del laberinto.
   
    /* Configuración de la interfaz de Processing */
    public void settings() {
        size( ancho*celda, (alto*celda)+32);
    }

    @Override
    public void setup() {
        background(50);
        fuente = createFont("Arial",12,true);
	modelo = new ModeloLaberintos(ancho, alto, celda);
    }
    
    /* Pintar la cuadrícula */
    @Override
    public void draw() {
	for(int i = 0; i < alto; i++)
	    for(int j = 0; j < ancho; j++){
		if(modelo.done)
		    fill(255);
		else if (modelo.posX == j && modelo.posY == i)
		    fill(239, 15, 15);
		else if(modelo.mundo[i][j].visitada)
		    fill(4, 4, 128);
		else
		    fill(255);
		rect(j*modelo.tamanio, i*modelo.tamanio, 
		     modelo.tamanio, modelo.tamanio);
		if (modelo.mundo[i][j].paredIzq) {
		    stroke(0);		    
		    line(j*modelo.tamanio, i*modelo.tamanio, 
			 j*modelo.tamanio, ((i+1)*modelo.tamanio));
		}
		if (modelo.mundo[i][j].paredArriba) {
		    stroke(0);
		    line(j*modelo.tamanio, i*modelo.tamanio, 
			 ((j+1)*modelo.tamanio), i*modelo.tamanio);
		}
		if (modelo.mundo[i][j].paredDer) {
		    stroke(0);
		    line((j*modelo.tamanio) + modelo.tamanio, 
			 i*modelo.tamanio, (j+1)*modelo.tamanio, 
			 (((i+1)*modelo.tamanio)));
		}
		if (modelo.mundo[i][j].paredAbajo) {
		    stroke(0);
		    line(j*modelo.tamanio, (i*modelo.tamanio) + 
			 modelo.tamanio, ((j+1)*modelo.tamanio), 
			 ((i+1)*modelo.tamanio));
		}
		stroke(255);
	    }
	/* Pintar informacion del modelo en la parte inferior de la 
	   ventana. */
	fill(50);
	rect(0, alto*celda, (ancho*celda), 32);
	fill(255);
	textFont(fuente,10);
	text("Cuadricula: " + modelo.ancho + " x " + modelo.alto, 5, 
	     (alto*celda)+12);
	text("Generacion " + modelo.generacion, 128, (alto*celda)+12);
        modelo.paso();
    }
    
    
    
    // --- Clase Celda --
    /* Abstrae una Celda del laberinto */
    public class Celda{
	int celdaX, celdaY; /* Posición de la celda */
	boolean visitada; /* Nos dice si la celda ya fue visitada */
	boolean queda; /* Nos dice si quedan posiciones en las cuales 
			  movernos estando parados en la celda */
	boolean paredArriba = true; /* Nos dice si la celda tiene 
				       pared arriba */
	boolean paredAbajo = true; /* Nos dice si la celda tiene pared
				      abajo */
	boolean paredIzq = true; /* Nos dice si la celda tiene pared 
				    a la izquierda */
	boolean paredDer = true; /* Nos dice si la celda tiene pared a 
				    la derecha */
	
	/* Constructor de una Celda */
	public Celda(int celdaY, int celdaX){
	    this.celdaX = celdaX;
	    this.celdaY = celdaY;
	    this.visitada = false;
	    this.queda = true;
	}	
    } 
    
    // --- Clase ModeloLaberintos ---
    /**
     * Representa un laberinto
     */
    class ModeloLaberintos{
	int ancho, alto;  // Tamaño de celdas a lo largo y ancho de la cuadrícula.
	int tamanio;  // Tamaño en pixeles de cada celda.
	int generacion;  // Conteo de generaciones (cantidad de iteraciones) del modelo.
	Celda[][] mundo;  // Mundo de celdas del laberinto.
	int posX, posY; /* Posiciones en X y Y en que estamos parados. */
	Random rd = new Random(); /* Variable para generar numeros aleatorios */
	Stack<Celda> pila = new Stack<>(); /* La pila de celdas (se utiliza en el algoritmo) */
	Celda actual; /* La celda en la que estamos parados */
	boolean done; /* Nos dice si ya se terminó de pintar el laberinto */
	
	/** Constructor del modelo
        @param ancho Cantidad de celdas a lo ancho en la cuadricula.
        @param ancho Cantidad de celdas a lo largo en la cuadricula.
        @param tamanio Tamaño (en pixeles) de cada celda cuadrada que compone la cuadricula.
        */
	ModeloLaberintos(int ancho, int alto, int tamanio){
	    this.ancho = ancho;
	    this.alto = alto;
	    this.tamanio = tamanio;
	    this.generacion = 0;
	    //Inicializar mundo
	    mundo = new Celda[alto][ancho];
	    for(int i = 0; i < alto; i++)
		for(int j = 0; j < ancho; j++)
		    mundo[i][j] = new Celda(i,j);
	    posX = rd.nextInt(ancho);
	    posY = rd.nextInt(alto);
	    actual = mundo[posY][posX];
	    pila.push(actual); /* La celda actual se mete a la pila */ 
	    actual.visitada = true;
	    done = false;
	}
	
	/* Nos dice si una celda se puede mover hacia arriba */
	public boolean seMueveArriba(Celda c){
	    return (c.celdaY > 0) && c.paredArriba && !mundo[c.celdaY-1][c.celdaX].visitada;
	}

	/* Nos dice si una celda se puede mover hacia abajo */
	public boolean seMueveAbajo(Celda c){
	    return (c.celdaY < alto-1) && c.paredAbajo && !mundo[c.celdaY+1][c.celdaX].visitada;
	}
	
	/* Nos dice si una celda se puede mover hacia la izquierda */
	public boolean seMueveIzq(Celda c){
	    return (c.celdaX > 0) && c.paredIzq && !mundo[c.celdaY][c.celdaX-1].visitada;
	}

	/* Nos dice si una celda se puede mover hacia la derecha */
	public boolean seMueveDer(Celda c){
	    return (c.celdaX < ancho -1) && c.paredDer && !mundo[c.celdaY][c.celdaX+1].visitada;
	}
	
	/* Realiza un paso del algoritmo */
	public void paso(){     
	    if(actual.queda){
		int pared = rd.nextInt(4); /* Dirección en la que nos vamos a mover */
		switch(pared){
		case 0:
		    if(seMueveIzq(actual)){
			generacion++;
			actual.paredIzq = false;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			actual = mundo[posY][--posX];
			actual.paredDer = false;
			actual.visitada = true;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			pila.push(actual);
		    }
		    break;
		case 1:
		    if(seMueveArriba(actual)){
			generacion++;
			actual.paredArriba = false;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			actual = mundo[--posY][posX];
			posY = actual.celdaY;
			actual.paredAbajo = false;
			actual.visitada = true;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			pila.push(actual);
		    }
		    break;
		case 2:
		    if(seMueveDer(actual)){
			generacion++;
			actual.paredDer = false;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			actual = mundo[posY][++posX];
			posX = actual.celdaX;
			actual.paredIzq = false;
			actual.visitada = true;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			pila.push(actual);	
		    }
		    break;
		case 3:
		    if(seMueveAbajo(actual)){
			generacion++;
			actual.paredAbajo = false;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			actual = mundo[++posY][posX];
			posY = actual.celdaY;
			actual.paredArriba = false;
			actual.visitada = true;
			actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
			pila.push(actual);
		    }
		    break;
		}
	    } else if(!pila.empty()){
		actual = pila.pop();
		posX = actual.celdaX;
		posY = actual.celdaY;
		actual.queda = seMueveDer(actual) || seMueveArriba(actual) || seMueveAbajo(actual) || seMueveIzq(actual);
	    }else{
		this.done = true;
	    }
	}	
    }
    
    static public void main(String args[]) {
        PApplet.main(new String[] { "Laberintos" });
    }
}
 
