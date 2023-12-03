package com.ch2ps075.talenthub.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.ui.adapter.TalentAdapter
import com.ch2ps075.talenthub.data.local.Talent
import com.ch2ps075.talenthub.databinding.FragmentHomeBinding
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.WelcomeActivity
import com.ch2ps075.talenthub.ui.login.LoginActivity
import com.ch2ps075.talenthub.ui.main.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val talentAdapter = TalentAdapter()
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRecyclerView()
        observeSession()
        return binding.root
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
        }

        talentAdapter.submitList(dataItems)
    }

    private fun observeSession() {
        viewModel.getSession().observe(requireActivity()) { user ->
            binding.tvStateIsloginOrlogout.setOnClickListener {
                val targetClass = if (user.isLogin) {
                    viewModel.logout()
                    LoginActivity::class.java
                } else {
                    WelcomeActivity::class.java
                }
                startActivity(Intent(requireActivity(), targetClass))
            }

            val name = if (user.isLogin) user.username else "TALENTNITY!"
            val loginTextRes = if (user.isLogin) R.string.logout_text else R.string.login_text

            binding.tvName.text = resources.getString(R.string.account_name, name)
            binding.tvStateIsloginOrlogout.text = resources.getString(loginTextRes)
        }
    }
}