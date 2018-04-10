package ei1017.facturacion.fabricas;

import java.util.Date;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.cliente.ClienteEmpresa;
import ei1017.facturacion.cliente.ClienteParticular;
import ei1017.facturacion.interfaces.FabricaDeClientesInt;
import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.tarifa.Tarifa;

public class FabricaDeClientesImpl implements FabricaDeClientesInt {
	
	public FabricaDeClientesImpl(){
		super();
	}

	@Override
	public Cliente getNuevoCliente(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta) {
		
		return new Cliente(tarifa, nombre, nIF, direccion, email, fechaDeAlta);
	}
	
	@Override
	public Cliente getNuevoClienteParticular(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta, String apellidos) {
		
		return new ClienteParticular(tarifa, nombre, nIF, direccion, email,
			fechaDeAlta, apellidos);
	}

	@Override
	public Cliente getNuevoClienteEmpresa(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta) {
		
		return new ClienteEmpresa(tarifa, nombre, nIF, direccion, email, fechaDeAlta);
	}

	
}
