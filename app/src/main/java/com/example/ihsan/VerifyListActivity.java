package com.example.ihsan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

public class VerifyListActivity extends AppCompatActivity {
FirebaseFirestore fStore;
ArrayList<Beneficiary> beneficiaries;
RecyclerView recyclerView;
BeneficiaryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_list);
        recyclerView = (RecyclerView)findViewById(R.id.verifyRecycler);
        beneficiaries = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();
        fStore.collection("Beneficiaries").whereEqualTo("status","Waiting").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
              beneficiaries.clear();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots)
              {
                  Beneficiary b= documentSnapshot.toObject(Beneficiary.class);
                  b.ID=documentSnapshot.getId();
                  beneficiaries.add(b);
              }
                adapter = new BeneficiaryAdapter(getBaseContext(),beneficiaries);
                adapter.setVerifyButtonListener(new BeneficiaryAdapter.OnVerifyButtonItemClickListener() {
                    @Override
                    public void onVerifyIsClick(View button, int position) {
                        Intent intent = new Intent(getBaseContext(),CharityVerifyBenefActivity.class);
                        intent.putExtra("selectedBenef",beneficiaries.get(position).ID);
                        startActivity(intent);
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView.setAdapter(adapter);
            }
        });
    }
}