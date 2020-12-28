package com.example.sadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BillsAdaptor extends RecyclerView.Adapter<BillsAdaptor.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    List<Bills> billsList;

    MyOnClickListener myOnClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView billsID, CashierID,ItemsSold, NumberOfItems, Total, AmountPaid, Change;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billsID = itemView.findViewById(R.id.textViewBillsTableBillID);
            CashierID = itemView.findViewById(R.id.textViewBillsTableCashierID);
            ItemsSold = itemView.findViewById(R.id.textViewBillsTableItemsSold);
            NumberOfItems = itemView.findViewById(R.id.textViewBillsTableNumberOfItems);
            Total = itemView.findViewById(R.id.textViewBillsTableTotal);
            AmountPaid = itemView.findViewById(R.id.textViewBillsTableAmountPaid);
            Change = itemView.findViewById(R.id.textViewBillsTableChange);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( myOnClickListener != null){
                myOnClickListener.MyOnClick(itemView, getAdapterPosition(), itemView.getTag().toString() );
            }
        }
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener){
        this.myOnClickListener = myOnClickListener;
    }

    @NonNull
    @Override
    public BillsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bills, parent, false);
        BillsAdaptor.ViewHolder holder = new BillsAdaptor.ViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillsAdaptor.ViewHolder holder, int position) {
        Bills bills = billsList.get(position);
        holder.billsID.setText("BillID:" + bills.getID() );
        holder.CashierID.setText( "CashierID:" + bills.getCashierID() );
        holder.ItemsSold.setText( "ItemsSold:" + bills.getItemsSold() );
        holder.NumberOfItems.setText( "NumberOfItems:" + bills.getNumberOfItems() );
        holder.Total.setText( "Total:" + bills.getTotal() );
        holder.AmountPaid.setText( "AmountPaid:" + bills.getAmountPaid() );
        holder.Change.setText( "Change:" + bills.getChange() );

    }

    public BillsAdaptor(Context context, RecyclerView recyclerView, List<Bills> billsList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.billsList = billsList;
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }

}
