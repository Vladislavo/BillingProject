package ei1017.facturacion.excepciones;

public class TarifaNoElegidaException extends Exception {
private static final long serialVersionUID = 1L;
	
	public TarifaNoElegidaException(){
		super();
	}
	
	public TarifaNoElegidaException(String message){
		super(message);
	}
}
