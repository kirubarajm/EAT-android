package com.tovo.eat.ui.address.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDeleteRequest {

    @SerializedName("aid")
    @Expose
    private Long aid;

    public AddressDeleteRequest(Long aid) {
        this.aid = aid;
    }
}
