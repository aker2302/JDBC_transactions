package com.project.database_project.domain;

public class Customer {
	
	private int CustomerId;
	private String custname;
	private String custaddress;
	private String custphone;
	
	
	
	public Customer(int customerId, String custname, String custaddress, String custphone) {
		
		CustomerId = customerId;
		this.custname = custname;
		this.custaddress = custaddress;
		this.custphone = custphone;
	}



	public int getCustomerId() {
		return CustomerId;
	}



	public void setCustomerId(int customerId) {
		CustomerId = customerId;
	}



	public String getCustname() {
		return custname;
	}



	public void setCustname(String custname) {
		this.custname = custname;
	}



	public String getCustaddress() {
		return custaddress;
	}



	public void setCustaddress(String custaddress) {
		this.custaddress = custaddress;
	}



	public String getCustphone() {
		return custphone;
	}



	public void setCustphone(String custphone) {
		this.custphone = custphone;
	}



	@Override
	public String toString() {
		return "Customer [CustomerId=" + CustomerId + ", custname=" + custname + ", custaddress=" + custaddress
				+ ", custphone=" + custphone + "]";
	}
	
	

}
