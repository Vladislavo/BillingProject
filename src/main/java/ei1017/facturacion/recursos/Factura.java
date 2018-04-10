package ei1017.facturacion.recursos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.tarifa.Tarifa;

public class Factura implements EntradaRegistro, Serializable {
	private static final long serialVersionUID = 1L;
	private Tarifa tarifa;
	private int IDFactura;
	private Date fechaDeEmision;
	private Periodo periodoDeFacturacion;
	private double importe;
	
	public Factura(Tarifa tarifa, int iDFactura, Date fechaDeEmision, Periodo periodoDeFacturacion) {
		super();
		this.tarifa = tarifa;
		this.IDFactura = iDFactura;
		this.fechaDeEmision = fechaDeEmision;
		this.periodoDeFacturacion = periodoDeFacturacion;
		this.importe = -1;
	}
	
	public Factura emitir(List<Llamada> llamadas){
		importe = 0;
		
		for(Llamada llamada : llamadas){
			if(periodoDeFacturacion.estaDentro(llamada.getFecha())){
				importe += llamada.getDuracion()*tarifa.getImporte(llamada);
			}
		}
		
		return this;
	}
	
	public Tarifa getTarifa() {
		return tarifa;
	}

	public int getIDFactura() {
		return IDFactura;
	}

	public Date getFechaDeEmision() {
		return fechaDeEmision;
	}

	public Periodo getPeriodoDeFacturacion() {
		return periodoDeFacturacion;
	}

	public double getImporte() {
		return importe;
	}
	
	public String toWrite(){
		return IDFactura + "&" + importe + "&" +
				fechaDeEmision.toString() + "&" + periodoDeFacturacion.toWrite();
	}

	@Override
	public Date getFecha() {
		return fechaDeEmision;
	}
	
	@Override()
	public String toString(){
		return "Factura #" + IDFactura + "\nFecha de emisión: " + fechaDeEmision + "\n" +
				periodoDeFacturacion + "\n" + tarifa + "\n" + "Importe: " + importe;
	}
}
