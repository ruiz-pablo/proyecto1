package controller;

import view.MenuView;

public class MenuController {

    private MenuView menuView;
    private ClientController clientController;
    private ProductController productController;
    private BillController billController;

    public MenuController() {
        this.menuView = new MenuView();
        this.clientController = new ClientController();
        this.productController = new ProductController();
        this.billController = new BillController();
    }

    /**
     * Ejecuta el controlador principal del menú
     */
    public void execute() {
		boolean exec = true;

        while (exec) {
            int mainOption = menuView.startMenu();
            switch (mainOption) {
                case 1:
                    System.out.println("-- Has seleccionado la gestión de clientes. --");
                    clientMenu();
                    break;
                case 2:
                    System.out.println("-- Has seleccionado la gestión de productos. --");
                    productMenu();
                    break;
                case 3:
                    System.out.println("-- Has seleccionado la gestión de facturas. --");
                    billMenu();
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    exec = false;
            }
        }
    }

    // Método para el menú de clientes
    private void clientMenu() {
        int option = menuView.startMenuClientes();

        switch (option) {
            case 1:
                System.out.println("-- Has seleccionado listar clientes. --");
                clientController.list();
                break;
            case 2:
                System.out.println("-- Has seleccionado crear cliente. --");
                clientController.create();
                break;
            case 3:
                System.out.println("-- Has seleccionado modificar cliente. --");
                clientController.modify();
                break;
            case 4:
                System.out.println("-- Has seleccionado eliminar cliente. --");
                clientController.remove();
                break;
        }
    }

    //Método para el menú de productos
    private void productMenu() {
        int option = menuView.startMenuProductos();

        switch (option) {
            case 1:
                System.out.println("-- Has seleccionado listar productos. --");
                productController.list();
                break;
            case 2:
                System.out.println("-- Has seleccionado crear producto. --");
                productController.create();
                break;
            case 3:
                System.out.println("-- Has seleccionado modificar producto. --");
                productController.modify();
                break;
            case 4:
                System.out.println("-- Has seleccionado eliminar producto. --");
                productController.remove();
                break;
        }
    }
    
    // Método para el menú de facturas.
    private void billMenu() {
        int option = menuView.startMenuFacturas();

        switch (option) {
            case 1:
                System.out.println("-- Has seleccionado listar facturas. --");
                billController.list();
                break;
            case 2:
                System.out.println("-- Has seleccionado emitir factura. --");
                billController.create();
                break;
            case 3:
                System.out.println("-- Has seleccionado marcar factura como pagada. --");
                billController.modify();
                break;
        }
    }
}