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
public class items_order_adapter extends RecyclerView.Adapter<items_order_adapter.cartItemsHolder>{
    private Context mContext;
    private ArrayList<cart> items = new ArrayList<>();
    private ArrayList<CharityItem> charityItems = new ArrayList<>();
    CharityItemAdapter.OnDeleteButtonItemClickListener deleteButtonListener;


    public interface OnDeleteButtonItemClickListener {
        void onDeleteIsClick(View button, int position);
    }

    public void setDeleteButtonListener(CharityItemAdapter.OnDeleteButtonItemClickListener deleteButtonListener) {
        this.deleteButtonListener= deleteButtonListener;
    }

    public items_order_adapter(Context mContext, ArrayList<cart> items) {
        this.mContext = mContext;
        this.items = items;

    }



    @NonNull
    @Override
    public items_order_adapter.cartItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_order_layout,parent,false);
        cartItemsHolder cartItems =new cartItemsHolder(view);
        return cartItems;
    }

    @Override
    public void onBindViewHolder(@NonNull cartItemsHolder holder, final int position) {
        holder.charityItemTxt.setText(items.get(position).itemDesc);
        holder.charityName.setText(items.get(position).itemChName);
        Picasso.get().load(items.get(position).getItem_img()).into(holder.charityItemImg);
        holder.numOfBas.setText(items.get(position).needCount);
        holder.ssize.setText(items.get(position).getItemSize());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public  class cartItemsHolder extends RecyclerView.ViewHolder
    {
        public ImageView charityItemImg;
        public TextView charityItemTxt , charityName,ssize;
        public TextView numOfBas;

        public cartItemsHolder(@NonNull View itemView) {
            super(itemView);
            charityItemImg = (ImageView)itemView.findViewById(R.id.charityItemImg);
            charityItemTxt = (TextView) itemView.findViewById(R.id.charityItemTxt);
            charityName = (TextView) itemView.findViewById(R.id.charityName);
            numOfBas = (TextView)itemView.findViewById(R.id.numOfBas);
            ssize = (TextView) itemView.findViewById(R.id.ssize);
        }
    }

}
