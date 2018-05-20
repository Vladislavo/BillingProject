package ei1017.facturacion.excepciones;

public class CampoDeFechaVacioException extends Exception {
	private static final long serialVersionUID = 1L;

	public CampoDeFechaVacioException(){
		super();
	}
	
	public CampoDeFechaVacioException(String message){
		super(message);
	}
}
