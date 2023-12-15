package com.ch2ps075.talenthub.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.FragmentProfileBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel
import com.ch2ps075.talenthub.ui.profile.helpcenter.HelpCenterActivity
import com.ch2ps075.talenthub.ui.profile.language.LanguageActivity
import com.ch2ps075.talenthub.ui.profile.privacy.PrivacyPolicyActivity
import com.ch2ps075.talenthub.ui.profile.settings.SettingsActivity
import com.ch2ps075.talenthub.ui.profile.terms.TermsOfServicesActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSession()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            helpCenter.setOnClickListener { startActivity(Intent(requireActivity(), HelpCenterActivity::class.java)) }
            language.setOnClickListener { startActivity(Intent(requireActivity(), LanguageActivity::class.java)) }
            termsOfServices.setOnClickListener { startActivity(Intent(requireActivity(), TermsOfServicesActivity::class.java)) }
            privacyPolicy.setOnClickListener { startActivity(Intent(requireActivity(), PrivacyPolicyActivity::class.java)) }
            settings.setOnClickListener { startActivity(Intent(requireActivity(), SettingsActivity::class.java)) }
        }
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

        /*if (!user.isLogin) {
            showWarningAlert()
        }*/
    }

    private fun showWarningAlert() {
        SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText(getString(R.string.inaccessible_title))
            .setContentText(getString(R.string.no_session_text))
            .setConfirmButton(getString(R.string.login_text)) {
                startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
            }
            .setCancelButton(getString(R.string.back_title)) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }
}