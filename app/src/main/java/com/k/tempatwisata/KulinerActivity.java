package com.k.tempatwisata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KulinerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListviewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<ModelList> arrayList = new ArrayList<ModelList>();
    private String url = "https://kangoding.com/kuliner.php";
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuliner);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("List Kuliner");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.listview);
        adapter = new ListviewAdapter(this, arrayList, "vertical");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), anError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}