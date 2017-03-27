package com.yourcloud.yourcloud.View.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yourcloud.yourcloud.Model.Items.CommnFileItem;
import com.yourcloud.yourcloud.Model.Items.HeaderItem;
import com.yourcloud.yourcloud.Model.Items.SimpleItem;
import com.yourcloud.yourcloud.Model.Utils.DBDao;
import com.yourcloud.yourcloud.Model.Utils.DividerItemDecoration;
import com.yourcloud.yourcloud.Model.Utils.KiiUtils;
import com.yourcloud.yourcloud.Model.Utils.Utils;
import com.yourcloud.yourcloud.Model.Utils.OnUploadItemListener;
import com.yourcloud.yourcloud.Presentor.Adapter.MyFlexibleAdapter;
import com.yourcloud.yourcloud.R;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.Payload;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.helpers.ActionModeHelper;
import eu.davidea.flexibleadapter.helpers.UndoHelper;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;


public class LocalFileFragment extends BaseFragment implements
        ActionMode.Callback, UndoHelper.OnUndoListener,
        FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.OnItemLongClickListener,
        FlexibleAdapter.OnItemMoveListener,
        FlexibleAdapter.OnItemSwipeListener {

    private static final String TAG = "LocalFileFragment";
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ActionModeHelper mActionModeHelper;
    MyFlexibleAdapter mAdapter;

    private OnFragmentInteractionListener mListener;
    public List<AbstractFlexibleItem> list = new ArrayList<>();

    private final Handler mRefreshHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0: // Stop
                    mSwipeRefreshLayout.setRefreshing(false);
                    return true;
                case 1: // Start
                    mSwipeRefreshLayout.setRefreshing(true);
                    return true;
                case 2: // Show empty view
                    ViewCompat.animate(getActivity().findViewById(R.id.empty_view)).alpha(1);
                    return true;
                default:
                    return false;
            }
        }
    });


    public static LocalFileFragment newInstance() {
        LocalFileFragment fragment = new LocalFileFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("data_list", (ArrayList<? extends Parcelable>) list);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_file, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);


        if (savedInstanceState != null) {
            list = (List<AbstractFlexibleItem>) savedInstanceState.get("data_list");
        }
        initView(view);
        return view;
    }


    private void initData() {
        list = DBDao.getInstance(getContext()).getFileList("1");
    }


    private void initView(View view) {

        initSwipefreshLayout(view);
        initRecyclerView(view);
        initActionModeHelper(SelectableAdapter.MODE_SINGLE);

        mListener.onFragmentChange(mSwipeRefreshLayout, mRecyclerView, SelectableAdapter.MODE_SINGLE);

    }

    private void initSwipefreshLayout(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setEnabled(true);
        initializeSwipeToRefresh();

    }

    private void initActionModeHelper(int mode) {
        mActionModeHelper = new ActionModeHelper(mAdapter, getContextMenuResId(), this) {
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

    private void initializeSwipeToRefresh() {

        // Swipe down to force synchronize
        //mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setDistanceToTriggerSync(390);
        //mSwipeRefreshLayout.setEnabled(true); //Controlled by fragments!
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_purple, android.R.color.holo_blue_light,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // Passing true as parameter we always animate the changes between the old and the new data set
//                DatabaseService.getInstance().updateNewItems();
            mAdapter.updateDataSet(DBDao.getInstance(getContext()).getFileList("1"), true);
            mSwipeRefreshLayout.setRefreshing(true);
            mRefreshHandler.sendEmptyMessageDelayed(0, 1500L); //Simulate network time
//                mActionModeHelper.destroyActionModeIfCan();
        });
    }

    private void initRecyclerView(View view) {
        mAdapter = new MyFlexibleAdapter(list, getActivity());
        mAdapter.setNotifyChangeOfUnfilteredItems(true)
                .setMode(SelectableAdapter.MODE_SINGLE);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setFastScroller((FastScroller) view.findViewById(R.id.fast_scroller),
                Utils.getColorAccent(getActivity()));
        mAdapter.setLongPressDragEnabled(true)
                .setHandleDragEnabled(true)
                .setSwipeEnabled(true)
                .setUnlinkAllItemsOnRemoveHeaders(true)
                // Show Headers at startUp, 1st call, correctly executed, no warning log message!
                .setDisplayHeadersAtStartUp(true)
                .setStickyHeaders(true)
                // Simulate developer 2nd call mistake, now it's safe, not executed, no warning log message!
                .setDisplayHeadersAtStartUp(true)
                // Simulate developer 3rd call mistake, still safe, not executed, warning log message displayed!
                .showAllHeaders();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }


    @Override
    public void onActionStateChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        mSwipeRefreshLayout.setEnabled(actionState == ItemTouchHelper.ACTION_STATE_IDLE);
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible flexibleItem = mAdapter.getItem(position);
        if (mAdapter.getMode() != SelectableAdapter.MODE_IDLE && mActionModeHelper != null) {
            boolean activate = mActionModeHelper.onClick(position);
            Log.d(TAG, "Last activated position " + mActionModeHelper.getActivatedPosition());
            return activate;
        } else {
            if (flexibleItem instanceof SimpleItem) {
                //TODO: call your custom Action on item click
                String title = extractTitleFrom((AbstractFlexibleItem) flexibleItem);
            }
            return false;
        }
    }

    @Override
    public void onItemLongClick(int position) {
        mActionModeHelper.onLongClick((AppCompatActivity) getActivity(), position);

    }

    @Override
    public boolean shouldMoveItem(int fromPosition, int toPosition) {
        return true;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if (Utils.hasMarshmallow()) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorTransparent, getActivity().getTheme()));
        } else if (Utils.hasLollipop()) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorTransparent));
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
                List<String> nameList = new ArrayList<>(mAdapter.getSelectedItemCount());
                for (Integer pos : mAdapter.getSelectedPositions()) {
                    message.append(extractTitleFrom(mAdapter.getItem(pos)));
                    nameList.add(extractTitleFrom(mAdapter.getItem(pos)));
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
                                new DBDao(getActivity()).deleteFiles(nameList);
                            }
                        })
                        .remove(mAdapter.getSelectedPositions(),
                                mRecyclerView, message,
                                "取消", 7000);
                return true;
            default:
                return false;
        }
    }

    private String extractTitleFrom(AbstractFlexibleItem item) {
        if (item instanceof CommnFileItem) {
            CommnFileItem exampleItem = (CommnFileItem) item;
            String title = exampleItem.getName();
            return title;
        } else if (item instanceof HeaderItem) {
            HeaderItem headerItem = (HeaderItem) item;
            return headerItem.getTitle();
        }
        return "";
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        if (Utils.hasMarshmallow()) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, getActivity().getTheme()));
        } else if (Utils.hasLollipop()) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onUndoConfirmed(int action) {
        if (action == UndoHelper.ACTION_UPDATE) {

        } else if (action == UndoHelper.ACTION_REMOVE) {
            mAdapter.restoreDeletedItems();
            if (mAdapter.isRestoreWithSelection()) {
                mActionModeHelper.restoreSelection((AppCompatActivity) getActivity());
            }
        }
    }

    @Override
    public void onDeleteConfirmed(int action) {
        for (AbstractFlexibleItem adapterItem : mAdapter.getDeletedItems()) {
            try {
                switch (adapterItem.getLayoutRes()) {
                    case R.layout.recycler_expandable_item:
                        Log.d(TAG, "Confirm removed " + adapterItem);
                        break;
                }

            } catch (IllegalStateException e) {
                if (adapterItem instanceof SimpleItem) {
                    Log.d(TAG, "Confirm removed " + adapterItem);
                }
            }
        }
    }

    @Override
    public void onItemSwipe(int position, int direction) {
        Log.i(TAG, "onItemSwipe position=" + position +
                " direction=" + (direction == ItemTouchHelper.LEFT ? "LEFT" : "RIGHT"));


        List<Integer> positions = new ArrayList<>(1);
        positions.add(position);
        // Build the message
        IFlexible abstractItem = mAdapter.getItem(position);
        CommnFileItem singleItem = (CommnFileItem) mAdapter.getItem(position);
        StringBuilder message = new StringBuilder();
        message.append(extractTitleFrom((AbstractFlexibleItem) abstractItem)).append(" ");
        // Experimenting NEW feature
        if (abstractItem.isSelectable())
            mAdapter.setRestoreSelectionOnUndo(false);

        // Perform different actions
        // Here, option 2A) is implemented
        if (direction == ItemTouchHelper.LEFT) {
            message.append(getString(R.string.action_archived));

            // Example of UNDO color
            int actionTextColor;
            if (Utils.hasMarshmallow()) {
                actionTextColor = getResources().getColor(R.color.colorOrange);
            } else {
                //noinspection deprecation
                actionTextColor = getResources().getColor(R.color.colorOrange);
            }

            new UndoHelper(mAdapter, this)
                    .withPayload(null) //You can pass any custom object (in this case Boolean is enough)
                    .withAction(UndoHelper.ACTION_UPDATE, new UndoHelper.SimpleActionListener() {
                        @Override
                        public boolean onPreAction() {
                            // Return true to avoid default immediate deletion.
                            // Ask to the user what to do, open a custom dialog. On option chosen,
                            // remove the item from Adapter list as usual.
                            return true;
                        }
                    })
                    .withActionTextColor(actionTextColor)
                    .remove(positions, mRecyclerView, message,
                            getString(R.string.undo), UndoHelper.UNDO_TIMEOUT);

            //Here, option 1B) is implemented
        } else if (direction == ItemTouchHelper.RIGHT) {
            message.append(getString(R.string.action_uploaded));
            mSwipeRefreshLayout.setRefreshing(true);
            new KiiUtils().UploadSingleItem(getActivity(), singleItem);


//            mSwipeRefreshLayout.setRefreshing(true);
            new UndoHelper(mAdapter, this)
                    .withPayload(null) //You can pass any custom object (in this case Boolean is enough)
                    .withAction(UndoHelper.ACTION_REMOVE, new UndoHelper.SimpleActionListener() {
                        @Override
                        public void onPostAction() {
                            // Handle ActionMode title
                            if (mAdapter.getSelectedItemCount() == 0)
                                mActionModeHelper.destroyActionModeIfCan();
                            else
                                mActionModeHelper.updateContextTitle(mAdapter.getSelectedItemCount());
                        }
                    })
                    .remove(positions, mRecyclerView, message,
                            getString(R.string.undo), UndoHelper.UNDO_TIMEOUT);
        }
    }


}
