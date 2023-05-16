package com.example.projectmobilesmt4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.list_data_master, dataList);
        listView.setAdapter(adapter);

        getData();

        return view;
    }

    private void getData(){
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
}