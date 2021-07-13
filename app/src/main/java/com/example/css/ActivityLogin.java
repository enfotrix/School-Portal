package com.example.css;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.royrodriguez.transitionbutton.TransitionButton;

public class ActivityLogin extends AppCompatActivity {


    //btn_login
    //edit_userID
    //edit_userPassword

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText edit_userID = findViewById(R.id.edit_userID);
        EditText edit_userPass = findViewById(R.id.edit_userPassword);
        firestore= FirebaseFirestore.getInstance();


        Button login_btn = findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=edit_userID.getText().toString();
                String pass= edit_userPass.getText().toString();
                authUser(id,pass);
            }
        });

    }

    private void authUser(String id,String password){


        firestore.collection("Student").whereEqualTo("reg",id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {



                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    //password check
                                    if(document.getString("password").equals(password)){
                                        Toast.makeText(ActivityLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(ActivityLogin.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(ActivityLogin.this, "User ID Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}