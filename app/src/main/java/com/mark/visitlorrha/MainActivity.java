package com.mark.visitlorrha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {


    private ImageView dominicanAbbeyMainMenu;
    private ImageView augustinianAbbeyMainMenu;
    private ImageView stRuadhansAbbeyMainMenu;
    private ImageView lackeenCastleMainMenu;
    private ImageView redwoodCastleMainMenu;

    // obtaining images from Firebase
    public static final String URL_STORAGE_REFERENCE = "gs://visitlorrha.appspot.com/";
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(URL_STORAGE_REFERENCE);

        StorageReference dominicanAbbeyPic = storageRef.child("images/dominicanabbeypic1.jpg"); // storage referce to an image
        StorageReference augustinianAbbeyPic = storageRef.child("images/augustinianabbeypic1.jpg"); // storage referce to an image
        StorageReference stRuadhansAbbeyPic = storageRef.child("images/struadhansabbeypic1.jpg"); // storage referce to an image
        StorageReference lackeenCastlePic = storageRef.child("images/lackeencastlepic1.jpg"); // storage referce to an image
        StorageReference redwoodCastlePic = storageRef.child("images/redwoodcastlepic1.jpg"); // storage referce to an image


        dominicanAbbeyMainMenu = (ImageView) findViewById(R.id.imageViewDominicanAbbeyMainMenu);
        augustinianAbbeyMainMenu = (ImageView) findViewById(R.id.imageViewAugustinianAbbeyMainMenu);
        stRuadhansAbbeyMainMenu = (ImageView) findViewById(R.id.imageViewStRuadhansAbbeyAbbeyMainMenu);
        lackeenCastleMainMenu = (ImageView) findViewById(R.id.imageViewLackeenCastleMainMenu);
        redwoodCastleMainMenu = (ImageView) findViewById(R.id.RedwoodCastleMainMenu);

        // rendering images from Firebase using Glide
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(dominicanAbbeyPic)
                .fitCenter()
                .into(dominicanAbbeyMainMenu);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(augustinianAbbeyPic)
                .fitCenter()
                .into(augustinianAbbeyMainMenu);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(stRuadhansAbbeyPic)
                .fitCenter()
                .into(stRuadhansAbbeyMainMenu);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(lackeenCastlePic)
                .fitCenter()
                .into(lackeenCastleMainMenu);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(redwoodCastlePic)
                .fitCenter()
                .into(redwoodCastleMainMenu);



        dominicanAbbeyMainMenuOnClick(); // method to launch dominican abbey activity
        augustinianAbbeyMainMenuOnClick(); // method to launch augustinian abbey activity
        stRuadhansAbbeyMainMenuOnClick(); // method to launch StRuadhans abbey activity
        lackeenCastleMainMenuOnClick();  // method to launch Lackeen Castle activity
        redwoodCastleMainMenuOnClick();  // method to launch Redwood Castle activity

    }


    // launches Dominican Abbey activity
    private void dominicanAbbeyMainMenuOnClick() {
        dominicanAbbeyMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startDominicanAbbey = new Intent(getApplicationContext(), DominicanAbbey.class);
                startActivity(startDominicanAbbey);
            }
        });
    }

    // launches Augustinian Abbey activity
    private void augustinianAbbeyMainMenuOnClick(){
        augustinianAbbeyMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAugustinianAbbey = new Intent(getApplicationContext(), AugustinianAbbey.class);
                startActivity(startAugustinianAbbey);
            }
        });
    }

    // launches St Ruadhan's Abbey activity
    private void stRuadhansAbbeyMainMenuOnClick() {
        stRuadhansAbbeyMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startStRuadhansAbbey = new Intent(getApplicationContext(), StRuadhansAbbey.class);
                startActivity(startStRuadhansAbbey);
            }
        });
    }



    // launches Lackeen Castle activity
    private void lackeenCastleMainMenuOnClick(){
        lackeenCastleMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLackeenCastle = new Intent(getApplicationContext(), LackeenCastle.class);
                startActivity(startLackeenCastle);
            }
        });
    }


    // launches Redwood Castle activity
    private void redwoodCastleMainMenuOnClick(){
        redwoodCastleMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startRedwoodCastle = new Intent(getApplicationContext(), RedwoodCastle.class);
                startActivity(startRedwoodCastle);
            }
        });

    }


}
