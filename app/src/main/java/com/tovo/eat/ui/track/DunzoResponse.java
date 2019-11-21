package com.tovo.eat.ui.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DunzoResponse {

        @SerializedName("task_id")
        @Expose
        private String taskId;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("event_timestamp")
        @Expose
        private Integer eventTimestamp;
        @SerializedName("eta")
        @Expose
        private Eta eta;
        @SerializedName("runner")
        @Expose
        private Runner runner;
        @SerializedName("request_timestamp")
        @Expose
        private Integer requestTimestamp;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Integer getEventTimestamp() {
            return eventTimestamp;
        }

        public void setEventTimestamp(Integer eventTimestamp) {
            this.eventTimestamp = eventTimestamp;
        }

        public Eta getEta() {
            return eta;
        }

        public void setEta(Eta eta) {
            this.eta = eta;
        }

        public Runner getRunner() {
            return runner;
        }

        public void setRunner(Runner runner) {
            this.runner = runner;
        }

        public Integer getRequestTimestamp() {
            return requestTimestamp;
        }

        public void setRequestTimestamp(Integer requestTimestamp) {
            this.requestTimestamp = requestTimestamp;
        }

    public class Location {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }
    public class Eta {

        @SerializedName("dropoff")
        @Expose
        private Double dropoff;

        public Double getDropoff() {
            return dropoff;
        }

        public void setDropoff(Double dropoff) {
            this.dropoff = dropoff;
        }

    }

    public class Runner {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @SerializedName("location")
        @Expose
        private Location location;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

    }
}
