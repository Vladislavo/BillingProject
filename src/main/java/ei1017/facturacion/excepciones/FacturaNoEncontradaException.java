package ei1017.facturacion.excepciones;

public class FacturaNoEncontradaException extends Exception {
	private static final long serialVersionUID = 1L;

	public FacturaNoEncontradaException(){
		super();
	}
	
	public FacturaNoEncontradaException(String message){
		super(message);
	}
}
