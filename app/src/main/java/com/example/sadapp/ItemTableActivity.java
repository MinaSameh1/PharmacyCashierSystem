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

public class ItemTableActivity extends AppCompatActivity {

	// main RecyclerView
	RecyclerView recyclerView;
	// the layout manager
	RecyclerView.LayoutManager layoutManager;
	// the database Helper
	SqlHelper helper;
	// the db itself
	SQLiteDatabase db;
	// the adaptor of the RecyclerView
	ItemsAdaptor itemsAdaptor;
	int itemPosition;
	// the list of the items
	List<Items> itemsList = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_table);


		// objs of the views to control the elements of the layout.
		FloatingActionButton doButton = findViewById(R.id.floatingActionButtonItemsTableDo);
		FloatingActionButton delButton = findViewById(R.id.floatingActionButtonItemsTableDel);
		final TextView textView = findViewById(R.id.textViewTableItemID);
		final EditText editText = findViewById(R.id.editTextItemTableItemName);
		final EditText editText1 = findViewById(R.id.editTextItemTableItemCost);
		final EditText editText2 = findViewById(R.id.editTextItemTableInStock);
		final EditText editText3 = findViewById(R.id.editTextItemTableSold);

		// DatabaseHelper
		helper = new SqlHelper(getApplicationContext());
		// the Database
		db = helper.getReadableDatabase();
		// List of data
		itemsList = helper.getItems();
		// Main RecyclerView
		recyclerView = findViewById(R.id.RecyclerViewItemsTable);
		// make it so it has a fixed size to save some resources
		recyclerView.setHasFixedSize(true);
		// layout manager for the RCviewer
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		// ItemsAdaptor for
		itemsAdaptor = new ItemsAdaptor(this, itemsList, recyclerView);
		recyclerView.setAdapter(itemsAdaptor);

		// Set the textView to 0, meaning it is ready to accept new items.
		textView.setText("0");

		ItemsAdaptor.OnItemClickListener onItemClickListener = new ItemsAdaptor.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position, String tag) {
				textView.setText(itemsList.get(position).getID() + "");
				editText.setText(itemsList.get(position).getName());
				editText1.setText(itemsList.get(position).getCost() + "");
				editText2.setText(itemsList.get(position).getInStock() + "");
				editText3.setText(itemsList.get(position).getSold() + "");
				itemPosition = position;

			} //OnItemClick
		}; //OnItemClickListener
		// Set the listener
		itemsAdaptor.setOnItemClickListener(onItemClickListener);

		// Do action on button press
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
											// Create object of item
											Items item = new Items(
											        // Set the ID to New ID if the given ID is 0, Else set the ID to the textView.
													// This is used so if the user chose an item, we will keep the ID to update it and if the user
													// didn't choose an item, the ID will be 0 so we will get the new ID (Note the id is auto incremented from the database)
													(
															Integer.parseInt(textView.getText().toString()) == 0 ?  // if true return new ID
																	(itemsList.get(itemsList.size() - 1).getID() + 1) : // else return selected ID
																	Integer.parseInt(textView.getText().toString())) ,
													editText.getText().toString(),
													Double.parseDouble(editText1.getText().toString()),
													Integer.parseInt(editText2.getText().toString()),
													Integer.parseInt(editText3.getText().toString())
											);
											// DEBUG
											Log.d("MyApp", "onClick: ItemID: " + item.getID() );

											// if the itemID isn't 0, that means the item already exists
											if(!(textView.getText().toString().equals("0"))) {
												// Update the item in the table.
                                                if(!helper.updateItem(item)) {
                                                	// First remove the item from the data
													itemsList.remove(item.getID() - 1);
													// readd it with the new data at the same position
													itemsList.add(item.getID() - 1 , item);
													// notify the adaptor of the change
													itemsAdaptor.notifyDataSetChanged();
													// Tell the user Success!
													Toast.makeText(getApplicationContext(), "Item Updated!", Toast.LENGTH_SHORT).show();
												} else {
                                                    // Failed
													Toast.makeText(getApplicationContext(), "Item Failed to update!", Toast.LENGTH_SHORT).show();
												} // else
											} // if
											else {
												Log.d("MyApp", "onClick: The item has been added!");
												// add it to database
												helper.addItem(item);
												// tell user it works
												Toast.makeText(getApplicationContext(),"Item Added!", Toast.LENGTH_SHORT).show();
												// add it to the list!
												itemsList.add(item);
												//itemsAdaptor.notifyItemInserted(item.getID());
												itemsAdaptor.notifyDataSetChanged();
											} // else
											// reset the text and editText elements
											textView.setText("0");
											editText.setText("");
											editText1.setText("");
											editText2.setText("");
											editText3.setText("");
										} //OnClick
									} //ClickListener
		); // setOnclickListener

		delButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
			    // check if the user has item Selected
				if( textView.getText().equals("0")){
					Toast.makeText(getApplicationContext(), "Please Select an item first to delete!", Toast.LENGTH_SHORT).show();
					return;
				}
				// Check if the List has data
				if( itemsList.isEmpty() ) {
					Toast.makeText(getApplicationContext(), "The List is Empty! cannot remove what is not there", Toast.LENGTH_SHORT).show();
					return;
				}
				// The id  of the item
				int ID = Integer.parseInt(textView.getText().toString());
				// Del the item
				helper.delItem(
				        ID
				);
				// remove it from the data
				itemsList.remove(itemPosition);
				// notify the adaptor of the change
				itemsAdaptor.notifyDataSetChanged();
				// reset the views
				textView.setText("0");
				editText.setText("");
				editText1.setText("");
				editText2.setText("");
				editText3.setText("");
			}
		});

		// Close the database Connection
		db.close();
	}
}
