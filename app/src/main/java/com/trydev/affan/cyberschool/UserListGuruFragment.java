package com.trydev.affan.cyberschool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by affan on 08/11/17.
 */

public class UserListGuruFragment extends Fragment {

    DatabaseReference dbGuru;

    ListView listView;

    List<Guru> listGuru;

    ProgressDialog pd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_guru, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbGuru = FirebaseDatabase.getInstance().getReference("Guru");

        listView = view.findViewById(R.id.list_guru);

        listGuru = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading");
        pd.setMessage("Sedang membaca basis data...");
        pd.show();

        dbGuru.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listGuru.clear();

                for (DataSnapshot getData : dataSnapshot.getChildren()){
                    Guru guru = getData.getValue(Guru.class);
                    listGuru.add(guru);
                }

                if (getActivity()!=null){
                    GuruList adapter = new GuruList(getActivity(), listGuru);
                    listView.setAdapter(adapter);
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
