package com.example.attendink;

import android.content.DialogInterface;
import android.os.Bundle;
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

public class Dispteacherforstudent extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mstorage;
    RecyclerView recyclerView123;
    private Dispteacheradapter teachadapter;
    private List<teachermodel> teachermodelList;
    private EditText searchProductEt123;
    private ImageButton filterProductbtn123,backBtn105008;
    private TextView filteredproducttv123;

    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispteacherforstudent);

        //searchProductEt123=(EditText)findViewById(R.id.searchProductEt123);
        filterProductbtn123=(ImageButton) findViewById(R.id.filterProductbtn123);
        filteredproducttv123=(TextView) findViewById(R.id.selected123) ;
        backBtn105008=(ImageButton)findViewById(R.id.backBtn105008);



        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Users").child("Teachers");
        mstorage=FirebaseStorage.getInstance();
        recyclerView123=(RecyclerView) findViewById(R.id.recyclerview12);
        recyclerView123.setHasFixedSize(true);
        recyclerView123.setLayoutManager(new LinearLayoutManager(this));

        teachermodelList=new ArrayList<teachermodel>();
        teachadapter=new Dispteacheradapter(Dispteacherforstudent.this,teachermodelList);
        recyclerView123.setAdapter(teachadapter);

        // loadAllProducts();
        backBtn105008.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  onBackPressed();
//                startActivity(new Intent(Dispteacherforstudent.this,Teacherlogin.class));
//                finish();
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


     filterProductbtn123.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder=new AlertDialog.Builder(Dispteacherforstudent.this);
            builder.setTitle("Choose the Department")
                    .setItems(Constants.classes1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selected= Constants.classes1[which];
                            filteredproducttv123.setText(selected);
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

                        teachadapter=new Dispteacheradapter(Dispteacherforstudent.this,teachermodelList);
                        recyclerView123.setAdapter(teachadapter);
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

                        teachadapter=new Dispteacheradapter(Dispteacherforstudent.this,teachermodelList);
                        recyclerView123.setAdapter(teachadapter);
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