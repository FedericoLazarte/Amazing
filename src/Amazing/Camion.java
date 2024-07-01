package Amazing;

public class Camion extends Transporte {
	private int valorAdicionalPorPaquetes;

	public Camion(String patente, int capacidadVolumenTotal, int precioPorViaje, int valorAdicionalPorPaquetes) {
		super(patente, capacidadVolumenTotal, precioPorViaje);
		if(valorAdicionalPorPaquetes < 1)
			throw new RuntimeException("El valor adicional por paquetes debe ser mayor a 0");
		this.valorAdicionalPorPaquetes = valorAdicionalPorPaquetes;
	}

	@Override
	public int costoTotalPorViaje() {

		return (this.valorAdicionalPorPaquetes) + super.verPrecioPorViaje();
	}

	@Override
	public boolean cargarPaquete(Paquete paquete) {
		if (paqueteValido(paquete))
			return super.cargarPaquete(paquete);
		return false;
	}

	@Override
	public boolean paqueteValido(Paquete paquete) {
		boolean esEspecial = esUnPaqueteEspecial(paquete);
		boolean volumenMayorA2000 = esValidoElVolumenDelPaqueteEspecial(paquete);
		boolean capacidadMaxima = hayEspacioEnCargamento();
		return esEspecial && volumenMayorA2000 && capacidadMaxima;
	}

	private boolean esValidoElVolumenDelPaqueteEspecial(Paquete paquete) {
		return paquete.consultarVolumenDelPaquete() > 2000;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Camión: ");
		builder.append(super.toString());
		builder.append(", valor adicional por paquetes: ");
		builder.append(valorAdicionalPorPaquetes);
		builder.append("]");
		return builder.toString();
	}
}
