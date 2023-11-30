package com.jnu.student.data;

import java.io.Serializable;

public class ShopItem implements Serializable {
    public ShopItem(String title, int resourceId) {
        this.title = title;
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    private String title;
    //private Double price;
    private int resourceId;
}
