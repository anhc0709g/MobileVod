package com.itpro.mobileapp.mobilevod.leftMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itpro.mobileapp.mobilevod.R;

import java.util.List;



/**
 * Created by anhnt on 5/9/2015.
 */
public class LeftMenuAdapter extends RecyclerView.Adapter<LeftMenuAdapter.ViewHolder> {


    List<LeftMenuItem> data;






    LeftMenuAdapter( List<LeftMenuItem> data){ // LeftMenuAdapter Constructor with titles and icons parameter
       this.data=data;

    }



    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the left_menu_row_item.xmlw_item.xml layout if the viewType is Type_ITEM or else we inflate left_menu_row_header_profileenu_row_header_profile.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    @Override
    public LeftMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == LeftMenuItem.VIEW_TYPE_MENU_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_menu_row_item,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object


        }else if (viewType == LeftMenuItem.VIEW_TYPE_DIVIDER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_divider,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object
        }
        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(LeftMenuAdapter.ViewHolder holder, int position) {
        if(holder.viewType ==LeftMenuItem.VIEW_TYPE_MENU_ITEM) {
            LeftMenuItem menuItem=data.get(position);
            holder.txtTitle.setText(menuItem.title); // Setting the Text with the array of our Titles
            holder.imgIcon.setImageResource(menuItem.iconResourceId);// Settimg the image with array of our icons
        }

    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return data.size();
    }


    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        LeftMenuItem menuItem=data.get(position);
        return menuItem.viewType;
    }



    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int viewType;

        TextView txtTitle;
        ImageView imgIcon;


        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created
            if(ViewType == LeftMenuItem.VIEW_TYPE_MENU_ITEM) {
                txtTitle = (TextView) itemView.findViewById(R.id.txtTitle); // Creating TextView object with the id of textView from left_menu_row_item.xmlw_item.xml
                imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);// Creating ImageView object with the id of ImageView from left_menu_row_itemu_row_item.xml
                viewType = LeftMenuItem.VIEW_TYPE_MENU_ITEM;                                               // setting holder id as 1 as the object being populated are of type item row
            }

        }


    }

}
