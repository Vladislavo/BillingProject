package ei1017.facturacion.excepciones;

public class PeriodoDeTiempoIncoherente extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PeriodoDeTiempoIncoherente(){
		super();
	}
	
	public PeriodoDeTiempoIncoherente(String message){
		super(message);
	}
}
