package com.ch2ps075.talenthub.ui.profile.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import kotlinx.coroutines.launch

class LanguageViewModel(private val languagePref: LanguagePreferences) : ViewModel() {

    fun getLanguageSetting(): LiveData<String> {
        return languagePref.getLanguageSetting().asLiveData()
    }

    fun saveLanguageSetting(languageCode: String) {
        viewModelScope.launch {
            languagePref.saveLanguageSetting(languageCode)
        }
    }
}