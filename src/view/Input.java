package view;

import java.util.Scanner;

public class Input {
	private static Scanner stdin;

	public static void init() {
		if (stdin != null)
			close();

		stdin = new Scanner(System.in);
	}
	
	public static void close() {
		stdin.close();
	}

	public static String readLine() {
		return stdin.nextLine();
	}
}
