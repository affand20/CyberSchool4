package com.trydev.affan.cyberschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by affan on 09/11/17.
 */

public class LihatNilaiFragment extends Fragment{

    DatabaseReference dbNilai;

    String idSiswa;
    String smt;

    ListView listView;

    List<Nilai> nilaiList;

    Spinner semester;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getActivity()==((UserActivity) getActivity())){
            ((UserActivity) getActivity()).setActionBarTitle("Nilai");
        } else if(getActivity() == ((AdminActivity) getActivity())){
            ((AdminActivity) getActivity()).setActionBarTitle("Nilai");
        }
        return inflater.inflate(R.layout.fragment_lihat_nilai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity(), String.valueOf(getActivity()), Toast.LENGTH_SHORT).show();

        listView = (ListView) view.findViewById(R.id.nilai_per_semester);
        nilaiList = new ArrayList<>();

        semester = (Spinner) view.findViewById(R.id.semester);

        idSiswa = getArguments().getString("ID");

        dbNilai = FirebaseDatabase.getInstance().getReference("nilai").child(idSiswa);

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                smt = semester.getSelectedItem().toString();
                LoadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void LoadData(){
        dbNilai.child(smt).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                nilaiList.clear();

                for (DataSnapshot getData : dataSnapshot.getChildren()){
                    Nilai nilai = getData.getValue(Nilai.class);
                    nilaiList.add(nilai);
                }

                if (getActivity()!=null){
                    NilaiList adapter = new NilaiList(getActivity(), nilaiList);
                    listView.setAdapter(adapter);
                    if (nilaiList.isEmpty()){
                        Toast.makeText(getActivity(), "Data tidak ditemukan :(", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
