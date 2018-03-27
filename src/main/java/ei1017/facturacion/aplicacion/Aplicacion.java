package ei1017.facturacion.aplicacion;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.cliente.ClienteEmpresa;
import ei1017.facturacion.cliente.ClienteParticular;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.excepciones.FacturaNoEncontradaException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherente;
import ei1017.facturacion.gui.OpcionesCliente;
import ei1017.facturacion.gui.OpcionesMenu;
import ei1017.facturacion.gui.OpcionesMenuCliente;
import ei1017.facturacion.gui.OpcionesMenuFacturas;
import ei1017.facturacion.gui.OpcionesMenuLlamadas;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.AlmacenDeDatos;
import ei1017.facturacion.recursos.EntradaSalidaDeDatos;
import ei1017.facturacion.recursos.Factura;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.recursos.Tarifa;

public class Aplicacion {
	private Scanner sc;
	private EntradaSalidaDeDatos es;
	private AlmacenDeDatos almacen;
	private AtomicInteger idFacturas;
	
	public Aplicacion(EntradaSalidaDeDatos es, AlmacenDeDatos almacen){
		super();
		this.es = es;
		this.almacen = almacen;
		sc = new Scanner(System.in);
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
							menuClientes();
							break;
						}
						case FACTURAS: {
							menuFacturas();
							break;
						}
						case LLAMADAS: {
							menuLlamadas();
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
				}
			} else {
				System.out.println("Elija una de las opciones [0-" + OpcionesMenu.values().length + "] : ");
			}
		}
		sc.close();
	}

	private void menuLlamadas() throws ClienteNoEncontradoException, PeriodoDeTiempoIncoherente {
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

	private void menuFacturas() throws ClienteNoEncontradoException, 
	FacturaNoEncontradaException, PeriodoDeTiempoIncoherente {
		boolean fin = false;
	
		System.out.println("Introduce el NIF del cliente: ");
		String NIF = sc.nextLine();
		Cliente cliente = almacen.getCliente(NIF);
		
		while(!fin){
			System.out.println("Menu principal > Facturas : ");
			
			System.out.println(OpcionesMenuFacturas.getMenu());
			byte opcion = Byte.parseByte(sc.nextLine());
			
			if(opcion >= 0 && opcion < OpcionesMenuFacturas.values().length) {
				OpcionesMenuFacturas opcionMenu = OpcionesMenuFacturas.getOpcion(opcion);
				
				switch(opcionMenu){
					case EMITIR_FACTURA: {
						es.mostrarEstado("Se emite la factura del cliente " + cliente);
						Periodo periodoDeFacturacion = es.getPeriodo();
						Factura factura = cliente.emitirFactura(idFacturas.incrementAndGet(), 
								new Date(), periodoDeFacturacion);
						es.mostrarEstado("La factura " + factura + " emitida.");
						break;
					}
					case RECUPERAR_DATOS_FACTURA: {
						int codFactura = es.getFacturaPorCodigo();
						Factura factura = cliente.getFactura(codFactura);
						es.mostrarDatosEntradaRegistro(factura);
						break;
					}
					case RECUPERAR_TODAS_FACTURAS: {			
						if(cliente.getFacturas().values().size() > 0){
							Collection<Factura> facturas = cliente.getFacturas().values();
							es.listarDatosEntradaRegistro(facturas);
						} else {
							es.mostrarEstado("El " + cliente + " no tiene facturas emitidas.");
						}
						break;
					}
					case SALIR: {
						fin = true;
						break;
					}
					case FACTURAS_ENTRE_DOS_FECHAS: {
						Periodo periodo = es.getPeriodo();
						Collection<? extends EntradaRegistro> clts = 
								almacen.getEntradas(cliente.getFacturas().values(), periodo);
						es.listarDatosEntradaRegistro(clts);
						break;
					}
				}
			} else {
				System.out.println("Elija una de las opciones [0-" + OpcionesMenuFacturas.values().length + "] : ");
			}
		}
		
	}

	private void menuClientes() throws ClienteNoEncontradoException, PeriodoDeTiempoIncoherente {
		boolean fin = false;
		
		while(!fin){
			System.out.println("Menu principal > Clientes : ");
			System.out.println(OpcionesMenuCliente.getMenu());
			byte opcion = Byte.parseByte(sc.nextLine());
			
			if(opcion >= 0 && opcion < OpcionesMenuCliente.values().length) {
				OpcionesMenuCliente opcionMenu = OpcionesMenuCliente.getOpcion(opcion);
				
				switch(opcionMenu){
					case DAR_DE_ALTA_NUEVO_CLIENTE: {
						menuOpcionesTipoCliente();
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
							Tarifa tarifa = es.getNuevaTarifa();
							cliente.cambiarTarifa(tarifa);
							es.mostrarEstado("La nueva tarifa del " + cliente + 
									"es " + tarifa);
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

	private void menuOpcionesTipoCliente() {
		boolean fin = false;
		
		while(!fin){
			System.out.println("Menu principal > Clientes > Tipo de cliente : ");
			System.out.println(OpcionesCliente.getMenu());
			byte opcion = Byte.parseByte(sc.nextLine());
			
			if(opcion >= 0 && opcion < OpcionesCliente.values().length) {
				OpcionesCliente opcionMenu = OpcionesCliente.getOpcion(opcion);
				
				switch(opcionMenu){
					case PARTICULAR: {
						ClienteParticular clienteParticular = es.getNuevoClienteParticular();
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
						ClienteEmpresa clienteEmpresa = es.getNuevoClienteEmpresa();
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
	
	public void setAlmacenDeDatos(AlmacenDeDatos almacen){
		this.almacen = almacen;
	}
}
