package ei1017.facturacion.aplicacion;

import javax.swing.SwingUtilities;

import ei1017.facturacion.gui.controlador.Controlador;
import ei1017.facturacion.gui.modelo.AlmacenDeDatos;
import ei1017.facturacion.gui.vista.Vista;
import ei1017.facturacion.recursos.EntradaSalidaDeDatos;

public class Main {
	public static void main (String[] args){
		//EntradaSalidaDeDatos es = new EntradaSalidaDeDatos();
		//AlmacenDeDatos almacen = es.cargarDatosDeFichero().orElse(new AlmacenDeDatos());
		//Aplicacion aplicacion = new Aplicacion(es, almacen);
		//aplicacion.iniciaMenu();
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	Controlador controlador = new Controlador();
	            new Vista(controlador).dibujaTablero();
	        }
	    });
	}
}
