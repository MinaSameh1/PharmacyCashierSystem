package com.example.sadapp;

public class Warehouse {
    private int ID;
    private String Name;
    private String Password;
    private String Address;
    private int ItemIDSupplied;

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

    public int getItemIDSupplied() {
        return ItemIDSupplied;
    }

    public void setItemIDSupplied(int itemIDSupplied) {
        ItemIDSupplied = itemIDSupplied;
    }

    public Warehouse(int ID, String password, String name, String address, int itemIDSupplied) {
        this.ID = ID;
        Password = password;
        Name = name;
        Address = address;
        ItemIDSupplied = itemIDSupplied;
    }
}
