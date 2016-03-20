package com.perfectljy.ersanshi.Widget;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.perfectljy.ersanshi.Adapter.ShowRecordRecyclerViewAdapter;
import com.perfectljy.ersanshi.Base.BaseObserverActivity;
import com.perfectljy.ersanshi.Event.EventType;
import com.perfectljy.ersanshi.Event.NotifyInfo;
import com.perfectljy.ersanshi.MyView.DividerItemDecoration;
import com.perfectljy.ersanshi.R;
import com.perfectljy.ersanshi.Utils.DataProviderHelper;
import com.perfectljy.ersanshi.Utils.IsDoubleClick;
import com.perfectljy.ersanshi.db.model.BaseColumns;
import com.perfectljy.ersanshi.db.model.RecordColumns;
import com.perfectljy.ersanshi.db.model.RecordModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseObserverActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView addRecord;
    private AddRecordFragment addRecordFragment;
    private LinearLayout mRecordContentLy;
    private int mCurrentFragment;
    private final static int MAIN = 0;
    private final static int ADDRECORD = 1;
    private List<RecordModel> recordModelList;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ShowRecordRecyclerViewAdapter adapter;
    ContentResolver resolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawer();

    }

    @Override
    protected void findView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.left_menu_dl);
        addRecord = (TextView) findViewById(R.id.add_normal_tv);
        mRecordContentLy = (LinearLayout) findViewById(R.id.detail_ll);
        recyclerView = (RecyclerView) findViewById(R.id.show_record_recyclerviews);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("我的日记");
        mToolbar.setSubtitle("I am so happy");
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        resolver = getContentResolver();
        Cursor cursor = resolver.query(RecordColumns.CONTENT_URI, null, null, null, null);
        recordModelList = getModels(cursor);
        adapter = new ShowRecordRecyclerViewAdapter(this, recordModelList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void setOnClick() {
        addRecord.setOnClickListener(this);
        adapter.setOnItemClickListene(new ShowRecordRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onShowClick(View view, int position) {
                Toast.makeText(mContext, "show", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(View view, final int position) {

                new AlertDialog.Builder(MainActivity.this).setTitle("确定要删除吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.deleteItem(position);
                    }
                }).setNegativeButton("取消", null).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(mContext, "删除日记", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void initSvgView() {

    }

    @Override
    protected void onChange(NotifyInfo notifyInfo) {
        String eventType = notifyInfo.getEventType();
        if (eventType.equals(EventType.EVENT_REFRESH_RECORD)) {
            Cursor cursor = resolver.query(RecordColumns.CONTENT_URI, null, null, null, null);
            recordModelList = getModels(cursor);
            adapter.setRecordModelList(recordModelList);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected String[] getObserverEventType() {
        return new String[]{
                EventType.EVENT_REFRESH_RECORD
        };
    }

    @Override
    public void onClick(View v) {
        if (IsDoubleClick.isFastDoubleClick()) {
            return;
        }
        mRecordContentLy.setVisibility(View.VISIBLE);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_bottom_enter, R.anim.fragment_bottom_exit, R.anim.fragment_bottom_enter, R.anim.fragment_bottom_exit);
        addRecordFragment = new AddRecordFragment();
        ft.addToBackStack(null);
        ft.replace(R.id.detail_ll, addRecordFragment, "").commit();
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {

            }

            if (!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            setBackStackPressed();
        } else {
            super.onBackPressed();
        }
    }

    //fragment设置回退栈的情况下
    private void setBackStackPressed() {
        switch (mCurrentFragment) {
            case ADDRECORD:
                if (addRecordFragment != null) {
//                    addRecordFragment.saveOrUpdateRecordToDb();
                }
                getFragmentManager().popBackStack();
                addRecordFragment = null;//设置为NULL，让下一次进入界面的时候重新渲染
                setStatusBarView(getResources().getColor(R.color.main_bg));
                hideKeyBoard();
                bindMainToolBar();
                break;
            default:
                getFragmentManager().popBackStack();
                break;
        }
    }

    //设置侧滑菜单
    protected void setDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    /**
     * 从fragment返回可能需要重新绑定Toolbar
     */
    private void bindMainToolBar() {
        iniToolBar();//因为在fragment中已经绑定过toolbar了，重新绑定toolbar
        setDrawer();//重新监听侧滑菜单
        refreshToolBar(MAIN);
    }

    private void refreshToolBar(int currentFragment) {
        mCurrentFragment = currentFragment;
        invalidateOptionsMenu();
    }

    //得到记录model的集合
    public List<RecordModel> getModels(Cursor cursor) {
        List<RecordModel> recordModelList = new ArrayList<>();
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            RecordModel recordModel = new RecordModel();
            recordModel.setId(DataProviderHelper.parseString(cursor, BaseColumns._ID));
            recordModel.setTitle(DataProviderHelper.parseString(cursor, RecordColumns.TITLE));
            recordModel.setDate(DataProviderHelper.parseString(cursor, RecordColumns.DATE));
            recordModel.setWeather(DataProviderHelper.parseString(cursor, RecordColumns.WEATHER));
            recordModel.setContent(DataProviderHelper.parseString(cursor, RecordColumns.CONTENT));
            recordModel.setIsSecart(DataProviderHelper.parseInt(cursor, RecordColumns.IS_SECRET));
            recordModelList.add(recordModel);
        }
        return recordModelList;
    }

}
