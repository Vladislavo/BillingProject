package ei1017.facturacion.tarifa;

import java.io.Serializable;

import ei1017.facturacion.recursos.Llamada;

public abstract class TarifaExtra extends Tarifa implements Serializable {
	private static final long serialVersionUID = 1L;

	public TarifaExtra(Tarifa tarifa) {
		super();
	}
	
	public abstract boolean aceptable(Llamada llamada);
}
