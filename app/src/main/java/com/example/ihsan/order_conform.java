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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    RadioButton takeChar;
    private RecyclerView recycler_shcart;
    private Button place;

    CharityItem ch;
    String x;
    ArrayList<cart> items, filteredList;
    items_order_adapter items_order_adapter;

    //for order
    int orderId = 1;
    String numOfItems;
    int n;
    int m;
    String benef_addres = "null",
            benef_name = "null",
            benef_phone = "null",
            charity_name = "null",
            item_desc = "null",
            item_id = "null",
            order_date,
            order_status="طلب جديد",
            numOfBases,
            item_size = "null",
            item_image = "null",
            volenteer_name = "null";
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_conform);

        //for this inteface
        takeChar = (RadioButton) findViewById(R.id.takeChar);
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
                else  if(takeChar.isChecked()) {
                    String cha ="استلام الطلب من الجمعية";
                    benef_addres =cha;
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
                         //   ch1.item_id = snapshot.getId();
                            items.add(ch1);
                            fStore.collection("cartList").document(user.getEmail().toString()).collection("items").document(snapshot.getId()).delete();

                        }
                    }
                });
                           for(int i=0 ; i<items.size(); i++){

                            item_desc = items.get(i).itemDesc.toString();
                            item_id =  items.get(i).getItem_id().toString();
                            item_size =  items.get(i).getItemSize().toString();
                            item_image =  items.get(i).getItem_img();
                            charity_name =  items.get(i).getItemChName();
                            order_date=date;
                            numOfBases =  items.get(i).needCount;
                            n = (int) toInt(numOfBases);


                           editCharityItems(item_id,n);

                            addOrder();

                        }

                Map<String, Object> newCounter = new HashMap<>();
                newCounter.put("numOfItems", 0);
                fStore.collection("cartList").document(user.getEmail().toString()).set(newCounter);
                Toast.makeText(getBaseContext(),  "تم الطلب بنجاح", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getBaseContext(), b_homepage.class));

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
        //            ch1.item_id = snapshot.getId();
                    items.add(ch1);
                }

                numOfItems=toStr(items.size());
                numOfItemm = (TextView) findViewById(R.id.numOfItemm);
                numOfItemm.setText(String.valueOf(numOfItems));


                filteredList.addAll(items);

                items_order_adapter = new items_order_adapter(getBaseContext(), filteredList);

                recycler_shcart.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
                recycler_shcart.setAdapter(items_order_adapter);
            }
        });

    }




    void addOrder() {
        fStore.collection("OrderId").document("order_id").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                orderId = Integer.valueOf(documentSnapshot.get("order_id").toString());

                final order ord = new order(benef_addres, benef_name,benef_phone,
                        charity_name, order_date, order_status, numOfBases, item_desc, item_id, item_size, item_image,
                        "بانتظار متطوع التوصيل.", orderId);

                ++orderId;

                        Map<String, Object> nn = new HashMap<>();
                        nn.put("order_id",  orderId);
                        fStore.collection("OrderId").document("order_id").set(nn);
                fStore = FirebaseFirestore.getInstance();
                fStore.collection("Orders").add(ord);
            }
        });

    }



    void editCharityItems(final String item_id, final int n) {
        ch = new CharityItem();
        fStore = FirebaseFirestore.getInstance();
        fStore.collection("CharityItems").document(item_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                x = String.valueOf(documentSnapshot.get("count"));
     //           int  m = (int)toInt(x);
                int  m =toInt(x);

        if (n > m) {
            makeText(order_conform.this, " الرجاء تعديل عدد القطع من عربة التسوق .. عدد القطع المطلوبة من  أكثر من المتوفرة! ", LENGTH_SHORT).show();
            finish();
        } else if (n <= m) {
            int t = m - n;
            if (t == 0) {
                fStore.collection("CharityItems").document(item_id).delete();

            } else {
                String ss = toStr(t);
                HashMap<String, Object> result = new HashMap<>();
                result.put("count", ss);

             fStore.collection("CharityItems").document(item_id).update(result);
            }
        }
            }
        });
    }




    int toInt(String a) {
        int c=0;
        if (a.equals("١")) { c=1; }
        else if (a.equals("٢")) { c=2;}
        else if (a.equals("٣")) { c=3;}
        else if (a.equals("٤")) { c=4;}
        else if (a.equals("٥")) { c=5;}
        else if (a.equals("٦")) { c=6;}
        else if (a.equals("٧")) { c=7;}
        else if (a.equals("٨")) { c=8;}
        else if (a.equals("٩")) { c=9;}
        else if (a.equals("١٠")) { c=10;}
        return c;
    }


    String toStr(int a) {
        String c="";
        if (a==0) { c="٠"; }
        else if (a==1) { c="١"; }
        else if (a==2) { c="٢"; }
        else if (a==3) { c="٣"; }
        else if (a==4) { c="٤"; }
        else if (a==5) { c="٥"; }
        else if (a==6) { c="٦"; }
        else if (a==7) { c="٧"; }
        else if (a==8) { c="٨"; }
        else if (a==9) { c="٩"; }
        else if (a==10) { c="١٠"; }
        return c;
    }


}
