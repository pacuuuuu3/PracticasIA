/* Es el algoritmo de localización de Markov */
public class Markov{
    private Celda[][] mundo; /* El mundo */
    private Robot robot; /* El Robot a localizar */
    private int ancho; /* El ancho del mundo */
    private int alto; /* El alto del mundo */
    private int noObstaculos; /* Número de Celdas que no son obstáculos */
    private double sigmaS; /* La varianza de nuestra distribución estándar */
    private boolean movimiento; /* Por ahora guardaré una variable que nos 
				   dice si el Robot se mueve */
    private boolean giro; /* Variable que nos dice si el Robot giró */
    private boolean desplazamiento; /* Variable que nos dice si el Robot se 
				       desplazó */
    
    /* Construye una nueva instancia del algoritmo */
    public Markov(Celda[][] mundo, Robot robot, double sigmaS){
	this.movimiento = false;
	this.mundo = mundo;
	this.robot = robot;
	this.sigmaS = sigmaS;
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
		    distancias[Celda.N] = distanciaNorte(i, j);
		    distancias[Celda.S] = distanciaSur(i, j);
		    distancias[Celda.E] = distanciaEste(i, j);
		    distancias[Celda.O] = distanciaOeste(i, j);
		    distancias[Celda.NE] = distanciaNoreste(i, j);
		    distancias[Celda.SE] = distanciaSureste(i, j);
		    distancias[Celda.NO] = distanciaNoroeste(i, j);
		    distancias[Celda.SO] = distanciaSuroeste(i, j);
		    mundo[i][j].setDistanciaObstaculo(distancias);
		}
	    }
	}
	while(true){
	    decideMovimiento(); /* Aquí se decide si se mueve o no el Robot, 
				   pero no sé cómo hacerlo */
	    if(!movimiento){ /* No se movió el Robot */
		robot.setAT(0.0); /* La lectura del sensor odométrico se pone 
				     en 0 */ 
		/* Para todas las direcciones posibles... */
		for(int direccion = Celda.N; direccion <= Celda.NO; 
		    ++direccion){ 
		    for(int i = 0; i < ancho; ++i){
			for(int j = 0; j < alto; ++j){ /* Iteramos sobre las 
							  posiciones del 
							  mundo */
			    if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO){
				
			double p; /* Probabilidad de leer un 
					     obstáculo dada la posición en 
					     que se supone estamos(?) */
				p = Math.sqrt(1/(2*Math.PI*sigmaS));
				double exponente = (-1) * Math.pow(robot.getST() - mundo[i][j].getDistanciaObstaculo(direccion), 2); /* Variable 
																	auxiliar en 
																	cálculos */
				exponente /= 2*sigmaS;
				p *= Math.exp(exponente);
				mundo[i][j].setCreencia(mundo[i][j].getCreencia() * p);
				robot.setAT(robot.getAT() 
					    + mundo[i][j].getCreencia());
			    }
			}
		    }
		}
		/* Para todas las direcciones posibles... */
		for(int direccion = Celda.N; direccion <= Celda.NO; 
		    ++direccion) 
		    /* Ahora se normaliza la creencia (?) */
		    for(int i = 0; i < ancho; ++i)
			for(int j = 0; j < alto; ++j) /* Iteramos sobre las 
							 posiciones del 
							 mundo */
			    if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO)
				mundo[i][j].setCreencia(mundo[i][j].getCreencia() * (1/robot.getAT()));
	    }else{ /* Se movió el Robot */
		if(giro){ /* El Robot giró */
		    /* Para todas las direcciones posibles... */
		    for(int direccion = Celda.N; direccion <= Celda.NO; 
			++direccion){ 
			for(int i = 0; i < ancho; ++i){
			    for(int j = 0; j < alto; ++j){ /* Iteramos sobre 
							      las posiciones 
							      del mundo */
				
			    }
			}
		    }
		}
		if(desplazamiento){ /* El Robot se desplazó */
		    
		}
	    }
	}
    
    }
    
    /* Calcula la distancia al obstáculo más cercano en dirección norte para 
       la Celda con poscición (i, j).
       Lo que regresa se multiplica por 10 para simplificar distancias */
    public int distanciaNorte(int i, int j){
	int jOriginal = j; /* Guardamos la posición en j original para hacer 
			      una resta al final */
	while(j > 0 && mundo[i][--j].getTipo() != TipoCelda.OBSTACULO);
	if(j == 0 && mundo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(jOriginal + 1); 
	return 10*(jOriginal - j);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección sur para 
       la Celda con poscición (i, j) */
    public int distanciaSur(int i, int j){
	int jOriginal = j; /* Guardamos la posición en j original para hacer 
			      una resta al final */
	while(j < alto-1 && mundo[i][++j].getTipo() != TipoCelda.OBSTACULO);
	if(j == alto-1 && mundo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(alto-jOriginal);
	return 10*(j - jOriginal);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección este para 
       la Celda con poscición (i, j) */
    public int distanciaEste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	while(i < ancho-1 && mundo[++i][j].getTipo() != TipoCelda.OBSTACULO);
	if(i == ancho-1 && mundo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(ancho-iOriginal);
	return 10*(i - iOriginal);
    }

    
    /* Calcula la distancia al obstáculo más cercano en dirección oeste para 
       la Celda con poscición (i, j) */
    public int distanciaOeste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	while(i > 0 && mundo[--i][j].getTipo() != TipoCelda.OBSTACULO);
	if(i == 0 && mundo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(iOriginal + 1);
	return 10*(iOriginal - i);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección noroeste para 
       la Celda con poscición (i, j). 
       Se multiplica por 14, la aproximación a una unidad de distancia en 
       diagonal. */
    public int distanciaNoroeste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i > 0 &&  j > 0 && mundo[--i][--j].getTipo() != TipoCelda.OBSTACULO);
	if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == 0)
		return 14*(iOriginal + 1);
	    if(j == 0)
		return 14*(jOriginal + 1);
	}
	return 14*(iOriginal - i);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección noreste para 
       la Celda con poscición (i, j). */ 
    public int distanciaNoreste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i < ancho-1 &&  j > 0 && mundo[++i][--j].getTipo() != TipoCelda.OBSTACULO);
	if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == ancho-1)
		return 14*(ancho - iOriginal);
	    if(j == 0)
		return 14*(jOriginal + 1);
	}
	return 14*(i - iOriginal);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección sureste para 
       la Celda con poscición (i, j). */ 
    public int distanciaSureste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i < ancho-1 &&  j < alto-1 && mundo[++i][++j].getTipo() != TipoCelda.OBSTACULO);
	if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == ancho-1)
		return 14*(ancho - iOriginal);
	    if(j == alto-1)
		return 14*(alto - jOriginal);
	}
	return 14*(i - iOriginal);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección noroeste 
       para la Celda con poscición (i, j). */
    public int distanciaSuroeste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i > 0 &&  j < alto-1 && mundo[--i][++j].getTipo() != TipoCelda.OBSTACULO);
	if(mundo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == 0)
		return 14*(iOriginal + 1);
	    if(j == alto-1)
		return 14*(alto - jOriginal);
	}
	return 14*(iOriginal - i);
    }
    
    /* Decide si se mueve o no el Robot */
    public void decideMovimiento(){
	movimiento = giro || desplazamiento;
    }
    
}
