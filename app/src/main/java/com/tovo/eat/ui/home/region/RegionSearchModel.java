package com.tovo.eat.ui.home.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionSearchModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    public RegionSearchModel(String success, List<Result> result) {
        this.success = success;
        this.result = result;
    }

    public RegionSearchModel() {

    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {

        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;

        @SerializedName("hometownid")
        @Expose
        private Integer hometownid;
        @SerializedName("hometownname")
        @Expose
        private String hometown;

        @Override
        public String toString() {
            return hometown ;
        }

        public Result() {
        }


        public Result(Integer regionid, String regionname) {
            this.regionid = regionid;
            this.regionname = regionname;
        }

        public Integer getHometownid() {
            return hometownid;
        }

        public void setHometownid(Integer hometownid) {
            this.hometownid = hometownid;
        }

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }


        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

    }


}
