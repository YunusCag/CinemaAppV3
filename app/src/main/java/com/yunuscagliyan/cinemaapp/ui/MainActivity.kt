package com.yunuscagliyan.cinemaapp.ui

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.ads.MobileAds
import com.yunuscagliyan.cinemaapp.navigation.SetupNavGraph
import com.yunuscagliyan.core.data.enums.ThemeType
import com.yunuscagliyan.core.util.Constants
import com.yunuscagliyan.core.util.MobileAdsConsentManager
import com.yunuscagliyan.core_ui.extension.loadInterstitial
import com.yunuscagliyan.core_ui.helper.AdmobHelper
import com.yunuscagliyan.core_ui.theme.CinemaAppTheme
import com.yunuscagliyan.home.home.viewmodel.main.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var keepScreen: Boolean = true
    private var isMobileAdsInitializedCalled = AtomicBoolean(false)
    private lateinit var mobileAdsConsentManager: MobileAdsConsentManager

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
            keepScreen
        }
        getConsent()
        lifecycleScope.launch {
            delay(Constants.DurationUTil.SPLASH_DURATION.toLong())
            keepScreen = false
        }

        setContent {
            val context = LocalContext.current
            val appViewModel: AppViewModel = hiltViewModel()
            appViewModel.initLocale(context)

            val state by appViewModel.state
            val darkTheme = when (state.currentTheme) {
                ThemeType.Auto -> isSystemInDarkTheme()
                ThemeType.Dark -> true
                ThemeType.Light -> false
            }

            CinemaAppTheme(
                darkTheme = darkTheme
            ) {
                val navController = rememberAnimatedNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }

    private fun getConsent() {
        mobileAdsConsentManager = MobileAdsConsentManager.getInstance(applicationContext)
        mobileAdsConsentManager.gatherConsent(this) { error ->
            if (error != null) {
                Timber.e("${error.errorCode}: ${error.message}")
            }
            if (mobileAdsConsentManager.canRequestAds) {
                initializeMobileAdsSdk()
            }
        }
        if (mobileAdsConsentManager.canRequestAds) {
            initializeMobileAdsSdk()
        }
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializedCalled.getAndSet(true)) return
        MobileAds.initialize(this)
        this.loadInterstitial()
    }

    override fun onDestroy() {
        super.onDestroy()
        AdmobHelper.clearInterstitialAd()
    }
}