package com.example.ihsan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.protobuf.StringValue;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static android.widget.Toast.*;

public class order_conform extends AppCompatActivity {

    // for DB
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseStorage storage;


    //for this inteface
    TextView benefAdress , ssize;
    TextView numOfItemm;
    RadioButton oldAddress;
    RadioButton newAddress;
    private RecyclerView recycler_shcart;
    private Button place;


    ArrayList<cart> items, filteredList;
    items_order_adapter items_order_adapter;

    //for order
    int orderId = 1
         ,   numOfItems=0;
    String benef_addres = "null",
            benef_name = "null",
            benef_phone = "null",
            charity_name = "null",
            item_desc = "null",
            item_id = "null",
            item_size = "null",
            item_image = "null",
            volenteer_name = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_conform);

        //for this inteface
        oldAddress = (RadioButton) findViewById(R.id.oldAddress);
        newAddress = (RadioButton) findViewById(R.id.newAddress);
        final EditText otherAddress = (EditText) findViewById(R.id.otherAddress);

        recycler_shcart = (RecyclerView) findViewById(R.id.recycl);

        items = new ArrayList<cart>();
        filteredList = new ArrayList<cart>();
        place = (Button) findViewById(R.id.place);


        //address
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();



        getItems();


        // save order to database
        String userId = user.getUid();
        fStore.collection("Beneficiaries").document(userId.toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                benef_name = (documentSnapshot.get("firstName").toString() + " " + documentSnapshot.get("familyName").toString());
                benef_phone = (documentSnapshot.get("phoneNumber").toString());
                benef_addres = documentSnapshot.get("homeAddress").toString();

                benefAdress = (TextView) findViewById(R.id.benefAdress);
                benefAdress.setText(benef_addres);
            }

        });




        place.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {


                if (newAddress.isChecked()) {
                    String s =(String) otherAddress.getText().toString();
                    if (s.matches("")) {
                        makeText(order_conform.this, "لم يتم اضافة عنوان جديد ! الرجاء كتابة عنوانك الجديد", LENGTH_SHORT).show();
                        return;
                    }
                    else
                    benef_addres = s;
                }


                fStore = FirebaseFirestore.getInstance();
                final FirebaseUser user = fAuth.getCurrentUser();
                fStore.collection("cartList").document(user.getEmail().toString()).collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        items.clear();
                        filteredList.clear();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            cart ch1 = snapshot.toObject(cart.class);
                            ch1.item_id = snapshot.getId();
                            items.add(ch1);


                            item_desc = ch1.itemDesc.toString();
                            item_id = ch1.getItem_id();
                            item_size = ch1.getItemSize().toString();
                            item_image = ch1.getItem_img();
                            charity_name = ch1.getItemChName();
                             String idd=String.valueOf(orderId);
                            orderId++;

                            storage = FirebaseStorage.getInstance();
                            order ord = new order(benef_addres, benef_phone, benef_name,
                                    charity_name,item_desc,item_id,item_size,item_image,
                                    idd, "بانتظار متطوع التوصيل.");

                            fStore = FirebaseFirestore.getInstance();
                            fStore.collection("Orders").add(ord);
                      /*  .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    AlertDialog.Builder ab = new AlertDialog.Builder(order_conform.this);

                                    ab.setMessage("تم اضافة القطعة إلى سلة التسوق ، هل تريد انهاء الطلب أو متابعة التسوق ؟");
                                    ab.setCancelable(false);
                                    ab.setPositiveButton("انهاء الطلب", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            startActivity(new Intent(getBaseContext(), b_shopping_cart_Activity.class));
                                        }
                                    });

                                    ab.setNegativeButton(" متابعة التسوق", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            startActivity(new Intent(getBaseContext(), b_view_items.class));
                                        }

                                    });
                                    AlertDialog alert = ab.create();
                                    alert.show();
                                }
                            });*/
                            // }

                        }}
                });


           }
        });

    }



    void getItems() {
        fStore = FirebaseFirestore.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        fStore.collection("cartList").document(user.getEmail().toString()).collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                items.clear();
                filteredList.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    cart ch1 = snapshot.toObject(cart.class);
                    ch1.item_id = snapshot.getId();
                    items.add(ch1);
                }

                numOfItems=items.size();
                numOfItemm = (TextView) findViewById(R.id.numOfItemm);
                numOfItemm.setText(String.valueOf(numOfItems));


                filteredList.addAll(items);

                items_order_adapter = new items_order_adapter(getBaseContext(), filteredList);

                recycler_shcart.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
                recycler_shcart.setAdapter(items_order_adapter);
            }
        });

    }
}