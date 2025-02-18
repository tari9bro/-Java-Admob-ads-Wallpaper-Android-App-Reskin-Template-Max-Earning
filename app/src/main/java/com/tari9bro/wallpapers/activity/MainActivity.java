package com.tari9bro.wallpapers.activity;


import static com.facebook.ads.AdSettings.IntegrationErrorMode.INTEGRATION_ERROR_CRASH_DEBUG_MODE;
import static com.tari9bro.wallpapers.ads.Ads.rewardedAd;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.tari9bro.wallpapers.R;
import com.tari9bro.wallpapers.ads.Ads;
import com.tari9bro.wallpapers.fragments.RecyclerFragment;
import com.tari9bro.wallpapers.helpers.ClickListenerHelper;
import com.tari9bro.wallpapers.helpers.PreferencesHelper;
import com.tari9bro.wallpapers.helpers.Settings;
import com.tari9bro.wallpapers.notification.NotificationHelper;

public class MainActivity extends AppCompatActivity  {
    Ads ads;
    PreferencesHelper prefs;
    Settings settings;
    ClickListenerHelper clickListenerHelper;

    public static FragmentManager fragmentManager;

    NotificationHelper notificationHelper;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RecyclerFragment())
                    .commit();
        }
        settings = new Settings(MainActivity.this, this);
        prefs = new PreferencesHelper(this);

        if (!prefs.LoadBool("Permission")) {
            settings.showPermissionDialog();        }

        AudienceNetworkAds.initialize(this);
        AdSettings.setIntegrationErrorMode(INTEGRATION_ERROR_CRASH_DEBUG_MODE);
        AdSettings.addTestDevice("65f07fc3-1f8c-4a08-815a-49bc93c63a54");


        clickListenerHelper = new ClickListenerHelper(MainActivity.this, this);

        findViewById(R.id.share).setOnClickListener(clickListenerHelper);
        findViewById(R.id.apps).setOnClickListener(clickListenerHelper);
        findViewById(R.id.exit).setOnClickListener(clickListenerHelper);
        findViewById(R.id.floatingActionButton).setOnClickListener(clickListenerHelper);
        findViewById(R.id.rating).setOnClickListener(clickListenerHelper);
        findViewById(R.id.Feedback).setOnClickListener(clickListenerHelper);
        notificationHelper = new NotificationHelper(this);
        NotificationHelper.createNotificationChannel(this);
        notificationHelper.startPeriodicTasks();

        ads = new Ads(MainActivity.this,this);

         ads.loadBanner();
         ads.loadRewarded();
         ads.LoadInterstitialAd();

        this.settings.noInternetDialog(getLifecycle());

    }


    @Override
    protected void onDestroy() {
        if (ads.getAdView() != null) {
            ads.getAdView() .destroy();
        }
        if (ads.getInterstitialAd() != null) {
            ads.getInterstitialAd().destroy();
        }
        if (rewardedAd != null) {
            rewardedAd.destroy();
            rewardedAd = null;
        }
        super.onDestroy();
    }
}
