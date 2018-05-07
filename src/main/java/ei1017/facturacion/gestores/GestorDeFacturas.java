package ei1017.facturacion.gestores;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.excepciones.FacturaNoEncontradaException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherente;
import ei1017.facturacion.gui.OpcionesMenuFacturas;
import ei1017.facturacion.gui.modelo.AlmacenDeDatos;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.EntradaSalidaDeDatos;
import ei1017.facturacion.recursos.Factura;
import ei1017.facturacion.recursos.Periodo;

public class GestorDeFacturas {
	private AlmacenDeDatos almacen;
	private EntradaSalidaDeDatos es;
	private Scanner sc; 
	
	public GestorDeFacturas(AlmacenDeDatos almacen, Scanner sc, EntradaSalidaDeDatos es){
		super();
		this.almacen = almacen;
		this.sc = sc;
		this.es = es;
	}
	
	public void ejecuta() throws ClienteNoEncontradoException, 
				PeriodoDeTiempoIncoherente, FacturaNoEncontradaException{
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
						Factura factura = cliente.emitirFactura(almacen.siguienteIdFactura(), 
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
}
