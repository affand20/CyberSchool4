package com.trydev.affan.cyberschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

import static android.content.ContentValues.TAG;

/**
 * Created by affan on 28/10/17.
 */

public class BuatAkunFragment extends Fragment {

    EditText input_nama, ttl, nomor_kelas, alamat, email, password;
    Button submit;
    RadioGroup jenis_kelamin, list_jurusan, tingkatan_kelas;
    RadioButton lk, pr, ipa, ips, bahasa, sepuluh, sebelas, duabelas;
    String jk="";
    String jurusan="";
    String kls="";

    String pass;

    DatabaseReference db;

    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AdminActivity) getActivity()).setActionBarTitle("Buat Akun Siswa");
        return inflater.inflate(R.layout.fragment_buat_akun, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        input_nama = (EditText) view.findViewById(R.id.input_nama);
        ttl = (EditText) view.findViewById(R.id.ttl);
        nomor_kelas = (EditText) view.findViewById(R.id.nomor_kelas);
        alamat = (EditText) view.findViewById(R.id.alamat);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);

        submit = (Button) view.findViewById(R.id.submit);

        jenis_kelamin = (RadioGroup) view.findViewById(R.id.jenis_kelamin);
        list_jurusan = (RadioGroup) view.findViewById(R.id.list_jurusan);
        tingkatan_kelas = (RadioGroup) view.findViewById(R.id.tingkatan_kelas);

        lk = (RadioButton) view.findViewById(R.id.lk);
        pr = (RadioButton) view.findViewById(R.id.pr);
        ipa = (RadioButton) view.findViewById(R.id.ipa);
        ips = (RadioButton) view.findViewById(R.id.ips);
        bahasa = (RadioButton) view.findViewById(R.id.bahasa);
        sepuluh = (RadioButton) view.findViewById(R.id.sepuluh);
        sebelas = (RadioButton) view.findViewById(R.id.sebelas);
        duabelas = (RadioButton) view.findViewById(R.id.duabelas);

        db = FirebaseDatabase.getInstance().getReference("AkunSiswa");

        jenis_kelamin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.lk : jk="Laki-laki";
                        break;
                    case R.id.pr : jk="Perempuan";
                        break;
                }
            }
        });

        list_jurusan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.ipa : jurusan="IPA";
                        break;
                    case R.id.ips : jurusan="IPS";
                        break;
                    case R.id.bahasa : jurusan="BAHASA";
                        break;
                }
            }
        });

        tingkatan_kelas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sepuluh : kls="10";
                        break;
                    case R.id.sebelas: kls="11";
                        break;
                    case R.id.duabelas : kls="12";
                        break;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Memproses data...");
                pd.setCancelable(false);
                pd.show();
//                Toast.makeText(getActivity().getApplicationContext(), "Memproses data...", Toast.LENGTH_SHORT).show();
                getData();
            }
        });
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final String nama = input_nama.getText().toString();
                final String ttl2 = ttl.getText().toString();
                final String nKelas = nomor_kelas.getText().toString();
                final String almt = alamat.getText().toString();
                final String mail = email.getText().toString();
                pass = password.getText().toString();

                if (!TextUtils.isEmpty(nama)){
                    if (jk!=""){
                        if (!TextUtils.isEmpty(ttl2)){
                            if (kls!=""){
                                if (jurusan!=""){
                                    if (!TextUtils.isEmpty(nKelas)){
                                        if (!TextUtils.isEmpty(almt)){
                                            if (!TextUtils.isEmpty(mail)){
                                                if (TextUtils.isEmpty(pass)){
                                                    pass = "12345678";
                                                }
                                                String key = db.push().getKey();
                                                String klas = kls+" "+jurusan+" "+nKelas;
                                                Siswa siswa = new Siswa(key, nama, ttl2, jk, jurusan, klas, almt, mail, pass);
                                                db.child(key).setValue(siswa);
                                                pd.dismiss();
                                                Toast.makeText(getActivity().getApplicationContext(), "Berhasil !", Toast.LENGTH_SHORT).show();
                                                Fragment fragment = new ListAkunFragment();
                                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                                ft.replace(R.id.screen_area, fragment);
                                                ft.commit();
                                            } else{
                                                pd.dismiss();
                                                Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else{
                                            pd.dismiss();
                                            Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else{
                                        pd.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                                    }
                                } else{
                                    pd.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                pd.dismiss();
                                Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            pd.dismiss();
                            Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        pd.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    pd.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1500);

    }
}
