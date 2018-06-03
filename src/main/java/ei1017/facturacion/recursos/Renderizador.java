package ei1017.facturacion.recursos;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* Es una clase que alivia el trabajo y disminuye líneas de código de la clase 
 * 	VistaImpl */

public class Renderizador {
	private Container contentPane;
	
	public Renderizador(Container contentPane) {
		this.contentPane = contentPane;
	}
	
	public void ventanaNuevoClienteEmpresa(JTextField[] datosCliente){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Crear nuevo cliente empresa");
		panelTitulo.add(titulo);
		
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JLabel nombreLabel = new JLabel("Nombre:");
		JLabel nifLabel = new JLabel("NIF:");
		JLabel emailLabel = new JLabel("Email:");
		JLabel poblacionLabel = new JLabel("Población:");
		JLabel provinciaLabel = new JLabel("Provincia:");
		JLabel cpLabel = new JLabel("Código postal:");
		
		JPanel nombrePanel = new JPanel();
		datosCliente[TipoCampo.NOMBRE.ordinal()] = new JTextField(20);
		nombreLabel.setMinimumSize(cpLabel.getMinimumSize());
		nombreLabel.setMaximumSize(cpLabel.getMaximumSize());
		nombreLabel.setPreferredSize(cpLabel.getPreferredSize());
		nombrePanel.add(nombreLabel);
		nombrePanel.add(datosCliente[TipoCampo.NOMBRE.ordinal()]);
		contentPane.add(nombrePanel, BorderLayout.CENTER);
		
		JPanel nifPanel = new JPanel();
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifLabel.setMinimumSize(cpLabel.getMinimumSize());
		nifLabel.setMaximumSize(cpLabel.getMaximumSize());
		nifLabel.setPreferredSize(cpLabel.getPreferredSize());
		nifPanel.add(nifLabel);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		JPanel emailPanel = new JPanel();
		datosCliente[TipoCampo.EMAIL.ordinal()] = new JTextField(20);
		emailLabel.setMinimumSize(cpLabel.getMinimumSize());
		emailLabel.setMaximumSize(cpLabel.getMaximumSize());
		emailLabel.setPreferredSize(cpLabel.getPreferredSize());
		emailPanel.add(emailLabel);
		emailPanel.add(datosCliente[TipoCampo.EMAIL.ordinal()]);
		contentPane.add(emailPanel, BorderLayout.CENTER);
		
		JPanel poblacionPanel = new JPanel();
		datosCliente[TipoCampo.POBLACION.ordinal()] = new JTextField(20);
		poblacionLabel.setMinimumSize(cpLabel.getMinimumSize());
		poblacionLabel.setMaximumSize(cpLabel.getMaximumSize());
		poblacionLabel.setPreferredSize(cpLabel.getPreferredSize());
		poblacionPanel.add(poblacionLabel);
		poblacionPanel.add(datosCliente[TipoCampo.POBLACION.ordinal()]);
		contentPane.add(poblacionPanel, BorderLayout.CENTER);
		
		JPanel provinciaPanel = new JPanel();
		datosCliente[TipoCampo.PROVINCIA.ordinal()] = new JTextField(20);
		provinciaLabel.setMinimumSize(cpLabel.getMinimumSize());
		provinciaLabel.setMaximumSize(cpLabel.getMaximumSize());
		provinciaLabel.setPreferredSize(cpLabel.getPreferredSize());
		provinciaPanel.add(provinciaLabel);
		provinciaPanel.add(datosCliente[TipoCampo.PROVINCIA.ordinal()]);
		contentPane.add(provinciaPanel, BorderLayout.CENTER);
		
		JPanel cpPanel = new JPanel();
		datosCliente[TipoCampo.CODIGO_POSTAL.ordinal()] = new JTextField(20);
		cpPanel.add(cpLabel);
		cpPanel.add(datosCliente[TipoCampo.CODIGO_POSTAL.ordinal()]);
		contentPane.add(cpPanel, BorderLayout.CENTER);
	}
	
	public void ventanaNuevoClienteParticular(JTextField[] datosCliente){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Crear nuevo cliente particular");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JLabel apellidosLabel = new JLabel("Apellidos:");
		JLabel nombreLabel = new JLabel("Nombre:");
		JLabel nifLabel = new JLabel("NIF:");
		JLabel emailLabel = new JLabel("Email:");
		JLabel poblacionLabel = new JLabel("Población:");
		JLabel provinciaLabel = new JLabel("Provincia:");
		JLabel cpLabel = new JLabel("Código postal:");
		
		JPanel apellidosPanel = new JPanel();
		datosCliente[TipoCampo.APELLIDOS.ordinal()] = new JTextField(20);
		apellidosLabel.setMinimumSize(cpLabel.getMinimumSize());
		apellidosLabel.setMaximumSize(cpLabel.getMaximumSize());
		apellidosLabel.setPreferredSize(cpLabel.getPreferredSize());
		apellidosPanel.add(apellidosLabel);
		apellidosPanel.add(datosCliente[TipoCampo.APELLIDOS.ordinal()]);
		contentPane.add(apellidosPanel, BorderLayout.CENTER);
		
		JPanel nombrePanel = new JPanel();
		datosCliente[TipoCampo.NOMBRE.ordinal()] = new JTextField(20);
		nombreLabel.setMinimumSize(cpLabel.getMinimumSize());
		nombreLabel.setMaximumSize(cpLabel.getMaximumSize());
		nombreLabel.setPreferredSize(cpLabel.getPreferredSize());
		nombrePanel.add(nombreLabel);
		nombrePanel.add(datosCliente[TipoCampo.NOMBRE.ordinal()]);
		contentPane.add(nombrePanel, BorderLayout.CENTER);
		
		JPanel nifPanel = new JPanel();
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifLabel.setMinimumSize(cpLabel.getMinimumSize());
		nifLabel.setMaximumSize(cpLabel.getMaximumSize());
		nifLabel.setPreferredSize(cpLabel.getPreferredSize());
		nifPanel.add(nifLabel);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		JPanel emailPanel = new JPanel();
		datosCliente[TipoCampo.EMAIL.ordinal()] = new JTextField(20);
		emailLabel.setMinimumSize(cpLabel.getMinimumSize());
		emailLabel.setMaximumSize(cpLabel.getMaximumSize());
		emailLabel.setPreferredSize(cpLabel.getPreferredSize());
		emailPanel.add(emailLabel);
		emailPanel.add(datosCliente[TipoCampo.EMAIL.ordinal()]);
		contentPane.add(emailPanel, BorderLayout.CENTER);
		
		JPanel poblacionPanel = new JPanel();
		datosCliente[TipoCampo.POBLACION.ordinal()] = new JTextField(20);
		poblacionLabel.setMinimumSize(cpLabel.getMinimumSize());
		poblacionLabel.setMaximumSize(cpLabel.getMaximumSize());
		poblacionLabel.setPreferredSize(cpLabel.getPreferredSize());
		poblacionPanel.add(poblacionLabel);
		poblacionPanel.add(datosCliente[TipoCampo.POBLACION.ordinal()]);
		contentPane.add(poblacionPanel, BorderLayout.CENTER);
		
		JPanel provinciaPanel = new JPanel();
		datosCliente[TipoCampo.PROVINCIA.ordinal()] = new JTextField(20);
		provinciaLabel.setMinimumSize(cpLabel.getMinimumSize());
		provinciaLabel.setMaximumSize(cpLabel.getMaximumSize());
		provinciaLabel.setPreferredSize(cpLabel.getPreferredSize());
		provinciaPanel.add(provinciaLabel);
		provinciaPanel.add(datosCliente[TipoCampo.PROVINCIA.ordinal()]);
		contentPane.add(provinciaPanel, BorderLayout.CENTER);
		
		JPanel cpPanel = new JPanel();
		datosCliente[TipoCampo.CODIGO_POSTAL.ordinal()] = new JTextField(20);
		cpPanel.add(cpLabel);
		cpPanel.add(datosCliente[TipoCampo.CODIGO_POSTAL.ordinal()]);
		contentPane.add(cpPanel, BorderLayout.CENTER);
	}
	
	public void ventanaBorrarCliente(JTextField[] datosCliente) {
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Borrar clientes");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		JPanel nifPanel = new JPanel();
		nifPanel.add(new JLabel("NIF:"));
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
	}
	
	public void ventanaCambiarTarifaCliente(JTextField[] datosCliente, JRadioButton[] tarifaButtons) {
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Cambiar tarifa de clientes");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		JPanel nifPanel = new JPanel();
		nifPanel.add(new JLabel("NIF:"));
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		JPanel eleccionTarifaPanel = new JPanel();
		tarifaButtons[TipoTarifa.BASICA.ordinal()] = new JRadioButton("Básica");
		tarifaButtons[TipoTarifa.DE_TARDE.ordinal()] = new JRadioButton("De tarde");
		tarifaButtons[TipoTarifa.DOMINGO_GRATIS.ordinal()] = new JRadioButton("Domingo Gratis");
		ButtonGroup grupoTarifa = new ButtonGroup();
		grupoTarifa.add(tarifaButtons[TipoTarifa.BASICA.ordinal()]);
		grupoTarifa.add(tarifaButtons[TipoTarifa.DE_TARDE.ordinal()]);
		grupoTarifa.add(tarifaButtons[TipoTarifa.DOMINGO_GRATIS.ordinal()]);
		eleccionTarifaPanel.add(tarifaButtons[TipoTarifa.BASICA.ordinal()]);
		eleccionTarifaPanel.add(tarifaButtons[TipoTarifa.DE_TARDE.ordinal()]);
		eleccionTarifaPanel.add(tarifaButtons[TipoTarifa.DOMINGO_GRATIS.ordinal()]);
		contentPane.add(eleccionTarifaPanel);
	}

	public void ventanaRecuperarDatosCliente(JTextField[] datosCliente, JTextArea[] listados){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Recuperar datos de un cliente");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		JPanel nifPanel = new JPanel();
		nifPanel.add(new JLabel("NIF:"));
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		listados[TipoListado.DATOS_CLIENTE.ordinal()] = new JTextArea();
		listados[TipoListado.DATOS_CLIENTE.ordinal()].setEditable(false);
		JPanel infoClientePanel = new JPanel();
		infoClientePanel.add(listados[TipoListado.DATOS_CLIENTE.ordinal()]);
		contentPane.add(infoClientePanel, BorderLayout.CENTER);
	}

	public void ventanaListadoClientes(JTextArea[] listados) {
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Listado de todos clientes");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		listados[TipoListado.CLIENTES.ordinal()] = new JTextArea();
		listados[TipoListado.CLIENTES.ordinal()].setEditable(false);
		JScrollPane barraScroll = new JScrollPane();
		listados[TipoListado.CLIENTES.ordinal()].add(barraScroll);
		contentPane.add(listados[TipoListado.CLIENTES.ordinal()], BorderLayout.CENTER);
	}

	public void ventanaClientesRegistadosPeriodo(){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Elija las fechas");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
	}
	
	public void ventanaEmitirFactura(JTextField[] datosCliente){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Elija el periodo");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel nifPanel = new JPanel();
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifPanel.add(new JLabel("NIF:"));
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
	}
	
	public void ventanaRecuperarDatosFactura(JTextField[] datosCliente, JTextArea[] listados){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Recuperar datos de una factura a partir de su ID");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JLabel nifLabel = new JLabel("NIF:");
		JLabel idLabel = new JLabel("ID de factura:");
		
		JPanel nifPanel = new JPanel();
		nifLabel.setMinimumSize(idLabel.getMinimumSize());
		nifLabel.setPreferredSize(idLabel.getPreferredSize());
		nifLabel.setMaximumSize(idLabel.getMaximumSize());
		nifPanel.add(nifLabel);
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		JPanel idFacturaPanel = new JPanel();
		idFacturaPanel.add(idLabel);
		datosCliente[TipoCampo.ID_FACTURA.ordinal()] = new JTextField(20);
		idFacturaPanel.add(datosCliente[TipoCampo.ID_FACTURA.ordinal()]);
		contentPane.add(idFacturaPanel, BorderLayout.CENTER);
		
		JPanel infoClientePanel = new JPanel();
		listados[TipoListado.DATOS_FACTURA.ordinal()] = new JTextArea();
		listados[TipoListado.DATOS_FACTURA.ordinal()].setEditable(false);
		infoClientePanel.add(listados[TipoListado.DATOS_FACTURA.ordinal()]);
		contentPane.add(infoClientePanel, BorderLayout.CENTER);
	}
	
	public void ventanaListarFacturas(JTextField[] datosCliente, JTextArea[] listados){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Listado de todas facturas");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel nifPanel = new JPanel();
		nifPanel.add(new JLabel("NIF:"));
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		listados[TipoListado.FACTURAS.ordinal()] = new JTextArea();
		JScrollPane barraScroll = new JScrollPane();
		barraScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listados[TipoListado.FACTURAS.ordinal()].add(barraScroll);
		listados[TipoListado.FACTURAS.ordinal()].setEditable(false);
		contentPane.add(listados[TipoListado.FACTURAS.ordinal()], BorderLayout.CENTER);
	}
	
	public void ventanaFacturasEmitidasPeriodo(JTextField[] datosCliente){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Elija las fechas");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel nifPanel = new JPanel();
		nifPanel.add(new JLabel("NIF:"));
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
	}

	public void ventanaNuevaLlamada(JTextField[] datosCliente){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Crear nuevo cliente empresa");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JLabel nifLabel = new JLabel("NIF:");
		JLabel numTelLabel = new JLabel("Número:");
		JLabel duracionLabel = new JLabel("Duración:");
		
		JPanel nifPanel = new JPanel();
		nifLabel.setMinimumSize(duracionLabel.getMinimumSize());
		nifLabel.setPreferredSize(duracionLabel.getPreferredSize());
		nifLabel.setMaximumSize(duracionLabel.getMaximumSize());
		nifPanel.add(nifLabel);
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		JPanel numeroPanel = new JPanel();
		numTelLabel.setMinimumSize(duracionLabel.getMinimumSize());
		numTelLabel.setPreferredSize(duracionLabel.getPreferredSize());
		numTelLabel.setMaximumSize(duracionLabel.getMaximumSize());
		numeroPanel.add(numTelLabel);
		datosCliente[TipoCampo.NUMERO_TELEFONO.ordinal()] = new JTextField(20);
		numeroPanel.add(datosCliente[TipoCampo.NUMERO_TELEFONO.ordinal()]);
		contentPane.add(numeroPanel, BorderLayout.CENTER);
		
		JPanel duracionPanel = new JPanel();
		duracionPanel.add(duracionLabel);
		datosCliente[TipoCampo.DURACION.ordinal()] = new JTextField(20);
		duracionPanel.add(datosCliente[TipoCampo.DURACION.ordinal()]);
		contentPane.add(duracionPanel, BorderLayout.CENTER);
	}

	public void ventanaListarLlamadasCliente(JTextField[] datosCliente, JTextArea[] listados){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Listado de todas facturas");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel nifPanel = new JPanel();
		nifPanel.add(new JLabel("NIF:"));
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
		
		listados[TipoListado.LLAMADAS.ordinal()] = new JTextArea();
		JScrollPane barraScroll = new JScrollPane();
		barraScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listados[TipoListado.LLAMADAS.ordinal()].add(barraScroll);
		listados[TipoListado.LLAMADAS.ordinal()].setEditable(false);
		contentPane.add(listados[TipoListado.LLAMADAS.ordinal()], BorderLayout.CENTER);
	}

	public void ventanaLlamadasRegistradasClientePeriodo(JTextField[] datosCliente){
		JPanel panelTitulo = new JPanel();
		JLabel titulo = new JLabel("Elija las fechas");
		panelTitulo.add(titulo);
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel nifPanel = new JPanel();
		nifPanel.add(new JLabel("NIF:"));
		datosCliente[TipoCampo.NIF.ordinal()] = new JTextField(20);
		nifPanel.add(datosCliente[TipoCampo.NIF.ordinal()]);
		contentPane.add(nifPanel, BorderLayout.CENTER);
	}
}
