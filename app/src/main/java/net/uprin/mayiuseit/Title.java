package net.uprin.mayiuseit;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by CJS on 2017-11-23.
 */

public class Title extends ExpandableGroup<SubTitle> {

    private String imageUrl;

    public Title(String title, List<SubTitle> items, String imageUrl) {
        super(title, items);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
