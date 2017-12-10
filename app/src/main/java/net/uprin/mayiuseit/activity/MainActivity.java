package net.uprin.mayiuseit.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import net.uprin.mayiuseit.fragment.CategoryFragment;
import net.uprin.mayiuseit.fragment.HomeFragment;
import net.uprin.mayiuseit.fragment.MainActivityFragment;
import net.uprin.mayiuseit.fragment.NoticeFragment;
import net.uprin.mayiuseit.fragment.ProfileFragment;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.util.TokenManager;

import java.util.ArrayList;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final String TITLE = "title";


    TextView textCartItemCount;
    int mCartItemCount = 10;
    TokenManager tokenManager;
    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.category,
            R.drawable.notice,
            R.drawable.account
    };

    //TextView user_nickname,user_email;
    //CircleImageView user_img;
    //LinearLayout success_layout;
    //Button logout_btn;
    //AQuery aQuery;

    @Override
    protected void onResume() {
        super.onResume();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViewPagerAndTabs();


        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id==R.id.subscribe_manage){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "구독 관리 선택", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
                }
                else if (id==R.id.subscribe_setting){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "구독 설정 선택", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
                }

                return false;

            }
        });

    }




        //aQuery = new AQuery(this);

        /** 로그인 성공 시 사용할 뷰
         success_layout = (LinearLayout)findViewById(R.id.success_layout);
         user_nickname =(TextView)findViewById(R.id.user_nickname);
         user_img =(CircleImageView) findViewById(R.id.user_img);
         user_email =(TextView)findViewById(R.id.user_email);
         logout_btn = (Button)findViewById(R.id.logout);
         logout_btn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        if(Session.getCurrentSession().isOpened()) {
        requestLogout();
        }
        }
        });
         Intent intent = getIntent();
         user_nickname.setText(intent.getExtras().getString("user_nickname"));
         user_email.setText(intent.getExtras().getString("user_email"));
         aQuery.id(user_img).image(intent.getExtras().getString("user_img")); // <- 프로필 작은 이미지 , userProfile.getProfileImagePath() <- 큰 이미지
         **/



    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setLogo(R.drawable.toolbar_logo);
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(HomeFragment.createInstance(), "홈");
        pagerAdapter.addFragment(CategoryFragment.createInstance(), "카테고리");
        pagerAdapter.addFragment(NoticeFragment.createInstance(), getString(R.string.tab_3));
        pagerAdapter.addFragment(ProfileFragment.createInstance(), getString(R.string.tab_4));
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount() - 1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        int color = ResourcesCompat.getColor(getResources(), R.color.colorGreyLight, null);


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0])
                .getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(tabIcons[1])
                .getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setIcon(tabIcons[2])
                .getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).setIcon(tabIcons[3])
                .getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
                tab.getIcon().setColorFilter(selectedColor, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int color = ResourcesCompat.getColor(getResources(), R.color.colorGreyLight, null);
                tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupTabIcons() {

    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            //return fragmentTitleList.get(position);
            return null; //타이틀을 보여주기 싫은 경우 실행;
        }
    }


    private void requestLogout() {
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        tokenManager.deleteToken();
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.alarm_history);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.settings){

            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

        }else if (id==R.id.dev_id){

            startActivity(new Intent(getApplicationContext(), DevActivity.class));


        }else if (id==R.id.item3_id){

            Toast.makeText(getApplicationContext(),"item3 is selected",Toast.LENGTH_SHORT).show();

        }else if (id==R.id.logout_menu_item){
            requestLogout();

        }else if (id==R.id.search_id){

            startActivity(new Intent(getApplicationContext(), SearchActivity.class));

        }else if (id==R.id.alarm_history){

            Toast.makeText(getApplicationContext(),"alarm_history",Toast.LENGTH_SHORT).show();

        }else if (id==android.R.id.home){

            finish();

        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}

