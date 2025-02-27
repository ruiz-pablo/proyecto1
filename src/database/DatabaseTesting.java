package database;

import java.util.ArrayList;

import model.Client;
import model.Product;
import model.Bill;
import model.SoldProduct;

public class DatabaseTesting {
	public static void test()
	{		
		/**************/
		/* WARNING!!! */
		/**************/

		/* 
		 * All of this testing code is temporary 
		 * and only for development
		 * 
		 * Remember to add the option '-ea' to
		 * the JVM in order to enable asserts
		 */

		testClients();
		testProducts();
		testBills();
		testSoldProducts();

		System.out.println("[INFO] All test passed");
	}

	/****************************/
	/* Testing code for clients */
	/****************************/
	/*
	 * [x] select
	 * [x] selectAll
	 * [x] insert
	 * [x] remove
	 * [x] exists
	 * [x] update
	 */
	public static void testClients() {
		assert Database.clients.selectAll().size() == 0;

		ArrayList<Client>clients = new ArrayList<Client>();
		clients.add(new Client("Fruteria Pepe S.L.", "12312312A", "contacto@fruteriapepe.com", "Calle Mayor 12, Madrid", false));
		clients.add(new Client("Supermercado El Barato", "98765432B", "info@elbarato.com", "Avenida de la Constitución 34, Barcelona", true));
		clients.add(new Client("Restaurante La Estrella", "76543210C", "reservas@laestrella.com", "Calle de la Luna 56, Valencia", false));
		clients.add(new Client("Fruteria La Huerta", "54321098D", "fruteria@lahuerta.com", "Calle de la Huerta 23, Sevilla", true));
		clients.add(new Client("Mercado de San Miguel", "32109876E", "info@mercadodesanmiguel.com", "Plaza de San Miguel 1, Madrid", false));
		clients.add(new Client("Tienda de Productos Ecológicos", "87654321F", "ecologico@tienda.com", "Calle de la Tierra 45, Granada", true));
		clients.add(new Client("Hotel La Casa Grande", "65432109G", "reservas@lacasa-grande.com", "Avenida de la Playa 123, Málaga", false));
		clients.add(new Client("Fruteria El Jardín", "43210987H", "fruteria@eljardin.com", "Calle del Jardín 67, Alicante", true));
		clients.add(new Client("Supermercado La Unión", "32109876I", "info@launion.com", "Avenida de la Unión 90, Zaragoza", false));
		clients.add(new Client("Restaurante La Casa de Toño", "98765432J", "reservas@lacasadetono.com", "Calle de la Casa 34, Córdoba", true));
		
		// Test insert
		for (Client c : clients)
			Database.clients.insert(c);
		
		// Test exists
		assert !Database.clients.exists(0);
		assert !Database.clients.exists(999);
		assert Database.clients.exists(1);
		assert Database.clients.exists(5);
		assert Database.clients.exists(8);

		// Test select
		Client selectedClient = Database.clients.select(8);
		assert selectedClient.getName().equals("Fruteria El Jardín");
		assert Database.clients.select(0) == null;
		assert Database.clients.select(999) == null;
		
		// Test selectAll
		assert Database.clients.selectAll().size() == 10;
		
		// Test update
		Client newClient = selectedClient.newInstance();
		newClient.setName("nombre");
		newClient.setNif("nif");
		newClient.setEmail("email");
		newClient.setAddress("direccion");
		newClient.setUncovered(123);
		newClient.setRe(true);

		Database.clients.update(newClient);

		// Update selected client data
		selectedClient = Database.clients.select(selectedClient.getId());
		
		// Check that all parameters are equal
		assert selectedClient.getName().equals(newClient.getName());
		assert selectedClient.getNif().equals(newClient.getNif());
		assert selectedClient.getEmail().equals(newClient.getEmail());
		assert selectedClient.getAddress().equals(newClient.getAddress());
		assert selectedClient.getUncovered() == newClient.getUncovered();
		assert selectedClient.getRe() == newClient.getRe();

		// Test remove
		Database.clients.remove(selectedClient.getId());
		assert !Database.clients.exists(selectedClient.getId());
	}

	/*****************************/
	/* Testing code for products */
	/*****************************/
	/*
	 * [x] select
	 * [x] selectAll
	 * [x] insert
	 * [x] remove
	 * [x] exists
	 * [x] update
	 */
	public static void testProducts() {
		assert Database.products.selectAll().size() == 0;

		ArrayList<Product> products = new ArrayList<Product>();
		products.add(new Product("Manzana Granny Smith", "manzana verde y ácida", 120, 10));
		products.add(new Product("Plátano", "plátano maduro y dulce", 80, 10));
		products.add(new Product("Naranja Valencia", "naranja jugosa y dulce", 100, 10));
		products.add(new Product("Fresa", "fresa fresca y dulce", 150, 10));
		products.add(new Product("Pera", "pera madura y suave", 140, 10));
		products.add(new Product("Uva Red Globe", "uva roja y dulce", 200, 10));
		products.add(new Product("Kiwi", "kiwi verde y peludo", 180, 10));
		products.add(new Product("Mango Alphonso", "mango maduro y dulce", 250, 10));
		products.add(new Product("Peach", "melocotón maduro y jugoso", 220, 10));
		products.add(new Product("Pera Bartlett", "pera madura y suave", 160, 10));
		products.add(new Product("Nectarina", "nectarina madura y dulce", 200, 10));
		products.add(new Product("Ciruela Santa Rosa", "ciruela madura y dulce", 180, 10));
		products.add(new Product("Frutilla", "frutilla fresca y dulce", 120, 10));
		products.add(new Product("Limon", "limón verde y ácido", 100, 10));
		products.add(new Product("Naranja Sanguina", "naranja jugosa y dulce", 120, 10));
		
		// Test insert
		for (Product p : products)
			Database.products.insert(p);
		
		// Test exists
		assert !Database.products.exists(0);
		assert !Database.products.exists(999);
		assert Database.products.exists(1);
		assert Database.products.exists(3);
		assert Database.products.exists(6);
		assert Database.products.exists(8);

		// Test select
		Product selectedProduct = Database.products.select(5);
		assert selectedProduct.getName().equals("Pera");
		assert Database.products.select(0) == null;
		assert Database.products.select(999) == null;

		// Test selectAll
		assert Database.products.selectAll().size() == 15;

		// Test update
		Product newProduct = selectedProduct.newInstance();
		newProduct.setName("name");
		newProduct.setDescription("Description");
		newProduct.setPrice(78);
		newProduct.setIva(23);
		newProduct.setStock(98);
		Database.products.update(newProduct);

		// Update selected client data
		selectedProduct = Database.products.select(selectedProduct.getId());
		
		// Check that all parameters are equal
		assert selectedProduct.getName().equals(newProduct.getName());
		assert selectedProduct.getDescription().equals(newProduct.getDescription());
		assert selectedProduct.getPrice() == newProduct.getPrice();
		assert selectedProduct.getIva() == newProduct.getIva();
		assert selectedProduct.getStock() == newProduct.getStock();

		// Test remove
		Database.products.remove(selectedProduct.getId());
		assert !Database.products.exists(5);
	}

	/**************************/
	/* Testing code for bills */
	/**************************/
	/*
	 * [x] select
	 * [x] selectAll
	 * [x] insert
	 * [x] remove
	 * [x] exists
	 * [x] update
	 */
	public static void testBills() {
		assert Database.bills.selectAll().size() == 0;

		ArrayList<Bill> bills = new ArrayList<Bill>();
		bills.add(new Bill(1, 10050, true));
		bills.add(new Bill(2, 5025, false));
		bills.add(new Bill(3, 20000, true));
		bills.add(new Bill(4, 7500, false));
		bills.add(new Bill(5, 15000, true));
		bills.add(new Bill(6, 25000, false));
		bills.add(new Bill(7, 10000, true));
		bills.add(new Bill(8, 20050, false));
		bills.add(new Bill(9, 30000, true));
		bills.add(new Bill(10, 12000, false));
		bills.add(new Bill(1, 8000, true));
		bills.add(new Bill(2, 25000, false));
		bills.add(new Bill(3, 18000, true));
		bills.add(new Bill(4, 22000, false));
		bills.add(new Bill(5, 10000, true));
		
		// Test insert
		for (Bill p : bills)
			Database.bills.insert(p);
		
		// Test exists
		assert !Database.bills.exists(0);
		assert !Database.bills.exists(999);
		assert Database.bills.exists(1);
		assert Database.bills.exists(3);
		assert Database.bills.exists(6);
		assert Database.bills.exists(8);

		// Test select
		Bill selectedBill = Database.bills.select(9);

		assert Database.bills.select(0) == null;
		assert Database.bills.select(999) == null;

		// Test selectAll
		assert Database.bills.selectAll().size() == bills.size();

		// Test update
		Bill newBill = selectedBill.newInstance();
		newBill.setClientId(78);
		newBill.setDate("fecha");
		newBill.setAmount(7894387);
		newBill.setPaid(false);
		Database.bills.update(newBill);

		// Update selected client data
		selectedBill = Database.bills.select(selectedBill.getId());
		
		// Check that all parameters are equal
		assert selectedBill.getClientId() == newBill.getClientId();
		assert selectedBill.getDate().equals(newBill.getDate());
		assert selectedBill.getAmount() == newBill.getAmount();
		assert selectedBill.isPaid() == newBill.isPaid();

		// Test remove
		Database.bills.remove(selectedBill.getId());
		assert !Database.bills.exists(selectedBill.getId());
	}
	

	/*********************************/
	/* Testing code for soldProducts */
	/*********************************/
	/*
	 * [ ] select
	 * [ ] selectAll
	 * [ ] insert
	 * [ ] remove
	 * [ ] exists
	 * [ ] update
	 */
	public static void testSoldProducts() {
		assert Database.soldProducts.selectAll().size() == 0;

		ArrayList<SoldProduct> soldProducts = new ArrayList<SoldProduct>();
		soldProducts.add( new SoldProduct(1, 1, 10));
		soldProducts.add( new SoldProduct(2, 2, 20));
		soldProducts.add( new SoldProduct(3, 3, 5));
		soldProducts.add( new SoldProduct(4, 4, 15));
		soldProducts.add( new SoldProduct(5, 5, 30));
		soldProducts.add( new SoldProduct(6, 6, 25));
		soldProducts.add( new SoldProduct(7, 7, 10));
		soldProducts.add( new SoldProduct(8, 8, 40));
		soldProducts.add( new SoldProduct(9, 9, 20));
		soldProducts.add( new SoldProduct(10, 10, 15));
		soldProducts.add( new SoldProduct(11, 1, 25));
		soldProducts.add( new SoldProduct(12, 2, 10));
		soldProducts.add( new SoldProduct(13, 3, 30));
		soldProducts.add( new SoldProduct(14, 4, 20));
		soldProducts.add( new SoldProduct(15, 5, 40));
		soldProducts.add( new SoldProduct(1, 6, 15));
		soldProducts.add( new SoldProduct(2, 7, 25));
		soldProducts.add( new SoldProduct(3, 8, 10));
		soldProducts.add( new SoldProduct(4, 9, 30));
		soldProducts.add( new SoldProduct(5, 10, 20));
		soldProducts.add( new SoldProduct(6, 11, 15));
		soldProducts.add( new SoldProduct(7, 12, 25));
		soldProducts.add( new SoldProduct(8, 13, 10));
		soldProducts.add( new SoldProduct(9, 14, 30));
		soldProducts.add( new SoldProduct(10, 15, 20));
		
		// Test insert
		for (SoldProduct p : soldProducts)
			Database.soldProducts.insert(p);
		
		// Test exists
		assert !Database.soldProducts.exists(-1);
		assert !Database.soldProducts.exists(0);
		assert !Database.soldProducts.exists(999);
		assert !Database.soldProducts.exists(-999);
		
		for (int i = 0; i < soldProducts.size(); i++)
			assert Database.soldProducts.exists(i + 1);

		// Test select
		SoldProduct selectedSoldProduct = Database.soldProducts.select(12);

		assert Database.soldProducts.select(-1) == null;
		assert Database.soldProducts.select(0) == null;
		assert Database.soldProducts.select(999) == null;
		assert Database.soldProducts.select(-999) == null;

		// Test selectAll
		assert Database.soldProducts.selectAll().size() == soldProducts.size();

		// Test update
		SoldProduct newSoldProduct = selectedSoldProduct.newInstance();
		newSoldProduct.getBillId(78);
		newSoldProduct.setProductId(89);
		newSoldProduct.setAmount(34);
		Database.soldProducts.update(newSoldProduct);

		// Update selected client data
		selectedSoldProduct = Database.soldProducts.select(selectedSoldProduct.getId());
		
		// Check that all parameters are equal
		assert selectedSoldProduct.getBillId() == newSoldProduct.getBillId();
		assert selectedSoldProduct.getProductId() == newSoldProduct.getProductId();
		assert selectedSoldProduct.getAmount() == newSoldProduct.getAmount();

		// Test remove
		Database.soldProducts.remove(selectedSoldProduct.getId());
		assert !Database.soldProducts.exists(selectedSoldProduct.getId());
	}
}