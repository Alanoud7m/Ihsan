package com.example.ihsan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class b_view_items extends AppCompatActivity {
    ArrayList<CharityItem> charityItems ,filteredList;
    RecyclerView recyclerView;
    bItemAdapter bItemAdapter;
    FirebaseFirestore fireStore;
    FirebaseStorage storage;
    FirebaseAuth fAuth;
    MenuItem miLogout;
    private TextView noth;
    int numOf=0;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    SearchView searchView;
    String s="", e="" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_view_items);

        noth=(TextView) findViewById(R.id.noth);
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


            searchView =(SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.isEmpty())
                {filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.description.contains(s))
                        {
                            filteredList.add(ch);
                        }
                            if(ch.color.contains(s))
                        {
                            filteredList.add(ch);
                        }
                            if(ch.size.contains(s))
                            {
                                filteredList.add(ch);
                            }
                        if(ch.gender.contains(s))
                        {
                            filteredList.add(ch);
                        }
                        if(ch.charity.contains(s))
                    {
                        filteredList.add(ch);
                    }
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                    bItemAdapter.notifyDataSetChanged();
                }else
                {
                    filteredList.clear();
                    getItems();
                }


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
                startActivity(new Intent(b_view_items.this, b_homepage.class));

                break;

            case  R.id.account:
                startActivity(new Intent(b_view_items.this, b_ViewAccount.class));

                break;

            case  R.id.order:
                startActivity(new Intent(b_view_items.this, b_myorders.class));
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
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException f) {
                charityItems.clear();
                filteredList.clear();
                for(DocumentSnapshot snapshot :queryDocumentSnapshots)
                {   CharityItem ch1=snapshot.toObject(CharityItem.class);
                    ch1.id=snapshot.getId();
                    charityItems.add(ch1);
                }
                filteredList.addAll(charityItems) ;


                final Intent intent = getIntent();
                    String  e = intent.getAction();
                if(intent.getExtras()!=null && e.equals("a")){
                    s = intent.getExtras().getString("احذية");
                        filteredList.clear();

                        for(CharityItem ch :charityItems)
                        {
                            if(ch.type.contains(s))
                            {
                                filteredList.add(ch);
                            }
                        }
                }

                else if(intent.getExtras()!=null && e.equals("b")){
                    s = intent.getExtras().getString("ثياب رجالية");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }


                else if(intent.getExtras()!=null && e.equals("c")){
                    s = intent.getExtras().getString("فساتين");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }


                else  if(intent.getExtras()!=null && e.equals("d")){
                    s = intent.getExtras().getString("بلايز");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }


                else  if(intent.getExtras()!=null && e.equals("e")){
                    s = intent.getExtras().getString("بناطيل");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }


                else  if(intent.getExtras()!=null && e.equals("f")){
                    s = intent.getExtras().getString("حقائب");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }


                else  if(intent.getExtras()!=null && e.equals("g")){
                    s = intent.getExtras().getString("معاطف");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }

                else if(intent.getExtras()!=null && e.equals("h")){
                    s = intent.getExtras().getString("تنانير");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.type.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }

                else  if(intent.getExtras()!=null && e.equals("i")){
                    s = intent.getExtras().getString("نسائي");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.gender.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }


                else  if(intent.getExtras()!=null && e.equals("j")){
                    s = intent.getExtras().getString("رجالي");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.gender.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }

                else if(intent.getExtras()!=null && e.equals("k")){
                    s = intent.getExtras().getString("اطفال");
                    filteredList.clear();

                    for(CharityItem ch :charityItems)
                    {
                        if(ch.gender.contains(s))
                        {
                            filteredList.add(ch);
                        }
                    }
                }


                if(filteredList.size()==0){
                    noth.setVisibility(View.VISIBLE);
                }
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


                bItemAdapter.setAddButtonListener(new bItemAdapter.OnAddButtonItemClickListener() {
                    @Override
                    public void onAddIsClick(View button, final int position) {

                        fAuth = FirebaseAuth.getInstance();
                        final FirebaseUser currentUser = fAuth.getCurrentUser();

                        fireStore.collection("cartList").document(currentUser.getEmail().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                          numOf = Integer.valueOf(documentSnapshot.get("numOfItems").toString());
                                ++numOf;
                                storage = FirebaseStorage.getInstance();
                                // sRef = storage.getReference().child("cartList/" + randomKey);
                                                                     for (int i=0 ; i<charityItems.size();i++){
                                            if(charityItems.get(i).id.equals(charityItems.get(position).id)){
                                                CharityItem ch=charityItems.get(i);
                                                cart ca = new cart();
                                                ca.item_id = ch.id.toString();
                                                ca.setItem_img(ch.getImage());
                                                ca.itemDesc = ch.description.toString();
                                                ca.itemChName = ch.charity.toString();
                                                ca.needCount = "١";
                                                ca.setItemSize(ch.getSize());

                                                fireStore.collection("cartList").document(currentUser.getEmail().toString()).collection("items").add(ca).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Map<String, Object> newCounter = new HashMap<>();
                                                        newCounter.put("numOfItems", numOf);
                                                        fireStore.collection("cartList").document(currentUser.getEmail().toString()).set(newCounter).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                AlertDialog.Builder ab = new AlertDialog.Builder(b_view_items.this);

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

                                    }}
                            }
                        });

                    }


            });

                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                recyclerView.setAdapter(bItemAdapter);
            }
        });

    }



    }
