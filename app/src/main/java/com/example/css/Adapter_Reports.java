package com.example.css;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Reports extends RecyclerView.Adapter<Adapter_Reports.ViewHolder> {
    private List<com.example.css.Model_Reports> model_Reports;

    // RecyclerView recyclerView;
    public Adapter_Reports(List<com.example.css.Model_Reports> model_Reports) {
        this.model_Reports = model_Reports;
    }
    @NonNull
    @Override
    public com.example.css.Adapter_Reports.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_notifications, parent, false);
        com.example.css.Adapter_Reports.ViewHolder viewHolder = new com.example.css.Adapter_Reports.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.css.Adapter_Reports.ViewHolder holder, int position) {
        final com.example.css.Model_Reports current_Reports = model_Reports.get(position);
        holder.txt_date.setText(model_Reports.get(position).getDate());
        //holder.txt_name.setText(model_Reports.get(position).getName());
        holder.txt_notifi.setText(model_Reports.get(position).getName() + " "+ model_Reports.get(position).getHeading());

        holder.layout_Withdraw_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityNotificationDetails.class);
                intent.putExtra("id", current_Reports.getNotificatioID().toString().trim());
                view.getContext().startActivity(intent);
                //Toast.makeText(view.getContext(),"click on item: "+current_Reports.getAmount().toString().trim(), Toast.LENGTH_LONG).show();
                //Toast.makeText(view.getContext(),"click on item: "+current_investor.get_name_Investor(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return model_Reports.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public TextView txt_date,txt_name,txt_notifi;
        public LinearLayout layout_Withdraw_req;
        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_date = (TextView) itemView.findViewById(R.id.txt_date);
//            this.txt_name = (TextView) itemView.findViewById(R.id.txt_name_reports);
            this.txt_notifi = (TextView) itemView.findViewById(R.id.txt_notifi);
            layout_Withdraw_req = (LinearLayout)itemView.findViewById(R.id.layout_reports);
        }

    }
}
