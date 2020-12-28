package com.example.sadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText id_input,name_input, cost_input,quantity_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);

        name_input = findViewById(R.id.name_input);
        cost_input = findViewById(R.id.cost_input);
        quantity_input = findViewById(R.id.quantity_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addItem(name_input.getText().toString().trim(),
                        Double.parseDouble(cost_input.getText().toString().trim()),
                        Integer.parseInt(quantity_input.getText().toString().trim()));


            }
        });


    }
}