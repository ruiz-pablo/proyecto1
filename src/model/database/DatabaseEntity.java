package model.database;

public interface DatabaseEntity<T> {

	public int getId();
	public void setId(int id);
	public T newInstance();
}
