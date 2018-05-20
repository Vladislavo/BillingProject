package ei1017.facturacion.gui.modelo;

import java.util.Collection;
import java.util.Map;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.tarifa.Tarifa;

public interface AlmacenDeDatosModeloInt {
	
	/* Clientes */
	public boolean anyadirCliente(Cliente cliente);
	public boolean borrarCliente(Cliente cliente) throws ClienteNoEncontradoException;
	public Cliente getCliente(String nIF) throws ClienteNoEncontradoException;
	public void cambiarTarifaCliente(String nif, Tarifa tarifa);
	
	/* Llamadas */
	public void anyadirLlamada(String nif, Llamada llamada) throws ClienteNoEncontradoException;
	
	/* Facturas */
	public int siguienteIdFactura();
	
	/* Comun */
	public Collection<? extends EntradaRegistro> getEntradas(Collection<? extends EntradaRegistro> entradas,
			Periodo periodo);
	public Map<String, Cliente> getClientes();
	
}
