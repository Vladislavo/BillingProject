package ei1017.facturacion.recursos;

import java.io.Serializable;
import java.util.Date;

import ei1017.facturacion.excepciones.CampoDeFechaVacioException;
import ei1017.facturacion.excepciones.PeriodoDeTiempoIncoherenteException;

public class Periodo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Date fechaInicio;
	private Date fechaFinal;
	
	public Periodo(Date fecha_inicio, Date fecha_final) throws 
			PeriodoDeTiempoIncoherenteException, CampoDeFechaVacioException {
		super();
		if(fecha_inicio == null || fecha_final == null) {
			throw new CampoDeFechaVacioException();
		} else if(fecha_final.before(fecha_inicio)) {
			throw new PeriodoDeTiempoIncoherenteException();
		}
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
		return "Período:\nFecha de inicio " + fechaInicio.toString() + "\nFecha final " + fechaFinal.toString();
	}
}
