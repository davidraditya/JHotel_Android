package com.example.davidraditya.jhotel_android_davidraditya;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Hotel> listHotel = new ArrayList<Hotel>();
    private ArrayList<Room> listRoom = new ArrayList<Room>();
    private HashMap<Hotel, ArrayList<Room>> childMapping = new HashMap<Hotel, ArrayList<Room>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if(jsonResponse != null){
                        JSONObject e = jsonResponse.getJSONObject(0).getJSONObject("hotel");
                        JSONObject lokasi = e.getJSONObject("lokasi");
                        listHotel.add(new Hotel(e.getString("nama"), new Lokasi(lokasi.getInt("x_coord"), lokasi.getInt("y_coord"), lokasi.getString("deskripsiLokasi")),
                                e.getInt("bintang"), e.getInt("id")));
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Login Success")
                                .create()
                                .show();
                    }
                } catch (JSONException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Login Failed")
                            .create()
                            .show();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}
