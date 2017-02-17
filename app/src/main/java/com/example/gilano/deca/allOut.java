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

        studentsIn = new ArrayList<Student>();
        mRef = FirebaseDatabase.getInstance().getReference("students");
        displayList = (ListView)findViewById(R.id.allOutList);

        mRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
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

                    Student student = new Student(id, firstName, lastName, stat);
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

        listAdapter = new StudentAdapter(this, R.layout.listview_item_row, studentsIn);
        displayList.setAdapter(listAdapter);
        //listAdapter.notifyDataSetChanged();

//        displayList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    String id = studentsIn.get(i).getId();
//                    mRef.child(id).child("status").setValue(true);
//                    studentsIn.get(i).setStatus(true);
//                    listAdapter.notifyDataSetChanged();
//                    //clickIn(i);
//                return false;
//            }
//        });
    }

//    public void clickIn(int pos){
//        String name = studentsIn.get(pos).getName();
//        final String id = studentsIn.get(pos).getId();
//        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
//        alert.setMessage("Check In " + name + "?")
//                .setPositiveButton("Check In", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        mRef.child("students").child(id).child("status").setValue(true);
//
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
//                .create();
//        alert.show();
//    }
}
