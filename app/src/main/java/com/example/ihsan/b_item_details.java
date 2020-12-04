package com.example.ihsan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class b_item_details extends AppCompatActivity {
    EditText itemDesc,itemChName;
    TextView titemCount, titemGender,titemColor,titemSize,titemType;
    ImageView Imgl;
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    StorageReference sRef;
    FirebaseAuth fAuth;
    int numOf = 0;
    ArrayList<cart> items;

    static Spinner needCount;
    CharityItem ch;
    String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_item_details);

        needCount = (Spinner) findViewById(R.id.needCount);
        Imgl = (ImageView) findViewById(R.id.Imgl);
        ImageButton add_to_shooping = (ImageButton) findViewById(R.id.add_to_shooping);
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = fAuth.getCurrentUser();
        itemDesc = (EditText) findViewById(R.id.itemDesc);
        itemChName = (EditText) findViewById(R.id.itemChName);
        titemCount = (TextView) findViewById(R.id.titemCount);
        titemGender = (TextView) findViewById(R.id.titemGender);
        titemColor = (TextView) findViewById(R.id.titemColor);
        titemSize = (TextView) findViewById(R.id.titemSize);
        titemType = (TextView) findViewById(R.id.titemType);

        final ArrayList<String> counts = new ArrayList<String>();
        counts.add("١");
        ArrayAdapter<String> countAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, counts);
        needCount.setAdapter(countAdapter);

    final Intent intent = getIntent();
    String e = intent.getAction();
        if(intent.getExtras()!=null && e.equals("v")) {

            itemId = intent.getExtras().getString("itemID");
            TextView titleTxt = (TextView) findViewById(R.id.titleTxt);
            titleTxt.setText("عرض تفاصيل القطعة");
            Imgl = (ImageView) findViewById(R.id.Imgl);

            itemDesc.setEnabled(false);
            itemChName.setEnabled(false);
            ch = new CharityItem();

            fStore = FirebaseFirestore.getInstance();
            fStore.collection("CharityItems").document(itemId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    ch = documentSnapshot.toObject(CharityItem.class);

                    assert ch != null;
                    itemId = itemId;
                    titemCount.setText(ch.getCount());
                    titemGender.setText(ch.getGender());
                    titemColor.setText(ch.getColor());
                    titemSize.setText(ch.getSize());
                    titemType.setText(ch.getType());
                    itemDesc.setText(ch.description);
                    itemChName.setText(ch.charity);
                    Picasso.get().load(ch.getImage()).into(Imgl);

                    //need count
                    String a = (String) ch.getCount();
                    if (a.equals("٢")) {
                        counts.add("٢");

                    } else if (a.equals("٣")) {
                        counts.add("٢");
                        counts.add("٣");
                    } else if (a.equals("٤")) {
                        counts.add("٢");
                        counts.add("٣");
                        counts.add("٤");
                    } else if (a.equals("٥")) {
                        counts.add("٢");
                        counts.add("٣");
                        counts.add("٤");
                        counts.add("٥");
                    } else if (a.equals("٦")) {
                        counts.add("٢");
                        counts.add("٣");
                        counts.add("٤");
                        counts.add("٥");
                        counts.add("٦");
                    } else if (a.equals("٧")) {
                        counts.add("٢");
                        counts.add("٣");
                        counts.add("٤");
                        counts.add("٥");
                        counts.add("٦");
                        counts.add("٧");
                    } else if (a.equals("٨")) {
                        counts.add("٢");
                        counts.add("٣");
                        counts.add("٤");
                        counts.add("٥");
                        counts.add("٦");
                        counts.add("٧");
                        counts.add("٨");
                    } else if (a.equals("٩")) {
                        counts.add("٢");
                        counts.add("٣");
                        counts.add("٤");
                        counts.add("٥");
                        counts.add("٦");
                        counts.add("٧");
                        counts.add("٨");
                        counts.add("٩");
                    } else if (a.equals("١٠")) {
                        counts.add("٢");
                        counts.add("٣");
                        counts.add("٤");
                        counts.add("٥");
                        counts.add("٦");
                        counts.add("٧");
                        counts.add("٨");
                        counts.add("٩");
                        counts.add("١٠");
                    }


                }

            });
        }


        add_to_shooping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("cartList").document(currentUser.getEmail().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        numOf = Integer.valueOf(documentSnapshot.get("numOfItems").toString());
                        ++numOf;
                storage = FirebaseStorage.getInstance();
                // sRef = storage.getReference().child("cartList/" + randomKey);
                cart ca = new cart();
                ca.item_id = itemId;
                        ca.setItem_img(ch.getImage());
                        ca.itemDesc = itemDesc.getText().toString();
                ca.itemChName = itemChName.getText().toString();
                ca.needCount = needCount.getSelectedItem().toString();
                ca.setItemSize(ch.getSize());
                fStore.collection("cartList").document(currentUser.getEmail().toString()).collection("items").add(ca).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Map<String, Object> newCounter = new HashMap<>();
                        newCounter.put("numOfItems", numOf);
                        fStore.collection("cartList").document(currentUser.getEmail().toString()).set(newCounter).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder ab= new AlertDialog.Builder(b_item_details.this);

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
                        });

                    }
                });


                    }
                });


                    }
                });

            }


            }
