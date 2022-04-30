package com.k.tempatwisata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlideAdapter slideAdapter;
    private Toolbar toolbar;
    private String url = "https://kangoding.com/test.php";
    private ArrayList<ModelList> arrayList = new ArrayList<ModelList>();
    private ArrayList<ModelList> arrayRekomendasi = new ArrayList<ModelList>();
    private LinearLayout tempatwisata,kuliner,about;

    private ProgressDialog dialog;
    private RecyclerView listview;
    private ListviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        slideAdapter = new SlideAdapter(this, arrayList);
        viewPager.setAdapter(slideAdapter);

        listview = findViewById(R.id.listview);
        adapter = new ListviewAdapter(this, arrayRekomendasi, "horizontal");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listview.setLayoutManager(mLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.setAdapter(adapter);

        tempatwisata = findViewById(R.id.tempatwisata);
        tempatwisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(MainActivity.this, WisataActivity.class);
                startActivity(move);
            }
        });

        kuliner = findViewById(R.id.kuliner);
        kuliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(MainActivity.this, KulinerActivity.class);
                startActivity(move); //ini buat pindah activity
            }
        });

        about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(move); //ini buat pindah activity
            }
        });

        getList();

    }

    public void getList(){
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Mohon Tunggu");
        dialog.show();
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dialog.dismiss();
                        getRekomendasi();
                        for(int i =0;i<response.length();i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                String image = obj.getString("image");
                                String lokasi = obj.getString("lokasi");
                                String name = obj.getString("name");
                                String bintang = obj.getString("bintang");
                                String deskripsi = obj.getString("deskripsi");

                                ModelList item = new ModelList(image,name,lokasi,bintang,deskripsi);
                                arrayList.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Gagal mendapatkan list", Toast.LENGTH_LONG).show();
                            }
                        }
                        slideAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), anError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void getRekomendasi(){
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading Rekomendasi");
        dialog.show();
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dialog.dismiss();
                        for(int i =0;i<3;i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                String image = obj.getString("image");
                                String lokasi = obj.getString("lokasi");
                                String name = obj.getString("name");
                                String bintang = obj.getString("bintang");
                                String deskripsi = obj.getString("deskripsi");

                                ModelList item = new ModelList(image,name,lokasi,bintang,deskripsi);
                                arrayRekomendasi.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Gagal mendapatkan list", Toast.LENGTH_LONG).show();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), anError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}