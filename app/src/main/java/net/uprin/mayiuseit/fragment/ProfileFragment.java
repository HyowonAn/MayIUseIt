package net.uprin.mayiuseit.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.LoginActivity;
import net.uprin.mayiuseit.util.TokenManager;

public class ProfileFragment extends Fragment {

    public final static String ITEMS_COUNT_KEY = "MainActivityFragment$ItemsCount";
    ImageView profile_default_background;
    TextView profile_name,user_profile_short;
    TokenManager tokenManager;
    de.hdodenhof.circleimageview.CircleImageView profile_image;
    public static ProfileFragment createInstance() {
        ProfileFragment homeFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        tokenManager = TokenManager.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));

        if(tokenManager.getToken() == null){
            startActivity(new Intent(super.getContext().getApplicationContext(), LoginActivity.class));
            ((Activity)getContext()).finish();
        }



        profile_default_background = v.findViewById(R.id.profile_background_image);
        profile_name = v.findViewById(R.id.profile_name);
        user_profile_short = v.findViewById(R.id.user_profile_short);
        profile_image = v.findViewById(R.id.profile_image);


        profile_name.setText(tokenManager.getTokenData().getNickname().toString());
        user_profile_short.setText(tokenManager.getTokenData().getEmail_address().toString());

        Glide.with(this).load(R.drawable.profile_default_background).into(profile_default_background);

        Glide.with(this).load(tokenManager.getTokenData().getProfile_image())
                .thumbnail(Glide.with(this).load(R.drawable.profile_default).apply(new RequestOptions().centerCrop()))
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.profile_default).centerCrop())
                //.apply(bitmapTransform(new BlurTransformation(100)))
               .into(profile_image);

        return v;

    }

}
