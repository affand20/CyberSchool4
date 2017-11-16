package com.trydev.affan.cyberschool;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    DatabaseReference getDBAdmin = FirebaseDatabase.getInstance().getReference("AkunAdmin");
    DatabaseReference getDBSiswa = FirebaseDatabase.getInstance().getReference("AkunSiswa");
    List<Siswa> listAkunSiswa;
    List<Admin> listAkunAdmin;

    ProgressDialog pd;

    Button button;

    public static String uname;
    public static String idSiswa;

    public static Siswa akun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.loginbutton);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        listAkunAdmin = new ArrayList<>();
        listAkunSiswa = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if (!TextUtils.isEmpty(mail)){
                    if (!TextUtils.isEmpty(pass)){
                        boolean ketemu = false;

                        for (int i=0; i<listAkunAdmin.size(); i++){
                            Admin admin = listAkunAdmin.get(i);
                            if (admin.getEmail().equals(mail)){
                                if (admin.getPassword().equals(pass)){
                                    uname = admin.getNama();
                                    ketemu = true;
                                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                    intent.putExtra("USERNAME", uname);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }

                        if (!ketemu){
                            for (int i = 0; i < listAkunSiswa.size(); i++) {
                                Siswa siswa = listAkunSiswa.get(i);
                                if (siswa.getEmail().equals(mail)){
                                    if (siswa.getPassword().equals(pass)){
                                        uname = siswa.getNama();
                                        idSiswa = siswa.getId();
                                        ketemu = true;
                                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                        intent.putExtra("ID_SISWA", idSiswa);
                                        intent.putExtra("USERNAME", uname);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                            }
                        }

                        if (!ketemu){
                            Toast.makeText(LoginActivity.this, "Email/Sandi tidak cocok !", Toast.LENGTH_SHORT).show();
                        }

                    } else{
                        Toast.makeText(getApplicationContext(), "Email/Sandi tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Email/Sandi tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Sedang membaca basis data...");
        pd.show();

        getDBAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAkunAdmin.clear();

                for (DataSnapshot getData : dataSnapshot.getChildren()){
                    Admin a = getData.getValue(Admin.class);
                    listAkunAdmin.add(a);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Mohon periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
            }
        });

        getDBSiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAkunSiswa.clear();

                for (DataSnapshot getData : dataSnapshot.getChildren()){
                    Siswa s = getData.getValue(Siswa.class);
                    listAkunSiswa.add(s);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Mohon periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
            }
        });
        pd.dismiss();
    }
}
