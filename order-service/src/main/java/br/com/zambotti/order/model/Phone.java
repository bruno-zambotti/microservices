package br.com.zambotti.order.model;

public class Phone {
    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
