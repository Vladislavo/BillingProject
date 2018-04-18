package ei1017.facturacion.tarifa;

import java.io.Serializable;

import ei1017.facturacion.recursos.Llamada;

public class TarifaPorHoras extends TarifaExtra implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final double IMPORTE = .05;
	private static final double MINUTO = 60;
	private int desde;
	private int hasta;
	
	public TarifaPorHoras(Tarifa tarifa, int desde, int hasta) {
		super(tarifa);
		this.desde = desde;
		this.hasta = hasta;
	}

	@Override
	public double getImporte(Llamada llamada){
		if(aceptable(llamada)){
			System.out.println(super.getImporte(llamada));
			return IMPORTE*MINUTO < super.getImporte(llamada) ? IMPORTE*MINUTO : super.getImporte(llamada);
		}
		return super.getImporte(llamada);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean aceptable(Llamada llamada){
		return llamada.getFecha().getHours() >= desde && llamada.getFecha().getHours() < hasta;
	}
	
	@Override
	public String toString(){
		return super.toString() + ", por horas, de " + desde + " a " + hasta + " gratis.";
	}
	
}
