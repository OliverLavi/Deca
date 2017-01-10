package com.example.gilano.deca;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by gilano on 12/28/2016.
 */

public class Deca extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
