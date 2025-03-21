package model;

import model.database.DatabaseEntity;

public class Client implements DatabaseEntity<Client> {
	private int     id;
	private String  name;
	private String  cif;
	private String  email;
	private String  address;
	private int     uncovered;
	private boolean re;
	
	/***************/
	/* Constructor */
	/***************/

	// Mostly internal constructor with all fields
	public Client(int id, String name, String cif, String email, String address, int uncovered, boolean re) {
		this.id = id;
		this.name = name;
		this.cif = cif;
		this.email = email;
		this.address = address;
		this.uncovered = uncovered;
		this.re = re;
	}

	// Cut down constructor intended for actual development
	public Client(String name, String cif, String email, String address, boolean re) {
		this(0, name, cif, email, address, 0, re);
	}

	/***********/
	/* Getters */
	/***********/

	// NOTE: Required by DatabaseEntity
	@Override
	public int getId() {
	    return this.id;
	}

	public String getName() {
	    return this.name;
	}

	public String getCif() {
	    return this.cif;
	}

	public String getEmail() {
	    return this.email;
	}

	public String getAddress() {
	    return this.address;
	}

	public int getUncovered() {
	    return this.uncovered;
	}

	public boolean getRe() {
	    return this.re;
	}

	/***********/
	/* Setters */
	/***********/

	// NOTE: Required by DatabaseEntity
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUncovered(int uncovered) {
		this.uncovered = uncovered;
	}

	public void setRe(boolean re) {
		this.re = re;
	}

	/*****************/
	/* Other methods */
	/*****************/

	/*
	 * Returns a new instance of itself,
	 * required by DatabaseEntity
	 */
	@Override
	public Client newInstance() {
		return new Client(
				getId(),
				getName(),
				getCif(),
				getEmail(),
				getAddress(),
				getUncovered(),
				getRe()
				);
	}

	// NOTE: Only for debugging
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", cif=" + cif + ", email=" + email + ", address=" + address + ", uncovered=" + uncovered + ", re=" + re + "]";
	}
}
