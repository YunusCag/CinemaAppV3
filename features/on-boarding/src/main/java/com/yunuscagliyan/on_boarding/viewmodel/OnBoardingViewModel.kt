package com.yunuscagliyan.on_boarding.viewmodel

import androidx.lifecycle.ViewModel
import com.yunuscagliyan.core.data.local.preference.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {
    fun saveShowShouldPreference() {
        preferences.shouldShowOnBoard = false
    }
}