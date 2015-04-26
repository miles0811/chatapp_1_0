package com.wowcow.chat10.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wowcow.chat10.Adapter.DrawerListAdapter;
import com.wowcow.chat10.Consts.Constants;
import com.wowcow.chat10.Fragment.PreferencesFragment;
import com.wowcow.chat10.Loader.RegisterLoader;
import com.wowcow.chat10.Models.ApiError;
import com.wowcow.chat10.Models.NavItem;
import com.wowcow.chat10.Models.User;
import com.wowcow.chat10.Parser.UserSetParser;
import com.wowcow.chat10.R;
import com.wowcow.chat10.Service.CookietimeGCM;
import com.wowcow.chat10.Utils.APIResponse;
import com.wowcow.chat10.Utils.PreferencesUtil;
import com.wowcow.chat10.Utils.RandomGenerator;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private Context mContext;
    private static String TAG = MainActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    Fragment fragment;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = this;

        if(!PreferencesUtil.getGCMRegisterId(mContext).equals("")) {
            //register gcm id, if user register
            CookietimeGCM gcm = new CookietimeGCM(this);
            gcm.startGCM();
        }
        // first login
        if(!PreferencesUtil.getUserToken(mContext).equals("")) {
            register();
        }

        init();
        // todo combine account
    }

    public void register(){
        final View edit_view = (View) getLayoutInflater().inflate(R.layout.dialog_edit, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("請輸入暱稱");
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nick_name = ((EditText) edit_view.findViewById(R.id.edit)).getText().toString();
                if (!nick_name.equals("")){
                    PreferencesUtil.setUserNickName(mContext, nick_name);
                }else{
                    Toast.makeText(mContext, "請輸入暱稱",Toast.LENGTH_LONG).show();
                }
            }
        })
        .setView(edit_view);
        dialog.show();

        getSupportLoaderManager().restartLoader(RandomGenerator.genLodaerId(),null, registerLoaderCallbacks);
    }

    public void init(){
        //todo load chatroom
        mNavItems.add(new NavItem("H"));
        mNavItems.add(new NavItem("P"));
        mNavItems.add(new NavItem("A"));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        //initial fragment
        if(fragment == null){
            //todo input chatroom data
            fragment = new ChatRoomActivity();

            Bundle bundle = new Bundle();
            // if 推播
            if(getIntent() != null) {
                bundle.putString(Constants.CHATROOM, getIntent().getStringExtra(Constants.CHATROOM));
                fragment.setArguments(bundle);
            // by click
            }else{

            }

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .commit();

//            mDrawerList.setItemChecked(position, true);
//            setTitle(mNavItems.get(position).mTitle);

            // Close the drawer
            mDrawerLayout.closeDrawer(mDrawerPane);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
//                if (findViewById(R.id.navList) != null
//                        && mContentFragment != null && mContentFragment.getView() != null) {
                    // Get the width of the NavigationDrawerFragment
                    int width = findViewById(R.id.navList).getWidth();

                    // Set the translationX of the Fragment's View to be the offset (a percentage)
                    // times the width of the NavigationDrawerFragment
                    fragment.getView().setTranslationX(width * slideOffset);
//                fragment.getLayoutInflater().inflate(R.layout.activity_chatroom, null).setTranslationX(width * slideOffset);
//                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(TAG, "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        startActivity(new Intent(this, ChatRoomActivity.class));
    }

    private void selectItemFromDrawer(int position) {
        fragment = new ChatRoomActivity();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
//                .replace(R.id.mainContent, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }


    private LoaderManager.LoaderCallbacks<APIResponse> registerLoaderCallbacks = new LoaderManager.LoaderCallbacks<APIResponse>() {
        UserSetParser parser = new UserSetParser();
        User user;

        @Override
        public Loader<APIResponse> onCreateLoader(int id, Bundle bundle) {
            String gcm_id = PreferencesUtil.getGCMRegisterId(mContext);
            String nick_name = PreferencesUtil.getUserNickName(mContext);
            return new RegisterLoader(mContext, gcm_id, nick_name);
        }

        @Override
        public void onLoadFinished(Loader<APIResponse> loader, APIResponse data) {
//            if (pd != null)
//                pd.dismiss();
            getSupportLoaderManager().destroyLoader(loader.getId());
            if (data.getException() == null) {
                try {
                    ApiError error = parser.toError(data.getResult());
                    if (error == null) {
                        user = parser.toData(data.getResult());
                        PreferencesUtil.setProfile(mContext, user);
                    } else {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG)
                                .show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, data.getException().getMessage(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }

        @Override
        public void onLoaderReset(Loader<APIResponse> arg0) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
