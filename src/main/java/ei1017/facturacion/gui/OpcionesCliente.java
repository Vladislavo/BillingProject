package ei1017.facturacion.gui;

public enum OpcionesCliente {
	PARTICULAR("Cliente particular"),
	EMPRESA("Cliente empresa"),
	SALIR("Salir en el menu anterior.");
	
	private String descripcion;
    
    private OpcionesCliente(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
	
    public static OpcionesCliente getOpcion(int posicion) {
	    return values()[posicion];
	}

	public static String getMenu() {
	    StringBuilder sb = new StringBuilder();
	    for(OpcionesCliente opcion: OpcionesCliente.values()) {
	        sb.append(opcion.ordinal());
	        sb.append(".- ");
	        sb.append(opcion.getDescripcion());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
}
