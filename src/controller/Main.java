package controller;

import database.Database;
import database.DatabaseExamples;
import database.DatabaseTesting;
import model.Bill;
import model.Client;
import model.Product;

public class Main {
	public static void main(String[] args) {
		// Ejercutar test sobre la base de datos
		// DatabaseTesting.test();

		// Ejecutar ejemplos de como usar la base de datos
		DatabaseExamples.runAll();
	}
}