/* Modela un perceptrón que aprende usando la función 'signo' como función de 
   activación */
public class Perceptron{

    private double[] pesos; /* Pesos del Perceptron */
    private double tazaDeAprendizaje; /* α */
    private double umbral; /* θ */
    
    /* Inicialización del Perceptron.
       Pone los pesos y el umbral como números aleatorios. 
       Recibe un tamaño para saber el número de pesos que debe tener. */
    public Perceptron(int tamano, double tazaDeAprendizaje){
	this.tazaDeAprendizaje = tazaDeAprendizaje;
	this.pesos = new double[tamano];
	for(int i = 0; i < tamano; ++i)
	    this.pesos[i] = Math.random() - 0.5;
	this.umbral = Math.random() - 0.5;
    }

    /* Imprime al Perceptron */
    @Override
    public String toString(){
	String s = ""; /* La cadena a regresar */
	int i = 0; /* Contador */
	for(double peso: pesos){
	    s += "w" + i + ": " + peso + "\n";
	    i++;
	}
	s += "Taza de aprendizaje: " + tazaDeAprendizaje + "\n";
	s += "Umbral: " + umbral;
	return s;
    }
    
    /* Aprende a partir de una entrada */
    public void aprende(int[] entrada){
	boolean correcta = false; /* Nos dice si la salida es correcta */
	while(!correcta){
	    int salida; /* La salida del Perceptron */
	    double suma = 0.0; /* La suma de las transformación lineal a las 
				  entradas */
	    for(int i = 0; i < entrada.length - 1; ++i) /* Para las 
							   entradas */
		suma += entrada[i] * pesos[i] - umbral;
	    salida = aplicaFuncion(suma);
	    if(salida == entrada[entrada.length - 1])
		correcta = true;
	    else{ /* Se actualizan los pesos */
		int error = entrada[entrada.length - 1] - salida;
		for(int i = 0; i < entrada.length - 1; ++i)
		    pesos[i] += tazaDeAprendizaje * entrada[i] * error; 
		/* Faltaba actualizar el umbral para que no se atorara el 
		   algoritmo */
		umbral -= tazaDeAprendizaje*error;
	    }
	}
    }
    
    /* Le aplica la función 'signo' a una entrada */
    public int aplicaFuncion(double entrada){
	if(entrada > 0)
	    return 1;
	return 0;
    }
    
    /* Regresa la salida después de pasar la entrada por el Perceptron */
    public int salida(int[] entrada){
	int salida; /* La salida del Perceptron */
	double suma = 0.0; /* La suma de las transformación lineal a las 
			      entradas */
	for(int i = 0; i < entrada.length - 1; ++i) /* Para las entradas */
	    suma += entrada[i] * pesos[i] - umbral;
    	salida = aplicaFuncion(suma);
	return salida;
    }
    
    /* Aprende a partir de un conjunto de aprendizaje */
    public void aprendeConjunto(int[][] conjuntoDeAprendizaje){
	for(int[] leccion: conjuntoDeAprendizaje)
	    aprende(leccion);
    }
    
    /* Lo usaré para probar al Perceptron */
    public static void main(String args[]){
       	/* Conjunto 1 */
	System.out.println("Conjunto 1: Total");
	int[][] conjuntoDeAprendizajeAnd1 = {{1, 1, 1, 1}, {0, 1, 1, 0}, 
					 {1, 0, 1, 0}, {1, 1, 0, 0}, 
					 {1, 0, 0, 0}, {0, 1, 0, 0}, 
					 {0, 0, 1, 0}, {0, 0, 0, 0}};
	int[][] conjuntoDeAprendizajeOr1 = {{1, 1, 1, 1}, {0, 1, 1, 1}, 
					 {1, 0, 1, 1}, {1, 1, 0, 1}, 
					 {1, 0, 0, 1}, {0, 1, 0, 1}, 
					 {0, 0, 1, 1}, {0, 0, 0, 0}};
	Perceptron and = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						    función AND */
	Perceptron or = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						   función OR */
	for(int i = 0; i < 1000; ++i){
	    and.aprendeConjunto(conjuntoDeAprendizajeAnd1);
	    or.aprendeConjunto(conjuntoDeAprendizajeOr1);
	}
     	System.out.println("Perceptrón AND: \n" + and);
	int correctos = 0; /* Número de resultados correctos */
	for(int[] entrada: conjuntoDeAprendizajeAnd1){
	    int salida = and.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("");
	System.out.println("Perceptrón OR: \n" + or);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeOr1){
	    int salida = or.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("**********************************************");
	
	/* Conjunto 2 */
	System.out.println("Conjunto 2: {{1, 1, 1}, {0, 0, 0}}");
	int[][] conjuntoDeAprendizajeAnd2 = {{1, 1, 1, 1}, {0, 0, 0, 0}};
	int[][] conjuntoDeAprendizajeOr2 = {{1, 1, 1, 1}, {0, 0, 0, 0}};
	and = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						    función AND */
	or = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						   función OR */
	for(int i = 0; i < 1000; ++i){
	    and.aprendeConjunto(conjuntoDeAprendizajeAnd2);
	    or.aprendeConjunto(conjuntoDeAprendizajeOr2);
	}
     	System.out.println("Perceptrón AND: \n" + and);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeAnd1){
	    int salida = and.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("");
	System.out.println("Perceptrón OR: \n" + or);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeOr1){
	    int salida = or.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("**********************************************");

	/* Conjunto 3 */
	System.out.println("Conjunto 3: {{0, 0, 1}, {1, 1, 1}, {1, 1, 0}, " + 
			   "{0, 1, 1}, {1, 0, 1}}");
	int[][] conjuntoDeAprendizajeAnd3 = {{0, 0, 1, 0}, {1, 1, 1, 1},
					     {1, 1, 0, 0}, {0, 1, 1, 0},
					     {1, 0, 1, 0}};
	int[][] conjuntoDeAprendizajeOr3 = {{0, 0, 1, 1}, {1, 1, 1, 1},
					    {1, 1, 0, 1}, {0, 1, 1, 1},
					    {1, 0, 1, 1}};
	and = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						    función AND */
	or = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						   función OR */
	for(int i = 0; i < 1000; ++i){
	    and.aprendeConjunto(conjuntoDeAprendizajeAnd3);
	    or.aprendeConjunto(conjuntoDeAprendizajeOr3);
	}
     	System.out.println("Perceptrón AND: \n" + and);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeAnd1){
	    int salida = and.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("");
	System.out.println("Perceptrón OR: \n" + or);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeOr1){
	    int salida = or.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("**********************************************");

	/* Conjunto 4 */
	System.out.println("Conjunto 4: {{0, 0, 1}, {0, 0, 0}, {1, 1, 0}, " + 
			   "{0, 1, 1}, {1, 0, 1}, {0, 1, 0}}");
	int[][] conjuntoDeAprendizajeAnd4 = {{0, 0, 1, 0}, {0, 0, 0, 0},
					     {1, 1, 0, 0}, {0, 1, 1, 0},
					     {1, 0, 1, 0}, {0, 1, 0, 0}};
	int[][] conjuntoDeAprendizajeOr4 = {{0, 0, 1, 1}, {0, 0, 0, 0},
					    {1, 1, 0, 1}, {0, 1, 1, 1},
					    {1, 0, 1, 1}, {0, 1, 0, 1}};
	and = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						    función AND */
	or = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						   función OR */
	for(int i = 0; i < 1000; ++i){
	    and.aprendeConjunto(conjuntoDeAprendizajeAnd4);
	    or.aprendeConjunto(conjuntoDeAprendizajeOr4);
	}
     	System.out.println("Perceptrón AND: \n" + and);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeAnd1){
	    int salida = and.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("");
	System.out.println("Perceptrón OR: \n" + or);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeOr1){
	    int salida = or.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("**********************************************");

	/* Conjunto 5 */
	System.out.println("Conjunto 5: {{1, 1, 1}}");
	int[][] conjuntoDeAprendizajeAnd5 = {{1, 1, 1, 1}};
	int[][] conjuntoDeAprendizajeOr5 = {{1, 1, 1, 1}};
	and = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						    función AND */
	or = new Perceptron(3, 0.5); /* Perceptron que aprende la 
						   función OR */
	for(int i = 0; i < 1000; ++i){
	    and.aprendeConjunto(conjuntoDeAprendizajeAnd5);
	    or.aprendeConjunto(conjuntoDeAprendizajeOr5);
	}
     	System.out.println("Perceptrón AND: \n" + and);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeAnd1){
	    int salida = and.salida(entrada);
	    if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");
	System.out.println("");
	System.out.println("Perceptrón OR: \n" + or);
	correctos = 0;
	for(int[] entrada: conjuntoDeAprendizajeOr1){
	    int salida = or.salida(entrada);
	   if(salida == entrada[3])
		correctos++;
	}
	System.out.println("Resultados correctos: " + correctos + "/8");	
    }
}
