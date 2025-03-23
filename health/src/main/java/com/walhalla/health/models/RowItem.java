package com.walhalla.health.models;


public class RowItem {

    public final int description;
    private final int imageId;

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    private int theme;

    public int getIcon() {
        return icon;
    }

    private final int icon;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int color;
    public int title;

    public RowItem(int i, int str, int str2, int theme, int icon, int color) {
        this.imageId = i;
        this.title = str;
        this.description = str2;
        this.theme = theme;
        this.icon = icon;
        this.color = color;
    }

    public int getImageId() {
        return this.imageId;
    }


}
