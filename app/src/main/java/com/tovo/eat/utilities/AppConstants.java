package com.tovo.eat.utilities;

public class AppConstants {

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvvm.db";

    public static final long NULL_INDEX = -1L;

    public static final String PREF_NAME = "mindorks_pref";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";

    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";


    public static final String FCM_RECEIVER= "ALERT";

    public static final String TEMP_URL_MY = "http://192.168.1.100/tovo/";
    public static final String TEMP_URL_LIVE = "http://13.232.246.20:3000/eat/";
   // public static final String TEMP_URL_LIVE = "http://192.168.1.225:4000/eat/";
    public static final String TEMP_URL = "http://192.168.1.100:3000/eat/";

   // public static final String SALES_SERVER_URL = "http://192.168.1.100:3000/sales/";
    public static final String EAT_SERVER_URL = "http://13.232.246.20:3000/sales/";
    public static final String EAT_KITCHEN_LIST_URL =TEMP_URL_LIVE+ "makeitfilter";
    public static final String EAT_DISH_LIST_URL =TEMP_URL_LIVE+ "dishfilter";
    public static final String EAT_KITCHEN_DISH_LIST_URL =TEMP_URL_LIVE+ "products";
    public static final String EAT_ADD_ADDRESS_URL =TEMP_URL_LIVE+ "address";
    public static final String EAT_ADD_ADDRESS_LIST_URL =TEMP_URL_LIVE+ "address/";
    public static final String EAT_CART_DETAILS_URL =TEMP_URL_LIVE+ "cartdetails/";

    public static final String EAT_CREATE_ORDER_URL =TEMP_URL_LIVE+ "proceedtopay";

    public static final String EAT_ORDER_DETAILS_URL =TEMP_URL_LIVE+ "order/";

    public static final String EAT_LIVE_ORDER_URL =TEMP_URL_LIVE+ "liveorders/";

    public static final String EAT_FAV_URL =TEMP_URL_LIVE+ "fav/";

    public static final String EAT_DISH_LIST ="http://192.168.1.225:3000/eat/fav/dishlist/";



    public static final String PACKAGE_NAME = "com.tovo.sales";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public  static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    /*public  static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();

     static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("SFO", new LatLng(37.621313, -122.378955));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(37.422611,-122.0840577));
    }*/











    public  static final int KITCHEMN_REQUEST_CODE = 1;
    public  static final int KITCHEMN_APPLIANCES_REQUEST_CODE = 2;
    public  static final int PACKAGING_IMAGE_REQUEST_CODE = 3;
    public  static final  int SIGNATURE_REQUEST_CODE = 4;
    public static final int GPS_REQUEST =500 ;


    public AppConstants() {
        // This utility class is not publicly instantiable
    }

    ///////Server Ip Ports
    //public static final String URL_SERVER_IP_PORT = "http://13.232.246.20:3000";////live ip port
    //public static final String URL_SERVER_IP_PORT = "http://192.168.1.225:4000";////ip port(suresh)
    public static final String URL_SERVER_IP_PORT = "http://192.168.1.100:3000";////ip port(suresh)
    //public static final String URL_SERVER_IP_PORT = "http://192.168.1.200:3000";////DEV ip port(param)

    public static final String URL_QUERY_INSERT = URL_SERVER_IP_PORT + "/query";/////POST method
    public static final String URL_QUERY_REPLIES_COUNT = URL_SERVER_IP_PORT + "/repliescount";/////POST method
    public static final String URL_QUERY_QUERIES_LIST = URL_SERVER_IP_PORT + "/querylist";/////POST method
    public static final String URL_REPLIES_CHAT = URL_SERVER_IP_PORT + "/queryreplies/";/////GET method
    public static final String URL_CHAT_ANSWER = URL_SERVER_IP_PORT + "/queryanswer/";/////GET method
    public static final String URL_CHAT_REPLIES_READ = URL_SERVER_IP_PORT + "/repliesread/";/////GET method
    public static final String URL_ORDERS_HISTORY_LIST = URL_SERVER_IP_PORT + "/eat/orders/1";/////GET method
    public static final String URL_ORDERS_HISTORY_VIEW = URL_SERVER_IP_PORT + "/eat/order/1";/////GET method
    public static final String URL_REFERRALS = URL_SERVER_IP_PORT + "/eat/referral/1";/////GET method

    public static final int ADMIN =0;
    public static final int MAKEIT =1;
    public static final int MOVEIT =2;
    public static final int SALES=3;
    public static final int EAT =4;

    public static final int EAT_CHAT_ID =4;






    ////ToAST
    public static final String TOAST_ENTER_REPLY_TO_SEND = "Enter reply to send";
    public static final String TOAST_ENTER_QUERY_TO_SEND ="Enter query to send";
}
