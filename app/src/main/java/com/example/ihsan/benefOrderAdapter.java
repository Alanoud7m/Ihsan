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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class benefOrderAdapter extends RecyclerView.Adapter<benefOrderAdapter.orderHolder>{
    private Context mContext;
    private ArrayList<order> orders = new ArrayList<>();
    benefOrderAdapter.OnDetailsClickListener detailsButtonListener;
    benefOrderAdapter.OnExitClickListener exitButtonListener;
    benefOrderAdapter.OnEvalClickListener evalButtonListener;


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


    public interface OnEvalClickListener {
        void onEvalClick(View view, int position);
    }
    public void setEvalClickListener(benefOrderAdapter.OnEvalClickListener evalButtonListener) {
        this.evalButtonListener= evalButtonListener;
    }


    public benefOrderAdapter(Context mContext, ArrayList<order> orders) {
        this.mContext = mContext;
        this.orders = orders;

    }




    public benefOrderAdapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.benef_order_layout,parent,false);
        benefOrderAdapter.orderHolder orddd =new benefOrderAdapter.orderHolder(view);
        return orddd;
    }

    @Override
    public void onBindViewHolder(@NonNull orderHolder holder, final int position) {
        holder.ON.setText(orders.get(position).order_id);
        holder.OS.setText(orders.get(position).order_status);
        holder.NOB.setText(orders.get(position).numOfBases);

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

        holder.DD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evalButtonListener.onEvalClick(view,position );
            }
        });

    }



    @Override
    public int getItemCount() {
        return orders.size();
    }

    public  class orderHolder extends RecyclerView.ViewHolder
    {
        public Button DO,EO,DD ;
        public TextView ON,OS,NOB;

        public orderHolder(@NonNull View itemView) {
            super(itemView);
            DO = (Button) itemView.findViewById(R.id.DO);
            EO = (Button) itemView.findViewById(R.id.EO);
            DD = (Button) itemView.findViewById(R.id.DD);

            ON = (TextView) itemView.findViewById(R.id.ON);
            OS = (TextView) itemView.findViewById(R.id.OS);
            NOB = (TextView) itemView.findViewById(R.id.NOB);


        }
    }

}

