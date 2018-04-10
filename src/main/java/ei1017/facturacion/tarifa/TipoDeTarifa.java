package ei1017.facturacion.tarifa;

public enum TipoDeTarifa {
	DE_TARDE("La tarifa de tardes entre las 16:00 y las 20:00 a 5 céntimos por minuto"), 
	DOMINGO_GRATIS("La tarifa de domingos gratis");
	
	private String descripcion;
	
	private TipoDeTarifa(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public static TipoDeTarifa getOpcion(int posicion) {
	    return values()[posicion];
	}

	public static String getMenu() {
	    StringBuilder sb = new StringBuilder();
	    for(TipoDeTarifa opcion: TipoDeTarifa.values()) {
	        sb.append(opcion.ordinal());
	        sb.append(".- ");
	        sb.append(opcion.getDescripcion());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
}
