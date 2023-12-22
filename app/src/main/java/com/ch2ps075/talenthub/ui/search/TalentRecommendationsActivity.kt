package com.ch2ps075.talenthub.ui.search

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.data.preference.LanguagePreferences
import com.ch2ps075.talenthub.data.preference.languageDataStore
import com.ch2ps075.talenthub.databinding.ActivityTalentRecommendationsBinding
import com.ch2ps075.talenthub.state.ResultState
import com.ch2ps075.talenthub.ui.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class TalentRecommendationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalentRecommendationsBinding
    private val viewModel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance(this, LanguagePreferences.getInstance(this.languageDataStore))
    }
    private var selectedCategory: String? = null
    private var selectedGroupType: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalentRecommendationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAutoComplete()
        binding.ivBack.setOnClickListener { onSupportNavigateUp() }

        binding.cbLocation.setOnClickListener {
            if (!checkPermission(FINE_LOCATION_REQUIRED_PERMISSION) &&
                !checkPermission(COARSE_LOCATION_REQUIRED_PERMISSION)
            ) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        FINE_LOCATION_REQUIRED_PERMISSION,
                        COARSE_LOCATION_REQUIRED_PERMISSION
                    )
                )
            }
        }

        with(binding) {
            recommendationSearchButton.setOnClickListener {
                when {
                    edPrice.text.isEmpty() -> {
                        edPrice.error = getString(R.string.error_empty_field)
                    }

                    else -> setupUI()
                }
            }
        }
    }

    private fun setupAutoComplete() {
        setupAutoCompleteOptions(binding.autoCompleteCategory, R.array.category_titles_recc)
        setupAutoCompleteOptions(binding.autoCompleteGrouptype, R.array.group_type_titles_recc)
    }

    private fun setupUI() {
        if (!selectedCategory.isNullOrEmpty() && !selectedGroupType.isNullOrEmpty()) {
            val selectedCategory = selectedCategory
            val selectedGroupType = selectedGroupType
            val priceString = binding.edPrice.text.toString()

            val price = priceString.toIntOrNull()

            getMyLastLocation { latitude, longitude ->
                if (selectedCategory != null && selectedGroupType != null && latitude != null && longitude != null && price != null) {

                    if (binding.cbLocation.isChecked) {
                        getRecommendationTalents(latitude, longitude, price, selectedCategory, selectedGroupType)
                    } else {
                        Toast.makeText(this@TalentRecommendationsActivity, getString(R.string.location_checklist), Toast.LENGTH_SHORT).show()
                    }
                }
            }


        } else {
            Toast.makeText(this@TalentRecommendationsActivity, getString(R.string.required_category_grouptype), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRecommendationTalents(
        latitude: Double,
        longitude: Double,
        price: Int,
        category: String,
        quantity: String,
    ) {
        viewModel.getRecommendationTalents(
            latitude, longitude, price, category, quantity
        ).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {

                    }

                    is ResultState.Success -> {

                    }

                    is ResultState.Error -> {

                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupAutoCompleteOptions(
        autoCompleteTextView: AutoCompleteTextView,
        arrayResId: Int,
    ) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(arrayResId)
        )
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedValue = adapter.getItem(position).toString()
            when (autoCompleteTextView) {
                binding.autoCompleteCategory -> {
                    selectedCategory = selectedValue
                }

                binding.autoCompleteGrouptype -> {
                    selectedGroupType = selectedValue
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }
            }
        }

    private fun getMyLastLocation(latLng: (Double?, Double?) -> Unit = { _, _ -> }) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkPermission(FINE_LOCATION_REQUIRED_PERMISSION) &&
            checkPermission(COARSE_LOCATION_REQUIRED_PERMISSION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    latLng(latitude, longitude)
                } else {
                    showToast(getString(R.string.error_empty_field))
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    FINE_LOCATION_REQUIRED_PERMISSION,
                    COARSE_LOCATION_REQUIRED_PERMISSION
                )
            )
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val FINE_LOCATION_REQUIRED_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        private const val COARSE_LOCATION_REQUIRED_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}