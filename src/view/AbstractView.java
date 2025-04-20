package view;

public abstract class AbstractView<T> {
	public abstract void list();

	public abstract T create();
	
	public abstract int remove();
	
	public abstract T modify();
}
