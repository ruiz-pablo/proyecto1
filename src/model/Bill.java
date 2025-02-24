package model;

public class Bill {
	private int id;
	private int clientId;
	String date; // TODO: Change to a date format
	private int amount;
	boolean paid;

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

	public Bill(int clientId, String date, int amount, boolean paid) {
		super();
		this.clientId = clientId;
		this.date = date;
		this.amount = amount;
		this.paid = paid;
	}
}