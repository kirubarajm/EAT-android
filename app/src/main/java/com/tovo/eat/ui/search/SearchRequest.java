package com.tovo.eat.ui.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchRequest {

    @SerializedName("search")
    @Expose
    private String search;


    public SearchRequest(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}