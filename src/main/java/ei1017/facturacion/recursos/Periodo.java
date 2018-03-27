package ei1017.facturacion.recursos;

import java.io.Serializable;
import java.util.Date;

public class Periodo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date fechaInicio;
	private Date fechaFinal;
	
	public Periodo(Date fecha_inicio, Date fecha_final) {
		super();
		this.fechaInicio = fecha_inicio;
		this.fechaFinal = fecha_final;
	}

	public Date getFecha_inicio() {
		return fechaInicio;
	}

	public Date getFecha_final() {
		return fechaFinal;
	}
	
	public boolean estaDentro(Date fecha){
		return fechaInicio.before(fecha) && fechaFinal.after(fecha);
	}
	
	@Override
	public String toString(){
		return "Fecha de inicio: " + fechaInicio.toString() + ", fecha final: " + fechaFinal.toString();
	}

	public String toWrite() {
		return fechaInicio.toString() + "#" + fechaFinal.toString();
	}
	
}
