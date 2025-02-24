package controller;

import database.Database;
import model.Client;

public class Main {
	public static void main(String[] args) {
		Client client = new Client("Fruteria Pepe S.L.", "12312312A", "contacto@fruteriapepe.com", "Calle de las Manzanas 2", false);
		Client client2 = new Client("Maria", "12312312A", "contacto@fruteriapepe.com", "Calle de las Manzanas 2", false);
		Client client3 = new Client("Fruteria Pepe S.L.", "12312312A", "contacto@fruteriapepe.com", "Calle de las Manzanas 2", false);

		Database.clients.insert(client);  // 1
		Database.clients.update(1, client2);
		Database.clients.insert(client3); // 3
		
		Database.clients.remove(2);

		for (Client c : Database.clients.selectAll())
			System.out.println(c);
		
		System.out.println("Found: " + Database.clients.select(1));
	}
}