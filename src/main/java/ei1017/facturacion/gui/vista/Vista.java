package ei1017.facturacion.gui.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ei1017.facturacion.gui.controlador.Controlador;

public class Vista {
	private JFrame frame = null;
	private JLabel estado = null;
	private Controlador controlador;
	private ClientesListener clientesListener;
	private FacturasListener facturasListener;
	private LlamadasListener llamadasListener;
	private BotonesClientesListener botonesClientesListener;
	private BotonesFacturasListener botonesFacturasListener;
	private BotonesLlamadasListener botonesLlamadasListener;
	
	public Vista(Controlador controlador){
		this.controlador = controlador;
		frame = new JFrame("Facturación de Telefonía Móvil");
		clientesListener = new ClientesListener();
		facturasListener = new FacturasListener();
		llamadasListener = new LlamadasListener();
		botonesClientesListener = new BotonesClientesListener();
		botonesFacturasListener = new BotonesFacturasListener();
		botonesLlamadasListener = new BotonesLlamadasListener();
	}
	
	public void dibujaTablero() {
		anyadeMenu();
		anyadePanelEstado("Bienvenido a la aplicación.");
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);	
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
					nombrePanel.add(new JLabel("Nombre"));
					nombrePanel.add(new JTextField(20));
					frame.getContentPane().add(nombrePanel, BorderLayout.CENTER);
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel emailPanel = new JPanel();
					emailPanel.add(new JLabel("Email"));
					emailPanel.add(new JTextField(20));
					frame.getContentPane().add(emailPanel, BorderLayout.CENTER);
					
					JPanel poblacionPanel = new JPanel();
					poblacionPanel.add(new JLabel("Población"));
					poblacionPanel.add(new JTextField(20));
					frame.getContentPane().add(poblacionPanel, BorderLayout.CENTER);
					
					JPanel provinciaPanel = new JPanel();
					provinciaPanel.add(new JLabel("Provincia"));
					provinciaPanel.add(new JTextField(20));
					frame.getContentPane().add(provinciaPanel, BorderLayout.CENTER);
					
					JPanel cpPanel = new JPanel();
					cpPanel.add(new JLabel("Código postal"));
					cpPanel.add(new JTextField(20));
					frame.getContentPane().add(cpPanel, BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton anyadir = new JButton("Dar de alta");
					anyadir.setActionCommand("empresa");
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
					
					JPanel nombrePanel = new JPanel();
					nombrePanel.add(new JLabel("Nombre"));
					nombrePanel.add(new JTextField(20));
					frame.getContentPane().add(nombrePanel, BorderLayout.CENTER);
					
					JPanel apellidosPanel = new JPanel();
					apellidosPanel.add(new JLabel("Apellidos"));
					apellidosPanel.add(new JTextField(20));
					frame.getContentPane().add(apellidosPanel, BorderLayout.CENTER);
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel emailPanel = new JPanel();
					emailPanel.add(new JLabel("Email"));
					emailPanel.add(new JTextField(20));
					frame.getContentPane().add(emailPanel, BorderLayout.CENTER);
					
					JPanel poblacionPanel = new JPanel();
					poblacionPanel.add(new JLabel("Población"));
					poblacionPanel.add(new JTextField(20));
					frame.getContentPane().add(poblacionPanel, BorderLayout.CENTER);
					
					JPanel provinciaPanel = new JPanel();
					provinciaPanel.add(new JLabel("Provincia"));
					provinciaPanel.add(new JTextField(20));
					frame.getContentPane().add(provinciaPanel, BorderLayout.CENTER);
					
					JPanel cpPanel = new JPanel();
					cpPanel.add(new JLabel("Código postal"));
					cpPanel.add(new JTextField(20));
					frame.getContentPane().add(cpPanel, BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton anyadir = new JButton("Dar de alta");
					botonPanel.add(anyadir);
					anyadir.setActionCommand("Particular");
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
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton borrarButton = new JButton("Borrar");
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
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel eleccionTarifaPanel = new JPanel();
					JRadioButton basicaButton = new JRadioButton("Básica");
					JRadioButton deTardeButton = new JRadioButton("De tarde");
					JRadioButton dominogGratisButton = new JRadioButton("Domingo Gratis");
					ButtonGroup grupoTarifa = new ButtonGroup();
					grupoTarifa.add(basicaButton);
					grupoTarifa.add(deTardeButton);
					grupoTarifa.add(dominogGratisButton);
					eleccionTarifaPanel.add(basicaButton);
					eleccionTarifaPanel.add(deTardeButton);
					eleccionTarifaPanel.add(dominogGratisButton);
					frame.add(eleccionTarifaPanel);
					
					JPanel botonPanel = new JPanel();
					JButton borrarButton = new JButton("Cambiar tarifa");
					botonPanel.add(borrarButton);
					frame.getContentPane().add(borrarButton, BorderLayout.CENTER);
					
					anyadePanelEstado("Menu cliente");
					frame.pack();
					break;
				}
				case "datosnif" : {
					JPanel panelTitulo = new JPanel();
					JLabel titulo = new JLabel("Cambiar tarifa de clientes");
					panelTitulo.add(titulo);
					frame.getContentPane().add(panelTitulo, BorderLayout.NORTH);
					frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
					
					JPanel nifPanel = new JPanel();
					nifPanel.add(new JLabel("NIF"));
					nifPanel.add(new JTextField(20));
					frame.getContentPane().add(nifPanel, BorderLayout.CENTER);
					
					JPanel infoClientePanel = new JPanel();
					infoClientePanel.add(new JTextField("Informacion sobre cliente"));
					frame.getContentPane().add(infoClientePanel, BorderLayout.CENTER);
					
					JPanel botonPanel = new JPanel();
					JButton borrarButton = new JButton("Recuperar");
					botonPanel.add(borrarButton);
					frame.getContentPane().add(borrarButton, BorderLayout.CENTER);
					
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
					
					
					JPanel botonPanel = new JPanel();
					JButton borrarButton = new JButton("Listar");
					botonPanel.add(borrarButton);
					frame.getContentPane().add(borrarButton, BorderLayout.CENTER);
					
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
					
					JPanel botonPanel = new JPanel();
					JButton borrarButton = new JButton("Listar");
					botonPanel.add(borrarButton);
					frame.getContentPane().add(borrarButton, BorderLayout.CENTER);
					
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
			frame.removeAll();
			frame.revalidate();
			frame.repaint();

			String command = e.getActionCommand();
			switch(command) {
				case "emitir" : {
					
					break;
				}
				case "datosid" : {
					
					break;
				}
				case "listar" : {
					
					break;
				}
				case "2fechas" : {
					
					break;
				}
			}
		}
	}
	private class LlamadasListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.removeAll();
			frame.revalidate();
			frame.repaint();
			
			String command = e.getActionCommand();
			switch(command) {
				case "nueva" : {
					
					break;
				}
				case "listar" : {
					
					break;
				}
				case "2fechas" : {
					
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
				case "" : {
					
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
				case "" : {
					
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
				case "" : {
					
					break;
				}
			}
		}
		
	}
}
