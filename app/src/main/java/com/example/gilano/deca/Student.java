package com.example.gilano.deca;

import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gilano on 1/20/2017.
 */

public class Student {
    private int id;
    private String fName;
    private String lName;
    private boolean status;
    private String name;

    public Student(){
        id = 0;
        fName = "failed";
        lName = "failed";
        status = false;
    }

    public Student(int idIn, String firstName, String lastName, boolean createStatus){
        id = idIn;
        fName = firstName;
        lName = lastName;
        status = createStatus;
        name = fName + " " + lName;
    }

    public Student(JSONObject object){
        try {
            this.name = object.getString("name");
            this.status = object.getBoolean("status");
            this.id = object.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getFirstName(){
        return fName;
    }

    public String getLastName(){
        return lName;
    }

    public boolean getStatus(){
        return status;
    }

    public static ArrayList<Student> fromJson(JSONArray jsonObjects){
        ArrayList<Student> students = new ArrayList<Student>();
        for(int i = 0; i < jsonObjects.length(); i++) {
            try {
                students.add(new Student(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return students;
    }
}
