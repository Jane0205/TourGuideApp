package com.example.android.tourguideapp;

/**
 * Created by 재은 on 2017-02-23.
 */

public class Detail {

    private String dname;
    private String dlocation;
    private int dnum;

    private int mAudioResourceId;
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Detail(String name,String location,int imageResourceId,int num,int audioResourceId){
        dname = name;
        dlocation = location;
        dnum = num;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public Detail(String name, String location,int imageResourceId,int audioResourceId){
        dname = name;
        dlocation = location;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getdname(){return dname;}

    public String getdlocation(){return dlocation;}

    public int getImageResourceId(){return mImageResourceId;}
    public boolean hasImage(){return mImageResourceId!=NO_IMAGE_PROVIDED;}
    public int getdnum(){return dnum;}
    public int getAudioResourceId(){return mAudioResourceId;}

    @Override
    public String toString() {
        return "Detail{" +
                "dname='" + dname + '\'' +
                ", dlocation='" + dlocation + '\'' +
                ", dnum=" + dnum +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }
}
