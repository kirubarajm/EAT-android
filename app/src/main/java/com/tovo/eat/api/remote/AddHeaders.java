package com.tovo.eat.api.remote;

import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import java.util.HashMap;
import java.util.Map;

public class AddHeaders {

    String version;

    public AddHeaders() {
    }


    public Map<String, String> setHeaders(String version) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        //  headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("accept-version",version);
        headers.put("apptype", AppConstants.APP_TYPE_ANDROID);
        //  headers.put("Authorization","Bearer");
        AppPreferencesHelper preferencesHelper=new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
        headers.put("Authorization","Bearer "+preferencesHelper.getApiToken());

        // headers.put("token",preferencesHelper.getApiToken());





       /* HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("accept-version",AppConstants.API_VERSION_ONE);
        headers.put("Authorization","Bearer "+getDataManager().getApiToken());
        headers.put("apptype",AppConstants.APP_TYPE_ANDROID);*/




        return  headers;


    }

}
