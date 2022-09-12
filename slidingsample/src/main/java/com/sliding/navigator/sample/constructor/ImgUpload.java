package com.sliding.navigator.sample.constructor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImgUpload {
    @SerializedName("path")
    @Expose
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
