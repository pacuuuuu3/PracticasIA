/* Clase que simula un robot que intenta ubicarse en un mundo */

/* Madres de Processing (seguro se necesitan) */
import processing.core.PApplet;
import processing.core.PFont;

public class DibujanteRobot extends PApplet{ /* La extensión se agrega para 
						usar Processing */
    PFont fuente; /* Fuente para mostrar texto en pantalla */
    int tamanioMosaico = 60; /* Tamaño de cada mosaico en pixeles */
    int y = 10; /* Número de renglones del mundo */
    int x = 21; /* Número de columnas del mundo */
    Mundo mundo; /* El mundo en sí */
    Robot robot; /* El Robot que está en el mundo */
    int posX, posY; /* Coordenadas de la posición del Robot */
    
    /* Configura el mundo */
    public void settings() {
        size(x * tamanioMosaico, y * tamanioMosaico + 70); /* No le entiendo 
							      muy bien por el 
							      momento y tengo 
							      que modificarlo 
							   */ 
    }
    
    @Override
    /* Configuración inicial */
    public void setup(){
        background(102);
        fuente = createFont("Arial", 12, true); 
        textFont(fuente, 12);
	/* Construcción del mundo */
	/* Se llena el arreglo */
	Celda[][] espacio = new Celda[x][y]; /* Arreglo de Celdas que 
						representa al mundo */
	for(int i = 0; i < x; ++i)
	    for(int j = 0; j < y; ++j)
		espacio[i][j] = new Celda();
	mundo = new Mundo(espacio);
	posX = (int)(Math.random() * x); /* Coordenada en X del Robot */
	posY = (int)(Math.random() * y); /* Coordenada en Y del Robot */
	int theta= (int)(Math.random() * 8) * 45; /* Dirección inicial en la 
						     que mira el Robot */
	robot = new Robot(posX, posY, theta, mundo);
    }

    @Override
    /* Dibuja el mundo */
    public void draw(){
	posX = robot.getX();
	posY = robot.getY();
	Celda actual; /* La celda que estamos dibujando actualmente */
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
	 	actual = mundo.getPosicion(i, j);
		if(i != posX || j != posY){
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
			fill(255, 255, 0);
			break;
		    case PROBABILIDAD_BAJA:
			stroke(0); 
			fill(220,20,60); 
			break;
		    default:
			stroke(0); 
			fill(0);
		    }
		}else{
		    /* Coloreamos diferente la posición del Robot */
		    stroke(0); 
		    fill(222,184,135); 
		}
		rect(i*tamanioMosaico, j*tamanioMosaico, tamanioMosaico, tamanioMosaico); /* Dibujamos el rectángulo */
		/* Aquí escribimos la probabilidad */
		if(actual.getTipo() != TipoCelda.OBSTACULO){
		    fill(0);
		    text("P=" + actual.getCreencia(), i*tamanioMosaico+4, 
			 (j+1)*tamanioMosaico - 4);
		}
	    }
	}

	fill(128,128,128);
        rect(10, y * tamanioMosaico + 10, 20, 20);
        fill(0);
        text("Obstáculo", 40, y * tamanioMosaico + 30);
	
	fill(0,255,0);
        rect(10, y * tamanioMosaico + 30, 20, 20);
        fill(0);
        text("Probabilidad alta", 40, y * tamanioMosaico + 50);

	fill(255,255,0);
        rect(2 * tamanioMosaico + 30, y * tamanioMosaico + 10, 20, 20);
        fill(0);
        text("Probabilidad media", 2 * tamanioMosaico + 60, y * tamanioMosaico + 30);

	fill(220,20,60);
        rect(2 * tamanioMosaico + 30, y * tamanioMosaico + 30, 20, 20);
        fill(0);
        text("Probabilidad baja", 2 * tamanioMosaico + 60, y * tamanioMosaico + 50);

	fill(222,184,135);
        rect(2 * tamanioMosaico + 200, y * tamanioMosaico + 30, 20, 20);
        fill(0);
        text("Robot", 2 * tamanioMosaico + 220, y * tamanioMosaico + 50);
	robot.markov(1000000, 1000000, 1000000);
	/* Que duerma un poco después de dibujar */
	try{
	    Thread.sleep(1000);
	}catch(InterruptedException e){
	    return;
	}
    }

    
    

    /* Dibuja el mundo */
    public static void main(String args[]) {
	PApplet.main(new String[] { "DibujanteRobot" });
    
    }

}
