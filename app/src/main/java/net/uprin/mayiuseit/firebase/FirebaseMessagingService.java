package net.uprin.mayiuseit.firebase;

/**
 * Created by uPrin on 2017. 12. 10..
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.MainActivity;
import net.uprin.mayiuseit.util.BadgeManager;
import net.uprin.mayiuseit.util.TokenManager;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    BadgeManager badgeManager;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendPushNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));



        badgeManager = BadgeManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        Log.e("getBadge",Integer.parseInt(remoteMessage.getData().get("badge")) +"");
        badgeManager.setBadgeCount(Integer.parseInt(remoteMessage.getData().get("badge"))) ;

//        Intent badgeIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//        badgeIntent.putExtra("badge_count", Integer.parseInt(remoteMessage.getData().get("badge")));
//        badgeIntent.putExtra("badge_count_package_name", getPackageName());
//        badgeIntent.putExtra("badge_count_class_name", getLauncherClassName());
//        sendBroadcast(badgeIntent);

    }

    private String getLauncherClassName() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getApplicationContext().getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }

    private void sendPushNotification(String title, String message) {
        System.out.println("received message : " + message);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_stat_name) )
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.noti_sound)).setLights(000000255,500,2000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}