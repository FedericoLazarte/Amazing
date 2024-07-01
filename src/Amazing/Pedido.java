package Amazing;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private static int proximoCodPedido = 1;
	private int codigoPedido;
	private Cliente cliente;
	private List<Paquete> carritoDeCompras;
	private boolean pedidoCerrado;

	public Pedido(Cliente cliente) {
		if (cliente == null)
			throw new RuntimeException("El cliente no puede ser nulo");
		this.codigoPedido = proximoCodPedido++;
		this.cliente = cliente;
		this.carritoDeCompras = new ArrayList<>();
		this.pedidoCerrado = false;
	}

	public int numPedido() {
		return this.codigoPedido;
	}

	public void agregarPaqueteAlCarrito(Paquete paquete) {
		if (this.pedidoCerrado)
			throw new RuntimeException("No se pueden agregar paquetes a un pedido cerrado");
		this.carritoDeCompras.add(paquete);
	}

	public boolean quitarPaquete(int idPaquete) {
		Paquete paquete = buscarPaquetePorId(idPaquete);

		if (paquete == null) {
			throw new RuntimeException("El paquete no se encuentra en el carrito");
		}

		return this.carritoDeCompras.remove(paquete);
	}

	public Cliente cliente() {
		return this.cliente;
	}

	public boolean pedidoCerrado() {
		return this.pedidoCerrado;
	}

	public List<Paquete> verCarritoDeCompras() {
		return new ArrayList<>(this.carritoDeCompras);
	}

	public void cerrarPedido() {
		if (!pedidoCerrado) {
			pedidoCerrado = true;
		} else {
			throw new RuntimeException("El pedido ya está cerrado.");
		}
	}

	public int totalAPagar() {
		return calcularTotalAPagar();
	}

	private int calcularTotalAPagar() {
		int sumaPrecios = 0;
		for (Paquete paq : this.carritoDeCompras) {
			sumaPrecios += paq.totalAPagar();
		}
		return sumaPrecios;
	}

	public Paquete buscarPaquetePorId(int idPaquete) {
		for (Paquete paquete : this.carritoDeCompras) {
			if (paquete.verIdPaquete() == idPaquete) {
				return paquete;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido [Código del Pedido: ");
		builder.append(codigoPedido);
		builder.append(", Cliente: ");
		builder.append(cliente);
		builder.append(", Carrito de Compras: ");
		builder.append(carritoDeCompras);
		builder.append(", El pedido se encuentra cerrado?: ");
		builder.append(pedidoCerrado);
		builder.append("]");
		return builder.toString();
	}
}
