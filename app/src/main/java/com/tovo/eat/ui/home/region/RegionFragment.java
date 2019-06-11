package com.tovo.eat.ui.home.region;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentRegionBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.home.region.list.RegionListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RegionFragment extends BaseFragment<FragmentRegionBinding, RegionViewModel> implements RegionNavigator, RegionsAdapter.LiveProductsAdapterListener {

    @Inject
    RegionViewModel mRegionViewModel;
    /* @Inject
     LinearLayoutManager mLayoutManager;*/
    @Inject
    RegionsAdapter adapter;


    List<RegionSearchModel.Result> items;
   /* @Inject
    DishKitchenAdapter  kitchenAdapter;*/

    FragmentRegionBinding mFragmentRegionBinding;
    RegionSearchModel regionSearchModel;

    SearchView.SearchAutoComplete searchSrcTextView;


    private static final String[] SUGGESTIONS = {
            "Bauru", "Sao Paulo", "Rio de Janeiro",
            "Bahia", "Mato Grosso", "Minas Gerais",
            "Tocantins", "Rio Grande do Sul"
    };
    private SimpleCursorAdapter mAdapter;


    public static RegionFragment newInstance() {
        Bundle args = new Bundle();
        RegionFragment fragment = new RegionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegionViewModel.setNavigator(this);
        adapter.setListener(this);
        //   kitchenAdapter.setListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentRegionBinding = getViewDataBinding();


        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentRegionBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentRegionBinding.recyclerviewOrders.setAdapter(adapter);
        // subscribeToLiveData();

        mFragmentRegionBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                mFragmentRegionBinding.shimmerViewContainer.startShimmerAnimation();
                mRegionViewModel.fetchRepos(0);
            }
        });


        /*List<RegionSearchModel> searchModels=new ArrayList<>();
        searchModels.add(new RegionSearchModel("Theni"));
        searchModels.add(new RegionSearchModel("Madurai"));
        searchModels.add(new RegionSearchModel("Trichy"));
        searchModels.add(new RegionSearchModel("Chennai"));
        searchModels.add(new RegionSearchModel("Kerla"));
        searchModels.add(new RegionSearchModel("Thanjavur"));*/


        /*AutoCompleteRegionAdapter   mAutoCompleteItemAdapter = new AutoCompleteRegionAdapter(getContext(), android.R.layout.simple_dropdown_item_1line);
        mFragmentRegionBinding.regionSearch.setThreshold(1);
        mFragmentRegionBinding.regionSearch.setAdapter(mAutoCompleteItemAdapter);
        mAutoCompleteItemAdapter.setData(searchModels);



        mFragmentRegionBinding.regionSearch.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(mFragmentRegionBinding.regionSearch){
            @Override
            public boolean onDrawableClick(){
                mFragmentRegionBinding.regionSearch.clearListSelection();
                mFragmentRegionBinding.regionSearch.setText("");
                return true;
            }
        } );



        mFragmentRegionBinding.regionSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                regionSearchModel = ((AutoCompleteRegionAdapter)     mFragmentRegionBinding.regionSearch.getAdapter()).getFilterList().get(position);
                Log.e("", regionSearchModel.getRegions());


                Toast.makeText(getContext(), regionSearchModel.getRegions(), Toast.LENGTH_SHORT).show();

            }
        });*/


       /* mFragmentRegionBinding.recyclerviewOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                mFragmentRegionBinding.regionSearch.setVisibility(View.GONE);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mFragmentRegionBinding.regionSearch.setVisibility(View.VISIBLE);

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFragmentRegionBinding.recyclerviewOrders.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    mFragmentRegionBinding.regionSearch.setVisibility(View.GONE);

                }
            });
        }*/


        //  mFragmentRegionBinding.ss.setSuggestionsAdapter(mAutoCompleteItemAdapter);











       /* final String[] from = new String[] {"cityName"};
        final int[] to = new int[] {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);



        mFragmentRegionBinding.searchh.setSuggestionsAdapter(mAdapter);
        mFragmentRegionBinding.searchh.setIconifiedByDefault(false);
        // Getting selected (clicked) item suggestion
        mFragmentRegionBinding.searchh.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {

                Cursor cursor = (Cursor) mAdapter.getItem(position);
                String txt = cursor.getString(cursor.getColumnIndex("cityName"));
                mFragmentRegionBinding.searchh.setQuery(txt, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                return true;
            }
        });
        mFragmentRegionBinding.searchh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }
        });*/


        // List<String> items = Lists.newArrayList(new String[] {"aaaaa", "bbbbb", "ccccc", "ddddd"});

        searchSrcTextView = view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        items = new ArrayList<>();
        mRegionViewModel.regionList();



      /*  items.add(new RegionSearchModel.Result(0, "Madurai"));
        items.add(new RegionSearchModel.Result(1, "Theni"));
        items.add(new RegionSearchModel.Result(2, "Chennai"));
        items.add(new RegionSearchModel.Result(3, "Trichy"));*/

        // items=mRegionViewModel.regionList().getResult();

      /*  items.add(new RegionSearchModel("madurai"));
        items.add(new RegionSearchModel("theni"));
        items.add(new RegionSearchModel("trichy"));
        items.add(new RegionSearchModel("thanjai"));
        items.add(new RegionSearchModel("chennai"));*/


    }


    // You must implements your logic to get data using OrmLite
    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});
        for (int i = 0; i < SUGGESTIONS.length; i++) {
           /* if (SUGGESTIONS[i].toLowerCase().startsWith(query.toLowerCase()))
                c.addRow(new Object[] {i, SUGGESTIONS[i]}); */


            if (SUGGESTIONS[i].toLowerCase().contains(query.toLowerCase()))
                c.addRow(new Object[]{i, SUGGESTIONS[i]});

        }
        mAdapter.changeCursor(c);


        if (c.getCount() == 0) {
            c.addRow(new Object[]{0, "No results found"});
        }





       /* if (c.isNull(0)) {

            c.addRow(new Object[]{0,"No results found"});
        }
*/

    }

    @Override
    public int getBindingVariable() {
        return BR.regionViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_region;
    }

    @Override
    public RegionViewModel getViewModel() {
        return mRegionViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void kitchenListLoaded() {

        mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentRegionBinding.shimmerViewContainer.stopShimmerAnimation();

        mFragmentRegionBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void kitchenListLoading() {
        mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentRegionBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void searchLoaded(RegionSearchModel regionSearchModel) {


     //   Toast.makeText(getContext(), "started", Toast.LENGTH_SHORT).show();

        //searchSrcTextView.setThreshold(1);
        searchSrcTextView.setAdapter(new SuggestionAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, regionSearchModel.getResult()));
        searchSrcTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               RegionSearchModel.Result result  = ((SuggestionAdapter)searchSrcTextView.getAdapter()).getItem(position);


                Integer regionID =result.getRegionid();


              /*  Intent intent = RegionListActivity.newIntent(getContext());
                intent.putExtra("id", regionID);
                startActivity(intent);*/

                mRegionViewModel.fetchRepos(regionID);


                return;
            }
        });


    }


    private void subscribeToLiveData() {
        mRegionViewModel.getregionItemsLiveData().observe(this,
                regionItemViewModel -> mRegionViewModel.addKitchenItemsToList(regionItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();

        mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentRegionBinding.shimmerViewContainer.stopShimmerAnimation();


        ((MainActivity) getActivity()).statusUpdate();


        kitchenListLoading();
        mRegionViewModel.fetchRepos(0);

        subscribeToLiveData();


        mRegionViewModel.regionList();


    }


    @Override
    public void onItemClickData(Integer kitchenId) {
        //     mRegionViewModel.saveMakeitId(kitchenId);

        Intent intent = KitchenDishActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }

    @Override
    public void showMore(Integer regionId) {
        Intent intent = RegionListActivity.newIntent(getContext());
        intent.putExtra("id", regionId);
        startActivity(intent);


    }


    /*@Override
    public void onKitchenClicked(Integer kitchenId) {

        Intent intent = KitchenDishActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);


    }*/
}

