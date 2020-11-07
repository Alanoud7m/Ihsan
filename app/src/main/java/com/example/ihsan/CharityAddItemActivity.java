package com.example.ihsan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CharityAddItemActivity extends AppCompatActivity {

    EditText itemDesc,itemChName,itemCount;
    Spinner itemGender,itemColor,itemSize,itemType;
    FirebaseFirestore fStore;
    FirebaseStorage storage;
    StorageReference sRef;
    FirebaseAuth fAuth;
    Uri imgUri;
    String charityName;
    CharityItem ch;
    String itemId;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_add_item);

        itemCount = (EditText) findViewById(R.id.itemCount);
        itemDesc = (EditText) findViewById(R.id.itemDesc);
        itemChName = (EditText) findViewById(R.id.itemChName);
        itemType = (Spinner) findViewById(R.id.itemType);
        itemGender = (Spinner) findViewById(R.id.itemGender);
        itemColor = (Spinner) findViewById(R.id.itemColor);
        itemSize = (Spinner) findViewById(R.id.itemSize);

        final String[] types = new String[]{"حقائب", "احذية", "معاطف", "تنانير", "بناطيل", "بلايز", "فساتين", "ثياب رجالية", "اخرى"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, types);
        itemType.setAdapter(typeAdapter);

        final String[] genders = new String[]{"رجالي", "نسائي", "اطفال"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, genders);
        itemGender.setAdapter(genderAdapter);

        final String[] colors = new String[]{"أزرق", "أخضر", "أحمر", "أصفر", "أبيض", "أسود", "رمادي", "بنفسجي", "أخرى"};
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, colors);
        itemColor.setAdapter(colorAdapter);

        final String[] sizes = new String[]{"XS", "S", "M", "L", "XL", "2XL", "3XL"};
        final String[] shose_sizes = new String[]{"١٠-١٢", "١٣-١٤", "١٥-١٦",  "١٧-١٨", "١٩-٢٠", "٢١-٢٢", "٢٣-٢٤", "٢٥-٢٦", "٢٧-٢٨", "٢٩-٣٠", "٣١-٣٢", "٣٣-٣٤", "٣٥-٣٦", "٣٧", "٣٨", "٣٩","٤٠", "٤١","٤٢","٤٣", "٤٤", "اخرى"};
        final String[] bags = new String[]{ "حقيبة ظهر", "حقيبة يدوية", "حقيبة سفر", "اخرى"};
        final ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, sizes);
        final ArrayAdapter<String> sizeAdapter_sh = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, shose_sizes);
        final ArrayAdapter<String> bag_size = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, bags);

        itemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position==0) {
                    itemSize.setAdapter(bag_size);
                }
                if (position==1) {
                    itemSize.setAdapter(sizeAdapter_sh);
                }
                else {
                    itemSize.setAdapter(sizeAdapter);
                }
            }

            public void onNothingSelected() {

            }


            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });




        Button cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(CharityAddItemActivity.this);
                builder.setMessage("هل انت متأكد؟").setPositiveButton("نعم", dialogClickListener)
                        .setNegativeButton("لا", dialogClickListener).show();
            }
        });
        Button addItemBtn =findViewById(R.id.addItemBtn);
        Button addImgBtn =findViewById(R.id.addImgBtn);
        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),0);


            }
        });
        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            itemId=intent.getExtras().getString("itemID");

            TextView titleTxt= (TextView)findViewById(R.id.titleTxt);
            titleTxt.setText("تعديل بيانات القطعة");
            addItemBtn.setText("حفظ التعديلات");
             ch=new CharityItem();
            fStore=FirebaseFirestore.getInstance();
            fStore.collection("CharityItems").document(itemId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    ch= documentSnapshot.toObject(CharityItem.class);

                    itemCount.setText(ch.count);
                    itemDesc.setText(ch.description);
                    itemChName.setText(ch.charity);
                    itemType.setSelection(Arrays.asList(types).indexOf(ch.type));
                    itemGender.setSelection(Arrays.asList(genders).indexOf(ch.gender));
                    itemColor.setSelection(Arrays.asList(colors).indexOf(ch.color));
                    itemSize.setSelection(Arrays.asList(sizes).indexOf(ch.size));
                }
            });
            addItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(imgUri!=null)
                    {
                        String randomKey = UUID.randomUUID().toString();
                        storage=FirebaseStorage.getInstance();
                        sRef =storage.getReference().child("CharityItems/"+randomKey);
                        sRef.putFile(imgUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {



                                                ch.count=itemCount.getText().toString();
                                                ch.description=itemDesc.getText().toString();
                                                ch.charity=itemChName.getText().toString();
                                                ch.type=itemType.getSelectedItem().toString();
                                                ch.gender=itemGender.getSelectedItem().toString();
                                                ch.color=itemColor.getSelectedItem().toString();
                                                ch.size=itemSize.getSelectedItem().toString();
                                                ch.image=uri.toString();
                                                fStore=FirebaseFirestore.getInstance();
                                                fStore.collection("CharityItems").document(itemId).set(ch).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getBaseContext(),"تم تعديل القطعة بنجاح",Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(getBaseContext(),NavaigationCharity.class));
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                    }else
                    {

                        ch.count=itemCount.getText().toString();
                        ch.description=itemDesc.getText().toString();
                        ch.charity=itemChName.getText().toString();
                        ch.type=itemType.getSelectedItem().toString();
                        ch.gender=itemGender.getSelectedItem().toString();
                        ch.color=itemColor.getSelectedItem().toString();
                        ch.size=itemSize.getSelectedItem().toString();
                        fStore=FirebaseFirestore.getInstance();
                        fStore.collection("CharityItems").document(itemId).set(ch).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getBaseContext(),"تم تعديل القطعة بنجاح",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getBaseContext(),NavaigationCharity.class));
                            }
                        });
                    }


                }
            });
        }else
        {
            fAuth=FirebaseAuth.getInstance();
            FirebaseUser user= fAuth.getCurrentUser();
            String userId=user.getUid();
            charityName="";
            fStore=FirebaseFirestore.getInstance();
            fStore.collection("Charities").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    charityName= documentSnapshot.get("charityName").toString();

                    itemChName.setText(charityName);

                }
            });



            addItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String randomKey = UUID.randomUUID().toString();
                    storage=FirebaseStorage.getInstance();
                    sRef =storage.getReference().child("CharityItems/"+randomKey);
                    if(itemCount.getText().toString().isEmpty()||itemDesc.getText().toString().isEmpty()||itemChName.getText().toString().isEmpty())
                    {
                        Toast.makeText(getBaseContext(),"الرجاء ملء جميع الحقول",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(imgUri==null)
                    {
                        Toast.makeText(getBaseContext(),"الرجاء اختيار صورة القطعة",Toast.LENGTH_LONG).show();
                        return;
                    }
                    sRef.putFile(imgUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(final Uri uri) {
                                            fStore.collection("utility").document("charityItemCounter").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    counter= Integer.valueOf(documentSnapshot.get("Counter").toString());
                                                    counter++;

                                                    CharityItem charityItem = new CharityItem();
                                                    charityItem.number=String.valueOf(counter);
                                                    charityItem.count=itemCount.getText().toString();
                                                    charityItem.description=itemDesc.getText().toString();
                                                    charityItem.charity=itemChName.getText().toString();
                                                    charityItem.type=itemType.getSelectedItem().toString();
                                                    charityItem.gender=itemGender.getSelectedItem().toString();
                                                    charityItem.color=itemColor.getSelectedItem().toString();
                                                    charityItem.size=itemSize.getSelectedItem().toString();
                                                    charityItem.image=uri.toString();
                                                    fStore=FirebaseFirestore.getInstance();
                                                    fStore.collection("CharityItems").add(charityItem).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Map<String, Object> newCounter = new HashMap<>();
                                                            newCounter.put("Counter",counter);
                                                            fStore.collection("utility").document("charityItemCounter").set(newCounter).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(getBaseContext(),"تم اضافة القطعة بنجاح ورقمها "+counter,Toast.LENGTH_LONG).show();
                                                                    startActivity(new Intent(getBaseContext(),NavaigationCharity.class));
                                                                }
                                                            });

                                                        }
                                                    });

                                                }
                                            });

                                        }
                                    });
                                }
                            });

                }
            });

        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK && data !=null)
        {
            imgUri=data.getData();
        }
    }
}