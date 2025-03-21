package controller;

import model.Product;
import model.database.Database;
import view.Input;
import view.ProductView;

public class ProductController extends AbstractController {
    private ProductView view;
    
    public ProductController() {
        this.view = new ProductView();
    }

    @Override
    public void list() {
        view.list();
    }

    @Override
    public void create() {
        Product product = view.create(); // Llama al método create() de la vista

        // Insertar el producto en la base de datos
		int id = Database.products.insert(product); // Insertar y obtener el ID generado
		System.out.println("Producto insertado con ID: " + id);
    }

    @Override
    public void remove() {
        int productId = view.remove();
		Database.products.remove(productId);
		
		if (!Input.readYesNo("Seguro que quire eliminar el producto?"))
			System.out.println("Cancelando...");
		else
			System.out.println("Producto eliminado con éxito.");
    }

    @Override
    public void modify() {
        Product updatedProduct = view.modify();

		Database.products.update(updatedProduct);
		if (!Input.readYesNo("Seguro que quire modificar el producto?"))
			System.out.println("Cancelando...");
		else
			System.out.println("Producto actualizado con éxito.");
    }
}