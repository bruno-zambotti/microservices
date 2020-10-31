package br.com.zambotti.order.model;

import br.com.zambotti.order.model.enums.AddressType;

public class Address {

	private String streetName;
	private Integer number;
	private String complement;
	private String postalCode;
	private String city;
	private String province;
	private String country;
	private Integer type;

	public Address(){}

	public Address(String streetName, Integer number, String complement, String postalCode, String city,
				   String province, String country, Integer type) {
		this.streetName = streetName;
		this.number = number;
		this.complement = complement;
		this.postalCode = postalCode;
		this.city = city;
		this.province = province;
		this.country = country;
		this.type = type;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getType() { return type;	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	

}
