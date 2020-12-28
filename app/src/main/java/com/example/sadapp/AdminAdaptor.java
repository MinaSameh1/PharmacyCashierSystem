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


public class AdminAdaptor extends RecyclerView.Adapter<AdminAdaptor.ViewHolder> {

    Context context;
    List<Admin> adminsList;
    RecyclerView recyclerView;
    private OnAdminClickListener mAdminClickListener;
    View view;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView AdminID, AdminName, AdminPassword , AdminAddress, AdminTelephone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AdminID = itemView.findViewById(R.id.textViewAdminID);
            AdminName = itemView.findViewById(R.id.textViewAdminName);
            AdminPassword = itemView.findViewById(R.id.textViewAdminPassword);
            AdminAddress = itemView.findViewById(R.id.textViewAdminAddress);
            AdminTelephone = itemView.findViewById(R.id.textViewAdminTelephone);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( mAdminClickListener != null){
                Log.d("MyApp", "onAdminClick: I AM HERE * 2");
                mAdminClickListener.onAdminClick(itemView, this.getAdapterPosition(), itemView.getTag().toString() );
            }
        }
    }

    public interface OnAdminClickListener {
        void onAdminClick(View view, int position, String tag);
    }

    public void setOnAdminClickListener(final OnAdminClickListener mAdminClickListener) {
        this.mAdminClickListener = mAdminClickListener;
    }

    public AdminAdaptor(Context context, List<Admin> adminsList, RecyclerView recyclerView) {
        this.context = context;
        this.adminsList = adminsList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_card_admin, parent, false);
        AdminAdaptor.ViewHolder holder = new AdminAdaptor.ViewHolder(view);
        this.view = view;
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Admin admin = adminsList.get(position);
        holder.AdminID.setText( "AdminID:" + admin.getID() );
        holder.AdminName.setText("Admin Name:" + admin.getName() );
        holder.AdminPassword.setText("Admin Password:" + admin.getPassword() );
        holder.AdminAddress.setText("Admin Address:" + admin.getAddress() );
        holder.AdminTelephone.setText("Admin Telephone:" + admin.getTelephone() );

    }

    @Override
    public int getItemCount() {
        return adminsList.size();
    }

}
