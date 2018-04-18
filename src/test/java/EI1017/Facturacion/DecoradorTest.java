package EI1017.Facturacion;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ei1017.facturacion.cliente.Cliente;
import ei1017.facturacion.excepciones.FacturaNoEncontradaException;
import ei1017.facturacion.fabricas.FabricaDeClientesImpl;
import ei1017.facturacion.fabricas.FabricaDeTarifasImpl;
import ei1017.facturacion.interfaces.FabricaDeTarifasInt;
import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.tarifa.Tarifa;
import ei1017.facturacion.tarifa.TarifaBasica;

public class DecoradorTest {
	private final static double EPSILON = 0.e-9;
	private final static double SEGUNDOS = 10.0;
	private final static double IMPORTE_BASICA = .15;
	private final static double IMPORTE_DE_TARDE = .05;
	private final static double IMPORTE_DOMINGO = 0;
	private final static double MINUTO = 60.0;
	private final static int NUM_FACTURA = 1;
	
	private Cliente cliente;
	private FabricaDeClientesImpl fabricaDeClientes;
	private Llamada llamada;
	private Periodo periodo;
	private FabricaDeTarifasInt fabricaDeTarifas = new FabricaDeTarifasImpl();
	
	
	@SuppressWarnings("deprecation")
	@Before
	public void before(){
		Direccion direccion = new Direccion("Castellon", "Castellon", 48000);
		fabricaDeClientes = new FabricaDeClientesImpl();
		cliente = fabricaDeClientes.getNuevoClienteEmpresa(new TarifaBasica(), "UJI", "123X", direccion, "info@uji.es", new Date());
		Date desde = new Date();
		desde.setMonth(2);
		Date hasta = new Date();
		hasta.setMonth(5);
		periodo = new Periodo(desde, hasta);
	}
	
	@Test
	public void tarifaBasicaTest() throws FacturaNoEncontradaException {
		llamada = new Llamada("123", new Date(), SEGUNDOS);
		cliente.addLlamada(llamada);
		cliente.emitirFactura(NUM_FACTURA, new Date(), periodo);
		
		assertEquals(cliente.getFactura(NUM_FACTURA).getImporte(), SEGUNDOS*IMPORTE_BASICA*MINUTO, EPSILON);
	}
	
	@Test
	public void tarifaDeTardeTest() throws FacturaNoEncontradaException{
		llamada = new Llamada("123", new Date(), SEGUNDOS);
		cliente.addLlamada(llamada);
		Tarifa deTarde = fabricaDeTarifas.getNuevaTarifaPorHoras(cliente.getTarifa(), 2, 22);
		cliente.setTarifa(deTarde);
		cliente.emitirFactura(NUM_FACTURA, new Date(), periodo);
		
		assertEquals(cliente.getFactura(NUM_FACTURA).getImporte(), SEGUNDOS*IMPORTE_DE_TARDE*MINUTO, EPSILON);
	}
	
	@Test
	public void tarifaDeTardeNoAceptableTest() throws FacturaNoEncontradaException{
		llamada = new Llamada("123", new Date(), SEGUNDOS);
		cliente.addLlamada(llamada);
		Tarifa deTarde = fabricaDeTarifas.getNuevaTarifaPorHoras(cliente.getTarifa(), 21, 22);
		cliente.setTarifa(deTarde);
		cliente.emitirFactura(NUM_FACTURA, new Date(), periodo);
		
		assertEquals(cliente.getFactura(NUM_FACTURA).getImporte(), SEGUNDOS*IMPORTE_BASICA*MINUTO, EPSILON);
	}
	
	@Test
	public void tarifaDeDomingos() throws FacturaNoEncontradaException{
		Tarifa deDomingos = fabricaDeTarifas.getNuevaTarifaDiaria(cliente.getTarifa(), 0);
		cliente.setTarifa(deDomingos);
		Calendar calendar = new GregorianCalendar(2018,5,28,13,24,56);
		Date domingo = calendar.getTime(); // domingo 28/04/2018
		llamada = new Llamada("123", domingo, SEGUNDOS);
		cliente.addLlamada(llamada);
		cliente.emitirFactura(NUM_FACTURA, new Date(), periodo);
		
		assertEquals(cliente.getFactura(NUM_FACTURA).getImporte(), SEGUNDOS*IMPORTE_DOMINGO*MINUTO, EPSILON);
	}
	
	@Test
	public void tarifaDeDomingosNoAceptableTest() throws FacturaNoEncontradaException{
		Tarifa deDomingos = fabricaDeTarifas.getNuevaTarifaDiaria(cliente.getTarifa(), 0);
		cliente.setTarifa(deDomingos);
		llamada = new Llamada("123", new Date(), SEGUNDOS);
		cliente.addLlamada(llamada);
		cliente.emitirFactura(NUM_FACTURA, new Date(), periodo);
		
		assertEquals(cliente.getFactura(NUM_FACTURA).getImporte(), SEGUNDOS*IMPORTE_BASICA*MINUTO, EPSILON);
	}
	
	@Test
	public void tarifaOptimaDeDomingosTest() throws FacturaNoEncontradaException{
		Tarifa deDomingos = fabricaDeTarifas.getNuevaTarifaDiaria(cliente.getTarifa(), 0);
		cliente.setTarifa(deDomingos);
		Tarifa deTarde = fabricaDeTarifas.getNuevaTarifaPorHoras(cliente.getTarifa(), 2, 22);
		cliente.setTarifa(deTarde);
		
		Calendar calendar = new GregorianCalendar(2018,5,28,13,24,56);
		Date domingo = calendar.getTime(); // domingo 28/04/2018
		llamada = new Llamada("123", domingo, SEGUNDOS);
		cliente.addLlamada(llamada);
		cliente.emitirFactura(NUM_FACTURA, new Date(), periodo);
		
		assertEquals(cliente.getFactura(NUM_FACTURA).getImporte(), SEGUNDOS*IMPORTE_DOMINGO*MINUTO, EPSILON);
	}
	
	@Test
	public void tarifaOptimaDeDomingosNoAceptableTest() throws FacturaNoEncontradaException{
		Tarifa deDomingos = fabricaDeTarifas.getNuevaTarifaDiaria(cliente.getTarifa(), 0);
		cliente.setTarifa(deDomingos);
		Tarifa deTarde = fabricaDeTarifas.getNuevaTarifaPorHoras(cliente.getTarifa(), 2, 22);
		cliente.setTarifa(deTarde);
		
		llamada = new Llamada("123", new Date(), SEGUNDOS);
		cliente.addLlamada(llamada);
		cliente.emitirFactura(NUM_FACTURA, new Date(), periodo);
		
		assertEquals(cliente.getFactura(NUM_FACTURA).getImporte(), SEGUNDOS*IMPORTE_DE_TARDE*MINUTO, EPSILON);
	}
	
	@After
	public void after(){
		cliente.getFacturas().clear();
	}
}
