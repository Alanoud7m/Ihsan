package com.example.ihsan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class v_order_list extends AppCompatActivity {

    final Context context = this;
    private RecyclerView recyclerView;
    FirebaseFirestore fireStore;
    FirebaseAuth fAuth;
    ArrayList<order> orders, filteredList ;
    V_ordersList_adapter V_ordersList_adapter;
    RadioButton NewOrderrB ,PickedrB,DeliverB;
    DeliveryVolunteers d1 ;
    MenuItem miLogout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_order_list);
        recyclerView = findViewById(R.id.ordersRecycler);
        orders = new ArrayList<>();
        filteredList = new ArrayList<>();
        getItems();

        //Navigation coding start
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(v_order_list.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView=findViewById(R.id.navigation_view);
        View navView=navigationView.inflateHeaderView(R.layout.navaigation_header_d);
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
            case  R.id.home:
                startActivity(new Intent(v_order_list.this, NavaigationDelivery.class));
                break;

            case  R.id.account:
                startActivity(new Intent(v_order_list.this, d_ViewAccount.class));

                break;

            case  R.id.order:
                startActivity(new Intent(v_order_list.this, v_order_list.class));
                break;

            case  R.id.call:
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder=new androidx.appcompat.app.AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("تواصل معنا ... ");
                alertDialogBuilder.setMessage("ihsangpksu@gmail.com");
                // alertDialogBuilder.setCancelable(false);


                alertDialogBuilder.setNegativeButton("أغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(v_order_list.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(v_order_list.this,"إلغاء الأمر",Toast.LENGTH_LONG).show();
                    }
                });

                androidx.appcompat.app.AlertDialog alertDialog1=alertDialogBuilder1.create();
                alertDialog1.show();
    }}


    void getItems () {
        fireStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = fAuth.getCurrentUser();
        fireStore.collection("DeliveryVolunteers").document(fAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
@Override
public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
        d1= new DeliveryVolunteers();
        d1.setUserName(documentSnapshot.get("userName").toString() );

        fireStore.collection("Orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                orders.clear();
                filteredList.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    final order ch1 = snapshot.toObject(order.class);
                    if(ch1.getVolenteer_name().equals(d1.userName)){
                    ch1.orderID= snapshot.getId();
                    orders.add(ch1);}
                }
                filteredList.addAll(orders);

                V_ordersList_adapter = new V_ordersList_adapter(getBaseContext(), filteredList);

                V_ordersList_adapter.setOrderDetailsBtnClickListener(new V_ordersList_adapter.OnOrderDetailsBtnClickListener() {

                    @Override
                    public void onOrderDetailsBtnClick(View view, int position) {
                        // custom dialog
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.order_detail);
                        dialog.setTitle("تفاصيل الطلب");

                        // set the custom dialog components - text, image and button
                        TextView text1 = (TextView) dialog.findViewById(R.id.textView24);
                        TextView orderNumtext = (TextView) dialog.findViewById(R.id.orderNumtext);//:)
                        orderNumtext.setText(String.valueOf(filteredList.get(position).order_id));
                        TextView text2 = (TextView) dialog.findViewById(R.id.textView16);
                        TextView text3 = (TextView) dialog.findViewById(R.id.textView10);
                        TextView text4 = (TextView) dialog.findViewById(R.id.textView17);
                        TextView DelName_text = (TextView) dialog.findViewById(R.id.DelName_text);//:)
                        DelName_text.setText(filteredList.get(position).volenteer_name);
                        TextView benNum_text = (TextView) dialog.findViewById(R.id.benNum_text);//:)
                        benNum_text.setText(filteredList.get(position).benef_phone);
                        TextView BenfName_text = (TextView) dialog.findViewById(R.id.BenfName_text);//:)
                        BenfName_text.setText(filteredList.get(position).benef_name);
                        TextView text5 = (TextView) dialog.findViewById(R.id.textView13);
                        TextView text6 = (TextView) dialog.findViewById(R.id.textView12);
                        TextView ChariName_text = (TextView) dialog.findViewById(R.id.ChariName_text);//:)
                        ChariName_text.setText(filteredList.get(position).charity_name);
                        TextView BenfAddress_text = (TextView) dialog.findViewById(R.id.BenfAddress_text);//:)
                        BenfAddress_text.setText(filteredList.get(position).benef_addres);
                        TextView text7 = (TextView) dialog.findViewById(R.id.textView9);
                        TextView orderStatus = (TextView) dialog.findViewById(R.id.orderStatus);//:)
                        orderStatus.setText(filteredList.get(position).order_status);


                        dialog.show();
                    }
                });

                V_ordersList_adapter.setChangeStatusBtnClickListener(new V_ordersList_adapter.OnChangeStatusBtnClickListener() {

                    @Override
                    public void onChangeStatusBtnClick(View view, final int position) {
                       // custom dialog
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.order_status);
                        dialog.setTitle("تغيير حالة الطلب");

                        // set the custom dialog components - text, image and button
                        NewOrderrB = (RadioButton) dialog.findViewById(R.id.NewOrderrB);
                        PickedrB = (RadioButton) dialog.findViewById(R.id.PickedrB);
                        DeliverB = (RadioButton) dialog.findViewById(R.id.DeliverB);
                        Button ok =(Button) dialog.findViewById(R.id.ok);
                        Button no =(Button) dialog.findViewById(R.id.no);

                        ok.setOnClickListener( new View.OnClickListener() {
                            String s =filteredList.get(position).getOrder_status();
                            @Override
                            public void onClick(View view) {
                                if (NewOrderrB.isChecked())
                                    s = "طلب جديد" ;
                                 else if (PickedrB.isChecked())
                                    s =  "تم استلامه من الجمعية";
                                 else if (DeliverB.isChecked()) {
                                    s = "تم التوصيل";
                                }

                                fireStore.collection("Orders").document(filteredList.get(position).getOrderID()).update("order_status",s);
                                V_ordersList_adapter.notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), "تم تحديث حالة الطلب", Toast.LENGTH_LONG).show();
                                dialog.hide();
                            }
                        });
                        no.setOnClickListener( new View.OnClickListener() {

                                                   public void onClick(View view) {
                                                       dialog.hide();
                                                   }
                        });
                        dialog.show();
                    }
                });

                V_ordersList_adapter.setCancelBtnClickListener(new V_ordersList_adapter.OnCancelBtnClickListener() {

                    @Override
                    public void onCancelBtnClick(View view, final int position) {


                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        fireStore.collection("Orders").document(filteredList.get(position).getOrderID()).update("volenteer_name","بانتظار متطوع التوصيل");
                                        fireStore.collection("Orders").document(filteredList.get(position).getOrderID()).update("order_status","طلب جديد");
                                                V_ordersList_adapter.notifyDataSetChanged();
                                                Toast.makeText(getBaseContext(), "تمت ازالة الطلب من قائمة التوصيل الخاصة بك", Toast.LENGTH_LONG).show();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        TextView mTitle = new TextView(v_order_list.this);
                        mTitle.setText("إلغاء عملية التوصيل ؟");
                        mTitle.setGravity(Gravity.RIGHT);
                        mTitle.setPadding(10,40,60,10);
                        mTitle.setTextSize(20);



                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v_order_list.this,R.style.AlertDialog);
                        builder.setCustomTitle(mTitle);
                        builder.setMessage("سيتم إعادة الطلب إلى قائمة الطلبات الجاهزة للتوصيل").setPositiveButton("موافق", dialogClickListener)
                                .setNegativeButton("تراجع", dialogClickListener).show();

                    }
                });
                recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 1));
                recyclerView.setAdapter(V_ordersList_adapter);
            }
        });
}

        });
    }


}


