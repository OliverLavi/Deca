package com.example.gilano.deca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        final List<Student> completeList = new ArrayList<Student>();
        mList = (ListView)findViewById(R.id.listAllStudents);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> students = dataSnapshot.getChildren();

                for (DataSnapshot child: students) {
                    Student student = child.getValue(Student.class);
                    completeList.add(student);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<Student> listAdapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_2, completeList);
        mList.setAdapter(listAdapter);

    }
}
