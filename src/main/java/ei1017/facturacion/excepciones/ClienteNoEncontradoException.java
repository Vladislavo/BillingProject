package ei1017.facturacion.excepciones;

public class ClienteNoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public ClienteNoEncontradoException(){
		super();
	}
	
	public ClienteNoEncontradoException(String message){
		super(message);
	}
}
