package com.tovo.eat.ui.home.region;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Dhanasekar on 3/19/2018.
 */
public class AutoCompleteRegionAdapter extends ArrayAdapter<RegionSearchModel> implements Filterable {

    // Context
    Context context;

    //Data
    List<RegionSearchModel> baseSuggestion;
    List<RegionSearchModel> filterSuggestion;
    private ArrayFilter mFilter;

    public AutoCompleteRegionAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        baseSuggestion = new ArrayList<>();
        filterSuggestion = new ArrayList<>();
    }

    public void setData(List<RegionSearchModel> list) {
        baseSuggestion.clear();
        baseSuggestion.addAll(list);
    }

    @Override
    public int getCount() {
        return filterSuggestion.size();
    }

    @Override
    public RegionSearchModel getItem(int position) {
        return filterSuggestion.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults filterResults = new FilterResults();

            if (prefix != null && prefix.length() > 0) {
                ArrayList<RegionSearchModel> list = new ArrayList<>();

                for (int i = 0; i < baseSuggestion.size(); i++) {
                    if ((baseSuggestion.get(i).getRegions().toUpperCase(Locale.getDefault()))
                            .contains(prefix.toString().toUpperCase(Locale.getDefault()))) {
                        list.add(baseSuggestion.get(i));
                    }
                }
                filterResults.count = list.size();
                filterResults.values = list;

                if (list.size()==0){

                    list.add(new RegionSearchModel("No Regions found"));
                }



            } else {
                filterResults.count = baseSuggestion.size();
                filterResults.values = baseSuggestion;
            }
            return filterResults;

        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results.values!=null){
                filterSuggestion = (ArrayList<RegionSearchModel>) results.values;
            }else{
                filterSuggestion = new ArrayList<>();
            }
            notifyDataSetChanged();

        }
    }

    public List<RegionSearchModel> getFilterList(){
        return filterSuggestion;
    }

    public void clearItems() {
        baseSuggestion.clear();
        filterSuggestion.clear();
    }

    public void addOrderCompletedItems(List<RegionSearchModel> blogList) {
        baseSuggestion.addAll(blogList);
        filterSuggestion.addAll(blogList);
        notifyDataSetChanged();
    }





}