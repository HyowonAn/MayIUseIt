package net.uprin.mayiuseit.util;

import android.content.Context;
import android.preference.Preference;

import net.uprin.mayiuseit.R;

/**
 * Created by uPrin on 2017. 11. 2..
 */

public class LoadingPreference extends Preference {
    public LoadingPreference(Context context){
        super(context);
        setLayoutResource(R.layout.loading_placeholder);
    }
}