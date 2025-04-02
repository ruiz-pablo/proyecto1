package controller;

import java.util.ArrayList;
import java.util.HashMap;

import model.Bill;
import model.Client;
import model.Product;
import model.SoldProduct;
import model.database.Database;
import view.BillView;
import view.Input;

public class BillController extends AbstractController {
	private static final int RE_PERCENTAGE = 3;

	private BillView view;

	public BillController() {
		this.view = new BillView();
	}

	@Override
	public void list() {
		// Read bill id to print from user
		view.list();
	}

	@Override
	public void create() {
		try {
			// Get bill's values from user
			Object[] values =  view.create();

			// Cast them to proper values
			Client client = (Client) values[0];
			@SuppressWarnings("unchecked")
			HashMap<Integer, Integer> productMap = (HashMap<Integer, Integer>) values[1];
			boolean printBill = (boolean) values[2];
			
			// Create bill
			int billId = createBillFromProductMap(client, productMap);
			
			// Print bill
			if (printBill)
				view.printBillDetails(billId);

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void remove() {
		// Do not implement, it is not going to be used
	}

	@Override
	public void modify() {
		// Read bill id from user
		int billId = view.modify();
		Bill bill = Database.bills.select(billId);

		// User confirmation
		if (!Input.readYesNo("¿Marca factura como pagada? (s/n): ")) {
			System.out.println("Cancelando...");
			return;
		}

		// Mark as paid
		try {
			markBillAsPaid(bill.getId());
			System.out.println("Se ha marcado la factura como pagada");
		} catch (IllegalArgumentException e){
			System.out.println("No se pudo marcar la factura como pagada");
			System.out.println(e.getMessage());
		}
	}

	/******************/
	/* Helper Methods */
	/******************/

	public int createBillFromProductMap(Client client, HashMap<Integer, Integer> productMap) {
		// Test clients exits
		if (client == null || productMap == null)
			throw new IllegalArgumentException("Null parameter not valid");
		if (!Database.clients.exists(client.getId()))
			throw new IllegalArgumentException("Client not found");
		
		// Test products and amounts
		for (Integer productId : productMap.keySet()) {
			if (!Database.products.exists(productId))
				throw new IllegalArgumentException("Product not found");
			if (productMap.get(productId) <= 0)
				throw new IllegalArgumentException("Product amount not valid");
			if (productMap.get(productId) > Database.products.select(productId).getStock())
				throw new IllegalArgumentException("Not enough stock available");
		}

		// Create bill
		Bill bill = new Bill(
				client.getId(), // Id of the client
				0,              // Set amount temporally to 0, it will be updated later
				false           // Set paid to false by default
				);
		
		int billId = Database.bills.insert(bill); // Insert bill into table to get Id
		bill = Database.bills.select(billId);     // Update bill with new information

		// Convert map with product-amount values to SoldProduct objects
		ArrayList<SoldProduct> soldProducts = new ArrayList<SoldProduct>();
		for (Integer key : productMap.keySet()) {
			SoldProduct soldProduct = new SoldProduct(
					billId,
					key,
					productMap.get(key)
					);
			soldProducts.add(soldProduct);
		}

		// Insert SoldProduct objects into database
		for (SoldProduct p : soldProducts)
			Database.soldProducts.insert(p);
		
		// Decrease stock
		for (SoldProduct p : soldProducts) {
			Product product = Database.products.select(p.getProductId());
			product.setStock(product.getStock() - p.getAmount());
			Database.products.update(product);
		}
		
		// Update total amount of the bill
		int amount = calculateBillAmount(billId);
		bill.setAmount(amount);
		Database.bills.update(bill);
		
		// Increase client's uncovered
		increaseUncovered(client.getId(), billId);

		return bill.getId();
	}

	private int calculateBillAmount(int billId) {
		Bill bill = Database.bills.select(billId);
		
		int importe = 0;
		int iva = 0;
		int re_amount = 0;

		// Add up importe and IVA
		for (SoldProduct soldProduct : (ArrayList<SoldProduct>) Database.soldProducts.selectByBillId(billId)) {
			Product product = Database.products.select(soldProduct.getProductId());
			
			importe += product.getPrice() * soldProduct.getAmount();
			iva += (int) Math.round((importe * product.getIva()) / 100.0);
		}
		
		// Calculate up RE quote
		if (Database.clients.select(bill.getClientId()).getRe())
			re_amount = (int) Math.round((importe * RE_PERCENTAGE) / 100.0);
		
		return importe + iva + re_amount;
	}

	public void markBillAsPaid(int billId) {
		// Mark bill as paid
		Bill bill = Database.bills.select(billId);
		
		if (bill == null)
			throw new IllegalArgumentException("No existe la factura introducida");

		bill.setPaid(true);
		Database.bills.update(bill);

		// Decrease client's uncovered
		decreaseUncovered(bill.getClientId(), bill.getAmount());
	}

	private void increaseUncovered(int clientId, int amount) {
		Client client = Database.clients.select(clientId);
		int newUncovered = client.getUncovered();
		newUncovered += amount;
		client.setUncovered(newUncovered);
	}

	private void decreaseUncovered(int clientId, int amount) {
		Client client = Database.clients.select(clientId);
		int newUncovered = client.getUncovered();
		newUncovered -= amount;
		client.setUncovered(newUncovered);
	}
}