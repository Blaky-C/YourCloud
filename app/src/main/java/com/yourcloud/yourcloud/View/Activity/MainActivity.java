package com.yourcloud.yourcloud.View.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.yourcloud.yourcloud.Model.Utils.KiiUtils;
import com.yourcloud.yourcloud.Model.Utils.OnUploadItemListener;
import com.yourcloud.yourcloud.Model.Utils.Utils;
import com.yourcloud.yourcloud.Presentor.Adapter.MyFlexibleAdapter;
import com.yourcloud.yourcloud.R;
import com.yourcloud.yourcloud.View.Dialog.MessageDialog;
import com.yourcloud.yourcloud.View.Fragment.BaseFragment;
import com.yourcloud.yourcloud.View.Fragment.CloudFileFragment;
import com.yourcloud.yourcloud.View.Fragment.LocalFileFragment;
import com.yourcloud.yourcloud.View.Fragment.OnFragmentInteractionListener;
import com.yourcloud.yourcloud.View.Fragment.UploadFileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

public class MainActivity extends AppCompatActivity implements
        OnFragmentInteractionListener {

    private static final String STATE_ACTIVE_FRAGMENT = "active_fragment";
    private final String TAG = MainActivity.class.getSimpleName();
    //    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private List<AbstractFlexibleItem> fileList;
    private List<AbstractFlexibleItem> tempList;
    private MyFlexibleAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseFragment mFragment;

    int i = 0;
    BaseFragment[] mainFragments = {LocalFileFragment.newInstance(), CloudFileFragment.newInstance()};


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.container)
//    CoordinatorLayout container;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.btn_switch)
    SwitchButton mSwitchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView(savedInstanceState);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState");
        mAdapter.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, STATE_ACTIVE_FRAGMENT, mFragment);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && mAdapter != null) {
            mAdapter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void initView(Bundle savedInstanceState) {
        initFragment(savedInstanceState);
        initToolBar();
        initFloatButton();
        initDrawerLayout();
        initSwitchButton();
    }

    private void initSwitchButton() {
        mSwitchButton.setOnCheckedChangeListener((compoundButton, b) -> {

            mFragment = mainFragments[(++i) % 2];
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, mFragment).commit();

        });

    }


    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFragment = (LocalFileFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_ACTIVE_FRAGMENT);
        }
        if (mFragment == null) {
            mFragment = LocalFileFragment.newInstance();

        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, mFragment).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.bringToFront();
        drawer.requestLayout();
        toggle.syncState();

        View header = mNavigationView.getHeaderView(0);
        TextView userName = (TextView) header.findViewById(R.id.textView);
        userName.setText(getIntent().getStringExtra("userName"));


        mNavigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_localFiles) {
                mFragment = LocalFileFragment.newInstance();
            } else if (id == R.id.nav_cloudFiles) {
                mFragment = CloudFileFragment.newInstance();
            } else if (id == R.id.nav_uploadFiles) {
                mFragment = UploadFileFragment.newInstance();
            } else if (id == R.id.nav_downloadFiles) {

            } else if (id == R.id.nav_manage) {

            } else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {

            } else if (id == R.id.nav_about) {
                MessageDialog.newInstance(
                        R.drawable.ic_info_grey600_24dp,
                        getString(R.string.about_title),
                        getString(R.string.about_body,
                                Utils.getVersionName(MainActivity.this),
                                Utils.getVersionCode(MainActivity.this)))
                        .show(getFragmentManager(), MessageDialog.TAG);
            }

            if (mFragment != null) {
                item.setChecked(true);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, mFragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    public List<AbstractFlexibleItem> getSelectedItems() {
        fileList = new ArrayList<>();
        tempList = new ArrayList<>();
        tempList = mAdapter.getDataList();
        final List<Integer> indexList = mAdapter.getSelectedPositions();
        for (int i = 0; i < indexList.size(); i++) {
            fileList.add(tempList.get(indexList.get(i)));
        }
        return fileList;
    }


    private void initFloatButton() {
        fab.setOnClickListener(view -> {

            new KiiUtils().Upload(MainActivity.this, getSelectedItems());

        });
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            MessageDialog.newInstance(
                    R.drawable.ic_info_grey600_24dp,
                    getString(R.string.about_title),
                    getString(R.string.about_body,
                            Utils.getVersionName(MainActivity.this),
                            Utils.getVersionCode(MainActivity.this)))
                    .show(getFragmentManager(), MessageDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, @SelectableAdapter.Mode int mode) {
        mAdapter = (MyFlexibleAdapter) recyclerView.getAdapter();
        mSwipeRefreshLayout = swipeRefreshLayout;
    }


}
