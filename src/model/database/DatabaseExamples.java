package model.database;

import model.Client;
import model.Product;

public class DatabaseExamples {
	/***************/
	/* R E A D M E */
	/***************/

	/*
	 * La clase estatica Database contiene cuatro
	 * parametros publicos que se corresponde con cada una
	 * de la tablas:
	 * 
	 * Database.clients --> Tabla de clientes
	 * Database.products --> Tabla de productos
	 * Database.bills    --> Tabla de facturas
	 * Database.soldProducs --> Tabla de productos vendidos 
	 *                          (Almacena los produtos de 
	 *                          cada factura y sus cantidades)
	 * 
	 * Cada una de estas tablas expone cinco metodos que permiten 
	 * interactuar con la base de datos. A continuacion se
	 * documentan estos metodos para la tabla de clientes, pero
	 * equivalentes en el resto de entidades.
	 * 
	 * Seleccionar datos de una tabla
	 * ------------------------------
	 * 
	 * Database.clients.select(int id) --> Client
	 * 
	 * Toma como parametro un entero con la id del cliente y 
	 * devuelve el correspondiente objecto Client con esa id.
	 * Si no encuentra la id en la table, devuelve null.
	 * 
	 * 
	 * Seleccionar todos los datos de una tabla
	 * ----------------------------------------
	 * 
	 * Database.clients.selectAll() --> Collection<Client>
	 * 
	 * Devuelve una collecion que contiene todos los clientes 
	 * almacenados en la tabla. Seria el equivalente a hacer
	 * 'SELECT * FROM clients*' en SQL.
	 * Si no  hay datos en la tabla, se devuelve una coleccion 
	 * vacia.
	 * 
	 * Este metodo es muy util para cuando se necesita listar
	 * todos los clientes, por ejemplo:
	 * 
	 * for (Client cliente : Database.clients.selectAll())
	 *     System.out.println(cliente);
	 *     
	 * Este bucle foreach imprimiria todos los clientes de la
	 * base de datos.
	 * 
	 * 
	 * Insertar datos en una tabla
	 * ---------------------------
	 * 
	 * Database.clients.insert(Clint cliente) --> int
	 * 
	 * Toma como parametro un cliente y lo inserta en la tabla.
	 * Al insertarlo, el campo cliente.id es remplazado por una
	 * nueva id unica que se genera automaticamente de forma 
	 * incremental. Devuelve la id del cliente que se acaba de 
	 * insertar.
	 * 
	 * De esta forma es muy sencillo almacenar un cliente:
	 * 
	 * Client cliente = new Cliente("nombre", "NIF", ...); // Creamos el cliente
	 * int id = Database.clients.insert(cliente);          // Lo insertamos en la tabla
	 *                                                     // y nos guardamos su id
	 * 
	 * client = Database.clients.select(id);               // Lo volvemos a seleccionar de la tabla
	 * System.out.println(clienteSelect.getId())           // Se le ha asignado una id automaticamente
	 * 
	 * Eliminar datos de una tabla
	 * ---------------------------
	 * 
	 * Database.clients.remove(int idCliente)
	 * 
	 * Toma como parametro la id de un cliente y lo elimina
	 * de la tabla.
	 * 
	 * NOTA: Si el cliente no existe en la tabla se lanza
	 * una excepcion de tipo IllegalArgumentException
	 * 
	 * 
	 * Comprobar si existe un dato en una tabla
	 * ----------------------------------------
	 * 
	 * Database.clients.exists(idCliente) --> boolean
	 * 
	 * Este metodo permite comprobar si existe un cliente 
	 * con una id determinada en la tabla y de vuelve 
	 * verdadero o falso en consecuencia.
	 * 
	 * 
	 * Modificar los campos de un registro
	 * -----------------------------------
	 * 
	 * Database.clients.update(Client newClient)
	 * 
	 * Este metodo permite modificar los diferentes 
	 * campos de un cliente, reemplazandolo por los
	 * que contiene el objeto newClient.
	 * 
	 * NOTA: El cliente que se desea modificar 
	 * debe estar ya presente en la tabla, de lo 
	 * contrario el metodo lanza una excepcion
	 * IllegalArgumentException. 
	 * Dicho de otra forma: No se pueden modificar
	 * los datos de un cliente que no estar en la tabla
	 * 
	 * NOTA: Si la id del nuevo cliente es 0, el metodo
	 * lanza la excepcion IllegalArgumentException de manera
	 * preventiva. Esto es asi para evitar que se intente
	 * modificar un cliente sin id. Por ejemplo:
	 * 
	 * Client cliente = new Client("Nombre", "NIF", ...);
	 * Database.clients.update(cliente); // Lanza un error porque cliente.id = 0
	 */
	
	public static void runAll() {
		example1();
		example2();
		example3();
		example4();
	}
	
	/*
	 * Ejemplo: Insertar un cliente en la base de datos
	 */
	public static void example1() {
		// Primero creamos el cliente
		Client client = new Client(
				"Productos Electronicos S.L.",
				"33366677A",
				"electronica@malaga.es",
				"El Romeral N5",
				false
				);
		
		// Una vez creado ya podemos insertarlo en la base de datos
		int id = Database.clients.insert(client);
		System.out.println("Cliente insertado: " + Database.clients.select(id));
		
		// Podemos comprobar que lo hemos insertado correctamente con el metodo exists
		if (Database.clients.exists(id))
			System.out.println("El cliente con id " + id + " existe");
		else
			System.out.println("No existe ningun cliente con id " + id);
	}
	
	/*
	 * Ejemplo: Modificar el stock de un producto
	 */
	public static void example2() {
		// Insertamos un nuevo producto en la base de datos
		Database.products.insert(new Product("Teclado gaming", "Teclado RGB-834 5xPRO", 5600, 21));
		
		// Seleccionamos el producto con id 1
		Product product = Database.products.select(1);
		System.out.println("Producto antes: " + product);
		
		// Cambiamos el precio del producto
		product.setPrice(5500); // Recuerda que precio de los productos se almacena en centimos

		// Aumentamos el stock disponible en 10 unidades
		product.setStock(product.getStock() + 10);
		
		// Actualizamos los cambios en la base de datos,
		// de lo contrario no se reflejaran los cambios
		Database.products.update(product);

		System.out.println("Producto despues: " + Database.products.select(1));
	}
	
	/*
	 * Ejmplo: Eliminar un producto de la base de datos
	 */
	public static void example3() {
		int id = 1;
		
		// Si existe el producto
		
		if (Database.products.exists(id)) {
			// Lo eliminamos
			Database.products.remove(1);
			System.out.println("Se ha eliminado el producto con id " + id);
			
			// ATENCION: Ten en cuenta que interar eliminar un producto que
			// no existe provoca una excepcion de tipo IllegalArgumentException
		}
		else {
			// Sino, imprimimos una advertancia
			System.out.println("No se pudo eliminar el producto con id " + id);
		}
	}
	
	/*
	 * Ejemplo: Listar todos los clientes
	 */
	public static void example4() {
		System.out.println("Listado de todos los clientes");
		System.out.println();

		// Con selectAll() recibimos una coleccion con todos los clientes que continen
		// la base de datos. Podemos iterar sobre esta coleccion para imprimir los
		// de tablles de cada cliente.
		for (Client client : Database.clients.selectAll()) {
			System.out.println("Id      " + client.getId());
			System.out.println("Nombre: " + client.getName());
			System.out.println("NIF:    " + client.getNif());
			System.out.println("Email:  " + client.getEmail());
			System.out.println();
		}
	}
}