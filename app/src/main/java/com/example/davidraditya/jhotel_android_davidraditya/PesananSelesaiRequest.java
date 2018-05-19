package com.example.davidraditya.jhotel_android_davidraditya;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananSelesaiRequest extends StringRequest {
    //private static String userId;
    private static final String PesananSelesai_URL = "http://192.168.43.79:8080/finishpesanan";
    private Map<String, String> params;

    public PesananSelesaiRequest(String id_pesanan, Response.Listener<String> listener) {
        super(Method.POST, PesananSelesai_URL, listener, null);
        params = new HashMap<>();
        params.put("id_pesanan",id_pesanan);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
