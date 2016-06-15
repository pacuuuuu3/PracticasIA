/* Clase que representa al mundo en el que se moverá el Robot */
import java.util.ArrayList;

public class Mundo{
    private Celda[][] arreglo; /* Arreglo de Celdas que se usa como 
				  representación del Mundo */
    
    /* Construye un Mundo a partir de su arreglo de Celdas */
    public Mundo(Celda[][] arreglo){
	this.arreglo = arreglo;
	for(int i = 0; i < arreglo.length; ++i){
	    for(int j = 0; j < arreglo[0].length; ++j){
		Celda c = arreglo[i][j]; /* La Celda actual */
		c.setPosX(i);
		c.setPosY(j);
		if(c.getTipo() != TipoCelda.OBSTACULO){ /* Se llena su 
							   arreglo de 
							   distancias a 
							   obstáculos */
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
		    c.setDistanciaObstaculo(distancias);
		}
	    }
	}
    }
    
    public Celda getPosicion(int i, int j){ /* Regresa la Celda en Posición 
					       (i, j) */
	return this.arreglo[i][j];
    }
    
    /* Modifica la Celda con posición (i, j) */
    public void setPosicion(Celda nueva, int i, int j){
	nueva.setPosX(i);
	nueva.setPosY(j);
	this.arreglo[i][j] = nueva;
    }
    
    /* Regresa el ancho del Mundo */
    public int getAncho(){
	return this.arreglo.length;
    }

    /* Regresa el alto del Mundo */
    public int getAlto(){
	return this.arreglo[0].length;
    }
    
    /* Nos dice el número de posiciones en el mundo (sin contar obstáculos) */
    public int posiciones(){
	int posiciones = 0; /* El entero que regresaremos */
	for(Celda[] arreglin: this.arreglo)
	    for(Celda c: arreglin)
		if(c.getTipo() != TipoCelda.OBSTACULO)
		    posiciones++;
	return posiciones;
    }
   
     /* Calcula la distancia al obstáculo más cercano en dirección norte para 
       la Celda con posición (i, j).
       Lo que regresa se multiplica por 10 para simplificar distancias */
    public int distanciaNorte(int i, int j){
	int jOriginal = j; /* Guardamos la posición en j original para hacer 
			      una resta al final */
	while(j > 0 && arreglo[i][--j].getTipo() != TipoCelda.OBSTACULO);
	if(j == 0 && arreglo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(jOriginal + 1); 
	return 10*(jOriginal - j);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección sur para 
       la Celda con posición (i, j) */
    public int distanciaSur(int i, int j){
	int jOriginal = j; /* Guardamos la posición en j original para hacer 
			      una resta al final */
	while(j < arreglo[0].length-1 && arreglo[i][++j].getTipo() != TipoCelda.OBSTACULO);
	if(j == arreglo[0].length-1 && arreglo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(arreglo[0].length-jOriginal);
	return 10*(j - jOriginal);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección este para 
       la Celda con posición (i, j) */
    public int distanciaEste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	while(i < arreglo.length-1 && arreglo[++i][j].getTipo() != TipoCelda.OBSTACULO);
	if(i == arreglo.length-1 && arreglo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(arreglo.length-iOriginal);
	return 10*(i - iOriginal);
    }

    
    /* Calcula la distancia al obstáculo más cercano en dirección oeste para 
       la Celda con posición (i, j) */
    public int distanciaOeste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	while(i > 0 && arreglo[--i][j].getTipo() != TipoCelda.OBSTACULO);
	if(i == 0 && arreglo[i][j].getTipo() != TipoCelda.OBSTACULO)
	    return 10*(iOriginal + 1);
	return 10*(iOriginal - i);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección noroeste para 
       la Celda con posición (i, j). 
       Se multiplica por 14, la aproximación a una unidad de distancia en 
       diagonal. */
    public int distanciaNoroeste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i > 0 &&  j > 0 && arreglo[--i][--j].getTipo() != TipoCelda.OBSTACULO);
	if(arreglo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == 0)
		return 14*(iOriginal + 1);
	    if(j == 0)
		return 14*(jOriginal + 1);
	}
	return 14*(iOriginal - i);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección noreste para 
       la Celda con posición (i, j). */ 
    public int distanciaNoreste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i < arreglo.length-1 &&  j > 0 && arreglo[++i][--j].getTipo() != TipoCelda.OBSTACULO);
	if(arreglo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == arreglo.length-1)
		return 14*(arreglo.length - iOriginal);
	    if(j == 0)
		return 14*(jOriginal + 1);
	}
	return 14*(i - iOriginal);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección sureste para 
       la Celda con posición (i, j). */ 
    public int distanciaSureste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i < arreglo.length-1 &&  j < arreglo[0].length-1 && arreglo[++i][++j].getTipo() != TipoCelda.OBSTACULO);
	if(arreglo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == arreglo.length-1)
		return 14*(arreglo.length - iOriginal);
	    if(j == arreglo[0].length-1)
		return 14*(arreglo[0].length - jOriginal);
	}
	return 14*(i - iOriginal);
    }

    /* Calcula la distancia al obstáculo más cercano en dirección noroeste 
       para la Celda con posición (i, j). */
    public int distanciaSuroeste(int i, int j){
	int iOriginal = i; /* Guardamos la posición en i original para hacer 
			      una resta al final */
	int jOriginal = j; /* También hay que guardar la posición original en 
			      j */
	while(i > 0 &&  j < arreglo[0].length-1 && arreglo[--i][++j].getTipo() != TipoCelda.OBSTACULO);
	if(arreglo[i][j].getTipo() != TipoCelda.OBSTACULO){
	    if(i == 0)
		return 14*(iOriginal + 1);
	    if(j == arreglo[0].length-1)
		return 14*(arreglo[0].length - jOriginal);
	}
	return 14*(iOriginal - i);
    }   
    
    /* Regresa un ArrayList con los elementos del Mundo */
    public ArrayList<Celda> toArrayList(){
	ArrayList<Celda> regreso = new ArrayList<>(); /* Lista a regresar */
	for(Celda[] columna: arreglo)
	    for(Celda lugar: columna)
		if(lugar.getTipo() != TipoCelda.OBSTACULO) /* No quiero 
							      agregar 
							      obstáculos */
		    regreso.add(lugar);
	return regreso;
    }
    
}
