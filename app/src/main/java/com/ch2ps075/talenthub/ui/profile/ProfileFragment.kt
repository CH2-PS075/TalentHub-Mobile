package com.ch2ps075.talenthub.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.databinding.FragmentProfileBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.login.LoginActivity
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel
import com.ch2ps075.talenthub.ui.profile.helpcenter.HelpCenterActivity
import com.ch2ps075.talenthub.ui.profile.language.LanguageActivity
import com.ch2ps075.talenthub.ui.profile.privacy.PrivacyPolicyActivity
import com.ch2ps075.talenthub.ui.profile.settings.SettingsActivity
import com.ch2ps075.talenthub.ui.profile.terms.TermsOfServicesActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        observeSession()
        binding.helpCenter.setOnClickListener { startActivity(Intent(requireActivity(), HelpCenterActivity::class.java)) }
        binding.language.setOnClickListener { startActivity(Intent(requireActivity(), LanguageActivity::class.java)) }
        binding.termsOfServices.setOnClickListener { startActivity(Intent(requireActivity(), TermsOfServicesActivity::class.java)) }
        binding.privacyPolicy.setOnClickListener { startActivity(Intent(requireActivity(), PrivacyPolicyActivity::class.java)) }
        binding.settings.setOnClickListener { startActivity(Intent(requireActivity(), SettingsActivity::class.java)) }
        return binding.root
    }

    private fun observeSession() {
        viewModel.getSession().observe(requireActivity()) { user ->
            handleSession(user)
        }
    }

    private fun handleSession(user: UserModel) {
        val name = if (user.isLogin) user.username else "TALENTNITY!"
        val email = if (user.isLogin) user.email else "-"

        with(binding) {
            tvProfileName.text = resources.getString(R.string.name_profile, name)
            tvProfileEmail.text = resources.getString(R.string.email_profile, email)
        }

        if (!user.isLogin) {
            showAlertDialog(
                getString(R.string.inaccessible_title),
                getString(R.string.no_session_text),
                getString(R.string.continue_title),
                WelcomeActivity::class.java
            )
        }
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveButtonResId: String,
        targetActivity: Class<*>? = LoginActivity::class.java,
    ) {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButtonResId) { _, _ ->
                val intent = Intent(requireActivity(), targetActivity)
                startActivity(intent)
                requireActivity().finish()
            }
            setNegativeButton(getString(R.string.back_title)) { _, _ ->
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }.create().apply {
            setCanceledOnTouchOutside(false)
            show()
        }
    }
}