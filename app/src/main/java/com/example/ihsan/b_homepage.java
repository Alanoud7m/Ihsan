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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class b_homepage extends AppCompatActivity {
    ArrayList<CharityItem> charityItems ,filteredList;
    RecyclerView recyclerView;
    bItemAdapter bItemAdapter;
    FirebaseFirestore fireStore;
    FirebaseStorage storage;
    FirebaseAuth fAuth;
    MenuItem miLogout;
    Button viewItem,chose,thyab,fasateen,balayz,banateel,hakaeb,maatef,tananeer,womn,men,child;
    String a="";
    int numOf=0;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_homepage);

        recyclerView = findViewById(R.id.recycler_th);

        LinearLayoutManager layoutManager= new LinearLayoutManager(b_homepage.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        charityItems = new ArrayList<CharityItem>();
        filteredList = new ArrayList<CharityItem>();
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
        actionBarDrawerToggle = new ActionBarDrawerToggle(b_homepage.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
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

        viewItem= (Button)findViewById(R.id.viewItem);
        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),b_view_items.class));
            }
        });



        chose= (Button)findViewById(R.id.chose);
        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="احذية";
                Intent intentd = new Intent(chose.getContext(),b_view_items.class);
                intentd.putExtra("احذية",a);
                intentd.setAction("a");
                chose.getContext().startActivity(intentd);
            }
        });



        thyab= (Button)findViewById(R.id.thyab);
        thyab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="ثياب رجالية";
                Intent intentd = new Intent(thyab.getContext(),b_view_items.class);
                intentd.putExtra("ثياب رجالية",a);
                intentd.setAction("b");
                thyab.getContext().startActivity(intentd);
            }
        });



        fasateen= (Button)findViewById(R.id.fasateen);
        fasateen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="فساتين";
                Intent intentd = new Intent(fasateen.getContext(),b_view_items.class);
                intentd.putExtra("فساتين",a);
                intentd.setAction("c");
                fasateen.getContext().startActivity(intentd);
            }
        });



        balayz= (Button)findViewById(R.id.balayz);
        balayz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="بلايز";
                Intent intentd = new Intent(balayz.getContext(),b_view_items.class);
                intentd.putExtra("بلايز",a);
                intentd.setAction("d");
                chose.getContext().startActivity(intentd);
            }
        });



        banateel= (Button)findViewById(R.id.banateel);
        banateel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="بناطيل";
                Intent intentd = new Intent(banateel.getContext(),b_view_items.class);
                intentd.putExtra("بناطيل",a);
                intentd.setAction("e");
                banateel.getContext().startActivity(intentd);
            }
        });



        hakaeb= (Button)findViewById(R.id.hakaeb);
        hakaeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="حقائب";
                Intent intentd = new Intent(hakaeb.getContext(),b_view_items.class);
                intentd.putExtra("حقائب",a);
                intentd.setAction("f");
                hakaeb.getContext().startActivity(intentd);
            }
        });



        maatef= (Button)findViewById(R.id.maatef);
        maatef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="معاطف";
                Intent intentd = new Intent(maatef.getContext(),b_view_items.class);
                intentd.putExtra("معاطف",a);
                intentd.setAction("g");
                maatef.getContext().startActivity(intentd);
            }
        });



        tananeer= (Button)findViewById(R.id.tananeer);
        tananeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="تنانير";
                Intent intentd = new Intent(tananeer.getContext(),b_view_items.class);
                intentd.putExtra("تنانير",a);
                intentd.setAction("h");
                tananeer.getContext().startActivity(intentd);
            }
        });

        womn= (Button)findViewById(R.id.womn);
        womn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="نسائي";
                Intent intentd = new Intent(womn.getContext(),b_view_items.class);
                intentd.putExtra("نسائي",a);
                intentd.setAction("i");
                womn.getContext().startActivity(intentd);
            }
        });



        men= (Button)findViewById(R.id.men);
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="رجالي";
                Intent intentd = new Intent(men.getContext(),b_view_items.class);
                intentd.putExtra("رجالي",a);
                intentd.setAction("j");
                men.getContext().startActivity(intentd);
            }
        });



        child= (Button)findViewById(R.id.child);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a="اطفال";
                Intent intentd = new Intent(child.getContext(),b_view_items.class);
                intentd.putExtra("اطفال",a);
                intentd.setAction("k");
                child.getContext().startActivity(intentd);
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
                int i =0;
                  for(DocumentSnapshot snapshot :queryDocumentSnapshots)
                {
                    if (i==7)
                        break;

                    CharityItem ch1=snapshot.toObject(CharityItem.class);
                    ch1.id=snapshot.getId();
                    charityItems.add(ch1);
                    i++;
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
                                                        AlertDialog.Builder ab = new AlertDialog.Builder(b_homepage.this);

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

                recyclerView.setAdapter(bItemAdapter);
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
                startActivity(new Intent(b_homepage.this,  b_homepage.class));

                break;

            case  R.id.account:
                startActivity(new Intent(b_homepage.this, b_ViewAccount.class));

                break;

            case  R.id.order:
                startActivity(new Intent(b_homepage.this, b_myorders.class));
                break;

            case  R.id.call:
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder=new androidx.appcompat.app.AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("تواصل معنا ... ");
                alertDialogBuilder.setMessage("ihsangpksu@gmail.com");
                // alertDialogBuilder.setCancelable(false);


                alertDialogBuilder.setNegativeButton("أغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(b_homepage.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(b_homepage.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog1=alertDialogBuilder1.create();
                alertDialog1.show();


        }


    }
}