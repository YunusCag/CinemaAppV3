package com.yunuscagliyan.core.util

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import com.yunuscagliyan.core.BuildConfig

/**
 * The Google Mobile Ads SDK provides the User Messaging Platform (Google's IAB Certified consent
 * management platform) as one solution to capture consent for users in GDPR impacted countries.
 * This is an example and you can choose another consent management platform to capture consent.
 */
class MobileAdsConsentManager private constructor(
    context: Context
) {
    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(context)

    /** Interface definition for a callback to be invoked when consent gathering is complete. */
    fun interface OnConsentGatheringCompleteListener {
        fun consentGatheringComplete(error: FormError?)
    }

    /** Helper variable to determine if the app can request ads. */
    val canRequestAds: Boolean
        get() = consentInformation.canRequestAds()

    /** Helper variable to determine if the privacy options form is required. */
    val isPrivacyOptionRequired: Boolean
        get() = consentInformation.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED


    fun gatherConsent(
        activity: Activity,
        onConsentGatheringCompleteListener: OnConsentGatheringCompleteListener
    ) {
        // For testing purposes
        val debugSettings =
            ConsentDebugSettings.Builder(activity)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId("B4BA8A6EAF9004918E0E47840092F844")
                .build()

        val paramsBuilder = ConsentRequestParameters.Builder()
        if (BuildConfig.DEBUG) {
            paramsBuilder.setConsentDebugSettings(debugSettings)
        }
        val params = paramsBuilder.build()

        // Requesting an update to consent information should be called on every app launch.
        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
                    // Consent has been gathered.
                    onConsentGatheringCompleteListener.consentGatheringComplete(formError)
                }
            },
            { requestConsentError ->
                onConsentGatheringCompleteListener.consentGatheringComplete(requestConsentError)
            }
        )
    }
    /** Helper method to call the UMP SDK method to show the privacy options form. */
    fun showPrivacyOptionsForm(
        activity: Activity,
        onConsentFormDismissedListener: ConsentForm.OnConsentFormDismissedListener
    ) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity,onConsentFormDismissedListener)
    }

    companion object {
        @Volatile
        private var instance: MobileAdsConsentManager? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: MobileAdsConsentManager(context).also { instance = it }
        }
    }
}