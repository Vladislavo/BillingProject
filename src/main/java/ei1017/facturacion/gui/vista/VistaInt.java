package ei1017.facturacion.gui.vista;

import ei1017.facturacion.excepciones.CampoDeFechaVacioException;
import ei1017.facturacion.excepciones.CampoVacioException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherenteException;
import ei1017.facturacion.excepciones.TarifaNoElegidaException;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.recursos.TipoCampo;
import ei1017.facturacion.recursos.TipoTarifa;

public interface VistaInt {
	
	/* Clientes */
	public TipoTarifa botonSeleccionado() throws TarifaNoElegidaException;
	public void setDatosRecuperadosCliente(String info);
	public void actualizarListadoClientes(String listado);
	
	/* Facturas */
	public void setDatosRecuperadosFactura(String info);
	public void actualizarListadoFacturas(String listado);
	
	/* Llamadas */
	public void actualizarListadoLlamadas(String listado);
	
	/* Comun */
	public String getCampo(TipoCampo campos) throws CampoVacioException;
	public void actualizarEstadoGlobal(String estado);
	public void actualizarEstadoError(String error);
	public Periodo getPeriodo() throws 
		PeriodoDeTiempoIncoherenteException, CampoDeFechaVacioException;
	
}
