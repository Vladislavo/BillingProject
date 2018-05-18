package ei1017.facturacion.gui.controlador;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.fabricas.FabricaDeClientesImpl;
import ei1017.facturacion.fabricas.FabricaDeTarifasImpl;
import ei1017.facturacion.gui.modelo.AlmacenDeDatos;
import ei1017.facturacion.gui.vista.Vista;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.Campos;
import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.recursos.TipoTarifa;
import ei1017.facturacion.tarifa.Tarifa;
import ei1017.facturacion.tarifa.TarifaBasica;

public class Controlador implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private FabricaDeClientesImpl fabricaDeClientesImpl;
	private FabricaDeTarifasImpl fabricaDeTarifasImpl;
	
	private final static int DESDE_HORA = 16;
	private final static int HASTA_HORA = 20;
	private final static int DIA = 0; // Domingo
	
	private AlmacenDeDatos almacen; // Modelo
	private Vista vista;
	
	public Controlador(AlmacenDeDatos almacen, Vista vista){
		this.almacen = almacen;
		this.vista = vista;
		this.fabricaDeClientesImpl = new FabricaDeClientesImpl();
		this.fabricaDeTarifasImpl = new FabricaDeTarifasImpl();
	}

	public void nuevoClienteEmpresa() {
		almacen.anyadirCliente(fabricaDeClientesImpl.getNuevoClienteEmpresa(new TarifaBasica(), 
				vista.getCampo(Campos.NOMBRE.ordinal()).getText(), 
				vista.getCampo(Campos.NIF.ordinal()).getText(),
				new Direccion(vista.getCampo(Campos.POBLACION.ordinal()).getText(),
						vista.getCampo(Campos.PROVINCIA.ordinal()).getText(),
						Integer.parseInt(vista.getCampo(Campos.CODIGO_POSTAL.ordinal()).getText())),
				vista.getCampo(Campos.EMAIL.ordinal()).getText(),
				new Date()));
	}

	public void nuevoClienteParticular() {
		almacen.anyadirCliente(fabricaDeClientesImpl.getNuevoClienteParticular(new TarifaBasica(), 
				vista.getCampo(Campos.NOMBRE.ordinal()).getText(), 
				vista.getCampo(Campos.NIF.ordinal()).getText(),
				new Direccion(vista.getCampo(Campos.POBLACION.ordinal()).getText(),
						vista.getCampo(Campos.PROVINCIA.ordinal()).getText(),
						Integer.parseInt(vista.getCampo(Campos.CODIGO_POSTAL.ordinal()).getText())),
				vista.getCampo(Campos.EMAIL.ordinal()).getText(),
				new Date(), 
				vista.getCampo(Campos.APELLIDOS.ordinal()).getText()));
	}

	public void borrarCliente() {
		String nif = vista.getCampo(Campos.NIF.ordinal()).getText();
		try {
			almacen.borrarCliente(almacen.getCliente(nif));
		} catch (ClienteNoEncontradoException e) {
			e.printStackTrace();
		}
		vista.actualizarEstadoGlobal("Usuario borrado");
	}

	public void cambiarTarifaCliente() {
		TipoTarifa tipo = vista.botonSeleccionado();
		try {
			Cliente cliente = almacen.getCliente(vista.getCampo(Campos.NIF.ordinal()).getText());
			Tarifa tarifa = null;
			
			
			switch(tipo) {
				case BASICA : {
					tarifa = fabricaDeTarifasImpl.getNuevaTarifaBasica();
					break;
				}
				case DE_TARDE : {
					tarifa = fabricaDeTarifasImpl.getNuevaTarifaPorHoras(
							cliente.getTarifa(), DESDE_HORA, HASTA_HORA);
					break;
				}
				case DOMINGO_GRATIS : {
					tarifa = fabricaDeTarifasImpl.getNuevaTarifaDiaria(
							cliente.getTarifa(), DIA);
						break;
				}
			}
			almacen.cambiarTarifaCliente(vista.getCampo(Campos.NIF.ordinal()).getText(),
					tarifa);
		} catch (ClienteNoEncontradoException e) {
			e.printStackTrace();
		}
	}

	public void listarDatosCliente() throws ClienteNoEncontradoException{
		String nif = vista.getCampo(Campos.NIF.ordinal()).getText();
		vista.setDatosRecuperadosCliente(almacen.getCliente(nif).toString());
	}
	
	public void listarDatosClientes() {
		String listado = "";
		Collection<Cliente> clientes = almacen.getClientes().values();
		for(Cliente cliente : clientes){
			listado += cliente.toString() + "\n";
		}
		vista.actualizarListadoClientes(listado);
	}

	public void listarDatosEntreFechasCliente() {
		Periodo periodo = vista.getPeriodo();
		Collection<? extends EntradaRegistro> clientes = almacen.getEntradas(almacen.getClientes().values()
				, periodo);
		String listado = "";
		for(EntradaRegistro cliente : clientes){
			listado += cliente.toString() + "\n";
		}
		vista.actualizarListadoClientes(listado);
	}

}
