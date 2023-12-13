package com.ch2ps075.talenthub.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.FragmentSectionPagerBinding

class SectionPagerFragment : Fragment() {

    private lateinit var binding: FragmentSectionPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSectionPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0)
        val categoryName = arguments?.getString(ARG_NAME, "Category")
        
        binding.sectionLabel.text = getString(R.string.content_tab_text, position)
        binding.sectionName.text = getString(R.string.content_name_tab_text, categoryName)
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_NAME = "section_name"
    }
}