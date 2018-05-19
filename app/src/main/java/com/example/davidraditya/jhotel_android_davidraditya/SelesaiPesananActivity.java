package com.example.davidraditya.jhotel_android_davidraditya;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiPesananActivity extends AppCompatActivity {
    private int currentUserId;
    private int id_pesanan;
    private int biaya_akhir;
    private int jumlah_hari;
    private String tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        Intent selesaiPesananIntent = getIntent();
        Bundle b = selesaiPesananIntent.getExtras();
        if(b!=null){
            currentUserId = b.getInt("id_customer");
        }

        final Button batal = findViewById(R.id.batal);
        final Button selesai = findViewById(R.id.selesai);
        final RelativeLayout tampilan = findViewById(R.id.tampilan);
        tampilan.setVisibility(View.INVISIBLE);

        fetchPesanan();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                final String id = String.valueOf(id_pesanan);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Pesanan Berhasil Dibatalkan").create().show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Pesanan Gagal Dibatalkan.").create().show();
                        }
                    }
                };
                PesananBatalRequest batalRequest = new PesananBatalRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(batalRequest);
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                final String id = String.valueOf(id_pesanan);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                                builder.setMessage("Pesanan Berhasil Diselesaikan").create().show();
                            }
                        } catch (JSONException e) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelesaiPesananActivity.this);
                            builder.setMessage("Pesanan Gagal Diselesaikan").create().show();
                        }
                    }
                };
                PesananSelesaiRequest selesaiRequest = new PesananSelesaiRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(selesaiRequest);
            }
        });
    }

    public void fetchPesanan(){
        final String id_customer = String.valueOf(currentUserId);

        Response.Listener<String> responseListener = new Response.Listener<String> () {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse!=null) {
                        final TextView idPesanan = findViewById(R.id.idPesanan);
                        final TextView biaya = findViewById(R.id.biaya);
                        final TextView tanggal_pesan = findViewById(R.id.tanggal_pesan);
                        final TextView hari = findViewById(R.id.hari);
                        final Button batal = findViewById(R.id.batal);
                        final Button selesai = findViewById(R.id.selesai);

                        final RelativeLayout tampilan = findViewById(R.id.tampilan);
                        id_pesanan = jsonResponse.getInt("id");
                        biaya_akhir = jsonResponse.getInt("biaya");
                        jumlah_hari = jsonResponse.getInt("jumlahHari");
                        tanggal = jsonResponse.getString("tanggalPesan");

                        idPesanan.setText(String.valueOf(id_pesanan));
                        biaya.setText(String.valueOf(biaya_akhir));
                        hari.setText(String.valueOf(jumlah_hari));
                        tanggal_pesan.setText(tanggal);
                        tampilan.setVisibility(View.VISIBLE);
                    } else {
                        Intent gagalInt = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                        gagalInt.putExtra("id_customer", currentUserId);
                        SelesaiPesananActivity.this.startActivity(gagalInt);
                    }
                } catch (JSONException e) {
                    Intent gagalInt = new Intent(SelesaiPesananActivity.this, MainActivity.class);
                    gagalInt.putExtra("id_customer", currentUserId);
                    SelesaiPesananActivity.this.startActivity(gagalInt);
                }
            }
        };
        PesananFetchRequest fetchPesananRequest = new PesananFetchRequest(id_customer, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(fetchPesananRequest);
    }
}