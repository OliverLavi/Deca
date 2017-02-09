package com.example.gilano.deca;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class barscan extends AppCompatActivity implements OnClickListener{

    private Button mScan;
    private Button mCheckIn;
    private Button mCheckOut;
    private TextView mResultNote;
    private EditText fName;
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

    }

    public void onClick(View v){
        if(v.getId()==R.id.scanBtn){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

    }

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

    public void checkIn(View view){
            id = inputID.getText().toString();
            if(id.matches("")){
                Toast.makeText(this, "You did not enter an ID", Toast.LENGTH_SHORT).show();
                return;
            }
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
            if(id.matches("")){
                Toast.makeText(this, "You did not enter an ID", Toast.LENGTH_SHORT).show();
                return;
            }
            mDatabase.child("students").child(id).child("status").setValue(false);
            inputID.setText("");
            Context context = getApplicationContext();
            CharSequence text = "Checked Out";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
    }

    public void clearData(){
        mDatabase.child("students").removeValue();
        Toast.makeText(this, "Trip Cleared", Toast.LENGTH_SHORT).show();
    }

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
