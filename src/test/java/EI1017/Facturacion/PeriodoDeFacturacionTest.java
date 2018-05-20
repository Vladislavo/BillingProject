package EI1017.Facturacion;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ei1017.facturacion.excepciones.CampoDeFechaVacioException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherenteException;
import ei1017.facturacion.recursos.Periodo;

public class PeriodoDeFacturacionTest {
	Periodo periodoDeFacturacion;
	Date antes;
	Date inicioPeriodo;
	Date dentro;
	Date finalPeriodo;
	Date despues;
	
	@Before
	public void init() throws PeriodoDeTiempoIncoherenteException, CampoDeFechaVacioException {
		antes = new Date();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		inicioPeriodo = new Date();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dentro = new Date();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finalPeriodo = new Date();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		despues = new Date();
		
		periodoDeFacturacion = new Periodo(inicioPeriodo, finalPeriodo);
	}
	
	@Test
	public void antesPeriodo(){
		assertFalse(periodoDeFacturacion.estaDentro(antes));
	}
	@Test
	public void despuesPeriodo(){
		assertFalse(periodoDeFacturacion.estaDentro(despues));
	}
	@Test
	public void dentroPeriodo(){
		assertTrue(periodoDeFacturacion.estaDentro(dentro));
	}
}
