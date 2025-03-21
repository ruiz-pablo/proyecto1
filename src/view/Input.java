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

    public static int readOption(int min, int max) {
        int selectedOptionNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                String selectedOption = Input.readLine();
                selectedOptionNumber = Integer.parseInt(selectedOption);
                if (selectedOptionNumber >= min && selectedOptionNumber <= max) {
                    validInput = true;
                } else {
                    System.out.println("El número debe estar entre " + min + " y " + max + ". Intenta de nuevo:");
                }
            } catch (NumberFormatException e) {
                System.out.print("Ingresa solo números válidos. Intentalo de nuevo: ");
            }
        }
        return selectedOptionNumber;
    }
    
	public static String readName(String msg) {
		System.out.print(msg);
		String name = readLine();

		while (name.length() == 0) {
			System.out.println("El nombre no puede estar vacio");
			System.out.print(msg);
			name = readLine();
		}

		return name;
	}

	public static String readDescription(String msg) {
		System.out.print(msg);
		String description = readLine();

		while (description.length() == 0) {
			System.out.println("La descripcion no puede estar vacia");
			System.out.print(msg);
			description = readLine();
		}

		return description;
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
		
		while (!line.equals("s") && !line.equals("n")) {
			System.out.println("Opcion no reconocida");
			System.out.print(msg);
			line = Input.readLine();
		}
		
		return line.equals("s");
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
	
	public static int readPrice(String msg) {
		int price = 0;
		boolean isValid = false;

		while (!isValid) {
			System.out.print(msg);
			Scanner scanner = new Scanner(readLine());
			
			if (scanner.hasNextFloat()) {
				price = (int) Math.round(scanner.nextFloat() * 100.0); // Convert to cents
				if (price >= 0)
					isValid = true;
			}
			
			if (!isValid)
				System.out.println("El precio introducido no es valido");

			scanner.close();
		}
		
		return price;
	}
	
	public static int readPercentage(String msg) {
		int percentage = 0;
		boolean isValid = false;

		while (!isValid) {
			System.out.print(msg);
			Scanner scanner = new Scanner(readLine());
			
			if (scanner.hasNextInt()) {
				percentage = scanner.nextInt();
				if (percentage >= 0 && percentage <= 100)
					isValid = true;
			}
			
			if (!isValid)
				System.out.println("El porcentaje introducido no es valido");

			scanner.close();
		}
		
		return percentage;
	}
	
	public static int readStock(String msg) {
		int stock = 0;
		boolean isValid = false;

		while (!isValid) {
			try {
				System.out.print(msg);
				stock = Integer.parseInt(Input.readLine());
				if (stock < 0)
					throw new NumberFormatException("Stock is lower than 0");

				isValid = true;
			}
			catch (NumberFormatException e) {
				System.out.println("La cantidad de stock introducida no es valida");
			}
		}

		return stock;
	}
}
