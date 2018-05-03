package com.example.davidraditya.jhotel_android_davidraditya;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    private static final String Regis_URL = "http://10.5.76.236:8080/logincust";
    private Map<String, String> params;

    public MenuRequest(Response.Listener<String> listener) {
        super(Method.POST, Regis_URL, listener, null);
        params = new HashMap<>();
        }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}