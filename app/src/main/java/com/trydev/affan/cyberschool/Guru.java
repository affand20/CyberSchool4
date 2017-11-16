package com.trydev.affan.cyberschool;

/**
 * Created by affan on 02/11/17.
 */

public class Guru {

    private String nama;
    private String mata_ajar;
    private String alamat;

    public Guru(){}
    public Guru(String nama, String mata_ajar, String alamat) {
        this.nama = nama;
        this.mata_ajar = mata_ajar;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public String getMata_ajar() {
        return mata_ajar;
    }

    public String getAlamat() {
        return alamat;
    }
}
