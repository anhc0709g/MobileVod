package com.itpro.mobileapp.mobilevod.homePage;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itpro.mobileapp.mobilevod.BaseFragment;
import com.itpro.mobileapp.mobilevod.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {

    RecyclerView rcHome;
    List<Object> itemList;
    HomePageAdapter homePageAdapter;
    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    //    Log.i("anhnt","HomePageFragment-onCreateView");
        View rootView=inflater.inflate(R.layout.home_page_fragment, container, false);
        rcHome=(RecyclerView)rootView.findViewById(R.id.rcHome);

        rcHome.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
/*
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        rcHome.setLayoutManager(manager);*/

        itemList=new ArrayList<>();
        homePageAdapter = new HomePageAdapter(itemList,rcHome);



        rcHome.setAdapter(homePageAdapter);

        loadTestData();

        return rootView;



    }

    private void loadTestData(){

        List<Category> categoryList=new ArrayList<>();
        Category category=new Category();
        category.title="Phim hot";
        category.listItem=new ArrayList<>();

        VideoItem videoItem=new VideoItem();
        videoItem.Title="Hồn ma thức tỉnh ";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/18_05_2015/tle18-05-2015_14g08-06.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Long môn phi giáp";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/download/11-04-2015/58931726f4_11-04-2015_11g50-28.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Cuộc chiến ngai vàng (Phần 5)";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/download/13-04-2015/game-of-thrones-season-premier_13-04-2015_08g10-08.jpg?w=190&ampp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Người Máy nổi dậy - Ex Machina";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/15_05_2015/em15-05-2015_09g46-18.jpg?w=190&amp";
        category.listItem.add(videoItem);

        categoryList.add(category);
        category=new Category();
        category.title="Phim hanh dong";
        category.listItem=new ArrayList<>();
        videoItem=new VideoItem();
        videoItem.Title="Hồn ma thức tỉnh ";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/18_05_2015/tle18-05-2015_14g08-06.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Long môn phi giáp";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/download/11-04-2015/58931726f4_11-04-2015_11g50-28.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Cuộc chiến ngai vàng (Phần 5)";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/download/13-04-2015/game-of-thrones-season-premier_13-04-2015_08g10-08.jpg?w=190&ampp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Người Máy nổi dậy - Ex Machina";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/15_05_2015/em15-05-2015_09g46-18.jpg?w=190&amp";
        category.listItem.add(videoItem);

        categoryList.add(category);

        category=new Category();
        category.listItem=new ArrayList<>();
        category.title="Phim Tam ly";
        category.listItem=new ArrayList<>();
        videoItem=new VideoItem();
        videoItem.Title="Hồn ma thức tỉnh ";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/18_05_2015/tle18-05-2015_14g08-06.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Long môn phi giáp";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/download/11-04-2015/58931726f4_11-04-2015_11g50-28.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Cuộc chiến ngai vàng (Phần 5)";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/download/13-04-2015/game-of-thrones-season-premier_13-04-2015_08g10-08.jpg?w=190&ampp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Người Máy nổi dậy - Ex Machina";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/15_05_2015/em15-05-2015_09g46-18.jpg?w=190&amp";
        category.listItem.add(videoItem);
        categoryList.add(category);



        category=new Category();
        category.listItem=new ArrayList<>();
        category.title="Phim Hai";
        category.listItem=new ArrayList<>();
        videoItem=new VideoItem();
        videoItem.Title="Hồn ma thức tỉnh ";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/18_05_2015/tle18-05-2015_14g08-06.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Long môn phi giáp";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/download/11-04-2015/58931726f4_11-04-2015_11g50-28.jpg?w=190&amp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Cuộc chiến ngai vàng (Phần 5)";
        videoItem.imgUrl="http://static.fptplay.net.vn/static/img/share/video/download/13-04-2015/game-of-thrones-season-premier_13-04-2015_08g10-08.jpg?w=190&ampp";
        category.listItem.add(videoItem);
        videoItem=new VideoItem();
        videoItem.Title="Người Máy nổi dậy - Ex Machina";
        videoItem.imgUrl="http://static1.fptplay.net.vn/static/img/share/video/15_05_2015/em15-05-2015_09g46-18.jpg?w=190&amp";
        category.listItem.add(videoItem);
        categoryList.add(category);


        // add data
        itemList.clear();
        itemList.add("the fist is Slidershow");
        for(int i=0;i<categoryList.size();i++){
         /*   CategoryHeader header=new CategoryHeader();
            Category cat=categoryList.get(i);
            header.title=cat.title;
            header.ref=cat.ref;
            itemList.add(header);*/
            Category cat=categoryList.get(i);
            itemList.add(cat);
            itemList.addAll(cat.listItem);
            CategoryFooter footer=new CategoryFooter();
            footer.ref=cat;
            itemList.add(footer);

        }

        homePageAdapter.notifyDataSetChanged();


    }



}
