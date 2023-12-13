package com.ch2ps075.talenthub.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.ui.adapter.TalentAdapter
import com.ch2ps075.talenthub.data.local.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.FragmentHomeBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.auth.login.LoginActivity
import com.ch2ps075.talenthub.helper.GridSpacingItemDecoration
import com.ch2ps075.talenthub.ui.category.CategoryActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val talentAdapter = TalentAdapter()
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeSession()
        binding.viewAllCategory.setOnClickListener { startActivity(Intent(requireActivity(), CategoryActivity::class.java)) }
    }

    private fun initRecyclerView() {
        val dataItems = listOf(
            Talent(1, "John Doe", "Singer", "Bekasi, Indonesia", "https://i.imgur.com/oYqwuI0.png"),
            Talent(2, "Jane Smith", "Dancer", "Tangerang, Indonesia", "https://i.imgur.com/oYqwuI0.png"),
            Talent(3, "David Johnson", "Actor", "Depok, Indonesia", "https://i.imgur.com/oYqwuI0.png"),
            Talent(4, "Sarah Williams", "Musician", "Bandung, Indonesia", "https://i.imgur.com/oYqwuI0.png"),
            Talent(5, "Michael Brown", "Comedian", "Bali, Indonesia", "https://i.imgur.com/oYqwuI0.png"),
            Talent(6, "Emily Davis", "Painter", "Jakarta, Indonesia", "https://i.imgur.com/oYqwuI0.png")
        )

        val mLayoutManager = GridLayoutManager(requireContext(), 2)

        binding.rvTalents.apply {
            layoutManager = mLayoutManager
            adapter = talentAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 16, true))
        }

        talentAdapter.submitList(dataItems)
    }

    private fun observeSession() {
        viewModel.getSession().observe(requireActivity()) { user ->
            binding.tvStateIsloginOrlogout.setOnClickListener {
                if (user.isLogin) {
                    showWarningAlert()
                } else {
                    startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
                }
            }

            val name = if (user.isLogin) user.username else "TALENTNITY!"
            val loginTextRes = if (user.isLogin) R.string.logout_text else R.string.login_text

            binding.tvName.text = resources.getString(R.string.account_name, name)
            binding.tvStateIsloginOrlogout.text = resources.getString(loginTextRes)
        }
    }

    private fun showWarningAlert() {
        SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText(getString(R.string.logout_text))
            .setContentText(getString(R.string.logout_confirm_text))
            .setConfirmButton(getString(R.string.logout_text)) {
                viewModel.logout()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
            .setCancelButton(getString(R.string.back_title)) { it.dismissWithAnimation() }
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }
}