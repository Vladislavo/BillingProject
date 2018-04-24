package ei1017.facturacion.gui.vista;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Vista {
	private JFrame frame = null;
	private JLabel estado = null;
	
	
	public void dibujaTablero() {
		anyadeMenu();
		anyadePanelEstado("Estado actual");		
		frame.setSize(500, 600);
		frame.setVisible(true);	
	}

	private void anyadePanelEstado(String string) {
		
	}

	private void anyadeMenu() {
		
	}
}
