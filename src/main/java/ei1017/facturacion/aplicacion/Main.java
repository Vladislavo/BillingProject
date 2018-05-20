package ei1017.facturacion.aplicacion;

import javax.swing.SwingUtilities;

import ei1017.facturacion.gui.controlador.ControladorImpl;
import ei1017.facturacion.gui.modelo.AlmacenDeDatosModeloImpl;
import ei1017.facturacion.gui.vista.VistaImpl;

public class Main {
	public static void main (String[] args){
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	AlmacenDeDatosModeloImpl almacen = AlmacenDeDatosModeloImpl.cargarDatosDeFichero().orElse(new AlmacenDeDatosModeloImpl());
	        	VistaImpl vista = new VistaImpl(almacen);
	        	almacen.setVista(vista);
	        	ControladorImpl controlador = new ControladorImpl(almacen, vista);
	        	vista.setControlador(controlador);
	            vista.dibujaTablero();
	        }
	    });
	}
}
