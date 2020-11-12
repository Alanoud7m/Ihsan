package com.example.ihsan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class b_view_items extends AppCompatActivity {
    ArrayList<CharityItem> charityItems ,filteredList;
    RecyclerView recyclerView;
    bItemAdapter bItemAdapter;
    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_view_items);

        recyclerView = findViewById(R.id.recycler_tow);
        charityItems = new ArrayList<CharityItem>();
        filteredList = new ArrayList<CharityItem>();
        Button viewAll = findViewById(R.id.view_all);

        getItems();
    }

    void getItems()
    {
        fireStore = FirebaseFirestore.getInstance();
        fireStore.collection("CharityItems").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                charityItems.clear();
                filteredList.clear();
                for(DocumentSnapshot snapshot :queryDocumentSnapshots)
                {   CharityItem ch1=snapshot.toObject(CharityItem.class);
                    ch1.id=snapshot.getId();
                    charityItems.add(ch1);
                }
                filteredList.addAll(charityItems) ;

                bItemAdapter = new bItemAdapter(getBaseContext(),filteredList);
                bItemAdapter.setDetailseButtonListener(new bItemAdapter.OnDetailseButtonItemClickListener() {
                    @Override
                    public void onDetailseIsClick(View button, int position) {

                        Intent intentd = new Intent(getBaseContext(),CharityAddItemActivity.class);
                        intentd.putExtra("itemID",charityItems.get(position).id);
                        intentd.setAction("v");
                        startActivity(intentd);
                    }
                });

                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                recyclerView.setAdapter(bItemAdapter);
            }
        });

    }
}