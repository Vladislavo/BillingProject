package ei1017.facturacion.recursos;

import java.io.Serializable;

public class Direccion implements Serializable {
	private static final long serialVersionUID = 1L;
	private String poblacion;
	private String provincia;
	private int codigoPostal;
	
	public Direccion(String poblacion, String provincia, int codigoPostal){
		super();
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.codigoPostal = codigoPostal;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}
	
	@Override
	public String toString(){
		return "Dirección: " + poblacion + ", " + provincia + ", CP " + codigoPostal; 
	}
}
