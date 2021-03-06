package com.example.gilano.deca;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class barscan extends AppCompatActivity implements OnClickListener{

    private Button mScan;
    private Button mCheckIn;
    private Button mCheckOut;
    private TextView mResultNote;
    private ArrayList<Student> currentStudents;
    private EditText lName;
    private EditText inputID;
    private DatabaseReference mDatabase;
    private String id;
    private Button mClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barscan);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mScan = (Button)findViewById(R.id.scanBtn);
        mCheckIn = (Button)findViewById(R.id.chkInBtn);
        mCheckOut = (Button)findViewById(R.id.chkOutBtn);
        mResultNote = (TextView)findViewById(R.id.textResult);
        inputID = (EditText)findViewById(R.id.idIn);
        mScan.setOnClickListener(this);
        mClear = (Button)findViewById(R.id.clearBtn);
        currentStudents = new ArrayList<Student>();

        //Get all students to cross reference
        mDatabase.child("students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String name = child.child("name").getValue().toString();
                    String firstName = child.child("firstName").getValue().toString();
                    String lastName = child.child("lastName").getValue().toString();
                    String status = child.child("status").getValue().toString();
                    boolean stat = true;
                    if(status == "false"){
                        stat = false;
                    }
                    String id = child.child("id").getValue().toString();

                    Student student = new Student(id, firstName, lastName, stat);
                    currentStudents.add(student);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //Start Scanning app
    public void onClick(View v){
        if(v.getId()==R.id.scanBtn){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

    }
    //Populate ID field with scan info
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult != null) {
            String scanContent = scanningResult.getContents().toString();
            inputID.setText(scanContent);

        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();

        }
    }
    //Check in student
    public void checkIn(View view){
            id = inputID.getText().toString();
            //Check if student exists in database
            boolean exists = false;
            for(Student s: currentStudents){
                if(id.equals(s.getId().toString())){
                    exists = true;
                }
            }
            if(id.matches("") || exists == false){
                Toast.makeText(this, "You did not enter a valid ID", Toast.LENGTH_SHORT).show();
                return;
            }

            //Check student in
            mDatabase.child("students").child(id).child("status").setValue(true);
            inputID.setText("");

            Context context = getApplicationContext();
            CharSequence text = "Checked In";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
    }

    public void checkOut(View view) {
            id = inputID.getText().toString();
            //Check if student is in database
            boolean exists = false;
            for(Student s: currentStudents){
                if(id.equals(s.getId().toString())){
                    exists = true;
                }
            }
            if(id.matches("") || exists == false){
                Toast.makeText(this, "You did not enter a valid ID", Toast.LENGTH_SHORT).show();
                return;
            }
            //Checkout
            mDatabase.child("students").child(id).child("status").setValue(false);
            inputID.setText("");

            Context context = getApplicationContext();
            CharSequence text = "Checked Out";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
    }
    //Clear all students from database
    public void clearData(){
        mDatabase.child("students").removeValue();
        Toast.makeText(this, "Trip Cleared", Toast.LENGTH_SHORT).show();
    }

    //Clear Trip warning
    public void cheese(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("This will delete all the students from the list! Are you sure?")
                .setPositiveButton("I understand, continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearData();
                        dialogInterface.cancel();

                    }
                })
                .setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create();
        alert.show();
    }
}
