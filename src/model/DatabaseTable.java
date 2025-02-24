package model;

import java.util.Collection;

public interface DatabaseTable<T> {
	// Returns null if no id is found
	public abstract T select(int id);

	// Returns an empty Collection if the table has no rows
	public abstract Collection<T> selectAll();
	
	// T.id cannot be duplicated
	public abstract void insert(T value) throws IllegalArgumentException;
	
	// T.id must exists
	public abstract void remove(int id) throws IllegalArgumentException;
	
	// Return true id the given id is present in the table,
	// otherwise returns false
	public abstract boolean exists(int id);
	
	// TODO: Figure out how to implement the update method
	// public void update(int id, ...);
}