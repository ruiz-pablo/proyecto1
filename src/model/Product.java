package model;

public class Product {
	private int id;
	private String name;
	private String description;
	private int price;
	private int iva;
	private int stock;

	public Product(String name, String description, int price, int iva) {
		this.id = 0;
		this.name = name;
		this.description = description;
		this.price = price;
		this.iva = iva;
		this.stock = 0;
	}
	
	public int getId() {
	    return this.id;
	}

	public String getName() {
	    return this.name;
	}

	public String getDescription() {
	    return this.description;
	}

	public int getPrice() {
	    return this.price;
	}

	public int getIva() {
	    return this.iva;
	}

	public int getStock() {
	    return this.stock;
	}
}