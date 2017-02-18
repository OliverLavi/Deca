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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class allStudents extends AppCompatActivity {

    private ListView mList;
    private ArrayList<Student> completeList;
    private StudentAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        mList = (ListView)findViewById(R.id.listAllStudents);

        //Initialize ArrayList
        completeList = new ArrayList<Student>();

        //Get Instance of database
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("students");


        mRef.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Convert Firebase nodes to Student objects
                Student student = dataSnapshot.getValue(Student.class);
                //Add new Student objects to ArrayList
                completeList.add(student);
                //Displays Student object in ListView
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                completeList.add(student);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                completeList.remove(student);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        for(Student s: completeList){
//            System.out.println("Student name: " + s.getName());
//        }
//        Collections.sort(completeList, new Comparator<Student>() {
//            @Override
//            public int compare(Student student, Student t1) {
//                return student.getName().compareTo(t1.getName());
//            }
//        });

        //Create StudentAdapter
        listAdapter = new StudentAdapter(this, R.layout.listview_item_row, completeList);
        //Set adapter to ListView
        mList.setAdapter(listAdapter);

    }
}
