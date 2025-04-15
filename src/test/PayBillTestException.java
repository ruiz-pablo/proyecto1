package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PayBillTestException {

	@Before
	public void setUp() throws Exception {
		// Añadir aqui los clientes para comprobar que no se puede añadir un cliente repetido
		// Database.clients.insertClient(new Client("nombre", "apellidos"...));
	}

	@Test
	public void testAddClient() {
		fail("Not yet implemented");
	}
}
