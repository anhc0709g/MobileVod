package com.itpro.mobileapp.mobilevod;


import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.itpro.mobileapp.mobilevod.account.AccountLoginFragment;
import com.itpro.mobileapp.mobilevod.homePage.HomePage;
import com.itpro.mobileapp.mobilevod.leftMenu.LeftMenuFragment;
import com.itpro.mobileapp.mobilevod.leftMenu.LeftMenuItem;


public class MainActivity extends BaseActivity implements LeftMenuFragment.OnLeftMenuItemSelected, SearchView.OnQueryTextListener{


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    LeftMenuFragment leftMenuDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setUpToolBar();
        setTitle("Movie vod");

        leftMenuDrawerLayout=(LeftMenuFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        leftMenuDrawerLayout.callBackListener=this;
        DrawerLayout leftMenuDrawer= (DrawerLayout) findViewById(R.id.DrawerLayout);
        leftMenuDrawerLayout.setUp(toolbar);


        fragmentManager=getSupportFragmentManager();
        fragmentTransaction =fragmentManager.beginTransaction();
        HomePage homePage=new HomePage();
        fragmentTransaction.replace(R.id.container, homePage);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        SearchManager SManager =  (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchViewAction = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchViewAction.setSearchableInfo(SManager.getSearchableInfo(getComponentName()));
        searchViewAction.setIconifiedByDefault(true);
        searchViewAction.setOnQueryTextListener(this);
        searchViewAction.setMaxWidth(1000000);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if(leftMenuDrawerLayout.isDrawerOpen()){
            leftMenuDrawerLayout.closeDrawer();
        }else {


            //if (getCurrentFragmentName() == "fragB") {
           // fragmentTransaction=fragmentManager.beginTransaction();
            if(fragmentManager.getBackStackEntryCount()>0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                leftMenuDrawerLayout.setDrawerIndicatorEnabled(true);
                fragmentManager.popBackStack();
            }else
                super.onBackPressed();
/*
            fragmentTransaction.remove(loginFragment);
                fragmentTransaction.commit();
*/


           // super.onBackPressed();
        }
    }
    AccountLoginFragment loginFragment;
    @Override
    public void onLeftMenuItemSelected(LeftMenuItem selectedItem) {
        leftMenuDrawerLayout.closeDrawer();
        if(selectedItem.ref==LeftMenuItem.REF_LOGIN){

            if( getSupportFragmentManager().findFragmentByTag(AccountLoginFragment.TAG)==null){
                fragmentManager.popBackStackImmediate();
                fragmentTransaction =fragmentManager.beginTransaction();
                loginFragment=new AccountLoginFragment();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
                fragmentTransaction.replace(R.id.container, loginFragment);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                fragmentTransaction.addToBackStack(loginFragment.TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
            // Log.i("anhnt","fragmentManager.getBackStackEntryCount()="+fragmentManager.getBackStackEntryCount());
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
