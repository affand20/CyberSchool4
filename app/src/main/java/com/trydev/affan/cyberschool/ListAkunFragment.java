package com.trydev.affan.cyberschool;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Created by affan on 28/10/17.
 */

public class ListAkunFragment extends Fragment {



    DatabaseReference getDB;

    ListView listView;

    List<Siswa> siswa;
    ProgressDialog progress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AdminActivity) getActivity()).setActionBarTitle("Daftar Akun Siswa");
        return inflater.inflate(R.layout.fragment_list_akun, null );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        getDB = FirebaseDatabase.getInstance().getReference("AkunSiswa");

        listView = view.findViewById(R.id.list_siswa);

        siswa = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Siswa data_siswa = siswa.get(position);

                Fragment fragment = new DetailSiswaFragment();

                Bundle var = new Bundle();
                var.putString("ID_SISWA", data_siswa.getId());
                var.putString("NAMA_SISWA", data_siswa.getNama());
                var.putString("TTL_SISWA", data_siswa.getTtl());
                var.putString("JK_SISWA", data_siswa.getJk());
                var.putString("JURUSAN", data_siswa.getJurusan());
                var.putString("KELAS_SISWA", data_siswa.getKelas());
                var.putString("ALAMAT_SISWA", data_siswa.getAlamat());
                var.putString("EMAIL_SISWA", data_siswa.getEmail());
                var.putString("PASSWORD_SISWA", data_siswa.getPassword());
                fragment.setArguments(var);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.replace(R.id.screen_area, fragment, "DETAIL_SISWA");
                ft.commit();

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Sedang membaca basis data...");
        progress.setCancelable(false);
        progress.show();


        getDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                siswa.clear();

                for (DataSnapshot getData : dataSnapshot.getChildren()){
                    Siswa murid = getData.getValue(Siswa.class);
                    siswa.add(murid);
                }

                if (getActivity()!=null){
                    AkunSiswaList adapter = new AkunSiswaList(ListAkunFragment.this.getActivity(), siswa);
                    listView.setAdapter(adapter);
                    progress.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
