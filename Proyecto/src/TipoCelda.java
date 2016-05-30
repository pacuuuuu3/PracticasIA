/* Enumeración para representar el tipo de una celda */
public enum TipoCelda {
    OBSTACULO, /* Por ahora, se deja como obstáculo. En el futuro, se puede extender a diferentes tipos de obstáculo, como mesa y silla. */
    /* La siguientes 3 probabilidades representan una estimación de la probabilidad de que el robot esté en la celda */
    PROBABILIDAD_ALTA,
    PROBABILIDAD_MEDIA,
    PROBABILIDAD_BAJA
}
