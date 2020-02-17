package com.tovo.eat.utilities.chat;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONObject;

import java.util.Map;


public class IssuesItemViewModel {


    public final ObservableField<String> ratings = new ObservableField<>();

    public final ObservableField<String> issue_name = new ObservableField<>();

    public final IssueItemViewModelListener mListener;
    public final IssuesListResponse.Result issue;



    public IssuesItemViewModel(IssueItemViewModelListener mListener, IssuesListResponse.Result issue) {
        this.mListener = mListener;
        this.issue = issue;
        this.issue_name.set(issue.getIssues());

    }


    public void onItemClick() {
        mListener.onItemClick(issue);
    }

    public interface IssueItemViewModelListener {
        void onItemClick(IssuesListResponse.Result issue);

    }

}
