package com.example.gilano.deca;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class allOut extends AppCompatActivity {

    private ListView displayList;
    private DatabaseReference mRef;
    private StudentAdapter listAdapter;
    private ArrayList<Student> studentsIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_out);

        //Initiate ArrayList
        studentsIn = new ArrayList<Student>();
        //Get firebase instance
        mRef = FirebaseDatabase.getInstance().getReference("students");
        displayList = (ListView)findViewById(R.id.allOutList);

        mRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child: dataSnapshot.getChildren() ){
                    //Get data from each node
                    String name = child.child("name").getValue().toString();
                    String firstName = child.child("firstName").getValue().toString();
                    String lastName = child.child("lastName").getValue().toString();
                    String status = child.child("status").getValue().toString();
                    boolean stat = true;
                    if(status == "false"){
                        stat = false;
                    }
                    String id = child.child("id").getValue().toString();
                    //Create Student object from data
                    Student student = new Student(id, firstName, lastName, stat);

                    //Populate ArrayList with students checked out
                    if(stat == false){
                        studentsIn.add(student);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        //Create adapter
        listAdapter = new StudentAdapter(this, R.layout.listview_item_row, studentsIn);
        //Set adapter
        displayList.setAdapter(listAdapter);
    }
}
