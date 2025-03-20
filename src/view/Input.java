package view;

import java.util.Scanner;

public class Input {
	private static Scanner stdin = new Scanner(System.in);

	public static void close() {
		stdin.close();
	}

	public static String readLine() {
		return stdin.nextLine();
	}
	
	// TODO: Properly validate first and last letter
	private static boolean isCifValid(String cif) {
		final String validLetters = "ABCDEFGHJNPQS";

		if (cif.length() != 9)
			return false;
		
		// Check first letter
		String firstLetter = cif.substring(0, 1);
		if (!validLetters.contains(firstLetter))
			return false;

		// Check last letter
		String lastLetter = cif.substring(8, 9);
		if (!validLetters.contains(lastLetter))
			return false;
		
		// Check number part
		Scanner scanner = new Scanner(cif.substring(1, 8));
		if (!scanner.hasNextInt()) {
			scanner.close();
			return false;
		}
		
		int cifNumber = scanner.nextInt();
		scanner.close();

		if (cifNumber < 0)
			return false;
		
		return true;
	}

	public static String readCif(String msg) {
		System.out.print(msg);
		String cif = Input.readLine();

		while (!isCifValid(cif)) {
			System.out.println("Introduzca un CIF valido");
			System.out.print(msg);
			cif = Input.readLine();
		}

		return cif;
	}
	
	public static boolean readYesNo(String msg) {
		System.out.print(msg);
		String line = Input.readLine();
		
		while (!line.equals("y") && !line.equals("n")) {
			System.out.println("Opcion no reconocida");
			System.out.print(msg);
			line = Input.readLine();
		}
		
		return line.equals("y");
	}

	public static int readId(String msg) {
		int id = 0;
		boolean isValid = false;

		while (!isValid) {
			try {
				System.out.print(msg);
				id = Integer.parseInt(Input.readLine());

				isValid = true;
			}
			catch (NumberFormatException e) {
				System.out.println("La id introducida no es valida");
			}
		}
		
		return id;
	}
	
	public static int readAmount(String msg) {
		int amount = 0;
		boolean isValid = false;

		while (!isValid) {
			try {
				System.out.print(msg);
				amount = Integer.parseInt(Input.readLine());
				if (amount < 0)
					throw new NumberFormatException("Amount is lower than 0");

				isValid = true;
			}
			catch (NumberFormatException e) {
				System.out.println("La cantidad introducida no es valida");
			}
		}

		return amount;
	}
}
