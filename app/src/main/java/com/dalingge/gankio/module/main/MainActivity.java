package com.dalingge.gankio.module.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;

import com.dalingge.gankio.R;
import com.dalingge.gankio.common.base.BaseActivity;
import com.dalingge.gankio.common.base.factory.RequiresPresenter;
import com.dalingge.gankio.main.activity.AboutActivity;
import com.dalingge.gankio.main.fragment.WelfareListFragment;
import com.dalingge.gankio.module.home.HomeFragment;
import com.dalingge.gankio.module.mine.MineFragment;
import com.dalingge.gankio.module.star.StarFragment;
import com.dalingge.gankio.util.PreferencesUtils;

import butterknife.BindView;



/**
 * FileName:MainActivity.java
 * Description:主页
 * Author:dingboyang
 * Email:445850053@qq.com
 * Date:16/4/2
 */
@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.buttonNavigationView)
    BottomNavigationView buttonNavigationView;

    private HomeFragment mHomeFragment;
    private StarFragment mStarFragment;
    private MineFragment mMineFragment;

//    @BindView(R.id.tab_layout)
//    TabLayout tablayout;
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;
//    @BindView(R.id.fab)
//    FloatingActionButton fab;
//
//    private MainPresenter mainPresenter;
//    private ViewPageFragmentAdapter tabsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setDefaultFragment();
        buttonNavigationView.setOnNavigationItemSelectedListener(this);
//        setTitle(R.string.title_main);
//
//        mainPresenter=new MainPresenter(this);
//
//        tabsAdapter = new ViewPageFragmentAdapter(getSupportFragmentManager(), tablayout, viewPager);
//        String[] title = getResources().getStringArray(R.array.home_viewpage_arrays);
//
//        Observable.from(title).subscribe(s ->  {
//            tabsAdapter.addTab(s, "", WelfareListFragment.class,
//                    getBundle(s));
//
//        });
//        viewPager.setOffscreenPageLimit(title.length);
//        viewPager.setAdapter(tabsAdapter);
//        tablayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
//        fab.setOnClickListener(view ->  startActivity(SubmitGankActivity.newIntent(view.getContext())));
//
//
//        mainPresenter.getSplashImage();

    }



    /**
     * 设置默认的Fragment
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHomeFragment = HomeFragment.newInstance("Home");
        transaction.replace(R.id.contentLayout, mHomeFragment);
        transaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (item.getItemId()){
            case R.id.item_navigation_home:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance(item.getTitle().toString());
                }
                transaction.replace(R.id.contentLayout, mHomeFragment);
                break;
            case R.id.item_navigation_star:
                if (mStarFragment == null) {
                    mStarFragment = StarFragment.newInstance(item.getTitle().toString());
                }
                transaction.replace(R.id.contentLayout, mStarFragment);
                break;
            case R.id.item_navigation_mine:
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance(item.getTitle().toString());
                }
                transaction.replace(R.id.contentLayout, mMineFragment);
                break;
        }
        // 事务提交
        transaction.commit();
        return false;
    }
    /**
     * 基类会根据不同的orderType展示相应的数据
     *
     * @param type 要显示的数据类别
     * @return
     */
    private Bundle getBundle(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(WelfareListFragment.BUNDLE_KEY_TYPE, type);
        return bundle;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_day_night);
        initNotifiableItemState(item);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int uiMode = getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        }
    }

    private void initNotifiableItemState(MenuItem item) {
        PreferencesUtils preferencesUtils = new PreferencesUtils(this);
        item.setChecked(preferencesUtils.getBoolean(R.string.action_day_night, false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_day_night:
                PreferencesUtils preferencesUtils = new PreferencesUtils(this);
                if (item.isChecked()) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    preferencesUtils.saveBoolean(R.string.action_day_night, false);
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    preferencesUtils.saveBoolean(R.string.action_day_night, true);
                }
                recreate();
                return true;
            case R.id.action_about:
                startActivity(AboutActivity.newIntent(this));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
