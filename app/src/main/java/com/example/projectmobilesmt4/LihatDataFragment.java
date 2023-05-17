package com.example.projectmobilesmt4;

import static com.android.volley.VolleyLog.TAG;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LihatDataFragment extends Fragment {

    Button btn_CekData;
    ImageView btn_Scan;
    EditText noMeter;
    TextView textNoMeter, textKriteriaGaransi, textGangguan, textTahunBuat, textTahunganti, textHasilNoMeter, textHasilKriteriaGaransi, textHasilGangguan, textHasilTahunBuat, textHasilTahunGanti;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lihat_data, container, false);

        btn_CekData = (Button) view.findViewById(R.id.btnCekData);
        btn_Scan = (ImageView) view.findViewById(R.id.btn_cekData);
        noMeter = (EditText) view.findViewById(R.id.nomorMeterLihatData);
        textNoMeter = (TextView) view.findViewById(R.id.textNoMeter);
        textKriteriaGaransi = (TextView) view.findViewById(R.id.textKriteriaGaransi);
        textGangguan = (TextView) view.findViewById(R.id.textGangguan);
        textTahunBuat = (TextView) view.findViewById(R.id.textTahunBuat);
        textTahunganti = (TextView) view.findViewById(R.id.textTahunGanti);
        textHasilNoMeter = (TextView) view.findViewById(R.id.textHasilNoMeter);
        textHasilKriteriaGaransi = (TextView) view.findViewById(R.id.textHasilKriteriGaransi);
        textHasilGangguan = (TextView) view.findViewById(R.id.textHasilGangguan);
        textHasilTahunBuat = (TextView) view.findViewById(R.id.textHasilTahunBuat);
        textHasilTahunGanti = (TextView) view.findViewById(R.id.textHasilTahunGanti);

        btn_Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        btn_CekData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    cekData();

            }
        });


        return view;
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
            noMeter.setText(result.getContents());
        }
    });

    private void cekData(){
        final String sNoMeter = noMeter.getText().toString().trim();

        if(sNoMeter.isEmpty() || sNoMeter.equals("0")){
            noMeter.setError("Mohon Masukan No Meter");
            noMeter.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.URL_CEK_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                JSONObject dataJson = obj.getJSONObject("user");

                                String hasilNoMeter = dataJson.getString("no_meter");
                                String hasilKriteriaGaransi = dataJson.getString("kriteria_garansi");
                                String hasilGangguan = dataJson.getString("gangguan");
                                String hasilTahunBuat = dataJson.getString("tahun_buat");
                                String hasilTahunGanti = dataJson.getString("tahun_ganti");
                                String hasilDataDibuat = dataJson.getString("data_dibuat");

                                textHasilNoMeter.setText(hasilNoMeter);
                                if(hasilKriteriaGaransi.equals("Garansi")){
                                    textHasilKriteriaGaransi.setText(hasilKriteriaGaransi);
                                    textHasilKriteriaGaransi.setTextColor(Color.GREEN);
                                } else {
                                    textHasilKriteriaGaransi.setText(hasilKriteriaGaransi);
                                    textHasilKriteriaGaransi.setTextColor(Color.RED);
                                }
                                textHasilGangguan.setText(hasilGangguan);
                                textHasilTahunBuat.setText(hasilTahunBuat);
                                textHasilTahunGanti.setText(hasilTahunGanti);

                            } else {
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("no_meter", sNoMeter);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}