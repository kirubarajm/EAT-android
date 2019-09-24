package com.tovo.eat.ui.registration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tovo.eat.R;

import java.util.List;

public class RegionAdapter extends BaseAdapter {

    List<RegionResponse.Result> metadatas;
    Context context;

    public RegionAdapter(Context context, List<RegionResponse.Result> metadatas) {
        this.metadatas = metadatas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return metadatas.size();
    }

    @Override
    public Object getItem(int position) {
        return metadatas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final RegionAdapter.ViewHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.list_item_spinner, viewGroup, false);

            holder = new RegionAdapter.ViewHolder();

            holder.txtDemo = view.findViewById(R.id.txt_spinner);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        RegionResponse.Result resutlt = (RegionResponse.Result) getItem(position);

        holder.txtDemo.setText(resutlt.getRegionname());

        return view;
    }

    public void clearItems() {
        metadatas.clear();
    }

    public void addChatItems(List<RegionResponse.Result> blogList) {
        metadatas.addAll(blogList);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView txtDemo;
    }
}
