package com.example.sadapp;

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

public class WarehouseTableActivity extends AppCompatActivity {
	// main RecyclerView
	RecyclerView recyclerView;
	// the layout manager
	RecyclerView.LayoutManager layoutManager;
	// the database Helper
	SqlHelper helper;
	// the adaptor of the RecyclerView
    WarehouseTableAdaptor adaptor;
	int warehousePosition;
	// the list of the items
	List<Warehouse> warehouseList = new ArrayList<>();
	// editText and textviews
	TextView textView;
	EditText editText,editText1,editText2,editText3;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_warehouse_table);

		FloatingActionButton doButton = findViewById(R.id.floatingActionButtonWarehouseTableDo),
				delButton = findViewById(R.id.floatingActionButtonWarehouseTableDel);
		textView = findViewById(R.id.textViewWarehouseTableID);
		editText = findViewById(R.id.editTextWarehouseTableName);
		editText1= findViewById(R.id.editTextWarehouseTablePassword);
		editText2= findViewById(R.id.editTextWarehouseTableAddress);
		editText3= findViewById(R.id.editTextWarehouseTableItemID);

		// DatabaseHelper
		helper = new SqlHelper(getApplicationContext());
		// List of data
		warehouseList = helper.getWarehouse();
		// Main RecyclerView
		recyclerView = findViewById(R.id.recyclerViewWarehouseTable);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adaptor = new WarehouseTableAdaptor(this, warehouseList, recyclerView);
		recyclerView.setAdapter(adaptor);

		// Set the textView to 0, meaning it is ready to accept new items.
		textView.setText("0");

	adaptor.setMyOnClickListener(new MyOnClickListener() {
		@Override
		public void MyOnClick(View view, int position, String tag) {
			textView.setText( warehouseList.get(position).getID() + "");
			editText.setText( warehouseList.get(position).getName());
			editText1.setText( warehouseList.get(position).getPassword());
			editText2.setText( warehouseList.get(position).getAddress());
			editText3.setText( warehouseList.get(position).getItemIDSupplied() + "");
		}
	});

	doButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
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
			// Create object
			Warehouse warehouse = new Warehouse(
					// Set the ID to New ID if the given ID is 0, Else set the ID to the textView.
					// This is used so if the user chose a admin, we will keep the ID to update it and if the user
					// didn't choose an item, the ID will be 0 so we will get the new ID (Note the id is auto incremented from the database)
					        0,
					editText.getText().toString(),
					editText1.getText().toString(),
					editText2.getText().toString(),
					Integer.parseInt(editText3.getText().toString())
			);

			if( warehouseList.size() == 0 ) {
				warehouse.setID(1);
			}
			else{
				warehouse.setID(Integer.parseInt(textView.getText().toString()) == 0 ?  // if true return new ID
						(warehouseList.get(warehouseList.size() - 1).getID() + 1) : // else return selected ID
						Integer.parseInt(textView.getText().toString()));
			}
			// if the itemID isn't 0, that means the item already exists
			if(!(textView.getText().toString().equals("0"))) {
				// Update the admin in the table.
				if(!helper.updateWarehouse(warehouse)) {
					// First remove the admin from the data
					warehouseList.remove(warehouse.getID() - 1);
					// readd it with the new data at the same position
					warehouseList.add(warehouse.getID() - 1 , warehouse);
					// notify the adaptor of the change
					adaptor.notifyDataSetChanged();
					// Tell the user Success!
					Toast.makeText(getApplicationContext(), "warehouse Updated!", Toast.LENGTH_SHORT).show();
				} else {
					// Failed
					Toast.makeText(getApplicationContext(), "warehouse Failed to update!", Toast.LENGTH_SHORT).show();
				} // else
			} // if
			else {
				Log.d("MyApp", "onClick: The admin has been added!");
				// add it to database
				helper.addWarehouse(warehouse.getID(), warehouse.getName(), warehouse.getPassword(), warehouse.getAddress(), warehouse.getItemIDSupplied() );
				// add it to the list!
				warehouseList.add(warehouse);
				// notify the adaptor of the change.
				adaptor.notifyDataSetChanged();
				// tell user it works
				Toast.makeText(getApplicationContext(),"Warehouse Added!", Toast.LENGTH_SHORT).show();
			} // else
			// reset the text and editText elements
			resetText();
		}
	});

				delButton.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v) {
						// check if the user has admin Selected
						if( textView.getText().equals("0")){
							Toast.makeText(getApplicationContext(), "Please Select a Warehouse first to delete!", Toast.LENGTH_SHORT).show();
							return;
						}
						// Check if the List has data
						if( warehouseList.isEmpty() ) {
							Toast.makeText(getApplicationContext(), "The List is Empty! cannot remove what is not there", Toast.LENGTH_SHORT).show();
							return;
						}
						// The id  of the admin
						int ID = Integer.parseInt(textView.getText().toString());
						// Del the warehouse
						helper.delWarehouse(
								ID
						);
						// remove it from the data
						warehouseList.remove(warehousePosition);
						// notify the adaptor of the change
						adaptor.notifyDataSetChanged();
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
