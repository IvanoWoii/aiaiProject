package com.example.projectmobilesmt4;

import android.app.ProgressDialog;
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

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {
   Button btn_scan,btn_tambahData;
   EditText nomor_meter,gangguan,tahun_buat,tahun_ganti_meter;
   Spinner kriteria_garansi;
   String selectedValue;
   ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_home, container, false);

        btn_scan = (Button) view1.findViewById(R.id.btn_scan);
        btn_tambahData = (Button) view1.findViewById(R.id.btn_tambahData);
        nomor_meter = (EditText) view1.findViewById(R.id.txt_nomorMeter);
        kriteria_garansi = (Spinner) view1.findViewById(R.id.txt_kriteriaGaransi);
        gangguan = (EditText) view1.findViewById(R.id.txt_gangguan);
        tahun_buat = (EditText) view1.findViewById(R.id.txt_tahunBuat);
        tahun_ganti_meter = (EditText) view1.findViewById(R.id.txt_tahunGantiMeter);
        progressBar = (ProgressBar) view1.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        String[] items = {"-- pilih opsi --","Garansi","Tidak Garansi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
        kriteria_garansi.setAdapter(adapter);

        kriteria_garansi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValue = kriteria_garansi.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_scan.setOnClickListener(view -> {
            scanCode();
        });

        btn_tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogWithProgressDialog();
            }
        });

        return view1;
    }

    private void showAlertDialogWithProgressDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Cek Data");
        builder.setMessage("Cek Data Apakah Sudah Benar?");
        builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.VISIBLE);
                tambahData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                }, 2000);
            }
        });
        builder.setNegativeButton("Cek lagi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.GONE);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
        final String sKriteria_garansi = selectedValue;
        final String sGangguan = gangguan.getText().toString().trim();
        final String sTahun_buat = tahun_buat.getText().toString().trim();
        final String sTahun_ganti_meter = tahun_ganti_meter.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateSekarang = dateFormat.format(today);
        final String sDateSekarang = dateSekarang;

        //validasi form

        if(TextUtils.isEmpty(sNomor_meter)){
            nomor_meter.setError("Nomor Meter Tidak Boleh Kosong");
            nomor_meter.requestFocus();
            return;
        }
        if(nomor_meter.length() > 11){
            nomor_meter.setError("Nomor Meter Melebih 11 Karakter");
            nomor_meter.requestFocus();
            return;
        }
        if (selectedValue == "-- pilih opsi --"){
            Toast.makeText(getActivity(), "Silahkan Pilih Opsi", Toast.LENGTH_SHORT).show();
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
                                gangguan.setText("");
                                kriteria_garansi.setSelection(0);
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
                params.put("tanggal_pendataan", sDateSekarang);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
