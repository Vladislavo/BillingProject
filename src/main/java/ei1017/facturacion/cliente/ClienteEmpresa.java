package ei1017.facturacion.cliente;

import java.util.Date;

import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.tarifa.Tarifa;

public class ClienteEmpresa extends Cliente{
	private static final long serialVersionUID = 1L;

	public ClienteEmpresa(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta) {
		super(tarifa, nombre, nIF, direccion, email, fechaDeAlta);
	}
	
	@Override
	public String toString(){
		return "Cliente Empresa:\n" + super.toString();
	}
	
}
