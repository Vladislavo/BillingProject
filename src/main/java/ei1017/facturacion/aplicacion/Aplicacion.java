package ei1017.facturacion.aplicacion;

import java.util.Scanner;

import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.excepciones.FacturaNoEncontradaException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherente;
import ei1017.facturacion.gestores.GestorDeClientes;
import ei1017.facturacion.gestores.GestorDeFacturas;
import ei1017.facturacion.gestores.GestorDeLlamadas;
import ei1017.facturacion.gui.OpcionesMenu;
import ei1017.facturacion.gui.modelo.AlmacenDeDatos;
import ei1017.facturacion.recursos.EntradaSalidaDeDatos;

public class Aplicacion {
	private Scanner sc;
	private EntradaSalidaDeDatos es;
	private AlmacenDeDatos almacen;
	
	private GestorDeClientes gestorDeClientes;
	private GestorDeFacturas gestorDeFacturas;
	private GestorDeLlamadas gestorDeLlamadas;
	
	public Aplicacion(EntradaSalidaDeDatos es, AlmacenDeDatos almacen){
		super();
		this.es = es;
		this.almacen = almacen;
		sc = new Scanner(System.in);
		this.gestorDeClientes = new GestorDeClientes(almacen, sc, es);
		this.gestorDeFacturas = new GestorDeFacturas(almacen, sc, es);
		this.gestorDeLlamadas = new GestorDeLlamadas(almacen, sc, es);
	}
	
	public void iniciaMenu(){
		boolean fin = false;
		
		System.out.println("Bienvenido a la aplicacion de genstión de facturas!\nElija "
				+ "una de las opciones.\n");
		while(!fin){
			System.out.println("Menu principal : ");
			System.out.println(OpcionesMenu.getMenu());
			byte opcion = Byte.parseByte(sc.nextLine());
			
			if(opcion >= 0 && opcion < OpcionesMenu.values().length) {
				OpcionesMenu opcionMenu = OpcionesMenu.getOpcion(opcion);
				
				try {
					switch(opcionMenu){
						case CLIENTES: {
							gestorDeClientes.ejecuta();
							break;
						}
						case FACTURAS: {
							gestorDeFacturas.ejecuta();
							break;
						}
						case LLAMADAS: {
							gestorDeLlamadas.ejecuta();
							break;
						}
						case SALIR: {
							es.guardarDatosAFichero(almacen);
							System.out.println("Gracias por uso de nuestra aplicación.");
							fin = true;
							break;
						}
					}
				} catch (ClienteNoEncontradoException e) {
					System.out.println("El cliente no está en la base de datos de la aplicación.");
				} catch (FacturaNoEncontradaException e) {
					System.out.println("El cliente no tiene la factura con el ID introducido.");
				} catch(PeriodoDeTiempoIncoherente e) {
					System.out.println("El periodo de tiempo introducido no es coherente.");
				} catch (Exception e) {
					System.out.println("Hubo un problema.");
					e.printStackTrace();
				}
			} else {
				System.out.println("Elija una de las opciones [0-" + OpcionesMenu.values().length + "] : ");
			}
		}
		sc.close();
	}
	
	public void setAlmacenDeDatos(AlmacenDeDatos almacen){
		this.almacen = almacen;
	}
}
