package model;

public class Client {
	private int id;
	private String name;
	private String nif;
	private String email;
	private String address;
	private int uncovered;
	private boolean re;

	public Client(int id, String name, String nif, String email, String address, int uncovered, boolean re) {
		this.id = id;
		this.name = name;
		this.nif = nif;
		this.email = email;
		this.address = address;
		this.uncovered = uncovered;
		this.re = re;
	}

	public Client(String name, String nif, String email, String address, boolean re) {
		this(0, name, nif, email, address, 0, re);
	}
	
	public int getId() {
	    return this.id;
	}

	public String getName() {
	    return this.name;
	}

	public String getNif() {
	    return this.nif;
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

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", nif=" + nif + ", email=" + email + ", address=" + address
				+ ", uncovered=" + uncovered + ", re=" + re + "]";
	}
}