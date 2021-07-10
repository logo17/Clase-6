package com.loguito.clase6.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding4.widget.textChanges
import com.loguito.clase6.R
import com.loguito.clase6.viewmodels.CurrentWeatherViewModel
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {
    private lateinit var searchEditText: TextInputEditText
    private lateinit var cityName: TextView
    private lateinit var cityMapImage: ImageView
    private lateinit var weatherIconImageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = view.findViewById(R.id.searchEditText)
        cityName = view.findViewById(R.id.textView)
        cityMapImage = view.findViewById(R.id.imageView)
        weatherIconImageView = view.findViewById(R.id.imageView2)
        progressBar = view.findViewById(R.id.progressBar)

        searchEditText.textChanges()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .subscribe {
                viewModel.fetchCurrentWeatherData(it)
            }

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) {
            cityName.text = it.name

            Picasso.get()
                .load("https://maps.googleapis.com/maps/api/staticmap?center=${it.coord.lat},${it.coord.lon}&zoom=14&size=400x400&key=")
                .into(cityMapImage)

            Picasso.get()
                .load("https://openweathermap.org/img/w/${it.weather.first().icon}.png")
                .into(weatherIconImageView)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.serverError.observe(viewLifecycleOwner) {
            Snackbar.make(searchEditText, requireContext().getString(R.string.server_error_message), Snackbar.LENGTH_LONG).show()
        }
    }
}