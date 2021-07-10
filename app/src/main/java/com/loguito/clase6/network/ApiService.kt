package com.loguito.clase6.network

import com.loguito.clase6.network.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// TODO 3: Creamos la interfaz donde vamos a declarar los diferentes metodos HTTP
interface ApiService {
    @GET("weather")
    fun getCurrentWeatherByCity(@Query("q") city: String, @Query("appid") apiKey: String) : Call<WeatherResponse>
}