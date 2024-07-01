package Amazing;

public class Utilitario extends Transporte {
	private int valorExtra;

	public Utilitario(String patente, int capacidadVolumenTotal, int precioPorViaje, int valorExtra) {
		super(patente, capacidadVolumenTotal, precioPorViaje);
		if(valorExtra < 1)
			throw new RuntimeException("El valor extra debe ser mayor a 0");
		this.valorExtra = valorExtra;
	}

	@Override
	public int costoTotalPorViaje() {

		if (super.totalDePaquetesCargados() > 3)
			return super.verPrecioPorViaje() + valorExtra;
		return super.verPrecioPorViaje();
	}

	@Override
	public boolean cargarPaquete(Paquete paquete) {
		if (paqueteValido(paquete)) {
			return super.cargarPaquete(paquete);
		}

		return false;
	}

	@Override
	public boolean paqueteValido(Paquete paquete) {
		boolean capacidadMaxima = hayEspacioEnCargamento();
		if (esUnPaqueteEspecial(paquete)) {
			return esValidoElVolumenDelPaqueteEspecial(paquete) && capacidadMaxima;
		}
		return esValidoElVolumenDelPaqueteOrdinario(paquete) && capacidadMaxima;
	}

	private boolean esValidoElVolumenDelPaqueteOrdinario(Paquete paquete) {
		boolean volumenOrdinario = paquete.consultarVolumenDelPaquete() >= 2000;

		return volumenOrdinario;
	}

	private boolean esValidoElVolumenDelPaqueteEspecial(Paquete paquete) {

		boolean volumenEspecial = paquete.consultarVolumenDelPaquete() <= 2000;
		return volumenEspecial;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Utilitario: ");
		builder.append(super.toString());
		builder.append(", valor extra: ");
		builder.append(valorExtra);
		builder.append("]");
		return builder.toString();
	}

}
