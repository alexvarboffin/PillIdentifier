package com.github.pdfviewer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.io.Serializable;

public class PDFConfig implements Parcelable{

    public static final String EXTRA_CONFIG = "PDFConfig";

    private String filepath;
    private int swipeorientation;
//    private String network_url;

    public PDFConfig() {

    }

    protected PDFConfig(Parcel in) {
        this.filepath = in.readString();
        this.swipeorientation = in.readInt();
//        this.network_url = in.readString();
    }


//    public String getNetwork_url() {
//        return network_url;
//    }
//
//    public void setNetwork_url(String network_url) {
//        this.network_url = network_url;
//    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filepath);
        dest.writeInt(swipeorientation);
    }

    public static final Creator<PDFConfig> CREATOR = new Creator<PDFConfig>() {
        @Override
        public PDFConfig createFromParcel(Parcel in) {
            return new PDFConfig(in);
        }

        @Override
        public PDFConfig[] newArray(int size) {
            return new PDFConfig[size];
        }
    };

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getSwipeorientation() {
        return swipeorientation;
    }

    public void setSwipeorientation(int swipeorientation) {
        this.swipeorientation = swipeorientation;
    }

    @Override
    public int describeContents() {
        return 0;
    }


//    @Override
//    public void readFromParcel(Parcel in) {
//        this.filepath = in.readString();
//        this.swipeorientation = in.readInt();
//    }
}