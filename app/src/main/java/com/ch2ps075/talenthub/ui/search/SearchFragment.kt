package com.ch2ps075.talenthub.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.FragmentSearchBinding
import com.ch2ps075.talenthub.helper.GridSpacingItemDecoration
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.adapter.TalentAdapter
import com.ch2ps075.talenthub.ui.detail.TalentDetailActivity
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val talentAdapter = TalentAdapter()
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(
            requireContext(),
            LanguagePreferences.getInstance(requireContext().languageDataStore)
        )
    }
    private val searchViewModel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance(
            requireContext(),
            LanguagePreferences.getInstance(requireContext().languageDataStore)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSession()
        setupSearchView()
        observeTalents()
        binding.tvTalentSize.text = getString(R.string.display_talent_size, "0")
    }

    private fun observeTalents() {
        searchViewModel.getTalentsByName("").observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        initRecyclerView()
                        showTalents(result.data)
                        showLoading(false)
                        binding.tvTalentSize.text = getString(
                            R.string.display_talent_size,
                            result.data.size.toString()
                        )
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val textSearch = searchView.text.toString()
                searchBar.setText(textSearch)
                searchView.hide()
                showToast(getString(R.string.display_talent_search, textSearch))
                searchViewModel.getTalentsByName(textSearch).observe(requireActivity()) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultState.Loading -> {
                                showLoading(true)
                            }

                            is ResultState.Success -> {
                                initRecyclerView()
                                showTalents(result.data)
                                showLoading(false)
                                binding.tvTalentSize.text = getString(
                                    R.string.display_talent_size,
                                    result.data.size.toString()
                                )
                            }

                            is ResultState.Error -> {
                                showLoading(false)
                            }
                        }
                    }
                }
                false
            }
        }
        setupMenu()
    }

    private fun showTalents(listTalents: List<Talent>) {
        if (listTalents.isNotEmpty()) {
            binding.rvTalentSearch.visibility = View.VISIBLE
            talentAdapter.submitList(listTalents)
        } else {
            binding.rvTalentSearch.visibility = View.INVISIBLE
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTalentSearch.apply {
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
        intentToDetail.putExtra(TALENT_ID, talent.talentId.toString())
        startActivity(intentToDetail)
    }

    private fun setupMenu() {
        binding.searchBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_recommendation_search -> {
                    val intent =
                        Intent(requireActivity(), TalentRecommendationsActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val TALENT_ID = "TALENT_ID"
    }
}