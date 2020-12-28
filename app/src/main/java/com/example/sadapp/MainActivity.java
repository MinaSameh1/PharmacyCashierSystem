package com.example.sadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Variables
    protected EditText username;
    protected EditText password;
    protected Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get stuff from the design xml
        username = findViewById(R.id.UsernameText);
        password = findViewById(R.id.PasswordText);
        loginButton = findViewById(R.id.LoginButton);

        // Login Button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // method call
                login();
            }
        });

    }

    // method that takes care of login
    public void login(){

        // if credentials are empty return to main function, and tell user to enter text
        if( username.getText().toString().equals("") ||
                password.getText().toString().equals("") )
        {
        	// Tell the user to type username and pass
            Toast.makeText(this.getApplicationContext(), "Please Make sure that you typed username and password", Toast.LENGTH_SHORT).show();
            return;
        } // if

        // objects to communicate with db
        SqlHelper helper = new SqlHelper(this.getBaseContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        // Updates the DB, required if modifications to SqlHelper has been made.
        //helper.onUpgrade(db,1,1);

        // Store the User type
        int Type = 0;

        // Holder for query
        String search;
        // object to grab db related info
        Cursor cursor = null;

        try {
			// Try admin first
            search = "SELECT AdminID,AdminName,AdminPassword FROM Admin WHERE " +
                    "AdminName='" + username.getText() + "' AND AdminPassword='" + password.getText() + "'";
            // Execute the Query
            Log.d("Main", "login: Trying Admin");
            cursor = db.rawQuery(search, null);

            // If the cursor is empty means no admin users found with same name and pass.
            if( cursor.getCount() != 0 ) {
                Type = 1;
            } // if
			// else check for cashier user.
            else {
                //String, Query for cashier
                search = "SELECT CashierID,CashierName,CashierPassword FROM Cashier WHERE " +
                        "CashierName='" + username.getText() + "' AND CashierPassword='" + password.getText() + "'";
                // Execute the Query
                Log.d("Main", "login: Trying Cashier");
                cursor = db.rawQuery(search, null);

                // check cursor for Users.
                if (cursor.getCount() != 0) {
                    Type = 2;
                } //if
                // else no user cashier found with that info, check for warehouse user.
                else {
                    // Query for warehouse search.
                    Log.d("Main", "login: Trying Warehouse");
                    search = "SELECT WarehouseID,WarehouseName,WarehousePass FROM Warehouse WHERE" +
                            " WarehouseName='" + username.getText() + "' AND WarehousePass='" + password.getText() + "'";
                    // Execute the Query
                    cursor = db.rawQuery(search, null);

                    // If cursor has users, means that warehouse user is found.
                    if (cursor.getCount() != 0) {
                        Type = 3;
                    } //if
                } // else
            } // else

            // Toast for showing info to user
            // intent to launch the app.
            Intent intent;
            int index;
            switch(Type){
                case 1:
                    // Welcome Admin!
                    Toast.makeText(this.getApplicationContext(),
                            "Welcome Admin!", Toast.LENGTH_SHORT).show();
                    // Intent of the new activity.
                    intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                    break;

                case 2:
                    // Welcome Cashier!
                    intent = new Intent(MainActivity.this, CashierActivity.class);
                    cursor.moveToFirst();
                    index = cursor.getColumnIndex("CashierID");
                    intent.putExtra("CashierID",cursor.getInt(index));
                    startActivity(intent);
                    Toast.makeText(this.getApplicationContext(),
                            "Welcome Cashier!", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    // Welcome Warehouse!!!
                    intent = new Intent(MainActivity.this, WarehouseActivity.class);
                    cursor.moveToFirst();
                    index = cursor.getColumnIndex("WarehouseID");
                    intent.putExtra("CashierIDWarehouseID",cursor.getInt(index));
                    startActivity(intent);
                    Toast.makeText(this.getApplicationContext(),
                            "Welcome Warehouse!", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    // Tell the user To check the name and pass, they weren't found!
                    // TODO: Maybe make the error sent to the user in a better way?
                    Toast.makeText(this.getApplicationContext(),
                            "Please Check your Username and password", Toast.LENGTH_SHORT).show();
            } // Switch

			// if we want to use our Cursor
            //cursor.moveToFirst();

        }  // try
        catch( Exception ex ) {
            Toast.makeText(this.getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        } // catch
		finally {
            // close the DB connection even if error occurs
            if (cursor != null) {
                cursor.close();
            }
            if( db != null) {
                db.close();
            }
            if(helper != null) {
                helper.close();
            }
        }

    } // login

}
