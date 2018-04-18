package ei1017.facturacion.tarifa;

import java.io.Serializable;

public class TarifaBasica extends Tarifa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public TarifaBasica(){
		super();
	}
	
	@Override
	public String toString() {
		return "Tarifa basica";
	}

}
