package com.example.gameNews;

public class uploadModel {
    private String mTitle;
    private String mImageUri;
    private String mComments;

    public uploadModel(){

    }

    public uploadModel(String mTitle, String mImageUri,String comments) {

        if(mTitle.trim().equals("")){
            mTitle = "no Title";
        }

        this.mTitle = mTitle;
        this.mImageUri = mImageUri;
        this.mComments = comments;
    }

    public String getmTitle() { return mTitle; }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getmComments() { return mComments; }

    public void setmComments(String mComments) { this.mComments = mComments; }
}
