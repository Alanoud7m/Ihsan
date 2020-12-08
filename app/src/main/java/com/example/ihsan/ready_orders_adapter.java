package com.example.ihsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ready_orders_adapter extends RecyclerView.Adapter<ready_orders_adapter.ordersHolder>{


    private ArrayList<order> items = new ArrayList<>();
    OnOrderDetailsBtnClickListener OrderDetailsBtnListener;
    OnDeliverOrNotBtnClickListener DeliverOrNotBtnListener;

    public interface OnOrderDetailsBtnClickListener{
        void onOrderDetailsBtnClick(View view, int position);
    }//:)
    public void setOrderDetailsBtnClickListener(com.example.ihsan.ready_orders_adapter.OnOrderDetailsBtnClickListener orderDetailsBtnListener) {
        this.OrderDetailsBtnListener= orderDetailsBtnListener;
    }//:)

    public interface OnDeliverOrNotBtnClickListener{
        void onDeliverOrNotBtnClick(View view, int position);
    }//:)
    public void setDeliverOrNotBtnClickListener(com.example.ihsan.ready_orders_adapter.OnDeliverOrNotBtnClickListener deliverOrNotBtnListener) {
        this.DeliverOrNotBtnListener= deliverOrNotBtnListener;
    }//:)


    public ready_orders_adapter(Context mContext, ArrayList<order> items) {
        this.items = items;
    }//End Con :)



    @NonNull
    @Override
    public com.example.ihsan.ready_orders_adapter.ordersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ready_for_delivery_orders,parent,false);
        com.example.ihsan.ready_orders_adapter.ordersHolder orders =new com.example.ihsan.ready_orders_adapter.ordersHolder(view);
        return orders;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.ihsan.ready_orders_adapter.ordersHolder holder, final int position) {
        holder.OrderNum.setText(String.valueOf(items.get(position).order_id));
        holder.Address.setText(items.get(position).benef_addres);

        holder.OrderDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailsBtnListener.onOrderDetailsBtnClick(view, position);

            }
        });
        if(items.get(position).getVolenteer_name().equals("بانتظار متطوع التوصيل.") || items.get(position).getVolenteer_name().equals("بانتظار متطوع التوصيل") ){


        holder.DeliverOrNotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliverOrNotBtnListener.onDeliverOrNotBtnClick(view, position);

            }
        });}

        else ordersHolder.dvis();

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ordersHolder extends RecyclerView.ViewHolder
    {
        public TextView OrderNum , Address;
        public Button OrderDetailsBtn;
        public static Button DeliverOrNotBtn;

        public ordersHolder(@NonNull View itemView) {
            super(itemView);
            OrderNum = (TextView) itemView.findViewById(R.id.OrderNum);
            Address = (TextView) itemView.findViewById(R.id.Address);
            OrderDetailsBtn= (Button)itemView.findViewById(R.id.OrderDetailsBtn);
            DeliverOrNotBtn= (Button) itemView.findViewById (R.id.DeliverOrNotBtn);

        }
        static void dvis(){ DeliverOrNotBtn.setEnabled(false);
            DeliverOrNotBtn.setClickable(false);}
    }

}


