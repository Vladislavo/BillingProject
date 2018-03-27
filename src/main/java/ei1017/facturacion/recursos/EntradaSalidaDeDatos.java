package ei1017.facturacion.recursos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.cliente.ClienteEmpresa;
import ei1017.facturacion.cliente.ClienteParticular;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherente;
import ei1017.facturacion.interfaces.EntradaRegistro;

public class EntradaSalidaDeDatos {
	private Scanner sc;
	private static final String FICHERO_DATOS_APLICACION = System.getProperty("user.dir") 
			+ "/resources/datosDeAplicacion.bin";
	
	public EntradaSalidaDeDatos(){
		super();
		sc = new Scanner(System.in);	
	}
	
	private Cliente getNuevoCliente(){
		System.out.println("Introduce el nombre del cliente:");
		String nombre = sc.nextLine();
		
		System.out.println("Introduce el NIF del cliente:");
		String NIF = sc.nextLine();
		
		System.out.println("Introduce el email del cliente:");
		String email = sc.nextLine();
		
		System.out.println("Introduce la tarifa (euro/min): ");
		Tarifa tarifa = new Tarifa (Double.parseDouble(sc.nextLine()));
		
		System.out.println("Introduce la dirección:\nPoblación: ");
		String poblacion = sc.nextLine();
		System.out.println("Provincia: ");
		String provincia = sc.nextLine();
		System.out.println("Código postal: ");
		int codPostal = Integer.parseInt(sc.nextLine());
		Direccion direccion = new Direccion(poblacion, provincia, codPostal);
		
		return new Cliente(tarifa, nombre, NIF, direccion, email, new Date());
	}
	
	public ClienteParticular getNuevoClienteParticular(){
		System.out.println("Introduce los apellidos del cliente:");
		String apellidos = sc.nextLine();
		
		Cliente cliente = getNuevoCliente();
		
		return new ClienteParticular(cliente.getTarifa(), cliente.getNombre(), cliente.getNIF(),
				cliente.getDireccion(), cliente.getEmail(), new Date(), apellidos);
	}
	
	public ClienteEmpresa getNuevoClienteEmpresa(){
		Cliente cliente = getNuevoCliente();
		
		return new ClienteEmpresa(cliente.getTarifa(), cliente.getNombre(), cliente.getNIF(),
				cliente.getDireccion(), cliente.getEmail(), new Date());
	}
	
	
	public Tarifa getNuevaTarifa(){
		System.out.println("Indroduce nueva tarifa (euro/min): ");
		Tarifa nuevaTarifa = new Tarifa(sc.nextDouble());
		
		return nuevaTarifa;
	}
	
	public void listarDatosCliente(Cliente cliente){
		System.out.println(cliente);
	}
	
	public void listarTodosClientes(Collection<Cliente> clientes){
		for(Cliente cliente : clientes){
			listarDatosCliente(cliente);
			System.out.println("-----------------------------");
		}
	}
	
	public Llamada getNuevaLlamada(){
		System.out.println("Introduce el número de teléfono:");
		String numTelefono = sc.nextLine();
		System.out.println("Introduce la duración de la llamada:");
		double duracion = Double.parseDouble(sc.nextLine());
		
		return new Llamada(numTelefono, new Date(), duracion);
	}
	
	public void listarLlamadasDeCliente(Cliente cliente){
		List<Llamada> llamadas = cliente.getLlamadas();
		System.out.println("Lista de llamadas del " + cliente + "\n");
		for(Llamada llamada : llamadas){
			System.out.println(llamada);
			System.out.println("-----------------------------");
		}
	}
	
	public void mostrarEstado(String estado){
		System.out.println(estado);
	}
	
	public Periodo getPeriodo() throws PeriodoDeTiempoIncoherente{
		Periodo periodoDeFacturacion = null;
		System.out.println("Introduce la fecha de inicio (dd/mm/aaaa): ");
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
			String fechaInicioString = sc.nextLine();
			System.out.println("Introduce la fecha del final (dd/mm/aaaa): ");
			String fechaFinalString = sc.nextLine();
			Date fechaInicio = df.parse(fechaInicioString);
			Date fechaFinal = df.parse(fechaFinalString);
			periodoDeFacturacion = new Periodo(fechaInicio, fechaFinal);
			
			if(fechaInicio.after(fechaFinal)){
				throw new PeriodoDeTiempoIncoherente(periodoDeFacturacion.toString());
			}
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return periodoDeFacturacion;
	}
	
	public int getFacturaPorCodigo(){
		System.out.println("Introduce el código de la factura: ");
		int codigoFactura = sc.nextInt();
		return codigoFactura;
	}
	
	public void mostrarDatosEntradaRegistro(EntradaRegistro entrada){
		System.out.println(entrada);
	}
	
	public void listarDatosEntradaRegistro(Collection<? extends EntradaRegistro> entradas){
		for(EntradaRegistro entrada : entradas){
			mostrarDatosEntradaRegistro(entrada);
			System.out.println("---------------------------");
		}
	}
	
	public void guardarDatosAFichero(AlmacenDeDatos almacen) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(FICHERO_DATOS_APLICACION);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(almacen);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Optional<AlmacenDeDatos> cargarDatosDeFichero(){
		AlmacenDeDatos almacen = null;
		try {
			FileInputStream fis = new  FileInputStream(FICHERO_DATOS_APLICACION);
			ObjectInputStream ois = new ObjectInputStream(fis);
			almacen = (AlmacenDeDatos) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			almacen = new AlmacenDeDatos();
		} catch (IOException e) {
			e.printStackTrace();
			almacen = new AlmacenDeDatos();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			almacen = new AlmacenDeDatos();
		}
		
		return Optional.of(almacen);
	}
}
