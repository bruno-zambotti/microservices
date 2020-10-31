package br.com.zambotti.order.model;

import java.util.Date;
import java.util.Set;

public class Customer {

	private Integer id;
	private String name;
	private String surname;
	private String birthDate;
	private char gender;
	private Set<Address> address;
	private Set<Phone> phones;

	public Customer() {
	}

	public Customer(String name, String surname, String birthDate, char gender, Set<Phone> phones) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phones = phones;
	}

	public Customer(String name, String surname, String birthDate, char gender, Set<Address> address,
                    Set<Phone> phones) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phones = phones;
	}

	public Customer(Integer id, String name, String surname, String birthDate, char gender, Set<Address> address,
                    Set<Phone> phones) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.address = address;
		this.phones = phones;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}
}
