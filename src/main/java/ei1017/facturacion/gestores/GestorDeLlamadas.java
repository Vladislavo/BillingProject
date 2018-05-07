package ei1017.facturacion.gestores;

import java.util.Collection;
import java.util.Scanner;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherente;
import ei1017.facturacion.gui.OpcionesMenuLlamadas;
import ei1017.facturacion.gui.modelo.AlmacenDeDatos;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.EntradaSalidaDeDatos;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;

public class GestorDeLlamadas {
	private AlmacenDeDatos almacen;
	private EntradaSalidaDeDatos es;
	private Scanner sc; 
	
	public GestorDeLlamadas(AlmacenDeDatos almacen, Scanner sc, EntradaSalidaDeDatos es){
		super();
		this.almacen = almacen;
		this.sc = sc;
		this.es = es;
	}
	
	public void ejecuta() throws ClienteNoEncontradoException, PeriodoDeTiempoIncoherente{
		boolean fin = false;
		
		System.out.println("Introduce el NIF del cliente: ");
		String NIF = sc.nextLine();
		Cliente cliente = almacen.getCliente(NIF);
		System.out.println(cliente);
		
		while(!fin){
			System.out.println("Menu principal > Llamadas : ");
			
			System.out.println(OpcionesMenuLlamadas.getMenu());
			byte opcion = Byte.parseByte(sc.nextLine());
				
			if(opcion >= 0 && opcion < OpcionesMenuLlamadas.values().length) {
				OpcionesMenuLlamadas opcionMenu = OpcionesMenuLlamadas.getOpcion(opcion);
				
				switch(opcionMenu){
					case DAR_DE_ALTA_LLAMADA: {
						Llamada llamada = es.getNuevaLlamada();
						cliente.addLlamada(llamada);
						System.out.println("La llamada " + llamada + " fue añadida al cliente "
								+ cliente.getNombre());
						break;
					}
					case LISTAR_LLAMADAS_CLIENTE: {
						es.listarLlamadasDeCliente(cliente);
						break;
					}
					case SALIR: {
						fin = true;
						break;
					}
					case LLAMADAS_ENTRE_DOS_FECHAS: {
						Periodo periodo = es.getPeriodo();
						Collection<? extends EntradaRegistro> clts = 
								almacen.getEntradas(cliente.getLlamadas(), periodo);
						es.listarDatosEntradaRegistro(clts);
						break;
					}
				}
			} else {
				System.out.println("Elija una de las opciones [0-" + OpcionesMenuLlamadas.values().length + "] : ");
			}
		}
	}
}
