package com.example.ihsan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class b_shopping_cart_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button nxt;
    private TextView noth;

    FirebaseFirestore fireStore;
    FirebaseAuth fAuth;
    ArrayList<cart> items, filteredList;
    cart_items_adapter cart_items_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_shopping_cart_);


        recyclerView = findViewById(R.id.recycler_shcart);
        items = new ArrayList<cart>();
        filteredList = new ArrayList<cart>();
        getItems();
        noth=(TextView) findViewById(R.id.noth);
        nxt = (Button) findViewById(R.id.nextt);
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), order_conform.class));
            }
        });


    }


    void getItems() {
        fireStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = fAuth.getCurrentUser();
        fireStore.collection("cartList").document(currentUser.getEmail().toString()).collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                items.clear();
                filteredList.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    cart ch1 = snapshot.toObject(cart.class);
                    ch1.item_id = snapshot.getId();
                    items.add(ch1);
                }
                if(items.size()==0){
                    nxt.setVisibility(View.GONE);
                    noth.setVisibility(View.VISIBLE);
                }
                filteredList.addAll(items);
                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
                recyclerView.setAdapter(cart_items_adapter);


            }
        });

        cart_items_adapter = new cart_items_adapter(getBaseContext(), filteredList);
        cart_items_adapter.setDeleteButtonListener(new cart_items_adapter.OnDeleteButtonItemClickListener() {


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

        });

    }
}