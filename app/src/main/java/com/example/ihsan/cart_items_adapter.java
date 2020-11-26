package com.example.ihsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class cart_items_adapter extends RecyclerView.Adapter<cart_items_adapter.cartItemsHolder>{
    private Context mContext;
    private ArrayList<cart> items = new ArrayList<>();
    OnDeleteButtonItemClickListener deleteButtonListener;


    public interface OnDeleteButtonItemClickListener {
        void onDeleteIsClick(View view, int position);
    }
    public void setDeleteButtonListener(cart_items_adapter.OnDeleteButtonItemClickListener deleteButtonListener) {
        this.deleteButtonListener= deleteButtonListener;
    }


    public cart_items_adapter(Context mContext, ArrayList<cart> items) {
        this.mContext = mContext;
        this.items = items;

    }



    @NonNull
    @Override
    public cart_items_adapter.cartItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.b_shoppingcart_layout,parent,false);
        cartItemsHolder cartItems =new cartItemsHolder(view);
        return cartItems;
    }

    @Override
    public void onBindViewHolder(@NonNull cartItemsHolder holder, final int position) {
        holder.charityItemTxt.setText(items.get(position).itemDesc);
        holder.charityName.setText(items.get(position).itemChName);
        Picasso.get().load(items.get(position).getItem_img()).into(holder.charityItemImg);
        holder.numOfBas.setText(items.get(position).needCount);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteButtonListener.onDeleteIsClick(view, position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public  class cartItemsHolder extends RecyclerView.ViewHolder
    {
        public ImageView charityItemImg;
        public TextView charityItemTxt , charityName;
        public TextView numOfBas;
        public ImageButton deleteBtn;

        public cartItemsHolder(@NonNull View itemView) {
            super(itemView);
            charityItemImg = (ImageView)itemView.findViewById(R.id.charityItemImg);
            charityItemTxt = (TextView) itemView.findViewById(R.id.charityItemTxt);
            charityName = (TextView) itemView.findViewById(R.id.charityName);
            deleteBtn= (ImageButton)itemView.findViewById(R.id.deleteBtn);
            numOfBas = (TextView)itemView.findViewById(R.id.numOfBas);

        }
    }

}

