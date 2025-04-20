package test;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import model.Client;
import model.database.Database;

@RunWith(Parameterized.class)
public class AddClientTest {

    private String nombre;
    private String nif;
    private String email;
    private String direccion;
    private int saldo;
    private boolean recurrente;

    private int idCliente;

    public AddClientTest(String nombre, String nif, String email, String direccion, int saldo, boolean recurrente) {
        this.nombre = nombre;
        this.nif = nif;
        this.email = email;
        this.direccion = direccion;
        this.saldo = saldo;
        this.recurrente = recurrente;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> datos() {
        return Arrays.asList(new Object[][] {
            {"Lucía", "12345678A", "lucia@correo.com", "Calle A", 1000, true},       // Caso típico, cliente recurrente
            {"Miguel", "87654321B", "miguel@correo.com", "Calle B", 500, false},     // Caso típico, cliente no recurrente
            {"Laura", "00000000Z", "laura@correo.com", "Calle C", 0, true},          // Saldo cero
            {"Pedro", "11111111X", "pedro@correo.com", "Calle D", -200, false},      // Saldo negativo (caso límite)
            {"Ana", "22222222Y", "", "Calle E", 100, true}                           // Email vacío (caso límite)
        });
    }

    @Test
    public void testAddClient() {
    	testInsert();
    	testSelect();
    	testDelete();
    }
    
    public void testInsert() {
        Client cliente = new Client(0, nombre, nif, email, direccion, saldo, recurrente);
        idCliente = Database.clients.insert(cliente);
    }

    public void testSelect() {
        assertTrue("El cliente debería existir después de insertarlo", Database.clients.exists(idCliente));

        Client c = Database.clients.select(idCliente);
        assertEquals(nombre, c.getName());
        assertEquals(nif, c.getCif());
        assertEquals(email, c.getEmail());
        assertEquals(direccion, c.getAddress());
        assertEquals(saldo, c.getUncovered());
        assertEquals(recurrente, c.getRe());
    }

    public void testDelete() {
        assertTrue("El cliente debería existir antes de borrarlo", Database.clients.exists(idCliente));
        Database.clients.remove(idCliente);
        assertFalse("El cliente no debería existir después de borrarlo", Database.clients.exists(idCliente));
    }
}