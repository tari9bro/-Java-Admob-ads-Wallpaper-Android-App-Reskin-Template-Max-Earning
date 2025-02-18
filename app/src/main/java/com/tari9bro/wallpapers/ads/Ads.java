package com.tari9bro.wallpapers.ads;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.tari9bro.wallpapers.R;
import com.tari9bro.wallpapers.helpers.PreferencesHelper;

public class Ads  {




    PreferencesHelper pref;
    public Ads(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        pref = new PreferencesHelper(activity);

    }




    public AdView adView;



    public static RewardedVideoAd rewardedAd;


    public  InterstitialAd interstitialAd;

    private final Activity activity;
    private final Context context;
    public static int positionInt;
    public AdView getAdView() {
        return adView;
    }
    public InterstitialAd getInterstitialAd() {
        return interstitialAd;
    }
    public void loadBanner() {
        // Create an ad request.
// Instantiate an AdView object.
// NOTE: The placement ID from the Facebook Monetization Manager identifies your App.
// To get test ads, add IMG_16_9_APP_INSTALL# to your placement id. Remove this when your app is ready to serve real ads.

        adView = new AdView(context, activity.getString(R.string.bannerAd), AdSize.BANNER_HEIGHT_50);
// Find the Ad Container
        LinearLayout adContainer = (LinearLayout) activity.findViewById(R.id.banner_container);
// Add the ad view to your activity layout
        adContainer.addView(adView);
// Request an ad
        adView.loadAd();

    }
    public  void LoadInterstitialAd( ) {
        // Instantiate an InterstitialAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        interstitialAd = new InterstitialAd(context, activity.getString(R.string.bannerAd));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

    }
    public void loadRewarded() {
        rewardedAd = new RewardedVideoAd(context, activity.getString(R.string.bannerAd));
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");
                pref.saveIntArray(positionInt,"unlockedList");
                pref.SaveBool("Rewarded",true);
                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedAd.loadAd(
                rewardedAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }
    public  void playInterstitialAd() {
        if(interstitialAd  != null && !interstitialAd.isAdInvalidated()) {
            interstitialAd.show();
        }else {
            Toast.makeText(context,"ad is not ready yet!", Toast.LENGTH_LONG).show();
        }


    }



    public void playRewarded(){
        if (rewardedAd != null && !rewardedAd.isAdInvalidated()) {
            rewardedAd.show();
        }else {
            Toast.makeText(context,"ad is not ready yet!", Toast.LENGTH_LONG).show();

        }
    }







}
