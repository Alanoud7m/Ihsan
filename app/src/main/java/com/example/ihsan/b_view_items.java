package com.example.ihsan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class b_view_items extends AppCompatActivity {
    ArrayList<CharityItem> charityItems ,filteredList;
    RecyclerView recyclerView;
    bItemAdapter bItemAdapter;
    FirebaseFirestore fireStore;
    MenuItem miLogout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_view_items);

        recyclerView = findViewById(R.id.recycler_tow);
        charityItems = new ArrayList<CharityItem>();
        filteredList = new ArrayList<CharityItem>();

        //shopping cart icon
        ImageButton shoppingcart = findViewById(R.id.shoppingcart);
        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),b_shopping_cart_Activity.class));
            }
        });

        getItems();
        miLogout = findViewById(R.id.logout);
        Toolbar toolbar = findViewById(R.id.bb_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Navigation coding start
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(b_view_items.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_lines);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelected(menuItem);
                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void UserMenuSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case  R.id.address:
                break;

            case  R.id.account:
                startActivity(new Intent(b_view_items.this, b_ViewAccount.class));

                break;

            case  R.id.order:
                break;

            case  R.id.call:
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder=new androidx.appcompat.app.AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("تواصل معنا ... ");
                alertDialogBuilder.setMessage("ihsangpksu@gmail.com");
                // alertDialogBuilder.setCancelable(false);


                alertDialogBuilder.setNegativeButton("أغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(b_view_items.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
                    }
                });

                androidx.appcompat.app.AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();;
                break;

            case  R.id.logout:
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder1=new androidx.appcompat.app.AlertDialog.Builder(this);
                alertDialogBuilder1.setTitle("رسالة !");
                alertDialogBuilder1.setMessage("هل أنت متأكد أنك تريد تسجيل الخروج ؟");
                alertDialogBuilder1.setCancelable(false);

                alertDialogBuilder1.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                alertDialogBuilder1.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(b_view_items.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog1=alertDialogBuilder1.create();
                alertDialog1.show();


        }


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

                        Intent intentd = new Intent(getBaseContext(),b_item_details.class);
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
