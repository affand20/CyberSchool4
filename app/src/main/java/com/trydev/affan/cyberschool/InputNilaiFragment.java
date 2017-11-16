package com.trydev.affan.cyberschool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by affan on 18/10/17.
 */

public class InputNilaiFragment extends Fragment {

    Spinner spinner, semester, mata_pelajaran;
    DatabaseReference db_nilai, data_siswa;
    ProgressDialog pd;
    List<String> akun, id;
    EditText tugas, uh, uts, uas;
    Button submit_nilai;
    String target;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AdminActivity) getActivity()).setActionBarTitle("Input Nilai Siswa");
        return inflater.inflate(R.layout.fragment_input_nilai, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = (Spinner) view.findViewById(R.id.daftar_siswa);
        semester = (Spinner) view.findViewById(R.id.semester);
        mata_pelajaran = (Spinner) view.findViewById(R.id.pelajaran);

        tugas = (EditText) view.findViewById(R.id.nilai_tugas);
        uh = (EditText) view.findViewById(R.id.nilai_uh);
        uts = (EditText) view.findViewById(R.id.nilai_uts);
        uas = (EditText) view.findViewById(R.id.nilai_uas);

        submit_nilai = (Button) view.findViewById(R.id.submit_nilai);

        data_siswa = FirebaseDatabase.getInstance().getReference("AkunSiswa");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                target = id.get(i);
                db_nilai = FirebaseDatabase.getInstance().getReference("nilai").child(target);
                Toast.makeText(getActivity(), target, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit_nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nilai_tugas = tugas.getText().toString();
                String nilai_uh = uh.getText().toString();
                String nilai_uts = uts.getText().toString();
                String nilai_uas = uas.getText().toString();
                String smt = semester.getSelectedItem().toString();
                String mapel = mata_pelajaran.getSelectedItem().toString();

                if (TextUtils.isEmpty(nilai_tugas)){
                    nilai_tugas = "Nilai tugas belum di input oleh admin";
                }
                if (TextUtils.isEmpty(nilai_uh)){
                    nilai_uh = "Nilai ulangan harian belum di input oleh admin";
                }
                if (TextUtils.isEmpty(nilai_uts)){
                    nilai_uts = "Nilai uts belum di input oleh admin";
                }
                if (TextUtils.isEmpty(nilai_uas)){
                    nilai_uas = "Nilai uas belum di input oleh admin";
                }

                Nilai nilai = new Nilai(mapel, nilai_tugas, nilai_uh, nilai_uts, nilai_uas);
                db_nilai.child(smt).child(mapel).setValue(nilai);

                Toast.makeText(getActivity(), "Input nilai berhasil !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Loading");
        pd.setMessage("Sedang membaca basis data...");
        pd.setCancelable(false);
        pd.show();

        data_siswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                akun = new ArrayList<>();
                id = new ArrayList<>();
                for (DataSnapshot getData: dataSnapshot.getChildren()){
                    Siswa siswa = getData.getValue(Siswa.class);
                    akun.add(siswa.getNama());
                    id.add(siswa.getId());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, akun);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
