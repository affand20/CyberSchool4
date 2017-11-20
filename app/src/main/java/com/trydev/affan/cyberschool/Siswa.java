package com.trydev.affan.cyberschool;

/**
 * Created by affan on 28/10/17.
 */

public class Siswa {
    private String nama;
    private String ttl;
    private String jurusan;
    private String kelas;
    private String alamat;
    private String id;
    private String jk;
    private String email;
    private String password;

    public Siswa(){}
    public Siswa(String id, String nama, String ttl, String jk, String jurusan, String kelas, String alamat, String email, String password) {
        this.id = id;
        this.nama = nama;
        this.ttl = ttl;
        this.jk = jk;
        this.jurusan = jurusan;
        this.kelas = kelas;
        this.alamat = alamat;
        this.email = email;
        this.password = password;
    }

    public String getId(){
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getTtl() {
        return ttl;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getJk() {return jk;}

    public String getKelas() {
        return kelas;
    }

    public String getAlamat() {return alamat;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}
}
