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
import android.widget.Spinner;
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

public class Addteacher extends AppCompatActivity {
//    EditText Tname, Tid;
//    EditText Tphoneno, Tdept, Tage;
//    EditText Taddress, Temail, Tpass;
//    String tname, tphoneno, tdept, tage, taddress, temail, tpass, classname, tid;
//    DatabaseReference databaseteacher;
//    Button btnadd,btncancel;

    private ImageView teachimg;
    private EditText tname, tphone, temail, taddress, tage, tid, tpassword, cpassword;
    private Spinner  tdept;
    private RadioGroup rgruop;
    private RadioButton rbmale, rbfemale;
    private Button btnsubmit, btncancel;
    private String ttname, ttphone, ttaddress, ttemail, ttdept, ttage, ttid, ttpassword, ttgender, ttcpassword;
    private FirebaseDatabase teacherdatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private static long back_pressed;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;

    private String[] cameraPermission;
    private String[] storagepermission;
    private Uri image_uri;

    ImageButton backBtn105003;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteacher);




        teachimg = (ImageView) findViewById(R.id.imageView);
        tname = (EditText) findViewById(R.id.newteacher);
        tphone = (EditText) findViewById(R.id.phoneno);
        taddress = (EditText) findViewById(R.id.address);
        tdept = (Spinner) findViewById(R.id.department);
        tage = (EditText) findViewById(R.id.age);
        temail = (EditText) findViewById(R.id.emailid);
        tpassword = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        tid = (EditText) findViewById(R.id.teacherid);
        btnsubmit = (Button) findViewById(R.id.submit);
        btncancel = (Button) findViewById(R.id.cancel);
        rgruop = (RadioGroup) findViewById(R.id.rgroup);
        rbmale = (RadioButton) findViewById(R.id.male);

        backBtn105003=(ImageButton)findViewById(R.id.backBtn105003);


        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait.....");
        progressDialog.setCanceledOnTouchOutside(false);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backpressed();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputdata();

            }
        });
        teachimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        backBtn105003.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void backpressed() {
        tname.setText("");
        tphone.setText("");
        taddress.setText("");
        temail.setText("");
        tid.setText("");
        tage.setText("");
        tpassword.setText("");
        cpassword.setText("");
    }

    private void inputdata() {
        ttname = tname.getText().toString().trim();
        ttphone = tphone.getText().toString().trim();
        ttaddress = taddress.getText().toString().trim();
        ttemail = temail.getText().toString().trim();
        ttdept = tdept.getSelectedItem().toString().trim();
        ttid = tid.getText().toString().trim();
        ttage = tage.getText().toString().trim();
        ttpassword = tpassword.getText().toString().trim();
        ttcpassword = cpassword.getText().toString().trim();
        if (findViewById(rgruop.getCheckedRadioButtonId()) == findViewById(R.id.male)) {
            ttgender = "Male";
        } else {
            ttgender = "Female";
        }

        if (TextUtils.isEmpty(ttname)) {
            Toast.makeText(this, "Enter the name....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttphone)) {
            Toast.makeText(this, "Enter the phone number....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ttphone.matches("[0-9]{10}")) {
            tphone.requestFocus();
            tphone.setError("Enter the phone number correctly...");
            return;
        }
        if (TextUtils.isEmpty(ttaddress)) {
            Toast.makeText(this, "Enter the Address ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttemail)) {
            Toast.makeText(this, "Enter the email address ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(ttemail).matches()) {
            Toast.makeText(this, "Invalid email pattern  ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttid)) {
            Toast.makeText(this, "Enter the Student id ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttage)) {
            Toast.makeText(this, "Enter the age ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ttdept)) {
            Toast.makeText(this, "Enter the section ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ttpassword.length() < 6) {
            Toast.makeText(this, "Password character must be 6 character long ....", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ttpassword.equals(ttcpassword)) {
            Toast.makeText(this, "Password deosn't match ", Toast.LENGTH_SHORT).show();
            return;
        }

        createAccount();
    }

    private void createAccount() {
        progressDialog.setMessage("Creating Faculty...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(ttemail, ttpassword)
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
                        Toast.makeText(Addteacher.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveFirebasedata() {
        progressDialog.setMessage("Saving account information...");
        String timestamp = "" + System.currentTimeMillis();
        if (image_uri == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("uid", "" + firebaseAuth.getUid());
            hashMap.put("email", "" + ttemail);
            hashMap.put("name", "" + ttname);
            hashMap.put("tid", "" + ttid);
            hashMap.put("phone", "" + ttphone);
            hashMap.put("age", "" + ttage);
            hashMap.put("department", "" + ttdept);
            hashMap.put("address", "" + ttaddress);
            hashMap.put("password",""+ttpassword);
            hashMap.put("gender", "" + ttgender);
            hashMap.put("timestamp", "" + timestamp);
            hashMap.put("accounttype", "" + "Teacher");
            hashMap.put("online", "true");
            hashMap.put("profileimage", "");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child("Teachers").child(ttid).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            startActivity(new Intent(Addteacher.this, Adminlogin.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            startActivity(new Intent(Addteacher.this, Adminlogin.class));
                            finish();
                        }
                    });

        } else {
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
                                hashMap.put("email", "" + ttemail);
                                hashMap.put("name", "" + ttname);
                                hashMap.put("tid", "" + ttid);
                                hashMap.put("phone", "" + ttphone);
                                hashMap.put("age", "" + ttage);
                                hashMap.put("section", "" + ttdept);
                                hashMap.put("address", "" + ttaddress);
                                hashMap.put("password",""+ttpassword);
                                hashMap.put("gender", "" + ttgender);
                                hashMap.put("timestamp", "" + timestamp);
                                hashMap.put("accounttype", "" + "Teacher");
                                hashMap.put("online", "true");
                                hashMap.put("profileimage", "" + downloadImageUri);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                ref.child("Teachers").child(ttid).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(Addteacher.this, "Teacher added succefully ", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Addteacher.this, Adminlogin.class));
                                                finish();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(Addteacher.this, Adminlogin.class));
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
                            Toast.makeText(Addteacher.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

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
                teachimg.setImageURI(image_uri);
            }
            else if(requestCode==IMAGE_PICK_CAMERA_CODE)
            {
                teachimg.setImageURI(image_uri);
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
