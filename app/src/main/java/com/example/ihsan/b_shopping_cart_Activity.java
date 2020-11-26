package com.example.ihsan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class b_shopping_cart_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button nxt;
    FirebaseFirestore fireStore;
    FirebaseAuth fAuth;
    ArrayList<cart> items ,filteredList;
    cart_items_adapter cart_items_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_shopping_cart_);


        recyclerView=findViewById(R.id.recycler_shcart);
        items =new ArrayList<cart>();
        filteredList =new ArrayList<cart>();
        getItems();

        nxt = (Button) findViewById(R.id.nextt);
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),order_conform.class));
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
                for(DocumentSnapshot snapshot :queryDocumentSnapshots)
                {   cart ch1=snapshot.toObject(cart.class);
                    ch1.item_id=snapshot.getId();
                    items.add(ch1);
               }
                filteredList.addAll(items) ;

                cart_items_adapter = new cart_items_adapter(getBaseContext(),filteredList);
                cart_items_adapter.setDeleteButtonListener(new cart_items_adapter.OnDeleteButtonItemClickListener() {


                    @Override
                    public void onDeleteIsClick(View view, int position) {


                    }
               });

                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                recyclerView.setAdapter(cart_items_adapter);
            }
        });
    }



}