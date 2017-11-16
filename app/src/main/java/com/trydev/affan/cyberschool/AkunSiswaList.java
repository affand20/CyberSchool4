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
 * Created by affan on 28/10/17.
 */

public class AkunSiswaList extends ArrayAdapter<Siswa> {

    private Activity activity;
    private List<Siswa> listAkun;

    public AkunSiswaList(Activity activity, List<Siswa> listAkun){
        super(activity, R.layout.layout_list_siswa, listAkun);
        this.activity = activity;
        this.listAkun = listAkun;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        View listView = inflater.inflate(R.layout.layout_list_siswa, null, true);

        TextView nama = (TextView) listView.findViewById(R.id.nama);
        TextView nis = (TextView) listView.findViewById(R.id.nis);
        ImageView logo = (ImageView) listView.findViewById(R.id.logo_siswa);

        Siswa siswa = listAkun.get(position);

        nama.setText(siswa.getNama());
        nis.setText(siswa.getKelas()+" ("+siswa.getId()+")");

        if (siswa.getJk().equals("Laki-laki")){
            logo.setImageResource(R.drawable.avatar_male);
        } else if (siswa.getJk().equals("Perempuan")){
            logo.setImageResource(R.drawable.avatar_female);
        }

        return listView;
    }
}
