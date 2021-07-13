package com.example.css;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.royrodriguez.transitionbutton.TransitionButton;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class ActivitySignIn extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private Utils utils;


    private TransitionButton transitionButton;
    private TextView txt_contactUs;
    private EditText edit_userID,edit_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edit_userID=findViewById(R.id.edit_ID_SignIn);
        edit_password=findViewById(R.id.edit_Password_SignIn);
        transitionButton = findViewById(R.id.btn_Login);
        txt_contactUs=findViewById(R.id.txt_contactUs);

        utils=new Utils(this);
        firestore= FirebaseFirestore.getInstance();

        txt_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ActivityContacts.class));
            }
        });



        transitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionButton.startAnimation();
                if(!isEmpty()){
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firestore.collection("Student").whereEqualTo("reg",edit_userID.getText().toString()).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if(!task.getResult().isEmpty()){
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        if(document.getString("password").equals(edit_password.getText().toString())){
                                                            utils.putToken(document.getString("studentID"));
                                                            //Toast.makeText(ActivitySignIn.this, document.getString("id"), Toast.LENGTH_SHORT).show();
                                                            transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, new TransitionButton.OnAnimationStopEndListener() {
                                                                @Override
                                                                public void onAnimationStopEnd() {
                                                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            });

                                                        }
                                                        else {
                                                            stopAnimation("Incorrect Password");
                                                        }
                                                    }
                                                }
                                                else {
                                                    stopAnimation("User Id Not Exist");
                                                }
                                            }
                                            else {

                                                stopAnimation(task.getException().toString());
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    stopAnimation(e.getMessage());
                                }
                            });

                        }
                    }, 500);
                }

            }
        });
    }
    private boolean isEmpty(){
        boolean isEmpty=false;
        if(edit_userID.getText().toString().isEmpty()){
            isEmpty=true;
            stopAnimation("User Id can't be empty");
        }
        else if(edit_password.getText().toString().isEmpty()){
            isEmpty=true;
            stopAnimation("User password can't be empty");
        }
        return isEmpty;
    }
    private void stopAnimation(String errorString){
        Toast.makeText(ActivitySignIn.this, errorString, Toast.LENGTH_SHORT).show();
        transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
    }
}