package com.example.css;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityContacts extends AppCompatActivity {

    private  Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        utils=new Utils(this);
        Button btn =findViewById(R.id.edit_userID);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                utils.logout();
                new actvity()
            }
        });


        fir3estore.colloection("student").document(utils.getToken())

    }
}