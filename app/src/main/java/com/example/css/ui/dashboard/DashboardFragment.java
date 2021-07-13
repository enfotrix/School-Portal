package com.example.css.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.css.MainActivity;
import com.example.css.R;
import com.example.css.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.royrodriguez.transitionbutton.TransitionButton;

import java.text.BreakIterator;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class DashboardFragment extends Fragment {

    private ACProgressFlower progDialog;
    private FirebaseFirestore firestore;
    private Utils utils;
    private ImageView img_profile;
    private Button btn_marks;
    private TextView txt_Name,txt_fatherName,txt_reg,txt_class,txt_section,txt_bForm,txt_dob,txt_contact,txt_address , txt_teacher, txt_medium;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        txt_Name=root.findViewById(R.id.txt_fatherName_student);
        txt_fatherName=root.findViewById(R.id.txt_name_student);
        txt_reg=root.findViewById(R.id.text_regNumber_student);
        txt_class=root.findViewById(R.id.txt_class_student);
        txt_teacher=root.findViewById(R.id.txt_class_teacher);
        txt_medium=root.findViewById(R.id.txt_class_medium);
        txt_section=root.findViewById(R.id.txt_section_student);
        txt_bForm=root.findViewById(R.id.txt_NIC_student);
        txt_dob=root.findViewById(R.id.txt_dob_student);
        txt_contact=root.findViewById(R.id.txt_contact_student);
        txt_address=root.findViewById(R.id.txt_address_student);
        btn_marks=root.findViewById(R.id.btn_marks_student);
        img_profile=root.findViewById(R.id.img_profile_student);
        utils=new Utils(this.getContext());
        firestore= FirebaseFirestore.getInstance();



        fetchData(utils.getToken());

        return root;
    }


    private void fetchData(String token) {
        progDialogText("Please Wait...");
        progDialog.show();
        firestore.collection("Student").whereEqualTo("studentID",token).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(!task.getResult().isEmpty()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    txt_reg.setText(document.getString("reg"));
                                    txt_Name.setText(document.getString("studentName"));
                                    txt_fatherName.setText(document.getString("fatherName"));
                                    txt_bForm.setText(document.getString("cnic"));
                                    txt_bForm.setText(document.getString("contact"));
                                    txt_dob.setText(document.getString("dob"));
                                    txt_address.setText(document.getString("address"));

                                    firestore.collection("Class").document(document.getString("classID"))
                                            .collection("Sections").whereEqualTo("sectionID",document.getString("sectionID")).get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if(!task.getResult().isEmpty()){
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                txt_class.setText(document.getString("grade"));
                                                                txt_section.setText(document.getString("sectionName"));
                                                                txt_medium.setText(document.getString("medium"));

                                                                firestore.collection("Teacher").whereEqualTo("teacherID",document.getString("sectionTeacher")).get()
                                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    if(!task.getResult().isEmpty()){
                                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                            txt_teacher.setText(document.getString("teacherName"));
                                                                                        }
                                                                                        progDialog.dismiss();
                                                                                    }
                                                                                }

                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progDialog.dismiss();
                                                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }

                                                        }
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progDialog.dismiss();
                                            Toast.makeText(getContext(), "debug "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progDialog.dismiss();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void progDialogText(String Loading_Text){
        progDialog = new ACProgressFlower.Builder(this.getContext()).direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();
    }
}