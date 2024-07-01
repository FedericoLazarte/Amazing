package Amazing;

public class Cliente {
	private String nombre;
	private int dni;
	private String direccion;

	public Cliente(String nombre, int dni, String direccion) {
		if(nombre == null || nombre.length() == 0)
			throw new RuntimeException("El nombre no puede estar vacío o ser null");
		if(direccion == null || direccion.length() == 0)
			throw new RuntimeException("La dirección no puede estar vacío o ser null");
		this.nombre = nombre;
		this.dni = dni;
		this.direccion = direccion;
	}

	public String nombre() {
		return this.nombre;
	}

	public int dni() {
		return this.dni;
	}

	public String direccion() {
		return this.direccion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente: [nombre: ");
		builder.append(nombre);
		builder.append(", DNI: ");
		builder.append(dni);
		builder.append(", direccion: ");
		builder.append(direccion);
		builder.append("]");
		return builder.toString();
	}
	
	
}
