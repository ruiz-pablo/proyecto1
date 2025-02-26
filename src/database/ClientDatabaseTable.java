package database;

import java.util.ArrayList;
import java.util.Collection;

import model.Client;

public class ClientDatabaseTable implements DatabaseTable<Client> {
	private int lastId = 0;
	private ArrayList<Client> data;
	
	public ClientDatabaseTable() {
		data = new ArrayList<Client>();
	}
	
	private int newId() {
		lastId++;
		return lastId;
	}

	private int indexOf(int id) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getId() == id)
				return i;
		}

		return -1;
	}

	@Override
	public Client select(int id) {
		for (Client client : data) {
			if (client.getId() == id)
				return client;
		}

		return null;
	}

	@Override
	public Collection<Client> selectAll() {
		return data;
	}

	@Override
	public void insert(Client client) {
		// NOTE: The id of the client is manually generated
		Client toInsert = new Client(
				newId(),
				client.getName(),
				client.getNif(),
				client.getEmail(),
				client.getAddress(),
				client.getUncovered(),
				client.getRe()
				);

		data.add(toInsert);
	}

	@Override
	public void remove(int id) throws IllegalArgumentException {
		if (!exists(id))
			throw new IllegalArgumentException(String.format("There is no client in the table with id %d", id));

		data.remove(indexOf(id));
	}

	@Override
	public boolean exists(int id) {
		Client client = select(id);
		if (client != null)
			return true;

		return false;
	}

	@Override
	public void update(int id, Client client) {
		if (!exists(id) || client == null)
			throw new IllegalArgumentException(String.format("The given id %d does not exists", id));

		Client newClient = new Client(
				id,
				client.getName(),
				client.getNif(),
				client.getEmail(),
				client.getAddress(),
				client.getUncovered(),
				client.getRe()
				);
		
		// Replace with newClient
		data.set(indexOf(id), newClient);
	}
}