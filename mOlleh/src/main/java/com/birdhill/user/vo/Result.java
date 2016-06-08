package com.birdhill.user.vo;

/**
 * Created by lg on 2016-06-01.
 */
public class Result {

    boolean duplicated;
    String availableId;

    public String getAvailableId() {
        return availableId;
    }

    public void setAvailableId(String availableId) {
        this.availableId = availableId;
    }

    public boolean isDuplicated() {
        return duplicated;
    }

    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }


}
