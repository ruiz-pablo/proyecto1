package database;

import java.util.ArrayList;
import java.util.Collection;

public class AbstractDatabaseTable<T extends DatabaseEntity<T>> {
	private int lastId = 0;
	private ArrayList<T> data;

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

	private T getClone(T entity) {
		return entity.newInstance();
	}

	public AbstractDatabaseTable() {
		data = new ArrayList<T>();
	}

	public int insert(T entity) throws IllegalArgumentException {
		if (entity == null)
			throw new IllegalArgumentException("entity is null");
		
		T clone = getClone(entity);
		int id = newId();
		clone.setId(id);
		data.add(clone);
		return id;
	}
	
	public T select(int id) {
		for (T entity : data) {
			if (entity.getId() == id)
				return getClone(entity);
		}
		
		return null;
	}
	
	public Collection<T> selectAll() {
		return data;
	}
	
	public void remove(int id) throws IllegalArgumentException {
		int index = indexOf(id);
		if (index == -1)
			throw new IllegalArgumentException(String.format("There is no entity with id %d in the database", id));

		data.remove(index);
	}
	
	public void update(T entity) throws IllegalArgumentException {
		if (entity == null)
			throw new IllegalArgumentException("entity is null");
		if (entity.getId() == 0)
			throw new IllegalArgumentException("The id 0 is reserve. Make sure your entity has a valid id");
		if (!exists(entity.getId()))
			throw new IllegalArgumentException(String.format("There is no entity with id %d in the database", entity.getId()));
		
		T clone = getClone(entity);
		data.set(indexOf(entity.getId()), clone);
	}

	public boolean exists(int id) {
		if (indexOf(id) != -1)
			return true;

		return false;
	}
}