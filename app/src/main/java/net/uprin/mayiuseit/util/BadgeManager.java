package net.uprin.mayiuseit.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import net.uprin.mayiuseit.service.App;

import java.util.List;

/**
 * Created by uPrin on 2017. 12. 11..
 */

public class BadgeManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int badgeCount;

    private static BadgeManager INSTANCE = null;

    private BadgeManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized BadgeManager getInstance(SharedPreferences prefs){
        if(INSTANCE == null){
            INSTANCE = new BadgeManager(prefs);
        }
        return INSTANCE;
    }

    public int getBadgeCount(){
        badgeCount = prefs.getInt("BADGE_COUNT",0);
        return badgeCount;
    }

    public void setBadgeCount(int data){
        editor.putInt("BADGE_COUNT", data).commit();
        Log.e("BadgeManager","Success");
        refreshBadge();

    }

    public void deleteBadgeCount() {
        editor.remove("BADGE_COUNT").commit();
        refreshBadge();
    }
    public void refreshBadge() {
        Intent badgeIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        badgeIntent.putExtra("badge_count", getBadgeCount());
        badgeIntent.putExtra("badge_count_package_name", App.getAppContext().getPackageName());
        badgeIntent.putExtra("badge_count_class_name", getLauncherClassName());
        App.getAppContext().sendBroadcast(badgeIntent);
    }

    private String getLauncherClassName() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = App.getAppContext().getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(App.getAppContext().getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }

}
