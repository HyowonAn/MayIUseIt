package net.uprin.mayiuseit.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.uprin.mayiuseit.R;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by CJS on 2017-12-07.
 */

public class EventsSampleActivity extends BaseSampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                Snackbar.make(findViewById(R.id.rootView), getString(R.string.selected_menu_item,
                        menuItem.getTitle()), Snackbar.LENGTH_SHORT).show();
                return false;
            }
        });

    }

}
