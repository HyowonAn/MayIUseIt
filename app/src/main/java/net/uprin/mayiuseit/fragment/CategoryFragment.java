package net.uprin.mayiuseit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.uprin.mayiuseit.activity.DocumentListActivity;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.EventsSampleActivity;

public class CategoryFragment extends Fragment {

    ImageView visit,comment,rated,all,food,machine,cosmetic,water,livestock,aborad,medical,vehicle;
    public static CategoryFragment createInstance() {
        CategoryFragment categoryFragmentFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        return categoryFragmentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        comment= (ImageView) v.findViewById(R.id.category_comment);
        rated = (ImageView) v.findViewById(R.id.category_rated);
        visit = (ImageView) v.findViewById(R.id.category_visit);
        all = (ImageView) v.findViewById(R.id.category_all);
        food = (ImageView) v.findViewById(R.id.category_food);
        machine = (ImageView) v.findViewById(R.id.category_machine);
        cosmetic = (ImageView) v.findViewById(R.id.category_cosmetic);
        livestock = (ImageView) v.findViewById(R.id.category_livestock);
        aborad = (ImageView) v.findViewById(R.id.category_aboard);
        medical = (ImageView) v.findViewById(R.id.category_medical);
        vehicle = (ImageView) v.findViewById(R.id.category_vehicle);
        water = (ImageView) v.findViewById(R.id.category_water);
        Glide.with(this).load(R.drawable.visit_background).into(visit);
        Glide.with(this).load(R.drawable.rated_background).into(rated);
        Glide.with(this).load(R.drawable.comment_background).into(comment);

        Glide.with(this).load(R.drawable.all_background).into(all);
        Glide.with(this).load(R.drawable.food_background).into(food);
        Glide.with(this).load(R.drawable.machine_background).into(machine);
        Glide.with(this).load(R.drawable.cosmetic_background).into(cosmetic);
        Glide.with(this).load(R.drawable.livestock_background).into(livestock);
        Glide.with(this).load(R.drawable.aboard_background).into(aborad);
        Glide.with(this).load(R.drawable.medical_background).into(medical);
        Glide.with(this).load(R.drawable.vehicle_background).into(vehicle);
        Glide.with(this).load(R.drawable.water_background).into(water);

        rated.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(), EventsSampleActivity.class);
            @Override
            public void onClick(View view) {

//                intent.putExtra("category", 0);
//                intent.putExtra("rankBy", "rated_count");
                startActivity(intent);
            }
        });

        visit.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(), DocumentListActivity.class);
            @Override
            public void onClick(View view) {

                intent.putExtra("category", 0);
                intent.putExtra("rankBy", "readed_count");
                startActivity(intent);
            }
        });

        comment.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(), DocumentListActivity.class);
            @Override
            public void onClick(View view) {

                intent.putExtra("category", 0);
                intent.putExtra("rankBy", "commented_count");
                startActivity(intent);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(), DocumentListActivity.class);
            @Override
            public void onClick(View view) {

                intent.putExtra("category", 0);
                intent.putExtra("rankBy", "rgsde");
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(), DocumentListActivity.class);
            @Override
            public void onClick(View view) {

                intent.putExtra("category", 216);
                intent.putExtra("rankBy", "rgsde");
                startActivity(intent);
            }
        });

        machine.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(getActivity(), DocumentListActivity.class);
            @Override
            public void onClick(View view) {

                intent.putExtra("category", 217);
                intent.putExtra("rankBy", "rgsde");
                startActivity(intent);
            }
        });


        return v;
    }

}
