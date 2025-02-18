package com.tari9bro.wallpapers.fragments;



import static com.tari9bro.wallpapers.activity.MainActivity.fragmentManager;
import static com.tari9bro.wallpapers.ads.Ads.rewardedAd;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tari9bro.wallpapers.adapters.FileAdapter2;
import com.tari9bro.wallpapers.R;
import com.tari9bro.wallpapers.ads.Ads;
import com.tari9bro.wallpapers.helpers.PreferencesHelper;
import com.tari9bro.wallpapers.helpers.FileUtilsHelper;
import com.tari9bro.wallpapers.helpers.Settings;
import com.tari9bro.wallpapers.helpers.WallpaperSetter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ImageFragment extends Fragment {
    private static final String ARG_FILE_NAME = "fileName";
    private static final String ARG_BADGE_RES_ID = "badgeResId"; // Add this for badge image

    Ads ads;
    PreferencesHelper pref;
    TextView share,Download,setWall,badge;
    WallpaperSetter wallpaperSetter;
    Settings settings;
    private AtomicInteger synchronizedPosition = new AtomicInteger(0);
    public static ImageFragment newInstance(String fileName) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE_NAME, fileName);
        fragment.setArguments(args);
        return fragment;
    }

    // Add this method for badge image
    public static ImageFragment newInstanceWithBadge(String fileName, int badgeResId) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE_NAME, fileName);
        args.putInt(ARG_BADGE_RES_ID, badgeResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);


        wallpaperSetter = new WallpaperSetter(requireContext());





        settings = new Settings(requireActivity(),requireContext());
        pref = new PreferencesHelper(requireActivity());
        ads = new Ads(requireActivity(),requireContext());

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),1); // or GridLayoutManager if you are using a grid layout
        recyclerView.setLayoutManager(layoutManager);



        List<String> fileList = FileUtilsHelper.getFilesFromAssets(requireContext());
        FileAdapter2 fileAdapter = new FileAdapter2(requireContext(), fileList, requireActivity(),fragmentManager); // Adjust the placeholder dimensions as per your preference
       recyclerView.setAdapter(fileAdapter);


        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter( fileAdapter);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaInAnimationAdapter));

        layoutManager.scrollToPosition(pref.LoadInt("position"));


        // Add an OnScrollListener to the RecyclerView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Get the first visible item position
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Update the synchronized position
                synchronizedPosition.set(firstVisibleItemPosition);

                if (!containsDigitThree(synchronizedPosition.get())  ) {
                    badge.setVisibility(View.GONE);
                    Download.setVisibility(View.VISIBLE);
                    setWall.setVisibility(View.VISIBLE);
                    share.setVisibility(View.VISIBLE);
                } else {
                    badge.setVisibility(View.VISIBLE);
                    if (  !pref.LoadIntArray("unlockedList").contains(synchronizedPosition.get()) ){
                        Download.setVisibility(View.GONE);
                        setWall.setVisibility(View.GONE);
                        share.setVisibility(View.GONE);



                        // Assuming you're inside the fragment class
                        if (badge.getParent() != null) {
                            // Remove the view from its current parent
                            ((ViewGroup) badge.getParent()).removeView(badge);
                            badge.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT // You can set the height as needed
                            ));
                            badge.setGravity(Gravity.CENTER);

                            ViewGroup container = getView().findViewById(R.id.relativ);
                            container.addView(badge);

                        }







                    }else{
                        Download.setVisibility(View.VISIBLE);
                        setWall.setVisibility(View.VISIBLE);
                        share.setVisibility(View.GONE);
                    }

                }
            }
        });


        share = rootView.findViewById(R.id.share);
        badge = rootView.findViewById(R.id.badge);
        setWall = rootView.findViewById(R.id.setwall);
        Download = rootView.findViewById(R.id.Download);

        share.setOnClickListener(view -> settings.sharTheApp());
        badge.setOnClickListener(view -> {

            if ( containsDigitThree(synchronizedPosition.get()) && !pref.LoadIntArray("unlockedList").contains(synchronizedPosition.get())) {
                if (rewardedAd != null && !rewardedAd.isAdInvalidated()){
                    ShowDialog(fileList.get(synchronizedPosition.get()));
                }else{
                    Toast.makeText(requireContext(),"not ready, try later", Toast.LENGTH_SHORT).show();

                }

            }else {

                wallpaperSetter.showWallpaperDialog(fileList.get(synchronizedPosition.get()));

            }

        });
        setWall.setOnClickListener(view -> {
             wallpaperSetter.showWallpaperDialog(fileList.get( synchronizedPosition.get()));
        });
        Download.setOnClickListener(view -> settings.saveImage(fileList.get(synchronizedPosition.get())));








        // Add this block for badge image


        return rootView;
    }

    public static boolean containsDigitThree(int number) {
        String numberString = String.valueOf(number);
        return numberString.contains("3");
    }

    private void ShowDialog(String fileName) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireContext());
        dialog.setTitle(R.string.exit_dialog_title);
        dialog.setIcon(R.drawable.ic_exit);
        dialog.setMessage(R.string.rewarded_dialog);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, (dialogInterface, i) -> {

            if(pref.LoadBool("Rewarded")){
                pref.SaveBool("Rewarded",false);

                WallpaperSetter wallpaperSetter = new WallpaperSetter(requireContext());
                wallpaperSetter.showWallpaperDialog(fileName);

            }else{
                ads.playRewarded();
            }
        });

        dialog.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss()) ;
        dialog.show();

    }

}