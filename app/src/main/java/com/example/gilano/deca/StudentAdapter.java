package com.example.gilano.deca;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gilano on 1/27/2017.
 */

public class StudentAdapter extends ArrayAdapter<Student>{

    Context context;
    int layoutResourceId;
    ArrayList<Student> data = null;

    public StudentAdapter(Context context, int layoutResourceId, ArrayList<Student> data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        StudentHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new StudentHolder();
            holder.name = (TextView)row.findViewById(R.id.nameTitle);
            holder.status = (TextView)row.findViewById(R.id.statusTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (StudentHolder)row.getTag();
        }

        Student student = getItem(position);
        //Weather weather = data[position];
        holder.name.setText(student.getName());
        if(student.getStatus() == true){
            holder.status.setText("Checked In");
        }else{
            holder.status.setText("Checked Out");
        }


        return row;
    }

    static class StudentHolder {
        TextView name;
        TextView status;
    }
}
