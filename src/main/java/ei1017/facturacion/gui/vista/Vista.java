package ei1017.facturacion.gui.vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ei1017.facturacion.gui.controlador.Controlador;

public class Vista {
	private JFrame frame = null;
	private JLabel estado = null;
	private Controlador controlador;
	private ClientesListener clientesListener;
	private FacturasListener facturasListener;
	private LlamadasListener llamadasListener;
	
	
	Vista(Controlador controlador){
		this.controlador = controlador;
		frame = new JFrame("Facturación de Telefonía Móvil");
		clientesListener = new ClientesListener();
		facturasListener = new FacturasListener();
		llamadasListener = new LlamadasListener();
		dibujaTablero();
	}
	
	public void dibujaTablero() {
		anyadeMenu();
		anyadePanelEstado("Estado actual");
		frame.setSize(500, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setVisible(true);	
	}

	private void anyadePanelEstado(String string) {
		
	}

	private void anyadeMenu() {
		// crear la barra de menu
		JMenuBar menubar = new JMenuBar();
		
		// crear desplegable de opciones de menu
		JMenu opcionesClientes = new JMenu("Clientes");
		// para que se pueda llamar por Alt+C
		opcionesClientes.setMnemonic(KeyEvent.VK_C);
		
		// crear item de menu
		JMenuItem darDeAltaItem = new JMenuItem("Dar de alta");
		darDeAltaItem.setMnemonic(KeyEvent.VK_N);
		// pequena explicacion del item
		darDeAltaItem.setToolTipText("Dar de alta a un nuevo cliente.");
		// asignacion del comando para que MenuListener lo pueda reconocer
		darDeAltaItem.setActionCommand("nuevo");
		// asigancion del actionlistner
		darDeAltaItem.addActionListener(clientesListener);
		
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
		
		opcionesClientes.add(darDeAltaItem);
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
			String command = e.getActionCommand();
			switch(command) {
				case "nuevo" : {
					
					 break;
				}
				case "borrar" : {
					
					break;
				}
				case "camtarifa" : {
					
					break;
				}
				case "datosnif" : {
					
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
	private class FacturasListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
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
}
