package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import model.Client;

@RunWith(Parameterized.class)
public class AddClientTestException {

    private String nombre;
    private String nif;
    private String email;
    private String direccion;
    private int saldo;
    private boolean recurrente;
    private String mensajeEsperado;

    public AddClientTestException(String nombre, String nif, String email, String direccion, int saldo, boolean recurrente, String mensajeEsperado) {
        this.nombre = nombre;
        this.nif = nif;
        this.email = email;
        this.direccion = direccion;
        this.saldo = saldo;
        this.recurrente = recurrente;
        this.mensajeEsperado = mensajeEsperado;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> datos() {
        return Arrays.asList(new Object[][] {
            {"Lucía", "12345678A", "lucia@gmail.com", "Calle A", 1000, true, null},
            {"Miguel", "87654321B", "miguel@gmail.com", "Calle B", 500, false, null},
            {"Laura", "89454382B", "laura@gmail.com", "Calle C", 0, true, null},
            {"Pedro", "71354352L", "pedro@gmail.com", "Calle D", -200, false, "Saldo no puede ser negativo"},
            {"Ana", "3221422Y", "", "Calle E", 100, true, "Email obligatorio"}
        });
    }

    @Test
    public void testCliente() {
        if (mensajeEsperado == null) {
            // Caso válido, no debe lanzar excepción
            try {
                Client cliente = new Client(0, nombre, nif, email, direccion, saldo, recurrente);
                assertNotNull(cliente);
            } catch (Exception e) {
                fail("No se esperaba excepción, pero se lanzó: " + e.getMessage());
            }
        } else {
            // Caso inválido, debe lanzar IllegalArgumentException con mensaje esperado
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Client(0, nombre, nif, email, direccion, saldo, recurrente);
            });

            assertEquals(mensajeEsperado, exception.getMessage());
        }
    }
}