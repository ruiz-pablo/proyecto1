package database;

public interface DatabaseTableEntity<T> {

	public int getId();
	public void setId(int id);
	public T newInstance();
}
