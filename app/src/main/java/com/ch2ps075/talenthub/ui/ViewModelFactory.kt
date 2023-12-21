package com.ch2ps075.talenthub.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.repo.AuthRepository
import com.ch2ps075.talenthub.data.repo.TalentRepository
import com.ch2ps075.talenthub.injection.Injection
import com.ch2ps075.talenthub.ui.auth.login.LoginViewModel
import com.ch2ps075.talenthub.ui.main.MainViewModel
import com.ch2ps075.talenthub.ui.profile.language.LanguageViewModel
import com.ch2ps075.talenthub.ui.auth.register.RegisterViewModel
import com.ch2ps075.talenthub.ui.detail.TalentDetailViewModel
import com.ch2ps075.talenthub.ui.home.HomeViewModel
import com.ch2ps075.talenthub.ui.search.SearchViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(
    private val authRepository: AuthRepository,
    private val talentRepository: TalentRepository,
    private val languagePreferences: LanguagePreferences,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(talentRepository) as T
            }

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(talentRepository) as T
            }

            modelClass.isAssignableFrom(TalentDetailViewModel::class.java) -> {
                TalentDetailViewModel(talentRepository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(LanguageViewModel::class.java) -> {
                LanguageViewModel(languagePreferences) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(
            context: Context,
            languagePreferences: LanguagePreferences
        ): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideAuthRepository(context),
                Injection.provideTalentRepository(context),
                LanguagePreferences.getInstance(languagePreferences.dataStore)
            )
        }.also { instance = it }
    }
}