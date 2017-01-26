package com.mark.visitlorrha;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.panorama.Panorama;
import com.google.android.gms.panorama.PanoramaApi;



public class Photosphere extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    public static final String TAG = Photosphere.class.getSimpleName();

    private GoogleApiClient mClient;

    String filename;

    int resourceId;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mClient = new GoogleApiClient.Builder(this, this, this)
                .addApi(Panorama.API)
                .build();

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if(b != null){
            filename = (String) b.get("filename"); // domincanabbey, yes!!
        }

        // obtaining the panorama resource ID  from the string 'filename'
        filename = "pano" + filename;  // panodomincanabbey, now to match it up with the panorama file
        resourceId = getResources().getIdentifier(filename, "raw", getPackageName()); //R.raw.panodominicanabbey

        mClient.connect(); // so if returned to this activity, it wont start up again using OnStart

    }

    @Override
    public void onStart() {
        super.onStart();
        finish(); // so when you press back in the viewerIntent, it skips past this activity and returns to the attraction's activity
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);  //R.raw.panodominicanabbey
        Panorama.PanoramaApi.loadPanoramaInfo(mClient, uri).setResultCallback(
                new ResultCallback<PanoramaApi.PanoramaResult>() {
                    @Override
                    public void onResult(PanoramaApi.PanoramaResult result) {
                        if (result.getStatus().isSuccess()) {
                            Intent viewerIntent = result.getViewerIntent();
                            Log.i(TAG, "found viewerIntent: " + viewerIntent);
                            if (viewerIntent != null) {
                                startActivity(viewerIntent);
                            }
                        } else {
                            Log.e(TAG, "error: " + result);
                        }
                    }
                });
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "connection suspended: " + cause);
    }

    @Override
    public void onConnectionFailed(ConnectionResult status) {
        Log.e(TAG, "connection failed: " + status);
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // to show up/back button in the action bar
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
