package com.example.shoppe.datamodel;

/**
 * Created by nitingup on 7/15/15.
 */
public class Items {

    private String mLabel;
    private String mImage;
    private String mWebUrl;

    public Items(String label, String image, String webUrl) {
        mLabel = label;
        mImage = image;
        mWebUrl = webUrl;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public void setWebUrl(String webUrl) {
        mWebUrl = webUrl;
    }
}
