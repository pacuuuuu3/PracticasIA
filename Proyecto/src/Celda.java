/* Representa a una Celda de nuestro mundo */
public class Celda{
    private TipoCelda tipo; /* El tipo de la Celda (se usa para dibujarla en Processing */

    /* Constructor. Las celdas se inicializan con probabilidad media, pues al principio todas las celdas que no sean obstáculos tienen la misma probabilidad de que el robot esté ahí. */
    public Celda(){
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
    
}
