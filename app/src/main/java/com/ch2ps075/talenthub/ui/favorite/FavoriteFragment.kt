package com.ch2ps075.talenthub.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.FragmentFavoriteBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.login.LoginActivity
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        viewModel.getSession().observe(requireActivity()) { user ->
            if (!user.isLogin) {
                showAlertDialog(
                    getString(R.string.inaccessible_title),
                    getString(R.string.no_session_text),
                    getString(R.string.continue_title),
                    WelcomeActivity::class.java
                )
            }
        }

        return binding.root
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        textButton: String,
        targetActivity: Class<*>? = LoginActivity::class.java,
    ) {
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(textButton) { _, _ ->
                val intent = Intent(requireActivity(), targetActivity)
                startActivity(intent)
                requireActivity().finish()
            }
            setNegativeButton(getString(R.string.back_title)) { _, _ ->
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }
}