package database;

import model.Bill;
import model.Client;
import model.Product;
import model.SoldProduct;

public class Database {
	public static AbstractDatabaseTable<Client> clients = new AbstractDatabaseTable<Client>();
	public static AbstractDatabaseTable<Product> products = new AbstractDatabaseTable<Product>();
	public static AbstractDatabaseTable<Bill> bills = new AbstractDatabaseTable<Bill>();
	public static AbstractDatabaseTable<SoldProduct> soldProducts = new AbstractDatabaseTable<SoldProduct>();
}
