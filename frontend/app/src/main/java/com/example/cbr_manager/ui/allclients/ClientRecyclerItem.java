package com.example.cbr_manager.ui.allclients;

public class ClientRecyclerItem {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public ClientRecyclerItem(int mImageResource, String mText1, String mText2) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
