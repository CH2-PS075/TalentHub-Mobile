package com.ch2ps075.talenthub.ui.auth.registertalent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityRegisterTalentBinding
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.ch2ps075.talenthub.ui.auth.login.LoginActivity

class RegisterTalentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterTalentBinding

    private val viewModel by viewModels<RegisterTalentViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTalentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            registerButton.setOnClickListener {
                when {
                    tvImageFileName.text.toString().isEmpty() -> {
                        tvImageFileName.error = getString(R.string.error_empty_field)
                    }

                    edRegisterTalentname.text.toString().isEmpty() -> {
                        edRegisterTalentname.error = getString(R.string.error_empty_field)
                    }

                    edRegisterQuantity.text.toString().isEmpty() -> {
                        edRegisterQuantity.error = getString(R.string.error_empty_field)
                    }

                    edRegisterAddress.text.toString().isEmpty() -> {
                        edRegisterAddress.error = getString(R.string.error_empty_field)
                    }

                    edRegisterContact.text.toString().isEmpty() -> {
                        edRegisterContact.error = getString(R.string.error_empty_field)
                    }

                    edRegisterPrice.text.toString().isEmpty() -> {
                        edRegisterPrice.error = getString(R.string.error_empty_field)
                    }

                    edRegisterEmail.text.toString().isEmpty() -> {
                        edRegisterEmail.error = getString(R.string.error_empty_field)
                    }

                    edRegisterPassword.text.toString().length < 8 -> {
                        edRegisterPassword.error = getString(R.string.error_short_password)
                    }

                    else -> register()
                }

                btnUploadImage.setOnClickListener {
                    openGallery()
                }

                tvSignin.setOnClickListener {
                    startActivity(Intent(this@RegisterTalentActivity, LoginActivity::class.java))
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }
    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val selectedFileName = selectedImageUri?.let { getFileName(it) }
            binding.tvImageFileName.text = selectedFileName
        }
    }

    private fun getFileName(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        return cursor?.use { c ->
            val displayNameIndex = c.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (displayNameIndex >= 0 && c.moveToFirst()) {
                c.getString(displayNameIndex)
            } else {
                uri.lastPathSegment.orEmpty()
            }
        } ?: uri.lastPathSegment.orEmpty()
    }

    private fun register() {
        with(binding) {
            val imageFileName = tvImageFileName.text.toString()
            val talentName = edRegisterTalentname.text.toString()
            val quantity = edRegisterQuantity.text.toString()
            val address = edRegisterAddress.text.toString()
            val contact = edRegisterContact.text.toString()
            val price = edRegisterPrice.text.toString()
            val email = edRegisterEmail.text.toString()
            val password = edRegisterPassword.text.toString()

            viewModel.register(imageFileName, talentName, quantity, address, contact, price, email, password)
                .observe(this@RegisterTalentActivity) { result ->
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