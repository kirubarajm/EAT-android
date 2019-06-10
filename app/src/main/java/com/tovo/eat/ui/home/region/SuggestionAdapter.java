package com.tovo.eat.ui.home.region;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SuggestionAdapter extends ArrayAdapter<RegionSearchModel.Result> {

    private List<RegionSearchModel.Result> items;

    private List<RegionSearchModel.Result> filteredItems;
    private ArrayFilter mFilter;

    public SuggestionAdapter(Context context,int resource, List<RegionSearchModel.Result> objects) throws NullPointerException {
        super(context, resource, objects);
        this.items = objects;
        this.filteredItems = objects;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RegionSearchModel.Result getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    public int getCount() {
        //todo: change to pattern-size
        return filteredItems.size();
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();


            List<RegionSearchModel.Result> originalList = new ArrayList<>();
            originalList = items;

            if (prefix != null && prefix.length() > 0) {
                ArrayList<RegionSearchModel.Result> list = new ArrayList<>();

                for (int i = 0; i < items.size(); i++) {
                    if ((items.get(i).getRegionname().toString().toUpperCase(Locale.getDefault()))
                            .contains(prefix.toString().toUpperCase(Locale.getDefault()))) {
                        list.add(items.get(i));
                    }
                }

                if (list.size() == 0) {

                    list.add(new RegionSearchModel.Result(0, "No Regions found"));

                }

                results.values = list;
                results.count = list.size();

            } else {
                results.values = items;
                results.count = items.size();
            }

            //custom-filtering of results


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems = (List<RegionSearchModel.Result>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                filteredItems = new ArrayList<>();
                notifyDataSetInvalidated();

            }


           /* if(results.values!=null){
                filteredItems = (ArrayList<RegionSearchModel>) results.values;
            }else{
                filteredItems = new ArrayList<>();
            }
            notifyDataSetChanged();*/


        }
    }
}