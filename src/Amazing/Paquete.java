package Amazing;

import java.util.Objects;

public abstract class Paquete {
	private static int proximoIdPaquete = 1;
	private int idPaquete;
	private int volumen;
	private int precio;
	private boolean entregado;

	public Paquete(int volumen, int precio) {
		if (volumen <= 0)
			throw new RuntimeException("El volumen no puede ser menor o igual que 0");
		if (precio <= 0)
			throw new RuntimeException("El precio no puede ser menor o igual que 0");
		this.idPaquete = proximoIdPaquete++;
		this.volumen = volumen;
		this.precio = precio;
		this.entregado = false;
	}

	public abstract int totalAPagar();

	public boolean paqueteEntregado() {
		return this.entregado;
	}

	public void marcarComoPaqueteEntregado() {
		if (!this.entregado)
			this.entregado = true;
		else
			throw new RuntimeException("El paquete ya fue entregado");
	}

	public int consultarVolumenDelPaquete() {
		return this.volumen;
	}

	public int verIdPaquete() {
		return this.idPaquete;
	}

	public int getPrecio() {
		return this.precio;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Paquete paquete = (Paquete) obj;
		return volumen == paquete.volumen && precio == paquete.precio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(volumen, precio);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(idPaquete);
		builder.append(", Volumen: ");
		builder.append(volumen);
		builder.append(", Precio: ");
		builder.append(precio);
		builder.append(", Entregado: ");
		builder.append(entregado);
		return builder.toString();
	}
	
	

}
