package com.trydev.affan.cyberschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by affan on 08/11/17.
 */

public class DashboardUserFragment extends Fragment {

    DatabaseReference dbSiswa;
    Siswa siswa;
    TextView nama, ttl, jk, kelas, alamat, email, jurusan;

    EditText passworduser;

    Button ubahpassword, simpanpassword;

    ImageView avatar;

    String id, username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        username = getArguments().getString("UNAME");
        ((UserActivity) getActivity()).setActionBarTitle(username);
        return inflater.inflate(R.layout.fragment_dashboard_user, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nama = (TextView) view.findViewById(R.id.nama_siswa);
        ttl = (TextView) view.findViewById(R.id.tempat_tanggal_lahir);
        jk = (TextView) view.findViewById(R.id.jenis_kelamin);
        kelas = (TextView) view.findViewById(R.id.kelas_siswa);
        alamat = (TextView) view.findViewById(R.id.alamat_siswa);
        email = (TextView) view.findViewById(R.id.email_siswa);
        jurusan = (TextView) view.findViewById(R.id.jurusan_siswa);

        avatar = (ImageView) view.findViewById(R.id.logo_siswa);

        passworduser = (EditText) view.findViewById(R.id.password_siswa);

        passworduser.setFocusable(false);
        passworduser.setClickable(false);
        passworduser.setLongClickable(false);

        ubahpassword = (Button) view.findViewById(R.id.editpassword);
        simpanpassword = (Button) view.findViewById(R.id.simpanpassword);

        simpanpassword.setEnabled(false);

        dbSiswa = FirebaseDatabase.getInstance().getReference("AkunSiswa");
        siswa = null;

        id = getArguments().getString("ID");

        ubahpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passworduser.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passworduser.setFocusableInTouchMode(true);
                passworduser.setLongClickable(true);
                simpanpassword.setEnabled(true);
                ubahpassword.setEnabled(false);
            }
        });

        simpanpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(passworduser.getText().toString())){
//                    //belajar update data firebase
                    String tt = ttl.getText().toString();
                    String jenis = jk.getText().toString();
                    String jur = jurusan.getText().toString();
                    String kls = kelas.getText().toString();
                    String almt = alamat.getText().toString();
                    String mail = email.getText().toString();
                    String pass = passworduser.getText().toString();

                    Siswa updateSiswa = new Siswa(id, username, tt, jenis, jur, kls, almt, mail, pass);
                    dbSiswa.child(id).setValue(updateSiswa);
                    Toast.makeText(getActivity(), "Berhasil !", Toast.LENGTH_SHORT).show();
                    passworduser.setFocusable(false);
                    passworduser.setClickable(false);
                    passworduser.setLongClickable(false);
                    simpanpassword.setEnabled(false);
                    ubahpassword.setEnabled(true);
                } else{
                    Toast.makeText(getActivity(), "Gagal ! Password tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        dbSiswa.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                siswa = dataSnapshot.getValue(Siswa.class);
                if (getActivity() != null){
                    nama.setText(siswa.getNama());
                    ttl.setText(siswa.getTtl());
                    jk.setText(siswa.getJk());
                    kelas.setText(siswa.getKelas());
                    alamat.setText(siswa.getAlamat());
                    email.setText(siswa.getEmail());
                    passworduser.setText(siswa.getPassword());
                    jurusan.setText(siswa.getJurusan());
                    if (siswa.getJk().equals("Laki-laki")){
                        avatar.setImageResource(R.drawable.avatar_male);
                    } else if (siswa.getJk().equals("Perempuan")){
                        avatar.setImageResource(R.drawable.avatar_female);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
