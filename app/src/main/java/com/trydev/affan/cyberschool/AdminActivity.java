package com.trydev.affan.cyberschool;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int count;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        uname = getIntent().getStringExtra("USERNAME");

        //untuk memanggil default fragment yang diinginkan ketika aplikasi baru dijalankan
        Fragment fragment = new HomeFragment();

        Bundle var = new Bundle();
        var.putString("USERNAME", uname);
        fragment.setArguments(var);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

        tx.replace(R.id.screen_area, fragment);
        tx.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //untuk menset default item yang di select pada navigation drawer ketika activity baru dibuat
        navigationView.setCheckedItem(R.id.home);


        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (count==0){
//                super.onBackPressed();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Anda yakin mau logout ?")
                        .setCancelable(true)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AdminActivity.this.finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                navigationView.setCheckedItem(R.id.dashboard);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Fragment fragment2 = null;
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,0,0);

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("DETAIL_SISWA");

                if (fragment!=null && fragment.isVisible()){
                    fragment2 = new ListAkunFragment();
                    ft.replace(R.id.screen_area, fragment2);
                    count = 1;
                    ft.commit();
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setCheckedItem(R.id.list_akun);
                } else{
                    fragment2 = new HomeFragment();
                    Bundle var = new Bundle();
                    var.putString("USERNAME", uname);
                    fragment2.setArguments(var);
                    ft.replace(R.id.screen_area, fragment2);
                    count = 0;
                    ft.commit();
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    navigationView.setCheckedItem(R.id.home);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.home) {
            fragment = new HomeFragment();
            count = 0;
        } else if (id == R.id.input_nilai) {
            fragment = new InputNilaiFragment();
            count = 1;
        } else if (id == R.id.buat_akun){
            fragment = new BuatAkunFragment();
            count = 1;
        } else if (id == R.id.list_akun){
            fragment = new ListAkunFragment();
            count = 1;
        } else if (id == R.id.input_bio_guru){
            fragment = new TambahGuruFragment();
            count = 1;
        } else if (id == R.id.list_guru){
            fragment = new ListGuruFragment();
            count = 1;
        } else if (id == R.id.about){

        } else if (id == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Anda yakin mau logout ?")
                    .setCancelable(true)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdminActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                            navigationView.setCheckedItem(R.id.dashboard);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        if (fragment!=null){
            Bundle var = new Bundle();
            var.putString("USERNAME", uname);
            fragment.setArguments(var);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            //setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            //^^^ untuk memberi animasi ketika berpindah fragment
            ft.replace(R.id.screen_area, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
