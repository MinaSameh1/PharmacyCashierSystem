package com.example.sadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CashierTableAdaptor extends RecyclerView.Adapter<CashierTableAdaptor.ViewHolder> {

    Context context;
    List<Cashier> cashierList;
    RecyclerView recyclerView;
    private MyOnClickListener myOnClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView CashierID, CashierName, CashierPassword, CashierAddress, CashierTelephone, CashierNumberOfOrders;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CashierID = itemView.findViewById(R.id.textviewCashierTableID);
            CashierName = itemView.findViewById(R.id.textViewCashierTableName);
            CashierPassword = itemView.findViewById(R.id.textViewCashierTablePassword);
            CashierAddress = itemView.findViewById(R.id.textViewCashierTableAddress);
            CashierTelephone = itemView.findViewById(R.id.textViewCashierTableTelephone);
            CashierNumberOfOrders = itemView.findViewById(R.id.textViewCashierTableNumberOfOrders);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( myOnClickListener != null){
                myOnClickListener.MyOnClick( itemView, getAdapterPosition(), itemView.getTag().toString() );
            }
        }
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public CashierTableAdaptor(Context context, List<Cashier> cashierList, RecyclerView recyclerView) {
        this.context = context;
        this.cashierList = cashierList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cashier_table_item, parent, false);
        CashierTableAdaptor.ViewHolder holder = new CashierTableAdaptor.ViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.CashierID.setText( "CashierID:" + cashierList.get(position).getID()  );
        holder.CashierName.setText( "CashierName:" + cashierList.get(position).getName() );
        holder.CashierPassword.setText( "CashierPassword:" + cashierList.get(position).getPassword() );
        holder.CashierAddress.setText( "CashierAddress:" + cashierList.get(position).getAddress() );
        holder.CashierTelephone.setText( "CashierTelephone:" + cashierList.get(position).getTelephone() );
        holder.CashierNumberOfOrders.setText( "CashierNumberOfOrders:" + cashierList.get(position).getNumberOfOrders() );
    }

    @Override
    public int getItemCount() {
        return cashierList.size();
    }

}
