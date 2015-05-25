package com.itpro.mobileapp.mobilevod.leftMenu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.gc.materialdesign.widgets.Dialog;
import com.itpro.mobileapp.mobilevod.BaseFragment;
import com.itpro.mobileapp.mobilevod.R;
import com.itpro.mobileapp.mobilevod.util.DownloadImageTask;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLeftMenuItemSelected} interface
 * to handle interaction events.
 */
public class LeftMenuFragment extends BaseFragment {


    public OnLeftMenuItemSelected callBackListener;
    private List<LeftMenuItem> listMenu;
    //Similarly we Create a String Resource for the name and email in the left_menu_row_header_profile view
    //And we also create a int resource for profile picture in the left_menu_row_header_profile view



    private RecyclerView mRecyclerView;                           // Declaring RecyclerView
    private RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    private RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mFragmentContainerView;
    private LinearLayout llLogined;
    private CircleImageView imgAvatar;
    private Button btLogin;
    private ImageView btLogout;
    private Profile profile;
    private AccessTokenTracker accessTokenTracker;


    CallbackManager callbackManager;


    public LeftMenuFragment() {
        // Required empty public constructor
    }

    public void setDrawerIndicatorEnabled(boolean enabled){
        mDrawerToggle.setDrawerIndicatorEnabled(enabled);
        mDrawerToggle.syncState();
    }
    public void setUp( Toolbar toolbar) {
             // Setting the layout Manager


        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.DrawerLayout);        // mDrawerLayout object Assigned to the view
        mFragmentContainerView=getActivity().findViewById(R.id.navigation_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;


                getActivity().invalidateOptionsMenu();
            }



        }; // mDrawerLayout Toggle Object Made

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle); // mDrawerLayout Listener set to the mDrawerLayout toggle
       // mDrawerLayout.openDrawer(getActivity().findViewById(R.id.navigation_drawer));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.left_menu_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.leftMenuDrawList); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        listMenu=new ArrayList<>();
        mAdapter = new LeftMenuAdapter(listMenu);
        mRecyclerView.setAdapter(mAdapter);

        getListMenu();
             // Creating the Adapter of LeftMenuAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,left_menu_row_header_profile view name, left_menu_row_header_profile view email,
        // and left_menu_row_header_profile view profile picture

                                    // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(getActivity());                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListenter(getActivity(), mRecyclerView, new ClickListenter() {
            @Override
            public void onclick(View view, int possition) {
                Toast.makeText(getActivity(), "onclick" + possition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongclick(View view, int possition) {
                Toast.makeText(getActivity(), "onLongclick" + possition, Toast.LENGTH_SHORT).show();
            }
        }));

        llLogined=(LinearLayout)rootView.findViewById(R.id.llLogined);
        btLogin=(Button)rootView.findViewById(R.id.btLogin);
        imgAvatar=(CircleImageView)rootView.findViewById(R.id.imgAvatar);
        btLogout=(ImageView)rootView.findViewById(R.id.btLogout);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackListener.onLeftMenuItemSelected(new LeftMenuItem("Login", LeftMenuItem.REF_LOGIN, LeftMenuItem.VIEW_TYPE_MENU_ITEM, 1));

            }
        });
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"logingout",Toast.LENGTH_SHORT).show();;
                logout();
            }
        });

        initFB();
        checkLogin();
        return rootView;


    }

    private void logout(){
/*        Dialog dialog = new Dialog(getActivity(),"Confirm", "Are you sure want to Logout?");


        dialog.addCancelButton("NO");

        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
            }
        });

        dialog.show();
        dialog.getButtonAccept().setText("OK");
        Log.i("anhnt", "getButtonAccept" + dialog.getButtonAccept() + "");*/

        new AlertDialog.Builder(getActivity())
                .setTitle("Confirm")
                .setMessage("Are you sure want to Logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                       //  setViewLogedOut();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
              //  .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    private void initFB(){
        llLogined.setVisibility(View.GONE);
        btLogin.setVisibility(View.VISIBLE);
        callbackManager = CallbackManager.Factory.create();
        if(AccessToken.getCurrentAccessToken()!=null) {
            loadFbAccount();
        }
       // if(AccessToken.getCurrentAccessToken()==null){
            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(
                        AccessToken oldAccessToken,
                        AccessToken currentAccessToken) {

                    Toast.makeText(getActivity(),"onCurrentAccessTokenChanged",Toast.LENGTH_SHORT).show();
                    loadFbAccount();
                }
            };
            accessTokenTracker.startTracking();
       /* }else{
            //loadFbAccount();
        }*/

    }
    private void loadFbAccount(){

        if(AccessToken.getCurrentAccessToken()!=null){
            setViewLogedIn();
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                    com.android.volley.toolbox.ImageLoader im;

                    Log.i("anhnt", "user=" + user);
                  //  LruCache
                    TextView txtEmail=(TextView)llLogined.findViewById(R.id.txtEmail);
                    try {
                        txtEmail.setText(user.getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //  Log.i("anhnt", "graphResponse=" + graphResponse);

                }
            });
            request.executeAsync();


            Profile profile=Profile.getCurrentProfile();
            if(profile != null){
                TextView txtName=(TextView)llLogined.findViewById(R.id.txtName);
                txtName.setText(profile.getName());

              //  Log.i("anhnt","profile.getProfilePictureUri(64, 64).toString()="+profile.getProfilePictureUri(64, 64).toString());
                new DownloadImageTask(imgAvatar).execute(profile.getProfilePictureUri(70, 70).toString());
            }else{

                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(
                            Profile oldProfile,
                            Profile currentProfile) {
                       this.stopTracking();
                        Profile profile=Profile.getCurrentProfile();
                        if(profile != null) {
                            TextView txtName = (TextView) llLogined.findViewById(R.id.txtName);
                            txtName.setText(profile.getName());


                            //  Log.i("anhnt","profile.getProfilePictureUri(64, 64).toString()="+profile.getProfilePictureUri(64, 64).toString());
                            new DownloadImageTask(imgAvatar).execute(profile.getProfilePictureUri(64, 64).toString());
                        }
                    }
                };
            }
            Log.i("anhnt", "profile=" + profile);

        }else{
            setViewLogedOut();
        }
    }

    private void setViewLogedOut(){

        llLogined.setVisibility(View.GONE);
        btLogin.setVisibility(View.VISIBLE);
        imgAvatar.setImageResource(R.drawable.unknowavata);
    }
    private void setViewLogedIn(){

        llLogined.setVisibility(View.VISIBLE);
        btLogin.setVisibility(View.INVISIBLE);
        imgAvatar.setImageResource(R.drawable.unknowavata);
    }


    private void checkLogin(){
       // llLogined.setVisibility(View.GONE);
    }
    private void getListMenu(){


        LeftMenuItem menuItem=new LeftMenuItem("Wish List",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_heart);
        listMenu.add(menuItem);
        menuItem=new LeftMenuItem("History",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_history);
        listMenu.add(menuItem);


        menuItem=new LeftMenuItem("",1,LeftMenuItem.VIEW_TYPE_DIVIDER,-1);
        listMenu.add(menuItem);

        menuItem= new LeftMenuItem("Home",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_home);
        listMenu.add(menuItem);

        menuItem=new LeftMenuItem("Film",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_movie);
        listMenu.add(menuItem);
        menuItem=new LeftMenuItem("Video Clip",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_video);
        listMenu.add(menuItem);
        menuItem=new LeftMenuItem("Music",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_music);
        listMenu.add(menuItem);

        menuItem=new LeftMenuItem("",1,LeftMenuItem.VIEW_TYPE_DIVIDER,-1);
        listMenu.add(menuItem);

        menuItem=new LeftMenuItem("Account",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_user);
        listMenu.add(menuItem);

        menuItem=new LeftMenuItem("Setting",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_setting);
        listMenu.add(menuItem);

        menuItem=new LeftMenuItem("",1,LeftMenuItem.VIEW_TYPE_DIVIDER,-1);
        listMenu.add(menuItem);
        menuItem=new LeftMenuItem("Share",1,LeftMenuItem.VIEW_TYPE_MENU_ITEM,R.drawable.ic_share);
        listMenu.add(menuItem);
        mAdapter.notifyDataSetChanged();
       // mRecyclerView.addItemDecoration((new SimpleDividerItemDecoration(getActivity())),2);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (callBackListener != null) {
           // callBackListener.onLeftMenuItemSelected(uri);
            ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
/*        try {
            callBackListener = (OnLeftMenuItemSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLeftMenuItemSelected");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBackListener = null;
    }
    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }
    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLeftMenuItemSelected {
        // TODO: Update argument type and name
        public void onLeftMenuItemSelected(LeftMenuItem selectedItem);
    }

    class RecyclerTouchListenter implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        ClickListenter clickListenter;

        public RecyclerTouchListenter(Context context, final RecyclerView recyclerView, final ClickListenter clickListenter){
            this.clickListenter=clickListenter;

            Log.d("anhnt","RecyclerTouchListenter");
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListenter!=null){
                        clickListenter.onLongclick(child,recyclerView.getChildPosition(child));
                    }
                    super.onLongPress(e);
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View chiView=rv.findChildViewUnder(e.getX(),e.getY());

            if(chiView!=null && clickListenter!=null && gestureDetector.onTouchEvent(e)){
                clickListenter.onclick(chiView, rv.getChildPosition(chiView));

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    @Override
    public void onDestroy() {
        accessTokenTracker.stopTracking();
        super.onDestroy();
    }

    public static interface ClickListenter{
        public void onclick(View view,int possition );
        public void onLongclick(View view,int possition );
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
