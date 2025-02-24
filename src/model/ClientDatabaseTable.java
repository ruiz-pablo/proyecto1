package model;

import java.util.ArrayList;
import java.util.Collection;

public class ClientDatabaseTable implements DatabaseTable<Client> {
	private ArrayList<Client> data;
	private static int lastId = 0;
	
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
		// NOTE: The id of the client is ignored
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

		if (id == 0)
			throw new IllegalArgumentException("The id 0 is reserved and is not valid");

		data.remove(indexOf(id));
	}

	@Override
	public boolean exists(int id) {
		Client client = select(id);
		if (client != null)
			return true;

		return false;
	}
}