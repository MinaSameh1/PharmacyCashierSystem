package com.example.sadapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_dashboard);
	}


	public void editAdminTable(View view) {
		Log.d("AdminDashBoard", "editAdminTable: Works");
		Intent intent = new Intent(AdminActivity.this, AdminTableActivity.class);
		startActivity(intent);
	}

	public void editCashierTable(View view) {
		Log.d("AdminDashBoard", "editCashierTable: Works");
		Intent intent = new Intent(AdminActivity.this, CashierTableActivity.class);
		startActivity(intent);
	}

	public void editItemTable(View view) {
		Log.d("AdminDashBoard", "editItemTable: Works");
		Intent intent = new Intent(AdminActivity.this, ItemTableActivity.class);
		startActivity(intent);
	}

	public void editBillsTable(View view) {
		Log.d("AdminDashBoard", "editBillsTable: Works");
		Intent intent = new Intent(AdminActivity.this, BillTableActivity.class);
		startActivity(intent);
	}

	public void editWarehouseTable(View view) {
		Log.d("AdminDashBoard", "editWarehouseTable: Works");
		Intent intent = new Intent(AdminActivity.this, WarehouseTableActivity.class);
		startActivity(intent);
	}
}
