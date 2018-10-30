package com.example.android.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    private int stepID;
    private String shortDescription;
    private String fullDescription;
    private String videoUrl;
    private String thumbnailUrl;

    public Step(int stepID, String shortDescription, String fullDescription, String videoUrl, String thumbnailUrl) {
        this.stepID = stepID;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected Step(Parcel in) {
        stepID = in.readInt();
        shortDescription = in.readString();
        fullDescription = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getStepID() {
        return stepID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepID);
        dest.writeString(shortDescription);
        dest.writeString(fullDescription);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }
}
