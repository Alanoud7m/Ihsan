package com.example.ihsan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class NavaigationActivity extends AppCompatActivity  {
    MenuItem miLogout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    SearchView searchView;

    //for homepage
    ArrayList<CharityItem> charityItems ,filteredList;
    RecyclerView recyclerView;
    CharityItemAdapter charityItemAdapter;
    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navaigation);
        miLogout=findViewById(R.id.logout);

        Toolbar toolbar=findViewById(R.id.b_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation coding start
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(NavaigationActivity.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_lines);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView=findViewById(R.id.navigation_view);
        View navView=navigationView.inflateHeaderView(R.layout.navigation_header);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelected(menuItem);
                return false;
            }
        });

        //for homepage
        Button viewAll = findViewById(R.id.view_all);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), b_view_items.class));
            }
        });
recyclerView=findViewById(R.id.recycler_item);
        charityItems =new ArrayList<CharityItem>();
        filteredList =new ArrayList<CharityItem>();
        getItems();
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
                    }
                    charityItemAdapter.notifyDataSetChanged();
                }else
                {
                    filteredList.clear();
                    getItems();
                }


                return false;
            }
        });
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
                charityItemAdapter = new CharityItemAdapter(getBaseContext(),filteredList);
                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                recyclerView.setAdapter(charityItemAdapter);
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
                startActivity(new Intent(NavaigationActivity.this, b_ViewAccount.class));

                break;

            case  R.id.order:
                break;

            case  R.id.call:
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("تواصل معنا ... ");
                alertDialogBuilder.setMessage("ihsangpksu@gmail.com");
                // alertDialogBuilder.setCancelable(false);


                alertDialogBuilder.setNegativeButton("أغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NavaigationActivity.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();;
                break;

            case  R.id.logout:
                AlertDialog.Builder alertDialogBuilder1=new AlertDialog.Builder(this);
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
                        Toast.makeText(NavaigationActivity.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog1=alertDialogBuilder1.create();
                alertDialog1.show();

        }

    }
     @Override
      public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.shopping_cart,menu);
      return super.onCreateOptionsMenu(menu);
      }

}
