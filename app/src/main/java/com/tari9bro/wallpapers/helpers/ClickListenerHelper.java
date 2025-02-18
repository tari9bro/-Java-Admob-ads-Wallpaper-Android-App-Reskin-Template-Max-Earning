package com.tari9bro.wallpapers.helpers;
//com.tari9bro.wallpapers

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.tari9bro.wallpapers.R;

public class ClickListenerHelper implements View.OnClickListener {

    private final Activity activity;
    private final Settings settings;
      private final PreferencesHelper prefs ;

    public ClickListenerHelper(Activity activity, Context context) {
        this.activity = activity;
        settings = new Settings(activity,context);
        prefs = new PreferencesHelper(activity);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        // Handle onClick event for all TextViews here
        if(view.getId()== R.id.share){
            settings.sharTheApp();
            showConstraintLayout();
        }
        if(view.getId()==R.id.apps){
            settings.moreApps();
            showConstraintLayout();
        }
        if(view.getId()==R.id.exit){
            settings.exitTheApp();
            showConstraintLayout();
        }

        if(view.getId()==R.id.floatingActionButton){
            showConstraintLayout();


        }
        if(view.getId()==R.id.rating){
            settings.moreApps();
            showConstraintLayout();
        }
        if(view.getId()==R.id.Feedback){
            settings.moreApps();
            showConstraintLayout();
        }
    }

    private void showConstraintLayout() {
        if (!prefs.loadConstraintLayout()) {
            // Show ConstraintLayout
            activity.findViewById(R.id.fabLayout).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.floatingActionButton).animate().rotation(45f);
            prefs.saveConstraintLayout(true);
        } else {
            // Hide ConstraintLayout
           // inter.playInterstitialAd();
            activity.findViewById(R.id.fabLayout).setVisibility(View.GONE);
            activity.findViewById(R.id.floatingActionButton).animate().rotation(0f);
            prefs.saveConstraintLayout(false);
        }
    }


}
