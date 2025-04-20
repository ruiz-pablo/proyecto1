package view;

public class Output {
	private static String formatCell(String data, int width) {
		if (data.length() > width)
			data = data.substring(0, width);

		int before = (int) Math.round((width - data.length()) / 2.0);
		int after = (int) Math.floor((width - data.length()) / 2.0);

		String toPrint = "";
		for (int i = 0; i < before; i++)
			toPrint += " ";

		toPrint += data;

		for (int i = 0; i < after; i++)
			toPrint += " ";

		return toPrint;
	}
	
	private static void printLine(int[] widths) {
		System.out.print("+");
		for (int col = 0; col < widths.length; col++) {
			System.out.print("-".repeat(widths[col] + 2));
			System.out.print("+");
		}
		System.out.println();
	}
	
	public static void printTable(String[] header, String[][] data) {
		// Make sure inputs are valid
		if (header == null || data == null)
			throw new IllegalArgumentException("'header' or 'data' are null");
		if (header.length == 0 || data.length == 0)
			return;
		if (header.length != data[0].length)
			throw new IllegalArgumentException("The number of columns in 'header' is not the same as in 'data'");

		int nrows = data.length;       // Number or rows
		int ncols = data[0].length;    // Number of columns
		int[] widths = new int[ncols]; // Max width of each column
		
		// Calculate max width of each column
		for (int col = 0; col < ncols; col++) {
			int max = header[col].length();
			for (int row = 0; row < nrows; row++) {
				int width = data[row][col].length();
				if (width > max)
					max = width;
			}
			
			widths[col] = max;
		}

		// Print header cells
		printLine(widths);

		System.out.print("| ");
		for (int col = 0; col < ncols; col++) {
			System.out.print(formatCell(header[col], widths[col]));
			System.out.print(" | ");
		}
		System.out.println();

		printLine(widths);

		// Print data
		for (String[] row : data) {
			String s = "| ";
			for (int col = 0; col < ncols; col++)
				s += formatCell(row[col], widths[col]) + " | ";
			System.out.println(s);
		}
		
		// Footer
		printLine(widths);
	}
}
