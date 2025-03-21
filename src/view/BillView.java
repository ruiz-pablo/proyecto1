package view;

import java.util.HashMap;

import controller.ProductController;

import java.util.ArrayList;

import model.Bill;
import model.Client;
import model.Product;
import model.SoldProduct;
import model.database.Database;

public class BillView {
	public int list() {
		// Read client CIF
		String clientCif = Input.readCif("Introduzca el CIF de un cliente: ");
		while (Database.clients.selectByCif(clientCif) == null) {
			clientCif = Input.readCif("Introduzca el CIF de un cliente: ");
			System.out.println("No existe ningun cliente con el CIF introducido");
		}

		// Print table of bills
		printBills(Database.clients.selectByCif(clientCif));

		// Read bill id
		int billId = Input.readId("Introduzca el código de la factura que desea imprimir: ");
		while (!Database.bills.exists(billId)) {
			System.out.println("No existe ninguna factura con el codigo introducido");
			billId = Input.readId("Introduzca el código de la factura que desea imprimir: ");
		}

		return billId;
	}

	public Object[] create() {
		// Print clients
		new ClientView().list();

		// Read CIF
		String cif = Input.readCif("Introduzca el CIF de un cliente: ");
		Client client = Database.clients.selectByCif(cif);
		
		// There is no client with that CIF
		if (client == null)
			throw new IllegalArgumentException("No se ha encontrado ningún cliente con el CIF introducido");
		
		// Create map of products-amounts from user input
		HashMap<Integer, Integer> productMap = new HashMap<Integer, Integer>();

		boolean continueAdding = true;
		do
		{
			// Display products
			new ProductController().list();
			
			int productId = Input.readId("Introduzca el código de un producto: ");
			Product product = Database.products.select(productId);
			
			if (product == null) {
				System.out.println("No existe ningún producto con el código introducido");
			}
			else {
				// Read amount
				System.out.println("Stock disponible: " + product.getStock());
				int productAmount = Input.readAmount("Introduzca la cantidad del producto seleccionado que desea: ");
				
				// Verify that the amount selected is not bigger than the current stock
				if (productAmount <= product.getStock())
					addProductToList(productMap, productId, productAmount);
				else
					System.out.println("No se puede añadir el producto. La cantidad del producto introducida supera la cantidad en stock");
			}
			
			continueAdding = Input.readYesNo("¿Desea añadir otro producto? (s/n): ");
		} while (continueAdding);
		

		// Only create bill if there are products
		if (productMap.size() == 0)
			throw new IllegalArgumentException("No se ha seleccionado ningún producto");
		
		// Ask where to print the bill or not
		boolean printBill = Input.readYesNo("¿Desea imprimir la factura? (s/n): ");

		// Return all values needed to create the bill to the controller
		return new Object[] {client, productMap, printBill};
	}

	public int modify() {
		String clientCif = Input.readCif("Introduzca el CIF de un cliente: ");

		while (Database.clients.selectByCif(clientCif) == null) {
			clientCif = Input.readCif("Introduzca el CIF de un cliente: ");
			System.out.println("No existe ningun cliente con el CIF introducido");
		}

		// Print table of bills
		printBills(Database.clients.selectByCif(clientCif));

		// Read bill id and print details
		int billId = Input.readId("Introduzca el código de la factura que desea marcar como pagada: ");
		while (!Database.bills.exists(billId)) {
			System.out.println("No existe ninguna factura con el código introducido");
			billId = Input.readId("Introduzca el código de la factura que desea marcar como pagada: ");
		}
		
		return billId;
	}

	/******************/
	/* Helper Methods */
	/******************/
	
	private void printBills(Client client) {
		String[] columns = new String[] {"Código", "Cliente", "Fecha", "Total", "Pagada"};
		ArrayList<String[]> data = new ArrayList<String[]>();

		for (Bill bill : Database.bills.selectByClientId(client.getId())) {
			String[] row = new String[columns.length];
			row[0] = String.valueOf(bill.getId());
			row[1] = client == null ? "None" : client.getName();
			row[2] = String.valueOf(bill.getDate());
			row[3] = String.format("%.2f€", bill.getAmount() / 100.0);
			row[4] = String.valueOf(bill.isPaid() ? "Sí" : "No");
			data.add(row);
		}
		
		Output.printTable(columns, data.toArray(new String[0][]));
	}

	private void addProductToList(HashMap<Integer, Integer> productsMap, int productId, int amount) {
		// Calculate new stock value
		Product product = Database.products.select(productId);
		int newStock = product.getStock() - amount;
		if (newStock < 0)
			throw new IllegalStateException("Tried to decrease stock unted 0 for product id " + productId);
		
		// Update database
		product.setStock(newStock);
		Database.products.update(product);

		// Create product if it is not in the map
		if (productsMap.get(Integer.valueOf(productId)) == null)
			productsMap.put(Integer.valueOf(productId), Integer.valueOf(amount));
		
		// Increase amount if it is
		else
			productsMap.put(Integer.valueOf(productId), Integer.valueOf(amount) + productsMap.get(Integer.valueOf(productId)));
	}
	
	public void printBillDetails(int billId) {
		Bill bill = Database.bills.select(billId);
		Client client = Database.clients.select(bill.getClientId());
		ArrayList<SoldProduct> soldProducts = (ArrayList<SoldProduct>) Database.soldProducts.selectByBillId(billId);
		
		// Print client's details
		System.out.println("===========");
		System.out.println("  Cliente  ");
		System.out.println("===========");
		System.out.println("Código cliente: " + client.getId());
		System.out.println("Nombre: " + client.getName());
		System.out.println("Email: " + client.getEmail());
		System.out.println("CIF: " + client.getCif());
		System.out.println("Recago equivalencia: " + (client.getRe() ? "Sí" : "No"));
		System.out.println();
		
		// Print bill's details
		System.out.println("===========");
		System.out.println("  Factura  ");
		System.out.println("===========");
		System.out.println("Código factura: " + bill.getId());
		System.out.println(String.format("Total %.2f€", bill.getAmount() / 100.0));
		System.out.println("Pagada: " + (bill.isPaid() ? "Sí" : "No"));
		System.out.println();

		// Print table of products
		String[] columns = new String[] {"Código", "Nombre", "Precio", "Cantidad"};
		ArrayList<String[]> data = new ArrayList<String[]>();

		for (SoldProduct soldProduct : soldProducts) {
			ArrayList<String> row = new ArrayList<String>();
			Product product = Database.products.select(soldProduct.getProductId());

			row.add(String.valueOf(soldProduct.getId()));
			row.add(String.valueOf(product.getName()));
			row.add(String.format("%.2f€", product.getPrice() / 100.0));
			row.add(String.valueOf(soldProduct.getAmount()));

			data.add(row.toArray(new String[0]));
		}
		
		System.out.println("=============");
		System.out.println("  Productos  ");
		System.out.println("=============");
		Output.printTable(columns, data.toArray(new String[0][]));
	}
}