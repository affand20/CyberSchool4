package com.trydev.affan.cyberschool;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by affan on 10/11/17.
 */

public class NilaiList extends ArrayAdapter<Nilai> {

    private Activity activity;
    private List<Nilai> listNilai;

    public NilaiList(Activity activity, List<Nilai> listNilai){
        super(activity, R.layout.layout_nilai_siswa, listNilai);
        this.activity = activity;
        this.listNilai =listNilai;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View listView = layoutInflater.inflate(R.layout.layout_nilai_siswa, null, true);

        TextView nama = (TextView) listView.findViewById(R.id.nama_mapel);
        TextView nilai_tugas = (TextView) listView.findViewById(R.id.nilai_tugas);
        TextView nilai_uh = (TextView) listView.findViewById(R.id.nilai_uh);
        TextView nilai_uts = (TextView) listView.findViewById(R.id.nilai_uts);
        TextView nilai_uas = (TextView) listView.findViewById(R.id.nilai_uas);
        ImageView logo_pelajaran = (ImageView) listView.findViewById(R.id.logo_pelajaran);

        Nilai nilai = listNilai.get(position);

        nama.setText(nilai.getNama());
        switch (nilai.getNama()){
            case "Matematika" :
                logo_pelajaran.setImageResource(R.drawable.matematika);
                break;
            case "Biologi" :
                logo_pelajaran.setImageResource(R.drawable.biologi);
                break;
            case "Kimia" :
                logo_pelajaran.setImageResource(R.drawable.kimia);
                break;
            case "Fisika" :
                logo_pelajaran.setImageResource(R.drawable.fisika);
                break;
            case "Bahasa Indonesia" :
                logo_pelajaran.setImageResource(R.drawable.bindo);
                break;
            case "Bahasa Inggris" :
                logo_pelajaran.setImageResource(R.drawable.bing);
                break;
            case "Pendidikan Kewarganegaraan" :
                logo_pelajaran.setImageResource(R.drawable.pkn);
                break;
            case "Sejarah Indonesia" :
                logo_pelajaran.setImageResource(R.drawable.sejarah);
                break;
            case "Fiqih" :
                logo_pelajaran.setImageResource(R.drawable.fiqh);
                break;
            case "Aswaja" :
                logo_pelajaran.setImageResource(R.drawable.aswaja);
                break;
            case "Aqidah Akhlak" :
                logo_pelajaran.setImageResource(R.drawable.aqidah);
                break;
            case "Al-Quran Hadits" :
                logo_pelajaran.setImageResource(R.drawable.qurdis);
                break;

        }
        nilai_tugas.setText(nilai.getNilai_tugas());
        nilai_uh.setText(nilai.getNilai_uh());
        nilai_uts.setText(nilai.getNilai_uts());
        nilai_uas.setText(nilai.getNilai_uas());


        return listView;
    }
}
