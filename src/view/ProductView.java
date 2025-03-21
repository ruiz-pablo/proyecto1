package view;

import model.Product;

import model.database.Database;

import java.util.Collection;

public class ProductView extends AbstractView<Product> {

    @Override
    public void list() {
    	Collection<Product> products = Database.products.selectAll();
        if (products.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
            return;
        }

        System.out.println("Listado de productos:");
        System.out.println("---------------------");
        for (Product product : products) {
            System.out.println("ID: "          + product.getId());
            System.out.println("Nombre: "      + product.getName());
            System.out.println("Descripción: " + product.getDescription());
            System.out.println(String.format("Precio: %.2f Euros", product.getPrice() / 100.0));
            System.out.println("IVA: "         + product.getIva() + "%");
            System.out.println("Stock: "       + product.getStock());
            System.out.println("---------------------");
        }
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
    	int productId = Input.readId("Ingrese el ID del producto a eliminar: ");
    	while (!Database.products.exists(productId)) {
    		System.out.println("No se ha encontrado el producto en la base de datos");
			productId = Input.readId("Ingrese la ID del producto a eliminar: ");
    	}
    	
    	return productId;
    }

    @Override
    public Product modify() {
        // Capturar el ID del producto a modificar
        int id = Input.readId("Ingrese el ID del producto a modificar: ");

        // Verificar si el producto existe en la base de datos
        while (!Database.products.exists(id)) {
            System.out.println("Error: No existe un producto con ID " + id);
			id = Input.readId("Ingrese el ID del producto a modificar: ");
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