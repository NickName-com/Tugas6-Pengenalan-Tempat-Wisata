package com.k.tempatwisata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView image;
    private TextView nama,lokasi,deskripsi,bintang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        image = findViewById(R.id.image);
        nama = findViewById(R.id.nama);
        lokasi = findViewById(R.id.lokasi);
        bintang = findViewById(R.id.bintang);
        deskripsi = findViewById(R.id.deskripsi);

        Glide.with(getApplicationContext())
                .load(intent.getStringExtra("image"))
                .centerCrop()
                .into(image);

        nama.setText(intent.getStringExtra("nama"));
        lokasi.setText(intent.getStringExtra("lokasi"));
        bintang.setText(intent.getStringExtra("bintang"));
        deskripsi.setText(intent.getStringExtra("deskripsi"));
    }
}