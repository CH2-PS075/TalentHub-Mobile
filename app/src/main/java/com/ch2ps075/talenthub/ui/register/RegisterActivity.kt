package com.ch2ps075.talenthub.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.ActivityRegisterBinding
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            registerButton.setOnClickListener {
                when {
                    edRegisterUsername.text.toString().isEmpty() -> {
                        edRegisterUsername.error = getString(R.string.error_empty_field)
                    }

                    edRegisterEmail.text.toString().isEmpty() -> {
                        edRegisterEmail.error = getString(R.string.error_empty_field)
                    }

                    edRegisterPassword.text.toString().length < 8 -> {
                        edRegisterPassword.error = getString(R.string.error_short_password)
                    }

                    else -> register()
                }

                tvSignin.setOnClickListener {
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                }
            }
        }
    }

    private fun register() {
        val username = binding.edRegisterUsername.text.toString()
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()

        viewModel.register(username, email, password)
            .observe(this@RegisterActivity) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            showSuccessAlert()
                            showLoading(false)
                        }

                        is ResultState.Error -> {
                            showErrorAlert()
                            showLoading(false)
                        }
                    }
                }
            }
    }

    private fun showSuccessAlert() {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(getString(R.string.success_title))
            .setContentText(getString(R.string.success_register_message))
            .setConfirmButton(getString(R.string.login_text)) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun showErrorAlert() {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(getString(R.string.failed_title))
            .setContentText(getString(R.string.failed_register_message))
            .setConfirmButton(getString(R.string.try_again_title)) {
                it.dismissWithAnimation()
            }
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}