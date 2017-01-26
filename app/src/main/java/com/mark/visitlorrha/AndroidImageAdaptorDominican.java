package com.mark.visitlorrha;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ResourceBundle;

/**
 * Created by carro on 12/12/2016.
 */
public class AndroidImageAdaptorDominican extends PagerAdapter{


    Context mContext;

    //testing stuff 2
//    private int resourceId1;
//    private int resourceId2;
//    private int resourceId3;
//
//    public void setResourceId1(int resourceId){
//        this.resourceId1 = resourceId;
//    }
//
//    public int getResourceId1(){
//        return resourceId1;
//    }
//
//    public void setResourceId2(int resourceId){
//        this.resourceId2 = resourceId;
//    }
//
//    public int getResourceId2(){
//        return resourceId2;
//    }
//
//    public void setResourceId3(int resourceId){
//        this.resourceId3 = resourceId;
//    }
//
//    public int getResourceId3(){
//        return resourceId3;
//    }

    // end of testing stuff 2



//    // testing stuff 1
//    private String filename;
//
//    public String getFilename(){
//        return filename;
//    }
//
//    public void setFilename(String fileName){
//        this.filename = fileName;
//    }
//
//
//
//
//
//    String filename1 = filename + "pic1";
//    String filename2 = filename + "pic2";
//    String filename3 = filename + "pic3";
//
//    int resourceId1 = mContext.getResources().getIdentifier(filename1, "drawable", mContext.getPackageName()); //"com.mark.sounddemoproject"
//    int resourceId2 = mContext.getResources().getIdentifier(filename2, "drawable", mContext.getPackageName()); //"com.mark.sounddemoproject"
//    int resourceId3 = mContext.getResources().getIdentifier(filename3, "drawable", mContext.getPackageName()); //"com.mark.sounddemoproject"
//    // end of testing stuff 1



    AndroidImageAdaptorDominican(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }




    private int[] sliderImagesId = new int[]{  // each resource is an integer

            //this.getResourceId1(), this.getResourceId2(), this.getResourceId3()
             //resourceId1, resourceId2, resourceId3,
            // 2130837665, 2130837666, 2130837667 // this works, they are the numbers for each of the dom pics 1 to 3
           R.drawable.dominicanabbeypic1, R.drawable.dominicanabbeypic2, R.drawable.dominicanabbeypic3,

    };

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) { // creating the image view and altering the pictures
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setAdjustViewBounds(true); // should this be in here?...made the image a bit narrorwer?, now works for horizontal images
        mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }



}
