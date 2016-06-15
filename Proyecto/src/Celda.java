/* Representa a una Celda de nuestro mundo */
public class Celda implements Comparable<Celda>{
    private TipoCelda tipo; /* El tipo de la Celda (se usa para dibujarla en Processing */
    private double creencia; /* La probabilidad de que el robot se encuentre en esta Celda */
    /* Aquí vienen las distancias a obstáculos. Las haré enteras porque podemos considerar la distancia en
       diagonal como 14 como en la práctica de A* para simplificar(?) */
    private int[] distanciaObstaculo; /* Las distancias a los obstáculos en las diferentes direcciones */
    private int posX, posY; /* Posición de la Celda en el mundo en el que 
			       está */
    /* Las 8 direcciones */
    public static final int N = 0; /* Norte */
    public static final int NE = 1; /* Noreste */
    public static final int E = 2; /* Este */
    public static final int SE = 3; /* Sureste */
    public static final int S = 4; /* Sur */
    public static final int SO = 5; /* Suroeste */
    public static final int O = 6; /* Oeste */
    public static final int NO = 7; /* Noroeste */
    
    /* Constructor. Define el número de direcciones como 8 */
    public Celda(){
	/* Haré que aleatoriamente 1/10 de las Celdas sean obstáculos */
	double random = Math.random(); /* Número aleatorio para ver el tipo 
					  de la Celda */
	if(random <= (1.0/10.0))
	    this.tipo = TipoCelda.OBSTACULO;
	else
	    this.tipo = TipoCelda.PROBABILIDAD_MEDIA;
    }

    /* Regresa el tipo de la Celda */
    public TipoCelda getTipo(){
	return this.tipo;
    }

    /* Asigna al tipo como 'nuevoTipo' */
    public void setTipo(TipoCelda nuevoTipo){
	this.tipo = nuevoTipo;
    }
    
    /* Le dice a la Celda su posición en X */
    public void setPosX(int x){
	this.posX = x;
    }

    /* Le dice a la Celda su posición en Y */
    public void setPosY(int y){
	this.posY = y;
    }
    
    /* Regresa la creencia de la Celda */
    public double getCreencia(){
	return this.creencia;
    }

    /* Asigna a la creencia como 'creencia' */
    public void setCreencia(double creencia){
	this.creencia = creencia;
    }

    /* Regresa la distancia al obstáculo más cercano en la dirección dada */
    public int getDistanciaObstaculo(int direccion){
	return this.distanciaObstaculo[direccion];
    }
    
    /* Pone el arreglo de distancias como 'nuevasDistancias' */
    public void setDistanciaObstaculo(int[] nuevasDistancias){
	this.distanciaObstaculo = nuevasDistancias;
    }   

    /* Compara dos Celdas por su creencia */
    @Override
    public int compareTo(Celda o){
	if(o.getCreencia() < creencia)
	    return 1;
	if(o.getCreencia() == creencia)
	    return 0;
	return -1;
    }
}
