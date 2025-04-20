package view;

import model.Client;
import model.database.Database;

import java.util.ArrayList;
import java.util.Collection;

public class ClientView extends AbstractView<Client> {
    @Override
    public void list() {
    	Collection<Client> clients = Database.clients.selectAll();

        if (clients.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
            return;
        }

		// Print table of clients
		String[] columns = new String[] {"Código", "Nombre", "CIF", "Email", "Dirección", "RE", "Descubierto"};
		ArrayList<String[]> data = new ArrayList<String[]>();

		for (Client client : clients) {
			ArrayList<String> row = new ArrayList<String>();

			row.add(String.valueOf(client.getId()));
			row.add(client.getName());
			row.add(client.getCif());
			row.add(client.getEmail());
			row.add(client.getAddress());
			row.add((client.getRe() ? "Sí" : "No"));
			row.add(String.format("%.2f€", client.getUncovered() / 100.0));

			data.add(row.toArray(new String[0]));
		}
		
		Output.printTable(columns, data.toArray(new String[0][]));
    }

    @Override
    public Client create() {
        System.out.println("Ingrese los datos del nuevo cliente:");
        String name = Input.readName("Nombre: ");
        String cif = Input.readCif("CIF: ");
        String email = Input.readEmail("Email: ");
        String address = Input.readAddress("Dirección: ");
        boolean re = Input.readYesNo("¿Aplicar recargo de equivalencia? (s/n): ");

        Client newClient = new Client(name, cif, email, address, re);
        return newClient;
    }

    @Override
    public int remove() {
    	// Print all clients
    	list();

    	// Remove client
    	String clientCif = Input.readCif("Ingrese el CIF del cliente que desea eliminar: ");
    	while (Database.clients.selectByCif(clientCif) == null) {
    		System.out.println("No exite ningún cliente con el CIF introducido");
			clientCif = Input.readCif("Ingrese el CIF del cliente que desea eliminar: ");
    	}

    	// Return id of client to be remove
    	Client client = Database.clients.selectByCif(clientCif);
    	return client.getId();
    }

    @Override
    public Client modify() {
    	// Print all clients
    	list();

    	// Update client
    	String clientCif = Input.readCif("Ingrese el CIF del cliente que desea modificar: ");
    	while (Database.clients.selectByCif(clientCif) == null) {
    		System.out.println("No exite ningún cliente con el CIF introducido");
			clientCif = Input.readCif("Ingrese el CIF del cliente que desea modificar: ");
    	}

        System.out.println("Ingrese los nuevos datos del cliente:");
        String name = Input.readName("Nuevo nombre: ");
        String cif = Input.readCif("Nuevo CIF: ");
        String email = Input.readEmail("Nuevo email: ");
        String address = Input.readAddress("Nueva dirección: ");
        boolean re = Input.readYesNo("¿Aplicar recargo de equivalencia? (s/n): ");

        // Return client object with updated values
        Client updatedClient = Database.clients.selectByCif(clientCif);
        updatedClient.setName(name);
        updatedClient.setCif(cif);
        updatedClient.setEmail(email);
        updatedClient.setAddress(address);
        updatedClient.setRe(re);

        return updatedClient;
    }
}