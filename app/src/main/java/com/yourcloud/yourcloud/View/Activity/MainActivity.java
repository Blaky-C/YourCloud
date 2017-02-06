package com.yourcloud.yourcloud.View.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yourcloud.yourcloud.Model.Items.AbstractItem;
import com.yourcloud.yourcloud.Model.Items.HeaderItem;
import com.yourcloud.yourcloud.Model.Items.SimpleItem;
import com.yourcloud.yourcloud.Model.Utils.Constant;
import com.yourcloud.yourcloud.Model.Utils.Utils;
import com.yourcloud.yourcloud.R;
import com.yourcloud.yourcloud.View.Dialog.MessageDialog;
import com.yourcloud.yourcloud.View.Fragment.LocalFileFragment;
import com.yourcloud.yourcloud.View.Fragment.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.Payload;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.helpers.ActionModeHelper;
import eu.davidea.flexibleadapter.helpers.UndoHelper;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ActionMode.Callback, UndoHelper.OnUndoListener,
        OnFragmentInteractionListener,
        FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.OnItemLongClickListener,
        FlexibleAdapter.OnItemMoveListener{

    private static final String STATE_ACTIVE_FRAGMENT = "active_fragment";
    private final String TAG = MainActivity.class.getSimpleName();

    private FlexibleAdapter<AbstractFlexibleItem> mAdapter;
    private ActionModeHelper mActionModeHelper;
    private LocalFileFragment mFragment;
    private RecyclerView mRecyclerView;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;


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
            mActionModeHelper.restoreSelection(this);
        }
    }

    @Override
    public void onFragmentChange(RecyclerView recyclerView, @SelectableAdapter.Mode int mode) {
        mRecyclerView = recyclerView;
        mAdapter = (FlexibleAdapter) recyclerView.getAdapter();
        initializeActionModeHelper(mode);
    }


    private void initView(Bundle savedInstanceState) {
        initFragment(savedInstanceState);
        initToolBar();
        initFloatButton();
        initDrawerLayout();
    }

    private void initializeActionModeHelper(int mode) {
        mActionModeHelper = new ActionModeHelper(mAdapter, mFragment.getContextMenuResId(), this) {
            @Override
            public void updateContextTitle(int count) {
                if (mActionMode != null) {
                    mActionMode.setTitle(count == 1 ?
                            getString(R.string.action_selected_one, Integer.toString(count)) :
                            getString(R.string.action_selected_many, Integer.toString(count)));
                }
            }
        }.withDefaultMode(mode);
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

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initFloatButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void initToolBar() {
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_about) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onItemClick(int position) {
        IFlexible flexibleItem = mAdapter.getItem(position);

        if (mAdapter.getMode() != SelectableAdapter.MODE_IDLE && mActionModeHelper != null) {
            boolean activate = mActionModeHelper.onClick(position);
            Log.d(TAG, "Last activated position " + mActionModeHelper.getActivatedPosition());
            return activate;
        } else {
            if (flexibleItem instanceof SimpleItem ) {
                //TODO: call your custom Action on item click
                String title = extractTitleFrom(flexibleItem);
            }
            return false;
        }
    }

    @Override
    public void onItemLongClick(int position) {
        mActionModeHelper.onLongClick(this, position);
    }

    @Override
    public void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    }

    @Override
    public boolean shouldMoveItem(int fromPosition, int toPosition) {
        return true;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //TODO : this doesn't work with all types of items (of course)..... we need to implement some custom logic. Consider to use also onActionStateChanged() when dragging is completed
//		DatabaseService.getInstance().swapItems(
//				DatabaseService.getInstance().getDatabaseList().indexOf(fromItem),
//				DatabaseService.getInstance().getDatabaseList().indexOf(toItem));
    }

    //    ActionMode
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if (Utils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        } else if (Utils.hasLollipop()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light));
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_all:
                mAdapter.selectAll();
                mActionModeHelper.updateContextTitle(mAdapter.getSelectedItemCount());
                return true;

            case R.id.action_delete:
                StringBuilder message = new StringBuilder();
                message.append(getString(R.string.action_deleted)).append(" ");
                for (Integer pos : mAdapter.getSelectedPositions()) {
                    message.append(extractTitleFrom(mAdapter.getItem(pos)));
                    if (mAdapter.getSelectedItemCount() > 1)
                        message.append(", ");
                }

                mAdapter.setRestoreSelectionOnUndo(true);

                new UndoHelper(mAdapter, this)
                        .withPayload(Payload.CHANGE)
                        .withAction(UndoHelper.ACTION_REMOVE, new UndoHelper.OnActionListener() {
                            @Override
                            public boolean onPreAction() {
                                return false;
                            }

                            @Override
                            public void onPostAction() {
                                mActionModeHelper.destroyActionModeIfCan();
                            }
                        })
                        .remove(mAdapter.getSelectedPositions(),
                                findViewById(R.id.main_view), message,
                                "取消", 7000);

                return true;


            default:
                return false;
        }
    }

    private String extractTitleFrom(IFlexible flexibleItem) {
        if (flexibleItem instanceof AbstractItem) {
            AbstractItem exampleItem = (AbstractItem) flexibleItem;
            String title = exampleItem.getName();

            return title;
        } else if (flexibleItem instanceof HeaderItem) {
            HeaderItem headerItem = (HeaderItem) flexibleItem;
            return headerItem.getTitle();
        }
        return "";
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        if (Utils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Utils.hasLollipop()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    @Override
    public void onUndoConfirmed(int action) {
        if (action == UndoHelper.ACTION_UPDATE) {

        } else if (action == UndoHelper.ACTION_REMOVE) {
            mAdapter.restoreDeletedItems();
            if (mAdapter.isRestoreWithSelection()) {
                mActionModeHelper.restoreSelection(this);
            }
        }
    }

    @Override
    public void onDeleteConfirmed(int action) {
        for (AbstractFlexibleItem adapterItem : mAdapter.getDeletedItems()) {
            try {
                switch (adapterItem.getLayoutRes()) {

                    case R.layout.recycler_expandable_item:
                        Constant.getInstance().removeItem(adapterItem);
                        Log.d(TAG, "Confirm removed " + adapterItem);
                        break;
                }

            } catch (IllegalStateException e) {
                if (adapterItem instanceof SimpleItem) {
                    Constant.getInstance().removeItem(adapterItem);
                    Log.d(TAG, "Confirm removed " + adapterItem);
                }
            }
        }
    }




}
