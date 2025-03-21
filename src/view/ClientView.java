package view;

import model.Client;
import model.database.Database;
import java.util.Collection;

public class ClientView extends AbstractView<Client> {
    @Override
    public void list() {
    	Collection<Client> clients = Database.clients.selectAll();

        if (clients.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
            return;
        }

        System.out.println("Listado de clientes:");
        System.out.println("---------------------");
        for (Client client : clients) {
            System.out.println("ID: " + client.getId());
            System.out.println("Nombre: " + client.getName());
            System.out.println("CIF: " + client.getCif());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Dirección: " + client.getAddress());
            System.out.println("Saldo descubierto: " + client.getUncovered());
            System.out.println("Recargo equivalencia: " + (client.getRe() ? "Sí" : "No"));
            System.out.println("---------------------");
        }
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
    	String clientCif = Input.readCif("Ingrese el CIF del cliente que desea eliminar: ");
    	while (Database.clients.selectByCif(clientCif) == null) {
    		System.out.println("No exite ningún cliente con el CIF introducido");
			clientCif = Input.readCif("Ingrese el CIF del cliente que desea eliminar: ");
    	}

    	Client client = Database.clients.selectByCif(clientCif);
    	return client.getId();
    }

    @Override
    public Client modify() {
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

        Client updatedClient = Database.clients.selectByCif(clientCif);
        updatedClient.setName(name);
        updatedClient.setCif(cif);
        updatedClient.setEmail(email);
        updatedClient.setAddress(address);
        updatedClient.setRe(re);

        return updatedClient;
    }
}