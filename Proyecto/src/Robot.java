/* Clase que representa al robot del algoritmo de localización de Markov */
public class Robot{
    private int sT; /* La lectura del láser del Robot. */
    private int thetaT; /* La lectura del giro del Robot */
    private double aT; /* La lectura del sensor odométrico del Robot */
        
    /* Actualiza la lectura del láser del Robot por sT */
    public void setST(int sT){
	this.sT = sT;
    }
    
    /* Regresa la lectura del láser del Robot */
    public int getST(){
	return this.sT;
    }

    /* Actualiza la lectura del giro del Robot por thetaT */
    public void setThetaT(int thetaT){
	this.thetaT = thetaT;
    }
    
    /* Regresa la lectura del giro del Robot */
    public int getThetaT(){
	return this.thetaT;
    }

    /* Actualiza la lectura del sensor odométrico del Robot por aT */
    public void setAT(double aT){
	this.aT = aT;
    }
    
    /* Regresa la lectura del sensor odométrico del Robot */
    public double getAT(){
	return this.aT;
    }
    
    
}
