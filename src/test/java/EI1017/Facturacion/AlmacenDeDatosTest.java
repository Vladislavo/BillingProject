package EI1017.Facturacion;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ei1017.facturacion.cliente.ClienteEmpresa;
import ei1017.facturacion.excepciones.ClienteNoEncontradoException;
import ei1017.facturacion.excepciones.FacturaNoEncontradaException;
import ei1017.facturacion.gui.modelo.AlmacenDeDatosModeloImpl;
import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.tarifa.Tarifa;
import ei1017.facturacion.tarifa.TarifaBasica;

public class AlmacenDeDatosTest {
	final static double EPSILON = 0.e-9;
	AlmacenDeDatosModeloImpl almacen;
	
	@Before
	public void init(){
		almacen = new AlmacenDeDatosModeloImpl();
	}
	
	@Test
	public void anyadirClienteTest() {
		Tarifa tarifa = new TarifaBasica();
		Direccion direccion = new Direccion("Valencia", "Sagunt", 46500);
		ClienteEmpresa clienteEmpresa = new ClienteEmpresa(tarifa, "FotoPepito", "123X", direccion, "pepito@foto.es", new Date());
		almacen.anyadirCliente(clienteEmpresa);
		assertTrue(almacen.tamanyo() == 1);
	}
	
	@Test
	public void anyadirClientesMismoNieTest() {
		Tarifa tarifa = new TarifaBasica();
		Direccion direccion = new Direccion("Valencia", "Sagunt", 46500);
		ClienteEmpresa clienteEmpresa0 = new ClienteEmpresa(tarifa, "FotoPepito", "123X", direccion, "pepito@foto.es", new Date());
		ClienteEmpresa clienteEmpresa1 = new ClienteEmpresa(tarifa, "FotoPepito", "123X", direccion, "pepito@foto.es", new Date());
		almacen.anyadirCliente(clienteEmpresa0);
		almacen.anyadirCliente(clienteEmpresa1);
		assertTrue(almacen.tamanyo() == 1);
	}
	
	@Test
	public void eliminarClienteTest() throws ClienteNoEncontradoException {
		Tarifa tarifa = new TarifaBasica();
		Direccion direccion = new Direccion("Valencia", "Sagunt", 46500);
		ClienteEmpresa clienteEmpresa0 = new ClienteEmpresa(tarifa, "FotoPepito", "123X", direccion, "pepito@foto.es", new Date());
		almacen.anyadirCliente(clienteEmpresa0);
		
		ClienteEmpresa clienteEmpresa1 = new ClienteEmpresa(tarifa, "FotoJuanito", "111X", direccion, "juanito@foto.es", new Date());
		almacen.anyadirCliente(clienteEmpresa1);
		

		almacen.borrarCliente(clienteEmpresa1);
		assertTrue(almacen.tamanyo() == 1);
	}
	
	@Test(expected=ClienteNoEncontradoException.class)
	public void clienteNoEncontradoExceptionTest() throws ClienteNoEncontradoException {
		Tarifa tarifa = new TarifaBasica();
		Direccion direccion = new Direccion("Valencia", "Sagunt", 46500);
		ClienteEmpresa clienteEmpresa0 = new ClienteEmpresa(tarifa, "FotoPepito", "123X", direccion, "pepito@foto.es", new Date());
		almacen.anyadirCliente(clienteEmpresa0);
		
		ClienteEmpresa clienteEmpresa1 = new ClienteEmpresa(tarifa, "FotoJuanito", "111X", direccion, "juanito@foto.es", new Date());
		almacen.anyadirCliente(clienteEmpresa1);
		
		almacen.getCliente("222Q");
	}
	
	@Test(expected=FacturaNoEncontradaException.class)
	public void facturaNoEncontradaExceptionTest() throws FacturaNoEncontradaException {
		Tarifa tarifa = new TarifaBasica();
		Direccion direccion = new Direccion("Valencia", "Sagunt", 46500);
		ClienteEmpresa clienteEmpresa0 = new ClienteEmpresa(tarifa, "FotoPepito", "123X", direccion, "pepito@foto.es", new Date());
		
		clienteEmpresa0.getFactura(1);
	}
	
	@Test
	public void almacenVacioTest() {
		assertFalse(almacen.tamanyo() > 0);
	}

}
