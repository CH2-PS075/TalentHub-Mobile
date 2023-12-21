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
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.ui.adapter.TalentAdapter
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.FragmentHomeBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.auth.login.LoginActivity
import com.ch2ps075.talenthub.helper.GridSpacingItemDecoration
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.category.CategoryActivity
import com.ch2ps075.talenthub.ui.detail.TalentDetailActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel
import com.ch2ps075.talenthub.ui.search.SearchFragment

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val talentAdapter = TalentAdapter()
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSession()
        setupCategoryClickListeners()
        observeTalents()
    }

    private fun observeTalents() {
        homeViewModel.getTalents().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        initRecyclerView()
                        showTalents(result.data)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        binding.lotteEmptyTalent.visibility = View.VISIBLE
                        binding.tvEmptyTalent.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showTalents(listTalents: List<Talent>) {
        if (listTalents.isNotEmpty()) {
            binding.rvTalents.visibility = View.VISIBLE
            talentAdapter.submitList(listTalents)
        } else {
            binding.rvTalents.visibility = View.INVISIBLE
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTalents.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            adapter = talentAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 16, true))
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

    private fun setCategoryClickListener(categoryId: Int) {
        val intent = Intent(requireActivity(), CategoryActivity::class.java)
        intent.putExtra(CATEGORY_ID, categoryId)
        startActivity(intent)
    }

    private fun setupCategoryClickListeners() {
        with(binding) {
            classicCategory.setOnClickListener { setCategoryClickListener(CLASSIC_GENRE_ID) }
            jazzCategory.setOnClickListener { setCategoryClickListener(JAZZ_GENRE_ID) }
            acousticCategory.setOnClickListener { setCategoryClickListener(ACOUSTIC_GENRE_ID) }
            popCategory.setOnClickListener { setCategoryClickListener(POP_GENRE_ID) }
            viewAllCategory.setOnClickListener { startActivity(Intent(requireActivity(), CategoryActivity::class.java)) }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
        const val CLASSIC_GENRE_ID = 0
        const val JAZZ_GENRE_ID = 1
        const val ACOUSTIC_GENRE_ID = 2
        const val POP_GENRE_ID = 3
    }
}