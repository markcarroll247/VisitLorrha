package com.mark.visitlorrha;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class AndroidImageAdaptorStRuadhansAbbey extends PagerAdapter {


    Context mContext;

    AndroidImageAdaptorStRuadhansAbbey(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }




    private int[] sliderImagesId = new int[]{   // each resource is an integer
            R.drawable.struadhansabbeypic1, R.drawable.struadhansabbeypic2, R.drawable.struadhansabbeypic3,

    };

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {  // creating the image view and altering the pictures
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setAdjustViewBounds(true);
        mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }
}
