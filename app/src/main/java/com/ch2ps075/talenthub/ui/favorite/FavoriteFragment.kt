package com.ch2ps075.talenthub.ui.favorite

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
import com.ch2ps075.talenthub.data.local.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.FragmentFavoriteBinding
import com.ch2ps075.talenthub.helper.GridSpacingItemDecoration
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.adapter.TalentAdapter
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val talentAdapter = TalentAdapter()
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSession()
        initRecyclerView()
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

        binding.rvFavoriteTalent.apply {
            layoutManager = mLayoutManager
            adapter = talentAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 24, true))
        }

        if (dataItems.isEmpty()) {
            binding.tvEmptyFavorite.visibility = View.VISIBLE
            binding.lottieEmptyFavorite.visibility = View.VISIBLE
        } else {
            talentAdapter.submitList(dataItems)
        }
    }

    private fun observeSession() {
        viewModel.getSession().observe(requireActivity()) { user ->
            if (!user.isLogin) {
                showWarningAlert()
            }
        }
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