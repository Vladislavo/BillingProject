package ei1017.facturacion.cliente;

import java.util.Date;

import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.tarifa.Tarifa;

public class ClienteParticular extends Cliente {
	private static final long serialVersionUID = 1L;
	private String apellidos;
	
	public ClienteParticular(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta, String apellidos) {
		super(tarifa, nombre, nIF, direccion, email, fechaDeAlta);
		this.apellidos = apellidos;
	}
	
	public String getApellidos(){
		return apellidos;
	}
	
	@Override
	public String toString(){
		return "Cliente Particular:\nApellidos: " + apellidos + ", " + super.toString();
	}
}
