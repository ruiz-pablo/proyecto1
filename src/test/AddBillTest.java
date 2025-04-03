package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import controller.BillController;
import model.Client;
import model.Product;
import model.database.Database;

@RunWith(Parameterized.class)
public class AddBillTest {
	private Client client;
	private int[][] productData;
	private int expectedBillAmount;
	private int expectedUncovered;

	public AddBillTest(Client client,
			int[][] productData,
			int expectedBillAmount,
			int expectedUncovered
			) {

		this.client = client;
		this.productData = productData;
		this.expectedBillAmount  = expectedBillAmount;
		this.expectedUncovered  = expectedUncovered;
	}

	@Before
	public void setUp() throws Exception {
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
		Database.clients.insert(new Client(1, "Juan Pérez", "A1231231A", "juan@example.com", "Calle 1", 100, true));
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
		return Arrays.asList(new Object[][]{
			{
				1,
				new int[][] {
					{2, 1, 9},
					{6, 5, 4},
					{9, 3, 1},
				},
				999,
				999
			},
			{
				1,
				new int[][] {
					{2, 1, 9},
					{6, 5, 4},
					{9, 3, 1},
				},
				999,
				999
			},
		});
	}

	@Test
	public void testCreateBillFromProductMap() {
		BillController controller = new BillController();
		
		// Create productMap from productData
		HashMap<Integer, Integer> productMap = new HashMap<Integer, Integer>();
		for (int[] row : productData) {
			int productId     = row[0];
			int amount        = row[1];
			int expectedStock = row[2];
			
			productMap.put(Integer.valueOf(productId), Integer.valueOf(amount));
			
		}

		controller.createBillFromProductMap(Database.clients.select(1), null);
	}

}
