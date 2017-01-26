package com.mark.visitlorrha;


public class Review {
    private String title, desc; // these must match the variables used in Realtime database,
    private float rating;

    public Review(){

    }

    public Review(String title, String desc, float rating) {
        this.title = title;
        this.desc = desc;
        this.rating = rating;
    }

    public float getRating(){
        return rating;
    }

    public void setRating(float rating){
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
