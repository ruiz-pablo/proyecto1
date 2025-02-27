package model;

import database.DatabaseEntity;

public class Bill implements DatabaseEntity<Bill> {
	private int     id;
	private int     clientId;
	private String  date; // TODO: Change to a date format
	private int     amount;
	private boolean paid;
	
	/****************/
	/* Constructors */
	/****************/

	// Mostly internal constructor with all fields
	public Bill(int id, int clientId, String date, int amount, boolean paid) {
		this.id = id;
		this.clientId = clientId;
		this.date = date;
		this.amount = amount;
		this.paid = paid;
	}
	
	// Cut down constructor intended for actual development
	public Bill(int clientId, int amount, boolean paid) {
		this(0, clientId, "now", amount, paid); // TODO: Replace 'now' for actual date
	}

	/***********/
	/* Getters */
	/***********/

	// NOTE: Required by DatabaseEntity
	@Override
	public int getId() {
		return id;
	}

	public int getClientId() {
		return clientId;
	}

	public String getDate() {
		return date;
	}

	public int getAmount() {
		return amount;
	}

	public boolean isPaid() {
		return paid;
	}

	/***********/
	/* Setters */
	/***********/

	// NOTE: Required by DatabaseEntity
	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setDate(String date) {
		this.date = date;
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
	public Bill newInstance() {
		return new Bill(
				this.id,
				this.clientId,
				this.date,
				this.amount,
				this.paid
		);
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", clientId=" + clientId + ", date=" + date + ", amount=" + amount + ", paid=" + paid + "]";
	}
}
