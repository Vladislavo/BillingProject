package ei1017.facturacion.gui.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.gui.vista.VistaInt;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.tarifa.Tarifa;

public class AlmacenDeDatosModeloImpl implements AlmacenDeDatosModeloInt, Serializable {
	private static final long serialVersionUID = -1065341850225848464L;
	private static final String FICHERO_DATOS_APLICACION = System.getProperty("user.dir") 
			+ "/resources/datosDeAplicacion.bin";
	private Map<String, Cliente> clientes;
	private AtomicInteger idFacturas;
	private transient VistaInt vista;
	
	public AlmacenDeDatosModeloImpl(){
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
		vista.actualizarEstadoGlobal("Cliente añadido");
		return ret;
	}
	
	public boolean borrarCliente(Cliente cliente) throws ClienteNoEncontradoException {
		if(clientes.remove(cliente.getNIF(), cliente)){
			return true;
		} else {
			throw new ClienteNoEncontradoException(cliente.toString());
		}
	}

	public Cliente getCliente(String nIF) throws ClienteNoEncontradoException {
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
	
	public void anyadirLlamada(String nif, Llamada llamada) throws ClienteNoEncontradoException{
		Cliente cliente = clientes.get(nif);
		if(cliente == null){
			throw new ClienteNoEncontradoException();
		} else {
			cliente.addLlamada(llamada);
			vista.actualizarEstadoGlobal("Llamada añadida.");
		}
	}
	
	public int siguienteIdFactura(){
		return idFacturas.incrementAndGet();
	}
	
	public Map<String, Cliente> getClientes(){
		return clientes;
	}
	
	public int tamanyo() {
		return clientes.size();
	}
	
	public void setVista(VistaInt vista){
		this.vista = vista;
	}
	
	public static void guardarDatosAFichero(AlmacenDeDatosModeloInt almacen) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(FICHERO_DATOS_APLICACION);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(almacen);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Optional<AlmacenDeDatosModeloImpl> cargarDatosDeFichero(){
		AlmacenDeDatosModeloImpl almacen = null;
		try {
			FileInputStream fis = new  FileInputStream(FICHERO_DATOS_APLICACION);
			ObjectInputStream ois = new ObjectInputStream(fis);
			almacen = (AlmacenDeDatosModeloImpl) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero " + FICHERO_DATOS_APLICACION + " no encontado."
					+ " Creado nuevo almacen");
			almacen = new AlmacenDeDatosModeloImpl();
		} catch (IOException e) {
			e.printStackTrace();
			almacen = new AlmacenDeDatosModeloImpl();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			almacen = new AlmacenDeDatosModeloImpl();
		}
		
		return Optional.of(almacen);
	}
	
	public void cambiarTarifaCliente(String nif, Tarifa tarifa){
		Cliente cliente = clientes.get(nif);
		cliente.setTarifa(tarifa);
		
		vista.actualizarEstadoGlobal("Nueva tarifa aplicada");
	}
}
