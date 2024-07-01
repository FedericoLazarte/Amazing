package Amazing;

import java.util.Objects;

public class PaqueteEspecial extends Paquete {
	private int porcentaje;
	private int adicional;

	public PaqueteEspecial(int volumen, int precio, int porcentaje, int adicional) {
		super(volumen, precio);
		if (porcentaje <= 0)
			throw new RuntimeException("El porcentaje no puede ser menor o igual a 0");
		if (adicional <= 0)
			throw new RuntimeException("El adicional no puede ser menor o igual a 0");
		this.porcentaje = porcentaje;
		this.adicional = adicional;
	}

	@Override
	public int totalAPagar() {
		int sumaPorcentaje = super.getPrecio() + (super.getPrecio() * this.porcentaje / 100);
		if (super.consultarVolumenDelPaquete() >= 5000) {
			return sumaPorcentaje + (this.adicional * 2);
		}

		if (super.consultarVolumenDelPaquete() >= 3000) {
			return sumaPorcentaje + this.adicional;
		}

		return sumaPorcentaje;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Paquete Especial: [");
		builder.append(super.toString());
		builder.append(", Porcentaje: ");
		builder.append(porcentaje);
		builder.append(", adicional: ");
		builder.append(adicional);
		builder.append("]");
		return builder.toString();
	}
	
	

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		PaqueteEspecial that = (PaqueteEspecial) o;
		return porcentaje == that.porcentaje && adicional == that.adicional;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), porcentaje, adicional);
	}
	
	
}
