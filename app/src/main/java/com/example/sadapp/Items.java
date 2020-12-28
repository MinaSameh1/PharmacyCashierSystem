package com.example.sadapp;

public class Items {
    private int ID;
    private String Name;
    private Double Cost;
    private int InStock;
    private int Sold;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getCost() {
        return Cost;
    }

    public void setCost(Double cost) {
        Cost = cost;
    }

    public int getInStock() {
        return InStock;
    }

    public void setInStock(int inStock) {
        InStock = inStock;
    }

    public int getSold() {
        return Sold;
    }

    public void setSold(int sold) {
        Sold = sold;
    }

    public Items(int ID, String name, Double cost, int inStock, int sold) {
        this.ID = ID;
        Name = name;
        Cost = cost;
        InStock = inStock;
        Sold = sold;
    }
}
