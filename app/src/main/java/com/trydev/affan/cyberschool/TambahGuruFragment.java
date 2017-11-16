package com.trydev.affan.cyberschool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by affan on 02/11/17.
 */

public class TambahGuruFragment extends Fragment {

    EditText nama, mapel, alamat;
    Button submit;
    DatabaseReference db_guru;
    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AdminActivity) getActivity()).setActionBarTitle("Input Informasi Guru");
        return inflater.inflate(R.layout.fragment_tambah_daftar_guru, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nama = (EditText) view.findViewById(R.id.nama_guru);
        mapel = (EditText) view.findViewById(R.id.mata_ajar);
        alamat = (EditText) view.findViewById(R.id.alamat_guru);

        submit = (Button) view.findViewById(R.id.submit);

        db_guru = FirebaseDatabase.getInstance().getReference("Guru");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Memproses data...", Toast.LENGTH_SHORT).show();
                progress = new ProgressDialog(getActivity());
                progress.setTitle("Loading");
                progress.setMessage("Memproses data...");
                progress.setCancelable(false);
                progress.show();
                String nm = nama.getText().toString();
                String mp = mapel.getText().toString();
                String almt = alamat.getText().toString();
                if (!TextUtils.isEmpty(nm)){
                    if (!TextUtils.isEmpty(mp)){
                        if (!TextUtils.isEmpty(almt)){
                            Guru guru = new Guru(nm, mp, almt);
                            db_guru.child(nm).setValue(guru);
                            Toast.makeText(getActivity().getApplicationContext(), "Berhasil !", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali", Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            }
        });
    }
}
