package com.example.sadapp;

public class Admin {
    private int ID;
    private String Name;
    private String Password;
    private String Address;
    private String Telephone;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getTelephone() {
        return Telephone;
    }


    public Admin(int ID,String Name,String Password,String Address,String Telephone ) {
        this.ID = ID;
        this.Name = Name;
        this.Password = Password;
        this.Address = Address;
        this.Telephone = Telephone;
    }

}

