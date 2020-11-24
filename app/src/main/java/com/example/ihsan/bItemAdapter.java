package com.example.ihsan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class bItemAdapter extends RecyclerView.Adapter<bItemAdapter.bItemViewHolder>  {
    private Context mContext;
    private ArrayList<CharityItem> charityItems = new ArrayList<>();
    OnDetailseButtonItemClickListener detailseButtonListener;

    public interface OnDetailseButtonItemClickListener {
        void onDetailseIsClick(View button, int position);
    }
    public void setDetailseButtonListener(bItemAdapter.OnDetailseButtonItemClickListener detailseButtonListener) {
        this.detailseButtonListener= detailseButtonListener;
    }

    public bItemAdapter(Context mContext, ArrayList<CharityItem> charityItems) {
        this.mContext = mContext;
        this.charityItems = charityItems;

    }


    @NonNull
    @Override
    public bItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.b_item_layout,parent,false);
        bItemViewHolder bItemViewHolder =new bItemViewHolder(view);
        return bItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull bItemViewHolder holder, final int position) {
        holder.charityItemTxt.setText(charityItems.get(position).description);
        Picasso.get().load(charityItems.get(position).getImage()).into(holder.charityItemImg);
        holder.view_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailseButtonListener.onDetailseIsClick(view, position);
            }
        });

    }


    @Override
            public int getItemCount() {
                return charityItems.size();
            }

            public class bItemViewHolder extends RecyclerView.ViewHolder {
                public Button  view_detail_btn;
                public ImageView charityItemImg;
                public TextView charityItemTxt;

                public bItemViewHolder(@NonNull View itemView) {
                    super(itemView);
                    charityItemImg = (ImageView) itemView.findViewById(R.id.charityItemImg);
                    charityItemTxt = (TextView) itemView.findViewById(R.id.charityItemTxt);
                    view_detail_btn = (Button) itemView.findViewById(R.id.view_detail_btn);
                }
            }


}
