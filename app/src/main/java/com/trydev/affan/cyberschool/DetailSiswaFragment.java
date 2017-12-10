package com.trydev.affan.cyberschool;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by affan on 04/11/17.
 */

public class DetailSiswaFragment extends Fragment {

    String ID_SISWA;
    public static String NAMA_SISWA;
    String TTL_SISWA;
    public static String JK_SISWA;
    String KELAS_SISWA;
    String ALAMAT_SISWA;
    String EMAIL_SISWA;
    String PASSWORD_SISWA;
    String JURUSAN;
    String jenis;

    EditText nama,ttl, alamat, email, kelas, password;
    Button edit, simpan, hapus;
    RadioGroup jk;
    RadioButton lk, pr;

    ImageView logo;

    DatabaseReference db_data_siswa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AdminActivity) getActivity()).setActionBarTitle(getArguments().getString("NAMA_SISWA"));
        return inflater.inflate(R.layout.fragment_detail_siswa, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ID_SISWA = getArguments().getString("ID_SISWA");
        NAMA_SISWA = getArguments().getString("NAMA_SISWA");
        TTL_SISWA = getArguments().getString("TTL_SISWA");
        JK_SISWA = getArguments().getString("JK_SISWA");
        JURUSAN = getArguments().getString("JURUSAN");
        KELAS_SISWA = getArguments().getString("KELAS_SISWA");
        ALAMAT_SISWA = getArguments().getString("ALAMAT_SISWA");
        EMAIL_SISWA = getArguments().getString("EMAIL_SISWA");
        PASSWORD_SISWA = getArguments().getString("PASSWORD_SISWA");

        nama = (EditText) view.findViewById(R.id.nama_siswa);
        ttl = (EditText) view.findViewById(R.id.tempat_tanggal_lahir);
        alamat = (EditText) view.findViewById(R.id.alamat_siswa);
        email = (EditText) view.findViewById(R.id.email_siswa);
        kelas = (EditText) view.findViewById(R.id.kelas_siswa);
        password = (EditText) view.findViewById(R.id.password_siswa);
        jk = (RadioGroup) view.findViewById(R.id.jenis_kelamin);
        lk = (RadioButton) view.findViewById(R.id.lk);
        pr = (RadioButton) view.findViewById(R.id.pr);
        logo = (ImageView) view.findViewById(R.id.logo_siswa);

        jenis = JK_SISWA;

        db_data_siswa = FirebaseDatabase.getInstance().getReference("AkunSiswa");

        nama.setText(NAMA_SISWA);
        ttl.setText(TTL_SISWA);
        alamat.setText(ALAMAT_SISWA);
        email.setText(EMAIL_SISWA);
        kelas.setText(KELAS_SISWA);
        password.setText(PASSWORD_SISWA);

        if (JK_SISWA.equals("Laki-laki")){
            lk.setChecked(true);
            logo.setImageResource(R.drawable.avatar_male);
        } else if(JK_SISWA.equals("Perempuan")){
            pr.setChecked(true);
            logo.setImageResource(R.drawable.avatar_female);
        }


        nama.setFocusable(false);
        nama.setClickable(false);
        nama.setLongClickable(false);
        ttl.setFocusable(false);
        ttl.setClickable(false);
        ttl.setLongClickable(false);
        alamat.setFocusable(false);
        alamat.setClickable(false);
        alamat.setLongClickable(false);
        email.setFocusable(false);
        email.setClickable(false);
        email.setLongClickable(false);
        kelas.setFocusable(false);
        kelas.setClickable(false);
        kelas.setLongClickable(false);
        password.setFocusable(false);
        password.setClickable(false);
        password.setLongClickable(false);
        jk.setFocusable(false);
        jk.setClickable(false);
        jk.setLongClickable(false);
        lk.setEnabled(false);
        pr.setEnabled(false);

        jk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.lk : jenis = "Laki-laki";
                        logo.setImageResource(R.drawable.avatar_male);
                    break;

                    case R.id.pr : jenis = "Perempuan";
                        logo.setImageResource(R.drawable.avatar_female);
                    break;
                }
            }

        });

        edit = (Button) view.findViewById(R.id.edit_info);
        simpan = (Button) view.findViewById(R.id.simpan_perubahan);
        hapus = (Button) view.findViewById(R.id.hapus_akun);

        simpan.setEnabled(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setEnabled(false);
                simpan.setEnabled(true);
                nama.setFocusableInTouchMode(true);
                nama.setLongClickable(true);
                ttl.setFocusableInTouchMode(true);
                ttl.setLongClickable(true);
                alamat.setFocusableInTouchMode(true);
                alamat.setLongClickable(true);
                email.setFocusableInTouchMode(true);
                email.setLongClickable(true);
                kelas.setFocusableInTouchMode(true);
                kelas.setLongClickable(true);
                password.setFocusableInTouchMode(true);
                password.setLongClickable(true);
                jk.setFocusable(true);
                jk.setClickable(true);
                jk.setLongClickable(true);
                lk.setEnabled(true);
                pr.setEnabled(true);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm = nama.getText().toString();
                String tt = ttl.getText().toString();
                String kls = kelas.getText().toString();
                String almt = alamat.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if (!TextUtils.isEmpty(nm)){
                    if (!TextUtils.isEmpty(tt)){
                        if (!TextUtils.isEmpty(kls)){
                            if (!TextUtils.isEmpty(almt)){
                                if (!TextUtils.isEmpty(mail)){
                                    if (!TextUtils.isEmpty(pass)){
                                        Siswa siswa = new Siswa(ID_SISWA, nm, tt, jenis, JURUSAN , kls, almt, mail, pass);
                                        db_data_siswa.child(ID_SISWA).setValue(siswa);
                                        Toast.makeText(getActivity(), "Berhasil !", Toast.LENGTH_SHORT).show();
                                        nama.setFocusable(false);
                                        nama.setClickable(false);
                                        nama.setLongClickable(false);
                                        ttl.setFocusable(false);
                                        ttl.setClickable(false);
                                        ttl.setLongClickable(false);
                                        alamat.setFocusable(false);
                                        alamat.setClickable(false);
                                        alamat.setLongClickable(false);
                                        email.setFocusable(false);
                                        email.setClickable(false);
                                        email.setLongClickable(false);
                                        kelas.setFocusable(false);
                                        kelas.setClickable(false);
                                        kelas.setLongClickable(false);
                                        password.setFocusable(false);
                                        password.setClickable(false);
                                        password.setLongClickable(false);
                                        jk.setFocusable(false);
                                        jk.setClickable(false);
                                        jk.setLongClickable(false);
                                        lk.setEnabled(false);
                                        pr.setEnabled(false);
                                        simpan.setEnabled(false);
                                        edit.setEnabled(true);
                                    } else{
                                        Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                                    }
                                } else{
                                    Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal ! Mohon cek form kembali.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusAkun(ID_SISWA);
                Fragment fragment = new ListAkunFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.replace(R.id.screen_area, fragment);
                ft.commit();
            }
        });

    }

    private void hapusAkun(String id){
        DatabaseReference s = FirebaseDatabase.getInstance().getReference("AkunSiswa").child(id);
        DatabaseReference n = FirebaseDatabase.getInstance().getReference("nilai").child(id);
        s.removeValue();
        n.removeValue();
        Toast.makeText(getActivity(), "Berhasil menghapus!", Toast.LENGTH_SHORT).show();
    }
}

