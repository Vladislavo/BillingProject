package ei1017.facturacion.gui;

public enum OpcionesMenu {
	CLIENTES("Gestionar clientes"),
	FACTURAS("Gestionar faturas de clientes"),
	LLAMADAS("Gestionar llamadas de clientes"),
	SALIR("Salir de la aplicacion.");
	
	private String descripcion;
    
    private OpcionesMenu(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
	
    public static OpcionesMenu getOpcion(int posicion) {
	    return values()[posicion];
	}

	public static String getMenu() {
	    StringBuilder sb = new StringBuilder();
	    for(OpcionesMenu opcion: OpcionesMenu.values()) {
	        sb.append(opcion.ordinal());
	        sb.append(".- ");
	        sb.append(opcion.getDescripcion());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
}
