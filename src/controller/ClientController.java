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
		Client newClient = view.create();
		Database.clients.insert(newClient);
		System.out.println("Cliente agregado con éxito");
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
}