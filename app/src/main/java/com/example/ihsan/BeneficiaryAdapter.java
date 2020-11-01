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

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder> {
    Context mContext;
    ArrayList<Beneficiary> beneficiaries= new ArrayList<>();
    OnVerifyButtonItemClickListener verifyButtonListener;

    public interface OnVerifyButtonItemClickListener {
        void onVerifyIsClick(View button, int position);
    }

    public void setVerifyButtonListener(BeneficiaryAdapter.OnVerifyButtonItemClickListener verifyButtonListener) {
        this.verifyButtonListener= verifyButtonListener;
    }

    public BeneficiaryAdapter(Context mContext, ArrayList<Beneficiary> beneficiaries) {
        this.mContext = mContext;
        this.beneficiaries = beneficiaries;

    }

    @NonNull
    @Override
    public BeneficiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verify_b_list_item,parent,false);
        BeneficiaryViewHolder beneficiaryViewHolder =new BeneficiaryViewHolder(view);
        return beneficiaryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BeneficiaryViewHolder holder, final int position) {

        holder.firstName.setText("اسم المستفيد الأول: "+beneficiaries.get(position).FirstName);
        holder.familyName.setText("عائلة المستفيد: "+beneficiaries.get(position).FamilyName);
        holder.phone.setText("هاتف المستفيد: "+beneficiaries.get(position).phoneNumber);
        holder.verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyButtonListener.onVerifyIsClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beneficiaries.size();
    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {
       public TextView firstName,familyName,phone;
       public Button verifyBtn;
        public BeneficiaryViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = (TextView)itemView.findViewById(R.id.firstName);
            familyName =(TextView) itemView.findViewById(R.id.familyName);
            phone = (TextView)itemView.findViewById(R.id.phone);

            verifyBtn = (Button) itemView.findViewById(R.id.verifyBtn);
        }
    }
}
