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
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.FragmentFavoriteBinding
import com.ch2ps075.talenthub.helper.GridSpacingItemDecoration
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.adapter.TalentAdapter
import com.ch2ps075.talenthub.ui.detail.TalentDetailActivity
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel
import com.ch2ps075.talenthub.ui.search.SearchFragment

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val talentAdapter = TalentAdapter()
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSession()
        initRecyclerView()

        favoriteViewModel.getAllFavoriteTalents().observe(requireActivity()) { talent ->
            val items = arrayListOf<Talent>()
            talent.map { item ->
                val data = Talent(
                    talentId = item.talentId,
                    talentName = item.talentName,
                    quantity = item.quantity,
                    address = item.address,
                    category = item.category,
                    contact = item.contact,
                    price = item.price,
                    picture = item.picture,
                    portfolio = item.portfolio,
                    latitude = item.latitude,
                    longitude = item.longitude
                )
                items.add(data)
            }

            if (items.isEmpty()) {
                binding.rvFavoriteTalent.visibility = View.INVISIBLE
                binding.tvEmptyFavorite.visibility = View.VISIBLE
                binding.lottieEmptyFavorite.visibility = View.VISIBLE
            } else {
                talentAdapter.submitList(items)
            }
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFavoriteTalent.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            adapter = talentAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 8, false))
        }

        talentAdapter.setOnItemClickCallback(object : TalentAdapter.OnItemClickCallback {
            override fun onItemClicked(talent: Talent) {
                showSelectedTalent(talent)
            }
        })
    }

    private fun showSelectedTalent(talent: Talent) {
        val intentToDetail = Intent(requireActivity(), TalentDetailActivity::class.java)
        intentToDetail.putExtra(SearchFragment.TALENT_ID, talent.talentId)
        startActivity(intentToDetail)
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