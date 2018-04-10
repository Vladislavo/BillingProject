package ei1017.facturacion.fabricas;

import ei1017.facturacion.interfaces.FabricaDeTarifasInt;
import ei1017.facturacion.tarifa.Tarifa;
import ei1017.facturacion.tarifa.TarifaBasica;
import ei1017.facturacion.tarifa.TarifaDiaria;
import ei1017.facturacion.tarifa.TarifaPorHoras;

public class FabricaDeTarifasImpl implements FabricaDeTarifasInt {

	public FabricaDeTarifasImpl(){
		super();
	}
	
	@Override
	public Tarifa getNuevaTarifaDiaria(Tarifa tarifa, int dia) {
		return new TarifaDiaria(tarifa, dia);
	}

	@Override
	public Tarifa getNuevaTarifaPorHoras(Tarifa tarifa, int desde, int hasta) {
		return new TarifaPorHoras(tarifa, desde, hasta);
	}

	@Override
	public Tarifa getNuevaTarifaBasica() {
		return new TarifaBasica();
	}
	
}
