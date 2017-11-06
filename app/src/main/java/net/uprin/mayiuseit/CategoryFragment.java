package net.uprin.mayiuseit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CategoryFragment extends Fragment {

    TextView comment,rated,visit,all,food,machine,cosmetic,water,aborad,medical,vehicle;
    public static CategoryFragment createInstance() {
        CategoryFragment categoryFragmentFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        return categoryFragmentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        comment= (TextView) v.findViewById(R.id.category_comment);
        rated = (TextView) v.findViewById(R.id.category_rated);
        visit = (TextView) v.findViewById(R.id.category_visit);
        comment.setBackgroundResource(R.drawable.comment_background);
        rated.setBackgroundResource(R.drawable.rated_background);
        visit.setBackgroundResource(R.drawable.visit_background);

        return v;
    }

}
