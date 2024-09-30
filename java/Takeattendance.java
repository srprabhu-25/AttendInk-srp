package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Takeattendance extends AppCompatActivity {

    String teacher_id;
    String class_selected,periodno;
    Spinner period,classess;

    ArrayList<String> selectedItems;
    ArrayList<String> nonselecteditems;
//    Toolbar mtoolbar;

    private static long back_pressed;
    ArrayList<String> ul;
    ListView listview;
    ArrayList<String> absentlist=new ArrayList<>();
    private ArrayAdapter adapter;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Usernames = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbuser = ref.child("Users").child("Students");
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    private ImageButton backBtn105;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeattendance);

//        mtoolbar = (Toolbar) findViewById(R.id.takeattendancebar);
//        getSupportActionBar().setTitle("Attendance");
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        period = (Spinner) findViewById(R.id.spinner4);
        classess = (Spinner) findViewById(R.id.spinner2001);
        backBtn105=(ImageButton)findViewById(R.id.backBtn1050015);


        selectedItems = new ArrayList<String>();

        backBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle1 = getIntent().getExtras();
        //class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");
        //TextView classname = (TextView) findViewById(R.id.textView123);


        class_selected = classess.getSelectedItem().toString();

    }

    public void viewlist(View view)
    {
        class_selected = classess.getSelectedItem().toString();
        Userlist.clear();
        dbuser.orderByChild("section").equalTo(class_selected).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString());
                    Usernames.add(dsp.child("name").getValue().toString());

                }
                OnStart(Userlist);


               // Toast.makeText(Takeattendance.this, (CharSequence) Userlist, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Takeattendance.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public boolean onCreateOptionMenu(Menu menu) {
        return true;
    }


    public void OnStart(ArrayList<String> userlist) {

        nonselecteditems = userlist;
        ListView chl = (ListView) findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
        chl.setAdapter(aa);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selecteditem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selecteditem))
                    selectedItems.remove(selecteditem);
                else
                    selectedItems.add(selecteditem);
            }

            });

    }

    public void showSelectedItems(View view) throws BiffException, IOException {
        String selItems="";
        periodno=period.getSelectedItem().toString();
        if(periodno.equals("Selected_Period"))
        {
            Toast.makeText(this, "Select a class ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ref=FirebaseDatabase.getInstance().getReference();
            dbAttendance=ref.child("Attendance").child(date);
          //  dbuser=ref.orderByChild("sid").

            for(String item : selectedItems)
            {
                Toast.makeText(this, "Attendance Created successfully", Toast.LENGTH_SHORT).show();
                nonselecteditems.remove(item);
                dbAttendance.child(item).child(periodno).setValue("P"+ " / "+ teacher_id);
                if(selItems == "")
                    selItems = item;
                else
                    selItems +="/" +item;

            }
            for (String item : nonselecteditems)
            {

                Toast.makeText(this, "Attendance created Successfully ", Toast.LENGTH_SHORT).show();
                dbAttendance.child(item).child(periodno).setValue("A"+"/"+teacher_id);


               absentlist.add(item);



            }
            Intent intent=new Intent(Takeattendance.this,Sendsmstostudent.class);
            intent.putExtra("absent",absentlist);
            startActivity(intent);
            finish();

            Userlist.clear();
        }

    // addtoreport();
    }



    private void addtoreport(View view) throws IOException, BiffException {


        Workbook workbook=null;
        WritableWorkbook wb=null;
        WritableSheet s=null;
        try
        {
            workbook=Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/online _attendance/"+class_selected+"_month_"+date.substring(3,5)+".xls"));
            wb=createWorkbook(new File(class_selected + "_month_" + date.substring(3, 5)),workbook);
            s=wb.getSheet(0);
        }
        catch (Exception e)
        {
            File wbfile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/online_attendance/"+class_selected+".xls");
            wb=createWorkbook(new File(class_selected + "_month_" + date.substring(3, 5)));
            s = createSheet(wb, "month_", 0);

        }

        int i=s.getColumns();
        if(i==0)
        {
            try
            {
                Label newCell=new Label(0,0,"Student_id");
                Label newCell2=new Label(1,0,"Student_name");
                WritableFont headerFont=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
                WritableCellFormat headerFormat=new WritableCellFormat(headerFont);

                headerFormat.setAlignment(Alignment.CENTRE);
                newCell.setCellFormat(headerFormat);
                newCell2.setCellFormat(headerFormat);
                s.addCell(newCell);
                s.addCell(newCell2);

            }
            catch (WriteException e)
            {
                e.printStackTrace();
            }
            for (Object item : Userlist) {
                int j = s.getRows();
                String name=Usernames.get(j-1).toString();

                Label label = new Label(0, j, item.toString());
                Label label2 = new Label(1, j, name);

                try {
                    s.addCell(label);
                    s.addCell(label2);


                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

        i=s.getColumns();

        int j=1;
        try
        {
         Label newCell=new Label(i,0,date);
         WritableFont headerFont=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
         WritableCellFormat headerFormat=new WritableCellFormat(headerFont);

         headerFormat.setAlignment(Alignment.CENTRE);
         newCell.setCellFormat(headerFormat);
         s.addCell(newCell);
        }
        catch (WriteException e)
        {
            e.printStackTrace();
        }

        for (Object item:Userlist)
        {
            Label label2;
            if(selectedItems.contains(item))
            {
                label2=new Label(i,j,"P");

            }
            else
            {
                label2=new Label(i,j,"A");
            }


            j++;
            try
            {

                s.addCell(label2);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Enable to create a sheet", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
        }

        Date today=new Date();

        String tomorrow=new SimpleDateFormat("dd=MM-yyyy").format(new Date(today.getTime()+(1000 * 60 * 60 * 24)));
        if(tomorrow.substring(0,2).equals("01"))
        {

            int row = s.getRows();
            int col = s.getColumns();
            String xx = "";
            int nop,tc;

            for (i = 0 ; i < row ; i++)
            {
                nop = 0;
                tc = -2;
                for (int c = 0; c < col; c++) {
                    Cell z = s.getCell(c, i);
                    xx = z.getContents();
                    if (xx.equals("P")) {
                        nop++;
                    }
                    if (!xx.isEmpty() || !xx.equals("")) {
                        tc++;
                    }
                }
                xx = xx + "\n";
                Label label = new Label(col, i, "" + nop);

                Label label2 = new Label(col + 1, i, nop*100/tc + "%");
                try
                {

                    if(i==0)
                    {
                        label = new Label(col,i,"Total=" +tc);
                        label2 = new Label(col+1,i,"percentage");

                    }
                    s.addCell(label);
                    s.addCell(label2);

                }
                catch (WriteException e)
                {
                    e.printStackTrace();
                }
            }

        }

        try
        {
          wb.write();
          wb.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (WriteException e)
        {
            e.printStackTrace();
        }

        Toast.makeText(this, "Sheet created successfully", Toast.LENGTH_SHORT).show();

    }

public WritableWorkbook createWorkbook(File filename, Workbook workbook)
{
    WorkbookSettings wbSettings = new WorkbookSettings();
    wbSettings.setUseTemporaryFileDuringWrite(true);
    File sdcard = Environment.getExternalStorageDirectory();

    File dir = new File(sdcard.getAbsolutePath() + "/online_attendance");
    dir.mkdirs();
    File wbfile = new File(dir,filename+".xls");
    
    WritableWorkbook wb = null;
    
    try 
    {

        wb = Workbook.createWorkbook(wbfile,workbook);
        
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
    return wb;
}


public WritableWorkbook createWorkbook(File filename)
{
    WorkbookSettings wbSettings=new WorkbookSettings();
    wbSettings.setUseTemporaryFileDuringWrite(true);
    
    File sdcard=Environment.getExternalStorageDirectory();
    
    File dir=new File(sdcard.getAbsolutePath()+"/online_attendance");
    dir.mkdirs();
    
    File wbfile = new File(dir,filename+".xls");
    WritableWorkbook wb=null;
    
    try 
    {
        wb = Workbook.createWorkbook(wbfile,wbSettings);
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
    return wb;
    
}

    public WritableSheet createSheet(WritableWorkbook wb, String sheetName, int sheetIndex){
        //create a new WritableSheet and return it

        return wb.createSheet(sheetName, sheetIndex);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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