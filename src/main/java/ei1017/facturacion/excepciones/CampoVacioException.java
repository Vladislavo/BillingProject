package ei1017.facturacion.excepciones;

public class CampoVacioException extends Exception {
	private static final long serialVersionUID = 1L;

	public CampoVacioException(){
		super();
	}
	
	public CampoVacioException(String message){
		super(message);
	}
}
