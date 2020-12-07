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

public class V_ordersList_adapter extends RecyclerView.Adapter<V_ordersList_adapter.orderHolder> {


    private ArrayList<order> orders = new ArrayList<order>();
    OnOrderDetailsBtnClickListener OrderDetailsBtnListener;
    OnChangeStatusBtnClickListener ChangeStatusBtnListener;
    OnCancelBtnClickListener CancelBtnListener;

    public interface OnOrderDetailsBtnClickListener{

        void onOrderDetailsBtnClick(View view, int position);
    }//:)
    public void setOrderDetailsBtnClickListener(com.example.ihsan.V_ordersList_adapter.OnOrderDetailsBtnClickListener orderDetailsBtnListener) {
        this.OrderDetailsBtnListener= orderDetailsBtnListener;
    }//:)

    public interface OnChangeStatusBtnClickListener{
        void onChangeStatusBtnClick(View view, int position);
    }//:)
    public void setChangeStatusBtnClickListener(com.example.ihsan.V_ordersList_adapter.OnChangeStatusBtnClickListener changeStatusBtnListener) {
        this.ChangeStatusBtnListener = changeStatusBtnListener;
    }//:)

    public interface OnCancelBtnClickListener{
        void onCancelBtnClick(View view, int position);
    }//:)
    public void setCancelBtnClickListener(com.example.ihsan.V_ordersList_adapter.OnCancelBtnClickListener cancelBtnListener) {
        this.CancelBtnListener = cancelBtnListener;
    }//:)


    public V_ordersList_adapter(Context mContext, ArrayList<order> orders) {
        this.orders = orders;
    }//End Con :)

    @NonNull
    @Override
    public com.example.ihsan.V_ordersList_adapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.deliveryv_orders_list,parent,false);
        com.example.ihsan.V_ordersList_adapter.orderHolder ordd =new com.example.ihsan.V_ordersList_adapter.orderHolder(view);
        return ordd;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.ihsan.V_ordersList_adapter.orderHolder holder, final int position) {
        holder.OrderNum.setText(String.valueOf(orders.get(position).order_id));
        holder.OrderStatus.setText(orders.get(position).order_status);
        holder.Address.setText(orders.get(position).benef_addres);

        if(orders.get(position).getOrder_status().equals("تم التوصيل"))
            orderHolder.dvis();


            holder.OrderDetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderDetailsBtnListener.onOrderDetailsBtnClick(view, position);

                }
            });

            holder.CancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CancelBtnListener.onCancelBtnClick(view, position);

                }
            });

            holder.ChangeStatusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangeStatusBtnListener.onChangeStatusBtnClick(view, position);

                }
            });
        }



    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class orderHolder extends RecyclerView.ViewHolder
    {
        public TextView OrderNum , Address ,OrderStatus;
        public Button OrderDetailsBtn;
        public static Button CancelBtn;
        public static Button ChangeStatusBtn;

        public orderHolder(@NonNull View itemView) {
            super(itemView);
            OrderNum = (TextView) itemView.findViewById(R.id.OrderNum);
            OrderStatus = (TextView) itemView.findViewById(R.id.OrderStatus);
            Address = (TextView) itemView.findViewById(R.id.Address);
            OrderDetailsBtn= (Button)itemView.findViewById(R.id.OrderDetailsBtn);
            CancelBtn= (Button)itemView.findViewById(R.id.CancelBtn);
            ChangeStatusBtn= (Button) itemView.findViewById (R.id.ChangeStatusBtn);

        }

        static void dvis(){
            CancelBtn.setEnabled(false);
            CancelBtn.setClickable(false);
            ChangeStatusBtn.setEnabled(false);
            ChangeStatusBtn.setClickable(false);
            }
    }
}

