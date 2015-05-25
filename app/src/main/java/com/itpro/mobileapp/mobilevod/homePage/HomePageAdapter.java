package com.itpro.mobileapp.mobilevod.homePage;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.itpro.mobileapp.mobilevod.R;
import com.itpro.mobileapp.mobilevod.util.BitmapLruCache;
import com.itpro.mobileapp.mobilevod.util.VolleySingleton;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anhnt on 5/20/2015.
 */
public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    public static int VIEW_TYPE_SLIDER=0;
    public static int VIEW_TYPE_HEADER=1;
    public static int VIEW_TYPE_ROW =2;
    public static int VIEW_TYPE_FOOTER=3;
    SliderLayout mDemoSlider;
    List<Object> itemList;

    public HomePageAdapter(List<Object> itemList,RecyclerView recyclerView) {

        this.itemList = itemList;
        int numberColumn=(int)recyclerView.getContext().getResources().getDimension(R.dimen.home_number_column);
        Log.i("anhnt","numberColum="+numberColumn);
        final GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), numberColumn);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
             //   Log.i("anhnt","getItemViewType("+position+")!=VIEW_TYPE_ROW)="+getItemViewType(position)+" layoutManager.getSpanCount() ="+layoutManager.getSpanCount() );
                return ( getItemViewType(position)==VIEW_TYPE_ROW)? 1:layoutManager.getSpanCount() ;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view =null;

            if(viewType==VIEW_TYPE_HEADER){
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_header, parent, false); //Inflating the layout
            }else if(viewType== VIEW_TYPE_ROW){
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_row, parent, false);
            }else if(viewType==VIEW_TYPE_FOOTER){
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_footer, parent, false);
            }else if(viewType==VIEW_TYPE_SLIDER){
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_slide_show, parent, false);
                setUpSliderShow(view);
            }
            ViewHolder vhItem = new ViewHolder(view,viewType);

            return vhItem;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        if(mDemoSlider!=null){
          //  mDemoSlider.stopAutoCycle();
        }
        super.onViewDetachedFromWindow(holder);
    }

    public void setUpSliderShow(final View view){
        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            final TextSliderView textSliderView = new TextSliderView(view.getContext());
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
                  /*  .setOnSliderClickListener(this);*/

           // Log.i("anhnt2",textSliderView.getView().findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image).toString());
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Tablet);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(5000);
        mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }



    @Override
    public int getItemViewType(int position) {
        if(itemList.get(position) instanceof Category){
            return VIEW_TYPE_HEADER;
        }else if(itemList.get(position) instanceof CategoryFooter){
            return VIEW_TYPE_FOOTER;
        }else if(itemList.get(position) instanceof String){
            return VIEW_TYPE_SLIDER;
        }
        else
            return VIEW_TYPE_ROW;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int possition) {
        if(viewHolder.viewType==VIEW_TYPE_HEADER){
            TextView txtCatTitle=(TextView)viewHolder.view.findViewById(R.id.txtCatTitle);
            Category cat=(Category)itemList.get(possition);
            txtCatTitle.setText(cat.title+" >");
        }else if(viewHolder.viewType==VIEW_TYPE_ROW){


            VideoItem videoItem=(VideoItem)itemList.get(possition);


            TextView txtVideoItemName=(TextView)viewHolder.view.findViewById(R.id.txtVideoItemName);
            txtVideoItemName.setText(videoItem.Title);
            TextView txtNumberView=(TextView)viewHolder.view.findViewById(R.id.txtNumberView);
            txtNumberView.setText("views:"+videoItem.numberView);


            NetworkImageView imageView = (NetworkImageView) viewHolder.view.findViewById(R.id.imgVideoSmall);
/*
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(viewHolder.view.getContext()), imageCache);
*/

            ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
            imageView.setImageUrl(videoItem.imgUrl, mImageLoader);
           // imageView.setDefaultImageResId(R.drawable.unknowavata);
            //imageView.setErrorImageResId(..);
        }else if(viewHolder.viewType==VIEW_TYPE_SLIDER){

        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        int viewType;
        View view;
        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            this.view=itemView;
            this.viewType=viewType;

        }
    }
}
