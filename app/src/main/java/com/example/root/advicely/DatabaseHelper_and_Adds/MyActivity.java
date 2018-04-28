package com.example.root.advicely.DatabaseHelper_and_Adds;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.root.advicely.R;
import com.example.root.advicely.utils.AppVariable;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static com.google.android.gms.internal.zzahn.runOnUiThread;

/**
 * Created by root on 1/14/18.
 */

public class MyActivity extends AppCompatActivity {

    public InterstitialAd mInterstitialAd;

    public void loadAdvertisement() {
        mInterstitialAd = newInterstitialAd();

        loadInterstitial();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getAdId());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private String getAdId() {
        if(AppVariable.addCount == 1) {
            return getString(R.string.interstitial_ad_unit_id_d);
        } else {
            return getString(R.string.interstitial_ad_unit_id);
        }
    }

    public void showMyAdd() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mInterstitialAd.isLoaded()) {
                            Toast.makeText(getApplicationContext(), "loaded " + AppVariable.addCount, Toast.LENGTH_SHORT).show();
                            if (AppVariable.addCount == 0)
                                AppVariable.addCount = 1;
                            else
                                AppVariable.addCount = 0;
                            mInterstitialAd.show();
                        } else
                            Toast.makeText(getApplicationContext(), "not loaded", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).start();
    }

}
