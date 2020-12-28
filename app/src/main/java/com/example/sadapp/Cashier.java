package com.example.sadapp;

public class Cashier {
    private int ID;
    private String Name;
    private String Password;
    private String Address;
    private String Telephone;
    private int NumberOfOrders;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public int getNumberOfOrders() {
        return NumberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        NumberOfOrders = numberOfOrders;
    }

    public Cashier(int ID, String name, String password, String address, String telephone, int numberOfOrders) {
        this.ID = ID;
        Name = name;
        Password = password;
        Address = address;
        Telephone = telephone;
        NumberOfOrders = numberOfOrders;
    }
}
