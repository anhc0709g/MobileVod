package com.itpro.mobileapp.mobilevod.leftMenu;

/**
 * Created by anhnt on 5/10/2015.
 */
public class LeftMenuItem {
    public static int REF_LOGIN =-1;
    public static int VIEW_TYPE_MENU_ITEM =1;
    public static int VIEW_TYPE_DIVIDER =2;
    public String title;
    public int iconResourceId;
    public int viewType;
    public int ref;

    public LeftMenuItem(String title, int ref, int viewType, int iconResourceId) {
        this.title = title;
        this.ref = ref;
        this.viewType = viewType;
        this.iconResourceId = iconResourceId;
    }
}

