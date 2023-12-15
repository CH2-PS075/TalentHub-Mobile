package com.ch2ps075.talenthub.ui.profile.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.databinding.ActivityMyProfileBinding

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
        personalInformation()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun personalInformation(/*user: UserModel*/) {
        /*val username = if (user.isLogin) user.username else "Username"
        val contact = if (user.isLogin) user.username else "Contact"
        val address = if (user.isLogin) user.username else "Address"*/

        with(binding) {
            tvUsername.text = "Piwew"
            tvContact.text = "081318896311"
            tvAddress.text = "Jalan Bima Sakti 14, Kabupaten Bogor, Jawa Barat"
        }
    }
}