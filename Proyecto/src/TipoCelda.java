/* Enumeración para representar el tipo de una celda */
public enum TipoCelda {
    ACTUAL, /* Si el robot está parado en la celda */
    OBSTACULO, /* Por ahora, se deja como obstáculo. En el futuro, se puede extender a diferentes tipos de obstáculo, como mesa y silla. */
    VACIA /* Si no hay nada en la celda */
}
