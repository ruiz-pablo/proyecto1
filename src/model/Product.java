package model;

import database.DatabaseTableEntity;

public class Product implements DatabaseTableEntity<Product> {
	private int    id;
	private String name;
	private String description;
	private int    price;
	private int    iva;
	private int    stock;
	
	/***************/
	/* Constructor */
	/***************/

	// Mostly internal constructor with all fields
	public Product(int id, String name, String description, int price, int iva, int stock) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.iva = iva;
		this.stock = stock;
	}

	// Cut down constructor intended for actual development
	public Product(String name, String description, int price, int iva) {
		this(0, name, description, price, iva, 0);
	}

	/***********/
	/* Getters */
	/***********/

	// NOTE: Required by DatabaseTableEntity
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

	/***********/
	/* Setters */
	/***********/

	// NOTE: Required by DatabaseTableEntity
	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	/*****************/
	/* Other methods */
	/*****************/

	/*
	 * Returns a new instance of itself,
	 * required by DatabaseTableEntity
	 */
	@Override
	public Product newInstance() {
		return new Product(
				this.id,
				this.name,
				this.description,
				this.price,
				this.iva,
				this.stock
				);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", iva=" + iva + ", stock=" + stock + "]";
	}
}