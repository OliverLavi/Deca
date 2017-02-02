package com.example.gilano.deca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class allStudents extends AppCompatActivity {

    private ListView mList;
    private int counter;
    private ArrayList<Student> completeList = new ArrayList<Student>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        mList = (ListView)findViewById(R.id.listAllStudents);
        //final ArrayList<Student> completeList = new ArrayList<Student>();
        completeList.add(new Student());
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("students");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                completeList.add(student);
                counter++;
                System.out.println("Students: " + student);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        for(Student s: completeList){
            System.out.println("Student name: " + s.getName());
        }
        StudentAdapter listAdapter = new StudentAdapter(this, R.layout.listview_item_row, completeList);
        mList.setAdapter(listAdapter);

    }
}
