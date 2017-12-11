package net.uprin.mayiuseit.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.model.DocumentListResponse;
import net.uprin.mayiuseit.model.SubscribeListConfigResponse;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiError;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.util.TokenManager;
import net.uprin.mayiuseit.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeActivity extends AppCompatActivity {

    private static final String TAG = SubscribeActivity.class.getSimpleName();

    ApiInterface api;
    TokenManager tokenManager;
    SubscribeListConfigResponse subscribeListConfigResponse;

    SwitchCompat subscribe_foods,subscribe_machines,subscribe_cosmetics,subscribe_waters,subscribe_livestocks,subscribe_aborads,subscribe_medicals,subscribe_vehicles;
    Button comment_dialog_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        initToolbar();

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        if(tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);
        Call<SubscribeListConfigResponse> call = api.subscribe_list_config();
        call.enqueue(new Callback<SubscribeListConfigResponse>() {
            @Override
            public void onResponse(Call<SubscribeListConfigResponse> call, Response<SubscribeListConfigResponse> response) {
                if(response.isSuccessful()){
                    subscribeListConfigResponse = response.body();
                    bindData();
                } else {
                    ApiError apiError = Utils.converErrors(response.errorBody());
                    Snackbar.make(getWindow().getDecorView().getRootView(), apiError.message(), Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                    subscribeListConfigResponse = new SubscribeListConfigResponse(false,false,false,false,false,false,false,false,"아직 추가하지 않았음");
                    // Toast.makeText(getApplicationContext(), response.errorBody()., Toast.LENGTH_LONG).show();
                    bindData();
                }
            }

            @Override
            public void onFailure(Call<SubscribeListConfigResponse> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });
    }
    private void bindData(){
        subscribe_foods = (SwitchCompat) findViewById(R.id.subscribe_foods);
        subscribe_foods.setChecked(subscribeListConfigResponse.getFoods());
        subscribe_foods.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setFoods(isChecked);
            }
        });
        subscribe_machines = (SwitchCompat) findViewById(R.id.subscribe_machines);
        subscribe_machines.setChecked(subscribeListConfigResponse.getMachines());
        subscribe_machines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setMachines(isChecked);
            }
        });


        subscribe_cosmetics = (SwitchCompat) findViewById(R.id.subscribe_cosmetics);
        subscribe_cosmetics.setChecked(subscribeListConfigResponse.getCosmetics());
        subscribe_cosmetics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setCosmetics(isChecked);
            }
        });


        subscribe_waters = (SwitchCompat) findViewById(R.id.subscribe_waters);
        subscribe_waters.setChecked(subscribeListConfigResponse.getWaters());
        subscribe_waters.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setWaters(isChecked);
            }
        });

        subscribe_livestocks = (SwitchCompat) findViewById(R.id.subscribe_livestocks);
        subscribe_livestocks.setChecked(subscribeListConfigResponse.getLivestocks());
        subscribe_livestocks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setLivestocks(isChecked);
            }
        });


        subscribe_aborads = (SwitchCompat) findViewById(R.id.subscribe_aborads);
        subscribe_aborads.setChecked(subscribeListConfigResponse.getAborads());
        subscribe_aborads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setAborads(isChecked);
            }
        });

        subscribe_medicals = (SwitchCompat) findViewById(R.id.subscribe_medicals);
        subscribe_medicals.setChecked(subscribeListConfigResponse.getMedicals());
        subscribe_medicals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setMedicals(isChecked);
            }
        });

        subscribe_vehicles = (SwitchCompat) findViewById(R.id.subscribe_vehicles);
        subscribe_vehicles.setChecked(subscribeListConfigResponse.getVehicles());
        subscribe_vehicles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                subscribeListConfigResponse.setVehicles(isChecked);
            }
        });

        comment_dialog_button = (Button) findViewById(R.id.comment_dialog_button);

        comment_dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SubscribeListConfigResponse> call = api.write_subscribe_list_config(subscribeListConfigResponse);
                call.enqueue(new Callback<SubscribeListConfigResponse>() {
                    @Override
                    public void onResponse(Call<SubscribeListConfigResponse> call, Response<SubscribeListConfigResponse> response) {
                        if(response.isSuccessful()){
                            subscribeListConfigResponse = response.body();
                            bindData();
                        } else {
                            ApiError apiError = Utils.converErrors(response.errorBody());
                            Snackbar.make(getWindow().getDecorView().getRootView(), apiError.message(), Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                            subscribeListConfigResponse = new SubscribeListConfigResponse(false,false,false,false,false,false,false,false,"아직 추가하지 않았음");
                            // Toast.makeText(getApplicationContext(), response.errorBody()., Toast.LENGTH_LONG).show();
                            bindData();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubscribeListConfigResponse> call, Throwable t) {
                        Log.e(TAG," Response Error "+t.getMessage());
                    }
                });
            }
        });


    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.subscribe_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("뒤로가기");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //mToolbar.setLogo(R.drawable.toolbar_logo);
        // ↓툴바에 홈버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
