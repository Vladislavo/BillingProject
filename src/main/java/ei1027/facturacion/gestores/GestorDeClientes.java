package ei1027.facturacion.gestores;

import java.util.Collection;
import java.util.Scanner;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherente;
import ei1017.facturacion.gui.OpcionesCliente;
import ei1017.facturacion.gui.OpcionesMenuCliente;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.AlmacenDeDatos;
import ei1017.facturacion.recursos.EntradaSalidaDeDatos;
import ei1017.facturacion.recursos.Periodo;

public class GestorDeClientes {
	private AlmacenDeDatos almacen;
	private EntradaSalidaDeDatos es;
	private Scanner sc; 
	
	public GestorDeClientes(AlmacenDeDatos almacen, Scanner sc, EntradaSalidaDeDatos es){
		super();
		this.almacen = almacen;
		this.sc = sc;
		this.es = es;
	}
	
	public void ejecuta() throws PeriodoDeTiempoIncoherente, ClienteNoEncontradoException{
		boolean fin = false;
		
		while(!fin){
			System.out.println("Menu principal > Clientes : ");
			System.out.println(OpcionesMenuCliente.getMenu());
			byte opcion = Byte.parseByte(sc.nextLine());
			
			if(opcion >= 0 && opcion < OpcionesMenuCliente.values().length) {
				OpcionesMenuCliente opcionMenu = OpcionesMenuCliente.getOpcion(opcion);
				
				switch(opcionMenu){
					case DAR_DE_ALTA_NUEVO_CLIENTE: {
						opcionesTipoCliente();
						break;
					}
					case BORRAR_CLIENTE: {
						System.out.println("Introduce el NIF del cliente: ");
						String NIF = sc.nextLine();
						if(!NIF.trim().equals("")){
							Cliente cliente = almacen.getCliente(NIF);
							if(almacen.borrarCliente(cliente)){
								es.mostrarEstado("Cliente " + cliente + " ha sido borrado satisfactoriamente.");
							}
						} else {
							es.mostrarEstado("NIF no introducido.");
						}
						break;
					}
					case CAMBIAR_TARIFA_CLIENTE: {
						System.out.println("Introduce el NIF del cliente: ");
						String NIF = sc.nextLine();
						if(!NIF.trim().equals("")){
							Cliente cliente = almacen.getCliente(NIF);
							
							es.aplicarNuevaTarifa(cliente);
							
							es.mostrarEstado("La nueva tarifa del " + cliente + 
									"es " + cliente.getTarifa());
						} else {
							es.mostrarEstado("NIF no introducido.");
						}
						break;
					}
					case RECUPERAR_DATOS_CLIENTE_NIF: {
						System.out.println("Introduce el NIF del cliente: ");
						String NIF = sc.nextLine();
						if(!NIF.trim().equals("")){
							Cliente cliente = almacen.getCliente(NIF);
							es.listarDatosCliente(cliente);
						} else {
							es.mostrarEstado("NIE no introducido.");
						}
						break;
					}
					case RECUPERAR_LISTADO_CLIENTES: {
						if(almacen.getClientes().values().size() > 0){
							Collection<Cliente> clientes = almacen.getClientes().values();
							es.listarTodosClientes(clientes);
						} else {
							es.mostrarEstado("Lista de clientes vacía.");
						}
						break;
					}
					case SALIR: {
						fin = true;
						break;
					}
					case CLIENTES_ENTRE_DOS_FECHAS: {
						Periodo periodo = es.getPeriodo();
						Collection<? extends EntradaRegistro> clts = 
								almacen.getEntradas(almacen.getClientes().values(), periodo);
						es.listarDatosEntradaRegistro(clts);
						break;
					}
				}
			} else {
				System.out.println("Elija una de las opciones [0-" + OpcionesMenuCliente.values().length + "] : ");
			}
		}
	}
	
	public void opcionesTipoCliente(){
		boolean fin = false;
		
		while(!fin){
			System.out.println("Menu principal > Clientes > Tipo de cliente : ");
			System.out.println(OpcionesCliente.getMenu());
			byte opcion = Byte.parseByte(sc.nextLine());
			
			if(opcion >= 0 && opcion < OpcionesCliente.values().length) {
				OpcionesCliente opcionMenu = OpcionesCliente.getOpcion(opcion);
				
				switch(opcionMenu){
					case PARTICULAR: {
						Cliente clienteParticular = es.getNuevoClienteParticular();
						if(almacen.anyadirCliente(clienteParticular)){
							es.mostrarEstado(clienteParticular + "\nha sido añadido "
									+ "satisfactoriame1nte.");
						} else {
							es.mostrarEstado("Cliente " + clienteParticular + " ya había sido "
									+ "añadido");
						}
						break;
					}
					case EMPRESA: {
						Cliente clienteEmpresa = es.getNuevoClienteEmpresa();
						if(almacen.anyadirCliente(clienteEmpresa)){
							es.mostrarEstado("Cliente " + clienteEmpresa + " ha sido añadido "
									+ "satisfactoriamente.");
						} else {
							es.mostrarEstado("Cliente " + clienteEmpresa + " ya había sido "
									+ "añadido");
						}
						break;
					}
					case SALIR: {
						fin = true;
						break;
					}
				}
			} else {
				System.out.println("Elija una de las opciones [0-" + OpcionesCliente.values().length + "] : ");
			}
		}
	}
}
