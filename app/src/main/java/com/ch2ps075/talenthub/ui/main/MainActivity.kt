package com.ch2ps075.talenthub.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityMainBinding
import com.ch2ps075.talenthub.helper.LanguageUtil
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.favorite.FavoriteFragment
import com.ch2ps075.talenthub.ui.home.HomeFragment
import com.ch2ps075.talenthub.ui.profile.ProfileFragment
import com.ch2ps075.talenthub.ui.profile.language.LanguageViewModel
import com.ch2ps075.talenthub.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    private val languageViewModel by viewModels<LanguageViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        languageViewModel.getLanguageSetting().observe(this) { languageCode ->
            applyLanguage(languageCode)
        }

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val favoriteFragment = FavoriteFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    setCurrentFragment(homeFragment)
                    binding.bottomNavigation.menu.findItem(R.id.action_search)
                        ?.changeIcon(R.drawable.ic_search)
                    binding.bottomNavigation.menu.findItem(R.id.action_home)
                        ?.changeIcon(R.drawable.ic_home_active)
                    binding.bottomNavigation.menu.findItem(R.id.action_favorite)
                        ?.changeIcon(R.drawable.ic_favorite)
                    binding.bottomNavigation.menu.findItem(R.id.action_profile)
                        ?.changeIcon(R.drawable.ic_profile)
                }

                R.id.action_search -> {
                    setCurrentFragment(searchFragment)
                    binding.bottomNavigation.menu.findItem(R.id.action_search)
                        ?.changeIcon(R.drawable.ic_search_active)
                    binding.bottomNavigation.menu.findItem(R.id.action_home)
                        ?.changeIcon(R.drawable.ic_home)
                    binding.bottomNavigation.menu.findItem(R.id.action_favorite)
                        ?.changeIcon(R.drawable.ic_favorite)
                    binding.bottomNavigation.menu.findItem(R.id.action_profile)
                        ?.changeIcon(R.drawable.ic_profile)
                }

                R.id.action_favorite -> {
                    setCurrentFragment(favoriteFragment)
                    binding.bottomNavigation.menu.findItem(R.id.action_search)
                        ?.changeIcon(R.drawable.ic_search)
                    binding.bottomNavigation.menu.findItem(R.id.action_home)
                        ?.changeIcon(R.drawable.ic_home)
                    binding.bottomNavigation.menu.findItem(R.id.action_favorite)
                        ?.changeIcon(R.drawable.ic_favorite_active)
                    binding.bottomNavigation.menu.findItem(R.id.action_profile)
                        ?.changeIcon(R.drawable.ic_profile)
                }

                R.id.action_profile -> {
                    setCurrentFragment(profileFragment)
                    binding.bottomNavigation.menu.findItem(R.id.action_search)
                        ?.changeIcon(R.drawable.ic_search)
                    binding.bottomNavigation.menu.findItem(R.id.action_home)
                        ?.changeIcon(R.drawable.ic_home)
                    binding.bottomNavigation.menu.findItem(R.id.action_favorite)
                        ?.changeIcon(R.drawable.ic_favorite)
                    binding.bottomNavigation.menu.findItem(R.id.action_profile)
                        ?.changeIcon(R.drawable.ic_profile_active)
                }
            }
            true
        }

        checkUserLogin()
    }

    private fun applyLanguage(languageCode: String) {
        LanguageUtil.setLocale(this, languageCode)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun MenuItem.changeIcon(drawableId: Int) {
        this.icon = ContextCompat.getDrawable(this@MainActivity, drawableId)
    }

    private fun checkUserLogin() {
        mainViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                mainViewModel.toastText.observe(this) { event ->
                    event.getContentIfNotHandled()?.let { _ ->
                        Toast.makeText(this, getString(R.string.welcome_greeting), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}