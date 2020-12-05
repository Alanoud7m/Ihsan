package com.example.ihsan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class b_myorders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView noth;
    TextView benefAdress , ssize;


    String benef_phone = "null";
    FirebaseFirestore fireStore;
    FirebaseAuth fAuth;
    ArrayList<order> orders, filteredList;
    benefOrderAdapter benefOrderAdapter;
    String bb=""  ,aa="" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_myorders);

/*
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
       fireStore.collection("Beneficiaries").document(userId.toString().).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                benef_phone = (documentSnapshot.get("phoneNumber").toString());

            }

        });*/
        recyclerView = findViewById(R.id.recycler_ord);
        orders = new ArrayList<order>();
        filteredList = new ArrayList<order>();
        getItems();
        noth=(TextView) findViewById(R.id.noth);

    }


    void getItems() {
        fireStore = FirebaseFirestore.getInstance();
   //     final FirebaseUser user = fAuth.getCurrentUser();
        fireStore.collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                orders.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    order oo = snapshot.toObject(order.class);
                    // oo.order_id = snapshot.getId();
                    orders.add(oo);
                }


/*        for (int i = 0; i < orders.size(); i++) {
            aa=orders.get(i).benef_phone.toString();
            bb=benef_phone.toString();
            if (aa.equals(bb)) {
                filteredList.clear();
                filteredList.add(orders.get(i));
            }
        }*/
        if(orders.size()==0){
            noth.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
        recyclerView.setAdapter(benefOrderAdapter);
            }
        });
      //detail
        benefOrderAdapter = new benefOrderAdapter(getBaseContext(), orders);
/*          benefOrderAdapter.setDetailsClickListener(new benefOrderAdapter.OnDetailsClickListener() {
             @Override
            public void OnDetailsClick(View view, int position) {

        }

        });
            //exit
        benefOrderAdapter.setExitClickListener(new benefOrderAdapter.OnExitClickListener() {

            @Override
            public void OnExitClick(View view, int position) {

            }
        });
          *//*     @Override
                public void OnDetailsClick(View view, int position) {

                }

              @Override
            public void onDeleteIsClick(View view, final int position) {

                fireStore = FirebaseFirestore.getInstance();
                fireStore.collection("cartList").document(currentUser.getEmail().toString()).collection("items").document(filteredList.get(position).item_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        fireStore.collection("cartList").document(currentUser.getEmail().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                int n = Integer.valueOf(documentSnapshot.get("numOfItems").toString());
                                n--;
                                Map<String, Object> nn = new HashMap<>();
                                nn.put("numOfItems", n);
                                fireStore.collection("cartList").document(currentUser.getEmail().toString()).set(nn);
                            }
                        });
                        Toast.makeText(getBaseContext(), "تم الحذف بنجاح", Toast.LENGTH_LONG).show();




                    }

                });
            }


*//*




        //evaluate
        benefOrderAdapter.setEvalClickListener(new benefOrderAdapter.OnEvalClickListener() {

            @Override
            public void onEvalClick(View view, int position) {

            }

        });*/



}
    }

