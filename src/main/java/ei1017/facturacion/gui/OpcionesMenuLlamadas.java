package ei1017.facturacion.gui;


public enum OpcionesMenuLlamadas {
	DAR_DE_ALTA_LLAMADA("Dar de alta una llamada."),
	LISTAR_LLAMADAS_CLIENTE("Listar todas las llamadas del cliente."),
	LLAMADAS_ENTRE_DOS_FECHAS("Mostrar un listado de llamadas de un cliente que fueron realizadas entre dos fechas."),
	SALIR("Salir en el menu anterior.");
	
	private String descripcion;
	
	private OpcionesMenuLlamadas(String descripcion) {
        this.descripcion = descripcion;
    }
	
	public String getDescripcion() {
        return descripcion;
    }
	
	public static OpcionesMenuLlamadas getOpcion(int posicion) {
	    return values()[posicion];
	}

	public static String getMenu() {
	    StringBuilder sb = new StringBuilder();
	    for(OpcionesMenuLlamadas opcion: OpcionesMenuLlamadas.values()) {
	        sb.append(opcion.ordinal());
	        sb.append(".- ");
	        sb.append(opcion.getDescripcion());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
}
