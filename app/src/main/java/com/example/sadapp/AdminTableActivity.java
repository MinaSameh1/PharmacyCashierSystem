package com.example.sadapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdminTableActivity extends AppCompatActivity {

	// main RecyclerView
	RecyclerView recyclerView;
	// the layout manager
	RecyclerView.LayoutManager layoutManager;
	// the database Helper
	SqlHelper helper;
	// the adaptor of the RecyclerView
	AdminAdaptor adminAdaptor;
	int adminPosition;
	// the list of the items
	List<Admin> adminsList = new ArrayList<>();
	// editText and textviews
	TextView textView;
	EditText editText,editText1,editText2,editText3;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_table);


		// objs of the views to control the elements of the layout.
		FloatingActionButton doButton = findViewById(R.id.floatingActionButtonAdminTableDo);
		FloatingActionButton delButton = findViewById(R.id.floatingActionButtonAdminTableCross);
		textView = findViewById(R.id.textViewAdminTableID);
		editText = findViewById(R.id.editTextAdminTableName);
		editText1 = findViewById(R.id.editTextAdminTablePassword);
		editText2 = findViewById(R.id.editTextAdminTableAddress);
		editText3 = findViewById(R.id.editTextAdminTableTelephone);

		// DatabaseHelper
		helper = new SqlHelper(getApplicationContext());
		// List of data
		adminsList = helper.getAdmin();
		// Main RecyclerView
		recyclerView = findViewById(R.id.recyclerViewAdminTable);
		// make it so it has a fixed size to save some resources
		recyclerView.setHasFixedSize(true);
		// layout manager for the RCviewer
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		// AdminAdaptor for the RecyclerView
		adminAdaptor = new AdminAdaptor(this, adminsList, recyclerView);
		recyclerView.setAdapter(adminAdaptor);

		// Set the textView to 0, meaning it is ready to accept new items.
		textView.setText("0");


		AdminAdaptor.OnAdminClickListener onAdminClickListener = new AdminAdaptor.OnAdminClickListener() {
			@Override
			public void onAdminClick(View view, int position, String tag) {
				Log.d("MyApp", "onAdminClick: I AM HERE");
				textView.setText( 	adminsList.get(position).getID() + ""   );
				editText.setText( 	adminsList.get(position).getName()      );
				editText1.setText(	adminsList.get(position).getPassword() 	);
				editText2.setText(	adminsList.get(position).getAddress()  	);
				editText3.setText(	adminsList.get(position).getTelephone()	);
				adminPosition = position;
			} //OnAdminClick
		}; //OnAdminClickListener
		// Set the listener
		adminAdaptor.setOnAdminClickListener(onAdminClickListener);


		doButton.setOnClickListener(new View.OnClickListener()
									{
										@Override
										public void onClick(View v) {
											// Check if the views are empty, send a msg to the user and exit this function
											if(
													TextUtils.isEmpty(editText.getText().toString()) ||
															TextUtils.isEmpty(editText1.getText().toString()) ||
															TextUtils.isEmpty(editText2.getText().toString()) ||
															TextUtils.isEmpty(editText3.getText().toString())
											)
											{
												Toast.makeText(getApplicationContext(), "Please make sure to input all the boxes", Toast.LENGTH_SHORT).show();
												return;
											}
											// Create object of Admin
											Admin admin = new Admin(
													// Set the ID to New ID if the given ID is 0, Else set the ID to the textView.
													// This is used so if the user chose a admin, we will keep the ID to update it and if the user
													// didn't choose an item, the ID will be 0 so we will get the new ID (Note the id is auto incremented from the database)
													(
															Integer.parseInt(textView.getText().toString()) == 0 ?  // if true return new ID
																	(adminsList.get(adminsList.size() - 1).getID() + 1) : // else return selected ID
																	Integer.parseInt(textView.getText().toString())) ,
													editText.getText().toString(),
													editText1.getText().toString(),
													editText2.getText().toString(),
													editText3.getText().toString()
											);
											// DEBUG
											Log.d("MyApp", "onClick: AdminID: " + admin.getID() + "\nAdminName:" + admin.getName() );

											// if the itemID isn't 0, that means the item already exists
											if(!(textView.getText().toString().equals("0"))) {
												// Update the admin in the table.
												if(!helper.updateAdmin(admin)) {
													// First remove the admin from the data
													adminsList.remove(admin.getID() - 1);
													// readd it with the new data at the same position
													adminsList.add(admin.getID() - 1 , admin);
													// notify the adaptor of the change
													adminAdaptor.notifyDataSetChanged();
													// Tell the user Success!
													Toast.makeText(getApplicationContext(), "Admin Updated!", Toast.LENGTH_SHORT).show();
												} else {
													// Failed
													Toast.makeText(getApplicationContext(), "Admin Failed to update!", Toast.LENGTH_SHORT).show();
												} // else
											} // if
											else {
												Log.d("MyApp", "onClick: The admin has been added!");
												// add it to database
												helper.addAdmin(admin);
												// add it to the list!
												adminsList.add(admin);
												// notify the adaptor of the change.
												adminAdaptor.notifyDataSetChanged();
												// tell user it works
												Toast.makeText(getApplicationContext(),"Admin Added!", Toast.LENGTH_SHORT).show();
											} // else
											// reset the text and editText elements
											resetText();
										} //OnClick
									} //ClickListener
		); // setOnclickListener

		delButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// check if the user has admin Selected
				if( textView.getText().equals("0")){
					Toast.makeText(getApplicationContext(), "Please Select an Admin first to delete!", Toast.LENGTH_SHORT).show();
					return;
				}
				// Check if the List has data
				if( adminsList.isEmpty() ) {
					Toast.makeText(getApplicationContext(), "The List is Empty! cannot remove what is not there", Toast.LENGTH_SHORT).show();
					return;
				}
				// The id  of the admin
				int ID = Integer.parseInt(textView.getText().toString());
				// Del the Admin
				helper.delAdmin(
						ID
				);
				// remove it from the data
				adminsList.remove(adminPosition);
				// notify the adaptor of the change
				adminAdaptor.notifyDataSetChanged();
				resetText();
			}
		});
	}

	// resets the textview and edittext to
	void resetText(){
		textView.setText("0");
		editText.setText("");
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
	}
}
