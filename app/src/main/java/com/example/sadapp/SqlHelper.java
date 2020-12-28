package com.example.sadapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// Class responsible for dealing with SQL
public class SqlHelper extends SQLiteOpenHelper {

    // Constructor
    protected SqlHelper(Context context) { super( context, "SAD_Pharmacy_Cashier", null, 1 ); }

    // Thie Creates the Entire Database from scratch
    @Override
    public void onCreate(SQLiteDatabase db) {

        // The tables that will be created
        final String SQL_CREATE_Admin =
                "CREATE TABLE IF NOT EXISTS Admin(" +
                        "AdminID INTEGER PRIMARY KEY AUTOINCREMENT," + // The admin ID
                        "AdminName VARCHAR(100) NOT NULL," + // The admin Name
                        "AdminPassword VARCHAR(100) NOT NULL," + // The admin user password
                        "AdminAddress VARCHAR(400)," + //The Admin Address
                        "AdminTelephone VARCHAR(15)" + // The admin Telephone number
                        ")";

        final String SQL_CREATE_Cashier=
                "CREATE TABLE IF NOT EXISTS Cashier(" +
                        "CashierID INT PRIMARY KEY," + // The cashier ID
                        "CashierName      VARCHAR(100) NOT NULL," + // The CashierName
                        "CashierPassword  VARCHAR(100) NOT NULL," + // The CashierPassword
                        "CashierAddress   VARCHAR(400)," + // The Cashier Address
                        "CashierTelephone VARCHAR(16)," + // The Cashier telephone Number
                        "CashierNumberOfOrders INT NOT NULL" + // The cashier how many bills did he write
                        ")";

        final String SQL_CREATE_Items=
                "CREATE TABLE IF NOT EXISTS Items(" +
                        "ItemID INT PRIMARY KEY," + // ItemID
                        "ItemName VARCHAR(100) NOT NULL," + // The item name
                        "ItemCost DOUBLE NOT NULL," + // The item cost in Double 0.00
                        "InStock INT," + // The stock of the item
                        "Sold INT" + // how many sold overall
                        ")";

        final String SQL_CREATE_Warehouse =
                "CREATE TABLE IF NOT EXISTS Warehouse(" +
                        "WarehouseID INTEGER PRIMARY KEY AUTOINCREMENT," + // The warehouse ID
                        "WarehouseName VARCHAR(100) NOT NULL," + // The warehouse name
                        "WarehousePass VARCHAR(100) NOT NULL," + // The warehouse account password
                        "WarehouseAddress VARCHAR(400)," + // The warehouse address
                        "WarehouseItemIDSupplied INT" + // The ItemID of the item that the warehouse supply
                        ")";

        final String SQL_CREATE_Bills =
                "CREATE TABLE IF NOT EXISTS Bills(" +
                        "BillID INTEGER PRIMARY KEY AUTOINCREMENT," + // Main ID of bill
                        "CashierID INT," + // CashierID that made the bill
                        "ItemsSold VARCHAR(500)," + // The items that are Sold, format: {ItemID:Quantity,ItemID:Quantity,ItemID:Quantity} Etc, Ex: {1:3,4:2}
                        "NumberOfItems INT," + // The number of items sold, not their Quantity
                        "Total DOUBLE," + // The total, in double 0.00
                        "AmountPaid DOUBLE," + // The amount the customer paid.
                        "Change DOUBLE" + // The change the customer got
//                        "FOREGIN KEY (CashierID) REFERENCES Cashier(CashierID)" + // Foregin Key of the cashhier ID
                        ")";

        // Fill up our Tables
        ContentValues admin = new ContentValues();
        ContentValues Cashier = new ContentValues();
        ContentValues Items = new ContentValues();
        ContentValues Items2 = new ContentValues();
        ContentValues Items3 = new ContentValues();
        ContentValues Items4 = new ContentValues();
        ContentValues Items5 = new ContentValues();
        ContentValues Warehouse = new ContentValues();
        ContentValues Warehouse2 = new ContentValues();
        ContentValues Warehouse3 = new ContentValues();
        ContentValues Warehouse4 = new ContentValues();


        // Admin
        admin.put("AdminID", 1);
        admin.put("AdminName", "admin");
        admin.put("AdminPassword", "admin");
        admin.put("AdminAddress", "Cairo El tagm3 el awl");
        admin.put("AdminTelephone", "0113024124");

        //  Cashier
        Cashier.put("CashierID",1);
        Cashier.put("CashierName","cashier");
        Cashier.put("CashierPassword","cashier");
        Cashier.put("CashierAddress","Cairo El tagm3 el awl");
        Cashier.put("CashierTelephone","0121312912");
        Cashier.put("CashierNumberOfOrders",0);

        //  Items
        Items.put("ItemID",1);
        Items.put("ItemName","abroxa");
        Items.put("ItemCost",100);
        Items.put("InStock",40);
        Items.put("Sold",10);

        Items2.put("ItemID",2);
        Items2.put("ItemName","asdlfd");
        Items2.put("ItemCost",120);
        Items2.put("InStock",30);
        Items2.put("Sold",11);

        Items3.put("ItemID",3);
        Items3.put("ItemName","dasasd");
        Items3.put("ItemCost",40);
        Items3.put("InStock",10);
        Items3.put("Sold",8);

        Items4.put("ItemID",4);
        Items4.put("ItemName","dlfds");
        Items4.put("ItemCost",200);
        Items4.put("InStock",10);
        Items4.put("Sold",10);

        Items5.put("ItemID",5);
        Items5.put("ItemName","qwdasAA");
        Items5.put("ItemCost",60);
        Items5.put("InStock",10);
        Items5.put("Sold",30);

        //Warehouse
        Warehouse.put("WarehouseName","Warehouse");
        Warehouse.put("WarehousePass","Warehouse");
        Warehouse.put("WarehouseAddress","alm3ady");
        Warehouse.put("WarehouseItemIDSupplied",20);

        Warehouse2.put("WarehouseName","medicine");
        Warehouse2.put("WarehousePass","medicine");
        Warehouse2.put("WarehouseAddress","masr algdyda");
        Warehouse2.put("WarehouseItemIDSupplied",90);

        Warehouse3.put("WarehouseName","zahram");
        Warehouse3.put("WarehousePass","zahram");
        Warehouse3.put("WarehouseAddress","al3bor");
        Warehouse3.put("WarehouseItemIDSupplied",50);

        Warehouse4.put("WarehouseName","alham");
        Warehouse4.put("WarehousePass","alham");
        Warehouse4.put("WarehouseAddress","alr7ab");
        Warehouse4.put("WarehouseItemIDSupplied",30);

        try {
            // Create the tables
            db.execSQL(SQL_CREATE_Admin);
            db.execSQL(SQL_CREATE_Cashier);
            db.execSQL(SQL_CREATE_Items);
            db.execSQL(SQL_CREATE_Warehouse);
            db.execSQL(SQL_CREATE_Bills);

            // insert data
            db.insert("Admin", null, admin);
            db.insert("Cashier", null, Cashier);
            db.insert("Items", null, Items);
            db.insert("Items", null, Items2);
            db.insert("Items", null, Items3);
            db.insert("Items", null, Items4);
            db.insert("Items", null, Items5);
            db.insert("Warehouse", null, Warehouse);
            db.insert("Warehouse", null, Warehouse2);
            db.insert("Warehouse", null, Warehouse3);
            db.insert("Warehouse", null, Warehouse4);

        } // Try
        catch( SQLException ex ){
            Log.e( "SQL", ex.getMessage(), ex);
        } // catch
    } // onCreate

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        // Destroy the old database
        final String SQL_DELETE_TABLE_Admin =
                "DROP TABLE IF EXISTS Admin";
        final String SQL_DELETE_TABLE_Cashier =
                "DROP TABLE IF EXISTS Cashier";
        final String SQL_DELETE_TABLE_Items =
                "DROP TABLE IF EXISTS Items";
        final String SQL_DELETE_TABLE_Warehouse =
                "DROP TABLE IF EXISTS Warehouse";
        final String SQL_DELETE_TABLE_Bills =
                "DROP TABLE IF EXISTS Bills";

        try {
            // drop them all
            db.execSQL(SQL_DELETE_TABLE_Admin);
            db.execSQL(SQL_DELETE_TABLE_Cashier);
            db.execSQL(SQL_DELETE_TABLE_Items);
            db.execSQL(SQL_DELETE_TABLE_Warehouse);
            db.execSQL(SQL_DELETE_TABLE_Bills);
        }
        catch ( SQLException ex ) {
            Log.e( "SQLhelper", "onUpgrade: " +  ex.getMessage() , ex );
        }
        // recreate the database
            onCreate(db);
    } // onUpgrade

	// Insert Item inTo table Items, takes columns as parameters
    public void addItem(String Name, Double ItemCost,
                        int InStock, int Sold){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Item = new ContentValues();

        Item.put("ItemName",Name);
        Item.put("ItemCost",ItemCost);
        Item.put("InStock",InStock);
        Item.put("Sold",Sold);

        try {
        	// Insert into table Items
            db.insert("Items", null, Item);
        } // try
        catch (SQLException ex) {
            Log.e("SqlHelper", "addItem: Error! " + ex.getMessage(), ex );
        }//catch
        finally {
            // close database if it is still open
            if( db != null ) {
                db.close();
            } // if
        } // finally
    } // addItem

    // addBill
    public void addBill( int CashierID, String ItemsSold,
                        int NumberOfItems, double total, double AmountPaid, double Change){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Bill = new ContentValues();

        // Bill values
        Bill.put("CashierID",CashierID);
        Bill.put("ItemsSold",ItemsSold);
        Bill.put("NumberOfItems",NumberOfItems);
        Bill.put("Total",total);
        Bill.put("AmountPaid",AmountPaid);
        Bill.put("Change",Change);

        try {
            // Insert into table Bills
            db.insert("Bills", null, Bill);
        } // try
        catch (SQLException ex) {
            Log.e("SqlHelper", "addBill: Error! " + ex.getMessage(), ex );
        }//catch
        finally {
            // close database if it is still open
            if( db != null ) {
                db.close();
            } // if
        } // finally
    } //addBill

    public void addAdmin(int AdminID, String AdminName,
                         String AdminPassword, String AdminAddress, String AdminTelephone){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues admin = new ContentValues();

        admin.put("AdminID",AdminID);
        admin.put("AdminName",AdminName);
        admin.put("AdminPassword",AdminPassword);
        admin.put("AdminAddress",AdminAddress);
        admin.put("AdminTelephone",AdminTelephone);

        try {
            // Insert into table admin
            db.insert("Admin", null, admin);
        } // try
        catch (SQLException ex) {
            Log.e("SqlHelper", "addAdmin: Error! " + ex.getMessage(), ex );
        }//catch
        finally {
            // close database if it is still open
            if( db != null ) {
                db.close();
            } // if
        } // finally
    }

    public void addCashier(int CashierID, String CashierName, String CashierPassword, String CashierAddress, String CashierTelephone, int CashierNumberOfOrders)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cashier = new ContentValues();

        cashier.put( "CashierID", CashierID );
        cashier.put( "CashierName", CashierName );
        cashier.put( "CashierPassword", CashierPassword );
        cashier.put( "CashierAddress", CashierAddress );
        cashier.put( "CashierTelephone", CashierTelephone );
        cashier.put( "CashierNumberOfOrders", CashierNumberOfOrders );

        try {
            // Insert into table admin
            db.insert("cashier", null, cashier);
        } // try
        catch (SQLException ex) {
            Log.e("SqlHelper", "addCashier: Error! " + ex.getMessage(), ex );
        }//catch
        finally {
            // close database if it is still open
            if( db != null ) {
                db.close();
            } // if
        } // finally
    }

    public void addWarehouse(int WarehouseID, String WarehouseName, String WarehousePass,
                             String WarehouseAddress, int WarehouseItemIDSupplied) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues warehouse = new ContentValues();

        warehouse.put("WarehouseID",WarehouseID);
        warehouse.put("WarehouseName",WarehouseName);
        warehouse.put("WarehousePass",WarehousePass);
        warehouse.put("WarehouseAddress",WarehouseAddress);
        warehouse.put("WarehouseItemIDSupplied",WarehouseItemIDSupplied);

        try {
            // Insert data into table Warehouse
            db.insert("Warehouse", null, warehouse );
        } // try
        catch ( SQLException ex ) {
                Log.e("SqlHelper", "addWarehouse: Error!" + ex.getMessage(), ex );
        } //catch
        finally {
            // close database if it is still open
            if( db != null ) {
                db.close();
            } // if
        } // finally
    } // addWarehouse

    //Read all data from the database, store them in a List of type Cursor for easy reading.
    public List readAllData(){
        // our vars
        SQLiteDatabase db = this.getWritableDatabase();
        List<Cursor> list = new ArrayList<>();
        Cursor cursor;

        String query;
        // every table name
        String[] TableName = new String[]{ "Admin", "Cashier", "Warehouse", "Items", "Bills" };

        try {
            if (db != null) {
                // enhanced For Loop
                for (String Table : TableName) {
                    // Query that has table name of our tables.
                    query = "SELECT * FROM " + Table;
                    cursor = db.rawQuery(query, null);
                    // add the Cursor to the list
                    list.add(cursor);
                } // for
            } // if
        } //try 
        catch( SQLException ex ){
            Log.e("SqlHelper","SQLHelper: " + ex.getMessage(), ex );
        } // catch
        finally {
                // close the connection
			if( db != null ) {
                db.close();
            }
			// if list isn't null then return it, else return null.
            return (list != null ) ? list : null;
        } // finally

    } // readAllData

    public Cursor getWarehouseCursor(){
        String query = "Select * From Items";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db !=null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;
    }

    public List<Items> getItems(){
        // Variables
        List<Items> itemsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database to return table itemsList
        String query = "SELECT * FROM Items";
        try {
            Cursor cursor = db.rawQuery(query, null);
            Log.d("MyApp", "getItems: The amount of items:" + cursor.getCount());

            // Move all data of table in the list
            while (cursor.moveToNext()) {
                int index;
                index = cursor.getColumnIndex("ItemID");
                int ItemID = cursor.getInt(index);
                index = cursor.getColumnIndex("ItemName");
                String name = cursor.getString(index);
                index = cursor.getColumnIndex("ItemCost");
                Double ItemCost = cursor.getDouble(index);
                index = cursor.getColumnIndex("InStock");
                int InStock = cursor.getInt(index);
                index = cursor.getColumnIndex("Sold");
                int Sold = cursor.getInt(index);

                // Create Item from the data of that row.
                Items item = new Items(ItemID, name, ItemCost, InStock, Sold);
                // add the item to the list
                itemsList.add(item);
            } //While
            if( cursor != null ) { cursor.close(); }
        } // Try
        catch ( SQLException ex ){
            Log.e("GetItems SQLHELPER:", "getItems: Error in SQL! :" + ex.getMessage(), ex );
        } // catch
        finally {
            db.close();
            return itemsList;
        }// finally
    } // getItems

    public List<Admin> getAdmin(){
        // Variables
        List<Admin> adminList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database to return table
        String query = "SELECT * FROM Admin";
        try {
            Cursor cursor = db.rawQuery(query, null);
            Log.d("MyApp", "getAdmin: The amount of items:" + cursor.getCount());


            // Move all data of table in the list
            while (cursor.moveToNext()) {
                int index;
                index = cursor.getColumnIndex("AdminID");
                int AdminID = cursor.getInt(index);
                index = cursor.getColumnIndex("AdminName");
                String AdminName = cursor.getString(index);
                index = cursor.getColumnIndex("AdminPassword");
                String AdminPassword = cursor.getString(index);
                index = cursor.getColumnIndex("AdminAddress");
                String AdminAddress = cursor.getString(index);
                index = cursor.getColumnIndex("AdminTelephone");
                String AdminTelephone = cursor.getString(index);

                // Create Admin from the data of that row.
                Admin admin = new Admin(AdminID, AdminName, AdminPassword, AdminAddress, AdminTelephone);
                // add the admin to the list
                adminList.add(admin);
            } //While
            if( cursor != null ) { cursor.close(); }
        } // Try
        catch ( SQLException ex ){
            Log.e("GetAdmin SQLHELPER:", "getAdmin: Error in SQL! :" + ex.getMessage(), ex );
        } // catch`
        finally {
            db.close();
            return adminList;
        }// finally
    } // getAdmin

    //cashier
    public List<Cashier> getCashier(){
        // Variables
        List<Cashier> CashierList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database to return table
        String query = "SELECT * FROM Cashier";
        try {
            Cursor cursor = db.rawQuery(query, null);
            Log.d("MyApp", "getCashier: The amount of Cashier:" + cursor.getCount());


            // Move all data of table in the list
            while (cursor.moveToNext()) {
                int index;
                index = cursor.getColumnIndex("CashierID");
                int CashierID = cursor.getInt(index);
                index = cursor.getColumnIndex("CashierName");
                String CashierName = cursor.getString(index);
                index = cursor.getColumnIndex("CashierPassword");
                String CashierPassword = cursor.getString(index);
                index = cursor.getColumnIndex("CashierAddress");
                String CashierAddress = cursor.getString(index);
                index = cursor.getColumnIndex("CashierTelephone");
                String CashierTelephone = cursor.getString(index);
                index = cursor.getColumnIndex("CashierNumberOfOrders");
                int CashierNumberOfOrders = cursor.getInt(index);
                // Create Cashier from the data of that row.
                Cashier cashier = new Cashier(CashierID,CashierName,CashierPassword, CashierAddress, CashierTelephone,CashierNumberOfOrders);
                // add the cashier to the list
                CashierList.add(cashier);
            } //While
            if( cursor != null ) { cursor.close(); }
        } // Try
        catch ( SQLException ex ){
            Log.e("GetCashier SQLHELPER:", "getCashier: Error in SQL! :" + ex.getMessage(), ex );
        } // catch
        finally {
            db.close();
            return CashierList;
        }// finally
    } // getCashier

    //warehouse
    public List<Warehouse> getWarehouse(){
        // Variables
        List<Warehouse> WarehouseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database to return table
        String query = "SELECT * FROM Warehouse";
        try {
            Cursor cursor = db.rawQuery(query, null);
            Log.d("MyApp", "getWarehouse: The amount of Warehouse:" + cursor.getCount());


            // Move all data of table in the list
            while (cursor.moveToNext()) {
                int index;
                index = cursor.getColumnIndex("WarehouseID");
                int WarehouseID = cursor.getInt(index);
                index = cursor.getColumnIndex("WarehouseName");
                String WarehouseName = cursor.getString(index);
                index = cursor.getColumnIndex("WarehousePass");
                String WarehousePass = cursor.getString(index);
                index = cursor.getColumnIndex("WarehouseAddress");
                String WarehouseAddress = cursor.getString(index);
                index = cursor.getColumnIndex("WarehouseItemIDSupplied");
                int WarehouseItemIDSupplied = cursor.getInt(index);
                // Create Item from the data of that row.
                Warehouse warehouse = new Warehouse(WarehouseID,WarehouseName,WarehousePass, WarehouseAddress,WarehouseItemIDSupplied);
                // add the item to the list
                WarehouseList.add(warehouse);
            } //While
            if( cursor != null ) { cursor.close(); }
        } // Try
        catch ( SQLException ex ){
            Log.e("GetWarehouse SQLHELPER:", "getWarehouse: Error in SQL! :" + ex.getMessage(), ex );
        } // catch`
        finally {
            db.close();
            return WarehouseList;
        }// finally
    } // getWarehouse

    //Bills
    public List<Bills> getBills(){
        // Variables
        List<Bills> BillsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database to return table
        String query = "SELECT * FROM Bills";
        try {
            Cursor cursor = db.rawQuery(query, null);
            Log.d("MyApp", "getBills: The amount of Bills:" + cursor.getCount());


            // Move all data of table in the list
            while ( cursor.moveToNext() ) {
                int index;
                index = cursor.getColumnIndex("BillID");
                int BillID = cursor.getInt(index);
                index = cursor.getColumnIndex("CashierID");
                int CashierID = cursor.getInt(index);
                index = cursor.getColumnIndex("ItemsSold");
                String ItemsSold = cursor.getString(index);
                index = cursor.getColumnIndex("NumberOfItems");
                int NumberOfItems = cursor.getInt(index);
                index = cursor.getColumnIndex("Total");
                Double Total = cursor.getDouble(index);
                index = cursor.getColumnIndex("AmountPaid");
                Double Amount_Paid = cursor.getDouble(index);
                index = cursor.getColumnIndex("Change");
                Double change = cursor.getDouble(index);
                // Create Item from the data of that row.
                Bills bill = new Bills(BillID,CashierID,ItemsSold,NumberOfItems,Total,Amount_Paid,change);

                // add the item to the list
                BillsList.add(bill);
            } //While
            if( cursor != null ) { cursor.close(); }
        } // Try
        catch ( SQLException ex ){
            Log.e("GetBills SQLHELPER:", "getBills: Error in SQL! :" + ex.getMessage(), ex );
        } // catch
        finally {
            db.close();
            return BillsList;
        }// finally
    } // getBills



    public void addItem(Items item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Item = new ContentValues();

        Item.put("ItemID",item.getID());
        Item.put("ItemName",item.getName());
        Item.put("ItemCost",item.getCost());
        Item.put("InStock",item.getInStock());
        Item.put("Sold",item.getSold());

        try {
            // Insert into table Items
            db.insert("Items", null, Item);
        } // try
        catch (SQLException ex) {
            Log.e("SqlHelper", "addItem: Error! " + ex.getMessage(), ex );
        }//catch
        finally {
            // close database if it is still open
            if( db != null ) {
                db.close();
            } // if
        } // finally
    } // addItem

    public boolean updateItem(Items updatedItem) {

        ContentValues Item = new ContentValues();
        Item.put("ItemID",updatedItem.getID());
        Item.put("ItemName",updatedItem.getName());
        Item.put("ItemCost",updatedItem.getCost());
        Item.put("InStock",updatedItem.getInStock());
        Item.put("Sold",updatedItem.getSold());

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            return db.update("Items", Item, "ItemID = ?", new String[]{String.valueOf(updatedItem.getID())}) > 0;
        } // try
        catch ( SQLException ex ){
            Log.e("MyApp", "updateItem: Failed!", ex );
        } // catch
        finally {
            db.close();
            return false;
        } // finally
    }

    // param: The item id that will be deleted.
    public void delItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Items","ItemID = ?", new String[] { String.valueOf(id) });
    } //delItem

    public void addAdmin(Admin ad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues admin = new ContentValues();

        Log.d("MyApp", "addAdmin: AdminName:" + ad.getName());
        admin.put("AdminName", ad.getName());
        admin.put("AdminPassword", ad.getPassword());
        admin.put("AdminAddress", ad.getAddress());
        admin.put("AdminTelephone", ad.getTelephone());

        try {
            // Insert into table admin
            db.insert("Admin", null, admin);
        } // try
        catch (SQLException ex) {
            Log.e("SqlHelper", "addAdmin: Error! " + ex.getMessage(), ex);
        }//catch
        finally {
            // close database if it is still open
            if (db != null) {
                db.close();
            } // if
        } // finally
    } // addadmin

    public boolean updateAdmin(Admin ad) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues admin = new ContentValues();

            admin.put("AdminID",ad.getID());
            admin.put("AdminName",ad.getName());
            admin.put("AdminPassword",ad.getPassword());
            admin.put("AdminAddress",ad.getAddress());
            admin.put("AdminTelephone",ad.getTelephone());

        try {
            return db.update("Admin", admin, "AdminID = ?", new String[]{String.valueOf(ad.getID())}) > 0;
        } // try
        catch ( SQLException ex ){
            Log.e("MyApp", "updateAdmin: Failed!", ex );
        } // catch
        finally {
            db.close();
            return false;
        } // finally
    } // updateAdmin

    public void delAdmin(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Admin","AdminID = ?", new String[] { String.valueOf(id) });
    } //delAdmin

    public boolean updateBill(Bills bills) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Bill = new ContentValues();

        // Bill values
        Bill.put("BillID",bills.getID());
        Bill.put("CashierID",bills.getCashierID());
        Bill.put("ItemsSold",bills.getItemsSold());
        Bill.put("NumberOfItems",bills.getNumberOfItems());
        Bill.put("Total",bills.getTotal());
        Bill.put("AmountPaid",bills.getAmountPaid());
        Bill.put("Change",bills.getChange());

        try {
            return db.update("Bills", Bill, "BillID = ?", new String[]{String.valueOf(bills.getID())}) > 0;
        } // try
        catch ( SQLException ex ){
            Log.e("MyApp", "updateBill: Failed!", ex );
        } // catch
        finally {
            db.close();
            return false;
        } // finally
    } // update Bill

    public void delBill(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Bills","BillID = ?", new String[] { String.valueOf(id) });
    } //delBell

    public boolean updateCashier(Cashier cash) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cashier = new ContentValues();

        cashier.put( "CashierID", cash.getID() );
        cashier.put( "CashierName", cash.getName() );
        cashier.put( "CashierPassword", cash.getPassword() );
        cashier.put( "CashierAddress", cash.getAddress() );
        cashier.put( "CashierTelephone", cash.getTelephone() );
        cashier.put( "CashierNumberOfOrders", cash.getNumberOfOrders() );

        try {
            return db.update("Cashier", cashier, "CashierID = ?", new String[]{String.valueOf(cash.getID())}) > 0;
        } // try
        catch ( SQLException ex ){
            Log.e("MyApp", "updateCashier: Failed!", ex );
        } // catch
        finally {
            db.close();
            return false;
        } // finally
    } //Update Cashier

    public void delCashier(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Cashier","CashierID = ?", new String[] { String.valueOf(ID) });
    }//del Cashier

    public boolean updateWarehouse(Warehouse ware) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues warehouse = new ContentValues();

        warehouse.put("WarehouseID",ware.getID());
        warehouse.put("WarehouseName",ware.getName());
        warehouse.put("WarehousePass",ware.getPassword());
        warehouse.put("WarehouseAddress",ware.getAddress());
        warehouse.put("WarehouseItemIDSupplied",ware.getItemIDSupplied());

        try {
            return db.update("Warehouse", warehouse, "WarehouseID = ?", new String[]{String.valueOf(ware.getID())}) > 0;
        } // try
        catch ( SQLException ex ){
            Log.e("MyApp", "update: Failed!", ex );
        } // catch
        finally {
            db.close();
            return false;
        } // finally
    }

    public void delWarehouse(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Warehouse","WarehouseID = ?", new String[] { String.valueOf(ID) });
    }//del Cashier

    void deleteAllWarehouseData(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.execSQL("DELETE FROM Warehouse");
    }
} // Class

