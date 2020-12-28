package com.example.sadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WarehouseTableAdaptor extends RecyclerView.Adapter<WarehouseTableAdaptor.ViewHolder> {
    Context context;
    List<Warehouse> warehousesList;
    RecyclerView recyclerView;
    MyOnClickListener myOnClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView warehouseID,warehouseName,warehousePassword,warehouseAddress,warehouseItemIDSupplied;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            warehouseID = itemView.findViewById(R.id.textViewWarehouseTableWarehouseID);
            warehouseName = itemView.findViewById(R.id.textViewWarehouseTableName);
            warehousePassword = itemView.findViewById(R.id.textViewWarehouseTablePassword);
            warehouseAddress = itemView.findViewById(R.id.textViewWarehouseTableAddress);
            warehouseItemIDSupplied = itemView.findViewById(R.id.textViewWarehouseTableItemSupplied);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( myOnClickListener != null) {
                myOnClickListener.MyOnClick(itemView, getAdapterPosition(), itemView.getTag().toString());
            }
        }
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public WarehouseTableAdaptor(Context context, List<Warehouse> warehousesList, RecyclerView recyclerView) {
        this.context = context;
        this.warehousesList = warehousesList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.warehouse_table_item, parent, false);
        WarehouseTableAdaptor.ViewHolder holder = new WarehouseTableAdaptor.ViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Warehouse warehouse = warehousesList.get(position);
        holder.warehouseID.setText("WarehouseID:" + warehouse.getID());
        holder.warehouseName.setText("WarehouseName:" + warehouse.getName());
        holder.warehousePassword.setText("WarehousePass:" + warehouse.getPassword());
        holder.warehouseAddress.setText("WarehouseAddress:" + warehouse.getAddress());
        holder.warehouseItemIDSupplied.setText("WarehouseItemIDSupplied:" + warehouse.getItemIDSupplied());
    }

    @Override
    public int getItemCount() {
        return warehousesList.size();
    }

}
