package com.example.gilano.deca;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class addStudent extends AppCompatActivity implements OnClickListener {

    private Button mScan;
    private Button mAdd;
    private EditText mID;
    private EditText fName;
    private EditText lName;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        //Firebase instance
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mScan = (Button)findViewById(R.id.scanBtn);
        mAdd = (Button)findViewById(R.id.addBtn);
        mScan.setOnClickListener(this);
        mID = (EditText)findViewById(R.id.idIn);
        fName = (EditText)findViewById(R.id.fNameIn);
        lName = (EditText)findViewById(R.id.lNameIn);
    }

    //Open scanning app
    public void onClick(View v){
        if(v.getId()==R.id.scanBtn){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

    }

    //Set ID field to scan result
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult != null) {
            String scanContent = scanningResult.getContents().toString();
            mID.setText(scanContent);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();

        }
    }

    //Adds student to the Firebase list
    public void addStudent(String uID, String firstName, String lastName){
        //create student object
        Student user = new Student(uID, firstName, lastName, true);
        //store on Firebase
        mDatabase.child("students").child(uID).setValue(user);
    }

    //Respond to button click
    public void addUser(View view){
        //Gets ID from text editor
        String x = mID.getText().toString();
        //add the student to the firebase
        addStudent(x, fName.getText().toString(), lName.getText().toString());

        //Create toast
        Context context = getApplicationContext();
        CharSequence text = "Student Added";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //Reset text fields
        mID.setText("");
        fName.setText("");
        lName.setText("");
    }
}
