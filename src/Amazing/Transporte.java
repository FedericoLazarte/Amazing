package Amazing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class Transporte {
	private String patente;
	private int capacidadVolumenTotal;
	private int precioPorViaje;
	private ArrayList<Paquete> paquetesCargados;

	public Transporte(String patente, int capacidadVolumenTotal, int precioPorViaje) {
		if(patente == null || patente.length() == 0)
			throw new RuntimeException("La patente no puede ser nula ni vacía");
		if(capacidadVolumenTotal < 1)
			throw new RuntimeException("La capacidad de volumen del transporte debe ser mayor a 0");
		if(precioPorViaje < 1)
			throw new RuntimeException("El precio por viaje debe ser mayor a 0");
		this.patente = patente;
		this.capacidadVolumenTotal = capacidadVolumenTotal;
		this.precioPorViaje = precioPorViaje;
		this.paquetesCargados = new ArrayList<>();
	}

	public abstract int costoTotalPorViaje();

	public abstract boolean paqueteValido(Paquete paquete);

	public boolean cargarPaquete(Paquete paquete) {
		if (this.paqueteYaEstaCargado(paquete.verIdPaquete())) {
			throw new RuntimeException("El paquete ya se encuentra cargado");
		}
		if (!paquete.paqueteEntregado()) {
			paquete.marcarComoPaqueteEntregado();
			return this.paquetesCargados.add(paquete);
		}
		return false;
	}

	public boolean esUnPaqueteOrdinario(Paquete paquete) {
		return paquete instanceof PaqueteOrdinario;
	}

	public boolean esUnPaqueteEspecial(Paquete paquete) {
		return paquete instanceof PaqueteEspecial;
	}

	public void quitarPaquete(int idPaquete) {
		if (paqueteYaEstaCargado(idPaquete)) {
			Iterator<Paquete> iterator = this.paquetesCargados.iterator();
			while (iterator.hasNext()) {
				Paquete paquete = iterator.next();
				if (paquete.verIdPaquete() == idPaquete) {
					iterator.remove();
				}
			}
		} else {
			throw new RuntimeException("El paquete no se encuentra en el cargamento");
		}

	}

	public String consultarPatente() {
		return this.patente;
	}

	public int verPrecioPorViaje() {
		return this.precioPorViaje;
	}

	public boolean paqueteYaEstaCargado(int idPaquete) {
		for (Paquete paquete : this.paquetesCargados) {
			if (paquete.verIdPaquete() == idPaquete)
				return true;
		}
		return false;
	}


	public int totalDePaquetesCargados() {
		return this.paquetesCargados.size();
	}

	public boolean hayEspacioEnCargamento() {
		return cantidadDeVolumenDeCargamento() <= this.capacidadVolumenTotal;
	}

	public boolean estaCargado() {
		return !this.paquetesCargados.isEmpty();
	}

	private int cantidadDeVolumenDeCargamento() {
		int volumenTotalDelCargamento = 0;
		for (Paquete paquete : this.paquetesCargados) {
			volumenTotalDelCargamento += paquete.consultarVolumenDelPaquete();
		}
		return volumenTotalDelCargamento;
	}

	@Override
	public boolean equals(Object otroTransporte) {
		if (this == otroTransporte)
			return true;
		if (otroTransporte == null || getClass() != otroTransporte.getClass())
			return false;
		Transporte transporte = (Transporte) otroTransporte;
		if (this.paquetesCargados.size() == 0 || transporte.paquetesCargados.size() == 0)
			return false;
		return this.paquetesCargados.equals(transporte.paquetesCargados);
	}

	@Override
	public int hashCode() {
		return Objects.hash(paquetesCargados);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[Patente: ");
		builder.append(patente);
		builder.append(", Capacidad de volumen total: ");
		builder.append(capacidadVolumenTotal);
		builder.append(", Precio por viaje: ");
		builder.append(precioPorViaje);
		builder.append(", Paquetes cargados: [");
		builder.append(paquetesCargados);
		builder.append("]");
		return builder.toString();
	}

}
