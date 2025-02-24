package database;

import java.util.Collection;

public interface DatabaseTable<T> {
	// Returns null if no id is found
	public abstract T select(int id);

	// Returns an empty Collection if the table has no rows
	public abstract Collection<T> selectAll();
	
	// Inserts 'value' into table ignoring the given value.id and assigning a new one
	public abstract void insert(T value) throws IllegalArgumentException;
	
	// 'id' must exists
	public abstract void remove(int id) throws IllegalArgumentException;
	
	// Returns true id the given id is present in the table,
	// otherwise returns false
	public abstract boolean exists(int id);
	
	// Updates an entry in the table with the values of 'value'
	// value.id is ignored and NOT change
	public void update(int id, T value) throws IllegalArgumentException;
}