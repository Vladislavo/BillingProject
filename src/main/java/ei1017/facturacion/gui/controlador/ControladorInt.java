package ei1017.facturacion.gui.controlador;

public interface ControladorInt {
	
	/* Clientes */
	public void nuevoClienteEmpresa();
	public void nuevoClienteParticular();
	public void borrarCliente();
	public void cambiarTarifaCliente();
	public void recuperarDatosCliente();
	public void listarDatosClientes();
	public void listarDatosClientesPeriodo();
	
	/* Facturas */
	public void emitirFactura();
	public void recuperarDatosFactura();
	public void listarDatosFacturasPeriodo();
	public void listarDatosFacturas();
	
	/* Llamadas */
	public void nuevaLlamada();
	public void listarLlamadasPeriodo();
	public void recuperarLlamadasCliente();
	
}
