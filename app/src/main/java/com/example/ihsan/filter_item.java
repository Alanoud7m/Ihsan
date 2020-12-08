package com.example.ihsan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static android.app.PendingIntent.getActivity;

public class filter_item extends AppCompatActivity  {
    Button addItemBt;
    Spinner  itemGender, itemColor, itemSize, itemType;
    String  s , Gender, Color, Size, Type;
    TextView textView1;
    FirebaseFirestore fireStore;
     String[] charit;
     int x=0;
    ArrayList<charity> ccc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_item);



        itemType = (Spinner) findViewById(R.id.itemType);
        itemGender = (Spinner) findViewById(R.id.itemGender);
        itemColor = (Spinner) findViewById(R.id.itemColor);
        itemSize = (Spinner) findViewById(R.id.itemSize);


        final String[] types = new String[]{"الكل", "حقائب", "احذية", "معاطف", "تنانير", "بناطيل", "بلايز", "فساتين", "ثياب رجالية", "اخرى"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        itemType.setAdapter(typeAdapter);

        final String[] genders = new String[]{"الكل", "رجالي", "نسائي", "اطفال"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        itemGender.setAdapter(genderAdapter);

        final String[] colors = new String[]{"الكل", "أزرق", "أخضر", "أحمر", "أصفر", "أبيض", "أسود", "رمادي", "بنفسجي", "أخرى"};
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        itemColor.setAdapter(colorAdapter);

        final String[] sizes = new String[]{"الكل", "مواليد", "٦ اشهر", "٩ اشهر", "١٢ شهر", "٢", "٤", "٦", "٨", "١٠", "١٢", "١٤", "١٦", "١٨", "٢٠", "XS", "S", "M", "١٧-١٨", "١٩-٢٠", "٢١-٢٢", "٢٣-٢٤", "٢٥-٢٦", "٢٧-٢٨", "٢٩-٣٠", "٣١-٣٢", "٣٣-٣٤", "٣٥-٣٦", "٣٧", "٣٨", "٣٩", "٤٠", "٤١", "٤٢", "٤٣", "٤٤", "اخرى"};
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizes);
        itemSize.setAdapter(sizeAdapter);


        itemGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                x = itemGender.getSelectedItemPosition()  ;
                 Gender= genders[x];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        itemSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                x = itemSize.getSelectedItemPosition()  ;
                 Size= sizes[x];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        itemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                x = itemType.getSelectedItemPosition()  ;
                Type= types[x];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        itemColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                x = itemColor.getSelectedItemPosition()  ;
                Color= colors[x];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });



/*        if(!Gender.equals("الكل")){
            sendme("gend","Gender",Gender);
            }
        if(!Color.equals("الكل")){
            sendme("colo","Color",Color);

        }
        if(!Size.equals("الكل")){
            sendme("size","Size",Size);

        }
        if(!Type.equals("الكل")){
            sendme("typ","Type",Type);

        }*/


        addItemBt= (Button)findViewById(R.id.addItemBt);
        addItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intnent = new Intent(addItemBt.getContext(),b_view_items.class);
                intnent.putExtra("Gender",Gender);
                intnent.putExtra("Color",Color);
                intnent.putExtra("Size",Size);
                intnent.putExtra("Type",Type);
                intnent.setAction("pass");
                startActivity(intnent);




/*
                Intent intnent = new Intent(addItemBt.getContext(),b_view_items.class);
                intnent.putExtra("Taype",Type);
                intnent.setAction("pas");
                startActivity(intnent);
                */
            }
        });


/*
        addItemBt= (Button)findViewById(R.id.addItemBt);
        addItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intented = new Intent(addItemBt.getContext(),b_view_items.class);
                intented.putExtra("Color",Color);
                intented.setAction("pass");
                addItemBt.getContext().startActivity(intented);
            }
        });
*/



    }


/*    void sendme(String pass , String filter, String type){

        Intent intentd = new Intent(getBaseContext(),b_view_items.class);
        intentd.putExtra(type, filter);
        intentd.setAction(pass);
        startActivity(intentd);
    }*/






        /*

       please();
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, charit);
     //   itemChName.setAdapter(nameAdapter);




    }
    void please(){
        fireStore = FirebaseFirestore.getInstance();
        fireStore.collection("Charities").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException f) {
                ccc.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    charity ch1 = snapshot.toObject(charity.class);
                    ch1.userName=snapshot.getId();
                    ccc.add(ch1);
                }


        charit[0] = "الكل";
              final int e =1;

        for(int c=0 ; c<ccc.size();++c) {

            fireStore = FirebaseFirestore.getInstance();
            fireStore.collection("Charities").document(ccc.get(c).userName.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    s = String.valueOf(documentSnapshot.get("charityName"));
                    charit[e++] = s;
                }


            });
        }
            }

        });   */
    }
