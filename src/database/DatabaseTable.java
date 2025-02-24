package database;

import java.util.Collection;

public interface DatabaseTable<T> {
	// Returns null if no id is found
	public abstract T select(int id);

	// Returns an empty Collection if the table has no rows
	public abstract Collection<T> selectAll();
	
	// Inserts 'value' into table ignoring the given T.id and assigning a new one
	public abstract void insert(T value) throws IllegalArgumentException;
	
	// T.id must exists
	public abstract void remove(int id) throws IllegalArgumentException;
	
	// Returns true id the given id is present in the table,
	// otherwise returns false
	public abstract boolean exists(int id);
	
	// TODO: Figure out how to implement the update method
	// public void update(int id, ...);
}