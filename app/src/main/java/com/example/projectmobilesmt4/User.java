package com.example.projectmobilesmt4;

public class User {
    private String username,nama, unitInduk, kode_user;

    public User(String kode_user, String username, String nama, String unit_induk){
        this.kode_user=kode_user;
        this.username=username;
        this.nama=nama;
        this.unitInduk=unit_induk;
    }

    public String getKode_user() {
        return kode_user;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getUnit_induk() {
        return unitInduk;
    }
}
