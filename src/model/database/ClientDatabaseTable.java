package model.database;

import model.Client;

public class ClientDatabaseTable extends AbstractDatabaseTable<Client> {
	public Client selectByCif(String cif) {
		for (Client client : data) {
			if (client.getNif().equals(cif))
				return client;
		}

		return null;
	}
}
