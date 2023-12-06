package com.ch2ps075.talenthub.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ch2ps075.talenthub.ui.main.MainActivity
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.databinding.ActivityLoginBinding
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            loginButton.setOnClickListener {
                when {
                    edLoginEmail.text.toString().isEmpty() -> {
                        edLoginEmail.error = getString(R.string.error_empty_field)
                    }

                    edLoginPassword.text.toString().length < 8 -> {
                        edLoginPassword.error = getString(R.string.error_short_password)
                    }

                    else -> login()
                }
            }
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()

        viewModel.login(email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        viewModel.saveSession(
                            UserModel(
                                username = result.data.loginResult.name,
                                email = email,
                                token = result.data.loginResult.token
                            )
                        )
                        showAlertDialog(
                            getString(R.string.success_title),
                            result.data.message,
                            getString(R.string.continue_title),
                            MainActivity::class.java
                        )
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        showAlertDialog(
                            getString(R.string.failed_title),
                            result.error,
                            getString(R.string.try_again_title)
                        )
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        textButton: String,
        targetActivity: Class<*>? = LoginActivity::class.java,
    ) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(textButton) { _, _ ->
                val intent = Intent(this@LoginActivity, targetActivity)
                startActivity(intent)
                finish()
            }.create().apply {
                setCanceledOnTouchOutside(false)
                show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}