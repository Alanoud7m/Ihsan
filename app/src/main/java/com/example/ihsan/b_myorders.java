package com.example.ihsan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class b_myorders extends AppCompatActivity {
    private RecyclerView recyclerView;
    final Context context=this;
    private TextView noth;
    String benef_phone;
    FirebaseFirestore fireStore;
    FirebaseAuth fAuth;
    ArrayList<order> orders, filteredList;
    benefOrderAdapter benefOrderAdapter;
    String bb=""  ,aa="" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_myorders);


        recyclerView = findViewById(R.id.recycler_ord);
        orders = new ArrayList<order>();
        filteredList = new ArrayList<order>();
        getItems();

/*        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
       fireStore.collection("Beneficiaries").document(userId.toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                benef_phone = (documentSnapshot.get("phoneNumber").toString());

            }

        });*/

        noth=(TextView) findViewById(R.id.noth);

    }


    void getItems() {
        fireStore = FirebaseFirestore.getInstance();
   //     final FirebaseUser user = fAuth.getCurrentUser();
        fireStore.collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                orders.clear();
                filteredList.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    order oo = snapshot.toObject(order.class);
                     oo.orderID = snapshot.getId();
                 //   orders.add(oo);
                    aa=oo.benef_phone;
                 //   bb=benef_phone;
                    if (aa!=benef_phone) {
                        filteredList.add(oo);
                }
                }



/*       for (int i = 0; i < orders.size(); i++) {
            aa=orders.get(i).benef_phone.toString();
            bb=benef_phone.toString();
            if (aa.equals(bb)) {
                filteredList.add(orders.get(i));
            }
        }*/
        if(filteredList.size()==0){
            noth.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
        recyclerView.setAdapter(benefOrderAdapter);
            }
        });
      //detail
        benefOrderAdapter = new benefOrderAdapter(getBaseContext(), filteredList);
          benefOrderAdapter.setDetailsClickListener(new benefOrderAdapter.OnDetailsClickListener() {
             @Override
            public void OnDetailsClick(View view, int position) {
                        Intent intentd = new Intent(getBaseContext(),order_details.class);
                        intentd.putExtra("orderID",filteredList.get(position).orderID);
                        intentd.setAction("a");
                        startActivity(intentd);
        }

        });


           //exit
        benefOrderAdapter.setExitClickListener(new benefOrderAdapter.OnExitClickListener() {

            @Override
            public void OnExitClick(View view, final int position) {
                if (filteredList.get(position).getOrder_status().equals("طلب جديد")) {


                    AlertDialog.Builder alertDialogBuilder1=new AlertDialog.Builder(b_myorders.this);
                    alertDialogBuilder1.setTitle("رسالة !");
                    alertDialogBuilder1.setMessage("هل أنت متأكد أنك تريد حذف الطلب ؟");
                    alertDialogBuilder1.setCancelable(false);

                    alertDialogBuilder1.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            CharityItem ch = new CharityItem();
                            fireStore = FirebaseFirestore.getInstance();
                            fireStore.collection("CharityItems").document(filteredList.get(position).item_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String x = String.valueOf(documentSnapshot.get("count"));
                                    //           int  m = (int)toInt(x);
                                    int  m =toInt(x) , n=toInt(filteredList.get(position).numOfBases);
                                    int t = m+n;

                                    String ss = toStr(t);
                                    HashMap<String, Object> result = new HashMap<>();
                                    result.put("count", ss);

                                    fireStore.collection("CharityItems").document(filteredList.get(position).item_id).update(result);
                                }


                            });


                            fireStore = FirebaseFirestore.getInstance();
                            fireStore.collection("Orders").document(filteredList.get(position).orderID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(getBaseContext(), "تم الحذف بنجاح", Toast.LENGTH_LONG).show();

                                }

                            });}
                    });

                    alertDialogBuilder1.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(b_myorders.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
                        }
                    });

                    AlertDialog alertDialog1=alertDialogBuilder1.create();
                    alertDialog1.show();


                }

            }
        });


        //track
        benefOrderAdapter.setEvalClickListener(new benefOrderAdapter.OnEvalClickListener() {

            @Override
            public void onEvalClick(View view, int position) {
                   // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.track_order);
                dialog.setTitle("تتبع الطلب");

                // set the custom dialog components - text, image and button
                TextView orderN = (TextView) dialog.findViewById(R.id.orderN);
                orderN.setText(String.valueOf(filteredList.get(position).order_id));


                ImageButton imageButtonPicked = (ImageButton) dialog.findViewById(R.id.imageButtonPicked);//:)
                TextView Pickedtext = (TextView) dialog.findViewById(R.id.Pickedtext);

                ImageButton imageButtonNew = (ImageButton) dialog.findViewById(R.id.imageButtonNew);//:)
                TextView NewOtext = (TextView) dialog.findViewById(R.id.NewOtext);

                ImageButton imageButtonDone = (ImageButton) dialog.findViewById(R.id.imageButtonDone);//:)
                TextView Donetext = (TextView) dialog.findViewById(R.id.Donetext);

                if (filteredList.get(position).order_status.equals("طلب جديد")) {
                    imageButtonNew.setColorFilter(Color.argb(255, 215, 171, 132));
                    NewOtext.setTextColor(Color.parseColor("#D3A882"));


                } else if (filteredList.get(position).order_status.equals("تم استلامه من الجمعية")) {
                    imageButtonNew.setColorFilter(Color.argb(255, 215, 171, 132));
                    imageButtonPicked.setColorFilter(Color.argb(255, 215, 171, 132));
                    NewOtext.setTextColor(Color.parseColor("#D3A882"));
                    Pickedtext.setTextColor(Color.parseColor("#D3A882"));


                } else if (filteredList.get(position).order_status.equals("تم التوصيل")) {
                    Pickedtext.setTextColor(Color.parseColor("#D3A882"));
                    NewOtext.setTextColor(Color.parseColor("#D3A882"));
                    Donetext.setTextColor(Color.parseColor("#D3A882"));
                    imageButtonNew.setColorFilter(Color.argb(255, 215, 171, 132));
                    imageButtonDone.setColorFilter(Color.argb(255, 215, 171, 132));
                    imageButtonPicked.setColorFilter(Color.argb(255, 215, 171, 132));
                }
                dialog.show();
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

