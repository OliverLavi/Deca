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
    private String id;
    private String firstName;
    private String lastName;
    private boolean status;
    private String name;

    //Default constructor
    public Student(){
        id = "0000";
        firstName = "failed";
        lastName = "failed";
        status = false;
    }

    //Primary constructor
    public Student(String idIn, String firstName, String lastName, boolean createStatus){
        id = idIn;
        this.firstName = firstName;
        this.lastName = lastName;
        status = createStatus;
        name = this.lastName.toUpperCase() + ", " + this.firstName.toUpperCase();
    }

    //JSON constructor
    public Student(JSONObject object){
        try {
            setFirstName(object.getString("firstName"));
            setLastName(object.getString("lastName"));
            this.name = object.getString("name");
            this.status = object.getBoolean("status");
            this.id = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Getters and setters
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return status;
    }

    /*
     * Populates ArrayList with student objects from JSONArray
     */
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
