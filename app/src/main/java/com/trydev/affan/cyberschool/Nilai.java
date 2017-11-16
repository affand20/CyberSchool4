package com.trydev.affan.cyberschool;

/**
 * Created by affan on 04/11/17.
 */

public class Nilai {
    private String nama;
    private String nilai_tugas;
    private String nilai_uh;
    private String nilai_uts;
    private String nilai_uas;

    public Nilai(){}

    public Nilai(String nama, String nilai_tugas, String nilai_uh, String nilai_uts, String nilai_uas) {
        this.nama = nama;
        this.nilai_tugas = nilai_tugas;
        this.nilai_uh = nilai_uh;
        this.nilai_uts = nilai_uts;
        this.nilai_uas = nilai_uas;
    }

    public String getNilai_tugas() {
        return nilai_tugas;
    }

    public String getNilai_uh() {
        return nilai_uh;
    }

    public String getNilai_uts() {
        return nilai_uts;
    }

    public String getNilai_uas() {
        return nilai_uas;
    }

    public String getNama() {
        return nama;
    }
}
