package ei1017.facturacion.interfaces;

import java.util.Date;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.tarifa.Tarifa;

public interface FabricaDeClientesInt {
	Cliente getNuevoCliente(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta);
	Cliente getNuevoClienteParticular(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta, String apellidos);
	Cliente getNuevoClienteEmpresa(Tarifa tarifa, String nombre, String nIF, Direccion direccion, String email,
			Date fechaDeAlta);
}
