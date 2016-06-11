/* Clase que simula un robot que intenta ubicarse en un mundo */

/* Madres de Processing (seguro se necesitan) */
import processing.core.PApplet;
import processing.core.PFont;

public class DibujanteRobot extends PApplet{ /* La extensión se agrega para usar Processing */
    PFont fuente; /* Fuente para mostrar texto en pantalla */
    int tamanioMosaico = 60; /* Tamaño de cada mosaico en pixeles */
    int y = 21; /* Número de columnas del mundo */
    int x = 10; /* Número de renglones del mundo */
    Celda mundo[][]; /* El mundo en sí (?)*/
    
    /* Configura el mundo */
    public void settings() {
        size(y * tamanioMosaico, x * tamanioMosaico + 70); /* No le entiendo muy bien por el momento y tengo que modificarlo */ 
    }
    
    @Override
    /* Configuración inicial */
    public void setup(){
        background(102);
        fuente = createFont("Arial", 12, true); 
        textFont(fuente, 12);
	/* Construcción del mundo */
	mundo = new Celda[x][y]; /* El espacio sobre el que se mueve el robot */   
	/* Se llena el arreglo(?) */
	for(int i = 0; i < x; ++i){
	    for(int j = 0; j < y; ++j){
		mundo[i][j] = new Celda();
	    }
	}
    
    }

    @Override
    /* Dibuja el mundo */
    public void draw(){
	Celda actual; /* La celda que estamos dibujando actualmente */
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                actual = mundo[i][j];
		switch(actual.getTipo()) {
		case OBSTACULO:
		    stroke(0); 
		    fill(128, 128, 128); 
		    break;
		case PROBABILIDAD_ALTA:
		    stroke(0); 
		    fill(0,255,0);
		    break;
		case PROBABILIDAD_MEDIA:
		    stroke(0); 
		    fill(255,255,0);
		    break;
		case PROBABILIDAD_BAJA:
		    stroke(0); 
		    fill(220,20,60); 
		    break;
		default:
		    stroke(0); 
		    fill(0);
                }
		rect(j*tamanioMosaico, i*tamanioMosaico, tamanioMosaico, tamanioMosaico); /* Dibujamos el rectángulo (?) */
		
		/* Aquí escribimos la probabilidad */
		fill(0);
		text("P=" + actual.getCreencia(), j*tamanioMosaico+4, (i+1)*tamanioMosaico - 4);
		
	    }
	}

	fill(128,128,128);
        rect(10, x * tamanioMosaico + 10, 20, 20);
        fill(0);
        text("Obstáculo", 40, x * tamanioMosaico + 30);
	
	fill(0,255,0);
        rect(10, x * tamanioMosaico + 30, 20, 20);
        fill(0);
        text("Probabilidad alta", 40, x * tamanioMosaico + 50);

	fill(255,255,0);
        rect(2 * tamanioMosaico + 30, x * tamanioMosaico + 10, 20, 20);
        fill(0);
        text("Probabilidad media", 2 * tamanioMosaico + 60, x * tamanioMosaico + 30);

	fill(220,20,60);
        rect(2 * tamanioMosaico + 30, x * tamanioMosaico + 30, 20, 20);
        fill(0);
        text("Probabilidad baja", 2 * tamanioMosaico + 60, x * tamanioMosaico + 50);
    }   

    /* Dibuja el mundo */
    public static void main(String args[]) {
	PApplet.main(new String[] { "DibujanteRobot" });
    
    }

}
