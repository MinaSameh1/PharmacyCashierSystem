package com.example.sadapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.PrivateKey;
import java.sql.SQLData;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "PharmacyWarhouse.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME ="Warehouse";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "item_name";
    private static final String COLUMN_COST = "item_cost";
    private static final String COLUMN_QUANTITY = "item_quantity";


    MyDatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_COST + " DOUBLE," +
                COLUMN_QUANTITY + " INTEGER);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addItem(String name, double cost, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COST, cost);
        cv.put(COLUMN_QUANTITY, quantity);
        long result = db.insert(TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context, "Failed To Add Item", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show();
        }


    }
        Cursor readAllData() {
            String query = "Select * From " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if (db !=null){
                cursor = db.rawQuery(query,null);

            }
            return cursor;




        }
        void updateData(String row_id,String name , double cost,int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
            cv.put(COLUMN_COST,cost);
            cv.put(COLUMN_QUANTITY,quantity);

            try {
                long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
                if (result == -1){
                    Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
                }
            } catch ( Exception ex){
                Log.e("app", "updateData: ERROR!:" + ex.getMessage(), ex );
            }


        }

        void deleteOneRow(String row_id){
        SQLiteDatabase db =this.getWritableDatabase();
        long result= db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
  if(result ==-1){
      Toast.makeText(context, "Failed to Delete.",Toast.LENGTH_SHORT).show();
  }else{
      Toast.makeText(context, "Successfully Delete.",Toast.LENGTH_SHORT).show();
  }
    }

    void deleteAllData(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
    }
