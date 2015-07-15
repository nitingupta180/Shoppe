package com.example.shoppe.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shoppe.R;
import com.example.shoppe.datamodel.DrawerItem;
import com.example.shoppe.utilities.Constants;
import com.example.shoppe.utilities.Utils;

import java.util.ArrayList;


public class ShoppeHomeActivity extends FragmentActivity {

    private static final String TAG = "ShoppeHomeActivity";

    private Context mContext;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<DrawerItem> mDrawerItems;
    private DrawerAdapter mDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppe_home);

        mContext = getApplicationContext();

        Utils.configureActionBarStyle(this);

        mTitle = mDrawerTitle = getTitle();

        populateDrawerItems();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerAdapter = new DrawerAdapter();
        mDrawerList.setAdapter(mDrawerAdapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerList.setItemChecked(position, true);
                openFragment(position);
            }
        });

        if(savedInstanceState == null) {
            mDrawerList.setItemChecked(0, true);
            openFragment(0);
        }
    }

    private void populateDrawerItems() {
        if(mDrawerItems == null) {
            mDrawerItems = new ArrayList<DrawerItem>();
        }

        mDrawerItems.clear();

        mDrawerItems.add(new DrawerItem(Constants.FILE_TITLE_1, ""));
        mDrawerItems.add(new DrawerItem(Constants.FILE_TITLE_2, ""));

        if(mDrawerAdapter != null) {
            mDrawerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shoppe_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDrawerItems != null) {
            mDrawerItems.clear();
            mDrawerItems = null;
        }
    }

    private void openFragment(int position) {
        if(mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        Fragment fragment = new MyContentFragment();;
        Bundle args = null;
        String fragmentTag = null;

        //if(position == 0 || position == 1) {
            mTitle = mDrawerItems.get(position).getTitle();
            args = new Bundle();
            args.putString(Constants.FRAGMENT_TITLE, mTitle.toString());
            args.putInt(Constants.DRAWER_POSITION, position);
            fragment.setArguments(args);
            fragmentTag = Constants.FRAGMENT_TAG_MY_CONTENT;

            getActionBar().setTitle(mTitle);
        //}

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragmentTag).commit();
        }

    }

    class DrawerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDrawerItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mDrawerItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final String currentHeader = mDrawerItems.get(position).getTitle();

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.drawer_list_item, null);
            }

            TextView itemTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);

            if (itemTitle != null) {
                itemTitle.setText(currentHeader);
            }

            if(((ListView) parent).isItemChecked(position)) {
                itemTitle.setTypeface(Typeface.create(Constants.TYPEFACE_SANS_SERIF, Typeface.BOLD));
            } else {
                itemTitle.setTypeface(Typeface.create(Constants.TYPEFACE_SANS_SERIF_LIGHT, Typeface.NORMAL));
            }

            return convertView;
        }
    }
}
