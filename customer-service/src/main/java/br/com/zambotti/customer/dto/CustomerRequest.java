package br.com.zambotti.customer.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CustomerRequest {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String birthDate;

    @NotNull
    private char gender;

    @NotNull
    private List<AddressDTO> adress;

    @NotNull
    private List<PhoneDTO> phones;

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

    public List<AddressDTO> getAdress() {
        return adress;
    }

    public void setAdress(List<AddressDTO> adress) {
        this.adress = adress;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }
}

