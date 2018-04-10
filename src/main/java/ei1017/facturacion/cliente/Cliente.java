package ei1017.facturacion.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ei1017.facturacion.excepciones.FacturaNoEncontradaException;
import ei1017.facturacion.interfaces.EntradaRegistro;
import ei1017.facturacion.recursos.Direccion;
import ei1017.facturacion.recursos.Factura;
import ei1017.facturacion.recursos.Llamada;
import ei1017.facturacion.recursos.Periodo;
import ei1017.facturacion.tarifa.Tarifa;

public class Cliente implements EntradaRegistro, Serializable {
	private static final long serialVersionUID = 1L;
	private Tarifa tarifa;
	private String nombre;
	private String NIF;
	private Direccion direccion;
	private String email;
	private Date fechaDeAlta;
	private List<Llamada> llamadas;
	private Map<Integer, Factura> facturas;
	
	public Cliente(Tarifa tarifa, String nombre, String nIF, Direccion direccion, 
			String email, Date fechaDeAlta) {
		super();
		this.tarifa = tarifa;
		this.nombre = nombre;
		NIF = nIF;
		this.direccion = direccion;
		this.email = email;
		this.fechaDeAlta = fechaDeAlta;
		this.llamadas = new ArrayList<Llamada>();
		this.facturas = new HashMap<Integer, Factura>();
	}

	@Override
	public Date getFecha() {
		return fechaDeAlta;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}
	
	public void setTarifa(Tarifa tarifa){
		this.tarifa = tarifa;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNIF() {
		return NIF;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public String getEmail() {
		return email;
	}

	public List<Llamada> getLlamadas() {
		return llamadas;
	}

	public Map<Integer, Factura> getFacturas() {
		return facturas;
	}
	
	public void addLlamada(Llamada llamada){
		llamadas.add(llamada);
	}
	
	public void addFactura(int idFactura, Factura factura){
		facturas.put(idFactura, factura);
	}
	
	public Factura emitirFactura(int idFactura, Date fechaDeEmision, 
		Periodo periodoDeFacturacion){
		
		Factura factura = new Factura(tarifa, idFactura, 
				fechaDeEmision, periodoDeFacturacion);
		factura.emitir(llamadas);
		
		facturas.put(idFactura, factura);

		return factura;
	}
	
	public Factura getFactura(int codFactura) throws FacturaNoEncontradaException{
		Factura factura = facturas.get(codFactura);
		
		if(factura == null){
			throw new FacturaNoEncontradaException("Factura con código " + codFactura + 
					" del cliente " + nombre + " no fue encontrada.");
		}
		
		return factura;
	}
	
	@Override
	public String toString(){
		return "Nombre: " + nombre + ", NIF: " + NIF + ", email: " + email + ", " + direccion +
				", " + tarifa + ", fecha de alta: " + fechaDeAlta.toString();
	}
}
