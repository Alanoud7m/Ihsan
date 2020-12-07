package com.example.ihsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.protobuf.StringValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class benefOrderAdapter extends RecyclerView.Adapter<benefOrderAdapter.orderHolder>{
    private Context mContext;
    private ArrayList<order> orders = new ArrayList<>();
    benefOrderAdapter.OnDetailsClickListener detailsButtonListener;
    benefOrderAdapter.OnExitClickListener exitButtonListener;
    benefOrderAdapter.OnTrackClickListener trackClickListener;

    public interface OnDetailsClickListener {
        void OnDetailsClick(View view, int position);
    }
    public void setDetailsClickListener(benefOrderAdapter.OnDetailsClickListener detailsButtonListener) {
        this.detailsButtonListener= detailsButtonListener;
    }


    public interface OnExitClickListener {
        void OnExitClick(View view, int position);
    }
    public void setExitClickListener(benefOrderAdapter.OnExitClickListener exitButtonListener) {
        this.exitButtonListener= exitButtonListener;
    }



    public interface OnTrackClickListener {
        void onTrackClick(View view, int position);
    }
    public void setTrackClickListener(benefOrderAdapter.OnTrackClickListener trackClickListener) {
        this.trackClickListener= trackClickListener;
    }


    public benefOrderAdapter(Context mContext, ArrayList<order> orders) {
        this.mContext = mContext;
        this.orders = orders;

    }




    public benefOrderAdapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.benef_order_layout,parent,false);
        orderHolder orddd =new orderHolder(view);
        return orddd;
    }

    @Override
    public void onBindViewHolder(@NonNull orderHolder holder, final int position) {
        String s = String.valueOf(orders.get(position).order_id);
        holder.ON.setText(s);
        holder.OS.setText(orders.get(position).order_status);
        holder.NOB.setText(orders.get(position).numOfBases);

        if( orders.get(position).getOrder_status().equals("طلب جديد")){
            orderHolder.evis();
        }


        holder.DO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsButtonListener.OnDetailsClick(view,position );
            }
        });

        holder.EO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitButtonListener.OnExitClick(view,position );
            }
        });

        holder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackClickListener.onTrackClick(view,position );
            }
        });

    }



    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class orderHolder extends RecyclerView.ViewHolder
    {
        public Button DO , track;
        public static Button EO;
        public TextView ON,OS,NOB;

        public orderHolder(@NonNull View itemView) {
            super(itemView);
            DO = (Button) itemView.findViewById(R.id.DO);
            EO = (Button) itemView.findViewById(R.id.EO);
            track = (Button) itemView.findViewById(R.id.track);

            ON = (TextView) itemView.findViewById(R.id.ON);
            OS = (TextView) itemView.findViewById(R.id.OS);
            NOB = (TextView) itemView.findViewById(R.id.NOB);


        }
       static void evis(){
            EO.setVisibility(View.VISIBLE);
    }

    }

}

