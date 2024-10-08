package com.tovo.eat.ui.search;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.home.homemenu.dish.DishFavRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.ui.home.region.list.RegionDetailsRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {


    public final ObservableField<String> regionName = new ObservableField<>();
    public final ObservableField<String> totalKitchens = new ObservableField<>();
    public ObservableList<SearchResponse.Result> searchItemViewModels = new ObservableArrayList<>();
    public KitchenResponse kitchenResponse;
    public ObservableList<KitchenResponse.Result> kitchenListItemViewModels = new ObservableArrayList<>();
    public ObservableList<KitchenDishResponse.Result> dishItemViewModels = new ObservableArrayList<>();
    public ObservableField<String> kitchenName = new ObservableField<>();
    public ObservableField<String> kitchenImage = new ObservableField<>();
    public ObservableField<String> kitchenCategory = new ObservableField<>();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();
    public ObservableBoolean cart = new ObservableBoolean();
    public ObservableField<String> searched = new ObservableField<>();
    boolean haveAddress = false;
    private MutableLiveData<List<SearchResponse.Result>> searchItemsLiveData;
    private MutableLiveData<List<KitchenResponse.Result>> kitchenListItemsLiveData;
    private MutableLiveData<List<KitchenDishResponse.Result>> dishItemsLiveData;

    public SearchViewModel(DataManager dataManager) {
        super(dataManager);
        searchItemsLiveData = new MutableLiveData<>();
        dishItemsLiveData = new MutableLiveData<>();
        kitchenListItemsLiveData = new MutableLiveData<>();

    }

    public ObservableList<SearchResponse.Result> getSearchListAnalytics() {
        return (searchItemViewModels);
    }


    public ObservableList<KitchenResponse.Result> getkitchenListItemViewModels() {
        return kitchenListItemViewModels;
    }

    public void setkitchenListItemViewModels(ObservableList<KitchenResponse.Result> kitchenListItemViewModels) {
        this.kitchenListItemViewModels = kitchenListItemViewModels;
    }

    public MutableLiveData<List<KitchenResponse.Result>> getkitchenListItemsLiveData() {
        return kitchenListItemsLiveData;
    }

    public void setkitchenListItemsLiveData(MutableLiveData<List<KitchenResponse.Result>> kitchenListItemsLiveData) {
        this.kitchenListItemsLiveData = kitchenListItemsLiveData;
    }

    public void addKitchenItemsToList(List<KitchenResponse.Result> ordersItems) {
        kitchenListItemViewModels.clear();
        kitchenListItemViewModels.addAll(ordersItems);

    }

    public ObservableList<KitchenDishResponse.Result> getDishItemViewModels() {
        return dishItemViewModels;
    }

    public void setDishItemViewModels(ObservableList<KitchenDishResponse.Result> dishItemViewModels) {
        this.dishItemViewModels = dishItemViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Result>> getDishItemsLiveData() {
        return dishItemsLiveData;
    }

    public void setDishItemsLiveData(MutableLiveData<List<KitchenDishResponse.Result>> dishItemsLiveData) {
        this.dishItemsLiveData = dishItemsLiveData;
    }

    public void addDishItemsToList(List<KitchenDishResponse.Result> ordersItems) {
        if (dishItemViewModels != null) {
            if (ordersItems != null) {
                dishItemViewModels.clear();
                dishItemViewModels.addAll(ordersItems);
            }
        }

    }


    public ObservableList<SearchResponse.Result> getSearchItemViewModels() {
        return searchItemViewModels;
    }

    public void setSearchItemViewModels(ObservableList<SearchResponse.Result> searchItemViewModels) {
        this.searchItemViewModels = searchItemViewModels;
    }

    public MutableLiveData<List<SearchResponse.Result>> getSearchItemsLiveData() {
        return searchItemsLiveData;
    }

    public void setSearchItemsLiveData(MutableLiveData<List<SearchResponse.Result>> searchItemsLiveData) {
        this.searchItemsLiveData = searchItemsLiveData;
    }


    public void addSearchItemsToList(List<SearchResponse.Result> ordersItems) {

        if (ordersItems != null) {
            searchItemViewModels.clear();
            searchItemViewModels.addAll(ordersItems);
        }

    }


    public void fetchRepos(String data) {


        searched.set(data);

        if (!MvvmApp.getInstance().onCheckNetWork()) return;


        setIsLoading(true);

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_EXPLORE_SEARCH, SearchResponse.class, new SearchRequest(data), new Response.Listener<SearchResponse>() {
                @Override
                public void onResponse(SearchResponse response) {
                    if (response.getResult() != null) {
                        searchItemsLiveData.setValue(response.getResult());
                        try {
                            StringBuilder dishSb = new StringBuilder();
                            StringBuilder kitchenSb = new StringBuilder();
                            StringBuilder regionSb = new StringBuilder();
                            int dishCount = 0, kitchenCount = 0, regionCount = 0;
                            for (int i = 0; i < searchItemViewModels.size(); i++) {
                                if (searchItemViewModels.get(i).getType() == 1) {
                                    dishCount++;
                                    dishSb.append(searchItemViewModels.get(i).getName()).append(",");
                                } else if (searchItemViewModels.get(i).getType() == 2) {
                                    kitchenCount++;
                                    kitchenSb.append(searchItemViewModels.get(i).getName()).append(",");
                                } else if (searchItemViewModels.get(i).getType() == 3) {
                                    regionCount++;
                                    regionSb.append(searchItemViewModels.get(i).getName()).append(",");
                                }
                            }
                            String strRegion = regionSb.toString();
                            String strKitchen = kitchenSb.toString();
                            String strDish = dishSb.toString();
                            String Region = "", Kitchen = "", Dish = "";

                            if (!strRegion.equals("")) {
                                Region = strRegion.substring(0, strRegion.length() - 1);
                            }
                            if (!strKitchen.equals("")) {
                                Kitchen = strKitchen.substring(0, strKitchen.length() - 1);
                            }
                            if (!strDish.equals("")) {
                                Dish = strDish.substring(0, strDish.length() - 1);
                            }
                            new Analytics().searchSuggestionPageMetrics(data, regionCount, Region, kitchenCount, Kitchen, dishCount, Dish);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (getNavigator() != null)
                        getNavigator().listLoaded();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }


    public void totalCart() {

        Gson sGson = new GsonBuilder().create();
        CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

        cart.set(false);

        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequestPojo();

        int count = 0;
        int price = 0;

        if (cartRequestPojo.getCartitems() != null) {

            if (cartRequestPojo.getCartitems().size() == 0) {

                cart.set(false);

            } else {

                for (int i = 0; i < cartRequestPojo.getCartitems().size(); i++) {

                    count = count + cartRequestPojo.getCartitems().get(i).getQuantity();
                    price = price + ((cartRequestPojo.getCartitems().get(i).getPrice()) * cartRequestPojo.getCartitems().get(i).getQuantity());

                }

                if (count <= 0) {
                    cart.set(false);
                } else {
                    if (count == 1) {

                        cartItems.set(String.valueOf(count) + " Item");
                        cart.set(true);

                        cartPrice.set(String.valueOf(price));

                        items.set("Item");
                    } else {

                        cartItems.set(String.valueOf(count) + " Items");
                        cart.set(true);
                        cartPrice.set(String.valueOf(price));
                        items.set("Items");
                    }

                }
            }
        }
    }


/*
    public void viewCart() {
        getNavigator().viewCart();
    }*/


    public void removeFavourite(Integer favId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.DELETE, AppConstants.EAT_FAV_URL + favId, CommonResponse.class, null, new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


                        //    getNavigator().toastMessage(response.getMessage());


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }


    public void addFavourite(Integer dishId, String fav) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new DishFavRequest(String.valueOf(getDataManager().getCurrentUserId()), String.valueOf(dishId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


                        //    getNavigator().toastMessage(response.getMessage());


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


    public String getCartPojoDetails() {

        return getDataManager().getCartDetails();
    }

    public void fetchDishes(String search) {

        searched.set(search);

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        FilterRequestPojo filterRequestPojo = new FilterRequestPojo();
        filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
        filterRequestPojo.setLat(getDataManager().getCurrentLat());
        filterRequestPojo.setLon(getDataManager().getCurrentLng());
        filterRequestPojo.setSearch(search);
        Gson gson = new Gson();
        String json = gson.toJson(filterRequestPojo);

        try {
            setIsLoading(true);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_SEARCH_DISH_LIST_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    if (response != null) {
                        KitchenDishResponse KitchenDishResponse;
                        Gson sGson = new GsonBuilder().create();
                        KitchenDishResponse = sGson.fromJson(response.toString(), KitchenDishResponse.class);

                        if (KitchenDishResponse.getResult() != null)
                            dishItemsLiveData.setValue(KitchenDishResponse.getResult());
                        Log.e("----response:---------", response.toString());
                        if (getNavigator() != null)
                            getNavigator().listLoaded();


                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());
                    //  SearchDishViewModel.this.getNavigator().dishListLoaded();
                }
            }) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    return AppConstants.setHeaders(AppConstants.API_VERSION_TWO);
                }
            };

            MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException j) {
            j.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }


    public void fetchRegionKitchens(String search, Integer regionId) {


        searched.set(search);


        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_REGION_KITCHEN_LIST, KitchenResponse.class, new RegionDetailsRequest(getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId(), regionId, getDataManager().getVegType()), new Response.Listener<KitchenResponse>() {
                @Override
                public void onResponse(KitchenResponse response) {
                    if (response != null) {


                        if (response.getResult().size() > 0) {

                            kitchenListItemsLiveData.setValue(response.getResult());
                            regionName.set(response.getResult().get(0).getRegionname());
                            totalKitchens.set(response.getResult().size() + " Homes specialize in " + response.getResult().get(0).getRegionname());
                            kitchenResponse = response;

                        } else {
                            if (getNavigator() != null)
                                getNavigator().noResults();
                        }

                    }
                    if (getNavigator() != null)
                        getNavigator().listLoaded();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getNavigator().listLoaded();
                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }


}



