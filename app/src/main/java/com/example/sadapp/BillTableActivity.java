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

public class BillTableActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	TextView textview;
	EditText editText,editText1,editText2,editText3,editText4,editText5;
	SqlHelper helper;
	FloatingActionButton doButton,delButton;
	List<Bills> billsList;
	int billPosition;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bills_table);

		// Connect layout stuff with code
		textview  = findViewById(R.id.textViewBillsTableID);
		editText  = findViewById(R.id.editTextBillsTableCashierID);
		editText1 = findViewById(R.id.editTextBillsTableItemsSold);
		editText2 = findViewById(R.id.editTextBillsTableNumberOfItems);
		editText3 = findViewById(R.id.editTextBillsTableTotal);
		editText4 = findViewById(R.id.editTextBillsTableAmount);
		editText5 = findViewById(R.id.editTextBillsTableChange);
		doButton  = findViewById(R.id.floatingActionButtonBillsDo);
		delButton = findViewById(R.id.floatingActionButtonBillsDel);

		// Layout manager for the recyclerView
		RecyclerView.LayoutManager layoutManager;
		// database manager
		helper = new SqlHelper(getApplicationContext());
		// List of bills.
        billsList = helper.getBills();
        // recyclerView
		recyclerView = findViewById(R.id.recyclerViewBillsTable);
		// make it so it has a fixed size to save some resources
		recyclerView.setHasFixedSize(true);
		// layout manager for the RCviewer
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		// adaptor for the recyclerView
		final BillsAdaptor billsAdaptor = new BillsAdaptor( getApplicationContext(), recyclerView , billsList );
		// set the recyclverView adaptor
		recyclerView.setAdapter(billsAdaptor);

		textview.setText("0");

		MyOnClickListener myOnClickListener = new MyOnClickListener() {
			@Override
			public void MyOnClick(View view, int position, String tag) {
				Log.d("MyApp", "MyOnClick Bills: I AM HERE AND position:" + position);
				textview .setText( billsList.get(position).getID() + ""     	  );
				editText .setText( billsList.get(position).getCashierID() + ""	  );
				editText1.setText( billsList.get(position).getItemsSold()    	  );
				editText2.setText( billsList.get(position).getNumberOfItems() +"" );
				editText3.setText( billsList.get(position).getTotal() + "" 	 	  );
				editText4.setText( billsList.get(position).getAmountPaid() + ""   );
				editText5.setText( billsList.get(position).getChange() + ""       );

				billPosition = position;
			}
		};
		billsAdaptor.setMyOnClickListener(myOnClickListener);

		doButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// Check if views are empty
				if(
						TextUtils.isEmpty( editText.getText().toString() )	||

								TextUtils.isEmpty( editText1.getText().toString() )	||
								TextUtils.isEmpty( editText2.getText().toString() )	||
								TextUtils.isEmpty( editText3.getText().toString() )	||
								TextUtils.isEmpty( editText4.getText().toString() )	||
								TextUtils.isEmpty( editText5.getText().toString() )
				)
				{
					Toast.makeText(getApplicationContext(), "Please make sure to input all the boxes", Toast.LENGTH_SHORT).show();
					return;
				}
				// Create object of bill
				Bills bills = new Bills(
						0,
						Integer.parseInt(editText.getText().toString()), // cashierID
						editText1.getText().toString(),
						Integer.parseInt(editText2.getText().toString()),
						Double.parseDouble(editText3.getText().toString()),
						Double.parseDouble(editText4.getText().toString()),
						Double.parseDouble(editText5.getText().toString())
				);
				// Without this check it would cause a crash, this checks if there is no bills in db, then sets the ID to 1. or to the correct ID
				if( billsList.isEmpty() ) {
					bills.setID(1);
				} else {
					bills.setID(
							Integer.parseInt(textview.getText().toString()) == 0 ?  // if true return new ID
									(billsList.get(billsList.size() - 1).getID() + 1) : // else return selected ID
									Integer.parseInt(textview.getText().toString())
					);
				}

				if(!(textview.getText().toString().equals("0"))) {
					// Update the admin in the table.
					if(!helper.updateBill(bills)) {
						// First remove the admin from the data
						billsList.remove(bills.getID() - 1);
						// readd it with the new data at the same position
						billsList.add(bills.getID() - 1 , bills);
						// notify the adaptor of the change
						billsAdaptor.notifyDataSetChanged();
						// Tell the user Success!
						Toast.makeText(getApplicationContext(), "Admin Updated!", Toast.LENGTH_SHORT).show();
					} else {
						// Failed
						Toast.makeText(getApplicationContext(), "Admin Failed to update!", Toast.LENGTH_SHORT).show();
					} // else
				} // if
				else {
					// add it to database
					helper.addBill(bills.getCashierID(),bills.getItemsSold(),bills.getNumberOfItems(),bills.getTotal(),bills.getAmountPaid(),bills.getChange());
					// add it to the list!
					billsList.add(bills);
					// notify the adaptor of the change.
					billsAdaptor.notifyDataSetChanged();
					// tell user it works
					Toast.makeText(getApplicationContext(),"Bill Added!", Toast.LENGTH_SHORT).show();
					Log.d("MyApp", "onClick: The bill has been added!");
				} // else
				// reset the text and editText elements
				resetText();

			}
		});

		delButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// check if the user has admin Selected
				if( textview.getText().equals("0")){
					Toast.makeText(getApplicationContext(), "Please Select a bill first to delete!", Toast.LENGTH_SHORT).show();
					return;
				}
				// Check if the List has data
				if( billsList.isEmpty() ) {
					Toast.makeText(getApplicationContext(), "The List is Empty! cannot remove what is not there", Toast.LENGTH_SHORT).show();
					return;
				}
				// The id  of the admin
				int ID = Integer.parseInt(textview.getText().toString());
				// Del the Bill
				helper.delBill(
						ID
				);
				// remove it from the data
				billsList.remove(billPosition);
				// notify the adaptor of the change
				billsAdaptor.notifyDataSetChanged();
				resetText();

			}
		});
	}

	void resetText(){
		textview.setText("0");
		editText.setText("");
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");
		editText5.setText("");
	}
}

