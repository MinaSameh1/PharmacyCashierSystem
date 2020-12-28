package com.example.sadapp;

public class Bills {
    private int ID;
    private int CashierID;
    private String ItemsSold;
    private int NumberOfItems;
    private Double Total;
    private Double AmountPaid;
    private Double Change;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCashierID() {
        return CashierID;
    }

    public String getItemsSold() {
        return ItemsSold;
    }

    public int getNumberOfItems() {
        return NumberOfItems;
    }

    public Double getTotal() {
        return Total;
    }

    public Double getAmountPaid() {
        return AmountPaid;
    }

    public Double getChange() {
        return Change;
    }

    public Bills(int ID, int cashierID, String itemsSold, int numberOfItems, Double total, Double amountPaid, Double change) {
        this.ID = ID;
        CashierID = cashierID;
        ItemsSold = itemsSold;
        NumberOfItems = numberOfItems;
        Total = total;
        AmountPaid = amountPaid;
        Change = change;
    }
}
