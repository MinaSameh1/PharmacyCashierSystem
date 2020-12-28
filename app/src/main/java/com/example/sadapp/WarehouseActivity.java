package com.example.sadapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WarehouseActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList <String> item_id,item_name,item_cost,item_quantity;
    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleViewWarehouseActivity);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WarehouseActivity.this,AddActivity.class) ;
                startActivity(intent);

            }

        });
        myDB = new MyDatabaseHelper(WarehouseActivity.this);
        item_id = new ArrayList<>();
        item_name = new ArrayList<>();
        item_cost = new ArrayList<>();
        item_quantity = new ArrayList<>();

        storeDataInArray();

        customAdapter = new CustomAdapter(WarehouseActivity.this ,this,item_id,item_name,item_cost,
                item_quantity);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(WarehouseActivity.this));



    }
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            recreate();
        }
    }


    void storeDataInArray(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {

                item_id.add(cursor.getString(0));
                item_name.add(cursor.getString(1));
                item_cost.add(cursor.getString(2));
                item_quantity.add(cursor.getString(3));

            }

        }


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId()==R.id.delete_all){
           confirmDialog();
       }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("delete All?" );
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialoginterface, int i) {
                MyDatabaseHelper myDB=new MyDatabaseHelper(WarehouseActivity.this);
                myDB.deleteAllData();
                //refresh Activity
                Intent intent=new Intent( WarehouseActivity.this,WarehouseActivity.class);
                startActivity(intent);
                finish();

            }

        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}


