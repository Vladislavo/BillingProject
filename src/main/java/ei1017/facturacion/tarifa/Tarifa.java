package ei1017.facturacion.tarifa;

import java.io.Serializable;

import ei1017.facturacion.recursos.Llamada;

public abstract class Tarifa implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final double IMPORTE = .15;
	private static final double MINUTO = 60;
	
	public Tarifa(){
		super();
	}
	
	public double getImporte(Llamada llamada){
		return IMPORTE*MINUTO;
	}
}
