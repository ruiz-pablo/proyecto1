package model;

import database.DatabaseEntity;

public class SoldProduct implements DatabaseEntity<SoldProduct> {
	private int id;
	private int billId;
	private int productId;
	private int amount;

	/***************/
	/* Constructor */
	/***************/

	// Mostly internal constructor with all fields
	public SoldProduct(int id, int billId, int productId, int amount) {
		this.id = id;
		this.billId = billId;
		this.productId = productId;
		this.amount = amount;
	}
	
	// Cut down constructor intended for actual development
	public SoldProduct(int billId, int productId, int amount) {
		this(0, billId, productId, amount);
	}

	/***********/
	/* Getters */
	/***********/

	// NOTE: Required by DatabaseEntity
	@Override
	public int getId() {
		return this.id;
	}

	public int getBillId() {
		return billId;
	}

	public int getProductId() {
		return productId;
	}

	public int getAmount() {
		return amount;
	}

	/***********/
	/* Setters */
	/***********/

	// NOTE: Required by DatabaseEntity
	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void getBillId(int billId) {
		this.billId = billId;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/*****************/
	/* Other methods */
	/*****************/

	/*
	 * Returns a new instance of itself,
	 * required by DatabaseEntity
	 */
	@Override
	public SoldProduct newInstance() {
		return new SoldProduct(this.id, this.billId, this.productId, this.amount);
	}

	@Override
	public String toString() {
		return "SoldProduct [id=" + id + ", billId=" + billId + ", productId=" + productId + ", amount=" + amount + "]";
	}
}
