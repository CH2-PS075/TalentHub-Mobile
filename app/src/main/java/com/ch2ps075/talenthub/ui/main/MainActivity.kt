package com.ch2ps075.talenthub.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.ActivityMainBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.ai.AiFragment
import com.ch2ps075.talenthub.ui.favorite.FavoriteFragment
import com.ch2ps075.talenthub.ui.home.HomeFragment
import com.ch2ps075.talenthub.ui.profile.ProfileFragment
import com.ch2ps075.talenthub.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val aiFragment = AiFragment()
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

                R.id.action_ai -> setCurrentFragment(aiFragment).apply { showBottomDialog() }

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

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_sheet_layout)

        val actionGallery: LinearLayout = dialog.findViewById(R.id.action_gallery)
        val actionCamera: LinearLayout = dialog.findViewById(R.id.action_camera)
        val cancelButton: ImageView = dialog.findViewById(R.id.cancelButton)

        actionGallery.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Upload a Video is clicked", Toast.LENGTH_SHORT)
                .show()
        }

        actionCamera.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Create a short is Clicked", Toast.LENGTH_SHORT)
                .show()
        }

        cancelButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun MenuItem.changeIcon(drawableId: Int) {
        this.icon = ContextCompat.getDrawable(this@MainActivity, drawableId)
    }

    private fun checkUserLogin() {
        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                Toast.makeText(this, "Welcome to TalentHub!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}