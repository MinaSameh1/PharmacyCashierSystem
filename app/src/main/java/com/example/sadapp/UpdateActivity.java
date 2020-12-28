package com.example.sadapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, cost_input, quantity_input;
    Button update_button, delete_button;

    String id, name, title;
    double cost;
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name_input = findViewById(R.id.name_input2);
        cost_input = findViewById(R.id.cost_input2);
        quantity_input = findViewById(R.id.quantity_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        //frist we call this
        getAndSetIntentData();
        //set actionbar title after getAndSEtIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    name = name_input.getText().toString();
                    cost = Double.parseDouble(cost_input.getText().toString());
                    quantity = Integer.parseInt(quantity_input.getText().toString());
                    MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                    Log.d("App",
                            "getAndSetIntentData: Name:" + name +
                                    "  ID: " + id +
                                    "  Cost : " + cost +
                                    "  quantity : " + quantity
                    );

                    myDB.updateData(id, name, cost, quantity);
                    delete_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            confirmDialog();
                        }
                    });

                } catch (Exception ex) {
                    Log.e("warehouseApp", "onClick: FAILED TO UPDATE" + ex.getMessage(), ex);
                }


            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("cost") && getIntent().hasExtra("quantity")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            cost = Double.parseDouble(getIntent().getStringExtra("cost"));
            quantity = Integer.parseInt(getIntent().getStringExtra("quantity"));
            // Setting Intent Data
            name_input.setText(name);
            cost_input.setText(cost + "");
            quantity_input.setText(quantity + "");
            Log.d("App",
                    "getAndSetIntentData: Name:" + name +
                            "Cost : " + cost +
                            "quantity : " + quantity
            );

        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Delete " + cost + "?");
        builder.setMessage("Are you sure you want to delete" + cost + "?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialoginterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(getApplicationContext());
                myDB.deleteOneRow(id);
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