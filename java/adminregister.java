package com.example.attendink;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class adminregister extends AppCompatActivity {

    private ImageView teachimg1212;
    private EditText tname1212, tphone1212, temail1212, taddress1212, tdept1212, tage1212, date1212, tpassword1212, cpassword1212;
    private RadioGroup rgruop1212;
    private RadioButton rbmale1212, rbfemale1212;
    private Button btnsubmit1212, btncancel1212;
    private String ttname1212, ttphone1212, ttaddress1212, ttemail1212, ttdept1212, ttage1212, tdate1212, ttpassword1212, ttgender1212, ttcpassword1212;
    private FirebaseDatabase admindatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    ImageButton backBtn105005;

    private static long back_pressed;


    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;

    private String[] cameraPermission;
    private String[] storagepermission;
    private Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminregister);

        teachimg1212 = (ImageView) findViewById(R.id.imageView1212);
        tname1212 = (EditText) findViewById(R.id.newteacher1212);
        tphone1212 = (EditText) findViewById(R.id.phoneno1212);
        taddress1212 = (EditText) findViewById(R.id.address1212);
       // tdept1212 = (EditText) findViewById(R.id.department1212);
        tage1212 = (EditText) findViewById(R.id.age1212);
        temail1212 = (EditText) findViewById(R.id.emailid1212);
        tpassword1212 = (EditText) findViewById(R.id.password1212);
        cpassword1212= (EditText) findViewById(R.id.cpassword1212);
        date1212 = (EditText) findViewById(R.id.date1212);
        btnsubmit1212 = (Button) findViewById(R.id.submit1212);
        btncancel1212 = (Button) findViewById(R.id.cancel1212);
        rgruop1212 = (RadioGroup) findViewById(R.id.rgroup1212);
        rbmale1212 = (RadioButton) findViewById(R.id.male1212);
        backBtn105005=(ImageButton)findViewById(R.id.backBtn105005);


        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);

        btncancel1212.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backpressed();
            }
        });

        btnsubmit1212.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputdata();

//                startActivity(new Intent(adminregister.this,Adminlogin.class));
//                finish();
            }
        });
        teachimg1212.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        backBtn105005.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void backpressed() {

        tname1212.setText("");
        tphone1212.setText("");
        taddress1212.setText("");
        temail1212.setText("");
        //tdept1212.setText("");
        date1212.setText("");
        tage1212.setText("");
        tpassword1212.setText("");
        cpassword1212.setText("");
    }

    private void inputdata() {

        ttname1212 = tname1212.getText().toString().trim();
        ttphone1212 = tphone1212.getText().toString().trim();
        ttaddress1212 = taddress1212.getText().toString().trim();
        ttemail1212 = temail1212.getText().toString().trim();
        //ttdept1212 = tdept1212.getText().toString().trim();
        tdate1212 = date1212.getText().toString().trim();
        ttage1212 = tage1212.getText().toString().trim();
        ttpassword1212 = tpassword1212.getText().toString().trim();
        ttcpassword1212 = cpassword1212.getText().toString().trim();
        if (findViewById(rgruop1212.getCheckedRadioButtonId()) == findViewById(R.id.male1212)) {
            ttgender1212 = "Male";
        } else {
            ttgender1212 = "Female";
        }

        if (TextUtils.isEmpty(ttname1212)) {
            Toast.makeText(this, "Enter the name....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttphone1212)) {
            Toast.makeText(this, "Enter the phone number....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ttphone1212.matches("[0-9]{10}")) {
            tphone1212.requestFocus();
            tphone1212.setError("Enter the phone number correctly...");
            return;
        }
        if (TextUtils.isEmpty(ttaddress1212)) {
            Toast.makeText(this, "Enter the Address ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttemail1212)) {
            Toast.makeText(this, "Enter the email address ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(ttemail1212).matches()) {
            Toast.makeText(this, "Invalid email pattern  ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tdate1212)) {
            Toast.makeText(this, "Enter the Student id ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttage1212)) {
            Toast.makeText(this, "Enter the age ....", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(ttdept1212)) {
//            Toast.makeText(this, "Enter the section ....", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (ttpassword1212.length() < 6) {
            Toast.makeText(this, "Password character must be 6 character long ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ttpassword1212.equals(ttcpassword1212)) {
            Toast.makeText(this, "Password deosn't match ", Toast.LENGTH_SHORT).show();
            return;
        }

        createAccount();
    }

    private void createAccount() {

        progressDialog.setMessage("Creating Principal...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(ttemail1212, ttpassword1212)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        saveFirebasedata();
                            }

                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(adminregister.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveFirebasedata() {

        progressDialog.setMessage("Saving account information...");
        String timestamp = "" + System.currentTimeMillis();
        if (image_uri == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("uid", "" + firebaseAuth.getUid());
            hashMap.put("pemail", "" + ttemail1212);
            hashMap.put("pname", "" + ttname1212);
            hashMap.put("pdob", "" + tdate1212);
            hashMap.put("pphone", "" + ttphone1212);
            hashMap.put("page", "" + ttage1212);
            //hashMap.put("department", "" + ttdept1212);
            hashMap.put("paddress", "" + ttaddress1212);
            hashMap.put("ppassword",""+ttpassword1212);
            hashMap.put("pgender", "" + ttgender1212);
            hashMap.put("timestamp", "" + timestamp);
            hashMap.put("accounttype", "" + "Principal");
            hashMap.put("online", "true");
            hashMap.put("profileimage", "");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");


            ref.child("Principal").child(firebaseAuth.getUid()).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {


                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            startActivity(new Intent(adminregister.this, Adminhome.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            startActivity(new Intent(adminregister.this, Adminhome.class));
                            finish();
                        }
                    });

        }
        else
        {
            String filePathandname = "profile_images/" + "" + firebaseAuth.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathandname);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadImageUri = uriTask.getResult();

                            if (uriTask.isSuccessful()) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("uid", "" + firebaseAuth.getUid());
                                hashMap.put("pemail", "" + ttemail1212);
                                hashMap.put("pname", "" + ttname1212);
                                hashMap.put("dob", "" + tdate1212);
                                hashMap.put("pphone", "" + ttphone1212);
                                hashMap.put("page", "" + ttage1212);
                                //hashMap.put("department", "" + ttdept1212);
                                hashMap.put("paddress", "" + ttaddress1212);
                                hashMap.put("ppassword",""+ttpassword1212);
                                hashMap.put("pgender", "" + ttgender1212);
                                hashMap.put("timestamp", "" + timestamp);
                                hashMap.put("accounttype", "" + "Principal");
                                hashMap.put("online", "true");
                                hashMap.put("profileimage", "" + downloadImageUri);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                ref.child("Principal").child(firebaseAuth.getUid()).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(adminregister.this, "Admin added succefully ", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(adminregister.this, adminstart.class));
                                                finish();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(adminregister.this, adminstart.class));
                                                finish();

                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(adminregister.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


        }
    }


    private void showImagePickDialog() {

        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick image").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (checkCameraPermission())
                    {
                        pickFromCamera();
                    }
                    else
                    {
                        requestCameraPermission();
                    }
                }
                else
                {
                    if (checkStoragePermission())
                    {
                        pickFromGallery();
                    }
                    else
                    {
                        requestStoragePermission();
                    }
                }
            }
        }).show();

    }

    private void pickFromGallery()
    {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_image Description");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);

    }

    private boolean checkStoragePermission()
    {
        boolean result= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission()
    {
        ActivityCompat.requestPermissions(this,storagepermission,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission()
    {
        boolean result=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }


    private void requestCameraPermission()
    {  ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);

    }


    public void onRequestPermissionResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0)
                {
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted&&storageAccepted)
                    {
                        pickFromCamera();
                    }
                    else
                    {
                        Toast.makeText(this, "Camera permissions are neccessary ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0)
                {
                    boolean storageAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted)
                    {
                        pickFromCamera();
                    }
                    else
                    {
                        Toast.makeText(this, "Storage permission are required ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(resultCode==RESULT_OK)
        {
            if(requestCode==IMAGE_PICK_GALLERY_CODE)
            {
                image_uri=data.getData();
                teachimg1212.setImageURI(image_uri);
            }
            else if(requestCode==IMAGE_PICK_CAMERA_CODE)
            {
                teachimg1212.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
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