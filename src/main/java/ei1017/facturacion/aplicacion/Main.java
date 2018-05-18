package ei1017.facturacion.aplicacion;

import javax.swing.SwingUtilities;

import ei1017.facturacion.gui.controlador.Controlador;
import ei1017.facturacion.gui.modelo.AlmacenDeDatos;
import ei1017.facturacion.gui.vista.Vista;

public class Main {
	public static void main (String[] args){
		//EntradaSalidaDeDatos es = new EntradaSalidaDeDatos();
		//Aplicacion aplicacion = new Aplicacion(es, almacen);
		//aplicacion.iniciaMenu();
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	AlmacenDeDatos almacen = AlmacenDeDatos.cargarDatosDeFichero().orElse(new AlmacenDeDatos());
	        	Vista vista = new Vista(almacen);
	        	almacen.setVista(vista);
	        	Controlador controlador = new Controlador(almacen, vista);
	        	vista.setControlador(controlador);
	            vista.dibujaTablero();
	        }
	    });
	}
}
