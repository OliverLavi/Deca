package com.example.gilano.deca;

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
}
