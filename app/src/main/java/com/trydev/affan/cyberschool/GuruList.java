package com.trydev.affan.cyberschool;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by affan on 02/11/17.
 */

public class GuruList extends ArrayAdapter<Guru> {

    private Activity activity;
    private List<Guru> guruList;

    public GuruList(Activity activity, List<Guru> guruList){
        super(activity, R.layout.layout_list_guru, guruList);
        this.activity = activity;
        this.guruList = guruList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View listView = inflater.inflate(R.layout.layout_list_guru, null, true);

        TextView nama_guru = (TextView) listView.findViewById(R.id.nama_guru);
        TextView mata_ajar = (TextView) listView.findViewById(R.id.mata_ajar);
        TextView alamat_guru = (TextView) listView.findViewById(R.id.alamat_guru);

        Guru guru = guruList.get(position);

        nama_guru.setText(guru.getNama());
        mata_ajar.setText(guru.getMata_ajar());
        alamat_guru.setText(guru.getAlamat());

        return listView;
    }
}
