/* Clase que representa al robot del algoritmo de localización de Markov */
public class Robot{
    private int sT; /* La lectura del láser del Robot. */
    private int thetaT; /* La lectura del giro del Robot */
    private double aT; /* La lectura del sensor odométrico del Robot */
    private int x, y; /* Coordenadas del cuadro en el que se encuentra el 
			 Robot */
    private int theta; /* Dirección en la que mira el Robot */ 
    private Mundo mundo; /* Mundo en el que está el robot */
    private boolean giro; /* Nos dice si el Robot giró */
    private boolean desplazamiento; /* Nos dice si el Robot se desplazó */
    private Celda celda; /* La Celda en la que está el Robot */

    /* Crea un Robot dada su posición inicial */
    public Robot(int x, int y, int theta, Mundo mundo){
	this.x = x;
	this.y = y;
	this.theta = theta;
	this.mundo = mundo;
	this.celda = mundo.getPosicion(x, y);
    }
    
    /**
     * Corre el algoritmo de localización de Markov.
     * @param sigmaSquared - Varianza de la distribución normal.
     * @param sigmaTheta - Ruido del sensor de giro.
     * @param s - Porcentaje de ruido de la lectura del odómetro.
     */
    public void Markov(double sigmaSquared, double sigmaTheta, double s){
	int posiciones = mundo.posiciones(); /* Número de Celdas que no son 
						obstáculos en el Mundo */
	/* Primera parte del algoritmo.
	   Inicializamos todas las Celdas con la misma creencia */
	for(int i = 0; i < mundo.getAncho(); ++i){
	    for(int j = 0; j < mundo.getAlto(); ++j){
		Celda c = mundo.getPosicion(i, j); /* Obtenemos la Celda 
						      actual */
		if(c.getTipo() != TipoCelda.OBSTACULO){
		    c.setCreencia(1.0/posiciones);
		    mundo.setPosicion(c, i, j);
		}
	    }
	}
	/* Segunda parte.
	 Toca determinar distancias a obstáculos.
	 Esto se hace desde el constructor del mundo y no será necesario.*/
	/* Tercera parte.
	   Correr el algoritmo indefinidamente */
	while(true){
	    /* Decidir si se mueve el Robot */
	    decideMovimiento();
	    boolean movimiento = giro || desplazamiento; /* Nos dice si el 
							    Robot se movió */
	    if(!movimiento){ /* No se movió el Robot */
		aT = 0.0;
		for(int direccion = Celda.N, direccion < Celda.NO; 
		    ++direccion){ /* Para todas las direcciones posibles */
		    
		    /* Distancia del Robot al siguiente obstáculo*/
		    double distanciaObstaculo = celda.getDistanciaObstaculo(direccion); 
		    /* Iteramos sobre las posiciones */
		    for(int i = 0; i < mundo.getAncho(); ++i){
			for(int j = 0; j < mundo.getAlto(); ++j){
			    Celda cuadro = mundo.getPosicion(i, j); /* La 
								       Celda 
								       actual 
								    */
			    if(cuadro.getTipo() != TipoCelda.OBSTACULO){
				double p; /* P(sT | l) */
				p = Math.sqrt(1.0/2*Math.PI*sigmaSquared);
				p *= Math.exp((-1.0)*Math.pow(sT - distanciaObstaculo, 2.0)/(2.0*sigmaSquared));
				cuadro.setCreencia(cuadro.getCreencia() * p);
				mundo.setPosicion(cuadro, i, j);
			    }
			}
		    }
		}
		/* Ahora se normaliza la creencia */
		/* En el pdf decía que para todas las direcciones posibles, pero según yo, eso está mal */
		/* Iteramos sobre las posiciones */
		for(i = 0; i < mundo.getAncho(); ++i){
		    for(j = 0; j < mundo.getAlto(); ++j){
			Celda actual = mundo.getPosicion(i, j); /* La Celda 
								   actual */
			if(actual.getTipo() != TipoCelda.OBSTACULO)
			    aT += cuadro.getCreencia();
		    }
		}
		
		/* El código ya se volvió un desmadre, pero según yo sigue
		   bien */
		/* Iteramos sobre las posiciones */
		for(i = 0; i < mundo.getAncho(); ++i){
		    for(j = 0; j < mundo.getAlto(); ++j){
			Celda actual = mundo.getPosicion(i, j); /* La Celda 
								   actual */
			if(actual.getTipo() != TipoCelda.OBSTACULO){
			    cuadro.setCreencia(cuadro.getCreencia() * 
					       (1.0/aT));
			    mundo.setPosicion(actual, i, j);
			}
		    }
		}

	    }else{ /* Segundo caso: Se movió el Robot */
		if(giro){ /* El Robot giró */
		    double[][] sumaProbas = new double[mundo.getAncho()][mundo.getLargo()]; /* Arreglo donde se pondrá ΣP(l|l', θT) */
		    for(int direccion = Celda.N, direccion < Celda.NO; 
			++direccion){ /* Para todas las direcciones posibles */
			/* Iteramos sobre las posiciones */
			for(int i = 0; i < mundo.getAncho(); ++i){
			    for(int j = 0; j < mundo.getLargo(); ++j){
				double p; /* P(l|l', θT) */
				p = (1.0) / ((2.0) * Math.PI * sigmaTheta);
				p *= Math.exp(Math.pow(((45*direccion - 
							 theta) - thetaT) / 
						       sigmaTheta, 2));
				sumaProbas[i][j] += p;
			    }
			}
		    } 
		    double creenciaRobot = celda.getCreencia(); /* Creencia 
								   de la 
								   Celda en 
								   la que 
								   está el 
								   Robot */
		    /* Iteramos sobre las posiciones */
		    for(int i = 0; i < mundo.getAncho(); ++i){
			for(int j = 0; j < mundo.getLargo(); ++j){
			    Celda c = mundo.getPosicion(i, j);
			    if(c.getTipo() != TipoCelda.OBSTACULO){
				c.setCreencia(creenciaRobot*sumaProbas[i][j]);
				mundo.setPosicion(c, i, j);
			    }
			}
		    }
		}else{ /* El Robot se desplazó */
		    double[][] sumaProbas = new double[mundo.getAncho()][mundo.getLargo()]; /* Arreglo donde se pondrá ΣP(l|l', aT) */
		    for(int direccion = Celda.N, direccion < Celda.NO; 
			++direccion){ /* Para todas las direcciones posibles */
			/* Iteramos sobre las posiciones */
			for(int i = 0; i < mundo.getAncho(); ++i){
			    for(int j = 0; j < mundo.getLargo(); ++j){
				double sigmaX, sigmaY; /* σx, σy */
				sigmaX = s * aT * Math.cos(direccion*45);
				sigmaY = s * aT * Math.sin(direccion*45);
				double p; /* P(l|l', aT) */
				p = (1.0) / ((2.0) * Math.PI *sigmaX * sigmaY);
				p *= Math.exp((-1.0/2.0)*
					      (Math.pow((x + aT*Math.cos(direccion*45)-i)/sigmaX, 2.0) + 
					       Math.pow((y + aT*Math.cos(direccion*45)-j)/sigmaY, 2.0)));
				sumaProbas[i][j] += p;
			    }
			}
		    } 
		    double creenciaRobot = celda.getCreencia(); /* Creencia 
								   de la 
								   Celda en 
								   la que 
								   está el 
								   Robot */
		    /* Iteramos sobre las posiciones */
		    for(int i = 0; i < mundo.getAncho(); ++i){
			for(int j = 0; j < mundo.getLargo(); ++j){
			    Celda c = mundo.getPosicion(i, j);
			    if(c.getTipo() != TipoCelda.OBSTACULO){
				c.setCreencia(creenciaRobot*sumaProbas[i][j]);
				mundo.setPosicion(c, i, j);
			    }
			}
		    }
		}
	    }
	}
    }
    
    /* Decide, a partir de una variable aleatoria, qué va a hacer el Robot */
    public void decideMovimiento(){
	double aleatoria = Math.random(); /* Variable aleatoria para decidir 
					     la acción del Robot. */
	if(aleatoria <= (1.0/3)){
	    giro = false;
	    desplazamiento = false;
	}else if (aleatoria <= (2.0/3)){
	    giro = true;
	    desplazamiento = false;
	    /* Se gira 45° o -45° dependiendo de otra variable aleatoria */
	    double aleatoria2 = Math.random(); /* Segunda variable aleatoria 
						  para ver la dirección de 
						  giro */
	    if(aleatoria2 <= 0.5)
		thetaT = -45;
	    else
		thetaT = 45;
	    /* Se actualiza la dirección en la que ve el Robot */
	    theta += thetaT;
	    theta %= 360;
	}
	else{ /* Se desplaza el Robot sólo si esto es posible */
	    giro = false;
	    desplazamiento = mueve(); /* La función mueve intenta mover al 
					 Robot, actualizando su posición y 
					 sensor odométrico. 
					 Regresa true si el Robot no chocó y 
					 false en otro caso. */
	}
    }
    
    /* Intenta mover al Robot y regresa true si se logró mover y false si no */
    public boolean mueve(){
	if(theta == 0)
	    return mueveNorte();
	if(theta == 45)
	    return mueveNoreste();
	if(theta == 90)
	    return mueveEste();
	if(theta == 135)
	    return mueveSureste();
	if(theta == 180)
	    return mueveSur();
	if(theta == 225)
	    return mueveSuroeste();
	if(theta == 270)
	    return mueveOeste();
	/* Si no es ninguna de las direcciones anteriores, es el noroeste */
	return mueveNoroeste();
    }

    /* Se intenta mover al norte. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveNorte(){
	if(y == 0)
	    return false;
	Celda adyacente = mundo.getPosicion(x, y-1); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	y = y-1;
	celda = adyacente;
	aT = 10.0; /* Actualiza el sensor odométrico */
	return true;
    }

    /* Se intenta mover al sur. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveSur(){
	if(y == mundo.getAlto() - 1)
	    return false;
	Celda adyacente = mundo.getPosicion(x, y+1); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	y = y+1;
	celda = adyacente;
	aT = 10.0; /* Actualiza el sensor odométrico */
	return true;
    }
    
    /* Se intenta mover al este. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveEste(){
	if(x == mundo.getAncho() - 1)
	    return false;
	Celda adyacente = mundo.getPosicion(x+1, y); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	x = x+1;
	celda = adyacente;
	aT = 10.0; /* Actualiza el sensor odométrico */
	return true;
    }

    /* Se intenta mover al oeste. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveOeste(){
	if(x == 0)
	    return false;
	Celda adyacente = mundo.getPosicion(x-1, y); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	x = x-1;
	celda = adyacente;
	aT = 10.0; /* Actualiza el sensor odométrico */
	return true;
    }

    /* Se intenta mover al noreste. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveNoreste(){
	if(y == 0 || x == mundo.getAncho()-1)
	    return false;
	Celda adyacente = mundo.getPosicion(x+1, y-1); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	x = x+1;
	y = y-1;
	celda = adyacente;
	aT = 14.0; /* Actualiza el sensor odométrico */
	return true;
    }

    /* Se intenta mover al noroeste. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveNoroeste(){
	if(y == 0 || x == 0)
	    return false;
	Celda adyacente = mundo.getPosicion(x-1, y-1); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	x = x-1;
	y = y-1;
	celda = adyacente;
	aT = 14.0; /* Actualiza el sensor odométrico */
	return true;
    }

    /* Se intenta mover al sureste. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveSureste(){
	if(y == mundo.getAlto()-1 || x == mundo.getAncho()-1)
	    return false;
	Celda adyacente = mundo.getPosicion(x+1, y+1); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	x = x+1;
	y = y+1;
	celda = adyacente;
	aT = 14.0; /* Actualiza el sensor odométrico */
	return true;
    }   

    /* Se intenta mover al suroeste. Regresa true si se pudo y false en otro 
       caso */
    public boolean mueveSuroeste(){
	if(y == mundo.getAlto()-1 || x == 0)
	    return false;
	Celda adyacente = mundo.getPosicion(x-1, y+1); /* La Celda a la que se 
							intentará mover */ 
	if(adyacente.getTipo() == TipoCelda.OBSTACULO)
	    return false;
	x = x-1;
	y = y+1;
	celda = adyacente;
	aT = 14.0; /* Actualiza el sensor odométrico */
	return true;
    }    
}
