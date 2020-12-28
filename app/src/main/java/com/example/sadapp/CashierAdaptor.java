package com.example.sadapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class CashierAdaptor extends RecyclerView.Adapter<CashierAdaptor.ViewHolder> {

    Context context;
    List<Cashier> cashierList;
    RecyclerView recyclerView;
    private MyOnClickListener myOnClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( myOnClickListener  != null){
                Log.d("MyApp", "onAdminClick: I AM HERE * 2");
                myOnClickListener.MyOnClick(itemView, this.getAdapterPosition(), itemView.getTag().toString() );
            }
        }
    }


    public void setMyOnClickListener(final MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public CashierAdaptor(Context context, List<Cashier> cashierList, RecyclerView recyclerView) {
        this.context = context;
        this.cashierList = cashierList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_card_admin, parent, false);
        CashierAdaptor.ViewHolder holder = new CashierAdaptor.ViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return cashierList.size();
    }

}
