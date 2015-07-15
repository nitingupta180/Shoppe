package com.example.shoppe.datamodel;

/**
 * Created by nitingup on 7/15/15.
 */
public class DrawerItem {

    private String mTitle;
    private String mFilePath;

    public DrawerItem(String title, String filePath) {
        mFilePath = filePath;
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }
}
