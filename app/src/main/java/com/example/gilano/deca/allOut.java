package com.example.gilano.deca;

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

//        displayList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                showAlert(i);
//                return true;
//            }
//        });
    }

    public void checkIn(int position){
        Student student = (Student)displayList.getItemAtPosition(position);
        final int pos = position;
        student.setStatus(true);
        String node = student.getId().toString();
        mRef.child(node).setValue(student);

        new Thread() {
            public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            studentsIn.remove(pos);
                            listAdapter.notifyDataSetChanged();
                        }
                    });
            }
        }.start();
        //studentsIn.remove(position);
        //listAdapter.notifyDataSetChanged();

    }
    public void showAlert(int position){
        final int cheese = position;
        Student student = (Student)displayList.getItemAtPosition(position);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Check in " + student.getName() + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkIn(cheese);

                        dialogInterface.cancel();
                        showToast();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create();
        alert.show();
    }

    public void showToast(){
        Toast.makeText(this, "Checked In", Toast.LENGTH_SHORT).show();
    }
}
