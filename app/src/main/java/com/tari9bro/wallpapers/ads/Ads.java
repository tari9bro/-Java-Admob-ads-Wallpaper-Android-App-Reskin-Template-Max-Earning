package com.tari9bro.wallpapers.ads;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.tari9bro.wallpapers.R;
import com.tari9bro.wallpapers.helpers.PreferencesHelper;

public class Ads  {

    public AdView adView;
    public static RewardedAd rewardedAd;
    public InterstitialAd interstitialAd;
    private final Activity activity;
    private final Context context;
    public static int positionInt;

    public AdView getAdView() {
        return adView;
    }
    public InterstitialAd  getInterstitialAd() {
        return interstitialAd;
    }

    PreferencesHelper pref;
    public Ads(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        pref = new PreferencesHelper(activity);
    }

    public void loadBanner() {
        LinearLayout adContainerView = (LinearLayout) activity.findViewById(R.id.banner_container);
        adView = new AdView(context);
        adView.setAdUnitId(activity.getString(R.string.bannerAd));
        adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, 360));
        adContainerView.removeAllViews();
        adContainerView.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    public  void LoadInterstitialAd( ) {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context,activity.getString(R.string.Interstitial_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd mInterstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitialAd = mInterstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        interstitialAd = null;
                    }
                });

    }
    public  void playInterstitialAd() {
        if (interstitialAd != null) {
            interstitialAd.show(activity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    public void loadRewarded() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, activity.getString(R.string.Video_id),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        rewardedAd = null;
                    }
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
    }
    public void playRewarded(){
        if (Ads.rewardedAd != null) {
                Ads.rewardedAd.show(activity, (OnUserEarnedRewardListener) rewardItem -> {
                // Handle the reward.
                Log.d(TAG, "The user earned the reward.");
                int rewardAmount = rewardItem.getAmount();
                String rewardType = rewardItem.getType();
                Log.d(TAG, "Rewarded video completed!");
                pref.saveIntArray(positionInt,"unlockedList");
                pref.SaveBool("Rewarded",true);
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }







}
