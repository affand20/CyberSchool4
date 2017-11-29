package com.trydev.affan.cyberschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by affan on 21/11/17.
 */
public class LoginActivityTest {

    DatabaseReference getDBAdmin = FirebaseDatabase.getInstance().getReference("AkunAdmin");
    DatabaseReference getDBSiswa = FirebaseDatabase.getInstance().getReference("AkunSiswa");
    List<Siswa> listAkunSiswa;
    List<Admin> listAkunAdmin;

    @Test
    public void onCreate() throws Exception {
    }

    @Test
    public void onStart() throws Exception {
        getDatabase();
        getDatabaseUser();
    }

    public void getDatabase() throws Exception{
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
            }
        });
    }
    public void getDatabaseUser() throws Exception{
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
            }
        });
    }

}