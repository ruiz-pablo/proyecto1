package test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import controller.BillController;
import model.Client;
import model.Bill;
import model.Product;
import model.database.Database;

@RunWith(Parameterized.class)
public class AddBillTest {
	
	private int clientId;
	private int expectedBillAmount;
	private int expectedUncovered;
	private boolean markPaid;

	private int[] productIds;
	private int[] productAmounts;
	private int[] productStocks;

	public AddBillTest(
			int clientId,
			int expectedBillAmount,
			int expectedUncovered,
			boolean markPaid,
			int[] productIds,
			int[] productAmounts,
			int[] productStocks
		) {
		
		this.clientId = clientId;
		this.expectedBillAmount = expectedBillAmount;
		this.expectedUncovered = expectedUncovered;
		this.markPaid = markPaid;
		this.productIds = productIds;
		this.productAmounts = productAmounts;
		this.productStocks = productStocks;
	}

	// This data will be inserted in the database and will be uses in the test below
	@BeforeClass
	public static void setUp() throws Exception {
		System.out.println("Before class");
		// Products
		Database.products.insert(new Product(1, "SmartWatch", "Es un reloj inteligente de alta calidad", 1200, 21, 5));
		Database.products.insert(new Product(2, "Café Espresso", "Es un café de alta calidad y sabor intenso", 500, 10, 10));
		Database.products.insert(new Product(3, "Tableta Gráfica", "Es una herramienta innovadora para artistas", 2000, 21, 0));
		Database.products.insert(new Product(4, "Ropa Deportiva", "Es un conjunto de ropa muy demandado por atletas", 800, 10, 12));
		Database.products.insert(new Product(5, "Diseño de Interiores", "Es un libro de diseño exclusivo y elegante", 1500, 21, 6));
		Database.products.insert(new Product(6, "Bicicleta Eléctrica", "Es un vehículo muy resistente y ecológico", 2500, 10, 9));
		Database.products.insert(new Product(7, "Proyector de Cine", "Es un dispositivo de última generación para cine en casa", 3000, 21, 0));
		Database.products.insert(new Product(8, "Juego de Mesa", "Es un juego muy versátil y divertido para toda la familia", 900, 10, 11));
		Database.products.insert(new Product(9, "Cámara de Fotografía", "Es una cámara de alta tecnología y calidad", 1800, 21, 4));
		Database.products.insert(new Product(10, "Auriculares Inalámbricos", "Es un producto muy asequible y de alta calidad", 600, 10, 0));
		
		// Clients
		Database.clients.insert(new Client(1, "Juan Pérez", "A1231231A", "juan@example.com", "Calle 1", 0, true));
		Database.clients.insert(new Client(2, "María García", "B9876543B", "maria@example.com", "Avenida 2", 200, true));
		Database.clients.insert(new Client(3, "Pedro Martínez", "C4567890C", "pedro@example.com", "Calle 3", 50, true));
		Database.clients.insert(new Client(4, "Ana López", "D2345678D", "ana@example.com", "Plaza 4", 150, true));
		Database.clients.insert(new Client(5, "Carlos Rodríguez", "E9012345E", "carlos@example.com", "Calle 5", 250, true));
		Database.clients.insert(new Client(6, "Sofía González", "F6789012F", "sofia@example.com", "Avenida 6", 300, false));
		Database.clients.insert(new Client(7, "Luis Hernández", "G3456789G", "luis@example.com", "Calle 7", 400, false));
		Database.clients.insert(new Client(8, "Eva Díaz", "H1234567H", "eva@example.com", "Plaza 8", 500, false));
		Database.clients.insert(new Client(9, "Tomás Sánchez", "I8901234I", "tomas@example.com", "Calle 9", 600, false));
		Database.clients.insert(new Client(10, "Lucía Moreno", "J5678901J", "lucia@example.com", "Avenida 10", 700, false));
	}
	
	@Parameters
	public static Collection<Object[]> parameters (){
		return Arrays.asList(new Object[][] {
			// Test 1
			{
				1,             // Client Id
				4464,          // expectedBillAmount
				4464,          // expectedUncovered
				false,         // Mark as paid
				new int[] {1}, // Products IDs
				new int[] {3}, // Products amount
				new int[] {2}, // Products expected stock
			},

			// Test 2
			{
				1,
				21386,
				4464,
				true,
				new int[] {2, 6, 9},
				new int[] {1, 5, 3},
				new int[] {9, 4, 1},
			},

			// Test 3
			{
				6,
				16775,
				300,
				true,
				new int[] {4, 5, 8},
				new int[] {5, 3, 7},
				new int[] {7, 3, 4},
			},

			// Test 4
			{
				6,
				9592,
				9892,
				false,
				new int[] {1, 2, 8, 9},
				new int[] {2, 1, 4, 1},
				new int[] {0, 8, 0, 0},
			},
		});
	}

	@Test
	public void testCreateBillFromProductMap() {
		// Create HashMap of productId - Amount
		HashMap<Integer, Integer> productMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < productIds.length; i++) {
			productMap.put(productIds[i], productAmounts[i]);
		}
		
		// Get client from database
		Client client = Database.clients.select(clientId);
		
		// Create bill
		BillController controller = new BillController();
		int billId = controller.createBillFromProductMap(client, productMap, markPaid);
		
		// Update client's data (uncovered value should have changed)
		client = Database.clients.select(clientId);
		
		// Make sure bill exists
		Assert.assertTrue(Database.bills.exists(billId));
		Bill bill = Database.bills.select(billId);
		
		// Make sure bill data is correct
		Assert.assertEquals(clientId, bill.getClientId());
		Assert.assertEquals(expectedBillAmount, bill.getAmount());
		Assert.assertEquals(markPaid, bill.isPaid());
		
		// Make sure uncovered is updated correctly
		Assert.assertEquals(expectedUncovered, client.getUncovered());
		
		// Make sure stock is updated
		for (int i = 0; i < productIds.length; i++) {
			Assert.assertEquals(productStocks[i], Database.products.select(productIds[i]).getStock());
		}
	}
}