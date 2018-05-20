package ei1017.facturacion.excepciones;

public class PeriodoDeTiempoIncoherenteException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PeriodoDeTiempoIncoherenteException(){
		super();
	}
	
	public PeriodoDeTiempoIncoherenteException(String message){
		super(message);
	}
}
