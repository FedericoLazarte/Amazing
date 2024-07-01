package Amazing;

import java.util.Objects;

public class PaqueteOrdinario extends Paquete {
	private int costoEnvio;

	public PaqueteOrdinario(int volumen, int precio, int costoEnvio) {
		super(volumen, precio);
		if (costoEnvio <= 0)
			throw new RuntimeException("El costo de envío no puede ser menor o igual a 0");
		this.costoEnvio = costoEnvio;
	}

	@Override
	public int totalAPagar() {
		return super.getPrecio() + this.costoEnvio;
	}

	public int consultarCostoEnvio() {
		return this.costoEnvio;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		PaqueteOrdinario that = (PaqueteOrdinario) o;
		return costoEnvio == that.costoEnvio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), costoEnvio);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Paquete Ordinario: [");
		builder.append(super.toString());
		builder.append(",Costo de envío: ");
		builder.append(costoEnvio);
		builder.append("]");
		return builder.toString();
	}

}
