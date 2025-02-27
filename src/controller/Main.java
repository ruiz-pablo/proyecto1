package controller;

import database.DatabaseExamples;
import database.DatabaseTesting;

public class Main {
	public static void main(String[] args) {
		// Ejercutar tests sobre la base de datos
		// DatabaseTesting.test();

		// Ejecutar ejemplos de como usar la base de datos
		DatabaseExamples.runAll();
		
		// Ejecutar el menu principal
		new MenuController().execute();
	}
}