package br.com.zambotti.customer.dto;

import java.util.List;

public class CustomerResponse {

    public CustomerResponse() {
    }

    public CustomerResponse(Integer id, String name, String surname, String birthDate, Character gender,
                            List<AddressDTO> address, List<PhoneDTO> phones) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.phones = phones;
    }

    private Integer id;
    private String name;
    private String surname;
    private String birthDate;
    private Character gender;
    private List<AddressDTO> address;
    private List<PhoneDTO> phones;

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

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public List<AddressDTO> getAddress() {
        return address;
    }

    public void setAddress(List<AddressDTO> adress) {
        this.address = adress;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

}
