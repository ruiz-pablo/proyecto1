package view;

import model.Product;

import model.database.Database;

import java.util.ArrayList;
import java.util.Collection;

public class ProductView extends AbstractView<Product> {

    @Override
    public void list() {
    	Collection<Product> products = Database.products.selectAll();
        if (products.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
            return;
        }
        
		// Print table of products
		String[] columns = new String[] {"Código", "Nombre", "Descripcion", "Precio", "IVA", "Stock"};
		ArrayList<String[]> data = new ArrayList<String[]>();

		for (Product product : products) {
			ArrayList<String> row = new ArrayList<String>();

			row.add(String.valueOf(product.getId()));
			row.add(product.getName());
			row.add(product.getDescription());
			row.add(String.format("%.2f€", product.getPrice() / 100.0));
			row.add(String.format("%d%%", product.getIva()));
			row.add(String.valueOf(product.getStock()));

			data.add(row.toArray(new String[0]));
		}
		
		Output.printTable(columns, data.toArray(new String[0][]));
    }
    
    @Override
    public Product create() {
        // Capturar los datos del producto desde la entrada del usuario
        String name        = Input.readName("Ingrese el nombre del producto: ");
        String description = Input.readDescription("Ingrese la descripción del producto: ");
        int price          = Input.readPrice("Ingrese el precio del producto: ");
        int iva            = Input.readPercentage("Ingrese el IVA: ");

        // Crear un objeto Product con los datos capturados
        Product newProduct = new Product(name, description, price, iva);
		return newProduct;
    }

    @Override
    public int remove() {
    	// Print all products
    	list();

    	// Remove product
		int productId = Input.readId("Ingrese el código del producto a eliminar: ");
    	while (!Database.products.exists(productId)) {
    		System.out.println("No se ha encontrado el producto en la base de datos");
			productId = Input.readId("Ingrese el código del producto a eliminar: ");
    	}
    	
    	// Return id of product to be remove
    	return productId;
    }

    @Override
    public Product modify() {
    	// Print all products
    	list();

        // Capturar el ID del producto a modificar
        int id = Input.readId("Ingrese el código del producto a modificar: ");

        // Verificar si el producto existe en la base de datos
        while (!Database.products.exists(id)) {
            System.out.println("Error: No existe un producto con código " + id);
			id = Input.readId("Ingrese el código del producto a modificar: ");
        }

        // Capturar los nuevos datos del producto
        String name        = Input.readName("Ingrese el nuevo nombre del producto: ");
        String description = Input.readDescription("Ingrese la nueva descripción: ");
        int price          = Input.readPrice("Ingrese el nuevo precio del producto: ");
        int iva            = Input.readPercentage("Ingrese el IVA: ");
        int stock          = Input.readStock("Ingrese el nuevo stock: ");

        // Crear un objeto Product con los nuevos datos
        Product updatedProduct = new Product(id, name, description, price, iva, stock);
        return updatedProduct;
    }
}