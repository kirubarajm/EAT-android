package com.tovo.eat.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.tovo.eat.R;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.namegender.TokenRequest;
import com.tovo.eat.ui.track.OrderTrackingActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class FCMMeassagingService extends FirebaseMessagingService {

    public static final String FCM_PARAM = "picture";
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private int numMessages = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        Map<String, String> data = remoteMessage.getData();
        Log.d("FROM", remoteMessage.getFrom());


        if (data == null) {
            sendNotification(notification);
        } else {
            sendNotification(data);
        }




       /* SharedPreferences sharedpreferences = getSharedPreferences("AAA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("cust_name", data.get("cust_name"));
        editor.commit();*/



/*
        Intent intent = new Intent(this, TaskAlertActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/



      /*  Intent intent = new Intent(this,FirebaseDataReceiver.class);
        intent.putExtra("data",notification.getTitle());
        sendBroadcast(intent);*/



       /* Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName cn = new ComponentName(this, TaskAlertActivity.class);
        intent.setComponent(cn);
        startActivity(intent);*/


      /*  AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setTitle("Hai..");
        builder.show();*/


       /* Intent intent = new Intent(AppConstants.FCM_RECEIVER);
        intent.putExtra("cust_name", data.get("cust_name"));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);*/

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        saveToken(s);
    }

    private void sendNotification( Map<String, String> data) {
              /*pageidOrder_Post:1,
                pageidOrder_Accept:2,
                pageidOrder_Preparing:3,
                pageidOrder_Prepared:4,
                pageidOrder_Pickedup:5,
                pageidOrder_Reached:6,
                pageidOrder_Delivered:7,
                pageidOrder_canceled:8
                 pageid replies: 9,
                 pageid rating: 10,*/

        Bundle bundle = new Bundle();
        Intent intent;

        String pageId = data.get("pageid");
        String title = data.get("title");
        String message = data.get("message");


        if (pageId==null)  pageId="0";

            switch (pageId) {

                case "1":
                    intent = new Intent(this, MainActivity.class);

                    break;
                case "2":
                    intent = new Intent(this, OrderTrackingActivity.class);
                    break;
                case "3":
                    intent = new Intent(this, OrderTrackingActivity.class);
                    break;
                case "4":
                    intent = new Intent(this, OrderTrackingActivity.class);
                    break;
                case "5":
                    intent = new Intent(this, OrderTrackingActivity.class);
                    break;
                case "6":
                    intent = new Intent(this, OrderTrackingActivity.class);
                    break;
                case "7":
                    intent = new Intent(this, OrderHistoryActivity.class);
                    break;
                case "8":
                    intent = new Intent(this, MainActivity.class);
                    break;
                    case "9":
                    intent = new Intent(this, RepliesActivity.class);
                    break;
                default:
                    intent = new Intent(this, MainActivity.class);
            }


        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                /*   .setContentInfo("Hello")*/
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setSmallIcon(R.mipmap.ic_launcher);

       /* try {
            String picture = data.get(FCM_PARAM);
            if (picture != null && !"".equals(picture)) {
                URL url = new URL(picture);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendNotification(RemoteMessage.Notification notification) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);


        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                /*  .setContentInfo("Hello")*/
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(getResources().getColor(R.color.colorAccent))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setSmallIcon(R.mipmap.ic_launcher);

       /* try {
            String picture = data.get(FCM_PARAM);
            if (picture != null && !"".equals(picture)) {
                URL url = new URL(picture);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getString(R.string.notification_channel_id), CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }

    public void saveToken(String token) {


        AppPreferencesHelper appPreferencesHelper=new AppPreferencesHelper(MvvmApp.getInstance(),AppConstants.PREF_NAME);
        int userIdMain=appPreferencesHelper.getCurrentUserId();


        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_FCM_TOKEN_URL, CommonResponse.class, new TokenRequest(userIdMain, token), new Response.Listener<CommonResponse>() {
            @Override
            public void onResponse(CommonResponse response) {
                if (response != null) {

                    if (response.isStatus()) {


                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },AppConstants.API_VERSION_ONE);
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }

}