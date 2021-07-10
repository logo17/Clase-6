package com.loguito.clase6.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loguito.clase6.network.RetrofitProvider
import com.loguito.clase6.network.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentWeatherViewModel : ViewModel() {
    private val retrofitProvider = RetrofitProvider()

    private val _currentWeatherLiveData = MutableLiveData<WeatherResponse>()
    val currentWeatherLiveData: LiveData<WeatherResponse> = _currentWeatherLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _serverError = MutableLiveData<Boolean>()
    val serverError: LiveData<Boolean> = _serverError

    fun fetchCurrentWeatherData(city: String) {
        _isLoading.postValue(true)
        retrofitProvider.getApiService()
            .getCurrentWeatherByCity(city, "")
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    _isLoading.postValue(false)
                    if (response.isSuccessful) {
                        _currentWeatherLiveData.postValue(response.body())
                    } else {
                        // Servidor falla, por varias razones, por ejemplo, no se armo bien el request
                        _serverError.postValue(true)
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    _isLoading.postValue(false)
                    _serverError.postValue(true)
                }

            })
    }
}