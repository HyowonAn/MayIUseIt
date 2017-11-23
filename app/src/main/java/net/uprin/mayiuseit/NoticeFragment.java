package net.uprin.mayiuseit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJS on 2017-11-23.
 */

public class NoticeFragment extends Fragment{

    String imageUrl[] = Constant.image;
    String names[] = Constant.name;
    String subNames[] = Constant.subName;

    RecyclerView fragment_activity_main;

    public static NoticeFragment createInstance() {
        NoticeFragment noticecFragmentFragment = new NoticeFragment();
        Bundle bundle = new Bundle();
        return noticecFragmentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.list_item_title, container, false);
        return v;
    }

    private List getList() {
        List list = new ArrayList<>();
        for (int i = 0; i < imageUrl.length; i++) {
            List subTitles = new ArrayList<>();
            for (int j = 0; j< subNames.length; j++){
                SubTitle subTitle = new SubTitle(subNames[j]);
                subTitles.add(subTitle);
            }
            Title model = new Title(names[i],subTitles, imageUrl[i]);
            list.add(model);
        }
        return list;
    }
}