# Wallpaper Android App — Java & AdMob Reskin Template

A Java-based Android wallpaper application template intended to be customized and rebranded. It includes a wallpaper browser, wallpaper-setting actions, Google Mobile Ads integration, and periodic notification scheduling.

> **Before publishing:** customize the app identity, verify you have permission to use every image and design asset, replace the current AdMob configuration with your own approved IDs, and review the app's storage permissions and Google Play policy requirements.

## Features

- Browse wallpaper images bundled in the app's assets.
- Apply a selected wallpaper to the home screen, lock screen, or both.
- Google Mobile Ads integration for banner, interstitial, and rewarded ads.
- Rewarded-ad flow for unlocking selected wallpapers.
- Periodic notification tasks.
- Android resource files for customizing the app name, icons, colors, and layouts.

## Tech stack

- Java
- Android Gradle Plugin (version managed in `gradle/libs.versions.toml`)
- Gradle Wrapper
- Google Mobile Ads SDK
- AndroidX and Material Components

## Requirements

- Android Studio with Android SDK Platform 35 installed.
- JDK 17.
- Android device or emulator running Android 7.0 (API 24) or newer.

The project currently sets `minSdk 24`, `compileSdk 35`, `targetSdk 35`, and app version `1.0` (`versionCode 1`). Re-check these values and all store requirements before distributing a build.

## Get the source

```bash
git clone https://github.com/tari9bro/-Java-Admob-ads-Wallpaper-Android-App-Reskin-Template-Max-Earning.git
cd -Java-Admob-ads-Wallpaper-Android-App-Reskin-Template-Max-Earning
```

You can also open the cloned project folder directly in Android Studio and allow Gradle sync to complete.

## Build

On macOS/Linux:

```bash
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

If the build succeeds, the debug APK is normally written to:

`app/build/outputs/apk/debug/app-debug.apk`

To run local unit tests:

```bash
./gradlew test
```

For a release build, use `./gradlew assembleRelease`. Configure signing securely before distributing a release APK; do not commit keystores, passwords, or signing credentials to the repository. These build commands are standard Gradle tasks; a successful build has not been verified as part of this README update.

## Reskin and configuration

1. **App name:** edit `app/src/main/res/values/strings.xml`, especially `app_name`.
2. **Application ID and version:** edit `app/build.gradle` (`applicationId`, `versionCode`, and `versionName`). Changing the application ID creates a different Android app identity.
3. **Wallpaper images:** add or replace appropriately licensed images in `app/src/main/assets`. Keep filenames and references in sync with the Java code.
4. **AdMob:** update `app_id`, `bannerAd`, `Interstitial_id`, and `Video_id` in `app/src/main/res/values/strings.xml` to use your own AdMob app and ad-unit IDs. Use Google's test ad units while developing and testing; never click your own live ads.
5. **Branding:** replace launcher icons and the Play Store icon under `app/src/main/res` and `app/src/main/ic_launcher-playstore.png`. Update colors, themes, and layouts under `app/src/main/res/values` and `app/src/main/res/layout`.
6. **Store links:** review `developer_search`, `app_link`, and `developer_id` in `strings.xml` and replace them with links for your own published app/developer account.
7. **Permissions and privacy:** review `app/src/main/AndroidManifest.xml`, especially broad storage permissions, network access, advertising ID, and notification permission. Request only permissions needed by your final app and provide privacy disclosures appropriate to the features and SDKs you use.

## Release checklist

Before publishing to Google Play or distributing an APK:

- Confirm the app builds and has been tested on supported Android versions.
- Replace all template branding, package identifiers, store links, and AdMob IDs.
- Check that wallpaper images, fonts, icons, and other assets may legally be redistributed.
- Review Google Play policies for ads, user data, notifications, and file/storage access.
- Configure release signing and keep signing credentials out of version control.
- Increment `versionCode` and set an appropriate `versionName` for each release.
- Test rewarded-ad behavior and ensure the app remains usable when an ad cannot load.

## License

This repository includes an MIT License; see [LICENSE](LICENSE). The license and redistribution rights for third-party libraries, ad SDKs, and image/design assets must be checked separately.

## Repository

[GitHub repository](https://github.com/tari9bro/-Java-Admob-ads-Wallpaper-Android-App-Reskin-Template-Max-Earning)


## CI and preview releases

The GitHub Actions workflow in `.github/workflows/android-ci.yml` runs unit tests and builds a debug APK on pushes and pull requests to `main`. It uploads the APK as a short-lived workflow artifact.

The `.github/workflows/android-release.yml` workflow creates a **GitHub prerelease** with a debug-signed APK when a `v*` tag is pushed, or when run manually from the Actions tab with a version such as `v1.0.0`. This APK is for evaluation only, not a production Google Play release. A production release should use a properly configured release signing key stored as GitHub Actions secrets, plus a tested and policy-compliant app build.
