package net.uprin.mayiuseit.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.style.UpdateLayout;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.adapter.SearchHistoryAdapter;
import net.uprin.mayiuseit.util.HistoryManager;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    ArrayList<String> histories;
    HistoryManager historyManager;
    TextView deleteButton;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Keyword");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.e("onQueryTextChange", "called");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                histories.add(query);
                historyManager.saveHistory(histories);

                Intent intent = new Intent(getApplicationContext(), SearchListActivity.class);
                intent.putExtra("keyword", query);
                intent.putExtra("rankBy", "rgsde");
                startActivity(intent);


                return false;
            }

        });


        return true;    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initToolbar();
        historyManager = HistoryManager.getInstance(getSharedPreferences("prefs",MODE_PRIVATE));
        histories =  historyManager.getHistory();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final SearchHistoryAdapter searchHistoryAdapter = new SearchHistoryAdapter(histories, R.layout.list_item_history, getApplicationContext());
        recyclerView.setAdapter(searchHistoryAdapter);

        deleteButton = (TextView) findViewById(R.id.delete_history_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                histories.clear();
                searchHistoryAdapter.notifyDataSetChanged();
                historyManager.deleteHistory();
                Snackbar.make(getWindow().getDecorView().getRootView(), "검색기록이 삭제되었습니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).show();

            }
        });
       // histories =  historyManager.getHistory();
        //final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //SearchHistoryAdapter adapter = new SearchHistoryAdapter(histories, R.layout.list_item_history, getApplicationContext());
        //recyclerView.setAdapter(adapter);

    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(mToolbar);
        //setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // ↓툴바에 홈버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
