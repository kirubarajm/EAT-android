package com.tovo.eat.ui.registration;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tovo.eat.R;

import java.util.List;

public class RegionAdapter extends BaseAdapter {

    Activity activity;
    List<RegionResponse.Result> metadatas;
    Context context;

    public RegionAdapter(Context context,List<RegionResponse.Result> metadatas) {
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
       /* RegionsAdapter.ViewHolder holder = null;
        ListItemSpinnerBinding mListItemSpinnerBinding = null;
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_spinner, viewGroup, false);
           *//* ListItemSpinnerBinding listItemOrdersBinding = ListItemSpinnerBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                    viewGroup, false);
            holder = new RegionsAdapter.ViewHolder(listItemOrdersBinding);*//*
            holder.txtDemo = (TextView) view.findViewById(R.id.txt_spinner);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        RegionsResponse.Result resutlt = (RegionsResponse.Result) getItem(position);
        holder.txtDemo.setText(resutlt.getRegionname());

       *//* final RegionsResponse.Result order = metadatas.get(position);
        RegionItemViewModel ordersItemViewModel = new RegionItemViewModel(order);
        mListItemSpinnerBinding.setRegItemViewModel(ordersItemViewModel);
        mListItemSpinnerBinding.executePendingBindings();*//*
        return view;*/

        final RegionAdapter.ViewHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.list_item_spinner, viewGroup, false);

            holder = new RegionAdapter.ViewHolder();

            holder.txtDemo  = (TextView) view.findViewById(R.id.txt_spinner);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        RegionResponse.Result resutlt = (RegionResponse.Result) getItem(position);

        holder.txtDemo.setText(resutlt.getRegionname());

       return view;
    }

    public class ViewHolder {
        TextView txtDemo;
        //ListItemSpinnerBinding mListItemSpinnerBinding;

        /*public ViewHolder(ListItemSpinnerBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
*/

//        public ViewHolder(ListItemSpinnerBinding mListItemSpinnerBinding) {
//            this.mListItemSpinnerBinding = mListItemSpinnerBinding;
//        }


    }


    public void clearItems() {
        metadatas.clear();
    }

    public void addChatItems(List<RegionResponse.Result> blogList) {
        metadatas.addAll(blogList);
        notifyDataSetChanged();
    }

   /* public void setListener(Context listener) {
        this.context = listener;
    }*/

    /*public interface RegionListener {
        void listItem(RegionsResponse.Result mOrderList);
    }*/
}
