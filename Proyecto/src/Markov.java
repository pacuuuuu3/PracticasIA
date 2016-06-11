/* Es el algoritmo de localización de Markov */
public class Markov{
    private Celda[][] mundo; /* El mundo */
    private Robot robot; /* El Robot a localizar */
    private int ancho; /* El ancho del mundo */
    private int alto; /* El alto del mundo */
    private int noObstaculos; /* Número de Celdas que no son obstáculos */
    
    /* Construye una nueva instancia del algoritmo */
    public Markov(Celda[][] mundo, Robot robot){
	this.mundo = mundo;
	this.robot = robot;
	this.ancho = mundo.length;
	this.alto = mundo[0].length;
	this.noObstaculos = 0;
	for(int i = 0; i < ancho; ++i){
	    for(int j = 0; j < alto; ++j){
		if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO)
		    noObstaculos++;
	    }
	}
    }
    
    /* Es disque el algoritmo de Markov */
    public void algoritmo(){
	for(int i = 0; i < ancho; ++i){
	    for(int j = 0; j < alto; ++j){
		if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO)
		    mundo[i][j].setCreencia(1.0/noObstaculos);
	    }
	}
	for(int i = 0; i < ancho; ++i){
	    for(int j = 0; j < alto; ++j){
		if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO){
		    int[] distancias = new int[8]; /* Distancias a los 
						      obstáculos */
		}
	    }
	}
    }
    
    /* Calcula la distancia al obstáculo más cercano en dirección norte para 
       la Celda con poscición i, j */
    public int distanciaNorte(int i, int j){
	
	while(mundo[i][j].getTipo() != TipoCelda.OBSTACULO);
	return 0;
    }
}
