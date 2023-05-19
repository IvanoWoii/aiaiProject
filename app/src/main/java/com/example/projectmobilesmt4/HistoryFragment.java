package com.example.projectmobilesmt4;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class HistoryFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;
    private ProgressBar progressBar;
    private TextView textProgress,textJam;
    private static final int PROGRESS_DELAY = 2000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_data_master, dataList);
        progressBar = view.findViewById(R.id.progressBar);
        textProgress = (TextView) view.findViewById(R.id.textProgress);
        textJam = (TextView) view.findViewById(R.id.tanggalJamTextView);
        listView.setAdapter(adapter);

        //jam realtime
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateTanggalJam();
                handler.postDelayed(this, 1000); // Perbarui setiap 1 detik
            }
        };
        handler.postDelayed(runnable, 0); // Mulai perbarui tanggal dan jam

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getData();
        } else {
            progressBar.setVisibility(View.GONE);
            textProgress.setGravity(Gravity.CENTER);
            textProgress.setText("Tidak Bisa Terhubung Ke Server Cek Kembali Koneksi Anda");
        }



        return view;
    }

    private void updateTanggalJam() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        String tanggalJam = dateFormat.format(calendar.getTime());
        textJam.setText(tanggalJam);
    }


    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        textProgress.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run(){
                                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, DbContract.URL_GET_DATA, null,
                                            new Response.Listener<JSONArray>() {
                                                @Override
                                                public void onResponse(JSONArray response) {
                                                    try {
                                                        for (int i = 0; i < response.length(); i++) {
                                                            JSONObject jsonObject = response.getJSONObject(i);
                                                            String data = "No Meter: " + jsonObject.getString("no_meter") + "\n" +
                                                                    "Kriteria Garansi: " + jsonObject.getString("kriteria_garansi") + "\n" +
                                                                    "Gangguan: " + jsonObject.getString("gangguan") + "\n" +
                                                                    "Tahun Buat: " + jsonObject.getString("tahun_buat") + "\n" +
                                                                    "Tahun Ganti: " + jsonObject.getString("tahun_ganti") + "\n" +
                                                                    "Data Dibuat: " + jsonObject.getString("data_dibuat");
                                                            dataList.add(data);
                                                        }
                                                        progressBar.setVisibility(View.GONE);
                                                        textProgress.setVisibility(View.GONE);

                                                        adapter.notifyDataSetChanged();
                                                    } catch (JSONException e) {
                                                        Log.e("Error", e.getMessage());
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.e("Error", error.getMessage());
                                                }
                                            });
                                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
                                }
        }, PROGRESS_DELAY);

    }
}