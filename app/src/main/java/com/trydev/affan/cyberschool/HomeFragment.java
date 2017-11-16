package com.trydev.affan.cyberschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by affan on 18/10/17.
 */

public class HomeFragment extends Fragment {

    TextView greetings;
    Button set;

    String uname;

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

        greetings.setText("Selamat datang, "+uname);

    }
}
