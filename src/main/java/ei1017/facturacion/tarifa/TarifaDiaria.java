package ei1017.facturacion.tarifa;

import java.io.Serializable;

import ei1017.facturacion.recursos.Llamada;

public class TarifaDiaria extends TarifaExtra implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final double IMPORTE = .0;
	private static final double MINUTO = 60;
	private int dia;
	
	public TarifaDiaria(Tarifa tarifa, int dia) {
		super(tarifa);
		this.dia = dia;
	}
	
	
	@Override
	public double getImporte(Llamada llamada){
		if(aceptable(llamada)){
			return IMPORTE*MINUTO < super.getImporte(llamada) ? IMPORTE*MINUTO : super.getImporte(llamada);
		}
		return super.getImporte(llamada);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean aceptable(Llamada llamada) {
		return llamada.getFecha().getDay() == dia;
	}
	
	@Override
	public String toString(){
		return super.toString() + ", diaria";
	}
	
}
