package com.example.gilano.deca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allOut extends AppCompatActivity {

    private ListView displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_out);

        final ArrayList<Student> studentsIn = new ArrayList<Student>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("students");
        displayList = (ListView)findViewById(R.id.allOutList);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child: dataSnapshot.getChildren() ){
                    String name = child.child("name").getValue().toString();
                    String firstName = child.child("firstName").getValue().toString();
                    String lastName = child.child("lastName").getValue().toString();
                    String status = child.child("status").getValue().toString();
                    boolean stat = true;
                    if(status == "false"){
                        stat = false;
                    }
                    String id = child.child("id").getValue().toString();

                    Student student = new Student(Integer.parseInt(id), firstName, lastName, stat);
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

        StudentAdapter listAdapter = new StudentAdapter(this, R.layout.listview_item_row, studentsIn);
        displayList.setAdapter(listAdapter);
    }
}
