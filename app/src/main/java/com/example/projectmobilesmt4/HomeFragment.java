package com.example.projectmobilesmt4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {
   Button btn_scan,btn_tambahData;
   EditText nomor_meter,kriteria_garansi,gangguan,tahun_buat,tahun_ganti_meter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_home, container, false);

        btn_scan = (Button) view1.findViewById(R.id.btn_scan);
        btn_tambahData = (Button) view1.findViewById(R.id.btn_tambahData);
        nomor_meter = (EditText) view1.findViewById(R.id.txt_nomorMeter);
        kriteria_garansi = (EditText) view1.findViewById(R.id.txt_kriteriaGaransi);
        gangguan = (EditText) view1.findViewById(R.id.txt_gangguan);
        tahun_buat = (EditText) view1.findViewById(R.id.txt_tahunBuat);
        tahun_ganti_meter = (EditText) view1.findViewById(R.id.txt_tahunGantiMeter);



        btn_scan.setOnClickListener(view -> {
            scanCode();
        });

        btn_tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahData();
            }
        });

        return view1;
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            nomor_meter.setText(result.getContents());
        }
    });

    private void tambahData(){
        final String sNomor_meter = nomor_meter.getText().toString().trim();
        final String sKriteria_garansi = kriteria_garansi.getText().toString().trim();
        final String sGangguan = gangguan.getText().toString().trim();
        final String sTahun_buat = tahun_buat.getText().toString().trim();
        final String sTahun_ganti_meter = tahun_ganti_meter.getText().toString().trim();

        //validasi form

        if(TextUtils.isEmpty(sNomor_meter)){
            nomor_meter.setError("Nomor Meter Tidak Boleh Kosong");
            nomor_meter.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sKriteria_garansi)){
            kriteria_garansi.setError("pastikan berisi garansi / tidak garansi");
            kriteria_garansi.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sGangguan)){
            gangguan.setError("Field ini harus di isi");
            gangguan.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sTahun_buat)){
            tahun_buat.setError("Field ini harus di isi");
            tahun_buat.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sTahun_ganti_meter)){
            tahun_ganti_meter.setError("Field ini harus di isi");
            tahun_ganti_meter.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.URL_CREATEDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                nomor_meter.setText("");
                                kriteria_garansi.setText("");
                                gangguan.setText("");
                                tahun_buat.setText("");
                                tahun_ganti_meter.setText("");
                            } else {
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })  {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("no_meter", sNomor_meter);
                params.put("kriteria_garansi", sKriteria_garansi);
                params.put("gangguan", sGangguan);
                params.put("tahun_buat", sTahun_buat);
                params.put("tahun_ganti", sTahun_ganti_meter);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
