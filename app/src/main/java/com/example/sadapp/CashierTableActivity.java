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

public class CashierTableActivity extends AppCompatActivity {

	// main RecyclerView
	RecyclerView recyclerView;
	// the layout manager
	RecyclerView.LayoutManager layoutManager;
	// the database Helper
	SqlHelper helper;
	// the adaptor of the RecyclerView
	CashierTableAdaptor cashierTableAdaptor;
	int cashierPosition;
	// the list of the items
	List<Cashier> cashierList = new ArrayList<>();
	// editText and textviews
	TextView textView;
	EditText editText,editText1,editText2,editText3,editText4;
	FloatingActionButton doButton,delButton;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cashier_table);

		doButton  = findViewById(R.id.floatingActionButtonCashierTableDo);
		delButton = findViewById(R.id.floatingActionButtonCashierTableDel);
		textView  = findViewById(R.id.textViewCashierTableCashierID);
		editText  = findViewById(R.id.editTextCashierTableName);
		editText1 = findViewById(R.id.editTextCashierTablePassword);
		editText2 = findViewById(R.id.editTextCashierTableAddress);
		editText3 = findViewById(R.id.editTextCashierTableTelephone);
		editText4 = findViewById(R.id.editTextCashierTableNumberOfOrders);
		recyclerView = findViewById(R.id.recyclerViewCashierTable);

		helper = new SqlHelper(getApplicationContext());

		cashierList = helper.getCashier();
		recyclerView.setHasFixedSize(true);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		cashierTableAdaptor = new CashierTableAdaptor(this, cashierList, recyclerView);
		recyclerView.setAdapter(cashierTableAdaptor);

		textView.setText("0");

		cashierTableAdaptor.setMyOnClickListener(new MyOnClickListener() {
			@Override
			public void MyOnClick(View view, int position, String tag) {
				textView.setText( cashierList.get(position).getID() + "");
				editText.setText( cashierList.get(position).getName() );
				editText1.setText( cashierList.get(position).getPassword() );
				editText2.setText( cashierList.get(position).getAddress() );
				editText3.setText( cashierList.get(position).getTelephone());
				editText4.setText( cashierList.get(position).getNumberOfOrders() + "");

				cashierPosition = position;
			}
		});

		doButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(
						TextUtils.isEmpty(editText.getText().toString()) ||
								TextUtils.isEmpty(editText1.getText().toString()) ||
								TextUtils.isEmpty(editText2.getText().toString()) ||
								TextUtils.isEmpty(editText3.getText().toString()) ||
								TextUtils.isEmpty(editText4.getText().toString())
				)
				{
					Toast.makeText(getApplicationContext(), "Please make sure to input all the boxes", Toast.LENGTH_SHORT).show();
					return;
				}

				Cashier cashier = new Cashier (
						(
								Integer.parseInt(textView.getText().toString()) == 0 ?  // if true return new ID
										(cashierList.get(cashierList.size() - 1).getID() + 1) : // else return selected ID
										Integer.parseInt(textView.getText().toString())) ,
						editText.getText().toString(),
						editText1.getText().toString(),
						editText2.getText().toString(),
						editText3.getText().toString(),
						Integer.parseInt(editText4.getText().toString())
				);

				if(!(textView.getText().toString().equals("0"))) {
					// Update the cashier in the table.
					if(!helper.updateCashier(cashier)) {
						cashierList.remove(cashier.getID() - 1);
						cashierList.add(cashier.getID() - 1 , cashier);
						cashierTableAdaptor.notifyDataSetChanged();
						Toast.makeText(getApplicationContext(), "Cashier Updated!", Toast.LENGTH_SHORT).show();
					} else {
						// Failed
						Toast.makeText(getApplicationContext(), "Cashier Failed to update!", Toast.LENGTH_SHORT).show();
					} // else
				} // if
				else {
					Log.d("MyApp", "onClick: The item has been added!");
					// add it to database
					helper.addCashier(
							cashier.getID(),
							cashier.getName(),
							cashier.getPassword(),
							cashier.getAddress(),
							cashier.getTelephone(),
							cashier.getNumberOfOrders()
					);
					// add it to the list!
					cashierList.add(cashier);
					cashierTableAdaptor.notifyDataSetChanged();
					// tell user it works
					Toast.makeText(getApplicationContext(),"Cashier Added!", Toast.LENGTH_SHORT).show();
				} // else
                resetText();
			}
		});

		delButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if( textView.getText().equals("0")){
					Toast.makeText(getApplicationContext(), "Please Select an item first to delete!", Toast.LENGTH_SHORT).show();
					return;
				}
				// Check if the List has data
				if( cashierList.isEmpty() ) {
					Toast.makeText(getApplicationContext(), "The List is Empty! cannot remove what is not there", Toast.LENGTH_SHORT).show();
					return;
				}
				// The id  of the item
				int ID = Integer.parseInt(textView.getText().toString());
				// Delete
				helper.delCashier(
						ID
				);
				// remove it from the data
				cashierList.remove(cashierPosition);
				// notify the adaptor of the change
				cashierTableAdaptor.notifyDataSetChanged();
				resetText();
			}
		});
	}

	void resetText(){
		textView.setText("0");
		editText.setText("");
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");
	}

}