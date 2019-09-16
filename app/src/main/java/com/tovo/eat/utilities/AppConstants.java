package com.tovo.eat.utilities;

import com.tovo.eat.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppConstants {



    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvvm.db";

    public static final int NULL_INDEX = 0;

    public static final String PREF_NAME = "tovologies_eat_pref";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";

    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String STATUS_CODE_FAILED = "failed";

    public static final String STATUS_CODE_SUCCESS = "success";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final int REFUND_LIST_CODE  = 1110;
    public static final int COUPON_LIST_CODE  = 1111;
    public static final int SELECT_ADDRESS_LIST_CODE  = 1122;


    public static final String FCM_RECEIVER = "ALERT";


    public static final String FCM_RECEIVER_ORDER = "ORDER RECEIVER";


    public static final String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


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
    public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
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


    public static final int KITCHEMN_REQUEST_CODE = 1;
    public static final int KITCHEMN_APPLIANCES_REQUEST_CODE = 2;
    public static final int PACKAGING_IMAGE_REQUEST_CODE = 3;
    public static final int SIGNATURE_REQUEST_CODE = 4;

    public static final int GPS_REQUEST = 500;
    public static final int HOME_ADDRESS_CODE = 5556;
    public static final int CART_REQUESTCODE = 111;
    public static final int COD_REQUESTCODE = 222;
    public static final int ONLINE_REQUESTCODE = 333;
    public static final int INTERNET_ERROR_REQUEST_CODE = 4404;

    public static final String SUPPORT_NUMBER = "9790876528";

    public static final int CALL_PHONE_PERMISSION_REQUEST_CODE= 501;

    public static final String API_VERSION_ONE = "1.0.0";
    public static final String API_VERSION_TWO = "2.0.0";
    public static final String API_VERSION_THEREE = "3.0.0";
    public static final String API_VERSION_FOUR = "4.0.0";
    public static final String APP_TYPE_ANDROID= "1";// 1 means android



//Analytics param key

    public static final String ANALYTICYS_PRODUCT_ID = "product_id";
    public static final String ANALYTICYS_PRODUCT_NAME = "product_name";
    public static final String ANALYTICYS_PRODUCT_QUANTITY = "product_quantity";
    public static final String ANALYTICYS_PRODUCT_PRICE= "product_price";
    public static final String ANALYTICYS_MOBILE_NUMBER= "mobile_number";
    public static final String ANALYTICYS_ORDER_ID= "order_id";
    public static final String ANALYTICYS_PRICE= "price";
    public static final String ANALYTICYS_USER_ID= "user_id";
    public static final String ANALYTICYS_USER_NAME= "user_name";
    public static final String ANALYTICYS_STORY_ID= "story_id";
    public static final String ANALYTICYS_STORY_TITLE= "story_title";
    public static final String ANALYTICYS_REGION= "region_name";
    public static final String ANALYTICYS_SEARCH_TYPE= "search_type";
    public static final String ANALYTICYS_SEARCH_NAME= "search_name";
    public static final String ANALYTICYS_SEARCH_ID= "search_id";





//Analytics param event key
public static final String ANALYTICYS_ADD_TO_CART= "add_to_cart";
public static final String ANALYTICYS_REMOVE_FROM_CART= "remove_from_cart";
public static final String ANALYTICYS_CURRENCY_TYPE= "currency";
public static final String ANALYTICYS_CURRENCY= "INR";
public static final String ANALYTICYS_PAYMENT_SUCCESS= "online_payment_success";
public static final String ANALYTICYS_PAYMENT_FAILED= "online_payment_failed";
public static final String ANALYTICYS_USER_LOGIN= "user_login";
public static final String ANALYTICYS_STORY_VIEW= "story";
public static final String ANALYTICYS_SEARCH_CLICKED= "search";
public static final String ANALYTICYS_PROCEED_TO_PAY= "proceed_to_pay";
public static final String ANALYTICYS_ORDER_PLACED= "order_placed";
public static final String ANALYTICYS_REGION_SELECTED= "region_selected";
public static final String ANALYTICYS_RATING= "rating";
public static final String ANALYTICYS_feedback= "feedback";
public static final String ANALYTICYS_CHAT_MESSAGE= "chat_message";
public static final String ANALYTICYS_QUERIES= "query";
public static final String ANALYTICYS_MAKE_QUERIES= "make_query";
public static final String ANALYTICYS_REPEAT_ORDER= "repeat_order";



//analytics screen name
    public static final String SCREEN_EDIT_MYACCOUNT=  "edit_my_account";
    public static final String SCREEN_FAVOURITE_DISH=  "favourite_dish";
        public static final String SCREEN_FAVOURITE_KITCHEN=  "favourite_kitchen";
        public static final String SCREEN_FAVOURITES=  "favourites";
        public static final String SCREEN_APP_FEEDBCK=  "app_feedback";
        public static final String SCREEN_SUPPORT_REPLIES=  "support_replies";
        public static final String SCREEN_QUERY_CHAT=  "queries_chat";
        public static final String SCREEN_SUPPORT=  "support";
        public static final String SCREEN_FEEDBACK_SUPPORT=  "feedback_and_support_page";
        public static final String SCREEN_ORDER_LIST=  "orders_list";
        public static final String SCREEN_ORDER_DETAILS=  "orders_details";
        public static final String SCREEN_REFERRAL=  "referral";
        public static final String SCREEN_MY_ACCOUNT=  "my_account";


//analytics screen click

    //edit my account

    public static final String CLICK_REGION_CHANGE=  "change_region";
    public static final String CLICK_REGION_OTHER=  "region_other";
    public static final String CLICK_APPLY_CHANGES=  "profile_update";
    public static final String CLICK_BACK_BUTTON=  "back_click";
    public static final String CLICK_MALE_SELECTED=  "male_selected";
    public static final String CLICK_FEMALE_SELECTED=  "female_selected";

    public static final String CLICK_CHANGE_KITCHEN_CONFIRM=  "change_kitchen_confirm";
    public static final String CLICK_CHANGE_KITCHEN_CANCEL=  "change_kitchen_cancel";
    public static final String CLICK_REFRESH=  "refresh";
    public static final String CLICK_KITCHEN_CLICK=  "kitchen_click";


    public static final String CLICK_KITCHEN_MENU=  "kitchen_menu";
    public static final String CLICK_DISH_MENU=  "dish_menu";
    public static final String CLICK_SEND=  "send";
    public static final String CLICK_CALL_SUPPORT=  "call_support";
    public static final String CLICK_QUERY_SUBMIT=  "query_submit";
    public static final String CLICK_REPLIES=  "replies";
    public static final String CLICK_FEEDBACK=  "feedback";
    public static final String CLICK_SUPPORT=  "support";
    public static final String CLICK_FAQ=  "faq";
    public static final String CLICK_VIEW_DETAILS=  "view_details";
    public static final String CLICK_OREDER_HISTORY_PLACE_FIRST_ORDER=  "orders_history_place_first_order";
    public static final String CLICK_REPEAT_THIS_ORDER=  "repeat_this_order";
    public static final String CLICK_SEND_REFERRAL=  "send_referral";
    public static final String CLICK_EDIT=  "edit_profile";
    public static final String CLICK_MANAGE_ADDRESS=  "manage_address";
    public static final String CLICK_REFERRALS=  "referrals";
    public static final String CLICK_ORDER_HISTORY=  "order_history";
    public static final String CLICK_FEEDBACK_SUPPORT=  "feedback_and_support";
    public static final String CLICK_LOGOUT=  "logout";






    ///////Server Ip Ports

    //public static final String URL_SERVER_IP_PORT = BuildConfig.BASE_URL;////live ip port new


    //public static final String URL_SERVER_IP_PORT = "http://eatalltime.co.in";

    //public static final String URL_SERVER_IP_PORT = "http://192.168.1.100:3000";////ip port(suresh)
   //public static final String URL_SERVER_IP_PORT = "http://www.eatalltime.co.in:5000";////live ip port new

   //public static final String URL_SERVER_IP_PORT = "http://eatalltime.co.in";////live ip port new

 // public static final String URL_SERVER_IP_PORT = "http://eatalltime.co.in";////live ip port new
    public static final String URL_SERVER_IP_PORT = "http://www.eatalltime.co.in:4000";////live ip port new
    //public static final String URL_SERVER_IP_PORT = "http://www.eatalltime.co.in:6000";////live ip port new

      //public static final String URL_SERVER_IP_PORT = "http://13.127.100.137:3000";////live ip port new

   //  public static final String URL_SERVER_IP_PORT = "http://13.127.100.137:5000";////live ip port new

     //public static final String URL_SERVER_IP_PORT = "http://13.127.100.137:5000";////live ip port new

   //   public static final String URL_SERVER_IP_PORT = "http://13.232.246.20:3000";////live ip port
     //public static final String URL_SERVER_IP_PORT = "http://13.127.100.137:3000";////live ip port new

    //  public static final String URL_SERVER_IP_PORT = "http://13.127.100.137:5000";////live ip port new

     // public static final String URL_SERVER_IP_PORT = "http://192.168.1.101:3000";////basheer 3000

      //public static final String URL_SERVER_IP_PORT = "http://192.168.1.101:3000";////basheer 3000

   //public static final String URL_SERVER_IP_PORT = "http://192.168.1.100:3000";////ip port(suresh)

   // public static final String URL_SERVER_IP_PORT = "http://192.168.1.100:4000";////ip port(suresh)
    //public static final String URL_SERVER_IP_PORT = "http://192.168.1.200:3000";////DEV ip port(param)

    public static final String URL_SIGN_UP = URL_SERVER_IP_PORT + "/eat/login";/////POST method
    public static final String URL_OTP_VERIFICATION = URL_SERVER_IP_PORT + "/eat/otpverification";/////POST method
    public static final String URL_NAME_GENDER_INSERT = URL_SERVER_IP_PORT + "/eat/edit";/////put method
    public static final String URL_APP_FEEDBACK = URL_SERVER_IP_PORT + "/eat/feedback";/////POST method
    public static final String URL_LOGIN_MAIN = URL_SERVER_IP_PORT + "/eat/checklogin";/////POST method
    public static final String URL_QUERY_INSERT = URL_SERVER_IP_PORT + "/query";/////POST method
    public static final String URL_QUERY_REPLIES_COUNT = URL_SERVER_IP_PORT + "/repliescount";/////POST method
    public static final String URL_QUERY_QUERIES_LIST = URL_SERVER_IP_PORT + "/querylist";/////POST method
    public static final String URL_REPLIES_CHAT = URL_SERVER_IP_PORT + "/queryreplies/";/////GET method
    public static final String URL_CHAT_ANSWER = URL_SERVER_IP_PORT + "/queryanswer/";/////GET method
    public static final String URL_CHAT_REPLIES_READ = URL_SERVER_IP_PORT + "/repliesread/";/////GET method
    public static final String URL_ORDERS_HISTORY_LIST = URL_SERVER_IP_PORT + "/eat/orders/";/////GET method
    public static final String URL_ORDERS_HISTORY_VIEW = URL_SERVER_IP_PORT + "/eat/order/";/////GET method
    public static final String URL_REFERRALS = URL_SERVER_IP_PORT + "/eat/referral/";/////GET method
    public static final String URL_REGISTRATION = URL_SERVER_IP_PORT + "/eat/postregistration";/////GET method
    public static final String URL_REGION_LIST = URL_SERVER_IP_PORT + "/masters/regionlist";/////GET method
    public static final String URL_FORGOT_PASSWORD = URL_SERVER_IP_PORT + "/eat/forgot";/////GET method
    public static final String URL_SET_CONFIRM_PASSWORD = URL_SERVER_IP_PORT + "/eat/password";/////GET method
    public static final String URL_ORDER_RATING = URL_SERVER_IP_PORT + "/eat/rating";/////POST method
    public static final String URL_ORDER_RATING_SKIP = URL_SERVER_IP_PORT + "/eat/orderskip";/////POST method
    public static final String URL_GET_USER_DETAILS = URL_SERVER_IP_PORT + "/eatusers/";/////POST method0
    public static final String URL_LOG_OUT = URL_SERVER_IP_PORT + "/eat/logout";/////POST method
    public static final String URL_CANCEL_ORDER= URL_SERVER_IP_PORT + "/eat/order/cancel/";/////POST method
    public static final String EAT_KITCHEN_LIST_URL = URL_SERVER_IP_PORT + "/eat/kitchenlist";
    public static final String EAT_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/dishlist";
    public static final String EAT_SEARCH_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/product/search";
    public static final String EAT_FAV_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/fav/dishlist/";
    public static final String EAT_FAV_KITCHEN_LIST_URL = URL_SERVER_IP_PORT + "/eat/fav/kitchenlist/";
    public static final String EAT_KITCHEN_DISH_LIST_URL = URL_SERVER_IP_PORT + "/eat/products";
    public static final String EAT_ADD_ADDRESS_URL = URL_SERVER_IP_PORT + "/eat/address";
    public static final String EAT_GET_ADDRESS_URL = URL_SERVER_IP_PORT + "/eat/addresslist/";
    public static final String EAT_DELETE_ADDRESS_URL = URL_SERVER_IP_PORT + "/eat/addressdelete/";
    public static final String EAT_ADD_ADDRESS_LIST_URL = URL_SERVER_IP_PORT + "/eat/address/";

    public static final String EAT_REFUND_LIST_URL = URL_SERVER_IP_PORT + "/eat/refund/";
    public static final String EAT_COUPON_LIST_URL = URL_SERVER_IP_PORT + "/eat/coupon/";
    public static final String EAT_COUPON_CHECK_URL = URL_SERVER_IP_PORT + "/eat/coupon/validate/";

    public static final String URL_FAQS = URL_SERVER_IP_PORT + "/faqs/";/////PUT method0

    public static final String EAT_CART_DETAILS_URL = URL_SERVER_IP_PORT + "/eat/cartdetails/";

    public static final String EAT_COLLECTION_DETAILS_URL = URL_SERVER_IP_PORT + "/eat/collectiondetails/";

    public static final String EAT_CREATE_ORDER_URL = URL_SERVER_IP_PORT + "/eat/proceedtopay";

    public static final String EAT_ORDER_DETAILS_URL = URL_SERVER_IP_PORT + "/eat/order/";

    public static final String EAT_ORDER_ETA = URL_SERVER_IP_PORT + "/eat/order/deliverytime";


    public static final String EAT_LIVE_ORDER_URL = URL_SERVER_IP_PORT + "/eat/liveorders/";

    public static final String EAT_FAV_URL = URL_SERVER_IP_PORT + "/eat/fav/";
    public static final String EAT_FCM_TOKEN_URL = URL_SERVER_IP_PORT + "/eat/pushid/add";

    public static final String EAT_FCM_FORCE_UPDATE = URL_SERVER_IP_PORT + "/eat/versioncheck";

    public static final String EAT_DEFAULT_ADDRESS = URL_SERVER_IP_PORT + "/eat/defaultaddress";
    public static final String EAT_ORDER_SUCCESS = URL_SERVER_IP_PORT + "/eat/orderplace";

    public static final String EAT_MASTER =  URL_SERVER_IP_PORT +"/masters";


    public static final String EAT_REGION_LIST = URL_SERVER_IP_PORT + "/eat/regionlist";
    public static final String EAT_MASTER_REGION_LIST = URL_SERVER_IP_PORT + "/masters/regionlist";
    public static final String EAT_REGION_KITCHEN_LIST = URL_SERVER_IP_PORT + "/eat/kitche/showmore";
    public static final String EAT_EXPLORE_SEARCH = URL_SERVER_IP_PORT + "/eat/quicksearch";
    public static final String EAT_STORIES_LIST = URL_SERVER_IP_PORT + "/eat/stories";
    public static final String EAT_COLLECTION_LIST = URL_SERVER_IP_PORT + "/eat/collection";
  //  public static final String EAT_REGION_LIST=  "http://192.168.1.102/tovo/regions.json";

    public static final int ADMIN = 0;
    public static final int MAKEIT = 1;
    public static final int MOVEIT = 2;
    public static final int SALES = 3;
    public static final int EAT = 4;
    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final boolean FLAG_TRUE = true;
    public static final boolean FLAG_FALSE = false;
    ////ToAST
    public static final String TOAST_ENTER_REPLY_TO_SEND = "Enter reply to send";
    public static final String TOAST_ENTER_QUERY_TO_SEND = "Enter query to send";
    public static final String TOAST_ENTER_MOBILE_NO = "Enter mobile number";
    public static final String TOAST_ENTER_VALID_MOBILE_NO = "Enter valid mobile number";
    public static final String TOAST_ENTER_OTP = "Enter OTP";
    public static final String TOAST_ENTER_NAME = "Enter Name";
    public static final String TOAST_ENTER_EMAIL = "Enter Email";

    public static final String TOAST_ENTER_INVALID_EMAIL = "Invalid Email Address";
    public static final String TOAST_ENTER_PASSWORD = "Enter password";
    public static final String TOAST_ENTER_RE_ENTER_PASSWORD = "ReType password";
    public static final String TOAST_PASSWORD_NOT_MATCHING = "Password not matching";
    public static final String TOAST_LOGIN_SUCCESS = "Login success";
    public static final String TOAST_LOGIN_FAILED = "Login failed";
    public static final int TERMS_AND_CONDITION_REQUEST_CODE = 701;
    public static final String CANCEL_ORDER_MEAASAGE_1 ="I entered the wrong delivery address" ;
    public static final String CANCEL_ORDER_MEAASAGE_2 ="I added the wrong items";
    public static final String CANCEL_ORDER_MEAASAGE_3 ="Placed order by mistake";

    public AppConstants() {
        // This utility class is not publicly instantiable
    }
}
