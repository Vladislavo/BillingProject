package ei1017.facturacion.tarifa;

import java.io.Serializable;

import ei1017.facturacion.recursos.Llamada;

public abstract class Tarifa implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Tarifa(){
		super();
	}
	
	public double getImporte(Llamada llamada){
		return 0.15;
	}
}
