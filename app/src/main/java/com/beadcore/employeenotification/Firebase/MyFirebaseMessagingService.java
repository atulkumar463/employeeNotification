package com.beadcore.employeenotification.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.beadcore.employeenotification.R;
import com.beadcore.employeenotification.Retrofit.ApiClient;
import com.beadcore.employeenotification.Retrofit.ApiInterface;
import com.beadcore.employeenotification.Retrofit.LoginResponse;
import com.beadcore.employeenotification.Views.Home;
import com.beadcore.employeenotification.Views.LoginActivity;
import com.beadcore.employeenotification.helper.PrefManager;
import com.beadcore.employeenotification.model.EmployeeDetails;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public final String TAG = "SDSD";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int counter = 0;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData() != null) {
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setDevice_name(remoteMessage.getData().get("device_name"));
            employeeDetails.setEmployee_name(remoteMessage.getData().get("employee_name"));
            employeeDetails.setTime(remoteMessage.getData().get("mark_time").split(" ")[1]);
            ShowNotificationForUser(employeeDetails);
            if (notificationListener != null) {
                notificationListener.onNewNotification();
            }
        }


    }

    public interface NotificationListener {
        void onNewNotification();
    }

    public static NotificationListener notificationListener;

    public static void setNotificationListener(NotificationListener listener) {
        notificationListener = listener;
    }

    private NotificationManager getNotificationManager(Context context) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("employee_notification_channel",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            mNotificationManager.createNotificationChannel(channel);
        }

        return mNotificationManager;
    }


    private void ShowNotificationForUser(EmployeeDetails employeeDetails) {
        RemoteViews notificationView;
        NotificationManager notificationManager;
        Notification customNotification;

        //  Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        notificationView = new RemoteViews(getPackageName(),
                R.layout.notification_layout);
        notificationManager = getNotificationManager(getApplicationContext());
        customNotification = new NotificationCompat.Builder(getApplicationContext(), getString(R.string.notification_channel))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigPictureStyle())
                .setAutoCancel(true)
                .setCustomBigContentView(notificationView)
                //  .setSound(notification, AudioManager.STREAM_NOTIFICATION)
                .build();
        notificationView.setTextViewText(R.id.device_name, employeeDetails.getDevice_name());
        notificationView.setTextViewText(R.id.date_time,  employeeDetails.getTime());
        notificationView.setTextViewText(R.id.tvEmployeeName,  employeeDetails.getEmployee_name());


        //the intent that is started when the notification is clicked (works)
        Intent notificationIntent = new Intent(getApplicationContext(), Home.class);
        notificationIntent.putExtra("from_notification", true);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        customNotification.contentView = notificationView;
        customNotification.contentIntent = pendingNotificationIntent;
        customNotification.priority = Notification.PRIORITY_MAX;

//        customNotification.flags |= Notification.FLAG_NO_CLEAR;
//        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
       /* customNotification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        customNotification.flags = Notification.FLAG_AUTO_CANCEL;*/
        //   customNotification.defaults |= Notification.DEFAULT_SOUND; // Sound
        if (counter > 6) {
            counter = 0;
            notificationManager.cancel(counter);
        } else {
            notificationManager.notify(counter++, customNotification);

        }
    }


    // [END receive_message]

    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Log.d(TAG, "Refreshed token: " + token);
      /*  sharedPreferences = Utils.getSharedPreferencesInstance(getApplicationContext());
        editor = Utils.getEditorInstance(getApplicationContext());
        editor.putString("firebase_token",token);
        editor.apply();*/
        PrefManager.writeStringPref(getApplicationContext(), PrefManager.FCM_TOKEN, token);
        registerFirebaseToken(PrefManager.readStringPref(getApplicationContext(), PrefManager.LOGIN_TOKEN), token);


    }

    private void registerFirebaseToken(String token, String fcmToken) {


        Log.i("fcmtoken", "registerFirebaseToken: " + fcmToken);

        if (fcmToken == null) {

            Toast.makeText(this, "Unable to register on google services for notification p[lease try later", Toast.LENGTH_SHORT).show();
        } else {

            PrefManager.writeStringPref(getApplicationContext(), PrefManager.FCM_TOKEN, fcmToken);

            JSONObject object = new JSONObject();

            try {

                object.put("firebase_token", fcmToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(object.toString(), JsonObject.class);


            ApiClient.ResetClient();
            ApiInterface apiInterface = ApiClient.getAuthorizationClient(getApplicationContext(), token).create(ApiInterface.class);
            Call<LoginResponse> call = apiInterface.updateToken(jsonObject);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body().isStatus()) {
                        Toast.makeText(getApplicationContext(), "token updated succesfully ", Toast.LENGTH_SHORT).show();

                       /* PrefManager.writeStringPref(getApplicationContext(),PrefManager.LOGIN_STATUS,"true");

                        startActivity(new Intent(getApplicationContext(),Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                |Intent.FLAG_ACTIVITY_NEW_TASK
                                |Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();*/

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "Unable to updfate token to server please login again", Toast.LENGTH_SHORT).show();
                    PrefManager.writeStringPref(getApplicationContext(), PrefManager.LOGIN_STATUS, "false");
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }
            });
        }
    }
    // [END on_new_token]

    /**
     * Schedule a job using FirebaseJobDispatcher.


     /**
     * Handle time allotted to BroadcastReceivers.
     */

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

    }

}