package net.uprin.mayiuseit.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import net.uprin.mayiuseit.R;

/**
 * Created by CJS on 2017-12-07.
 */

public class PositionSampleActivity extends BaseSampleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
