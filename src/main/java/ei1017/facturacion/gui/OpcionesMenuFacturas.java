package ei1017.facturacion.gui;

public enum OpcionesMenuFacturas {
	EMITIR_FACTURA("Emitir una factura para un cliente, calculando el importe de la misma en función de las llamadas."),
	RECUPERAR_DATOS_FACTURA("Recuperar los datos de una factura a partir de su código."),
	RECUPERAR_TODAS_FACTURAS("Recuperar todas las facturas de un cliente."),
	FACTURAS_ENTRE_DOS_FECHAS("Mostrar un listado de facturas de un cliente emitidas entre dos fechas."),
	SALIR("Salir en el menu anterior.");
	
	private String descripcion;
	
	private OpcionesMenuFacturas(String descripcion) {
        this.descripcion = descripcion;
    }
	
	public String getDescripcion() {
        return descripcion;
    }
	
	public static OpcionesMenuFacturas getOpcion(int posicion) {
	    return values()[posicion];
	}

	public static String getMenu() {
	    StringBuilder sb = new StringBuilder();
	    for(OpcionesMenuFacturas opcion: OpcionesMenuFacturas.values()) {
	        sb.append(opcion.ordinal());
	        sb.append(".- ");
	        sb.append(opcion.getDescripcion());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
}
