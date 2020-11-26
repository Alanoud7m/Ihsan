package com.example.ihsan;

public class cart {
    String item_id;
    String itemDesc;
    String itemSize;
    String itemChName;
    String needCount;
    private String item_img;


    public cart() {
    }

    public cart(String item_id, String itemDesc, String itemChName, String needCount,String itemSize, String item_img) {
        this.item_id = item_id;
        this.itemDesc = itemDesc;
        this.itemChName = itemChName;
        this.needCount = needCount;
        this.itemSize = itemSize;
        this.setItem_img(item_img);
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemChName() {
        return itemChName;
    }

    public void setItemChName(String itemChName) {
        this.itemChName = itemChName;
    }

    public String getNeedCount() {
        return needCount;
    }

    public void setNeedCount(String needCount) {
        this.needCount = needCount;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }
}
