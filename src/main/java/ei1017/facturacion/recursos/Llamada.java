package ei1017.facturacion.recursos;

import java.io.Serializable;
import java.util.Date;
import ei1017.facturacion.interfaces.EntradaRegistro;

public class Llamada implements EntradaRegistro, Serializable {
	private static final long serialVersionUID = 1L;
	private String numeroDeTelefono;
	private Date fecha;
	private double duracion;
	
	public Llamada(String numeroDeTelefono, Date fecha, double duracion) {
		super();
		this.numeroDeTelefono = numeroDeTelefono;
		this.fecha = fecha;
		this.duracion = duracion;
	}
	
	public String getNumeroDeTelefono() {
		return numeroDeTelefono;
	}

	public double getDuracion() {
		return duracion;
	}
	
	@Override
	public Date getFecha() {
		return fecha;
	}

	@Override
	public String toString(){
		return "Número de teléfono: " + numeroDeTelefono + "\nDuración: " + duracion + "\nFecha: " + fecha.toString() + "\n";
	}
	
}
