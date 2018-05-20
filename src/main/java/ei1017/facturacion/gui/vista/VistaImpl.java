package ei1017.facturacion.gui.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import ei1017.facturacion.excepciones.CampoDeFechaVacioException;
import ei1017.facturacion.excepciones.CampoVacioException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherenteException;
import ei1017.facturacion.excepciones.TarifaNoElegidaException;
import ei1017.facturacion.gui.controlador.ControladorInt;
import ei1017.facturacion.gui.modelo.AlmacenDeDatosModeloImpl;
import ei1017.facturacion.gui.modelo.AlmacenDeDatosModeloInt;
import ei1017.facturacion.recursos.TipoCampo;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.recursos.Renderizador;
import ei1017.facturacion.recursos.TipoTarifa;
import ei1017.facturacion.recursos.TipoListado;

public class VistaImpl implements VistaInt, Serializable {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame = null;
	private JLabel estado = null;
	private ControladorInt controlador;
	private ClientesListener clientesListener;
	private FacturasListener facturasListener;
	private LlamadasListener llamadasListener;
	private BotonesClientesListener botonesClientesListener;
	private BotonesFacturasListener botonesFacturasListener;
	private BotonesLlamadasListener botonesLlamadasListener;
	private EscuchadorVentana escuchadorVentana;
	
	private Renderizador renderizador;
	
	private JTextField[] datosCliente;
	private JTextArea[] listados;
	private JRadioButton[] tarifaButtons;
	
	private JDatePickerImpl startPicker;
	private JDatePickerImpl endPicker;
	
	private AlmacenDeDatosModeloInt almacen;
	
	public VistaImpl(AlmacenDeDatosModeloInt almacen){
		this.almacen = almacen;
		frame = new JFrame("Facturación de Telefonía Móvil");
		renderizador = new Renderizador(frame.getContentPane());
		escuchadorVentana = new EscuchadorVentana();
		clientesListener = new ClientesListener();
		facturasListener = new FacturasListener();
		llamadasListener = new LlamadasListener();
		botonesClientesListener = new BotonesClientesListener();
		botonesFacturasListener = new BotonesFacturasListener();
		botonesLlamadasListener = new BotonesLlamadasListener();
		
		datosCliente = new JTextField[TipoCampo.values().length];
		listados = new JTextArea[TipoListado.values().length];
		tarifaButtons = new JRadioButton[TipoTarifa.values().length];
	}
	
	public void setControlador(ControladorInt controlador){
		this.controlador = controlador;
	}
	
	public void dibujaTablero() {
		anyadeMenu();
		anyadePanelEstado("Bienvenido a la aplicación.");
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.addWindowListener(escuchadorVentana);
	}

	private void anyadePanelEstado(String string) {
		JPanel panelEstado = new JPanel();
		estado = new JLabel(string);
		panelEstado.add(estado);
		frame.getContentPane().add(panelEstado, BorderLayout.SOUTH);
	}

	private void anyadeMenu() {
		// crear la barra de menu
		JMenuBar menubar = new JMenuBar();
		
		// crear desplegable de opciones de menu
		JMenu opcionesClientes = new JMenu("Clientes");
		// para que se pueda llamar por Alt+C
		opcionesClientes.setMnemonic(KeyEvent.VK_C);
		
		// crear item de menu
		JMenu darDeAltaSubmenu = new JMenu("Dar de alta");
		darDeAltaSubmenu.setMnemonic(KeyEvent.VK_N);
		// pequena explicacion del item
		darDeAltaSubmenu.setToolTipText("Dar de alta a un nuevo cliente.");
		// asignacion del comando para que MenuListener lo pueda reconocer
		JMenuItem clienteParticularItem = new JMenuItem("Particular");
		clienteParticularItem.setMnemonic(KeyEvent.VK_P);
		clienteParticularItem.setActionCommand("particular");
		clienteParticularItem.addActionListener(clientesListener);
		
		JMenuItem clienteEmpresaItem = new JMenuItem("Empresa");
		clienteEmpresaItem.setMnemonic(KeyEvent.VK_E);
		clienteEmpresaItem.setActionCommand("empresa");
		clienteEmpresaItem.addActionListener(clientesListener);
		
		darDeAltaSubmenu.add(clienteParticularItem);
		darDeAltaSubmenu.add(clienteEmpresaItem);
		
		// asigancion del actionlistner
		darDeAltaSubmenu.addActionListener(clientesListener);
		
		JMenuItem borrarClienteItem = new JMenuItem("Borrar");
		borrarClienteItem.setMnemonic(KeyEvent.VK_B);
		borrarClienteItem.setToolTipText("Borrar un cliente");
		borrarClienteItem.setActionCommand("borrar");
		borrarClienteItem.addActionListener(clientesListener);
		
		JMenuItem cambiarTarifaClienteItem = new JMenuItem("Cambiar tarifa");
		cambiarTarifaClienteItem.setMnemonic(KeyEvent.VK_T);
		cambiarTarifaClienteItem.setToolTipText("Cambiar la tarifa de un cliente.");
		cambiarTarifaClienteItem.setActionCommand("camtarifa");
		cambiarTarifaClienteItem.addActionListener(clientesListener);
		
		JMenuItem recuperarDatosClienteItem = new JMenuItem("Recuperar datos");
		recuperarDatosClienteItem.setMnemonic(KeyEvent.VK_R);
		recuperarDatosClienteItem.setToolTipText("Recuperar los datos de un cliente a partir de su NIF.");
		recuperarDatosClienteItem.setActionCommand("datosnif");
		recuperarDatosClienteItem.addActionListener(clientesListener);
		
		JMenuItem listarClientesItem = new JMenuItem("Obtener listado");
		listarClientesItem.setMnemonic(KeyEvent.VK_L);
		listarClientesItem.setToolTipText("Recuperar el listado de todos los clientes.");
		listarClientesItem.setActionCommand("listar");
		listarClientesItem.addActionListener(clientesListener);
		
		JMenuItem entreFechasClienteItem = new JMenuItem("Registrados en un período");
		entreFechasClienteItem.setMnemonic(KeyEvent.VK_F);
		entreFechasClienteItem.setToolTipText("Mostrar un listado de clientes que fueron dados de alta entre dos fechas.");
		entreFechasClienteItem.setActionCommand("2fechas");
		entreFechasClienteItem.addActionListener(clientesListener);
		
		opcionesClientes.add(darDeAltaSubmenu);
		opcionesClientes.add(recuperarDatosClienteItem);
		opcionesClientes.add(listarClientesItem);
		opcionesClientes.add(cambiarTarifaClienteItem);
		opcionesClientes.add(entreFechasClienteItem);
		opcionesClientes.add(borrarClienteItem);
		
		
		JMenu opcionesFacturas = new JMenu("Facturas");
		
		JMenuItem emitirFacturaItem = new JMenuItem("Emitir");
		emitirFacturaItem.setMnemonic(KeyEvent.VK_E);
		emitirFacturaItem.setToolTipText("Emitir una factura para un cliente, calculando el importe de la misma en función de las llamadas.");
		emitirFacturaItem.setActionCommand("emitir");
		emitirFacturaItem.addActionListener(facturasListener);
		
		JMenuItem recuperarDatosFacturaItem = new JMenuItem("Recuperar datos");
		recuperarDatosFacturaItem.setMnemonic(KeyEvent.VK_R);
		recuperarDatosFacturaItem.setToolTipText("Recuperar los datos de una factura a partir de su código.");
		recuperarDatosFacturaItem.setActionCommand("datosid");
		recuperarDatosFacturaItem.addActionListener(facturasListener);
		
		JMenuItem listarFacturasItem = new JMenuItem("Listar");
		listarFacturasItem.setMnemonic(KeyEvent.VK_L);
		listarFacturasItem.setToolTipText("Recuperar todas las facturas de un cliente.");
		listarFacturasItem.setActionCommand("listar");
		listarFacturasItem.addActionListener(facturasListener);
		
		JMenuItem entre2FechasFacturasItem = new JMenuItem("Emitidas en un período");
		entre2FechasFacturasItem.setMnemonic(KeyEvent.VK_P);
		entre2FechasFacturasItem.setToolTipText("Mostrar un listado de facturas de un cliente emitidas entre dos fechas.");
		entre2FechasFacturasItem.setActionCommand("2fechas");
		entre2FechasFacturasItem.addActionListener(facturasListener);
		
		opcionesFacturas.add(emitirFacturaItem);
		opcionesFacturas.add(recuperarDatosFacturaItem);
		opcionesFacturas.add(listarFacturasItem);
		opcionesFacturas.add(entre2FechasFacturasItem);
		
		
		JMenu opcionesLlamadas = new JMenu("Llamadas");
		
		JMenuItem nuevaLlamadaItem = new JMenuItem("Dar de alta");
		nuevaLlamadaItem.setMnemonic(KeyEvent.VK_N);
		nuevaLlamadaItem.setToolTipText("Dar de alta una llamada.");
		nuevaLlamadaItem.setActionCommand("nueva");
		nuevaLlamadaItem.addActionListener(llamadasListener);
		
		JMenuItem listarLlamadasItem = new JMenuItem("Listar");
		listarLlamadasItem.setMnemonic(KeyEvent.VK_L);
		listarLlamadasItem.setToolTipText("Listar todas las llamadas del cliente.");
		listarLlamadasItem.setActionCommand("listar");
		listarLlamadasItem.addActionListener(llamadasListener);
		
		JMenuItem llamadas2fechasItem = new JMenuItem("Registradas en un período");
		llamadas2fechasItem.setMnemonic(KeyEvent.VK_P);
		llamadas2fechasItem.setToolTipText("Mostrar un listado de llamadas de un cliente que fueron realizadas entre dos fechas.");
		llamadas2fechasItem.setActionCommand("2fechas");
		llamadas2fechasItem.addActionListener(llamadasListener);
		
		opcionesLlamadas.add(nuevaLlamadaItem);
		opcionesLlamadas.add(listarLlamadasItem);
		opcionesLlamadas.add(llamadas2fechasItem);
		
		menubar.add(opcionesClientes);
		menubar.add(opcionesFacturas);
		menubar.add(opcionesLlamadas);
		
		frame.add(menubar);
		
		frame.getContentPane().add(menubar, BorderLayout.NORTH);
	}
	
	
	public String getCampo(TipoCampo campo) throws CampoVacioException {
		if(datosCliente[campo.ordinal()].getText().trim().equals(""))
			throw new CampoVacioException("Campo " + campo.toString() + " está vacío");
		return datosCliente[campo.ordinal()].getText();
	}
	
	public void setDatosRecuperadosCliente(String info){
		listados[TipoListado.DATOS_CLIENTE.ordinal()].setText(info);
		frame.pack();
	}
	public void setDatosRecuperadosFactura(String info){
		listados[TipoListado.DATOS_FACTURA.ordinal()].setText(info);
		frame.pack();
	}
	
	public TipoTarifa botonSeleccionado() throws TarifaNoElegidaException{
		for(TipoTarifa tipo : TipoTarifa.values()){
			if(tarifaButtons[tipo.ordinal()].isSelected()){
				return tipo;
			}
		}
		throw new TarifaNoElegidaException();
	}
	
	public Periodo getPeriodo() throws 
			PeriodoDeTiempoIncoherenteException, CampoDeFechaVacioException{
		return new Periodo((Date) startPicker.getModel().getValue(),
				(Date) endPicker.getModel().getValue());
	}
	
	public void actualizarEstadoGlobal(String estado){
		this.estado.setForeground(Color.black);
		this.estado.setText(estado);
	}
	public void actualizarEstadoError(String error) {
		this.estado.setForeground(Color.red);
		this.estado.setText(error);
	}
	
	public void actualizarListadoClientes(String listado){
		this.listados[TipoListado.CLIENTES.ordinal()].setText(listado);
		frame.getContentPane().repaint();
		frame.pack();
	}
	public void actualizarListadoFacturas(String listado){
		this.listados[TipoListado.FACTURAS.ordinal()].setText(listado);
		frame.getContentPane().repaint();
		frame.pack();
	}
	public void actualizarListadoLlamadas(String listado){
		this.listados[TipoListado.LLAMADAS.ordinal()].setText(listado);
		frame.getContentPane().repaint();
		frame.pack();
	}
	
	private class ClientesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
			anyadeMenu();
			
			String command = e.getActionCommand();
			switch(command) {
				case "empresa" : {
					renderizador.ventanaNuevoClienteEmpresa(datosCliente);
					
					JPanel botonPanel = new JPanel();
					JButton anyadir = new JButton("Dar de alta");
					anyadir.setActionCommand("emp");
					anyadir.addActionListener(botonesClientesListener);
					botonPanel.add(anyadir);
					frame.getContentPane().add(botonPanel, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "particular" : {
					renderizador.ventanaNuevoClienteParticular(datosCliente);
					
					JPanel botonPanel = new JPanel();
					JButton anyadir = new JButton("Dar de alta");
					botonPanel.add(anyadir);
					anyadir.setActionCommand("part");
					anyadir.addActionListener(botonesClientesListener);
					frame.getContentPane().add(botonPanel, BorderLayout.CENTER);

					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "borrar" : {
					renderizador.ventanaBorrarCliente(datosCliente);
					
					JPanel botonPanel = new JPanel();
					JButton borrarButton = new JButton("Borrar");
					borrarButton.setActionCommand("borCli");
					borrarButton.addActionListener(botonesClientesListener);
					botonPanel.add(borrarButton);
					frame.getContentPane().add(borrarButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "camtarifa" : {
					renderizador.ventanaCambiarTarifaCliente(datosCliente, tarifaButtons);
					
					JPanel botonPanel = new JPanel();
					JButton cambiarTarifaButton = new JButton("Cambiar tarifa");
					cambiarTarifaButton.setActionCommand("camTar");
					cambiarTarifaButton.addActionListener(botonesClientesListener);
					botonPanel.add(cambiarTarifaButton);
					frame.getContentPane().add(cambiarTarifaButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "datosnif" : {
					renderizador.ventanaRecuperarDatosCliente(datosCliente, listados);
					
					JPanel botonPanel = new JPanel();
					JButton recuperarClientesButton = new JButton("Recuperar");
					recuperarClientesButton.setActionCommand("listCli");
					recuperarClientesButton.addActionListener(botonesClientesListener);
					botonPanel.add(recuperarClientesButton);
					frame.getContentPane().add(recuperarClientesButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "listar" : {
					renderizador.ventanaListadoClientes(listados);
					
					controlador.listarDatosClientes();
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "2fechas" : {
					renderizador.ventanaClientesRegistadosPeriodo();
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");

					UtilDateModel startModel = new UtilDateModel();
					UtilDateModel endModel = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(startModel, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(endModel, p);
					startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
					frame.getContentPane().add(desdeLabel, BorderLayout.CENTER);
					frame.getContentPane().add(startPicker, BorderLayout.CENTER);
					frame.getContentPane().add(hastaLabel, BorderLayout.CENTER);
					frame.getContentPane().add(endPicker, BorderLayout.CENTER);
					
					listados[TipoListado.CLIENTES.ordinal()] = new JTextArea();
					JScrollPane barraScroll = new JScrollPane();
					listados[TipoListado.CLIENTES.ordinal()].add(barraScroll);
					frame.getContentPane().add(listados[TipoListado.CLIENTES.ordinal()], 
							BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton fechasClientesButton = new JButton("Listar");
					fechasClientesButton.setActionCommand("fechCli");
					fechasClientesButton.addActionListener(botonesClientesListener);
					botonPanel.add(fechasClientesButton);
					frame.getContentPane().add(fechasClientesButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
			}
		}
	}
	
	private class FacturasListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
			anyadeMenu();

			String command = e.getActionCommand();
			switch(command) {
				case "emitir" : {
					renderizador.ventanaEmitirFactura(datosCliente);
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");
					JLabel datePickerLabel = new JLabel();
					UtilDateModel startModel = new UtilDateModel();
					UtilDateModel endModel = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(startModel, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(endModel, p);
					startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
					datePickerLabel.add(startPanel);
					frame.getContentPane().add(desdeLabel, BorderLayout.CENTER);
					frame.getContentPane().add(startPicker, BorderLayout.CENTER);
					frame.getContentPane().add(hastaLabel, BorderLayout.CENTER);
					frame.getContentPane().add(endPicker, BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton emitirFacturaButton = new JButton("Emitir");
					emitirFacturaButton.setActionCommand("emFac");
					emitirFacturaButton.addActionListener(botonesFacturasListener);
					botonPanel.add(emitirFacturaButton);
					frame.getContentPane().add(emitirFacturaButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu facturas");
					frame.pack();
					
					break;
				}
				case "datosid" : {
					renderizador.ventanaRecuperarDatosFactura(datosCliente, listados);
					
					JPanel botonPanel = new JPanel();
					JButton recuperarDatosFacturasButton = new JButton("Recuperar");
					recuperarDatosFacturasButton.setActionCommand("facDat");
					recuperarDatosFacturasButton.addActionListener(botonesFacturasListener);
					botonPanel.add(recuperarDatosFacturasButton);
					frame.getContentPane().add(recuperarDatosFacturasButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu factura");
					frame.pack();
					
					break;
				}
				case "listar" : {
					renderizador.ventanaListarFacturas(datosCliente, listados);
					
					JPanel botonPanel = new JPanel();
					JButton nuevaLlamadaButton = new JButton("Listar");
					nuevaLlamadaButton.setActionCommand("lisFac");
					nuevaLlamadaButton.addActionListener(botonesFacturasListener);
					botonPanel.add(nuevaLlamadaButton);
					frame.getContentPane().add(nuevaLlamadaButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu facturas");
					frame.pack();
					break;
				}
				case "2fechas" : {
					renderizador.ventanaFacturasEmitidasPeriodo(datosCliente);
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");
					JLabel datePickerLabel = new JLabel();
					UtilDateModel startModel = new UtilDateModel();
					UtilDateModel endModel = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(startModel, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(endModel, p);
					startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
					datePickerLabel.add(startPanel);
					frame.getContentPane().add(desdeLabel, BorderLayout.CENTER);
					frame.getContentPane().add(startPicker, BorderLayout.CENTER);
					frame.getContentPane().add(hastaLabel, BorderLayout.CENTER);
					frame.getContentPane().add(endPicker, BorderLayout.CENTER);
					
					listados[TipoListado.FACTURAS.ordinal()] = new JTextArea();
					JScrollPane barraScrol = new JScrollPane();
					barraScrol.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					listados[TipoListado.FACTURAS.ordinal()].add(barraScrol);
					listados[TipoListado.FACTURAS.ordinal()].setEditable(false);
					frame.getContentPane().add(listados[TipoListado.FACTURAS.ordinal()], BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton fechasFacturasButton = new JButton("Listar");
					fechasFacturasButton.setActionCommand("fechFac");
					fechasFacturasButton.addActionListener(botonesFacturasListener);
					botonPanel.add(fechasFacturasButton);
					frame.getContentPane().add(fechasFacturasButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
			}
		}
	}
	
	private class LlamadasListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
			anyadeMenu();
			
			String command = e.getActionCommand();
			switch(command) {
				case "nueva" : {
					renderizador.ventanaNuevaLlamada(datosCliente);
					
					JPanel botonPanel = new JPanel();
					JButton nuevaLlamadaButton = new JButton("Dar de alta");
					nuevaLlamadaButton.setActionCommand("nueLla");
					nuevaLlamadaButton.addActionListener(botonesLlamadasListener);
					botonPanel.add(nuevaLlamadaButton);
					frame.getContentPane().add(nuevaLlamadaButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu llamadas");
					frame.pack();
					break;
				}
				case "listar" : {
					renderizador.ventanaListarLlamadasCliente(datosCliente, listados);
					
					JPanel botonPanel = new JPanel();
					JButton nuevaLlamadaButton = new JButton("Listar");
					nuevaLlamadaButton.setActionCommand("lisLlam");
					nuevaLlamadaButton.addActionListener(botonesLlamadasListener);
					botonPanel.add(nuevaLlamadaButton);
					frame.getContentPane().add(nuevaLlamadaButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu llamadas");
					frame.pack();
					break;
				}
				case "2fechas" : {
					renderizador.ventanaLlamadasRegistradasClientePeriodo(datosCliente);
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");
					JLabel datePickerLabel = new JLabel();
					UtilDateModel startModel = new UtilDateModel();
					UtilDateModel endModel = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(startModel, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(endModel, p);
					startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
					datePickerLabel.add(startPanel);
					frame.getContentPane().add(desdeLabel, BorderLayout.CENTER);
					frame.getContentPane().add(startPicker, BorderLayout.CENTER);
					frame.getContentPane().add(hastaLabel, BorderLayout.CENTER);
					frame.getContentPane().add(endPicker, BorderLayout.CENTER);
					
					listados[TipoListado.LLAMADAS.ordinal()] = new JTextArea();
					JScrollPane barraScrol = new JScrollPane();
					listados[TipoListado.LLAMADAS.ordinal()].setEditable(false);
					barraScrol.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					listados[TipoListado.LLAMADAS.ordinal()].add(barraScrol);
					frame.getContentPane().add(listados[TipoListado.LLAMADAS.ordinal()], BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton fechasLlamadasButton = new JButton("Listar");
					fechasLlamadasButton.setActionCommand("fechLla");
					fechasLlamadasButton.addActionListener(botonesLlamadasListener);
					botonPanel.add(fechasLlamadasButton);
					frame.getContentPane().add(fechasLlamadasButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
			}
		}
	}
	
	private class BotonesClientesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			switch (command) {
				case "emp" : {
					controlador.nuevoClienteEmpresa();
					break;
				}
				case "part" : {
					controlador.nuevoClienteParticular();
					break;
				}
				case "borCli" : {
					controlador.borrarCliente();
					break;
				}
				case "camTar" : {
					controlador.cambiarTarifaCliente();
					break;
				}
				case "listCli" : {
					controlador.recuperarDatosCliente();
					break;
				}
				case "fechCli" : {
					controlador.listarDatosClientesPeriodo();
					break;
				}
			}
		}
		
	}
	
	private class BotonesFacturasListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			switch (command) {
				case "emFac" : {
					controlador.emitirFactura();
					break;
				}
				case "facDat" : {
					controlador.recuperarDatosFactura();
					break;
				}
				case "fechFac" : {
					controlador.listarDatosFacturasPeriodo();
					break;
				}
				case "lisFac" : {
					controlador.listarDatosFacturas();
					break;
				}
			}
		}
		
	}
	
	private class BotonesLlamadasListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			switch (command) {
				case "nueLla" : {
					controlador.nuevaLlamada();
					break;
				}
				case "fechLla" : {
					controlador.listarLlamadasPeriodo();
					break;
				}
				case "lisLlam" : {
					controlador.recuperarLlamadasCliente();
					break;
				}
			}
		}
	}
	
	private class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		
		private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }
	        return "";
	    }
	}
	
	private class EscuchadorVentana extends WindowAdapter {
		 @Override
		    public void windowClosing(WindowEvent e) {
		        AlmacenDeDatosModeloImpl.guardarDatosAFichero(almacen);
			 	System.exit(0);
		    }
	}
}
