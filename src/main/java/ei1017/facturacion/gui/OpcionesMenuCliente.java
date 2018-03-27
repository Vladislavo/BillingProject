package ei1017.facturacion.gui;

public enum OpcionesMenuCliente {
	DAR_DE_ALTA_NUEVO_CLIENTE("Dar de alta a un nuevo cliente."),
	BORRAR_CLIENTE("Borrar un cliente."),
	CAMBIAR_TARIFA_CLIENTE("Cambiar la tarifa de un cliente."),
	RECUPERAR_DATOS_CLIENTE_NIF("Recuperar los datos de un cliente a partir de su NIF."),
	RECUPERAR_LISTADO_CLIENTES("Recuperar el listado de todos los clientes."),
	CLIENTES_ENTRE_DOS_FECHAS("Mostrar un listado de clientes que fueron dados de alta entre dos fechas."),
	SALIR("Salir en el menu anterior.");
	
	private String descripcion;
    
    private OpcionesMenuCliente(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
	
    public static OpcionesMenuCliente getOpcion(int posicion) {
	    return values()[posicion];
	}

	public static String getMenu() {
	    StringBuilder sb = new StringBuilder();
	    for(OpcionesMenuCliente opcion: OpcionesMenuCliente.values()) {
	        sb.append(opcion.ordinal());
	        sb.append(".- ");
	        sb.append(opcion.getDescripcion());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
}
