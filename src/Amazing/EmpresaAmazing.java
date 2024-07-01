package Amazing;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmpresaAmazing implements IEmpresa {
	private String cuit;
	private HashMap<String, Transporte> transportes;
	private HashMap<Integer, Pedido> pedidos;
	private double facturacionTotalPedidosCerrados;

	public EmpresaAmazing(String cuit) {
		if(cuit.length() == 0 || cuit == null)
			throw new RuntimeException("El cuit no puede ser vacío ni nulo");
		this.cuit = cuit;
		this.transportes = new HashMap<>();
		//this.pedidos = new ArrayList<>();
		this.pedidos = new HashMap<>();
		this.facturacionTotalPedidosCerrados = 0;
	}

	@Override
	public void registrarAutomovil(String patente, int volMax, int valorViaje, int maxPaq) {
		if (transporteSinRegistrar(patente)) {
			Automovil automovil = new Automovil(patente, volMax, valorViaje, maxPaq);
			this.transportes.put(patente, automovil);
		} 
	}

	@Override
	public void registrarUtilitario(String patente, int volMax, int valorViaje, int valorExtra) {
		if (transporteSinRegistrar(patente)) {
			Utilitario utilitario = new Utilitario(patente, volMax, valorViaje, valorExtra);
			this.transportes.put(patente, utilitario);
		} 
	}

	@Override
	public void registrarCamion(String patente, int volMax, int valorViaje, int adicXPaq) {
		if (transporteSinRegistrar(patente)) {
			Camion camion = new Camion(patente, volMax, valorViaje, adicXPaq);
			this.transportes.put(patente, camion);
		} 
	}

	@Override
	public int registrarPedido(String cliente, String direccion, int dni) {
		Cliente clientePedido = new Cliente(cliente, dni, direccion);
		Pedido pedido = new Pedido(clientePedido);
		this.pedidos.put(pedido.numPedido(), pedido);
		return pedido.numPedido();
	}

	@Override
	public int agregarPaquete(int codPedido, int volumen, int precio, int costoEnvio) {
		Paquete paqueteOrdinario = new PaqueteOrdinario(volumen, precio, costoEnvio);
		return this.agregarPaquete(codPedido, paqueteOrdinario);
	}

	@Override
	public int agregarPaquete(int codPedido, int volumen, int precio, int porcentaje, int adicional) {
		Paquete paqueteEspecial = new PaqueteEspecial(volumen, precio, porcentaje, adicional);
		return this.agregarPaquete(codPedido, paqueteEspecial);
	}

	public boolean quitarPaquete(int codPaquete) {
		for(Integer numPed : this.pedidos.keySet()) {
			Pedido pedido = this.pedidos.get(numPed);
			if(pedido.pedidoCerrado()) {
				throw new RuntimeException("El pedido ya se encuentra finalizado");
			} else {
				return pedido.quitarPaquete(codPaquete);
			}
		}
		return false;
		
	}

	@Override
	public double cerrarPedido(int codPedido) {
		Pedido pedido = this.pedidos.get(codPedido);
		if (pedido == null) {
			throw new RuntimeException("El pedido no se encuentra registrado");
		}
		if (pedido.pedidoCerrado())
			throw new RuntimeException("El pedido ya está cerrado");
		double totalAPagar = pedido.totalAPagar();
		pedido.cerrarPedido();

		this.facturacionTotalPedidosCerrados += totalAPagar;

		return totalAPagar;
	}

	@Override
	public String cargarTransporte(String patente) {
		Transporte transporte = buscarTransportePorPatente(patente);
		if (transporte == null) {
			throw new RuntimeException("La pantente del transporte no está registrada");
		}

		StringBuilder sb = new StringBuilder();
		for (Integer codPedido : this.pedidos.keySet()) {
			Pedido pedido = this.pedidos.get(codPedido);
			if (pedido.pedidoCerrado()) {
				for (Paquete paq : pedido.verCarritoDeCompras()) {
					if (transporte.cargarPaquete(paq)) {
						sb.append(" + [ ").append(pedido.numPedido()).append(" - ")
								.append(pedido.numPedido()).append(" ] ")
								.append(pedido.cliente().direccion()).append("\n");
					}
				}
			}
		}
		return sb.toString();
	}

	@Override
	public double costoEntrega(String patente) {
		Transporte transporte = buscarTransportePorPatente(patente);
		if (transporte == null) {
			throw new RuntimeException("La patente del transporte no está registrada.");
		}
		if (!transporte.estaCargado()) {

			throw new RuntimeException("El transporte no está cargado");
		}
		double costoBase = transporte.costoTotalPorViaje();
		return costoBase;
	}

	@Override
	public Map<Integer, String> pedidosNoEntregados() {
		Map<Integer, String> pedidosSinEntregar = new HashMap<>();
		for(Integer cod : this.pedidos.keySet()) {
			Pedido pedido = this.pedidos.get(cod);
			for(Paquete paq : pedido.verCarritoDeCompras()) {
				if(!paq.paqueteEntregado() && pedido.pedidoCerrado()) {
					pedidosSinEntregar.put(pedido.numPedido(), pedido.cliente().nombre());
				}
			}
		}
		return pedidosSinEntregar;
	}

	@Override
	public double facturacionTotalPedidosCerrados() {
		return this.facturacionTotalPedidosCerrados;
	}

	public boolean hayTransportesIdenticos() {
		return hayTransportesIdenticos(this.transportes);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmpresaAmazing [\n");
		builder.append("\tCUIT: ").append(cuit).append(",\n");
		builder.append("\tTransportes Registrados: [\n");
		for (String patente : transportes.keySet()) {
			Transporte transporte = this.transportes.get(patente);
			builder.append("\t\t").append(transporte.toString()).append(",\n");
		}
		builder.append("\t],\n");
		builder.append("\tPedidos Registrados: [\n");
		for (Integer codPedido :  this.pedidos.keySet()) {
			Pedido pedido = this.pedidos.get(codPedido);
			builder.append("\t\t").append(pedido.toString()).append(",\n");
		}
		builder.append("\t],\n");
		builder.append("\tFacturación Total de Pedidos Cerrados: ").append(facturacionTotalPedidosCerrados)
				.append("\n");
		builder.append("]");
		return builder.toString();
	}
	
	private <K, V> boolean hayTransportesIdenticos(Map<K, V> hashMap) {
		
		// Obtener la colección de valores
		Collection<V> valores = hashMap.values();
		
		// Convertir la colección de valores en un Set para eliminar duplicados
		Set<V> conjuntoValores = new HashSet<>(valores);
		
		// Comparar el tamaño del Set con el tamaño original
		return valores.size() != conjuntoValores.size();
	}

	private boolean transporteSinRegistrar(String patente) {
		if (this.transportes.containsKey(patente)) {
			throw new RuntimeException("El Vehículo ya se encuentra registado");
		}
		return true;
	}

	private Transporte buscarTransportePorPatente(String patente) {	
		return this.transportes.get(patente);
	}

	private boolean pedidoYaRegistrado(int idPedido) {
		
		return this.pedidos.containsKey(idPedido);
	}
	
	private int agregarPaquete(int codPedido, Paquete paquete) {
		if(this.pedidoYaRegistrado(codPedido)) {
			Pedido pedido = this.pedidos.get(codPedido);
			if(!pedido.pedidoCerrado()) {
				pedido.agregarPaqueteAlCarrito(paquete);
				return pedido.numPedido();
			} else {
				throw new RuntimeException("El pedido ya se encuentra Cerrado");
			}
		} else {
			throw new RuntimeException("El pedido no se encuentra registado");
		}
	}
	
	

	
	
}
