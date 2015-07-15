package com.example.shoppe.datamodel;

import java.util.ArrayList;

/**
 * Created by nitingup on 7/15/15.
 */
public class ProductTemplate {

    private String mLabel;
    private String mImage;
    private String mTemplate;
    private ArrayList<Items> mItemList;

    public ProductTemplate(String label, String image, String template, ArrayList<Items> itemList) {
        mLabel = label;
        mImage = image;
        mTemplate = template;
        mItemList = itemList;
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

    public String getTemplate() {
        return mTemplate;
    }

    public void setTemplate(String template) {
        mTemplate = template;
    }

    public ArrayList<Items> getItemList() {
        return mItemList;
    }

    public void setItemList(ArrayList<Items>itemList) {
        mItemList = itemList;
    }
}
