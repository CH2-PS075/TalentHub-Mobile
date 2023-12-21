package com.ch2ps075.talenthub.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.FragmentSectionPagerBinding
import com.ch2ps075.talenthub.helper.GridSpacingItemDecoration
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.adapter.TalentAdapter
import com.ch2ps075.talenthub.ui.detail.TalentDetailActivity
import com.ch2ps075.talenthub.ui.home.HomeViewModel
import com.ch2ps075.talenthub.ui.search.SearchFragment

class SectionPagerFragment : Fragment() {

    private lateinit var binding: FragmentSectionPagerBinding
    private val talentAdapter = TalentAdapter()
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext(), LanguagePreferences.getInstance(requireContext().languageDataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSectionPagerBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTalents()
    }

    private fun observeTalents() {
        val categoryName = arguments?.getString(ARG_CATEGORY_NAME, "Category")
        if (categoryName != null) {
            homeViewModel.getTalentsByCategory(categoryName).observe(requireActivity()) { result ->
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
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTalentPager.apply {
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

    private fun showTalents(listTalents: List<Talent>) {
        if (listTalents.isNotEmpty()) {
            binding.rvTalentPager.visibility = View.VISIBLE
            talentAdapter.submitList(listTalents)
        } else {
            binding.rvTalentPager.visibility = View.INVISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_CATEGORY_NAME = "category_name"
    }
}