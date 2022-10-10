package com.yarenchoi.tenderness.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yarenchoi.tenderness.R;
import com.yarenchoi.tenderness.BaseApplication;
import com.yarenchoi.tenderness.ui.fragment.MemoryFragment;
import com.yarenchoi.tenderness.ui.fragment.VoiceFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] titles;
    private Fragment[] fragments;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setUpView() {
        titles = new String[]{
                getString(R.string.tab_memories),
                getString(R.string.tab_voice)};
        fragments = new Fragment[titles.length];
        fragments[0] = new MemoryFragment();
        fragments[1] = new VoiceFragment();

        ViewPager contentPager = (ViewPager) findViewById(R.id.vp_content);
        contentPager.setAdapter(pagerAdapter);
        //setupWithViePager必须在ViewPager.setAdapter()之后调用！
        //addOnPageChangeListener和setOnTabSelectedListener和setTabsFromPagerAdapter的封装。
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_main);
        tabLayout.setupWithViewPager(contentPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    };

    @Override
    protected void handler(Message msg) {
    }

    private void exit() {
        Snackbar.make(findViewById(R.id.cl_main), "Exit?", Snackbar.LENGTH_LONG)
                .setAction("yes!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseApplication.getInstance().onTerminate();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            exit();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_book) {
            CodeBookActivity.startActivity(MainActivity.this);
        } else if (id == R.id.nav_author) {
            AuthorActivity.startActivity(MainActivity.this);
        } else if (id == R.id.nav_sync) {
            // TODO: 2016/9/5 同步数据
        } else if (id == R.id.nav_manage) {
            SettingsActivity.startActivity(MainActivity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
