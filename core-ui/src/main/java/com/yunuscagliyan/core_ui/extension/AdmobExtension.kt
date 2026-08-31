package com.yunuscagliyan.core_ui.extension

import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.yunuscagliyan.core.extension.findActivity
import com.yunuscagliyan.core_ui.BuildConfig
import com.yunuscagliyan.core_ui.helper.AdmobHelper
import timber.log.Timber


fun Context.loadInterstitial() {
    val request = AdRequest.Builder()
        .build()
    InterstitialAd.load(
        this,
        BuildConfig.ADMOB_INTERSTITIAL_ID,
        request,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Timber.e("Interstitial ad failed to load: ${adError.code} ${adError.message}")
                AdmobHelper.clearInterstitialAd()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                Timber.d("Interstitial ad loaded")
                AdmobHelper.loadInterstitialAd(interstitialAd)
            }
        }
    )
}

fun Context.showInterstitial(onAdDismissed: () -> Unit) {
    val context = this
    val activity = this.findActivity()
    AdmobHelper.increaseEventCounter()
    if (activity != null && AdmobHelper.isInterstitialAdLoaded()) {
        AdmobHelper.setInterstitialFullScreenCallback(object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {
                super.onAdFailedToShowFullScreenContent(e)
                Timber.e("Interstitial ad failed to show: ${e.code} ${e.message}")
                AdmobHelper.clearInterstitialAd()
                onAdDismissed()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                AdmobHelper.clearInterstitialAd()
                context.loadInterstitial()
                onAdDismissed()
            }
        })
        AdmobHelper.showInterstitial(activity)
    }else{
        onAdDismissed()
    }
}