package com.k.tempatwisata;

public class ModelList {
    public  String image,name,lokasi,deskripsi;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public ModelList(String image, String name, String lokasi, String deskripsi){
        this.image = image;
        this.name = name;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
    }
}
