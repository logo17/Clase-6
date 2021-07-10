package com.loguito.clase6.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO 4 Creamos el provider de retrofit para abstraer la inicializacion
class RetrofitProvider {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService() : ApiService = retrofit.create(ApiService::class.java)
}