package Amazing;

public class Automovil extends Transporte {
	private int limiteDePaquetes;

	public Automovil(String patente, int capacidadVolumenTotal, int precioPorViaje, int limiteDePaquetes) {
		super(patente, capacidadVolumenTotal, precioPorViaje);
		if(limiteDePaquetes < 1)
			throw new RuntimeException("El limite de paquetes debe ser mayor a 0");
		this.limiteDePaquetes = limiteDePaquetes;
	}

	@Override
	public int costoTotalPorViaje() {
		return super.verPrecioPorViaje();
	}

	@Override
	public boolean cargarPaquete(Paquete paquete) {
		if (this.paqueteValido(paquete)) {
			return super.cargarPaquete(paquete);
		}
		return false;
	}

	@Override
	public boolean paqueteValido(Paquete paquete) {
		boolean esOrdinario = esUnPaqueteOrdinario(paquete);
		boolean volumenMenorA2000 = esValidoElVolumenDelPaqueteOrdinario(paquete);
		boolean capacidadMaxima = hayEspacioEnCargamento();
		return esOrdinario && volumenMenorA2000 && this.hayEspacioParaCargar() && capacidadMaxima;
	}

	private boolean hayEspacioParaCargar() {
		return totalDePaquetesCargados() <= this.limiteDePaquetes;
	}

	private boolean esValidoElVolumenDelPaqueteOrdinario(Paquete paquete) {
		return paquete.consultarVolumenDelPaquete() < 2000;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Automóvil: ");
		builder.append(super.toString());
		builder.append(", Límite de paquetes: ");
		builder.append(limiteDePaquetes);
		builder.append("]");
		return builder.toString();
	}

}
