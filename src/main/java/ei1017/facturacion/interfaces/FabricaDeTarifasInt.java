package ei1017.facturacion.interfaces;

import ei1017.facturacion.tarifa.Tarifa;

public interface FabricaDeTarifasInt {
	Tarifa getNuevaTarifaDiaria(Tarifa tarifa, int dia);
	Tarifa getNuevaTarifaPorHoras(Tarifa tarifa, int desde, int hasta);
	Tarifa getNuevaTarifaBasica();
}
