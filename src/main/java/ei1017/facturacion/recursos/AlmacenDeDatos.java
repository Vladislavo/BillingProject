package ei1017.facturacion.recursos;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.interfaces.EntradaRegistro;

public class AlmacenDeDatos implements Serializable {
	private static final long serialVersionUID = -1065341850225848464L;
	private Map<String, Cliente> clientes;
	private AtomicInteger idFacturas;
	
	public AlmacenDeDatos(){
		super();
		clientes = new HashMap<String, Cliente>();
		idFacturas = new AtomicInteger(0);
	}
	
	public boolean anyadirCliente(Cliente cliente){
		boolean ret = true;
		if(clientes.get(cliente.getNIF()) == null){
			clientes.put(cliente.getNIF(), cliente);
		} else {
			ret = false;
		}
		return ret;
	}
	
	public boolean borrarCliente(Cliente cliente) throws ClienteNoEncontradoException{
		if(clientes.remove(cliente.getNIF(), cliente)){
			return true;
		} else {
			throw new ClienteNoEncontradoException(cliente.toString());
		}
	}

	public Cliente getCliente(String nIF) throws ClienteNoEncontradoException{
		Cliente cliente = clientes.get(nIF);
		if(cliente == null){
			throw new ClienteNoEncontradoException("Cliente con NIF " + nIF + 
					" no fue encontrado.");
		} else {
			return cliente;
		}
	}
	
	public Collection<? extends EntradaRegistro> getEntradas(Collection<? extends EntradaRegistro> entradas,
			Periodo periodo){
		
		 Collection<? extends EntradaRegistro> resultado = entradas.stream()
			.filter(entradaRegistro -> entradaRegistro.getFecha().after(periodo.getFecha_inicio()) 
					&& entradaRegistro.getFecha().before(periodo.getFecha_final()))
			.collect(Collectors.toList());
		
		return resultado;
	}
	
	public int siguienteIdFactura(){
		return idFacturas.incrementAndGet();
	}
	
	public Map<String, Cliente> getClientes(){
		return clientes;
	}
	
	public void setClientes(Map<String, Cliente> clientes){
		this.clientes = clientes;
	}
	
	public int tamanio() {
		return clientes.size();
	}
}
