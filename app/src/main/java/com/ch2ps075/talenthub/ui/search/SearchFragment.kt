package com.ch2ps075.talenthub.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.FragmentSearchBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.login.LoginActivity
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        observeSession()
        setupSearchView()
        binding.tvTalentSize.text = getString(R.string.display_talent_size, "0")
        return binding.root
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val textSearch = searchView.text.toString()
                searchBar.setText(textSearch)
                searchView.hide()
                showToast(textSearch)
                false
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun observeSession() {
        viewModel.getSession().observe(requireActivity()) { user ->
            if (!user.isLogin) {
                showAlertDialog(
                    R.string.inaccessible_title,
                    R.string.no_session_text,
                    R.string.continue_title,
                    WelcomeActivity::class.java
                )
            }
        }
    }

    private fun showAlertDialog(
        titleResId: Int,
        messageResId: Int,
        textButtonResId: Int,
        targetActivity: Class<*>? = LoginActivity::class.java,
    ) {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle(titleResId)
            setMessage(messageResId)
            setPositiveButton(textButtonResId) { _, _ ->
                val intent = Intent(requireActivity(), targetActivity)
                startActivity(intent)
                requireActivity().finish()
            }
            setNegativeButton(R.string.back_title) { _, _ ->
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }.create().apply {
            setCanceledOnTouchOutside(false)
            show()
        }
    }
}