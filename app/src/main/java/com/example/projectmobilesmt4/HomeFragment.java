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
                String sNomor_meter = nomor_meter.getText().toString();
                String sKriteria_garansi = kriteria_garansi.getText().toString();
                String sGangguan = gangguan.getText().toString();
                String sTahun_buat = tahun_buat.getText().toString();
                String sTahun_ganti_meter = tahun_ganti_meter.getText().toString();

//                TambahData(sNomor_meter, sKriteria_garansi, sGangguan, sTahun_buat, sTahun_ganti_meter);
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
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle("Result");
//            builder.setMessage(result.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            }).show();
        }
    });



//    public void TambahData(final String nomor_meter, final String kriteria_garansi, final String gangguan, final String tahun_buat, final String tahun_ganti_meter) {
//        if(checkNetworkConnection()){
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_ADD_DATA_URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                String resp = jsonObject.getString("server_response");
//                                if (resp.equals("[{\"status\":\"OK\"}]")) {
//                                    Toast.makeText(getActivity(), "Tambah Data Berhasil", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                Log.e("JSON Error", "Error parsin JSON: " + e.getMessage());
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }) {
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("nomor_meter", nomor_meter);
//                    params.put("kriteria_garansi", kriteria_garansi);
//                    params.put("gangguan", gangguan);
//                    params.put("tahun_buat", tahun_buat);
//                    params.put("tahun_ganti_meter", tahun_ganti_meter);
//                    return params;
//                }
//            };
//
//            VolleyConnection.getInstance(getActivity()).addToRequestQue(stringRequest);
//
//        } else {
//            Toast.makeText(getActivity(), "Sambungan Gagal", Toast.LENGTH_SHORT).show();
//        }
//    }

    public boolean checkNetworkConnection() {
        Context context = getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return (capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)));
    }
}
