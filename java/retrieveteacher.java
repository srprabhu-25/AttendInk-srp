package com.example.attendink;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class retrieveteacher extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mstorage;
    RecyclerView recyclerView;
    private teacheradapter teachadapter;
    private List<teachermodel> teachermodelList;
    private EditText searchProductEt;
    private ImageButton filterProductbtn;
    private TextView filteredproducttv;

    private static long back_pressed;
    private ImageButton backBtn1050010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieveteacher);

        searchProductEt=(EditText)findViewById(R.id.searchProductEt);
        filterProductbtn=(ImageButton) findViewById(R.id.filterProductbtn);
        filteredproducttv=(TextView) findViewById(R.id.selected) ;
        backBtn1050010=(ImageButton)findViewById(R.id.backBtn1050010);


        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Users").child("Teachers");
        mstorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        teachermodelList=new ArrayList<teachermodel>();
        teachadapter=new teacheradapter(retrieveteacher.this,teachermodelList);
        recyclerView.setAdapter(teachadapter);

       // loadAllProducts();

        backBtn1050010.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchProductEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    teachadapter.getFilter().filter(s);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                teachermodel teachmodel=snapshot.getValue(teachermodel.class);
                teachermodelList.add(teachmodel);
                teachadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        filterProductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(retrieveteacher.this);
                builder.setTitle("Choose the Department")
                        .setItems(Constants.classes1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               String selected= Constants.classes1[which];
                                filteredproducttv.setText(selected);
                               if(selected.equals("All"))
                               {
                                   loadAllProducts();
                               }
                               else {
                                   loadFilteredProducts(selected);

                               }

                            }
                        })
                        .show();


            }
        });

    }

    private void loadAllProducts()
    {
        //.child("name").child("section")child("tid").child("name").
        teachermodelList=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child("Teachers");
        reference.orderByChild("section")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        teachermodelList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){

                                teachermodel teachmodel = ds.getValue(teachermodel.class);
                                teachermodelList.add(teachmodel);


                        }

                        teachadapter=new teacheradapter(retrieveteacher.this,teachermodelList);
                        recyclerView.setAdapter(teachadapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }





    private void loadFilteredProducts(String selected)
    {
        teachermodelList=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child("Teachers");
        reference.orderByChild("section")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        teachermodelList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String productCategory=""+ds.child("section").getValue();

                            if(selected.equalsIgnoreCase(productCategory))
                            {
                                teachermodel teachmodel = ds.getValue(teachermodel.class);
                                teachermodelList.add(teachmodel);

                            }
                        }

                        teachadapter=new teacheradapter(retrieveteacher.this,teachermodelList);
                        recyclerView.setAdapter(teachadapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

//    public void onBackPressed()
//    {
//        if (back_pressed + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            finish();
//            ActivityCompat.finishAffinity(this);
//            System.exit(0);
//        } else {
//            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
//            back_pressed = System.currentTimeMillis();
//        }
//    }
}