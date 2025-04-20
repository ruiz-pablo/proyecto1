package view;

public class MenuView {

    public int startMenu() {
        System.out.println("--- ELIGE UNA OPCIÓN DEL 1 AL 4  ---");
        System.out.println("- 1. Gestión de Clientes -");
        System.out.println("- 2. Gestión de Productos -");
        System.out.println("- 3. Gestión de Facturas -");
        System.out.println("- 4. Salir -");
        System.out.print("Opción: ");

        return Input.readOption(1, 4);
    }

    public int startMenuClientes() {
        System.out.println("--- ELIGE UNA OPCIÓN DEL 1 AL 4  ---");
        System.out.println("- 1. Listar clientes -");
        System.out.println("- 2. Crear cliente -");
        System.out.println("- 3. Modificar cliente -");
        System.out.println("- 4. Eliminar cliente -");
        System.out.print("Opción: ");

        return Input.readOption(1, 4);
    }

    public int startMenuProductos() {
        System.out.println("--- ELIGE UNA OPCIÓN DEL 1 AL 4  ---");
        System.out.println("- 1. Listar productos -");
        System.out.println("- 2. Crear producto -");
        System.out.println("- 3. Modificar producto -");
        System.out.println("- 4. Eliminar producto -");
        System.out.print("Opción: ");

        return Input.readOption(1, 4);
    }

    public int startMenuFacturas() {
        System.out.println("--- ELIGE UNA OPCIÓN DEL 1 AL 3  ---");
        System.out.println("- 1. Listar facturas de un cliente -");
        System.out.println("- 2. Emitir factura -");
        System.out.println("- 3. Marca factura como pagada -");
        System.out.print("Opción: ");

        return Input.readOption(1, 3);
    }
}
