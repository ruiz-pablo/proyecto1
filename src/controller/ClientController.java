package controller;

import model.Client;
import model.database.Database;
import view.ClientView;
import view.Input;

public class ClientController extends AbstractController {

	private ClientView view;

	public ClientController() {
		this.view = new ClientView();
	}

	@Override
	public void list() {
		view.list();
	}

	@Override
	public void create() {
		try {
			Client newClient = view.create();
			addClient(newClient);
			System.out.println("Cliente agregado con éxito");
		} catch (IllegalArgumentException e) {
			System.out.println("No se pudo agregar el cliente");
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void remove() {
		int clientId = view.remove();
		
		if (!Input.readYesNo("¿Seguro que quire eliminar el cliente? (s/n): ")) {
			System.out.println("Cancelando...");
		}
		else {
			Database.clients.remove(clientId);
			System.out.println("Cliente eliminado con éxito.");
		}
	}

	@Override
	public void modify() {
		Client modifiedClient = view.modify();

		if (!Input.readYesNo("¿Seguro que quire modificar el cliente? (s/n): ")) {
			System.out.println("Cancelando...");
		}
		else {
			Database.clients.update(modifiedClient);
			System.out.println("Producto actualizado con éxito.");
		}
	}
	
	public void addClient(Client client) throws IllegalArgumentException {
		// Check for valid parameters
		if (client == null ||
			client.getName() == null ||
			client.getCif() == null ||
			client.getEmail() == null ||
			client.getAddress() == null)
			throw new IllegalArgumentException("Alguno de los parametros del cliente no es valido");
		
		// Verify CIF format
		if (!Input.isCifValid(client.getCif()))
			throw new IllegalArgumentException("El CIF introducido no tiene un formato valido");
		
		// Uncovered cannot be negative
		if (client.getUncovered() < 0)
			throw new IllegalArgumentException("El descubierto no puede ser negativo");
		
		// Check for duplicated CIF
		if (Database.clients.selectByCif(client.getCif()) != null)
			throw new IllegalArgumentException("Ya existe un cliente en la base de datos con ese CIF");
		
		// Add to database
		Database.clients.insert(client);
	}
}