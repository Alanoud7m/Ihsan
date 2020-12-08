package com.example.ihsan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CharityVerifyBenefActivity extends AppCompatActivity {
FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_verify_benef);
        final String selectedBenef = getIntent().getExtras().getString("selectedBenef");
        fStore = FirebaseFirestore.getInstance();
        final TextView firstName,familyName,phone,idNumber,charity,homeAddress;
        Button approveBtn,refuseBtn;

        firstName = (TextView)findViewById(R.id.firstName);
        familyName = (TextView)findViewById(R.id.familyName);
        phone = (TextView)findViewById(R.id.phone);
        idNumber = (TextView)findViewById(R.id.idNumber);
        charity = (TextView)findViewById(R.id.charity);
        homeAddress = (TextView)findViewById(R.id.homeAddress);

        approveBtn = (Button)findViewById(R.id.approveBtn);
        refuseBtn = (Button)findViewById(R.id.refuseBtn);

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("Beneficiaries").document(selectedBenef).update("status","Approved").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //////////////////////////////////////////////////////////////////////////////////////////
                        fAuth = FirebaseAuth.getInstance();
                        final FirebaseUser currentUser = fAuth.getCurrentUser();
                        Map<String, Object> newCounter = new HashMap<>();
                        newCounter.put("numOfItems", 0);
                        fStore.collection("cartList").document(currentUser.getEmail().toString()).set(newCounter);
                        fStore.collection("cartList").document(currentUser.getEmail().toString()).collection("items").document().delete();

                        Toast.makeText(getBaseContext(),"تم قبول المستفيد بنجاح",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });

        refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("Beneficiaries").document(selectedBenef).update("status","Refused").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(),"تم رفض المستفيد بنجاح",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });


        fStore.collection("Beneficiaries").document(selectedBenef).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Beneficiary b =documentSnapshot.toObject(Beneficiary.class);
                firstName.setText("اسم المستفيد الأول : "+b.FirstName);
                familyName.setText("عائلة المستفيد : "+b.FamilyName);
                phone.setText("هاتف المستفيد : "+b.phoneNumber);
                idNumber.setText("رقم الهوية : "+b.IdNumber);
                charity.setText("جمعية المستفيد : "+b.CharityName);
                homeAddress.setText("عنوان المنزل : "+b.HomeAddress);
            }
        });

    }
}