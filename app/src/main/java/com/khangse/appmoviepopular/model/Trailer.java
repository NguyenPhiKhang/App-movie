package com.khangse.appmoviepopular.model;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("id")
    private String mVideoId;

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;

    @SerializedName("site")
    private String mSite;

    @SerializedName("size")
    private int mSize;

    @SerializedName("type")
    private String mType;

    public Trailer(String mVideoId, String mKey, String mName, String mSite, int mSize, String mType) {
        this.mVideoId = mVideoId;
        this.mKey = mKey;
        this.mName = mName;
        this.mSite = mSite;
        this.mSize = mSize;
        this.mType = mType;
    }

    public String getmVideoId() {
        return mVideoId;
    }

    public void setmVideoId(String mVideoId) {
        this.mVideoId = mVideoId;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSite() {
        return mSite;
    }

    public void setmSite(String mSite) {
        this.mSite = mSite;
    }

    public int getmSize() {
        return mSize;
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
