package com.example.sadapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ItemsAdaptor extends RecyclerView.Adapter<ItemsAdaptor.ViewHolder>{

    Context context;
    List<Items> itemsList;
    RecyclerView recyclerView;
    private OnItemClickListener mItemClickListener;
    View view;

    // Returns RecyclerView view
    public View getView() {
        return view;
    }

    // updates the list inside the adaptor
    public void updateList(Items item){
        itemsList.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ItemID;
        TextView ItemName;
        TextView ItemCost;
        TextView InStock;
        TextView Sold;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ItemID = itemView.findViewById(R.id.textViewItemID);
            ItemName = itemView.findViewById(R.id.textViewItemName);
            ItemCost = itemView.findViewById(R.id.textViewItemCost);
            InStock = itemView.findViewById(R.id.textViewItemInStock);
            Sold = itemView.findViewById(R.id.textViewItemSold);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null ){
                mItemClickListener.onItemClick(itemView, getAdapterPosition(), itemView.getTag().toString() );
            }
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, String tag);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ItemsAdaptor(Context context, List<Items> itemsList, RecyclerView recyclerView){
        this.context = context;
        this.itemsList = itemsList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items, parent, false);
        /* view.setOnClickListener(onClickListener); See note at end. */
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        this.view = view;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdaptor.ViewHolder holder, int position) {
        Items item = itemsList.get(position);
        holder.ItemID.setText( "Item ID:" + item.getID() );
        holder.ItemName.setText("Item Name:" + item.getName() );
        holder.ItemCost.setText("Item Cost:" + item.getCost() );
        holder.InStock.setText("In Stock:" + item.getInStock() );
        holder.Sold.setText("Sold :" + item.getSold() );
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    /*
    // meant to be used if we wanted a onClick inside the adaptor, replaced with interface.
    final View.OnClickListener onClickListener = new MyOnClickListener();
    private class MyOnClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            ItemTableActivity i = (ItemTableActivity) context;

            int itemPosition = recyclerView.getChildLayoutPosition(v);

            String item = itemsList.get(itemPosition).getName();
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        } // onclick method

    } // class
     */
}
