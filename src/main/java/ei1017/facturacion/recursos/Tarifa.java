package ei1017.facturacion.recursos;

import java.io.Serializable;

public class Tarifa implements Serializable{
	private static final long serialVersionUID = 1L;
	private double tarifa;
	
	public Tarifa(double tarifa){
		super();
		this.tarifa = tarifa;
	}
	
	public double getTarifa(){
		return tarifa;
	}
	
	@Override
	public String toString(){
		return "Tarifa: " + tarifa;
	}
}
