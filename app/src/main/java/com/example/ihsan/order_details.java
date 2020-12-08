package com.example.ihsan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class order_details extends AppCompatActivity {
    EditText itemDesc,itemChName;
    Button track;
    TextView titemCount,titemSize,volnte,orddat,ordStat;
    ImageView Imgl;
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    FirebaseAuth fAuth;
    ArrayList<order> orders;

    order oo;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Imgl = (ImageView) findViewById(R.id.Imgl);
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = fAuth.getCurrentUser();
        itemDesc = (EditText) findViewById(R.id.itemDesc);
        itemChName = (EditText) findViewById(R.id.itemChName);
        titemCount = (TextView) findViewById(R.id.titemCount);
        titemSize = (TextView) findViewById(R.id.titemSize);
        volnte = (TextView) findViewById(R.id.volnte);
        orddat = (TextView) findViewById(R.id.orddat);
        ordStat = (TextView) findViewById(R.id.ordStat);



        final Intent intent = getIntent();
        String e = intent.getAction();
        if(intent.getExtras()!=null && e.equals("a")) {

            orderId = intent.getExtras().getString("orderID");
            TextView titleTxt = (TextView) findViewById(R.id.titleTxt);
            titleTxt.setText("عرض تفاصيل الطلب");
            Imgl = (ImageView) findViewById(R.id.Imgl);

            itemDesc.setEnabled(false);
            itemChName.setEnabled(false);
            oo = new order();

            fStore = FirebaseFirestore.getInstance();
            fStore.collection("Orders").document(orderId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    oo = documentSnapshot.toObject(order.class);


                    assert oo != null;
                    orderId = orderId;
                    titemCount.setText(oo.numOfBases);
                    titemSize.setText(oo.item_size);
                    itemDesc.setText(oo.getItem_desc());
                    itemChName.setText(oo.charity_name);
                    Picasso.get().load(oo.getItem_image()).into(Imgl);
                    volnte.setText(oo.getVolenteer_name());
                    orddat.setText(oo.getOrder_date());
                    ordStat.setText(oo.getOrder_status());


                }

            });



        }
    }
}

