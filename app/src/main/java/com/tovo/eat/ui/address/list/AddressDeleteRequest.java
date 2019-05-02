package com.tovo.eat.ui.address.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressDeleteRequest {

    @SerializedName("aid")
    @Expose
    private Integer aid;

    public AddressDeleteRequest(Integer aid) {
        this.aid = aid;
    }
}
