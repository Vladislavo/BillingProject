package ei1017.facturacion.gui.controlador;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.CampoDeFechaVacioException;
import ei1017.facturacion.excepciones.CampoVacioException;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.excepciones.FacturaNoEncontradaException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherenteException;
import ei1017.facturacion.excepciones.TarifaNoElegidaException;
import ei1017.facturacion.fabricas.FabricaDeClientesImpl;
import ei1017.facturacion.fabricas.FabricaDeTarifasImpl;
import ei1017.facturacion.gui.modelo.AlmacenDeDatosModeloInt;
import ei1017.facturacion.gui.vista.VistaInt;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.interfaces.FabricaDeClientesInt;
import ei1017.facturacion.interfaces.FabricaDeTarifasInt;
import ei1017.facturacion.recursos.TipoCampo;
import ei1017.facturacion.recursos.Dia;
import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.recursos.Factura;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.recursos.TipoTarifa;
import ei1017.facturacion.tarifa.Tarifa;
import ei1017.facturacion.tarifa.TarifaBasica;

public class ControladorImpl implements ControladorInt, Serializable {
	private static final long serialVersionUID = 1L;
	
	private FabricaDeClientesInt fabricaDeClientesImpl;
	private FabricaDeTarifasInt fabricaDeTarifasImpl;
	
	private final static int DESDE_HORA = 16;
	private final static int HASTA_HORA = 20;
	private static Dia DIA = Dia.DOMINGO;
	
	private AlmacenDeDatosModeloInt almacen;
	private VistaInt vista;
	
	public ControladorImpl(AlmacenDeDatosModeloInt almacen, VistaInt vista){
		this.almacen = almacen;
		this.vista = vista;
		this.fabricaDeClientesImpl = new FabricaDeClientesImpl();
		this.fabricaDeTarifasImpl = new FabricaDeTarifasImpl();
	}

	public void nuevoClienteEmpresa() {
		try {
			almacen.anyadirCliente(fabricaDeClientesImpl.getNuevoClienteEmpresa(new TarifaBasica(), 
					vista.getCampo(TipoCampo.NOMBRE), 
					vista.getCampo(TipoCampo.NIF),
					new Direccion(vista.getCampo(TipoCampo.POBLACION),
							vista.getCampo(TipoCampo.PROVINCIA),
							Integer.parseInt(vista.getCampo(TipoCampo.CODIGO_POSTAL))),
					vista.getCampo(TipoCampo.EMAIL),
					new Date()));
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene todos los campos");
		} catch (NumberFormatException e) {
			vista.actualizarEstadoError("Código postal es un número");
		}
	}

	public void nuevoClienteParticular() {
		try {
			almacen.anyadirCliente(fabricaDeClientesImpl.getNuevoClienteParticular(new TarifaBasica(), 
					vista.getCampo(TipoCampo.NOMBRE), 
					vista.getCampo(TipoCampo.NIF),
					new Direccion(vista.getCampo(TipoCampo.POBLACION),
							vista.getCampo(TipoCampo.PROVINCIA),
							Integer.parseInt(vista.getCampo(TipoCampo.CODIGO_POSTAL))),
					vista.getCampo(TipoCampo.EMAIL),
					new Date(), 
					vista.getCampo(TipoCampo.APELLIDOS)));
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene todos los campos");
		} catch (NumberFormatException e) {
			vista.actualizarEstadoError("Código postal es un número");
		}
	}

	public void borrarCliente() {
		String nif;
		try {
			nif = vista.getCampo(TipoCampo.NIF);
			almacen.borrarCliente(almacen.getCliente(nif));
			vista.actualizarEstadoGlobal("Usuario borrado");
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		}
	}

	public void cambiarTarifaCliente() {
		
		try {
			TipoTarifa tipo = vista.botonSeleccionado();
			
			Cliente cliente = almacen.getCliente(vista.getCampo(TipoCampo.NIF));
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
							cliente.getTarifa(), DIA.ordinal());
						break;
				}
			}
			almacen.cambiarTarifaCliente(vista.getCampo(TipoCampo.NIF),
					tarifa);
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (TarifaNoElegidaException e) {
			vista.actualizarEstadoError("Marque el tipo de tarifa");
		}
	}

	public void recuperarDatosCliente(){
		try {
			String nif = vista.getCampo(TipoCampo.NIF);
			vista.setDatosRecuperadosCliente(almacen.getCliente(nif).toString());
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		}
		
	}
	
	public void listarDatosClientes() {
		String listado = "";
		Collection<Cliente> clientes = almacen.getClientes().values();
		for(Cliente cliente : clientes){
			listado += cliente.toString() + "\n";
		}
		vista.actualizarListadoClientes(listado);
	}

	public void listarDatosClientesPeriodo() {
		try {
			Periodo periodo = vista.getPeriodo();
		
			Collection<? extends EntradaRegistro> clientes = almacen.getEntradas(almacen.getClientes().values()
					, periodo);
			String listado = "";
			for(EntradaRegistro cliente : clientes){
				listado += cliente.toString() + "\n";
			}
			vista.actualizarListadoClientes(listado);
		} catch (PeriodoDeTiempoIncoherenteException e) {
			vista.actualizarEstadoError("Período incoherente");
		} catch (CampoDeFechaVacioException e) {
			vista.actualizarEstadoError("Introduzca fechas del período");
		}
	}

	public void nuevaLlamada() {
		try {
			String nif = vista.getCampo(TipoCampo.NIF);
			
			String numero = vista.getCampo(TipoCampo.NUMERO_TELEFONO);
			Double duracion = Double.valueOf(vista.getCampo(TipoCampo.DURACION));
			Llamada llamada = new Llamada(numero, new Date(), duracion);
			
			almacen.anyadirLlamada(nif, llamada);
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene todos los campos");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		}
	}

	public void listarLlamadasPeriodo() {
		try {
			String nif = vista.getCampo(TipoCampo.NIF);
			Periodo periodo = vista.getPeriodo();
			Collection<? extends EntradaRegistro> llamadas;
			llamadas = almacen.getEntradas(almacen
						.getCliente(nif).getLlamadas(), periodo);
			String listado = "";
			for(EntradaRegistro llamada : llamadas){
				listado += llamada.toString() + "\n";
			}
			vista.actualizarListadoLlamadas(listado);
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		} catch (PeriodoDeTiempoIncoherenteException e) {
			vista.actualizarEstadoError("Período incoherente");
		} catch (CampoDeFechaVacioException e) {
			vista.actualizarEstadoError("Introduzca fechas del período");
		}
	}
	
	public void recuperarLlamadasCliente() {
		try {
			String nif = vista.getCampo(TipoCampo.NIF);
			
			String listado = "";
			Collection<Llamada> llamadas = almacen.getCliente(nif).getLlamadas();
			for(Llamada llamada : llamadas){
				listado += llamada.toString() + "\n";
			}
			vista.actualizarListadoLlamadas(listado);
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		}		
	}

	public void emitirFactura() {
		String nif;
		try {
			nif = vista.getCampo(TipoCampo.NIF);
			
			Cliente cliente = almacen.getCliente(nif);
			Tarifa tarifa = cliente.getTarifa();
			int idFactura = almacen.siguienteIdFactura();
			Periodo periodo = vista.getPeriodo();
			
			Factura factura = new Factura(tarifa, idFactura, new Date(), periodo);
			factura.emitir(cliente.getLlamadas());
			
			cliente.addFactura(idFactura, factura);
			
			vista.actualizarEstadoGlobal("Factura emitida.");
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		} catch (PeriodoDeTiempoIncoherenteException e) {
			vista.actualizarEstadoError("Período incoherente");
		} catch (CampoDeFechaVacioException e) {
			vista.actualizarEstadoError("Introduzca fechas del período");
		}	
	}

	public void recuperarDatosFactura() {
		String nif;
		try {
			nif = vista.getCampo(TipoCampo.NIF);
			
			int codFactura = Integer.valueOf(vista.getCampo(TipoCampo.ID_FACTURA));
			
			Factura factura = almacen.getCliente(nif).getFactura(codFactura);
			
			vista.setDatosRecuperadosFactura(factura.toString());
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene todos los campos");
		} catch (FacturaNoEncontradaException e) {
			vista.actualizarEstadoError("Factura no encontrada.");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		}
	}

	public void listarDatosFacturasPeriodo() {
		String nif;
		try {
			nif = vista.getCampo(TipoCampo.NIF);
			
			Periodo periodo = vista.getPeriodo();
			Collection<? extends EntradaRegistro> facturas = almacen.getEntradas(almacen
						.getCliente(nif).getFacturas().values(), periodo);
			String listado = "";
			for(EntradaRegistro factura : facturas){
				listado += factura.toString() + "\n";
			}
			vista.actualizarListadoFacturas(listado);
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		} catch (PeriodoDeTiempoIncoherenteException e) {
			vista.actualizarEstadoError("Período incoherente");
		} catch (CampoDeFechaVacioException e) {
			vista.actualizarEstadoError("Introduzca fechas del período");
		}
	}

	public void listarDatosFacturas() {
		try {
			String nif = vista.getCampo(TipoCampo.NIF);
			
			Collection<Factura> facturas = almacen.getCliente(nif).getFacturas().values();
			String listado = "";
			for(Factura factura : facturas){
				listado += factura.toString() + "\n";
			}
			
			vista.actualizarListadoFacturas(listado);
		} catch (CampoVacioException e) {
			vista.actualizarEstadoError("Rellene el campo NIF");
		} catch (ClienteNoEncontradoException e) {
			vista.actualizarEstadoError("Ciente no encontrado.");
		}
	}
}
