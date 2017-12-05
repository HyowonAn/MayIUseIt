package net.uprin.mayiuseit.util;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by CJS on 2017-12-05.
 */

public class HistoryManager {


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Set<String> histories;
    private static HistoryManager INSTANCE = null;

    private HistoryManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized HistoryManager getInstance(SharedPreferences prefs){
        if(INSTANCE == null){
            INSTANCE = new HistoryManager(prefs);
        }
        return INSTANCE;
    }

    public void saveHistory(ArrayList<String> histories){
        Set<String> set = new HashSet<>(histories);
        editor.putStringSet("History", set).commit();

        Log.e("HISTORY_LOG",prefs.getStringSet("History",null).toString());

    }

    public void deleteHistory(){
        editor.remove("History").commit();
    }

    public ArrayList<String> getHistory(){

        if (prefs.getStringSet("History", null) ==null) {
            histories = new HashSet<>();

        } else {
            histories = new HashSet<>(prefs.getStringSet("History", null));
        }
        return new ArrayList<>(histories);
    }
}
