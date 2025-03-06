package model.database;

import model.Bill;
import model.Client;
import model.Product;
import model.SoldProduct;

public class Database {
	public static final AbstractDatabaseTable<Client> clients = new AbstractDatabaseTable<Client>();
	public static final AbstractDatabaseTable<Product> products = new AbstractDatabaseTable<Product>();
	public static final AbstractDatabaseTable<Bill> bills = new AbstractDatabaseTable<Bill>();
	public static final AbstractDatabaseTable<SoldProduct> soldProducts = new AbstractDatabaseTable<SoldProduct>();
}
