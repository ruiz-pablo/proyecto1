package test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.BillController;
import model.Client;
import model.Product;
import model.database.Database;

import java.util.HashMap;

public class AddBillTestException {

	private static BillController controller;

	@BeforeClass
	public static void setUp() throws Exception {
		controller = new BillController();

		Database.products.insert(new Product(101, "Monitor 4K", "Monitor de alta resolución para gaming", 3500, 21, 8));
		Database.products.insert(new Product(102, "Teclado Mecánico", "Teclado gaming con switches Cherry MX", 850, 10, 15));
		Database.products.insert(new Product(103, "Ratón Inalámbrico", "Ratón ergonómico de alta precisión", 450, 21, 0));
		Database.products.insert(new Product(104, "Disco SSD", "Disco de estado sólido de 1TB", 1200, 10, 5));
		Database.products.insert(new Product(105, "Auriculares Gaming", "Auriculares con cancelación de ruido", 950, 21, 3));

		Database.clients.insert(new Client(201, "Elena Martínez", "X1234567X", "elena@test.com", "Calle Mayor 15", 0, true));
		Database.clients.insert(new Client(202, "Roberto Sánchez", "Y7654321Y", "roberto@test.com", "Avenida Principal 23", 300, true));
		Database.clients.insert(new Client(203, "Carmen López", "Z9876543Z", "carmen@test.com", "Plaza Central 8", 1500, false));
	}

	// Test dando un valor null al cliente.
	@Test
	public void testCreateBillWithNullClient() {
		HashMap<Integer, Integer> productMap = new HashMap<>();
		productMap.put(101, 2);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(null, productMap, true);
		});

		assertEquals("Null parameter not valid", exception.getMessage());
	}

	// Test dando un valor null al producto.
	@Test
	public void testCreateBillWithNullProductMap() {
		Client client = Database.clients.select(201);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(client, null, true);
		});

		assertEquals("Null parameter not valid", exception.getMessage());
	}

	// Test no hay productos seleccionados.
	@Test
	public void testCreateBillWithEmptyProductMap() {
		Client client = Database.clients.select(201);

		HashMap<Integer, Integer> productMap = new HashMap<>();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(client, productMap, true);
		});

		assertEquals("No products where provided", exception.getMessage());
	}

	// Test ID no existe.
	@Test
	public void testCreateBillWithNonExistentProduct() {
		Client client = Database.clients.select(201);

		HashMap<Integer, Integer> productMap = new HashMap<>();
		productMap.put(999, 1);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(client, productMap, true);
		});

		assertEquals("Product not found", exception.getMessage());
	}

	// Test cantidad negativa de stock.
	@Test
	public void testCreateBillWithNegativeProductAmount() {
		Client client = Database.clients.select(201);

		HashMap<Integer, Integer> productMap = new HashMap<>();
		productMap.put(101, -2);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(client, productMap, true);
		});

		assertEquals("Product amount not valid", exception.getMessage());
	}

	// Test cantidad de 0 en el stock.
	@Test
	public void testCreateBillWithZeroStock() {
		Client client = Database.clients.select(201);

		HashMap<Integer, Integer> productMap = new HashMap<>();
		productMap.put(101, 0);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(client, productMap, true);
		});

		assertEquals("Product amount not valid", exception.getMessage());
	}

	// Test cantidad insuficiente del stock
	@Test
	public void testCreateBillWithInsufficientStock() {
		Client client = Database.clients.select(201);

		HashMap<Integer, Integer> productMap = new HashMap<>();
		productMap.put(101, 10);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(client, productMap, true);
		});

		assertEquals("Not enough stock available", exception.getMessage());
	}

	// Test client no exite
	@Test
	public void testCreateBillWithNonExistentClient() {
		Client client = new Client(999, "Cliente Inexistente", "Z9999999Z", "noexiste@test.com", "Calle Falsa 123", 0,
				true);

		HashMap<Integer, Integer> productMap = new HashMap<>();
		productMap.put(102, 1);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			controller.createBillFromProductMap(client, productMap, true);
		});

		assertEquals("Client not found", exception.getMessage());
	}
}