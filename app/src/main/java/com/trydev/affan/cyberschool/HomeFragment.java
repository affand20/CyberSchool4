package com.trydev.affan.cyberschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by affan on 18/10/17.
 */

public class HomeFragment extends Fragment {

    TextView greetings;

    EditText username, email, password;

    Button edit, save;

    String uname;

    DatabaseReference dbadmin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        uname = getArguments().getString("USERNAME");
        ((AdminActivity) getActivity()).setActionBarTitle(uname);
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        greetings = (TextView) view.findViewById(R.id.greetings);

        username = (EditText) view.findViewById(R.id.username);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);

        edit = (Button) view.findViewById(R.id.edit_info_admin);
        save = (Button) view.findViewById(R.id.simpan_perubahan);

        dbadmin = FirebaseDatabase.getInstance().getReference("AkunAdmin");
        getAdminInfo();

        greetings.setText("Selamat datang, "+uname);

        username.setFocusable(false);
        username.setClickable(false);
        username.setLongClickable(false);
        password.setFocusable(false);
        password.setClickable(false);
        password.setLongClickable(false);
        email.setFocusable(false);
        email.setClickable(false);
        email.setLongClickable(false);

        save.setEnabled(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setFocusableInTouchMode(true);
                username.setLongClickable(true);
                email.setFocusableInTouchMode(true);
                email.setLongClickable(true);
                password.setFocusableInTouchMode(true);
                password.setLongClickable(true);
                save.setEnabled(true);
                edit.setEnabled(false);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Memproses data...", Toast.LENGTH_SHORT).show();
                setAdminInfo();
                username.setFocusable(false);
                username.setClickable(false);
                username.setLongClickable(false);
                password.setFocusable(false);
                password.setClickable(false);
                password.setLongClickable(false);
                email.setFocusable(false);
                email.setClickable(false);
                email.setLongClickable(false);

                save.setEnabled(false);
                edit.setEnabled(true);
            }
        });

    }

    private void getAdminInfo(){
        dbadmin.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Admin a = dataSnapshot.getValue(Admin.class);
                username.setText(a.getNama());
                email.setText(a.getEmail());
                password.setText(a.getPassword());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setAdminInfo(){
        String nama = username.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        if (!TextUtils.isEmpty(nama)){
            if (!TextUtils.isEmpty(mail)){
                if (!TextUtils.isEmpty(pass)){
                    Admin admin = new Admin(nama, mail, pass);
                    dbadmin.child("Admin").setValue(admin);
                    Toast.makeText(getActivity(), "Berhasil !", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), "Gagal! Mohon cek form kembali", Toast.LENGTH_SHORT).show();
                }
            }  else{
                Toast.makeText(getActivity(), "Gagal! Mohon cek form kembali", Toast.LENGTH_SHORT).show();
            }
        }  else{
            Toast.makeText(getActivity(), "Gagal! Mohon cek form kembali", Toast.LENGTH_SHORT).show();
        }
    }
}
