package com.example.projectmobilesmt4;

import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {
    private ArrayList<String> dataList;
    private ArrayList<String> filteredList;
    private ArrayAdapter<String> adapter;

    public CustomFilter(ArrayList<String> dataList, ArrayAdapter<String> adapter) {
        this.dataList = dataList;
        this.adapter = adapter;
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults filterResults = new FilterResults();

        if (constraint.length() == 0) {
            filteredList.addAll(dataList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            for (final String data : dataList) {
                if (data.toLowerCase().contains(filterPattern)) {
                    filteredList.add(data);
                }
            }
        }

        filterResults.values = filteredList;
        filterResults.count = filteredList.size();
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (constraint.length() == 0) {
            adapter.clear();
            adapter.addAll(dataList); // Mengembalikan data asli ke adapter
        } else {
            adapter.clear();
            adapter.addAll((List<String>) results.values); // Menambahkan hasil filter ke adapter
        }
        adapter.notifyDataSetChanged();
    }

}
