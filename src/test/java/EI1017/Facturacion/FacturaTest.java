package EI1017.Facturacion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ei1017.facturacion.recursos.Factura;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.tarifa.Tarifa;
import ei1017.facturacion.tarifa.TarifaBasica;

public class FacturaTest {
	final static double EPSILON = 0.e-9;
	Factura factura;
	Tarifa tarifa;
	Periodo periodoDeFacturacion;
	Date inicioDePeriodo;
	Date finalDePeriodo;
	Llamada llamadaAntesDePeriodo;
	Llamada llamadaDentroDePeriodo1;
	Llamada llamadaDentroDePeriodo2;
	Llamada llamadaDespuesDePeriodo;
	List<Llamada> llamadas;
	
	@Before
	public void init(){
		llamadaAntesDePeriodo = new Llamada("00000000", new Date(), 1.0);
		inicioDePeriodo = new Date();
		// Para fijar bien el periodo de llamadas
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		llamadaDentroDePeriodo1 = new Llamada("5555555", new Date(), 2.0);
		llamadaDentroDePeriodo2 = new Llamada("7777777", new Date(), 3.0);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finalDePeriodo = new Date();
		llamadaDespuesDePeriodo = new Llamada("9999999", new Date(), 1.0);
		tarifa = new TarifaBasica();
		llamadas = new ArrayList<Llamada>();
		periodoDeFacturacion = new Periodo(inicioDePeriodo, finalDePeriodo);
		
		factura = new Factura(tarifa, 1, new Date(), periodoDeFacturacion);
	}
	
	@Test
	public void emisionSoloDentro() {
		llamadas.add(llamadaDentroDePeriodo1);
		llamadas.add(llamadaDentroDePeriodo2);
		factura = factura.emitir(llamadas);
		double esperado = 0.5;
		double actual = factura.getImporte();
		assertEquals(esperado, actual, EPSILON);
	}
	
	@Test
	public void emisionAntesDePeriodo(){
		llamadas.add(llamadaAntesDePeriodo);
		factura = factura.emitir(llamadas);
		double esperado = 0;
		double actual = factura.getImporte();
		assertEquals(esperado, actual, EPSILON);	
	}
	
	@Test
	public void emisionDespuesDePeriodo(){
		llamadas.add(llamadaDespuesDePeriodo);
		factura = factura.emitir(llamadas);
		double esperado = 0;
		double actual = factura.getImporte();
		assertEquals(esperado, actual, EPSILON);	
	}
	
	@Test
	public void emisionAntesYDespuesDePeriodo(){
		llamadas.add(llamadaAntesDePeriodo);
		llamadas.add(llamadaDespuesDePeriodo);
		factura = factura.emitir(llamadas);
		double esperado = 0;
		double actual = factura.getImporte();
		assertEquals(esperado, actual, EPSILON);
	}
	
	@Test
	public void emisionGeneral(){
		llamadas.add(llamadaAntesDePeriodo);
		llamadas.add(llamadaDentroDePeriodo1);
		llamadas.add(llamadaDentroDePeriodo2);
		llamadas.add(llamadaDespuesDePeriodo);
		factura = factura.emitir(llamadas);
		double esperado = 0.5;
		double actual = factura.getImporte();
		assertEquals(esperado, actual, EPSILON);
	}
}
