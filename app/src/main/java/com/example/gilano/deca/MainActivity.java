package com.example.gilano.deca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scan(View view) {
        Intent intent = new Intent(this, barscan.class);
        startActivity(intent);
    }

    public void seeAll(View view) {
        Intent intent = new Intent(this, allStudents.class);
        startActivity(intent);
    }

    public void seeIn(View view) {
        Intent intent = new Intent(this, allIn.class);
        startActivity(intent);
    }

    public void seeOut(View view) {
        Intent intent = new Intent(this, allOut.class);
        startActivity(intent);
    }

    public void addStudent(View view) {
        Intent intent = new Intent(this, addStudent.class);
        startActivity(intent);
    }
}
