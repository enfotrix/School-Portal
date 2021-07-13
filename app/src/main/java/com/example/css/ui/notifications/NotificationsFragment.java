package com.example.css.ui.notifications;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.css.Adapter_Reports;
import com.example.css.Model_Reports;
import com.example.css.R;
import com.example.css.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class NotificationsFragment extends Fragment {

    private FirebaseFirestore firestore;
    private Utils utils;

    List<Model_Reports> myListData = new ArrayList<>();
    RecyclerView recyclerView ;

    private ACProgressFlower progDialog;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);



        utils=new Utils(this.getContext());
        firestore= FirebaseFirestore.getInstance();

        recyclerView = root.findViewById(R.id.list_UnNotifi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        fetchUnreadNotifi(utils.getToken());

        return root;
    }
    private void fetchUnreadNotifi(String token){

        progDialogText("Please Wait...");
        progDialog.show();
        firestore.collection("Student").document(token)// whereEqualTo("studentID",token)
                .collection("Notifications").whereEqualTo("status","unread").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("status").equals("unread")){
                                    Model_Reports model_reports =  new Model_Reports(
                                            document.getId(),
                                            document.getString("date"),
                                            document.getString("detail"),
                                            document.getString("heading"),
                                            document.getString("type"),
                                            document.getString("studentName"));
                                    myListData.add(model_reports);
                                }
                            }
                            Adapter_Reports adapter = new Adapter_Reports(myListData);
                            recyclerView.setAdapter(adapter);

                            progDialog.dismiss();
                        }
                        else {
                            progDialog.dismiss();
                            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
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