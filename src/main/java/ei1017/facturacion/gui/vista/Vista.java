package ei1017.facturacion.gui.vista;

import java.awt.BorderLayout;
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

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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

import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.gui.controlador.Controlador;
import ei1017.facturacion.gui.modelo.AlmacenDeDatos;
import ei1017.facturacion.recursos.Campos;
import ei1017.facturacion.recursos.MarcasPeriodo;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.recursos.TipoTarifa;
import ei1017.facturacion.recursos.TiposListados;

public class Vista implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame = null;
	private JLabel estado = null;
	private Controlador controlador;
	private ClientesListener clientesListener;
	private FacturasListener facturasListener;
	private LlamadasListener llamadasListener;
	private BotonesClientesListener botonesClientesListener;
	private BotonesFacturasListener botonesFacturasListener;
	private BotonesLlamadasListener botonesLlamadasListener;
	private EscuchadorVentana escuchadorVentana;
	
	private JTextField[] datosCliente;
	private JTextArea[] listados;
	private JRadioButton[] tarifaButtons;
	
	private JDatePickerImpl startPicker;
	private JDatePickerImpl endPicker;
	
	private AlmacenDeDatos almacen; // modelo
	
	public Vista(AlmacenDeDatos almacen){
		this.almacen = almacen;
		frame = new JFrame("Facturación de Telefonía Móvil");
		escuchadorVentana = new EscuchadorVentana();
		clientesListener = new ClientesListener();
		facturasListener = new FacturasListener();
		llamadasListener = new LlamadasListener();
		botonesClientesListener = new BotonesClientesListener();
		botonesFacturasListener = new BotonesFacturasListener();
		botonesLlamadasListener = new BotonesLlamadasListener();
		
		datosCliente = new JTextField[Campos.values().length];
		listados = new JTextArea[TiposListados.values().length];
		tarifaButtons = new JRadioButton[TipoTarifa.values().length];
	}
	
	public void setControlador(Controlador controlador){
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
	
	public void actualizarEstadoGlobal(String estado){
		this.estado.setText(estado);
	}
	
	public void actualizarListadoClientes(String listado){
		this.listados[TiposListados.CLIENTES.ordinal()].setText(listado);
		frame.getContentPane().repaint();
		frame.pack();
	}
	public void actualizarListadoFacturas(String listado){
		this.listados[TiposListados.FACTURAS.ordinal()].setText(listado);
	}
	public void actualizarListadoLlamadas(String listado){
		this.listados[TiposListados.LLAMADAS.ordinal()].setText(listado);
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
	
	
	public JTextField getCampo(int posicion){
		return datosCliente[posicion];
	}
	public void setDatosRecuperadosCliente(String info){
		listados[TiposListados.DATOS_CLIENTE.ordinal()].setText(info);
		frame.pack();
	}
	public void setDatosRecuperadosFactura(String info){
		listados[TiposListados.DATOS_FACTURA.ordinal()].setText(info);
		frame.pack();
	}
	public TipoTarifa botonSeleccionado(){
		for(TipoTarifa tipo : TipoTarifa.values()){
			if(tarifaButtons[tipo.ordinal()].isSelected()){
				return tipo;
			}
		}
		return null;
	}
	public Periodo getPeriodo(){
		return new Periodo((Date) startPicker.getModel().getValue(),
				(Date) endPicker.getModel().getValue());
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Crear nuevo cliente empresa");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JPanel nombrePanel = new JPanel();
					datosCliente[Campos.NOMBRE.ordinal()] = new JTextField(20);
					nombrePanel.add(new JLabel("Nombre"));
					nombrePanel.add(datosCliente[Campos.NOMBRE.ordinal()]);
					frame.getContentPane().add(nombrePanel, BorderLayout.CENTER);
					
					JPanel nifPanel = new JPanel();
					datosCliente[Campos.NIF.ordinal()] = new JTextField(20);
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(datosCliente[Campos.NIF.ordinal()]);
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel emailPanel = new JPanel();
					datosCliente[Campos.EMAIL.ordinal()] = new JTextField(20);
					emailPanel.add(new JLabel("Email"));
					emailPanel.add(datosCliente[Campos.EMAIL.ordinal()]);
					frame.getContentPane().add(emailPanel, BorderLayout.CENTER);
					
					JPanel poblacionPanel = new JPanel();
					datosCliente[Campos.POBLACION.ordinal()] = new JTextField(20);
					poblacionPanel.add(new JLabel("Población"));
					poblacionPanel.add(datosCliente[Campos.POBLACION.ordinal()]);
					frame.getContentPane().add(poblacionPanel, BorderLayout.CENTER);
					
					JPanel provinciaPanel = new JPanel();
					datosCliente[Campos.PROVINCIA.ordinal()] = new JTextField(20);
					provinciaPanel.add(new JLabel("Provincia"));
					provinciaPanel.add(datosCliente[Campos.PROVINCIA.ordinal()]);
					frame.getContentPane().add(provinciaPanel, BorderLayout.CENTER);
					
					JPanel cpPanel = new JPanel();
					datosCliente[Campos.CODIGO_POSTAL.ordinal()] = new JTextField(20);
					cpPanel.add(new JLabel("Código postal"));
					cpPanel.add(datosCliente[Campos.CODIGO_POSTAL.ordinal()]);
					frame.getContentPane().add(cpPanel, BorderLayout.CENTER);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Crear nuevo cliente particular");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					datosCliente[Campos.APELLIDOS.ordinal()] = new JTextField(20);
					JPanel apellidosPanel = new JPanel();
					apellidosPanel.add(new JLabel("Apellidos"));
					apellidosPanel.add(datosCliente[Campos.APELLIDOS.ordinal()]);
					frame.getContentPane().add(apellidosPanel, BorderLayout.CENTER);
					
					JPanel nombrePanel = new JPanel();
					datosCliente[Campos.NOMBRE.ordinal()] = new JTextField(20);
					nombrePanel.add(new JLabel("Nombre"));
					nombrePanel.add(datosCliente[Campos.NOMBRE.ordinal()]);
					frame.getContentPane().add(nombrePanel, BorderLayout.CENTER);
					
					JPanel nifPanel = new JPanel();
					datosCliente[Campos.NIF.ordinal()] = new JTextField(20);
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(datosCliente[Campos.NIF.ordinal()]);
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel emailPanel = new JPanel();
					datosCliente[Campos.EMAIL.ordinal()] = new JTextField(20);
					emailPanel.add(new JLabel("Email"));
					emailPanel.add(datosCliente[Campos.EMAIL.ordinal()]);
					frame.getContentPane().add(emailPanel, BorderLayout.CENTER);
					
					JPanel poblacionPanel = new JPanel();
					datosCliente[Campos.POBLACION.ordinal()] = new JTextField(20);
					poblacionPanel.add(new JLabel("Población"));
					poblacionPanel.add(datosCliente[Campos.POBLACION.ordinal()]);
					frame.getContentPane().add(poblacionPanel, BorderLayout.CENTER);
					
					JPanel provinciaPanel = new JPanel();
					datosCliente[Campos.PROVINCIA.ordinal()] = new JTextField(20);
					provinciaPanel.add(new JLabel("Provincia"));
					provinciaPanel.add(datosCliente[Campos.PROVINCIA.ordinal()]);
					frame.getContentPane().add(provinciaPanel, BorderLayout.CENTER);
					
					JPanel cpPanel = new JPanel();
					datosCliente[Campos.CODIGO_POSTAL.ordinal()] = new JTextField(20);
					cpPanel.add(new JLabel("Código postal"));
					cpPanel.add(datosCliente[Campos.CODIGO_POSTAL.ordinal()]);
					frame.getContentPane().add(cpPanel, BorderLayout.CENTER);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Borrar clientes");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					datosCliente[Campos.NIF.ordinal()] = new JTextField(20);
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(datosCliente[Campos.NIF.ordinal()]);
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Cambiar tarifa de clientes");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					datosCliente[Campos.NIF.ordinal()] = new JTextField(20);
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(datosCliente[Campos.NIF.ordinal()]);
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
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
					frame.add(eleccionTarifaPanel);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Recuperar datos de un cliente");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					datosCliente[Campos.NIF.ordinal()] = new JTextField(20);
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(datosCliente[Campos.NIF.ordinal()]);
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					listados[TiposListados.DATOS_CLIENTE.ordinal()] = new JTextArea();
					listados[TiposListados.DATOS_CLIENTE.ordinal()].setEditable(false);
					JPanel infoClientePanel = new JPanel();
					infoClientePanel.add(listados[TiposListados.DATOS_CLIENTE.ordinal()]);
					frame.getContentPane().add(infoClientePanel, BorderLayout.CENTER);
					//datosCliente[Campos.DATOS_CLIENTE.ordinal()].setVisible(false);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Listado de todos clientes");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					listados[TiposListados.CLIENTES.ordinal()] = new JTextArea();
					listados[TiposListados.CLIENTES.ordinal()].setEditable(false);
					JScrollPane barraScrol = new JScrollPane();
					listados[TiposListados.CLIENTES.ordinal()].add(barraScrol);
					
					frame.getContentPane().add(listados[TiposListados.CLIENTES.ordinal()], BorderLayout.CENTER);
					controlador.listarDatosClientes();
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "2fechas" : {
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Elija las fechas");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");
					JLabel datePickerLabel = new JLabel();
					UtilDateModel model = new UtilDateModel();
					UtilDateModel endModel = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(model, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(endModel, p);
					// Don't know about the formatter, but there it is...
					startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
					datePickerLabel.add(startPanel);
					datePickerLabel.add(endPanel);
					frame.getContentPane().add(desdeLabel, BorderLayout.CENTER);
					frame.getContentPane().add(startPicker, BorderLayout.CENTER);
					frame.getContentPane().add(hastaLabel, BorderLayout.CENTER);
					frame.getContentPane().add(endPicker, BorderLayout.CENTER);
					
					listados[TiposListados.CLIENTES.ordinal()] = new JTextArea();
					JScrollPane barraScroll = new JScrollPane();
					listados[TiposListados.CLIENTES.ordinal()].add(barraScroll);
					//listados[TiposListados.CLIENTES.ordinal()].setText("");
					frame.getContentPane().add(listados[TiposListados.CLIENTES.ordinal()], 
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Elija el periodo");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");
					JLabel datePickerLabel = new JLabel();
					UtilDateModel model = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(model, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(model, p);
					// Don't know about the formatter, but there it is...
					JDatePickerImpl startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					JDatePickerImpl endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
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
					
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Recuperar datos de una factura a partir de su ID");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("ID de factura"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel infoClientePanel = new JPanel();
					infoClientePanel.add(new JTextField("Informacion sobre la factura"));
					frame.getContentPane().add(infoClientePanel, BorderLayout.CENTER);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Listado de todas facturas");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JTextArea listado = new JTextArea();
					JScrollPane barraScrol = new JScrollPane();
					listado.add(barraScrol);
					listado.setText("aqui van facturas");
					frame.getContentPane().add(listado, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu facturas");
					frame.pack();
					break;
				}
				case "2fechas" : {
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Elija las fechas");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");
					JLabel datePickerLabel = new JLabel();
					UtilDateModel model = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(model, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(model, p);
					// Don't know about the formatter, but there it is...
					startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
					datePickerLabel.add(startPanel);
					frame.getContentPane().add(desdeLabel, BorderLayout.CENTER);
					frame.getContentPane().add(startPicker, BorderLayout.CENTER);
					frame.getContentPane().add(hastaLabel, BorderLayout.CENTER);
					frame.getContentPane().add(endPicker, BorderLayout.CENTER);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Crear nuevo cliente empresa");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel numeroPanel = new JPanel();
					numeroPanel.add(new JLabel("Número"));
					numeroPanel.add(new JTextField(20));
					frame.getContentPane().add(numeroPanel, BorderLayout.CENTER);
					
					JPanel duracionPanel = new JPanel();
					duracionPanel.add(new JLabel("Duración"));
					duracionPanel.add(new JTextField(20));
					frame.getContentPane().add(duracionPanel, BorderLayout.CENTER);
					
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
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Listado de todas facturas");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JTextArea listado = new JTextArea();
					JScrollPane barraScrol = new JScrollPane();
					barraScrol.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					listado.add(barraScrol);
					listado.setText("aqui van llamadas");
					frame.getContentPane().add(listado, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu llamadas");
					frame.pack();
					break;
				}
				case "2fechas" : {
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Elija las fechas");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JLabel desdeLabel = new JLabel("Desde: ");
					JLabel hastaLabel = new JLabel("Hasta: ");
					JLabel datePickerLabel = new JLabel();
					UtilDateModel model = new UtilDateModel();
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl startPanel = new JDatePanelImpl(model, p);
					JDatePanelImpl endPanel = new JDatePanelImpl(model, p);
					// Don't know about the formatter, but there it is...
					JDatePickerImpl startPicker = new JDatePickerImpl(startPanel, new DateLabelFormatter());
					JDatePickerImpl endPicker = new JDatePickerImpl(endPanel, new DateLabelFormatter());
					datePickerLabel.add(startPanel);
					frame.getContentPane().add(desdeLabel, BorderLayout.CENTER);
					frame.getContentPane().add(startPicker, BorderLayout.CENTER);
					frame.getContentPane().add(hastaLabel, BorderLayout.CENTER);
					frame.getContentPane().add(endPicker, BorderLayout.CENTER);
					
					JTextArea listado = new JTextArea();
					JScrollPane barraScrol = new JScrollPane();
					barraScrol.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					listado.add(barraScrol);
					listado.setText("aqui van llamadas");
					frame.getContentPane().add(listado, BorderLayout.CENTER);
					
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
					try {
						controlador.listarDatosCliente();
					} catch (ClienteNoEncontradoException e1) {
						e1.printStackTrace();
					}
					break;
				}
				case "fechCli" : {
					controlador.listarDatosEntreFechasCliente();
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
					
					break;
				}
				case "facDat" : {
					
					break;
				}
				case "fechFac" : {
					
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
					
					break;
				}
				case "fechLla" : {
					
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
		        AlmacenDeDatos.guardarDatosAFichero(almacen);
			 	System.exit(0);
		    }
	}
}
