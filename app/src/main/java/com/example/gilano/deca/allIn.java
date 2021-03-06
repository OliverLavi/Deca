package com.example.gilano.deca;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class allIn extends AppCompatActivity {

    private ListView displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_in);

        //Initialize ArrayList
        final ArrayList<Student> studentsIn = new ArrayList<Student>();
        //Database Instance
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("students");
        displayList = (ListView)findViewById(R.id.allInList);

        //Get data
        mRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child: dataSnapshot.getChildren() ){
                    //Get info for each student
                    String name = child.child("name").getValue().toString();
                    String firstName = child.child("firstName").getValue().toString();
                    String lastName = child.child("lastName").getValue().toString();
                    String status = child.child("status").getValue().toString();
                    boolean stat = true;
                    if(status == "false"){
                        stat = false;
                    }
                    String id = child.child("id").getValue().toString();
                    //Create student object
                    Student student = new Student(id, firstName, lastName, stat);
                    //Add to list if checked in
                    if(stat == true){
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
        StudentAdapter listAdapter = new StudentAdapter(this, R.layout.listview_item_row, studentsIn);
        //Set adapter to ListView
        displayList.setAdapter(listAdapter);

    }
}
